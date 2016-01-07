package com.jtouzy.cv.model.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.Competition;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.QueryCollection;

public class CompetitionDAO extends AbstractSingleIdentifierDAO<Competition> {
	public CompetitionDAO() {
		super(Competition.class);
	}
	
	public List<Competition> getAllWithChampionshipsBySeason(Integer seasonId)
	throws QueryException {
		QueryCollection<Competition,Championship> queryCollection = queryCollection(Championship.class);
		if (seasonId != null) {
			queryCollection.context().addEqualsCriterion(Competition.class, Competition.SEASON_FIELD, seasonId);
		}
		queryCollection.context().addEqualsCriterion(Competition.class, Competition.STATE_FIELD, Competition.State.V);
		queryCollection.context().addEqualsCriterion(Championship.class, Championship.STATE_FIELD, Championship.State.V);
		
		List<Competition> result = queryCollection.fill();
		// L'order by ne fonctionne pas sur QueryCollection : tri manuel
		Collections.sort(result, new Comparator<Competition>() {
			@Override
			public int compare(Competition o1, Competition o2) {
				int result = o1.getOrder() - o2.getOrder();
				if (result != 0)
					return result;
				return o1.getLabel().compareTo(o2.getLabel());
			}
		});
		
		return result;
	}
}
