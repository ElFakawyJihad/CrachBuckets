package fr.univlille1.m2iagl.dureyelfakawi.action.decideur;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class RandomDecideur extends Decideur{

	public RandomDecideur(Map<String, Bucket> buckets) {
		super(buckets);
	}

	@Override
	public ValuesDecided decide(Map<Integer, Stacktrace> toBeAttributed) {
		
		for(int stacktraceKey : toBeAttributed.keySet()){
			valuesDecided.put(stacktraceKey, decideStacktrace(toBeAttributed.get(stacktraceKey)));
		}
		
		return valuesDecided;
	}

	@Override
	public String decideStacktrace(Stacktrace stacktrace) {
		
		Random random = new Random();
		List<String> bucketsKey = new ArrayList<String>(buckets.keySet());
		
		return bucketsKey.get(random.nextInt(bucketsKey.size()));
	}
}
