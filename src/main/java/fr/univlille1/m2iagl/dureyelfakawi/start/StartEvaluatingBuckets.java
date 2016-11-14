package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.AveragePointsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.Decideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.LibFileAndFunctionsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.RandomDecideur;
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

public class StartEvaluatingBuckets {

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
		
		
		boolean chosen = false;
		Decideur decideur = null;
		Scanner scanner = new Scanner(System.in);
		while(!chosen){
			chosen = true;
			System.out.println("Choisissez l'algo : 1 -> Aléatoire, 2 -> Somme de points, 3 -> Moyenne de points, 4 -> Librairie, fichier et fonction");
			try {
				int algoInd = scanner.nextInt();
				switch (algoInd) {
				case 1:
					decideur = new RandomDecideur(buckets);
					break;
				case 2:
					decideur = new SumPointsDecideur(buckets);
					break;
				case 3:
					decideur = new AveragePointsDecideur(buckets);
					break;
				case 4:
					decideur = new LibFileAndFunctionsDecideur(buckets);
					break;
				default:
					chosen = false;
					System.out.println("Renseignez un chiffre entre 1 et 4");
				}
			} catch(Exception e){
				chosen = false;
				System.out.println("Renseignez un chiffre entre 1 et 4");
			}

		}
		
		/* Debut de l'analyse */
		long time = System.currentTimeMillis();
		
		/* Fin de création des buckets */

		int nbSucces = 0, nbEchecs = 0, nbTotal = 0;

		int i=0;

		for(String bucketKey : buckets.keySet()){
			Bucket bucket = buckets.get(bucketKey);

			List<String> stacktracesId = new ArrayList<String>(bucket.keySet());
			
			for(String stacktraceId : stacktracesId){

				Stacktrace stacktrace = bucket.remove(stacktraceId);

				if(bucket.keySet().size() > 1){

					String result = decideur.decideStacktrace(stacktrace);

					if(result.equals(bucketKey)){
						nbSucces++;
					} else {
						nbEchecs++;
					}

					i++;

					nbTotal++;
				}

				bucket.putStacktrace(stacktraceId, stacktrace);
			}
		}
		
		time = System.currentTimeMillis() - time;
		
		System.out.println("Temps : " + time + " ms");

		System.out.println("Succès : " + nbSucces);
		System.out.println("Echec : " + nbEchecs);
		System.out.println("Total : " + nbTotal);
	}

}
