package fr.univlille1.m2iagl.dureyelfakawi.action;

import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Model;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class BucketDecideur {

	
	private Model model;
	private ValuesDecided valuesDecided;
	
	public BucketDecideur(Model model){
		this.model = model;
		this.valuesDecided = new ValuesDecided();
	}
	
	public ValuesDecided decide(Map<Integer, Stacktrace> toBeAttributed){
	
		for(Integer key : toBeAttributed.keySet()){
			Stacktrace stacktrace = toBeAttributed.get(key);
		
			int valueDecided = decideStacktrace(stacktrace);
			valuesDecided.put(key, valueDecided);
		}
		
		return valuesDecided;
	}
	
	public int decideStacktrace(Stacktrace stacktrace){
		
		Map<Integer, Integer> values = new HashMap<Integer, Integer>();
		
		for(int bucketKey : model.bucketKeySet()){
			Bucket bucket = model.getBucket(bucketKey);
			values.put(bucketKey, getStacktracePointsAccordingToBucket(stacktrace, bucket));
		}
		
		
		return Helper.getBestBucket(values);
	}
	
	public int getStacktracePointsAccordingToBucket(Stacktrace toBeAttributed, Bucket bucket){
		
		int points = 0;
		for(int key : bucket.keySet()){
			Stacktrace stacktraceFromBucket = bucket.get(key);
			
			points += getPointsBetweenTwoStacktraces(stacktraceFromBucket, toBeAttributed);
		}
				
		return points;
	}
	
	public int getPointsBetweenTwoStacktraces(Stacktrace stacktraceFromBucket, Stacktrace stacktraceToBeAttributed){
		
		int points = 0;
		
		for(int coucheIdFromBucket : stacktraceFromBucket.keySet()){
			Couche coucheFromBucket = stacktraceFromBucket.getCouche(coucheIdFromBucket);
			
			for(int coucheIdFromToBeAttributed : stacktraceToBeAttributed.keySet()){
				Couche coucheFromToBeAttributed = stacktraceToBeAttributed.getCouche(coucheIdFromToBeAttributed);
				
				points += getPointsBetweenTwoCouches(coucheFromBucket, coucheFromToBeAttributed);
			}
		}
		
		return points;
	}
	
	public int getPointsBetweenTwoCouches(Couche coucheFromBucket, Couche coucheToBeAttributed){
		int points = 0;
		
		/*
		String filePathFromBucket = coucheFromBucket.getFilePath(),
				filePathFromToBeAttributed = coucheToBeAttributed.getFilePath();
		
		points += Helper.getPointsFromNameSimilitude(filePathFromBucket, filePathFromToBeAttributed);
		*/
		String libPathFromBucket = coucheFromBucket.getLibPath(),
				libPathFromToBeAttributed = coucheToBeAttributed.getLibPath();
		
		points += Helper.getPointsFromNameSimilitude(libPathFromBucket, libPathFromToBeAttributed);
		
		Method methodFromBucket = coucheFromBucket.getMethod(),
				methodFromToBeAttributed = coucheToBeAttributed.getMethod();
		
		points += getPointsBetweenTwoMethods(methodFromBucket, methodFromToBeAttributed);			
		
		
		return points;
		
	}
	
	public int getPointsBetweenTwoMethods(Method methodFromBucket, Method methodFromToBeAttributed){
		
		
		int points = 0;
		
		String methodNameFromBucket = methodFromBucket.getName(),
				methodNameFromToBeAttributed = methodFromToBeAttributed.getName();
		
		points += Helper.getPointsFromNameSimilitude(methodNameFromBucket, methodNameFromToBeAttributed);
		
		return points;
	}
}
