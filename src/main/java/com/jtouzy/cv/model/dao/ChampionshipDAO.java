package com.jtouzy.cv.model.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.Championship.Type;
import com.jtouzy.cv.model.classes.ChampionshipTeam;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.cv.model.errors.RankingsCalculateException;
import com.jtouzy.dao.DAOManager;
import com.jtouzy.dao.errors.DAOCrudException;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.DAOInstantiationException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.errors.validation.DataValidationException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.model.ModelContext;
import com.jtouzy.dao.query.CUD;
import com.jtouzy.dao.query.QueryCollection;

public class ChampionshipDAO extends AbstractSingleIdentifierDAO<Championship> {
	public ChampionshipDAO()
	throws DAOException {
		super(Championship.class);
	}
	
	public Championship getChampionshipTeamsAndMatchs(Integer championshipId)
	throws QueryException {
		try {
			QueryCollection<Championship,ChampionshipTeam> teamsQuery = queryCollection(ChampionshipTeam.class);
			teamsQuery.context().addDirectJoin(ModelContext.getTableContext(Team.class), ChampionshipTeam.TABLE)
			                    .addEqualsCriterion(Championship.class, Championship.IDENTIFIER_FIELD, championshipId);
			teamsQuery.context().orderBy(ChampionshipTeam.POINTS_FIELD, false);
			Championship championship = teamsQuery.fillOne();
			if (championship == null)
				return null;
			championship.setMatchs(DAOManager.getDAO(this.connection, MatchDAO.class).getChampionshipMatchs(championshipId));
			return championship;
		} catch (ContextMissingException | DAOInstantiationException ex) {
			throw new QueryException(ex);
		}
	}
	
	public void calculateRankings(Integer championshipId)
	throws RankingsCalculateException, DataValidationException {
		try {
			Championship championship = queryForOne(championshipId);
			if (championship == null)
				throw new RankingsCalculateException("Le championnat " + championshipId + " n'existe pas");
			if (championship.getType() != Type.CHP)
				throw new RankingsCalculateException("Calcul des scores impossibles : L'enregistrement " + championshipId + " ne correspond pas à un championnat");				
			this.connection.setAutoCommit(false);
			initializeChampionshipTeams(championshipId);
			calculateAllMatchs(championshipId);
			this.connection.commit();
			this.connection.setAutoCommit(true);
		} catch (SQLException | QueryException ex) {
			
		} finally {
			try {
				if (this.connection.getAutoCommit()) {
					this.connection.rollback();
					this.connection.setAutoCommit(true);
				}
			} catch (SQLException ex) {
				throw new RankingsCalculateException(ex);
			}
			
		}
	}
	
	private void initializeChampionshipTeams(Integer championshipId)
	throws RankingsCalculateException {
		try {
			CUD<ChampionshipTeam> cud = CUD.build(this.connection, ChampionshipTeam.class);
			cud.context().addColumnValue(ChampionshipTeam.FORFEIT_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.LOOSE3BY2_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.LOOSE_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.PLAY_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.POINTS_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.POINTSAGAINST_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.POINTSFOR_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.SETSAGAINST_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.SETSFOR_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.WIN_FIELD, 0)
					     .addEqualsCriterion(ChampionshipTeam.CHAMPIONSHIP_FIELD, championshipId);
			cud.executeUpdate();
		} catch (ContextMissingException | QueryException ex) {
			throw new RankingsCalculateException(ex);
		}
	}
	
	private void calculateAllMatchs(Integer championshipId)
	throws RankingsCalculateException, DataValidationException {
		try {
			ChampionshipTeamDAO ctDao = DAOManager.getDAO(this.connection, ChampionshipTeamDAO.class);
			List<ChampionshipTeam> teams = ctDao.getChampionshipTeams(championshipId);
			List<Match> matchs = DAOManager.getDAO(this.connection, MatchDAO.class).getChampionshipValidateMatchs(championshipId);
			Iterator<Match> it = matchs.iterator();
			Match match;
			ChampionshipTeam team1, team2;
			while (it.hasNext()) {
				match = it.next();
				team1 = getChampionshipTeam(teams, match.getFirstTeam());
				team2 = getChampionshipTeam(teams, match.getSecondTeam());
				updateRankings(team1, team2, match);
			}
			Iterator<ChampionshipTeam> itc = teams.iterator();
			while (itc.hasNext()) {
				team1 = itc.next();
				ctDao.update(team1);
			}
		} catch (DAOInstantiationException | QueryException | DAOCrudException ex) {
			throw new RankingsCalculateException(ex);
		}
	}
	
