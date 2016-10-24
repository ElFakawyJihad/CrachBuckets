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

	private void openFolder() {
		File file = new File(Constantes.READ);
		buckets = file.listFiles();
		nbfolder = buckets.length;
	}

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

	public static void main(String[] args)
			throws EndBucketsException, LengthFileInFolder, NoStackTraceFile, EndElementsInBuckets, IOException {
		ReadBuckets reader = new ReadBuckets();
		File buckets = reader.nextFolder();
		ReadStacktrace stactrace = new ReadStacktrace(buckets);
		stactrace.nextFile();
		stactrace.nextFile();
		AnalyzeStacktrace analyze = new AnalyzeStacktrace(stactrace.nextFile());
		System.out.println(analyze.getMethod(analyze.initCouchesList().get(0)));
		System.out.println(reader.nextFolder().getName());
	}
}
