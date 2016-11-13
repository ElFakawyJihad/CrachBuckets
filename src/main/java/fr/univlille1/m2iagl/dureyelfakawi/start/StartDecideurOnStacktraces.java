package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.action.DecisionWriter;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.AveragePointsDecideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.Decideur;
import fr.univlille1.m2iagl.dureyelfakawi.action.network.NetworkHelper;
import fr.univlille1.m2iagl.dureyelfakawi.model.analysis.ValuesDecided;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Bucket;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Couche;
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
		
		
		
		int i=0;
		for(File stacktraceFile : stacktraceFiles){
			AnalyzeStacktrace analyzeStacktrace = new AnalyzeStacktrace(stacktraceFile);
			StacktraceBuilder stacktraceBuilder = new StacktraceBuilder(analyzeStacktrace);
			stacktraces.put(Integer.valueOf(analyzeStacktrace.getStacktraceName()), stacktraceBuilder.build());
			i++;
			
		}
		
		/* Fin de parse des Stacktrace à mettre dans les buckets et debut de l'analyse */
		Decideur decideur = new AveragePointsDecideur(buckets);
		
		System.out.println("End of parsing. Start of analysis");
		long time = System.currentTimeMillis();
		
		ValuesDecided valuesDecided = decideur.decide(stacktraces);
		
		long timeBis = System.currentTimeMillis();
		System.out.println("Time : " + (timeBis - time));
				
		/* Fin de l'analyse et debut de l'ecriture sur fichier */
		DecisionWriter writer = new DecisionWriter(new PrintWriter(new File("result.txt")));
		String resp = writer.writeValuesDecided(valuesDecided);
		
		/*String url = "http://www.monperrus.net/martin/iagl-2016-crash-competition-result.py";
		String parameters = "proposal=" + resp;
		System.out.println(NetworkHelper.executePost(url, parameters));
		
		System.out.println("End of the algorithm. You can submit the result file");*/
		
	}
}