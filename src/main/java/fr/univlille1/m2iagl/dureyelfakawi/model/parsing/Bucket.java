package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bucket {
	
	private Map<Integer, Stacktrace> stacktraces;
	
	public Bucket(){
		this.stacktraces = new HashMap<>();
	}
	
	public void addStacktrace(int num, Stacktrace stacktrace){
		this.stacktraces.put(num, stacktrace);
		
	}
	
	public Set<Integer> keySet(){
		return stacktraces.keySet();
	}
	
	public Stacktrace get(int num){
		return stacktraces.get(num);
	}

}
