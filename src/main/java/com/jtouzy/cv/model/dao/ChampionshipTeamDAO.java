package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.ChampionshipTeam;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractDAO;

public class ChampionshipTeamDAO extends AbstractDAO<ChampionshipTeam> {
	public ChampionshipTeamDAO(Class<ChampionshipTeam> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
