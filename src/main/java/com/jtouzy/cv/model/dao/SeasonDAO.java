package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Season;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.StatementBuildException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

/**
 * Implémentation d'un DAO pour le modèle "Season"
 * @author jtouzy
 */
public class SeasonDAO extends AbstractSingleIdentifierDAO<Season> {
	/**
	 * Constructeur du DAO 
	 * @param daoClass Classe modèle gérée par le DAO (Season)
	 * @throws DAOException Si la validation technique du DAO est incorrecte
	 */
	public SeasonDAO(Class<Season> daoClass)
	throws DAOException {
		super(daoClass);
	}
	
	public Season getCurrentSeason()
	throws QueryException {
		try {
			Query<Season> query = Query.build(this.connection, this.daoClass)
			                           .equalsClause(Season.CURRENT_FIELD, true);
			query.printSql();
			return query.one();
		} catch (TableContextNotFoundException | StatementBuildException ex) {
			throw new QueryException(ex);
		}
	}
}
