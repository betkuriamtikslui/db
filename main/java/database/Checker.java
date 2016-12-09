package database;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import javax.script.*;

public class Checker implements Runnable {
	CircularList<String> list;
	@SuppressWarnings("restriction")
	ScriptEngine engine;
	public static boolean go = true;
	public static long start;
	public ConcurrentLinkedQueue<String> destination;

	public Checker(CircularList<String> list) {
		this.list = list;
		start = System.currentTimeMillis();
	}

	public static void setGo() {
		go = false;
	}
	
	@SuppressWarnings("restriction")
	public void run() {
		while (go) {
			String line = list.fetch();
			if (line != null) {
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("JavaScript");
				try {
					engine.eval("var x  = " + line);
					String id = (String) engine.eval("x.id");
					String parent_id = (String) engine.eval("x.parent_id");
					String link_id = (String) engine.eval("x.link_id");
					String name = (String) engine.eval("x.name");
					String author = (String) engine.eval("x.author");
					String body = (String) engine.eval("x.body");
					String subreddit_id = (String) engine.eval("x.subreddit_id");
					String subreddit = (String) engine.eval("x.subreddit");
					int score = (Integer) engine.eval("x.score");
					int created_utc = Integer.parseInt((String) engine.eval("x.created_utc"));

					String s = "INSERT INTO stuff (id, parent_id, link_id, name, author, body, subreddit_id,subreddit,score, created_utc )VALUES ('"
							+ id + "', '" + parent_id + "', '" + link_id + "', '" + name + "', '" + author + "', '"
							+ body + "', '" + subreddit_id + "', '" + subreddit + "', '" + Integer.toString(score)
							+ "', '" + Integer.toString(created_utc) + "');";

					destination.add(s);
					if(destination.size() > 100){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							continue;
						}
					}
				} catch (ScriptException e) {
					System.out.println(e);
				}
			} else {
				if(FileReader.empty){
					go = false;
				}
				
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new Checker(null)).start();
	}
}
