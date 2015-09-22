package com.jtouzy.cv.model.dao;

import java.util.Arrays;
import java.util.List;

import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.dao.errors.DAOInstantiationException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class SeasonTeamPlayerDAO extends AbstractDAO<SeasonTeamPlayer> {
	public SeasonTeamPlayerDAO() 
	throws DAOInstantiationException {
		super(SeasonTeamPlayer.class);
	}
	
	public List<SeasonTeamPlayer> getPlayers(Integer seasonId, Integer teamId)
	throws QueryException {
		return getPlayers(seasonId, Arrays.asList(teamId));
	}
	
	public List<SeasonTeamPlayer> getPlayers(Integer seasonId, List<Integer> teamIds)
	throws QueryException {
		Query<SeasonTeamPlayer> query = query();
		if (seasonId != null) {
			query.context().addEqualsCriterion(SeasonTeamPlayer.SEASON_FIELD, seasonId);
		}
		if (teamIds != null && teamIds.size() > 0) {
			query.context().addInCriterion(SeasonTeamPlayer.TEAM_FIELD, teamIds);
		}
		return query.many();
	}
}
