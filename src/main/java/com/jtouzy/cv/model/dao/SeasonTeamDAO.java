package com.jtouzy.cv.model.dao;

import com.google.common.collect.ImmutableMap;
import com.jtouzy.cv.model.classes.Gym;
import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class SeasonTeamDAO extends AbstractDAO<SeasonTeam> {
	public SeasonTeamDAO()
	throws DAOException {
		super(SeasonTeam.class);
	}
	
	@Override
	public Query<SeasonTeam> query() 
	throws QueryException {
		try {
			Query<SeasonTeam> query = super.query();
			query.context().addDirectJoin(Gym.class)
			               .addDirectJoin(Team.class);
			return query;
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
	
	public SeasonTeam getSeasonTeam(Integer seasonId, Integer teamId)
	throws QueryException {
		return this.queryUnique(ImmutableMap.of(SeasonTeam.SEASON_FIELD, seasonId,
				                                SeasonTeam.TEAM_FIELD, teamId));
	}
}
