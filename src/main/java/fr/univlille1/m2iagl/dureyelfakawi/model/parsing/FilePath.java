package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.List;

public class FilePath {

	private String name;

	private List<Parameter> parameters;

	public FilePath(String name, List<Parameter> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	public String getName(){
		return name;
	}
	public boolean equals(Object o){
		if (o instanceof FilePath){
			FilePath path=(FilePath) o;
			return (this.name.equals(path.name) && this.parameters.equals(path.parameters));
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "FilePath [name=" + name + ", parameters=" + parameters + "]";
	}

}
