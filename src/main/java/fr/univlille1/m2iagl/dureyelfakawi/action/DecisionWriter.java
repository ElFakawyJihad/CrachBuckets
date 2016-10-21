package fr.univlille1.m2iagl.dureyelfakawi.action;

import java.io.PrintWriter;

import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;

public class DecisionWriter {

	PrintWriter printWriter;
	
	public DecisionWriter(PrintWriter printWriter){
		this.printWriter = printWriter;
	}
	
	
	public void writeValuesDecided(ValuesDecided valuesDecided){
		for(Integer key : valuesDecided.keySet()){
			int valueDecided = valuesDecided.get(key);
			
			String string = key + " -> " + valueDecided + "\n";
			
			printWriter.write(string);
		}
	}
}
