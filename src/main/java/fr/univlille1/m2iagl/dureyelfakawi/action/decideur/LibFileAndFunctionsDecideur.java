package fr.univlille1.m2iagl.dureyelfakawi.action.decideur;

import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.action.Helper;
import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class LibFileAndFunctionsDecideur extends Decideur{

	public LibFileAndFunctionsDecideur(Map<String, Bucket> buckets) {
		super(buckets);
	}

	@Override
	public ValuesDecided decide(Map<Integer, Stacktrace> toBeAttributed) {
		int i=0;
		for(Integer key : toBeAttributed.keySet()){
			Stacktrace stacktrace = toBeAttributed.get(key);

			String valueDecided = decideStacktrace(stacktrace);
			valuesDecided.put(key, valueDecided);
			//return valuesDecided;
		}

		return valuesDecided;
	}

	@Override
	public String decideStacktrace(Stacktrace stacktrace) {
		Map<String, Double> values = new HashMap<String, Double>();

		for(String bucketKey : buckets.keySet()){
			Bucket bucket = buckets.get(bucketKey);
			values.put(bucketKey, getStacktracePointsAccordingToBucket(stacktrace, bucket));
		}
		return Helper.getBestBucket(values);
	}
	
	public double getStacktracePointsAccordingToBucket(Stacktrace toBeAttributed, Bucket bucket){

		int points = 0;
		for(String key : bucket.keySet()){
			Stacktrace stacktraceFromBucket = bucket.get(key);

			points += getPointsBetweenTwoStacktraces(stacktraceFromBucket, toBeAttributed);
		}

		return points/(bucket.keySet().size()+1);
	}

	public double getPointsBetweenTwoStacktraces(Stacktrace stacktraceFromBucket, Stacktrace stacktraceToBeAttributed){

		int points = 0;

		for(String lib : stacktraceToBeAttributed.libSet()){
			if(stacktraceFromBucket.libSet().contains(lib)){
				points += 1;
				
				for(String file : stacktraceToBeAttributed.getFilesAccordingToLib(lib)){
					if(stacktraceFromBucket.getFilesAccordingToLib(lib).contains(file)){
						points += 5;
						
						for(String function : stacktraceToBeAttributed.getFunctionsAccordingToLibAndFile(lib, file)){
							if(stacktraceFromBucket.getFunctionsAccordingToLibAndFile(lib, file).contains(function)){
								points += 25;
							}
						}
					}
				}
			}
		}

		return points;
	}

}
