package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import dao.DiaryDao;
import daoImpl.DiaryDaoJdbcImpl;
import domain.Diary;

class TestGetAll {

	private DiaryDao diaryDao = new DiaryDaoJdbcImpl();
	@Test
	void test() throws Exception {
		
		List<Diary> diarys = diaryDao.getAll();
		System.out.println(diarys);
	}

}
