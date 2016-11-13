package fr.univlille1.m2iagl.dureyelfakawi.action.decideur;

import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.action.Helper;
import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class SumPointsDecideur extends Decideur {


	public SumPointsDecideur(Map<String, Bucket> buckets) {
		super(buckets);
	}

	public ValuesDecided decide(Map<Integer, Stacktrace> toBeAttributed){

		for(Integer key : toBeAttributed.keySet()){
			Stacktrace stacktrace = toBeAttributed.get(key);

			String valueDecided = decideStacktrace(stacktrace);
			valuesDecided.put(key, valueDecided);
		}

		return valuesDecided;
	}

	public String decideStacktrace(Stacktrace stacktrace){

		Map<String, Double> values = new HashMap<String, Double>();

		for(String bucketKey : buckets.keySet()){
			Bucket bucket = buckets.get(bucketKey);
			values.put(bucketKey, getStacktracePointsAccordingToBucket(stacktrace, bucket));
		}
		return Helper.getBestBucket(values);
	}

	private double getStacktracePointsAccordingToBucket(Stacktrace toBeAttributed, Bucket bucket){

		int points = 0;
		for(String key : bucket.keySet()){
			Stacktrace stacktraceFromBucket = bucket.get(key);

			points += getPointsBetweenTwoStacktraces(stacktraceFromBucket, toBeAttributed);
		}

		return points;
	}

	private double getPointsBetweenTwoStacktraces(Stacktrace stacktraceFromBucket, Stacktrace stacktraceToBeAttributed){

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

	private double getPointsBetweenTwoCouches(Couche coucheFromBucket, Couche coucheToBeAttributed){
		int points = 0;

		/* Similitude par ligne */
		int lineFromBucket = coucheFromBucket.getLine(),
				lineFromToBeAttributed = coucheToBeAttributed.getLine();

		points += Helper.getPointsFromLineSimilitude(coucheFromBucket.getLine(), coucheToBeAttributed.getLine());


		String libPathFromBucket = coucheFromBucket.getLibPath(),
				libPathFromToBeAttributed = coucheToBeAttributed.getLibPath();

		String evalFromBucket, evalFromToBeAttributed;

		if(libPathFromBucket != null &&
				libPathFromToBeAttributed != null ){
			evalFromBucket = Helper.removeVersionNumber(libPathFromBucket);
			evalFromToBeAttributed = Helper.removeVersionNumber(libPathFromToBeAttributed);
			
		} else if(libPathFromBucket != null &&
				coucheToBeAttributed.getFilePath() != null && coucheToBeAttributed.getFilePath().toString() != null){
			evalFromBucket = Helper.removeVersionNumber(libPathFromBucket);
			evalFromToBeAttributed = coucheToBeAttributed.getFilePath().toString();

		} else if(coucheFromBucket.getFilePath() != null && coucheFromBucket.getFilePath().getName() != null &&
				coucheToBeAttributed.getFilePath() != null && coucheToBeAttributed.getFilePath().toString() != null){
			evalFromBucket = coucheFromBucket.getFilePath().getName();
			evalFromToBeAttributed = coucheToBeAttributed.getFilePath().toString();
			
		} else if(coucheFromBucket.getFilePath() != null && coucheFromBucket.getFilePath().getName() != null &&
				libPathFromToBeAttributed != null){
			evalFromBucket = coucheFromBucket.getFilePath().getName();
			evalFromToBeAttributed = Helper.removeVersionNumber(libPathFromToBeAttributed);

		} else {
			evalFromBucket = null;
			evalFromToBeAttributed = null;
		}

		points += Helper.getPointsFromNameSimilitude(evalFromBucket, evalFromToBeAttributed);
		points += Helper.getPointsFromCombinatedNameAndLineSimilitude(evalFromBucket, evalFromToBeAttributed, lineFromBucket, lineFromToBeAttributed);

		/* Similitude par methode */
		Method methodFromBucket = coucheFromBucket.getMethod(),
				methodFromToBeAttributed = coucheToBeAttributed.getMethod();

		points += getPointsBetweenTwoMethods(methodFromBucket, methodFromToBeAttributed);			

		return points;

	}

	private double getPointsBetweenTwoMethods(Method methodFromBucket, Method methodFromToBeAttributed){

		if(methodFromBucket == null || methodFromToBeAttributed == null)
			return 0;

		String methodNameFromBucket = methodFromBucket.getName(),
				methodNameFromToBeAttributed = methodFromToBeAttributed.getName();

		return Helper.getPointsFromNameSimilitude(methodNameFromBucket, methodNameFromToBeAttributed);

	}
}
