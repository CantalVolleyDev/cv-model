package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Gym;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class GymDAO extends AbstractSingleIdentifierDAO<Gym> {
	public GymDAO() {
		super(Gym.class);
	}
}
