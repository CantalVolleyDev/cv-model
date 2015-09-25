package com.jtouzy.cv.model.dao;

import java.util.Arrays;
import java.util.List;

import com.jtouzy.cv.model.classes.ChampionshipTeam;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class ChampionshipTeamDAO extends AbstractDAO<ChampionshipTeam> {
	public ChampionshipTeamDAO()
	throws DAOException {
		super(ChampionshipTeam.class);
	}
	
	public ChampionshipTeam getOneChampionshipTeam(Integer championshipId, Integer teamId)
	throws QueryException {
		List<ChampionshipTeam> onlyOneTeam = getChampionshipTeams(championshipId, teamId);
		if (onlyOneTeam.size() == 0)
			return null;
		return onlyOneTeam.get(0);
	}
	
	public List<ChampionshipTeam> getChampionshipTeams(Integer championshipId)
	throws QueryException {
		return this.getChampionshipTeams(championshipId, (List<Integer>)null);
	}
	
	public List<ChampionshipTeam> getChampionshipTeams(Integer championshipId, Integer teamId)
	throws QueryException {
		return this.getChampionshipTeams(championshipId, Arrays.asList(teamId));
	}
	
	public List<ChampionshipTeam> getChampionshipTeams(Integer championshipId, List<Integer> teamIds)
	throws QueryException {
		Query<ChampionshipTeam> query = query();
		if (championshipId != null) {
			query.context().addEqualsCriterion(ChampionshipTeam.CHAMPIONSHIP_FIELD, championshipId);
		} 
		if (teamIds != null) {
			query.context().addInCriterion(ChampionshipTeam.TEAM_FIELD, teamIds);
		}
		return query.many();
	}
}
