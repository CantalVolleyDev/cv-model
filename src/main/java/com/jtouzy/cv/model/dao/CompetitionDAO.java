package com.jtouzy.cv.model.dao;

import java.util.List;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.Competition;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ContextMissingException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.QueryCollection;

public class CompetitionDAO extends AbstractSingleIdentifierDAO<Competition> {
	public CompetitionDAO()
	throws DAOException {
		super(Competition.class);
	}
	
	public List<Competition> getAllWithChampionshipsBySeason(Integer seasonId)
	throws QueryException {
		try {
			QueryCollection<Competition,Championship> queryCollection = queryCollection(Championship.class);
			if (seasonId != null) {
				queryCollection.context().addEqualsCriterion(Competition.class, Competition.SEASON_FIELD, seasonId);
			}
			return queryCollection.fill();
		} catch (ContextMissingException ex) {
			throw new QueryException(ex);
		}
	}
}
