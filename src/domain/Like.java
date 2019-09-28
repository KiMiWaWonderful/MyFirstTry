package domain;

public class Like {

	int id;
	String userName;
	int count;
	String title;
	int user_id;
	int diary_id;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getDiary_id() {
		return diary_id;
	}
	public void setDiary_id(int diary_id) {
		this.diary_id = diary_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
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
	public Like(int id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	public Like() {
		
	}
	public Like(String userName) {
		this.userName = userName;
	}
	public Like(String userName, String title) {
		super();
		this.userName = userName;
		this.title = title;
	}
	
}
