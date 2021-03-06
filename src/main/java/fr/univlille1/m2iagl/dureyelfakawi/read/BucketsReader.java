/**
 * Classe qui lit les Buckets.
 */
package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.File;
import java.io.IOException;

import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NonExistantFolder;

public class BucketsReader {
	private File[] buckets;
	private int position;
	private int nbFolders;

	/**
	 * Fonction qui ouvre le dossier contenant les Buckets.
	 */
	public void openFolder(String path) throws NonExistantFolder {
		File file = new File(path);
		if (!file.exists()) {
			throw new NonExistantFolder();
		}
		buckets = file.listFiles();
		nbFolders = buckets.length;
		position = -1;
	}

	public String getName() {
		return this.buckets[position].getName();
	}

	public boolean hasNextFolder() {
		return position < nbFolders - 1;
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



	public int getNbFolders() {
		return nbFolders;
	}

	public void setNbFolders(int nbFolders) {
		this.nbFolders = nbFolders;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
