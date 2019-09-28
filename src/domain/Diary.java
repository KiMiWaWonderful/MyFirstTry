package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Diary {
	private Date date;
	

	private String title;
	private String author;
	private String content;
	private int id;
	private String category_name;
	//private String comment;
	private List<Comment> comments;
	private List<Category> category;
	//private int likeCount=0;
	private List<Like> likeCount;
	
	
	
	//public String getComment() {
		//return comment;
	//}
	//public void setComment(String comment) {
		//this.comment = comment;
	//}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategery_name(String category_name) {
		this.category_name = category_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
    
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Diary() {
		
	}
	
	public Diary( String title, String author, String content,Date date, int id) {
		super();
		this.date = date;
		this.title = title;
		this.author = author;
		this.content = content;
		this.id = id;
	}
	
	public Diary( String title, String author, String content,Date date) {
		super();
		this.date = date;
		this.title = title;
		this.author = author;
		this.content = content;
		
	}
	
	public Diary(int id, String title, String author, String content) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.content = content;
		
	}
	
	public Diary( String title, String author, String content) {
		super();
		//this.id = id;
		this.title = title;
		this.author = author;
		this.content = content;
		
	}
	
	public Diary(Date date, String title, String author, String content, int id, String category_name) {
		super();
		this.date = date;
		this.title = title;
		this.author = author;
		this.content = content;
		this.id = id;
		this.category_name = category_name;
	}
	public Diary(Date date, String title, String author, String content, int id, String category_name, String comment) {
		super();
		this.date = date;
		this.title = title;
		this.author = author;
		this.content = content;
		this.id = id;
		this.category_name = category_name;
		//this.comment = comment;
	}
	public Diary(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	public Diary(int id,String title,String author,String content,String comment) {
		super();
		//this.date = date;
		this.title = title;
		this.author = author;
		this.content = content;
		this.id = id;
		//this.catogery_name = catogery_name;
		//this.comment = comment;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public List<Comment> setComments(List<Comment> comments) {
		return this.comments = comments;
	}
	public List<Category> getCategory() {
		return category;
	}
	public List<Like> getLikeCount() {
		return likeCount;
	}
	public List<Like> setLikeCount(List<Like> likeCount) {
		return this.likeCount = likeCount;
	}
	//public void setCategory(List<Category> category) {
		//this.category = category;
	//}
	public List<Category> setCategory(List<Category> category) {
		return this.category = category;
	}
	public Diary(String title, String author, String content, int id, List<Comment> comments) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
		this.id = id;
		this.comments = comments;
	}
	
	public Diary(String title,String author,String content,Date date,String category_name) {
		this.title = title;
		this.author = author;
		this.content = content;
		this.date = date;
		this.category_name =category_name;
	}
	
	public String toString() {
		return "Diary[id="+id+",title="+title+",author="+author+",content="+content+"]";
	}
	
	
}
