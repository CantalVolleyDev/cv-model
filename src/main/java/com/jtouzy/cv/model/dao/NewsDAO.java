package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.News;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class NewsDAO extends AbstractSingleIdentifierDAO<News> {
	public NewsDAO(Class<News> daoClass)
	throws DAOException {
		super(daoClass);
	}
}
