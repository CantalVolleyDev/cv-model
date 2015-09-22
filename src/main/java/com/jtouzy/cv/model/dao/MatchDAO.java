package com.jtouzy.cv.model.dao;

import java.util.List;
import java.util.stream.Collectors;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.Competition;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.cv.model.errors.MatchNotFoundException;
import com.jtouzy.cv.model.validators.MatchValidator;
import com.jtouzy.dao.builders.model.OrContext;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ColumnContextNotFoundException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.model.ModelContext;
import com.jtouzy.dao.query.Query;

public class MatchDAO extends AbstractSingleIdentifierDAO<Match> {
	/**
	 * Constructeur
	 * @throws DAOException Si la définition du DAO est incorrecte
	 */
	public MatchDAO()
	throws DAOException {
		super(Match.class, new MatchValidator());
	}
	
	/**
	 * {@inheritDoc}
	 * Redéfinition de la méthode pour toujours avoir une jointure 
	 * avec les équipes à chaque fois qu'on utilise cette fonction
	 */
	@Override
	public Query<Match> query() 
	throws QueryException {
		try {
			Query<Match> query = super.query();
			query.context()
		         .addDirectJoin(Team.class, "eq1", Match.FIRST_TEAM_FIELD)
		         .addDirectJoin(Team.class, "eq2", Match.SECOND_TEAM_FIELD);
			return query;
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
	
	/**
	 * Recherche des informations d'un seul match
	 * @param id Identifiant unique du match
	 * @return Match correspondant à l'index
	 * @throws MatchNotFoundException Si le match n'as pas été trouvé
	 * @throws QueryException Si problème dans l'exécution de la requête
	 */
	public Match queryOne(Integer id)
	throws MatchNotFoundException, QueryException {
		return controlNotFound(id, super.queryForOne(id));
	}
	
	/**
	 * Recherche des informations d'un seul match avec détails
	 * (Championnat + Compétition)
	 * @param id Identifiant unique du match
	 * @return Match correspondant à l'index
	 * @throws MatchNotFoundException Si le match n'as pas été trouvé
	 * @throws QueryException Si problème dans l'exécution de la requête
	 */
	public Match queryOneWithDetails(Integer id) 
	throws MatchNotFoundException, QueryException {
		try {
			Query<Match> query = query();
			query.context()
			     .addDirectJoin(Championship.class)
			     .addDirectJoin(ModelContext.getTableContext(Competition.class), Championship.TABLE)
			     .addEqualsCriterion(Match.IDENTIFIER_FIELD, id);
			return (controlNotFound(id, query.one()));
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
	
	/**
	 * Contrôle commun pour tester si match recherché non null
	 * @param id Identifiant unique du match
	 * @param match Match recherché par une requête
	 * @return Le match recherché par une requête
	 * @throws MatchNotFoundException Si le match passé en paramètre est null
	 */
	private Match controlNotFound(Integer id, Match match)
	throws MatchNotFoundException {
		if (match == null) {
			throw new MatchNotFoundException(id);
		}
		return match;
	}
	
	/**
	 * Récupération d'une liste de matchs en fonction d'un numéro de saison
	 * ou un utilisateur en particulier (ou combinaison)
	 * @param seasonId Numéro de saison, si NULL, pas de critère
	 * @param userId Numéro d'utilisateur, si NULL, pas de critère
	 * @return Liste de matchs correspondants aux critères
	 * @throws QueryException Si problème dans l'exécution de la requête
	 */
	public List<Match> getMatchs(Integer seasonId, Integer userId)
	throws QueryException {
		try {
			Query<Match> query = query();
			if (seasonId != null) {
				query.context()
				     .addDirectJoin(Championship.class)
				     .addDirectJoin(ModelContext.getTableContext(Competition.class), Championship.TABLE)
				     .addEqualsCriterion(Competition.class, Competition.SEASON_FIELD, seasonId);
			}
			if (userId != null) {
				Query<SeasonTeamPlayer> queryTeam = Query.build(this.connection, SeasonTeamPlayer.class);
				queryTeam.context().addEqualsCriterion(SeasonTeamPlayer.PLAYER_FIELD, userId);
				if (seasonId != null) {
					queryTeam.context().addEqualsCriterion(SeasonTeamPlayer.SEASON_FIELD, seasonId);
				}
				List<SeasonTeamPlayer> teamPlayers = queryTeam.many();
				List<Integer> teamIds = teamPlayers.stream()
						                           .map(sp -> sp.getTeam().getIdentifier())
						                           .collect(Collectors.toList());
				OrContext orContext = new OrContext(query.context());
				orContext.addInCriterion(Match.FIRST_TEAM_FIELD, teamIds);
				orContext.addInCriterion(Match.SECOND_TEAM_FIELD, teamIds);
				query.context().addOrCriterion(orContext);
			}
			query.context().orderBy(Match.DATE_FIELD, false);
			return query.many();
		} catch (TableContextNotFoundException | ColumnContextNotFoundException ex) {
			throw new QueryException(ex);
		}
	}
}
