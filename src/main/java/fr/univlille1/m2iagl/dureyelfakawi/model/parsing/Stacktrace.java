package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.dureyelfakawi.action.Helper;

public class Stacktrace {

	private Map<Integer, Couche> couches;

	private Map<String, Map<String, Map<String, Integer>>> folders;

	public Stacktrace(){
		this.couches = new HashMap<Integer,Couche>();
		folders = new HashMap<String, Map<String, Map<String, Integer>>>();
	}

	public void put(int num, Couche couche){
		couches.put(num, couche);

		parseLib(couche);
		parseFile(couche);
	}
	
	public void parseLib(Couche couche){
		if(couche.getLibPath() != null){
			String folder = Helper.getFolderName(couche.getLibPath()),
					file = Helper.getFolderName(couche.getLibPath()),
					function = couche.getMethod() != null ? couche.getMethod().toString() : null;

			Map<String, Map<String, Integer>> files;
			
			if(folders.containsKey(folder)){
				files = folders.get(folder);
			} else {
				files = new HashMap<String, Map<String, Integer>>();
				folders.put(folder,  files);
			}
			
			
			Map<String, Integer> functions;
			
			if(files.keySet().contains(file)){
				functions = files.get(file);
			} else {
				functions = new HashMap<String, Integer>();
				files.put(file, functions);
			}
			
			if(function != null){
			
				if(functions.containsKey(function)){
					functions.put(function, functions.get(function) + 1);
				} else {
					functions.put(function, 1);
				}
			}
			
		}
	}	
	
	public void parseFile(Couche couche){
		if(couche.getFilePath() != null){
			String folder = Helper.getFolderName(couche.getFilePath().getName()),
					file = Helper.getFilename(couche.getFilePath().getName()),
					function = couche.getMethod() != null ? couche.getMethod().getName() : null;
					
			Map<String, Map<String, Integer>> files;
			
			if(folders.containsKey(folder)){
				files = folders.get(folder);
			} else {
				files = new HashMap<String, Map<String, Integer>>();
				folders.put(folder,  files);
			}
			
			
			Map<String, Integer> functions;
			
			if(files.keySet().contains(file)){
				functions = files.get(file);
			} else {
				functions = new HashMap<String, Integer>();
			}
			
			if(function != null){
			
				if(functions.containsKey(function)){
					functions.put(function, functions.get(function) + 1);
				} else {
					functions.put(function, 1);
				}
			}
			
		}
	}	
	
	public Integer nbTimesFunction(String lib, String file, String function){
		return folders.get(lib).get(file).get(function);
	}

	public Set<String> libSet(){
		return folders.keySet();
	}

	public Set<String> getFilesAccordingToLib(String lib){
		return folders.get(lib).keySet();
	}
	
	public Set<String> getFunctionsAccordingToLibAndFile(String lib, String file){
		return folders.get(lib).get(file).keySet();
		
	}

	public Set<Integer> keySet(){
		return couches.keySet();
	}

	public Couche getCouche(int num){
		return couches.get(num);
	}

}