	private ChampionshipTeam getChampionshipTeam(List<ChampionshipTeam> list, Team team) {
		Optional<ChampionshipTeam> opt = list.stream()
				                             .filter(ct -> ct.getTeam().getIdentifier() == team.getIdentifier())
				                             .findFirst();
		if (!opt.isPresent())
			return null;
		return opt.get();
	}
	
	public void updateRankings(Match match)
	throws RankingsCalculateException, DataValidationException {
		if (match.getState() != Match.State.V) {
			return;
		}
		try {
			ChampionshipTeamDAO ctDao = DAOManager.getDAO(this.connection, ChampionshipTeamDAO.class);
			// Recherche des 2 équipes du championnat en un seul select pour optimisation
			List<ChampionshipTeam> teams = 
					ctDao.getChampionshipTeams(match.getChampionship().getIdentifier(),
							Arrays.asList(match.getFirstTeam().getIdentifier(),
									      match.getSecondTeam().getIdentifier())); 
			// Ensuite, on retrouve celle de la première équipe et la seconde du match
			ChampionshipTeam firstTeam = getChampionshipTeam(teams, match.getFirstTeam());
			ChampionshipTeam secondTeam = getChampionshipTeam(teams, match.getSecondTeam());
			// Mise à jour des points
			updateRankings(firstTeam, secondTeam, match);
			// Mise à jour en base de données
			ctDao.update(firstTeam);
			ctDao.update(secondTeam);
		} catch (DAOInstantiationException | QueryException | DAOCrudException ex) {
			throw new RankingsCalculateException(ex);
		}
	}
	
	public void updateRankings(ChampionshipTeam firstTeam, ChampionshipTeam secondTeam, Match match) {
		if (match.getState() != Match.State.V) {
			return;
		}
		Integer sc1 = match.getSc1();
		Integer sc2 = match.getSc2();
		ChampionshipTeam winnerTeam = firstTeam;
		ChampionshipTeam looserTeam = secondTeam;
		Integer winnerScore = sc1;
		Integer looserScore = sc2;
		Integer winnerPoints = (match.getS11() + match.getS21() + match.getS31()) +
				               (match.getS41() == null ? 0 : match.getS41()) +
				               (match.getS51() == null ? 0 : match.getS51());
		Integer looserPoints = (match.getS12() + match.getS22() + match.getS32()) +
	               			   (match.getS42() == null ? 0 : match.getS42()) +
	               			   (match.getS52() == null ? 0 : match.getS52());
		if (sc2 > sc1) {
			winnerTeam = secondTeam;
			looserTeam = firstTeam;
			winnerScore = sc2;
			looserScore = sc1;
			int svg = looserPoints;
			looserPoints = winnerPoints;
			winnerPoints = svg;
		}
		winnerTeam.setPoints(winnerTeam.getPoints() + 3);
		winnerTeam.setPlay(winnerTeam.getPlay() + 1);
		winnerTeam.setSetsFor(winnerTeam.getSetsFor() + winnerScore);
		winnerTeam.setSetsAgainst(winnerTeam.getSetsAgainst() + looserScore);
		winnerTeam.setPointsFor(winnerTeam.getPointsFor() + winnerPoints);
		winnerTeam.setPointsAgainst(winnerTeam.getPointsAgainst() + looserPoints);
		looserTeam.setPlay(looserTeam.getPlay() + 1);
		looserTeam.setSetsFor(looserTeam.getSetsFor() + looserScore);
		looserTeam.setSetsAgainst(looserTeam.getSetsAgainst() + winnerScore);
		looserTeam.setPointsFor(looserTeam.getPointsFor() + looserPoints);
		looserTeam.setPointsAgainst(looserTeam.getPointsAgainst() + winnerPoints);
		winnerTeam.setWin(winnerTeam.getWin() + 1);
		if (match.isForfeit()) {
			looserTeam.setForfeit(looserTeam.getForfeit() + 1);
		} else {
			if (looserScore == 2) {
				looserTeam.setLoose3By2(looserTeam.getLoose3By2() + 1);
				looserTeam.setPoints(looserTeam.getPoints() + 2);
			} else {
				looserTeam.setLoose(looserTeam.getLoose() + 1);
				looserTeam.setPoints(looserTeam.getPoints() + 1);
			}
		}
	}
}
