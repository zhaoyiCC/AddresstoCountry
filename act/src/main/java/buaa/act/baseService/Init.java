package buaa.act.baseService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
//采用实现Comparable接口的方法实现排序
class S1 implements Comparable{
	String x;
	int y;
	S1(String x, int y){
		this.x = x;
		this.y = y;
	}
	//实现排序方法。先比较x，如果相同比较y
	@Override
	public int compareTo(Object o) {
		S1 obj = (S1) o;
		if(y != obj.y)
		{
			return obj.y - y;
		}
		return x.compareTo(obj.x);
	}
	//重写toStirng方法，改变println时的显示效果
	public String toString(){
//		return "("+x+", "+y+")";
		return "{name : '"+ x + "', value : "+ y+ "}";
	}
}

public class Init{
	Connection con;
	
	public void build(int choose) throws SQLException{
		String address = "";
		String login = "";
		String password = "";
		
		if(choose == 1)// choose github
		{
//			address = "192.168.7.124";
			address = "10.1.1.4";
			login = "neo4j";
			password = "buaaxzl";
		}
		else if(choose == 2){//choose topcoder
			address = "192.168.7.109" ;
			login = "neo4j";
			password = "123456";
		}
		else if(choose == 3) {
			address = "192.168.7.105";// master of so
		}
		else if(choose == 4) {
			address = "192.168.7.132";
			login = "neo4j";
			password = "123456";
		}
//		using http 
		this.con = DriverManager.getConnection("jdbc:neo4j:http://" + address + ":7474/", login, password);//192.168.7.123
		//using bolt
//		this.con = DriverManager.getConnection("jdbc:neo4j:bolt://" + address + ":7687/", login, password);
	}
	
	public void close() throws SQLException{
		this.con.close();
	}
	
	public ArrayList<String> executeCypher(int choose, String cypher, int length, boolean flag) throws SQLException{
		//address格式 : 192.168.7.xxx
		build(choose);
		try(Statement stmt = this.con.createStatement() ){
			ResultSet rs =  stmt.executeQuery(cypher);
			ArrayList<String> ret = new ArrayList<String>();
			
			int i = 0;
			 while(rs.next()){
				 if(flag) {
					 ret.add(Integer.toString(rs.getInt("fx")));
				 }
				 else {
					 ret.add(rs.getString("fx"));
				 }
				 i ++;
				 if(i > length) break;
			 }
			 close();
			return ret;
		}
	}
	
