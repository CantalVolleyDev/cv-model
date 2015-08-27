package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Team;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class TeamDAO extends AbstractSingleIdentifierDAO<Team> {
	public TeamDAO(Class<Team> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
