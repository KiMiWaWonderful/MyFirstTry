package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dao.DiaryDao;
import daoImpl.DiaryDaoJdbcImpl;
import domain.Diary;

class TestGetOne {

	DiaryDao diaryDao=new DiaryDaoJdbcImpl();
	
	@Test
	void testGetInteger() {
	Diary diary = diaryDao.get(1);
	System.out.print(diary);
	}
	//java.lang.NullPointerException 弄清楚反射机制
}
