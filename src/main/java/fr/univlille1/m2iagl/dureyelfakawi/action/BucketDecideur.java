package fr.univlille1.m2iagl.dureyelfakawi.action;

import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Model;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class BucketDecideur {

	
	private Model model;
	
	public BucketDecideur(Model model){
		this.model = model;
	}
	
	public ValuesDecided decide(Map<Integer, Stacktrace> toBeAttributed){
		ValuesDecided valuesDecided = new ValuesDecided();
	
		for(Integer key : toBeAttributed.keySet()){
			Stacktrace stacktrace = toBeAttributed.get(key);
		
			int valueDecided = decideStacktrace(stacktrace);
			valuesDecided.put(key, valueDecided);
		}
		
		return valuesDecided;
	}
	
	public int decideStacktrace(Stacktrace stacktrace){
		
		Set<Integer> keys = model.keySet();
		
	//	Map<Integer, Integer>
		
		return 4;
	}
	
	public int getStacktracePointsAccordingToBucket(Stacktrace toBeFitted, Bucket bucket){
		Set<Integer> keys = bucket.keySet();
		for(int key : keys){
			Stacktrace stacktrace = bucket.get(key);
			
			
		}
				
		return 0;
		
	}
}
