package database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader implements Runnable {
	private CircularList<String> list;
	private String location;
	private CircularList<String> list2;
	private CircularList<String> list3;
	private CircularList<String> list4;
	public static boolean empty = false;

	public FileReader(CircularList<String> list, String location, CircularList<String> list2,
			CircularList<String> list3, CircularList<String> list4) {
		this.location = location;
		this.list = list;
		this.list2 = list2;
		this.list3 = list3;
		this.list4 = list4;
	}

	public void read(String file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
//				if (list.size() < list2.size() && list.size() < list3.size() && list.size() < list4.size()) {
//					list.add(line);
//
//				} else if (list2.size() < list3.size() && list4.size() > list2.size()) {
//					list2.add(line);
//				} else if (list4.size() > list3.size()) {
//					list3.add(line);
//				} else {
					list.add(line);
					if(list.size() > 100){
						Thread.sleep(400);
					}
//				}
//	
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empty = true;
	}

	public void run() {
		this.read(location);
	}

}
