package com.jtouzy.cv.model.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.ChampionshipTeam;
import com.jtouzy.cv.model.classes.ChampionshipWeeks;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.cv.model.errors.CalendarGenerationException;
import com.jtouzy.dao.DAOManager;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.DAOInstantiationException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.QueryCollection;

public class MatchDAO extends AbstractSingleIdentifierDAO<Match> {
	public MatchDAO(Class<Match> daoClass)
	throws DAOException {
		super(daoClass);
	}
	
	private boolean isExemptMatch(ChampionshipTeam team1, ChampionshipTeam team2) {
		return team1.getTeam().getLabel().equals("Exempt") ||
			   team2.getTeam().getLabel().equals("Exempt");
	}
	
	public List<Match> buildCalendar(Integer championshipId, boolean returnMatchs)
	throws CalendarGenerationException {
		try {
			// -> Recherche des équipes pour le championnat
			ChampionshipDAO championshipDao = DAOManager.getDAO(this.connection, ChampionshipDAO.class);			
			QueryCollection<Championship, ChampionshipTeam> chpQuery = championshipDao.queryCollection(ChampionshipTeam.class); 
			chpQuery.context().addEqualsCriterion(Championship.IDENTIFIER_FIELD, championshipId);
			Championship championship = chpQuery.fillOne();
			List<ChampionshipTeam> teams = championship.getTeams();
			// -> Premier contrôle pour le nombre d'équipes
			int teamCount = teams.size();
			if (teamCount == 0) {
				throw new CalendarGenerationException("Aucune équipe dans le championnat");
			}
			// -> Ajout de l'équipe exempt si nécessaire
			if (teamCount%2 != 0) {
				Championship chp = new Championship();
				chp.setIdentifier(championshipId);
				Team exempt = new Team();
				exempt.setLabel("Exempt");
				ChampionshipTeam ctExempt = new ChampionshipTeam();
				ctExempt.setChampionship(chp);
				ctExempt.setTeam(exempt);
				ctExempt.setBonus(0);
				ctExempt.setForfeit(0);
				ctExempt.setLoose(0);
				ctExempt.setLoose3By2(0);
				ctExempt.setPlay(0);
				ctExempt.setPoints(0);
				ctExempt.setPointsAgainst(0);
				ctExempt.setPointsFor(0);
				ctExempt.setSetsAgainst(0);
				ctExempt.setSetsFor(0);
				ctExempt.setWin(0);
				teams.add(ctExempt);
			}
			// -> Nouveau décompte
			teamCount = teams.size();
			int dayCount = teamCount-1;
			// -> Recherche des semaines
			QueryCollection<Championship, ChampionshipWeeks> weeksQuery = championshipDao.queryCollection(ChampionshipWeeks.class); 
			weeksQuery.context().addEqualsCriterion(Championship.IDENTIFIER_FIELD, championshipId);
			championship = chpQuery.fillOne();
			// -> Contrôles sur le nombre de semaines
			List<ChampionshipWeeks> weeks = championship.getWeeks(); 
			int weeksCount = weeks.size();
			if (weeksCount == 0) {
				throw new CalendarGenerationException("Aucune semaine définie pour la génération");
			}
			int realDayCount = returnMatchs ? dayCount*2 : dayCount;
			if (weeksCount != realDayCount) {
				throw new CalendarGenerationException("Le nombre de semaines enregistré n'est pas cohérent : Pour " + teamCount + " équipes (EXEMPT compris), il faut " + realDayCount + " journées.");
			}
			// -> Initialisations
			int matchCount = teamCount/2;
			List<Match> matchs = new ArrayList<>();
			int firstTeamIndex, secondTeamIndex;
			ChampionshipTeam team1, team2;
			Match match;
			// -> Génération des matchs
			//  -> Boucle sur les journées
			int j;
			for (j=1; j<=dayCount; j++) {
				firstTeamIndex = 1;
				secondTeamIndex = teamCount-j+1;
				// -> Boucle sur les matchs pour cette journée
				for (int m=1; m<=matchCount; m++) {
					team1 = j%2 == 0 ? teams.get(firstTeamIndex-1) : teams.get(secondTeamIndex-1);
					team2 = j%2 == 0 ? teams.get(secondTeamIndex-1) : teams.get(firstTeamIndex-1);
					if (!isExemptMatch(team1, team2)) {
						match = new Match();
						match.setFirstTeam(team1.getTeam());
						match.setSecondTeam(team2.getTeam());
						match.setStep(j);
						// Positionnement de la date temporaire (premier jour de la semaine)
						match.setDate(weeks.get(j-1).getWeekDate());
						matchs.add(match);
					}
					if (j == 1) {
						firstTeamIndex++;
						secondTeamIndex--;
					} else {
						if (firstTeamIndex == 1 || firstTeamIndex == teamCount)
							firstTeamIndex = firstTeamIndex == 1 ? (teamCount - j + 2) : 2;
						else if (firstTeamIndex != teamCount)
							firstTeamIndex++;
						
						secondTeamIndex--;
						if (secondTeamIndex <= (matchCount - j) || secondTeamIndex == 1)
							secondTeamIndex = teamCount;
					}
					
				}
			}
			// Génération des matchs retour
			Iterator<Match> it;
			if (returnMatchs) {
				final List<Match> newMatchs = new ArrayList<>();
				it = matchs.iterator();
				Match match2;
				while (it.hasNext()) {
					match = it.next();
					j = match.getStep() + dayCount;
					match2 = new Match();
					match2.setFirstTeam(match.getSecondTeam());
					match2.setSecondTeam(match.getFirstTeam());
					match2.setStep(j);
					match2.setDate(weeks.get(j-1).getWeekDate());
					newMatchs.add(match2);
				}
				matchs.addAll(newMatchs);
			}
			// Calcul des dates réelles
			it = matchs.iterator();
			while (it.hasNext()) {
				match = it.next();
				// TODO
			}
			return matchs;
		} catch (DAOInstantiationException | QueryException ex) {
			throw new CalendarGenerationException(ex);
		}
	}
}
