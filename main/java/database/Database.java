package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Database implements Runnable {

	java.sql.Connection con = null;
	java.sql.Statement st = null;
	ResultSet rs = null;

	static String url = "jdbc:mysql://localhost:3306/my_db?autoReconnect=true&useSSL=false";
	static String user = "root";
	static String password = "123456789";
	public ConcurrentLinkedQueue<String> fetchFrom;

	Lock l;

	public Database() throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();
		rs = st.executeQuery("SELECT VERSION()");
		l = new ReentrantLock();
	}

	public void run() {
		boolean go = true;
		while (go) {
			try {
				String s = fetchFrom.poll();
				if (s != null) {
					st.executeUpdate(s);
					//System.out.println(s);
				} else {
					if(!Checker.go){
						break;
					}
				}
			} catch (Exception e) {
				
				continue;
			}
		}
	}

	
}