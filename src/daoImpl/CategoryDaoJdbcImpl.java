package daoImpl;

import java.util.List;

import dao.CategoryDao;
import dao.Dao;
import domain.Category;
import domain.Diary;

public class CategoryDaoJdbcImpl extends Dao<Category> implements CategoryDao{

	//Diary diary=new Diary();
	
	//@Override
	//public List<Diary> getByCategory(String category_name) {
		//String sql="select*from diary d join category c on d.category_name=c.category_name where d.category_name=?";
		//return getForList(sql,category_name);
	//}

	@Override
	public List<Category> getByCategory() {
		String sql="select category_name,GROUP_CONCAT(title) title from diary group by category_name";
		return getForList(sql);
	}

}
