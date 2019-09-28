package daoImpl;

import java.util.List;
import java.util.Map;

import dao.CommentDao;
import dao.Dao;
import domain.Comment;
import domain.Diary;


public class CommentJdbcImpl extends Dao<Comment> implements CommentDao{

	//Diary diary = new Diary();
	
	@Override
	public List<Comment> getAll() throws Exception {
		String sql="select*from comment";
		 return getForList(sql);
	}

	@Override
	public void save(Comment comment) throws Exception {
		String sql = "insert into comment(name,comment,time,title) values(?,?,?,?)";
		update(sql,comment.getName(), comment.getComment(),comment.getTime(),comment.getTitle());
		
	}

	//@Override
	//public List<Comment> getComments(Integer id) throws Exception {
	//String sql=" select c.name,c.time,c.comment from comment c join diary d on d.title=c.title where d.id = ?";
		//return getForList(sql,diary.);
	//}

	@Override
	public List<Map<String, Object>> getCommentss(String title) throws Exception {
		String sql=" select c.name,c.time,c.comment from comment c join diary d on d.title=c.title where d.title = ?";
		return getForMap(sql,title);
	}

	@Override
	public List<Comment> getComments(Integer id) throws Exception {
		String sql=" select c.name,c.time,c.comment from comment c join diary d on d.title=c.title where d.id = ?";
		return getForList(sql,id);
	}

}
