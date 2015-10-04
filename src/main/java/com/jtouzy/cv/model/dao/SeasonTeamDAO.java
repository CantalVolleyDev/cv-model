package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.Gym;
import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

public class SeasonTeamDAO extends AbstractSingleIdentifierDAO<SeasonTeam> {
	public SeasonTeamDAO()
	throws DAOException {
		super(SeasonTeam.class);
	}
	
	private Query<SeasonTeam> queryWithDetails() 
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
	
	public List<SeasonTeam> getAllBySeason(Integer seasonId)
	throws QueryException {
		Query<SeasonTeam> query = query();
		query.context().addEqualsCriterion(SeasonTeam.SEASON_FIELD, seasonId);
		return query.many();
	}
	
	public SeasonTeam getOneWithDetails(Integer seasonTeamId)
	throws QueryException {
		Query<SeasonTeam> query = queryWithDetails();
		query.context().addEqualsCriterion(SeasonTeam.IDENTIFIER_FIELD, seasonTeamId);
		return query.one();
	}
	
	public SeasonTeam getOneWithDetails(Integer seasonId, Integer teamId)
	throws QueryException {
		Query<SeasonTeam> query = queryWithDetails();
		query.context().addEqualsCriterion(SeasonTeam.SEASON_FIELD, seasonId);
		query.context().addEqualsCriterion(SeasonTeam.TEAM_FIELD, teamId);
		return query.one();
	}
}
