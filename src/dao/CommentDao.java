package dao;

import java.util.List;
import java.util.Map;

import domain.Comment;



public interface CommentDao {

	public List<Comment> getAll() throws Exception;
	public void save(Comment comment) throws Exception;
	public List<Comment> getComments(Integer id) throws Exception;
	public List<Map<String,Object>> getCommentss(String title) throws Exception;
}
