package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import fr.univlille1.m2iagl.dureyelfakawi.action.DecisionWriter;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.AveragePointsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.Decideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.LibFileAndFunctionsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.RandomDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.SumPointsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Stacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.AnalyzeStacktrace;
import fr.univlille1.m2iagl.dureyelfakawi.read.BucketBuilder;
import fr.univlille1.m2iagl.dureyelfakawi.read.BucketsReader;
import fr.univlille1.m2iagl.dureyelfakawi.read.Constantes;
import fr.univlille1.m2iagl.dureyelfakawi.read.StacktraceBuilder;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NonExistantFolder;

/**
 * Hello world!
 *
 */
public class StartDecideurOnStacktraces 
{
	public static void main(String[] args) throws EndBucketsException, LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException, IOException, NonExistantFolder
	{


		/* Création des buckets */
		BucketsReader bucketsReader = new BucketsReader();
		bucketsReader.openFolder(Constantes.LOCATION_BUCKETS);

		Map<String, Bucket> buckets = new HashMap<String, Bucket>();
		while(bucketsReader.hasNextFolder()){
			BucketBuilder bucketBuilder = new BucketBuilder(bucketsReader.nextFolder());
			buckets.put(bucketsReader.getName(), bucketBuilder.build());
		}

		/* Fin de création des buckets et début des parse des Stacktrace à mettre dans les buckets */

		File stacktracesFolder = new File(Constantes.LOCATION_STACKTRACE_TO_ATTRIBUTE);

		File[] stacktraceFiles = stacktracesFolder.listFiles();

		Map<Integer, Stacktrace> stacktraces = new HashMap<Integer, Stacktrace>();

		for(File stacktraceFile : stacktraceFiles){
			AnalyzeStacktrace analyzeStacktrace = new AnalyzeStacktrace(stacktraceFile);
			StacktraceBuilder stacktraceBuilder = new StacktraceBuilder(analyzeStacktrace);
			stacktraces.put(Integer.valueOf(analyzeStacktrace.getStacktraceName()), stacktraceBuilder.build());
		}
		/* Fin de parse des Stacktrace à mettre dans les buckets */
		/* Choix de l'algo */ 

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
		
		ValuesDecided valuesDecided = decideur.decide(stacktraces);
		
		time = System.currentTimeMillis() - time;
		
		System.out.println("Temps : " + time + " ms");


		/* Fin de l'analyse et debut de l'ecriture sur fichier */
		DecisionWriter writer = new DecisionWriter(new PrintWriter(new File("result.txt")));
		writer.writeValuesDecided(valuesDecided);

		System.out.println("End of the algorithm. You can submit the result file");

	}
}