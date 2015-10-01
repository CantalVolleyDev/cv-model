package com.jtouzy.cv.model.dao;

import java.util.Arrays;
import java.util.List;

import com.jtouzy.cv.model.classes.ChampionshipTeam;
import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class ChampionshipTeamDAO extends AbstractDAO<ChampionshipTeam> {
	public ChampionshipTeamDAO()
	throws DAOException {
		super(ChampionshipTeam.class);
	}
	
	/**
	 * Récupération d'un seul enregistrement pour équipe d'un championnat
	 * @param championshipId ID unique du championnat
	 * @param teamId ID unique de l'équipe
	 * @return Objet de représentation d'une équipe de championnat
	 * @throws QueryException
	 */
	public ChampionshipTeam getOne(Integer championshipId, Integer teamId)
	throws QueryException {
		List<ChampionshipTeam> onlyOneTeam = getAllByChampionshipIn(championshipId, Arrays.asList(teamId));
		if (onlyOneTeam.size() == 0)
			return null;
		return onlyOneTeam.get(0);
	}
	
	/**
	 * Récupération de toutes les équipes d'un championnat
	 * @param championshipId ID unique du championnat
	 * @return Liste d'objets de représentation des équipes du championnat
	 * @throws QueryException
	 */
	public List<ChampionshipTeam> getAllByChampionship(Integer championshipId)
	throws QueryException {
		return this.getAllByChampionshipIn(championshipId, (List<Integer>)null);
	}
	
	/**
	 * Récupération d'une liste d'équipes provenant d'un championnat
	 * @param championshipId ID unique du championnat
	 * @param teamIds Liste d'ID uniques d'équipes
	 * @return Liste d'objets de représentation des équipes du championnat correspondant aux IDs
	 * @throws QueryException
	 */
	public List<ChampionshipTeam> getAllByChampionshipIn(Integer championshipId, List<Integer> teamIds)
	throws QueryException {
		try {
			Query<ChampionshipTeam> query = query();
			if (championshipId != null) {
				query.context().addEqualsCriterion(ChampionshipTeam.CHAMPIONSHIP_FIELD, championshipId);
			} 
			if (teamIds != null) {
				query.context().addDirectJoin(SeasonTeam.class)
							   .addInCriterion(SeasonTeam.class, SeasonTeam.TEAM_FIELD, teamIds);
			}
			return query.many();
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
}
