package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.User;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class UserDAO extends AbstractSingleIdentifierDAO<User> {
	public UserDAO(Class<User> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
