package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Parameter;

public class AnalyzeStacktrace {
	private BufferedReader buffered;

	public AnalyzeStacktrace(File stacktrace) throws FileNotFoundException {
		FileReader reader = new FileReader(stacktrace);
		buffered = new BufferedReader(reader);
	}

	public AnalyzeStacktrace() throws FileNotFoundException {
	}

	private int getNumCouche(String line) {
		int debutIndex = line.indexOf('#');
		if (debutIndex != -1) {
			int finIndex = line.indexOf(' ');
			String stringNumCouche = line.substring(debutIndex + 1, finIndex);
			return Integer.parseInt(stringNumCouche);
		}
		return -1;
	}
	
	public Method getMethod(String line){
		int index;
		if ((index=line.indexOf("in"))!=-1){
			String nameMethod=line.substring(index+2);
			int finMethod=nameMethod.indexOf('(');
			String nameMethodWithoutParam=nameMethod.substring(0,finMethod);
			int finParam=nameMethod.indexOf(')');
			String params=nameMethod.substring(finMethod+1,finParam);
			ArrayList<Parameter> parametres = getParametres(params);
			return new Method(nameMethodWithoutParam, parametres);
		}
		return null;
	}
	
	public ArrayList<Parameter> getParametres(String param){
		ArrayList<Parameter> retour=new ArrayList<Parameter>();
		String[] params=param.split(",");
		for (int i=0;i<params.length;i++){
			String nomParam=params[i].split("=")[0];
			retour.add(new Parameter(nomParam.trim()));
		}
		return retour;
	}

	public ArrayList<String> initCouchesList() throws IOException {
		ArrayList<String> couchesListes = new ArrayList<String>();
		String couches = this.initCouches();
		int debut = 0;
		int fin = 0;
		while ((fin = couches.indexOf('#', 1)) != -1) {
			debut = couches.indexOf("#");
			couchesListes.add(couches.substring(debut, fin));
			couches = couches.substring(fin);
		}
		couchesListes.add(couches.substring(debut));
		return couchesListes;
	}

	private String initCouches() throws IOException {
		String line = this.buffered.readLine();
		String actuel = "";
		while ((actuel = this.buffered.readLine()) != null) {
			line = line + actuel + "\n";
		}
		return line;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(new AnalyzeStacktrace()
				.getNumCouche("#0  0xb732c2eb in g_hash_table_foreach_remove_or_steal (hash_table=0x80fed80,"));
	}

}
