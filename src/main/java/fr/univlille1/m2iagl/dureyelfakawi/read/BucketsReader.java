/**
 * Classe qui lit les Buckets.
 */
package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;

public class BucketsReader {
	private File[] buckets;
	private int position;
	private int nbFolders;

	/**
	 * Fonction qui ouvre le dossier contenant les Buckets.
	 */
	public void openFolder() {
		File file = new File(Constantes.LOCATION_BUCKETS);
		buckets = file.listFiles();
		nbFolders = buckets.length;
		position=-1;
	}
	public String getName(){
		return this.buckets[position].getName();
	}
	
	public boolean hasNextFolder(){
		return position < nbFolders-1;
	}

	/**
	 * 
	 * @return renvoie le prochain buckets.
	 * @throws EndBucketsException
	 *             si les buckets ont tous été traités.
	 */
	public File nextFolder() throws EndBucketsException {
		position++;
		if (position < nbFolders) {
			return buckets[position];
		}
		// Alors les fichiers sont terminers.
		throw new EndBucketsException();
	}

	/**
	 * Main de test
	 * 
	 * @param args
	 * @throws EndBucketsException
	 * @throws LengthFileInFolderException
	 * @throws NoStackTraceFileException
	 * @throws EndElementsInBucketsException
	 * @throws IOException
	 */
	public static void main(String[] args)
			throws EndBucketsException, LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException, IOException {
		BucketsReader reader = new BucketsReader();
		File buckets = reader.nextFolder();
		StacktracesReader stactrace = new StacktracesReader(buckets);
		stactrace.nextFile();
		stactrace.nextFile();
		AnalyzeStacktrace analyze = new AnalyzeStacktrace(stactrace.nextFile());
		System.out.println(analyze.getLibFrom(analyze.initCouches().get(7)));
		System.out.println(reader.nextFolder().getName());
	}
}
