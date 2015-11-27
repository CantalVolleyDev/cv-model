package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.ChampionshipWeeks;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractDAO;
import com.jtouzy.dao.query.Query;

public class ChampionshipWeeksDAO extends AbstractDAO<ChampionshipWeeks> {
	public ChampionshipWeeksDAO() {
		super(ChampionshipWeeks.class);
	}
	
	public List<ChampionshipWeeks> getAllByChampionship(Integer championshipId) 
	throws QueryException {
		Query<ChampionshipWeeks> query = query();
		query.context()
	         .orderBy(ChampionshipWeeks.class, ChampionshipWeeks.CHAMPIONSHIP_FIELD)
	         .orderBy(ChampionshipWeeks.class, ChampionshipWeeks.WEEK_DATE_FIELD)
             .addEqualsCriterion(ChampionshipWeeks.CHAMPIONSHIP_FIELD, championshipId);
		return query.many();
	}
}
