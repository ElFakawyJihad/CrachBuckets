package fr.univlille1.m2iagl.dureyelfakawi.action;

import java.util.Map;

public class Helper {
	
	public static int getBestBucket(Map<Integer, Integer> map){
		
		int maxValue = Integer.MIN_VALUE;
		int keyAssociated = -1;
		for(Integer key : map.keySet()){
			int value = map.get(key);
			if(value > maxValue){
				keyAssociated = key;
			}
		}
		
		return keyAssociated;
		
	}
	
	public static int getPointsFromNameSimilitude(String name1, String name2){
		return name1.equals(name2) ? 1 : 0;
	}

}
