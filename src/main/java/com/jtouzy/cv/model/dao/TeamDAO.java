package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

public class TeamDAO extends AbstractSingleIdentifierDAO<Team> {
	public TeamDAO()
	throws DAOException {
		super(Team.class);
	}
	
	public List<Team> getTeamsByName(String name)
	throws QueryException {
		Query<Team> query = query();
		query.context().addEqualsCriterion("lower(" + Team.LABEL_FIELD + ")", name);
		return query.many();
	}
}
