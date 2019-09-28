package domain;

import dao.UserDao;
import daoImpl.UserDaoJdbcImpl;

public class User {

	private UserDao userDao = new UserDaoJdbcImpl();
	int id;
	String userName;
	String password;
	public int getId(String userName,String password) {
		return userDao.findId(userName, password);
		//return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(int id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
	}
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public User() {
		
	}
}
