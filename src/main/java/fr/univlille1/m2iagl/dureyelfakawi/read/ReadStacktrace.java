package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.File;

/**
 * 
 * Classe qui va récuperer les Stractrace d'un buckets.
 *
 */
public class ReadStacktrace {
	private File[] folder;
	private int nbfile;
	private int position;

	/**
	 * @param bucket
	 *            dossier contenant les différentes Stacktrace.
	 */
	public ReadStacktrace(File bucket) {
		this.folder = bucket.listFiles();
		position = -1;
		nbfile = bucket.listFiles().length;
	}

	/**
	 * 
	 * @return
	 * @throws LengthFileInFolder
	 *             trop ou pas de fichiers dans le dossier de stractrace
	 * @throws NoStackTraceFile
	 *             pas de fichier "stacktrace.txt" dans le dossier
	 * @throws EndElementsInBuckets
	 *             plus d'éléments dans le Buckets
	 */
	public File nextFile() throws LengthFileInFolder, NoStackTraceFile, EndElementsInBuckets {
		position++;
		if (position < nbfile) {
			File file = this.folder[position];
			return getStacktrace(file);
		} else {
			throw new EndElementsInBuckets();
		}
	}

	/**
	 * 
	 * @param file
	 * @return retourne le fichier de stacktrace
	 * @throws LengthFileInFolder
	 *             trop ou pas de fichiers dans le dossier de stractrace
	 * @throws NoStackTraceFile
	 *             pas de fichier "stacktrace.txt" dans le dossier
	 */
	private File getStacktrace(File file) throws LengthFileInFolder, NoStackTraceFile {
		File[] folderStacktrace = file.listFiles();
		if (folderStacktrace.length != 1) {
			throw new LengthFileInFolder();
		}
		File stacktrace = folderStacktrace[0];
		if (stacktrace.getName().equals(Constantes.STACKTRACENAMEFILE)) {
			throw new NoStackTraceFile();
		}
		return stacktrace;
	}

}
