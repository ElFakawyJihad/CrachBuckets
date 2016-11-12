package fr.univlille1.m2iagl.dureyelfakawi.model.parsing;

public class Parameter {

	private String name;

	public Parameter(String name) {
		this.name = name;
	}


	public String toString() {
		return "Parameter [name=" + name + "]";
	}
	public boolean equals(Object o){
		if (o instanceof Parameter){
			Parameter parameter=(Parameter) o;
			return this.name.equals(parameter.name);
		}
		return false;
	}
}
