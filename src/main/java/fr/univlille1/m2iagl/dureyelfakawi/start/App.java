package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import fr.univlille1.m2iagl.dureyelfakawi.action.Factory;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.read.AnalyzeStacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.ReadBuckets;
import fr.univlille1.m2iagl.dureyelfakawi.read.ReadStacktraces;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) throws EndBucketsException, LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException, IOException
	{
		
		Factory factory = new Factory();
		
		ReadBuckets bucketsReader = new ReadBuckets();
		bucketsReader.openFolder();

		while(bucketsReader.hasNextFolder()){
			
			Bucket bucket = factory.createEmptyBucket();

			File bucketFolder = bucketsReader.nextFolder();


			ReadStacktraces stacktracesReader = new ReadStacktraces(bucketFolder);

			while(stacktracesReader.hasNextFile()){
				File stacktrace = stacktracesReader.nextFile();

				AnalyzeStacktrace analyze = new AnalyzeStacktrace(stacktrace);
				
				List<String> couchesString = analyze.initCouchesList();
				
				for(String coucheString : couchesString){
					String libPath = analyze.getLibFrom(coucheString);

					int numLine = analyze.getLigne(coucheString);
					
				}
				
			}
			
		}

	}
}
