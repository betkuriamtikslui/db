package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Writer implements Runnable{
	BufferedWriter bw;
	File output;
	static boolean go = true;
	private CircularList<String> fetchFrom;

	public Writer(File output, CircularList<String> list) throws IOException {
		this.output = output;
		bw = new BufferedWriter(new FileWriter(output,true));
		this.fetchFrom = list;
	}

	public void write() {
		String line;
		while (go) {
			try {
				line = fetchFrom.fetch();
				if(line == null){
					throw new Exception("yolo");
				}
				System.out.println(line);
				bw.write(line+",\n");
				bw.flush();
			} catch (Exception e) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					continue;
				}
				if(FileReader.empty && fetchFrom.size() == 0){
					go = false;
				}
				continue;	
			}
		}
		try {
			bw.write(
					"{\"subreddit\":\"IAmA\",\"body\":\"[deleted]\",\"parent_id\":\"t1_c22ssce\",\"ups\":1,\"name\":\"t1_c22x590\",\"author_flair_text\":null,\"link_id\":\"t3_icwk6\",\"archived\":true,\"subreddit_id\":\"t5_2qzb6\",\"score\":1,\"score_hidden\":false,\"retrieved_on\":1427302527,\"author_flair_css_class\":null,\"id\":\"c22x590\",\"author\":\"[deleted]\",\"edited\":false,\"gilded\":0,\"controversiality\":0,\"downs\":0,\"created_utc\":\"1309478768\",\"distinguished\":null}");
			bw.flush();
			bw.write("]");
			bw.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void run() {
		write();
	}

}
