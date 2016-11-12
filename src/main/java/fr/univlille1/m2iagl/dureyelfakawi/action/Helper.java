package fr.univlille1.m2iagl.dureyelfakawi.action;

import java.util.Map;

public class Helper {

	public static String removeVersionNumber(String string){
		String[] splited = string.split("/");

		String toBeReturned = "";
		for(int i=0;i<splited.length-1;i++){
			String stringSplited = splited[i];
			toBeReturned += stringSplited.replaceAll("([0-9]|\\.|-)*", "") + "/";
		}

		return toBeReturned + splited[splited.length-1];
	}

	public static String getBestBucket(Map<String, Double> map){

		double maxValue = Integer.MIN_VALUE;
		String keyAssociated = "";
		for(String key : map.keySet()){
			double value = map.get(key);


			if(value > maxValue){
				maxValue = value;
				keyAssociated = key;
			}
		}
		return keyAssociated;
	}

	public static double getPointsFromLineSimilitude(int line1, int line2){
		return line1 == line2 ? 1 : 0;
	}

	public static double getPointsFromNameSimilitude(String name1, String name2){
		//System.out.println("Name1 : " + name1 + ", name2 : " + name2);
		int points = 0;

		if(name1 == null || name2 == null){
			return 0;
		}

		String[] name1Splitted = name1.split("_");
		String[] name2Splitted = name2.split("_");

		for(String name1Part : name1Splitted){
			for(String name2Part : name2Splitted){
				if(name1Part.equals(name2Part)){
					points++;
				}

			}
		}

		return points;
	}

	public static String getFolderName(String path){

		String split = "/";  

		path = path.replace("/build/buildd/", "");
		path = path.replace("/usr/lib/", "");

		
		return path.split(split)[0];
	}

	public static String getFilename(String libPath){

		String split = "/";  

		libPath = libPath.replace("/build/buildd/", "");
		String[] splited = libPath.split(split);

		return splited[splited.length-1].split("\\.")[0];
	}

	public static double getPointsFromCombinatedNameAndLineSimilitude(String name1, String name2, int line1, int line2){
		return name1 != null & name2 != null && name1.equals(name2) && line1 == line2 ? 50 : 0;
	}

}
