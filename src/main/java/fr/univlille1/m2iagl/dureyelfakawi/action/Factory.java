package fr.univlille1.m2iagl.dureyelfakawi.action;

import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.FilePath;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class Factory {


	public static Bucket createEmptyBucket(){
		return new Bucket();
	}
	
	
	public static Couche createCouche(FilePath filePath, String lib, Method method, int numCouche, int line){
		if(filePath != null && lib == null){
			return Factory.createCoucheWithFilePath(filePath, method, numCouche, line);
		} else if(filePath == null && lib != null){
			return Factory.createCoucheWithLibPath(lib, method, numCouche, line);
		}
	}
	
	private static Couche createCoucheWithFilePath(FilePath filePath, Method method, int numCouche, int line){
		return new Couche(filePath, "", method, numCouche, line);
	}
	
	private static Couche createCoucheWithLibPath(String lib, Method method, int numCouche, int line){
		return new Couche(null,lib,method, numCouche, line);
	}
	
	
	public static Stacktrace createEmptyStacktrace(){
		return new Stacktrace();
	}
}
