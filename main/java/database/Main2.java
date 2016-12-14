package database;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main2 {

	static ExecutorService pool;

	public static void main(String[] args) throws SQLException {
		String subredditBase= ("INSERT INTO subreddits ( subreddit_id,subreddit)VALUES");
		
		String postBase = ("INSERT INTO posts (id, parent_id, link_id, name, author, body, score, created_utc )VALUES");
		
		
		Database.url = args[1];
		Database.user = args[2];
		Database.password = args[3];
		
		Database.initialize();

		
		pool = Executors.newFixedThreadPool(500);
		CircularList<String> list = new CircularList<String>();
		ConcurrentLinkedQueue<String> postQueue = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> subredditQueue = new ConcurrentLinkedQueue<String>();
		pool.execute(new FileReader(list,args[0]));
		pool.execute(new DoubleChecker(list,postQueue, subredditQueue));
		Database.initialize();
		pool.execute(new Database(postBase, postQueue));
		pool.execute(new Database(subredditBase, subredditQueue));

	}
}
