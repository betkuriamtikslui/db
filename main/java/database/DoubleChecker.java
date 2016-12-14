package database;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@SuppressWarnings("restriction")
public class DoubleChecker implements Runnable {
	ScriptEngineManager manager = new ScriptEngineManager();
	ScriptEngine engine = manager.getEngineByName("JavaScript");

	private CircularList<String> list;
	private ConcurrentLinkedQueue<String> subredditQueue;
	private ConcurrentLinkedQueue<String> postQueue;
	public static boolean go = true;

	public DoubleChecker(CircularList<String> list, ConcurrentLinkedQueue<String> postQueue,
			ConcurrentLinkedQueue<String> subredditQueue) {
		this.list = list;
		this.postQueue = postQueue;
		this.subredditQueue = subredditQueue;
	}

	public void run() {
		int count = 15;
		while (go) {
			String line = list.fetch();
			String subredditFull = "";
			String postFull = "";
			String sub;
			String post;
			for (int i = 0; i < count; i++) {
				if (line != null) {

					try {
						engine.eval("var x  = " + line);
						String id = (String) engine.eval("x.id");
						String parent_id = (String) engine.eval("x.parent_id");
						String link_id = (String) engine.eval("x.link_id");
						String name = (String) engine.eval("x.name");
						String author = (String) engine.eval("x.author");
						String body = (String) engine.eval("x.body");
						body = body.replaceAll("'", "||");
						body = body.replace("\\", "/");
						String subreddit_id = (String) engine.eval("x.subreddit_id");
						String subreddit = (String) engine.eval("x.subreddit");
						int score = (Integer) engine.eval("x.score");
						int created_utc = Integer.parseInt((String) engine.eval("x.created_utc"));

						sub = "('" + subreddit_id + "', '" + subreddit + "')";
						if (i != count - 1) {
							sub += ",\n";
						}
						post = "('" + id + "', '" + parent_id + "', '" + link_id + "', '" + name + "', '" + author
								+ "', '" + body + "', '" + Integer.toString(score) + "', '"
								+ Integer.toString(created_utc) + "')";
						if (i != count - 1) {
							post += ",\n";
						}

						// String s = "('" + id + "', '" + parent_id + "', '" +
						// link_id + "', '" + name + "', '" + author
						// + "', '" + body + "', '" + subreddit_id + "', '" +
						// subreddit + "', '"
						// + Integer.toString(score) + "', '" +
						// Integer.toString(created_utc) + "')";
						// if (i != count - 1) {
						// s += ",\n";
						// }
						postFull += post;
						subredditFull += sub;
					} catch (ScriptException e) {
						continue;
					}

					// System.out.println(full);
				} else {
					if (FileReader.empty) {
						this.go = false;
					}
				}
			}

			subredditQueue.add(subredditFull);
			postQueue.add(postFull);

			if (subredditQueue.size() > 150 || postQueue.size() > 150) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					continue;
				}
			}

		}
	}
}
