package daoImpl;

import java.util.List;

import dao.Dao;

import dao.UserDao;
import domain.User;

public class UserDaoJdbcImpl extends Dao<User> implements UserDao{

	@Override
	public void register(User user) {
         String sql="insert into user (id,username,password) values(?,?)";
         update(sql,user.getUserName(),user.getPassword());
		
	}

	//@Override    未知是否用得上login
	//public void login(User user) {
		//String sql="select username from user where id=?";
		//return get(sql,id);
		
	//}

	public  List<User> getAll(){
		String sql="select*from user";
		return getForList(sql);
	}

	@Override
	public String find(User user) throws Exception{
		
		String sql="select password from user where username=?";
		return getForValue(sql,user.getUserName());
		//return null;
	}

	@Override
	public int findId(String userName,String password) {
		String sql="select id from user where username=? and password=?";
		try {
			return (int)getForValue(sql,userName,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
