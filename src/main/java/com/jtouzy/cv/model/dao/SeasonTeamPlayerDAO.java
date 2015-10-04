package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.cv.model.classes.User;
import com.jtouzy.dao.errors.DAOInstantiationException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class SeasonTeamPlayerDAO extends AbstractDAO<SeasonTeamPlayer> {
	public SeasonTeamPlayerDAO() 
	throws DAOInstantiationException {
		super(SeasonTeamPlayer.class);
	}
	
	public List<SeasonTeamPlayer> getAllBySeasonTeam(Integer seasonTeamId)
	throws QueryException {
		Query<SeasonTeamPlayer> query = queryWithDetails();
		query.context().addEqualsCriterion(SeasonTeamPlayer.SEASON_TEAM_FIELD, seasonTeamId);
		return query.many();
	}
	
	public List<SeasonTeamPlayer> getAllBySeasonTeamIn(List<Integer> seasonTeamIds)
	throws QueryException {
		Query<SeasonTeamPlayer> query = queryWithDetails();
		query.context().addInCriterion(SeasonTeamPlayer.SEASON_TEAM_FIELD, seasonTeamIds);
		return query.many();
	}
	
	public List<SeasonTeamPlayer> getAllBySeasonAndPlayer(Integer seasonId, Integer playerId)
	throws QueryException {
		try {
			Query<SeasonTeamPlayer> query = queryWithDetails();
			if (seasonId != null) {
				query.context().addDirectJoin(SeasonTeam.class)
							   .addEqualsCriterion(SeasonTeam.class, SeasonTeam.SEASON_FIELD, seasonId);
			}
			if (playerId != null) {
				query.context().addEqualsCriterion(SeasonTeamPlayer.PLAYER_FIELD, playerId);
			}
			return query.many();
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
	
	private Query<SeasonTeamPlayer> queryWithDetails() 
	throws QueryException {
		try {
			Query<SeasonTeamPlayer> query = super.query();
			query.context().addDirectJoin(User.class)
			               .addDirectJoin(SeasonTeam.class);
			return query;
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
}
