package dao;

import java.util.List;

import domain.Like;

public interface LikeDao {

	public void addLike(String userName,String title);
	public void saveLike(String userName);
	public List<Like> collectLike(String title);
	public void addLike(String title);
	public void addLike(int user_id,int diary_id);
	public long collectLike(int diary_id) throws Exception ;
	public long judgeLike(int user_id,int diary_id);
}
