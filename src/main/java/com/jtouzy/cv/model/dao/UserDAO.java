package com.jtouzy.cv.model.dao;

import java.util.List;

import com.google.common.base.Strings;
import com.jtouzy.cv.model.classes.User;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

public class UserDAO extends AbstractSingleIdentifierDAO<User> {
	public UserDAO()
	throws DAOException {
		super(User.class);
	}
	
	public User getOneByMail(String mail)
	throws QueryException {
		if (Strings.isNullOrEmpty(mail))
			throw new IllegalStateException("L'adresse e-mail est obligatoire");
		Query<User> q = query();
		q.context().addEqualsCriterion(User.MAIL_FIELD, mail);
		return q.one();
	}
	
	public List<User> getAllByNames(String name, String firstName)
	throws QueryException {
		if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(firstName)) {
			throw new QueryException(new IllegalArgumentException("Le nom et le prénom doivent être renseignés pour la requête"));
		}
		Query<User> q = query();
		q.context().addEqualsCriterion(User.NAME_FIELD, name);
		q.context().addEqualsCriterion(User.FIRST_NAME_FIELD, firstName);
		return q.many();
	}
}
