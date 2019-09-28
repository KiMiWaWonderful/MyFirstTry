package dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import dbcp.DBCPUtils;
import domain.Category;
import domain.CriteriaDiary;
import domain.Diary;
import domain.Like;
import domain.User;

public class Dao<T> {

QueryRunner queryRunner = new QueryRunner();

private Class<T> clazz;
//要写反射

   public Dao() {//映射
	Type superClass = getClass().getGenericSuperclass();
	if(superClass instanceof ParameterizedType) {
		ParameterizedType parameterizedType = (ParameterizedType) superClass;
		Type[] typeArgs = parameterizedType.getActualTypeArguments();
		if(typeArgs!=null && typeArgs.length>0) {
			clazz=(Class<T>)typeArgs[0];
		}
	}
}
	
	//1.增删改操作:insert,delete,update
	public void update(String sql,Object ... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection=DBCPUtils.getConnection();
		    queryRunner.update(connection, sql,args);//不能漏掉connection和args
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBCPUtils.releaseConnection(connection, preparedStatement, resultSet);
		}
	}
	
	//2.返回T所对应的List
	public  List<T> getForList(String sql,Object ... args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection=DBCPUtils.getConnection();
			return queryRunner.query(connection,sql, new BeanListHandler<>(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBCPUtils.releaseConnection(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	public  List<Map<String,Object>> getForMap(String sql,Object ... args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection=DBCPUtils.getConnection();
			return queryRunner.query(connection,sql, new MapListHandler(),args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBCPUtils.releaseConnection(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	//3.返回对应的T的一个实例类的对象
	public T get(String sql,Object ... args) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection=DBCPUtils.getConnection();
			return queryRunner.query(connection,sql, new BeanHandler<>(clazz),args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBCPUtils.releaseConnection(connection, preparedStatement, resultSet);
		}
		return null;
	}
	
	/**
	 * 返回某一个字段的值，如返回某一条记录的customerName，或返回数据表中有多少条记录等
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception 
	 */
	public <E> E getForValue(String sql,Object...args) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		try {
			connection = DBCPUtils.getConnection();
			return (E)queryRunner.query(connection,sql,new ScalarHandler(),args);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBCPUtils.releaseConnection(connection, ps, rs);
		}
		return null;
	
	}

	

	
	
}
