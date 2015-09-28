package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.ChampionshipWeeks;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class ChampionshipWeeksDAO extends AbstractDAO<ChampionshipWeeks> {
	public ChampionshipWeeksDAO()
	throws DAOException {
		super(ChampionshipWeeks.class);
	}
	
	public List<ChampionshipWeeks> getAllByChampionship(Integer championshipId) 
	throws QueryException {
		try {
			Query<ChampionshipWeeks> query = query();
			query.context()
		         .orderBy(Championship.class, Championship.IDENTIFIER_FIELD)
		         .orderBy(ChampionshipWeeks.class, ChampionshipWeeks.WEEK_DATE_FIELD)
	             .addEqualsCriterion(Championship.class, Championship.IDENTIFIER_FIELD, championshipId);
			return query.many();
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
}
