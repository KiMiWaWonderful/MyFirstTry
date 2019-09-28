package daoImpl;

import java.util.List;
import java.util.Map;

import dao.Dao;
import dao.DiaryDao;
import domain.CriteriaDiary;
import domain.Diary;
import domain.User;

public class DiaryDaoJdbcImpl extends Dao<Diary> implements DiaryDao{

	@Override
	public void delete(Integer id)  {
		
			String sql = "delete from diary where id = ?";
			update(sql,id);	
		}

	//author应该可以用username代替
	@Override
	public void save(Diary diary) throws Exception {
	
		User user=new User();
		
		String sql="insert into diary(title,author,content,date,category_name) values(?,?,?,?,?)";//可以不写id,会自动增加
				update(sql,diary.getTitle(),diary.getAuthor(),diary.getContent(),diary.getDate(),diary.getCategory_name());
	}

	//author应该用username来代替
	@Override
	public void edit(Diary diary) throws Exception {
	
		String sql = "update diary set title=?,author=?,content=? where id=?";
		update(sql,diary.getTitle(),diary.getAuthor(),diary.getContent(),diary.getId());	
	}

	
	public  List<Diary> getAll(){//已实现
		String sql="select id,title,author,content,date from diary";
		return getForList(sql);
	}

	@Override
	public Diary get(Integer id) {
	//String sql="select id,title,author,content,comment from diary where id=?";
		String sql="select id,title,author,content from diary where id=?";
		return get(sql,id);
	}

	@Override
	public List<Diary> getForListWithCriteriaDiary(CriteriaDiary cd) {//已实现
		String sql="select id,title,author,content,date from diary where title like ? and author like ? and content like ?";
		return getForList(sql,cd.getTitle(),cd.getAuthor(),cd.getContent());
	}

	@Override
	public Diary outputFile(Integer id) {
		String sql="select id,title,author,content from diary where id=?";
		return get(sql,id);
	}

	//@Override
	//public List<Diary> getByCategory(Integer id) {
		//String sql=" select t.title,t.author,t.date,t.content,c.category_name from(select title,author,date,content,category_id from diary where category_id=?)t join category c on c.id=t.category_id;";
		//return getForList(sql,id);
	//}

	//@Override
	//public List<Diary> getByCategory() {
		//String sql="select title,author,content,date from diary where category_name=?";
		//return getForList(sql,category_name);
		//String sql="select category_name,GROUP_CONCAT(title) from diary group by category_name";
		//return getForList(sql);
	//}

	

	//@Override
	//public void addComment(Diary diary){
		//String sql="update diary set comment=? where id=?";
		//update(sql,diary.getComment(),diary.getId());
		
	//}

	

		
	}

	
	


