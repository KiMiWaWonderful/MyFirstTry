package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dao.DiaryDao;
import daoImpl.DiaryDaoJdbcImpl;

class TestDelete {

	private DiaryDao diaryDao = new DiaryDaoJdbcImpl();
	
	@Test
	void testDelete() throws Exception {
		diaryDao.delete(2);
	}

}
