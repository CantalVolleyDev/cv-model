package com.jtouzy.cv.model.dao;

import com.google.common.base.Strings;
import com.jtouzy.cv.model.classes.User;
import com.jtouzy.cv.model.errors.UserNotFoundException;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.errors.QueryException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;
import com.jtouzy.dao.query.Query;

public class UserDAO extends AbstractSingleIdentifierDAO<User> {
	public UserDAO()
	throws DAOException {
		super(User.class);
	}
	
	public User findByMail(String mail)
	throws UserNotFoundException {
		try {
			if (Strings.isNullOrEmpty(mail))
				throw new UserNotFoundException(new IllegalStateException("L'adresse e-mail est obligatoire"));
			Query<User> q = query();
			q.context().addEqualsCriterion(User.MAIL_FIELD, mail);
			User user = q.one();
			if (user == null)
				throw new UserNotFoundException(mail);
			return user;
		} catch (QueryException ex) {
			throw new UserNotFoundException(ex);
		}
	}
}
