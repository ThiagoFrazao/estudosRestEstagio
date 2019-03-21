package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

@ApplicationScoped
public class ConexaoDAO {
	
	//private static final String url = "jdbc:hsqldb:file:C:/hsqldb/demo/estudo;hsqldb.write_delay=false;shutdown=true";
	
	@Resource(lookup="java:/jdbc/hsqldb")
	private DataSource ds;

	public Connection getConexao(){
		
		Connection con = null;
	    try {
	    	//DriverManager.getConnection("url","usuario","senha");
	    	con = ds.getConnection();
	    	
			} catch (SQLException e) {	
				System.out.println("SQLException " + e.getMessage());
				e.printStackTrace();
			}	
	    return con;
	}
	
}
