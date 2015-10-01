package com.jtouzy.cv.model.dao;

import java.util.Arrays;
import java.util.List;

import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.cv.model.classes.Team;
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
	
	public List<SeasonTeamPlayer> getAllBySeasonAndTeam(Integer seasonId, Integer teamId)
	throws QueryException {
		return getAllBySeasonAndTeamIn(seasonId, Arrays.asList(teamId));
	}
	
	public List<SeasonTeamPlayer> getAllBySeasonAndTeamIn(Integer seasonId, List<Integer> teamIds)
	throws QueryException {
		try {
			Query<SeasonTeamPlayer> query = queryWithDetails();
			if (seasonId != null) {
				query.context().addDirectJoin(SeasonTeam.class)
				   			   .addEqualsCriterion(SeasonTeam.class, SeasonTeam.SEASON_FIELD, seasonId);
			}
			if (teamIds != null && teamIds.size() > 0) {
				if (seasonId == null) {
					query.context().addDirectJoin(SeasonTeam.class);
				}
				query.context().addInCriterion(SeasonTeam.class, SeasonTeam.TEAM_FIELD, teamIds);
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
