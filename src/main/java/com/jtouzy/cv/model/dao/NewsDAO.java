package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.News;
import com.jtouzy.cv.model.classes.User;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

public class NewsDAO extends AbstractSingleIdentifierDAO<News> {
	public NewsDAO(Class<News> daoClass)
	throws DAOException {
		super(daoClass);
	}
	
	@Override
	public Query<News> query() throws QueryException {
		try {
			Query<News> query = super.query();
			query.context().addDirectJoin(User.class);
			return query;
		} catch (TableContextNotFoundException ex) {
			throw new QueryException(ex);
		}
	}
}
