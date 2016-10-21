package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.Similarities;

public class Model {
	
	public static Model currentModel;
	
	private Map<Integer, Bucket> buckets;
	
	private Similarities similarities;
	
	public Model(){
		this.buckets = new HashMap<>();
	}
	
	public void setSimilarities(Similarities similarities){
		// Pour plus tard
		this.similarities = similarities;
	}
	
	public void addBucket(int num, Bucket bucket){
		buckets.put(num, bucket);
	}
	
	public Set<Integer> keySet(){
		return buckets.keySet();
	}
	
	public Bucket get(int num){
		return buckets.get(num);
	}

}
