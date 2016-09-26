package com.junald.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;

public class DBUtil {

	private static Connection conn;
	
	public static Connection getConnection() throws InvalidSyntaxException {
		if( conn != null )
			return conn;
		
		//************************************
		
		String vcap_services = System.getenv("VCAP_SERVICES");
	    String hostname = "";
	    String dbname = "";
	    String user = "";
	    String password = "";
	    String port = "";
		
	    	try {
	    	
	    if (vcap_services != null && vcap_services.length() > 0) {
	        JsonRootNode root;
			
				root = new JdomParser().parse(vcap_services);
			

	        JsonNode mysqlNode = root.getNode("p-mysql");
	        JsonNode credentials = mysqlNode.getNode(0).getNode(
	                "credentials");

	        dbname = credentials.getStringValue("name");
	        hostname = credentials.getStringValue("hostname");
	        user = credentials.getStringValue("username");
	        password = credentials.getStringValue("password");
	        port = credentials.getNumberValue("port");
	        String dbUrl = "jdbc:mysql://" + hostname + ":" + port + "/"
	                + dbname;
	        System.out.println(dbUrl);
	      
	       
	   
			String driver1 = "com.mysql.jdbc.Driver";
			
			Class.forName( driver1 );
			conn = DriverManager.getConnection(  dbUrl, user, password );
	    }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void closeConnection( Connection toBeClosed ) {
		if( toBeClosed == null )
			return;
		try {
			toBeClosed.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
