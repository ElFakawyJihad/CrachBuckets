package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

public class Couche {

	private String filePath;
	
	private String libPath;
	
	private Method method;

	private int numCouche;
	
	private int line;
	
	public Couche(String filePath, String libPath, Method method, int numCouche, int line){
		this.filePath = filePath;
		this.libPath = libPath;
		this.method = method;
		this.numCouche = numCouche;
		this.line = line;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getLibPath() {
		return libPath;
	}

	public Method getMethod() {
		return method;
	}

	public int getNumCouche() {
		return numCouche;
	}

	public int getLine() {
		return line;
	}
	
	
	
	
	
}