	public static void main(String[] args) throws SQLException{
		String[] st_all;
		Map<String, Integer> mp = new HashMap<String, Integer>();
		InputStreamReader isr;
        BufferedReader buff;
        int old_value, add_value;
        String str, s1, s2;
        FileOutputStream fs, fs2;
        PrintStream outtt = null;
        PrintStream dt = null;
        int cnt = 0;
        
        try{
        	String f_n = "output.txt";
        	fs = new FileOutputStream(new File(f_n));
        	
        	fs2 = new FileOutputStream(new File("detail.txt"));
        	
        	outtt = new PrintStream(fs);
        	
        	dt = new PrintStream(fs2);
        }catch (Exception e){}
        
        
		Init con = new Init();
		 {
			 
			 
			 String s = "map.txt";
			 File file = new File(s);
				
		        try {
		        	InputStream instr = new FileInputStream(file);
		            isr = new InputStreamReader(instr);
		            buff = new BufferedReader(isr);
//		            n = 1;
		            while ((str = buff.readLine()) != null)
		            {
		            	st_all = str.split(",");
//		            	System.out.println(st_all.length);
		            	mp.put(st_all[0],0);
		            	
		       
//		            	if (mp.containsKey("Iran")){
//		            		h = mp.get("Iran");
//		            		mp.put("Iran", h+1);
//		            	}
		            	System.out.println(st_all[0]+" &&& "+st_all[1]);
//		            	System.out.println(str);
//		            	str = str.replaceAll(" ", "");
//		            	str = str.replaceAll("\t", "");
		            }
		        }catch (Exception e){}
//		        if (mp.containsKey("Iran"))
//		        	System.out.print(mp.get("Iran"));
		        try{
//		        	System.out.println("123".contains("23"));
		        	
		        	InputStream instr2 = new FileInputStream("tojava.txt");
			        isr = new InputStreamReader(instr2);
			        buff = new BufferedReader(isr);
			        
			        
			        while ((str = buff.readLine()) != null)  {
//outtt.println(str);
			        	cnt++;
			        	st_all = str.split("::::::");
			        	
			        	if (st_all.length < 2){
			        		dt.println("######"+str);
			        		continue;
			        	}
			        	
			        	
			        	
			        	if (st_all.length>2){
			        		System.out.println("!!!!"+str);
			        	}
			        	if (!mp.containsKey(st_all[1])){
			        		System.out.println("****"+str);
			        	}
			        	s1 = st_all[0];
			        	s2 = st_all[1];
			        	
			        	if (s1.length()==0){
			        		dt.println("@@@@@@"+str);
			        		continue;
			        	}
			        	if (s1.length()==0){
			        		dt.println("~~~~~~"+str);
			        		continue;
			        	}
			        	
			        	old_value = 0;
			        	String slash = "\\";
			        	String quote = "\"";
//			        	System.out.println(quote);
//			        	String s_s =  + quote;
			        	//if (s1.contains(quote))	
			        	s1 = s1.replace("\\", "\\\\");
			        	s1 = s1.replace("\"", "\\\"");
			        	String query;
			        	//if (s1.contains("'") || s1.contains(quote)){
			        		query = "MATCH (n:User) where n.Location = \"" + s1 + "\" return count(n) as fx";
			        	//}else{
			        	//	query = "MATCH (n:User) where n.Location = '" + s1 + "' return count(n) as fx";
			        	//}
			        	//if (query.contains(quote))
			        		query = query.replace("'", "\\'");
			  
			        	ArrayList<String> ans = con.executeCypher(3, query, 1, true);
			        	add_value = Integer.parseInt(ans.get(0));
						if (add_value == 0){
							dt.println("^^^^^^^^^^" + str);
						}
dt.println(s1 + "     " + s2+"     "+ans.get(0) + "*****"+query);
			        	
			        	if (mp.containsKey(s2)){
			        		old_value = mp.get(s2);
			        		mp.put(s2, old_value+add_value);
			        	}else{
			        		System.out.println("%%%%%%"+str);
			        	}
			        }
		        }catch (Exception e){}
		        
		        
		        
		        List<S1> s1Set = new ArrayList<S1>();
		        S1 s_1;
		        for (Map.Entry<String, Integer> entry : mp.entrySet()) {  
		        	//System.out.println(entry.getKey() + "   " + entry.getValue());  
		       		s_1 = new S1(entry.getKey(),  entry.getValue());
		       		s1Set.add(s_1);
		        }  
		        int len = s1Set.size();
		        int ptr = 0;
		        //对容器进行排序的函数
		        Collections.sort(s1Set);
	       		Iterator it = s1Set.iterator();
	       		while(it.hasNext())
	       		{
	       			ptr++;
//	       			System.out.print(it.next());
//	       			if (ptr != len)
//	       				System.out.print(",");
//	       			System.out.println("");
	       			
	       			
	       			outtt.print(it.next());
	       			if (ptr != len)
	       				outtt.print(",");
	       			outtt.println("");
	       		}
	       		System.out.println("cnt = "+cnt);
		     /*   
			 String query = "MATCH (n:User) where n.Location = 'El Cerrito, CA' return count(n) as fx";
//					 "match (n:Project) where n.created_at < \"" + Integer.toString(i) + "-0-0\" return count(n) as fx";
			 ArrayList<String> ans = con.executeCypher(3, query, 1, true);
			 System.out.println(ans.get(0));
//			 result.put(Integer.toString(i), Integer.parseInt(ans.get(0)));
 */
		 }
	}
	
	
}
