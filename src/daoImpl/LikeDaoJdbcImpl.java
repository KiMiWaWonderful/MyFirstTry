package daoImpl;

import java.util.List;

import dao.Dao;
import dao.LikeDao;
import domain.Like;

public class LikeDaoJdbcImpl extends Dao<Like> implements LikeDao{

	@Override
	public void addLike(String userName,String title) {
	String sql="insert into liked(username,title) values( ?,?)";
	update(sql,userName,title);
		
	}

	@Override
	public List<Like> collectLike(String title) {
		String sql=" select count(*) count from liked where title=?";
		return getForList(sql,title);
	}

	@Override
	public void saveLike(String userName) {
		String sql="insert into liked(username) values(?)";
		update(sql,userName);
		
	}

	@Override
	public void addLike(String title) {
		String sql="insert into liked(title) values(?)";
		update(sql,title);
		
	}

	@Override
	public void addLike(int user_id, int diary_id) {
		String sql="insert into liked(user_id,diary_id) values(?,?)";
		update(sql,user_id,diary_id);
		
	}

	@Override
	public long collectLike(int diary_id) throws Exception {
		
		//try {
			String sql="select count(*) count from diary d join liked l on d.id=l.diary_id where d.id=? ";
			return (long)getForValue(sql,diary_id);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		//return null ;
	}

	@Override
	public long judgeLike(int user_id,int diary_id) {
		String sql="select count(*) count from liked l where l.user_id=? and l.diary_id=? ";
		try {
			return (long)getForValue(sql,user_id,diary_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}


}
