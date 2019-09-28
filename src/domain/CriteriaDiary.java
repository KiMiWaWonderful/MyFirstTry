package domain;

import java.util.Date;

public class CriteriaDiary {

	
	private String title;
	private String author;
	private String content;
	private int id;
	
	public CriteriaDiary(String title, String author, String content, int id) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		if(title==null) {
			title="%%";
		}else {
			title="%"+title+"%";
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		if(author==null) {
			author="%%";
		}else {
			author="%"+author+"%";
		}
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		if(content==null) {
			content="%%";
		}else {
			content="%"+content+"%";
		}
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public CriteriaDiary(String title, String author, String content) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
	}
	
}
