package dao;

import java.util.List;
import java.util.Map;

import domain.CriteriaDiary;
import domain.Diary;

public interface DiaryDao {

	public void delete(Integer id)throws Exception;//ɾ�����ѳɹ�
	
	public void save(Diary diary)throws Exception;//����δ�ɹ�
	
	
	public   List<Diary> getAll()throws Exception ;//ȫ����ѯ
	
	public Diary get(Integer id);//��ʾһ����û�з����޷�ʵ��(id�Ƿ�Ҫ�ĳɱ��⣿��
	
	public List<Diary> getForListWithCriteriaDiary(CriteriaDiary cd);//ģ����ѯ

	public void edit(Diary diary) throws Exception;
	
	//public List<Diary> getByCategory();
	
	public Diary outputFile(Integer id); //�ļ�����
	
//public void addComment(Diary diary);	
	
}
