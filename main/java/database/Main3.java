package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main3 {

	static ExecutorService pool;

	public static void main(String[] args) throws SQLException, IOException {

		pool = Executors.newFixedThreadPool(500);
		CircularList<String> list = new CircularList<String>();
		FileReader fr = new FileReader(list, "e:\\database\\dbs\\2.json");
		pool.execute(fr);
		File output = new File("E:\\12.json");
		BufferedWriter bw = new BufferedWriter(new FileWriter(output, false));
		bw.write("[");
		bw.flush();
		pool.execute(new Writer(output, list));

		while (!fr.empty && !Writer.go) {
			try {
				System.out.println(System.currentTimeMillis());
				System.out.println(" " + list.size());
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				continue;
			}
		}		
		System.out.println("finished");
	}
}
