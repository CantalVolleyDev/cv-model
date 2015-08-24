package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Competition;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class CompetitionDAO extends AbstractSingleIdentifierDAO<Competition> {
	public CompetitionDAO(Class<Competition> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
