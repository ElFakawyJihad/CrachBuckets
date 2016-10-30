package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.IOException;
import java.util.ArrayList;

import fr.univlille1.m2iagl.dureyelfakawi.action.Factory;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class StacktraceBuilder {
	
	private AnalyzeStacktrace analyzeStacktrace;
	
	public StacktraceBuilder(AnalyzeStacktrace analyzeStacktrace){
		this.analyzeStacktrace = analyzeStacktrace;
	}
	
	public Stacktrace build() throws IOException{
		
		ArrayList<String> couchesString = analyzeStacktrace.initCouchesList();
		
		Stacktrace stacktrace = Factory.createEmptyStacktrace();
		
		for(String coucheString : couchesString){
			
			Couche couche = analyzeStacktrace.buildCouche(coucheString);
			
			int numCouche = analyzeStacktrace.getNumCouche(coucheString);
			
			stacktrace.put(numCouche, couche);
		}
		
		return stacktrace;
	}

}
