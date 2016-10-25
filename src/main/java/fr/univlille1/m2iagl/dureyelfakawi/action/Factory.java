package fr.univlille1.m2iagl.dureyelfakawi.action;

import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.FilePath;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class Factory {

	
	public Couche createCoucheWithFilePath(FilePath filePath,Method method, int numCouche, int line){
		return new Couche(filePath, "", method, numCouche, line);
	}
	
	public Couche createCoucheWithLibPath(FilePath filePath,String lib,Method method, int numCouche, int line){
		return new Couche(null,lib,method, numCouche, line);
	}
	
	
	public Stacktrace createEmptyStacktrace(){
		return new Stacktrace();
	}
}
