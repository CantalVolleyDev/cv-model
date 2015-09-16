package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Championship;
import com.jtouzy.cv.model.classes.ChampionshipTeam;
import com.jtouzy.cv.model.errors.RankingsCalculateException;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.ColumnContextNotFoundException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.CUD;

public class ChampionshipDAO extends AbstractSingleIdentifierDAO<Championship> {
	public ChampionshipDAO(Class<Championship> daoClass)
	throws DAOException {
		super(daoClass);
	}
	
	public void calculateRankings(Integer championshipId)
	throws RankingsCalculateException {
		// TODO auto-commit false
		initializeChampionshipTeams(championshipId);
	}
	
	public void initializeChampionshipTeams(Integer championshipId)
	throws RankingsCalculateException {
		try {
			CUD<ChampionshipTeam> cud = CUD.build(this.connection, ChampionshipTeam.class);
			cud.context().addColumnValue(ChampionshipTeam.FORFEIT_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.LOOSE3BY2_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.LOOSE_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.PLAY_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.POINTS_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.POINTSAGAINST_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.POINTSFOR_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.SETSAGAINST_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.SETSFOR_FIELD, 0)
			             .addColumnValue(ChampionshipTeam.WIN_FIELD, 0)
					     .addEqualsCriterion(ChampionshipTeam.CHAMPIONSHIP_FIELD, championshipId);
			cud.executeUpdate();
		} catch (TableContextNotFoundException | QueryException | ColumnContextNotFoundException ex) {
			throw new RankingsCalculateException(ex);
		}
	}
}
