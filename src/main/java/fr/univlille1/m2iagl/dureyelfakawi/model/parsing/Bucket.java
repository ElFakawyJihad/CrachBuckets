package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bucket {
	
	private int stacktraceNb = 0;
	
	private int bucketNb;
	
	private Map<String, Stacktrace> stacktraces;
	
	public Bucket(){
		this.stacktraces = new HashMap<String, Stacktrace>();
	}
	
	public Bucket(int bucketNb){
		this();
		setBucketNb(bucketNb);
	}
	
	public void setBucketNb(int bucketNb){
		this.bucketNb = bucketNb;
	}
	
	public void addStacktrace(Stacktrace stacktrace){
		this.stacktraces.put((stacktraceNb++) + "", stacktrace);
		
	}
	
	public Stacktrace remove(String i){
		return stacktraces.remove(i);
	}
	
	public void putStacktrace(String name, Stacktrace stacktrace){
		this.stacktraces.put(name, stacktrace);
	}
	
	public Set<String> keySet(){
		return stacktraces.keySet();
	}
	
	public Stacktrace get(String num){
		return stacktraces.get(num);
	}

}
