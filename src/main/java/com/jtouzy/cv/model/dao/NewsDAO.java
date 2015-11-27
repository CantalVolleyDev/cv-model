package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.News;
import com.jtouzy.cv.model.classes.User;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

public class NewsDAO extends AbstractSingleIdentifierDAO<News> {
	public NewsDAO() {
		super(News.class);
	}
	
	public List<News> getAllWithDetails(Integer limitTo, Integer page) 
	throws QueryException {
		return queryWithDetails(limitTo, page).many();
	}
	
	public List<News> getAllPublished(Integer limitTo, Integer page)
	throws QueryException {
		Query<News> query = queryWithDetails(limitTo, page);
		query.context().addEqualsCriterion(News.STATE_FIELD, News.State.V);
		return query.many();
	}
	
	private Query<News> queryWithDetails(Integer limitTo, Integer page) {
		Query<News> query = query();
		query.context().addDirectJoin(User.class);
		Integer offset = null;
		if (limitTo != null && page != null && page > 1) {
			offset = limitTo * (page-1);
		}
		query.context()
		     .limitTo(limitTo)
		     .offset(offset);
		return query;
	}
}
