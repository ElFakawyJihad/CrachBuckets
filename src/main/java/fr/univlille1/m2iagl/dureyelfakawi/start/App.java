package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import fr.univlille1.m2iagl.dureyelfakawi.action.Factory;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.read.AnalyzeStacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.BucketBuilder;
import fr.univlille1.m2iagl.dureyelfakawi.read.BucketsReader;
import fr.univlille1.m2iagl.dureyelfakawi.read.StacktracesReader;
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
		
		BucketsReader bucketsReader = new BucketsReader();
		bucketsReader.openFolder();
		int nbBucketsTraiter=0;
		while(bucketsReader.hasNextFolder()){
			BucketBuilder bucketBuilder = new BucketBuilder(bucketsReader.nextFolder());
			System.out.println("Bucket file : " + bucketsReader.getName());
			Bucket bucket = bucketBuilder.build();
			System.out.println("Fin du traitement de la buckets :"+bucketsReader.getName());
			nbBucketsTraiter++;
		}
		System.out.println("Fin du programme, le nombre de buckets traiter est de:"+nbBucketsTraiter);
		
	}
}
