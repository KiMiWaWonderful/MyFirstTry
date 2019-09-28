package dao;

import java.util.List;

import domain.Category;
import domain.Diary;

public interface CategoryDao {

	//public List<Diary> getByCategory(String category_name);
	
	public List<Category> getByCategory();
}
