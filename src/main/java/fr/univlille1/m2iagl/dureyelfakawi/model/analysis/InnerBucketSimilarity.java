package fr.univlille1.m2iagl.dureyelfakawi.model.analysis;

import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;

public class InnerBucketSimilarity {
	
	private static final String filePath = "FILEPATH";
	private static final String libPath = "LIBPATH";
	private static final String line = "LINE";

	
	private Bucket bucket;
	
	Map<String, Integer> similarities;
	
	
	public InnerBucketSimilarity(Bucket bucket){
		this.bucket = bucket;
		similarities = new HashMap<String, Integer>();
		fillSimilaritiesKey();
	}
	
	public void computeSimilar(){
		for(String stacktraceKey : bucket.keySet()){
			
		}
	}
	
	public void fillSimilaritiesKey(){ }
}
