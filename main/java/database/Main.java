package database;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	static ExecutorService pool;

	public static void main(String[] args) throws SQLException {
		int length = 10;
		ConcurrentLinkedQueue<String> second1 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second2 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second4 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second3 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second5 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second6 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second7 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second8 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second9 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second12 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second10 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second11 = new ConcurrentLinkedQueue<String>();
		
		
		pool  = Executors.newFixedThreadPool(50);

		CircularList<String> list = new CircularList<String>();
		CircularList<String> list2 = new CircularList<String>();
		CircularList<String> list3 = new CircularList<String>();

		FileReader reader = new FileReader(list, args[0],list2, list3);
		new Thread(reader).start();
		int smth =36;
		for (int i = 0; i < smth; i++) {
			Checker c = new Checker(list);
			if(i%3==1){
				c.list = list2;
			}else if(i%3==2){
				c.list = list3;
			}
			int re = i%7;
			switch(re){
			case 0:
				c.destination = second1;
				break;
			case 1:
				c.destination = second2;
				break;
			case 2:
				c.destination = second3;
				break;
			case 3:
				c.destination = second4;
				break;
			case 4:
				c.destination = second5;
				break;
			case 5:
				c.destination = second6;
				break;
			case 12: 
				c.destination = second12;

				break;
			case 11: 
				c.destination = second11;

				break;
			case 10: 
				c.destination = second10;

				break;
			case 9: 
				c.destination = second9;

				break;
			default:
				c.destination = second8;
			}
			pool.execute(c);
		}
		
		for(int i = 0; i<  smth*3; i++){
			Database db = new Database();
			int re = i%7;
			switch(re){
			case 0:
				db.fetchFrom = second1;
				break;
			case 1:
				db.fetchFrom = second2;
				break;
			case 2:
				db.fetchFrom = second3;
				break;
			case 3:
				db.fetchFrom = second4;
				break;
			case 4:
				db.fetchFrom = second5;
				break;
			case 5:
				db.fetchFrom = second6;
				break;
			case 12: 
				db.fetchFrom = second7;
				break;
			case 11: 
				db.fetchFrom = second11;
				break;
			case 10: 
				db.fetchFrom = second10;
				break;
			case 9: 
				db.fetchFrom = second9;
				break;
			default:
				db.fetchFrom = second8;
			}
			pool.execute(db);
		}
		System.out.println("shit");
		while(Checker.go){
			try {
				Thread.sleep(5000);
				System.out.println(System.currentTimeMillis() - Checker.start);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	