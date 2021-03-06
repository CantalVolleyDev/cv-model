package com.jtouzy.cv.model.dao;

import java.util.List;
import java.util.stream.Collectors;

import com.jtouzy.cv.model.classes.Comment;
import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.cv.model.classes.SeasonTeamPlayer;
import com.jtouzy.cv.model.classes.User;
import com.jtouzy.dao.errors.DAOCrudException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.CUD;
import com.jtouzy.dao.query.Query;

public class CommentDAO extends AbstractSingleIdentifierDAO<Comment> {
	public CommentDAO() {
		super(Comment.class);
	}
	
	/**
	 * Récupération de tous les commentaires pour un match donné
	 * @param matchId ID unique du match
	 * @return Liste d'objets représentant la liste des commentaires d'un match
	 * @throws QueryException
	 */
	public List<Comment> getAllByMatch(Integer matchId)
	throws QueryException {
		Query<Comment> query = queryEntityComment(matchId, Comment.Entity.MAT);
		query.context().addDirectJoin(User.class);
		query.context().orderBy(Comment.DATE_FIELD, true);
		return query.many();
	}
	
	/**
	 * Récupération de tous les commentaires pour une équipe donnée
	 * @param teamId ID unique de l'équipe
	 * @return Liste d'objets représentant la liste des commentaires d'une équipe
	 * @throws QueryException
	 */
	public List<Comment> getAllByTeam(Integer teamId)
	throws QueryException {
		Query<Comment> query = queryEntityComment(teamId, Comment.Entity.EQI);
		query.context().addDirectJoin(User.class);
		query.context().orderBy(Comment.DATE_FIELD, true);
		return query.many();
	}
	
	public List<Comment> getAllLastMatchCommentsByTeam(
			List<Match> lastMatchs, List<SeasonTeamPlayer> playersToExclude, Integer limit)
	throws QueryException {
		Query<Comment> query = query();
		query.context().addDirectJoin(User.class);
		query.context().limitTo(limit);
		query.context().orderBy(Comment.DATE_FIELD, false);
		query.context().addEqualsCriterion(Comment.ENTITY_FIELD, Comment.Entity.MAT);
		query.context().addNotInCriterion(Comment.USER_FIELD, 
				playersToExclude.stream()
								.map(p -> p.getPlayer().getIdentifier())
								.collect(Collectors.toList()));
		if (!lastMatchs.isEmpty()){
			query.context().addInCriterion(Comment.ENTITY_VALUE_FIELD, 
					lastMatchs.stream()
					          .map(m -> m.getIdentifier())
					          .collect(Collectors.toList()));
		}
		return query.many();
	}
	
	/**
	 * Récupération d'un commentaire officiel d'équipe (un seul par équipe)
	 * @param matchId ID unique du match
	 * @param teamId ID unique de l'équipe
	 * @return Objet représentant le commentaire officiel d'une équipe
	 * @throws QueryException
	 */
	public Comment getOneByMatchTeam(Integer matchId, Integer teamId)
	throws QueryException {
		Query<Comment> query = queryEntityComment(matchId, Comment.Entity.MAT);
		query.context().addEqualsCriterion(Comment.TEAM_FIELD, teamId);
		return query.one();
	}
	
	private Query<Comment> queryEntityComment(Integer entityId, Comment.Entity entity) {
		Query<Comment> query = query();
		query.context().addEqualsCriterion(Comment.ENTITY_FIELD, entity);
		query.context().addEqualsCriterion(Comment.ENTITY_VALUE_FIELD, entityId);
		return query;
	}
	
	public void deleteMatchComments(Integer matchId, Integer teamId)
	throws DAOCrudException {
		try {
			CUD<Comment> cud = CUD.build(this.connection, this.daoClass);
			cud.context().addEqualsCriterion(Comment.ENTITY_FIELD, Comment.Entity.MAT);
			cud.context().addEqualsCriterion(Comment.ENTITY_VALUE_FIELD, matchId);
			if (teamId != null) {
				cud.context().addEqualsCriterion(Comment.TEAM_FIELD, teamId);
			}
			cud.executeDelete();
			//FIXME: QueryException??
		} catch (QueryException ex) {
			throw new DAOCrudException(Comment.class, ex);
		}
	}
}
