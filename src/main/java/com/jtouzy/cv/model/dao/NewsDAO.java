package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.News;
import com.jtouzy.cv.model.classes.User;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class NewsDAO extends AbstractSingleIdentifierDAO<News> {
	public NewsDAO(Class<News> daoClass)
	throws DAOException {
		super(daoClass);
	}
	
	@Override
	public List<News> queryAll() throws QueryException {
		try {
			return query().directJoin(User.class)
					      .many();
		} catch (TableContextNotFoundException ex) {
			throw new QueryException(ex);
		}
	}
}
