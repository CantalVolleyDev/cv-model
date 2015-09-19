package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.MatchPlayer;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractDAO;

public class MatchPlayerDAO extends AbstractDAO<MatchPlayer> {
	public MatchPlayerDAO()
	throws DAOException {
		super(MatchPlayer.class);
	}
}
