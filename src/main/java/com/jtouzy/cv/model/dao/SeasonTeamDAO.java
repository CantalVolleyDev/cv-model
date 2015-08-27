package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.SeasonTeam;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractDAO;

public class SeasonTeamDAO extends AbstractDAO<SeasonTeam> {
	public SeasonTeamDAO(Class<SeasonTeam> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
