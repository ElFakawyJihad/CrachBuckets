package fr.univlille1.m2iagl.dureyelfakawi.model.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValuesDecided {

	private Map<Integer, Integer> map;

	public ValuesDecided(){
		this.map = new HashMap<>();
	}
	
	public void put(int key, int bucketKey){
		map.put(key, bucketKey);
	}
	
	public Set<Integer> keySet(){
		return map.keySet();
	}
	
	public int get(int key){
		return map.get(key);
	}
}
