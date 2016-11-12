package fr.univlille1.m2iagl.dureyelfakawi.action;

import java.io.PrintWriter;

import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;

public class DecisionWriter {

	PrintWriter printWriter;

	public DecisionWriter(PrintWriter printWriter){
		this.printWriter = printWriter;
	}


	public String writeValuesDecided(ValuesDecided valuesDecided){
		String string = "";

		for(Integer key : valuesDecided.keySet()){
			String valueDecided = valuesDecided.get(key);
			string += key + "  -> " + valueDecided + "\n";
		}

		if(printWriter != null){
			printWriter.write(string);
			printWriter.flush();
		}
		
		return string;
	}
}
