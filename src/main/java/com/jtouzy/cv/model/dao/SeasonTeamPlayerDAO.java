package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.dao.errors.DAOInstantiationException;
import com.jtouzy.dao.impl.AbstractDAO;

public class SeasonTeamPlayerDAO extends AbstractDAO<SeasonTeamPlayer> {
	public SeasonTeamPlayerDAO() 
	throws DAOInstantiationException {
		super(SeasonTeamPlayer.class);
	}
}
