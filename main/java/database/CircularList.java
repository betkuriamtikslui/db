package database;

import java.util.LinkedList;

public class CircularList<T> {
	private LinkedList<T> list;
	public CircularList(){
		list = new LinkedList<T>();
	}
	
	public synchronized void add(T value){
		list.add(value);
		//System.out.println(list.size());
	}
	public synchronized T fetch(){
		if(list.size() == 0){
			return null;
		}
		return list.pop();
	}
	public synchronized int size(){
		return list.size();
	}
	
	
}
