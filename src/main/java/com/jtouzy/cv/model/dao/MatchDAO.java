package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.validators.MatchValidator;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class MatchDAO extends AbstractSingleIdentifierDAO<Match> {
	public MatchDAO(Class<Match> daoClass)
	throws DAOException {
		super(daoClass, new MatchValidator());
	}
}
