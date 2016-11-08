package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bucket {
	
	private int stacktraceNb = 0;
	
	private int bucketNb;
	
	private Map<Integer, Stacktrace> stacktraces;
	
	public Bucket(){
		this.stacktraces = new HashMap<Integer, Stacktrace>();
	}
	
	public Bucket(int bucketNb){
		this.stacktraces = new HashMap<Integer, Stacktrace>();
		setBucketNb(bucketNb);
	}
	
	public void setBucketNb(int bucketNb){
		this.bucketNb = bucketNb;
	}
	
	public void addStacktrace(Stacktrace stacktrace){
		this.stacktraces.put(stacktraceNb++, stacktrace);
		
	}
	
	public Stacktrace remove(int i){
		return stacktraces.remove(i);
	}
	
	public void putStacktrace(int i, Stacktrace stacktrace){
		this.stacktraces.put(i, stacktrace);
	}
	
	public Set<Integer> keySet(){
		return stacktraces.keySet();
	}
	
	public Stacktrace get(int num){
		return stacktraces.get(num);
	}

}
