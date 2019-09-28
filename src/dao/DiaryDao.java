package dao;

import java.util.List;
import java.util.Map;

import domain.CriteriaDiary;
import domain.Diary;

public interface DiaryDao {

	public void delete(Integer id)throws Exception;//删除，已成功
	
	public void save(Diary diary)throws Exception;//增添，未成功
	
	
	public   List<Diary> getAll()throws Exception ;//全部查询
	
	public Diary get(Integer id);//显示一个，没有反射无法实现(id是否要改成标题？）
	
	public List<Diary> getForListWithCriteriaDiary(CriteriaDiary cd);//模糊查询

	public void edit(Diary diary) throws Exception;
	
	//public List<Diary> getByCategory();
	
	public Diary outputFile(Integer id); //文件导出
	
//public void addComment(Diary diary);	
	
}
