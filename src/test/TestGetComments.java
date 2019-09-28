package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import dao.CommentDao;
import daoImpl.CommentJdbcImpl;
import domain.Comment;

class TestGetComments {

	private CommentDao commentDao = new CommentJdbcImpl();
	@Test
	void test() throws Exception {
		List<Comment> comments = commentDao.getAll();
		System.out.println(comments);
	}

}
