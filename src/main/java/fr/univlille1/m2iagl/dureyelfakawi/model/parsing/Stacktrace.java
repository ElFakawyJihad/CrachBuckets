package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Stacktrace {
	
	private Map<Integer, Couche> couches;
	
	public Stacktrace(){
		this.couches = new HashMap<Integer,Couche>();
	}
	
	public void put(int num, Couche couche){
		couches.put(num, couche);
	}
	
	public Set<Integer> keySet(){
		return couches.keySet();
	}
	
	public Couche getCouche(int num){
		return couches.get(num);
	}

}
