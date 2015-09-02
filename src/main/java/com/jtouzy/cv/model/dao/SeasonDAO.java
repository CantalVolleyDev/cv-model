package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Season;
import com.jtouzy.dao.errors.DAOCrudException;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.errors.model.TableContextNotFoundException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

/**
 * Implémentation d'un DAO pour le modèle "Season"
 * @author jtouzy
 */
public class SeasonDAO extends AbstractSingleIdentifierDAO<Season> {
	/**
	 * Saison courante en cache<br>
	 * Cette saison courante est modifiée uniquement lorsqu'on change, donc une fois par an pendant l'été<br>
	 * Lors de l'utilisation de la méthode {@link #getCurrentSeason()}, elle sera recherchée uniquement la première fois<br>
	 */
	private Season currentSeasonCached;
	
	/**
	 * Constructeur du DAO 
	 * @param daoClass Classe modèle gérée par le DAO (Season)
	 * @throws DAOException Si la validation technique du DAO est incorrecte
	 */
	public SeasonDAO(Class<Season> daoClass)
	throws DAOException {
		super(daoClass);
	}
	
	/**
	 * Modification de la saison courante en base
	 * @param newCurrent Instance de la nouvelle saison courante
	 * @throws QueryException si problème lors de l'exécution de la requête pour la saison courante
	 * @throws DAOCrudException si problème lors de la mise à jour en base de données
	 */
	public void updateCurrentSeason(Season newCurrent)
	throws QueryException, DAOCrudException {
		Season currentSeason = getCurrentSeason();
		if (currentSeason != null) {
			// TODO penser à contrôler si les championnats sont terminés, ou confirmation pour clotûrer
			currentSeason.setCurrent(false);
			update(currentSeason);
		}
		newCurrent.setCurrent(true);
		createOrUpdate(newCurrent);
		currentSeasonCached = newCurrent;
	}
	
	/**
	 * Recherche de l'instance de la saison courante
	 * @return Objet instance représentant la saison courante
	 * @throws QueryException si problème lors de l'exécution de la requête pour la saison courante
	 */
	public Season getCurrentSeason()
	throws QueryException {
		try {
			if (currentSeasonCached == null) {
				Query<Season> query = Query.build(this.connection, this.daoClass)
                                           .equalsClause(Season.CURRENT_FIELD, true);
				currentSeasonCached = query.one();
			}
			return currentSeasonCached;
		} catch (TableContextNotFoundException ex) {
			throw new QueryException(ex);
		}
	}
}
