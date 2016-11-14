package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model {
	
	public static Model currentModel;
	
	private Map<Integer, Bucket> buckets;
	
	
	public Model(){
		this.buckets = new HashMap<Integer, Bucket>();
	}
	
	public void addBucket(int num, Bucket bucket){
		buckets.put(num, bucket);
	}
	
	public Set<Integer> bucketKeySet(){
		return buckets.keySet();
	}
	
	public Bucket getBucket(int num){
		return buckets.get(num);
	}

}
