package dao;

import java.util.List;


import domain.User;

public interface UserDao {

	public void register(User user);
	//public void login(User user);  δ֪�Ƿ��õ���
	public   List<User> getAll()throws Exception ;
	
	public String find(User user) throws Exception;
	
	public int findId(String userName,String password);
}
