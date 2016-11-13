package fr.univlille1.m2iagl.dureyelfakawi.action.decideur;

import fr.univlille1.m2iagl.dureyelfakawi.action.Helper;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;

public class CouchePointsComputer extends Thread {
	
	private Stacktrace stacktrace;
	private Couche couche;
	
	private int points;
	
	private boolean hasFinished;
	
	public BucketPointsComputer(CoucheStacktrace stacktrace, Couche couche){
		this.stacktrace = stacktrace;
		this.couche = couche;
		hasFinished = false;
	}
	
	
	public void run(){
		//System.out.println("tata");
		points = 0;
		for(int coucheIdFromToBeAttributed : stacktrace.keySet()){
			Couche coucheFromToBeAttributed = stacktrace.getCouche(coucheIdFromToBeAttributed);
			
			points += getPointsBetweenTwoCouches(couche, coucheFromToBeAttributed);
		}
		//System.out.println("toto");
		
		hasFinished = true;
	}
	
	public boolean hasFinished(){
		return hasFinished;
	}
	
	public int getPoints(){
		return points;
	}
	
	private double getPointsBetweenTwoCouches(Couche coucheFromBucket, Couche coucheToBeAttributed){
		int points = 0;

		/* Similitude par ligne */
		int lineFromBucket = coucheFromBucket.getLine(),
				lineFromToBeAttributed = coucheToBeAttributed.getLine();

		points += Helper.getPointsFromLineSimilitude(coucheFromBucket.getLine(), coucheToBeAttributed.getLine());


		String libPathFromBucket = coucheFromBucket.getLibPath(),
				libPathFromToBeAttributed = coucheToBeAttributed.getLibPath();

		String evalFromBucket, evalFromToBeAttributed;

		if(libPathFromBucket != null &&
				libPathFromToBeAttributed != null){
			evalFromBucket = libPathFromBucket;
			evalFromToBeAttributed = libPathFromToBeAttributed;
			
		} else if(libPathFromBucket != null &&
				coucheToBeAttributed.getFilePath() != null && coucheToBeAttributed.getFilePath().toString() != null){
			evalFromBucket = libPathFromBucket;
			evalFromToBeAttributed = coucheToBeAttributed.getFilePath().toString();

		} else if(coucheFromBucket.getFilePath() != null && coucheFromBucket.getFilePath().getName() != null &&
				coucheToBeAttributed.getFilePath() != null && coucheToBeAttributed.getFilePath().toString() != null){
			evalFromBucket = coucheFromBucket.getFilePath().getName();
			evalFromToBeAttributed = coucheToBeAttributed.getFilePath().toString();
			
		} else if(coucheFromBucket.getFilePath() != null && coucheFromBucket.getFilePath().getName() != null &&
				libPathFromToBeAttributed != null){
			evalFromBucket = coucheFromBucket.getFilePath().getName();
			evalFromToBeAttributed = libPathFromToBeAttributed;

		} else {
			evalFromBucket = null;
			evalFromToBeAttributed = null;
		}

		points += Helper.getPointsFromNameSimilitude(evalFromBucket, evalFromToBeAttributed);
		points += Helper.getPointsFromCombinatedNameAndLineSimilitude(evalFromBucket, evalFromToBeAttributed, lineFromBucket, lineFromToBeAttributed);

		/* Similitude par methode */
		Method methodFromBucket = coucheFromBucket.getMethod(),
				methodFromToBeAttributed = coucheToBeAttributed.getMethod();

		points += getPointsBetweenTwoMethods(methodFromBucket, methodFromToBeAttributed);			

		return points;

	}
	
	private double getPointsBetweenTwoMethods(Method methodFromBucket, Method methodFromToBeAttributed){

		if(methodFromBucket == null || methodFromToBeAttributed == null)
			return 0;

		String methodNameFromBucket = methodFromBucket.getName(),
				methodNameFromToBeAttributed = methodFromToBeAttributed.getName();

		return Helper.getPointsFromNameSimilitude(methodNameFromBucket, methodNameFromToBeAttributed);

	}
	
	
}
