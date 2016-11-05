package fr.univlille1.m2iagl.dureyelfakawi.start;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.dureyelfakawi.action.DecisionWriter;
import fr.univlille1.m2iagl.dureyelfakawi.action.Helper;
import fr.univlille1.m2iagl.dureyelfakawi.action.decideur.Decideur;
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

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) throws EndBucketsException, LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException, IOException
	{
		
		/* Création des buckets */
		BucketsReader bucketsReader = new BucketsReader();
		bucketsReader.openFolder();
		
		Map<String, Bucket> buckets = new HashMap<>();
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
		/* Fin de parse des Stacktrace à mettre dans les buckets et debut de l'analyse */
		Decideur decideur = new SumPointsDecideur(buckets);
		ValuesDecided valuesDecided = decideur.decide(stacktraces);
		
		/* Fin de l'analyse et debut de l'ecriture sur fichier */
		DecisionWriter writer = new DecisionWriter(new PrintWriter(new File("result.txt")));
		writer.writeValuesDecided(valuesDecided);
		
		System.out.println("End of the algorithm. You can submit the result file");
		
	}
}
