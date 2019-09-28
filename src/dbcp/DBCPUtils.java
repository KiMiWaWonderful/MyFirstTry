package dbcp;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DBCPUtils {
	
	private static DataSource dataSource =null;
	static {
	
		try {
			//1.����properties�ļ�������
			InputStream is = DBCPUtils.class.getResourceAsStream("/dbcp.properties");	//��Ҫ�Ǹ�class.getclassloaderʲô�ġ��������Ӳ���		
			//2.����������
			Properties props = new Properties();
			props.load(is);
			
			//3.��������Դ
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	public static Connection getConnection() throws Exception {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new Exception(e);
		}
	}
	
	public static void releaseConnection(Connection conn,PreparedStatement ps,ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

}
