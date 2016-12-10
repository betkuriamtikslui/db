package database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Database implements Runnable {

	java.sql.Connection con = null;
	java.sql.Statement st = null;
	ResultSet rs = null;
	private final String  basic = "INSERT INTO stuff (id, parent_id, link_id, name, author, body, subreddit_id,subreddit,score, created_utc )VALUES ";

	static String url = "";
	static String user = "";
	static String password = "";
	public ConcurrentLinkedQueue<String> fetchFrom;


	public Database() throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();
		rs = st.executeQuery("SELECT VERSION()");
	}

	public void run() {
		boolean go = true;
		int end = 15;
		String query = "";
		String tmp;
		while (go) {
			// String basic = "INSERT INTO stuff (id, parent_id, link_id, name,
			// author, body, subreddit_id,subreddit,score, created_utc )VALUES
		
			query = basic;
			for (int i = 0; i < end; i++) {
				tmp = fetchFrom.poll();
				
				if (tmp == null) {
					i--;
				} else {
					//tmp = tmp.replaceAll("\'", "\\\'");
					query += tmp;
					if (i != end - 1) {
						query += ",";
					} else {
						query += ";";
					}
				}
				
			}
			try {

				st.executeUpdate(query);
				
			} catch (SQLException e) {
				continue;
			}
		}
	}

}