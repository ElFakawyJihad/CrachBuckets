package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.File;

import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;

/**
 * 
 * Classe qui va récuperer les Stractrace d'un buckets.
 *
 */
public class StacktracesReader {
	private File[] folder;
	private int nbFiles;
	private int position;

	/**
	 * @param bucketFolder
	 *            dossier contenant les différentes Stacktrace.
	 */
	public StacktracesReader(File bucketFolder) {
		this.folder = bucketFolder.listFiles();
		position = 0;
		nbFiles = bucketFolder.listFiles().length;
	}
	public String getName(){
		return this.folder[position].getName();
	}
	
	
	public boolean hasNextFile(){
		return position < nbFiles;
	}

	/**
	 * 
	 * @return
	 * @throws LengthFileInFolderException
	 *             trop ou pas de fichiers dans le dossier de stractrace
	 * @throws NoStackTraceFileException
	 *             pas de fichier "stacktrace.txt" dans le dossier
	 * @throws EndElementsInBucketsException
	 *             plus d'éléments dans le Buckets
	 */
	public File nextFile() throws LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException {
		if (position < nbFiles) {
			File file = this.folder[position];
			
			position++;
			return getStacktrace(file);
		} else {
			throw new EndElementsInBucketsException();
		}
	}

	/**
	 * 
	 * @param file
	 * @return retourne le fichier de stacktrace
	 * @throws LengthFileInFolderException
	 *             trop ou pas de fichiers dans le dossier de stractrace
	 * @throws NoStackTraceFileException
	 *             pas de fichier "stacktrace.txt" dans le dossier
	 */
	private File getStacktrace(File file) throws LengthFileInFolderException, NoStackTraceFileException {
		File[] folderStacktrace = file.listFiles();
		if (folderStacktrace.length != 1) {
			throw new LengthFileInFolderException();
		}
		File stacktrace = folderStacktrace[0];
		if (stacktrace.getName().equals(Constantes.STACKTRACE_FILENAME)) {
			throw new NoStackTraceFileException();
		}
		return stacktrace;
	}

}
