package domain;

public class Category {

	//private int id;
	private String category_name;
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	//public Category(int id, String category_name) {
		//super();
		//this.id = id;
		//this.category_name = category_name;
	//}
	public Category() {
		
	}
}
