package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.MatchPlayer;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class MatchPlayerDAO extends AbstractDAO<MatchPlayer> {
	public MatchPlayerDAO()
	throws DAOException {
		super(MatchPlayer.class);
	}
	
	public List<MatchPlayer> getPlayers(Integer matchId)
	throws QueryException {
		Query<MatchPlayer> query = query();
		if (matchId != null) {
			query.context().addEqualsCriterion(MatchPlayer.MATCH_FIELD, matchId);
		}
		return query.many();
	}
}
