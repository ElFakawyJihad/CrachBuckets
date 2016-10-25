/**
 * Classe d'exception dans le cas ou tous les buckets ont été consommés.
 */
package fr.univlille1.m2iagl.dureyelfakawi.read.exception;

public class EndBucketsException extends Exception {
	public EndBucketsException(){
		System.out.println("Il n'y a plus de buckets disponibles");
	}
}
