package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.Decideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.SumPointsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.BucketBuilder;
import fr.univlille1.m2iagl.dureyelfakawi.read.BucketsReader;
import fr.univlille1.m2iagl.dureyelfakawi.read.Constantes;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NonExistantFolder;

public class App2 {

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
		
		/* Création des buckets */
		BucketsReader bucketsReader = new BucketsReader();
		bucketsReader.openFolder(Constantes.LOCATION_BUCKETS);
		
		Map<String, Bucket> buckets = new HashMap<String, Bucket>();
		while(bucketsReader.hasNextFolder()){
			BucketBuilder bucketBuilder = new BucketBuilder(bucketsReader.nextFolder());
			buckets.put(bucketsReader.getName(), bucketBuilder.build());
		}
		
		/* Fin de création des buckets */
		
		int nbSucces =0, nbEchecs = 0, nbTotal = 0;
		
		int i=0;
		
		for(String bucketKey : buckets.keySet()){
			Bucket bucket = buckets.get(bucketKey);
			
			List<Integer> stracktracesId = new ArrayList<>(bucket.keySet());
			
			for(int stacktraceId : stracktracesId){
			
				Stacktrace stacktrace = bucket.remove(stacktraceId);
				
				Decideur decideur = new SumPointsDecideur(buckets);
				String result = decideur.decideStacktrace(stacktrace);
				
				if(result.equals(bucketKey)){
					nbSucces++;
					System.out.println("Succès : " + nbSucces);
				} else {
					nbEchecs++;
					System.out.println("Echec : " + nbEchecs);
				}
				
				System.out.println("i : " + i);
				i++;
				
				nbTotal++;
				
				bucket.putStacktrace(stacktraceId, stacktrace);
			}
		}
		
		System.out.println("Echec : " + nbEchecs);
		System.out.println("Total : " + nbTotal);
	}

}
