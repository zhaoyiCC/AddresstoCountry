package buaa.act.baseService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class Init_CSDN {
	Connection con;
	String driver = "com.mysql.jdbc.Driver";
    String url1 = "jdbc:mysql://192.168.7.132:3306/db_bbspage";
    String url2 = "jdbc:mysql://192.168.7.132:3306/db_yifei";
    String user = "root";
    String password = "123456";
	
	public void build(int choose) throws ClassNotFoundException, SQLException, Exception{
		Class.forName(driver);
		if(choose == 1){
			this.con = DriverManager.getConnection(url1, user, password);
		}
		else if (choose == 2){
			this.con = DriverManager.getConnection(url2, user, password);
		}	
	}
	
	public void close() throws SQLException{
		this.con.close();
	}
	
	public JSONArray executeCypher(int choose, String cypher, int length, boolean flag) throws ClassNotFoundException, Exception{
		build(choose);
		try(Statement statement = this.con.createStatement() ){
			JSONArray array = new JSONArray();
			
			//System.out.println(cypher);
			ResultSet rs = statement.executeQuery(cypher);
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			int count = 0;
			
			//System.out.println(columnCount);
			
			while (rs.next()) {        
		        JSONObject jsonObj = new JSONObject();
		        
		        for (int i = 1; i <= columnCount; i++) {  
		            String columnName =md.getColumnLabel(i);
		            //System.out.println("columnName "+columnName);
		            String value = rs.getString(columnName); 
		            //System.out.println("value "+value);
		            jsonObj.put(columnName, value);  
	            } 
		        count ++;
				if(count > length) break; 
		        array.add(jsonObj);
		    }  
			
			close();
			//System.out.println(array);
			return array;
		}
	}
	
}
