package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.LibFileAndFunctionsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.AnalyzeStacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.BucketBuilder;
import fr.univlille1.m2iagl.dureyelfakawi.read.StacktraceBuilder;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;

public class App4 {
	
	public static void main(String [] args) throws IOException, EndBucketsException, LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException{
		File file1 = new File("nautilus-training/000000977/0000211263/Stacktrace.txt");
		File file2 = new File("nautilus-training/000000977");
		
		File file3 = new File("nautilus-training/000022756");

		
		StacktraceBuilder stacktraceBuilder1 = new StacktraceBuilder(new AnalyzeStacktrace(file1));

		Stacktrace stacktrace1 = stacktraceBuilder1.build();
		
		Bucket rightBucket = new BucketBuilder(file2).build();
		
		Bucket wrongBucket = new BucketBuilder(file3).build();

		
		
		System.out.println(new LibFileAndFunctionsDecideur(new HashMap<String, Bucket>()).getStacktracePointsAccordingToBucket(stacktrace1, rightBucket));
		System.out.println(new LibFileAndFunctionsDecideur(new HashMap<String, Bucket>()).getStacktracePointsAccordingToBucket(stacktrace1, wrongBucket));
		
	}

}
