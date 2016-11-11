package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.LibFileAndFunctionsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.AnalyzeStacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.StacktraceBuilder;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NonExistantFolder;

public class App3 {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NonExistantFolder 
	 * @throws EndBucketsException 
	 * @throws EndElementsInBucketsException 
	 * @throws NoStackTraceFileException 
	 * @throws LengthFileInFolderException 
	 */
	public static void main(String[] args) throws IOException, NonExistantFolder, EndBucketsException, LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException {

		File file1 = new File("nautilus-training/000004623/0000208600/Stacktrace.txt");
		File file2 = new File("nautilus-training/000004623/0000261407/Stacktrace.txt");
		
		StacktraceBuilder stacktraceBuilder1 = new StacktraceBuilder(new AnalyzeStacktrace(file1));

		Stacktrace stacktrace1 = stacktraceBuilder1.build();
		
		StacktraceBuilder stacktraceBuilder2 = new StacktraceBuilder(new AnalyzeStacktrace(file2));

		Stacktrace stacktrace2 = stacktraceBuilder2.build();
		
		System.out.println(new LibFileAndFunctionsDecideur(new HashMap<String, Bucket>()).getPointsBetweenTwoStacktraces(stacktrace1, stacktrace2));
		
	}

}
