package br.com.branetlogistica.core.database.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class ConnectionFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	private static Connection conn;
	public static Connection createDataSource(String clientId, String url, String password, String user) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(url);
		sql.append("/");
		sql.append(clientId);

		sql.append("?user=");
		sql.append(user);
		sql.append("&password=");
		sql.append(password);
		try {
			 conn = DriverManager.getConnection(sql.toString());
			 conn.setAutoCommit(true);
			return conn;

		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalError(e.getMessage());
		}

		
	}
	
	
	private static void createDataBase(Connection con, String clientId) {
		StringBuffer sql = new StringBuffer(" CREATE DATABASE ");
		sql.append(clientId);	
		sql.append("; ");		
		try (PreparedStatement stmt = con.prepareStatement(sql.toString());) {
			stmt.execute();			
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalError(e.getMessage());
		}	
	}
	
	private static boolean checaBaseDadosExiste(Connection con, String clientId) {
		StringBuffer sql = new StringBuffer(" SELECT 1 FROM pg_database WHERE datname= '");
		sql.append(clientId);
		sql.append("'; ");
		try (PreparedStatement stmt = con.prepareStatement(sql.toString()); 
			ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				if (rs.getObject(1) != null)
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalError(e.getMessage());
		}
		return false;
	}
	
	public static void checkAndCreate(String clientId, String url, String password, String user){
		try (Connection con = createDataSource("postgres",url,password, user)){				
			if(!checaBaseDadosExiste(con, clientId))
				createDataBase(con, clientId);
		} catch (Exception e) {			
			throw new InternalError(e.getMessage());
	}
	}
	

	
}