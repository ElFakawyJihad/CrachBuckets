package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.File;
import java.io.IOException;

import fr.univlille1.m2iagl.dureyelfakawi.action.Factory;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;

public class BucketBuilder {
	
	private File bucketFolder;
	
	public BucketBuilder(File bucketFolder){
		this.bucketFolder = bucketFolder;
	}
	
	public Bucket build() throws EndBucketsException, LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException, IOException{

		String[] bucketPath = bucketFolder.toString().split("/");
		
		int bucketNb = Integer.parseInt(bucketPath[bucketPath.length-1]);
		
		Bucket bucket = Factory.createEmptyBucket(bucketNb);
		StacktracesReader stacktracesReader = new StacktracesReader(bucketFolder);

		while(stacktracesReader.hasNextFile()){
			File stacktraceFile = stacktracesReader.nextFile();

			StacktraceBuilder stacktraceBuilder = new StacktraceBuilder(new AnalyzeStacktrace(stacktraceFile));

			Stacktrace stacktrace = stacktraceBuilder.build();
			
			bucket.addStacktrace(stacktrace);
		}
		
		return bucket;
	}

}
