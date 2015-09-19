package com.jtouzy.cv.model.dao;

import com.jtouzy.cv.model.classes.Comment;
import com.jtouzy.dao.errors.DAOException;
import com.jtouzy.dao.impl.AbstractSingleIdentifierDAO;

public class CommentDAO extends AbstractSingleIdentifierDAO<Comment> {
	public CommentDAO()
	throws DAOException {
		super(Comment.class);
	}
}
