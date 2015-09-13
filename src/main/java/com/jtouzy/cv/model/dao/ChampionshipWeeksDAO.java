package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.ChampionshipWeeks;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractDAO;

public class ChampionshipWeeksDAO extends AbstractDAO<ChampionshipWeeks> {
	public ChampionshipWeeksDAO(Class<ChampionshipWeeks> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
