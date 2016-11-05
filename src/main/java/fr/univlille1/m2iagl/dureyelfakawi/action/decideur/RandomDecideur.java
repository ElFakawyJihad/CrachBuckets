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
		
		Random random = new Random();
		
		List<String> bucketsKey = new ArrayList<>(buckets.keySet());
		
		for(int stacktraceKey : toBeAttributed.keySet()){
			valuesDecided.put(stacktraceKey, bucketsKey.get(random.nextInt(bucketsKey.size())));
		}
		
		return valuesDecided;
	}

}
