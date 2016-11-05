package fr.univlille1.m2iagl.dureyelfakawi.model.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValuesDecided {

	private Map<Integer, String> map;

	public ValuesDecided(){
		this.map = new HashMap<>();
	}
	
	public void put(int key, String bucketKey){
		map.put(key, bucketKey);
	}
	
	public Set<Integer> keySet(){
		return map.keySet();
	}
	
	public String get(int key){
		return map.get(key);
	}
}
