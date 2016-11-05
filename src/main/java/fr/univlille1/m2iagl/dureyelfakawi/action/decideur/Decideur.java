package fr.univlille1.m2iagl.dureyelfakawi.action.decideur;

import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public abstract class Decideur {
	
	protected Map<String, Bucket> buckets;
	protected ValuesDecided valuesDecided;
	
	public Decideur(Map<String, Bucket> buckets){
		this.buckets = buckets;
		this.valuesDecided = new ValuesDecided();
	}
	
	public abstract ValuesDecided decide(Map<Integer, Stacktrace> toBeAttributed);

}
