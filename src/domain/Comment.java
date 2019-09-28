package domain;

import java.util.Date;

public class Comment {

	int commentid;
	String name;
	Date time;
	String comment;
	String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Comment(int commentid, String name, Date time, String comment) {
		super();
		this.commentid = commentid;
		this.name = name;
		this.time = time;
		this.comment = comment;
	}
	public Comment() {
		
	}
	public Comment( String name,Date time, String comment) {
		super();
	
		this.name = name;
		this.time = time;
		this.comment = comment;
	}
	public Comment(int commentid, String name, Date time, String comment, String title) {
		super();
		this.commentid = commentid;
		this.name = name;
		this.time = time;
		this.comment = comment;
		this.title = title;
	}
	public Comment( String name, Date time, String comment, String title) {
		super();
		this.name = name;
		this.time = time;
		this.comment = comment;
		this.title = title;
	}
}
