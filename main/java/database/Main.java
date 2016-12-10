package database;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	static ExecutorService pool;

	public static void main(String[] args) throws SQLException {
		Database.url = args[1];
		Database.url = args[2];
		Database.url = args[3];

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
		ConcurrentLinkedQueue<String> second13 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second14 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second15 = new ConcurrentLinkedQueue<String>();
		ConcurrentLinkedQueue<String> second16 = new ConcurrentLinkedQueue<String>();

		
		pool  = Executors.newFixedThreadPool(500);

		CircularList<String> list = new CircularList<String>();
		CircularList<String> list2 = new CircularList<String>();
		CircularList<String> list3 = new CircularList<String>();
		CircularList<String> list4 = new CircularList<String>();

		FileReader reader = new FileReader(list, args[0],list2, list3,list4);
		new Thread(reader).start();
		int smth =1;
		for (int i = 0; i < smth; i++) {
			Checker c = new Checker(list);
			if(i%4==0){
				c.list = list;
			}else if(i%3==1){
				c.list = list2;
			}else if(i%3==3){
				c.list = list3;
			}else{
				c.list = list4;
			}
			int re = i%smth;
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
			case 6:
				c.destination = second7;
				break;

			default:
				c.destination = second2;
			}
			pool.execute(c);
		}
		
		for(int i = 0; i<  smth*3; i++){
			Database db = new Database();
			int re = i%smth;
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
			case 6: 
				db.fetchFrom = second5;
				break;
			default:
			}
			pool.execute(db);
		}
		System.out.println("shit");
		while(Checker.go){
			try {
				Thread.sleep(60000);
				System.out.println(System.currentTimeMillis() - Checker.start);
				System.out.println(list.size() + " " + list2.size()+ " "+list3.size()+" "+ list4.size());

				System.out.println(second1.size()+" "+second2.size()+" "+second3.size()+" "+second4.size()+" "+second5.size()+" "+second6.size()+" "+second7.size()+" "+second8.size()+" "+second9.size()+" "+
						second10.size()+" "+second11.size()+" "+second12.size()+" "+second13.size()+" "+second14.size()+" "+second15.size()+" "+second16.size()+" ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
	