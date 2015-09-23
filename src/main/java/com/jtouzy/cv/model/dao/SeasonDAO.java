package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.cache.ApplicationCache;
import com.jtouzy.cv.model.classes.Season;
import com.jtouzy.dao.errors.DAOCrudException;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.errors.validation.DataValidationException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

/**
 * Implémentation d'un DAO pour le modèle "Season"
 * @author jtouzy
 */
public class SeasonDAO extends AbstractSingleIdentifierDAO<Season> {
	/**
	 * Constructeur du DAO 
	 * @throws DAOException Si la validation technique du DAO est incorrecte
	 */
	public SeasonDAO()
	throws DAOException {
		super(Season.class);
	}
	
	/**
	 * Modification de la saison courante en base
	 * @param newCurrent Instance de la nouvelle saison courante
	 * @throws QueryException si problème lors de l'exécution de la requête pour la saison courante
	 * @throws DAOCrudException si problème lors de la mise à jour en base de données
	 */
	public void updateCurrentSeason(Season newCurrent)
	throws QueryException, DAOCrudException, DataValidationException {
		Season currentSeason = getCurrentSeason();
		if (currentSeason != null) {
			// TODO penser à contrôler si les championnats sont terminés, ou confirmation pour clotûrer
			currentSeason.setCurrent(false);
			update(currentSeason);
		}
		newCurrent.setCurrent(true);
		createOrUpdate(newCurrent);
		ApplicationCache.setCurrentSeason(newCurrent);
	}
	
	/**
	 * Recherche de l'instance de la saison courante
	 * @return Objet instance représentant la saison courante
	 * @throws QueryException si problème lors de l'exécution de la requête pour la saison courante
	 */
	public Season getCurrentSeason()
	throws QueryException {
		try {
			Season cached = ApplicationCache.getCurrentSeason();
			if (cached == null) {
				Query<Season> query = Query.build(this.connection, this.daoClass);
				query.context().addEqualsCriterion(Season.CURRENT_FIELD, true);
				cached = query.one();
				ApplicationCache.setCurrentSeason(cached);
			}
			return cached;
		} catch (TableContextNotFoundException ex) {
			throw new QueryException(ex);
		}
	}
}
