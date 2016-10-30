package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

import java.util.List;

public class Method {

	private String name;

	private List<Parameter> parameters;

	public Method(String name, List<Parameter> parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", parameters=" + parameters + "]";
	}
	
	public String getName() {
		return name;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}


}
