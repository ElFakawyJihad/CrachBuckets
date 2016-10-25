/**
 * Classe qui lit les Buckets.
 */
package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadBuckets {
	private File[] buckets;
	private int position;
	private int nbfolder;

	/**
	 * Fonction qui ouvre le dossier contenant les Buckets.
	 */
	private void openFolder() {
		File file = new File(Constantes.LOCATIONBUCKETS);
		buckets = file.listFiles();
		nbfolder = buckets.length;
	}

	/**
	 * 
	 * @return renvoie le prochain buckets.
	 * @throws EndBucketsException
	 *             si les buckets ont tous été traités.
	 */
	private File nextFolder() throws EndBucketsException {
		// Si on à pas encore ouvert l'ensemble des dossiers contenant les
		// Buckets.
		if (buckets == null) {
			openFolder();
			position = 0;
			return buckets[position];
		}
		// Si il reste encore des Buckets.
		position++;
		if (position < nbfolder) {
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
	 * @throws LengthFileInFolder
	 * @throws NoStackTraceFile
	 * @throws EndElementsInBuckets
	 * @throws IOException
	 */
	public static void main(String[] args)
			throws EndBucketsException, LengthFileInFolder, NoStackTraceFile, EndElementsInBuckets, IOException {
		ReadBuckets reader = new ReadBuckets();
		File buckets = reader.nextFolder();
		ReadStacktrace stactrace = new ReadStacktrace(buckets);
		stactrace.nextFile();
		stactrace.nextFile();
		AnalyzeStacktrace analyze = new AnalyzeStacktrace(stactrace.nextFile());
		System.out.println(analyze.getLibFrom(analyze.initCouchesList().get(7)));
		System.out.println(reader.nextFolder().getName());
	}
}
