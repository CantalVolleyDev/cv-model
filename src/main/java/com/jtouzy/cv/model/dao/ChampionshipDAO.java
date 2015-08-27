package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class ChampionshipDAO extends AbstractSingleIdentifierDAO<Championship> {
	public ChampionshipDAO(Class<Championship> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
