package fr.univlille1.m2iagl.dureyelfakawi.read;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NonExistantFolder;

public class BucketsReaderTest {

	@Test
	public void testOpenFolder() throws NonExistantFolder {
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		assertEquals(-1, buckets.getPosition());
	}

	@Test(expected = NonExistantFolder.class)
	public void testOpenFolderNonExistantFolder() throws NonExistantFolder, IOException {
		// Creer un Dossier qui contient pas de sous dossier
		File dir = new File("NonExistantFolder");
		dir.mkdir();
		// -----------------------------------------------
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder("NonExistantFolder" + "/" + Constantes.LOCATION_BUCKETS);
		dir.delete();
	}

	@Test
	public void testNextFolder() throws NonExistantFolder, EndBucketsException {
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		// On charge le premier dossier dans le dossier contenant les buckets
		File firstBucketsName = new File(Constantes.LOCATION_BUCKETS).listFiles()[0];
		assertEquals(firstBucketsName, buckets.nextFolder());
	}

	@Test(expected = EndBucketsException.class)
	public void testEndBucketsException() throws NonExistantFolder, EndBucketsException {
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		// On charge tous les buckets
		for (int i = 0; i < buckets.getNbFolders(); i++) {
			buckets.nextFolder();
		}
		buckets.nextFolder();
	}

	@Test
	public void testGetName() throws NonExistantFolder, EndBucketsException {
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		String firstBucketsName = new File(Constantes.LOCATION_BUCKETS).list()[0];
		buckets.nextFolder();
		assertEquals(firstBucketsName, buckets.getName());
	}

	@Test
	public void testHasNextFolder() throws NonExistantFolder, EndBucketsException {
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		boolean haveFile = new File(Constantes.LOCATION_BUCKETS).listFiles()[0].exists();
		buckets.nextFolder();
		assertEquals(haveFile, buckets.hasNextFolder());
	}
	@Test
	public void testHasNotNextFolder() throws NonExistantFolder, EndBucketsException {
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		// On charge tous les buckets
		for (int i = 0; i < buckets.getNbFolders(); i++) {
			buckets.nextFolder();
		}
		assertEquals(false, buckets.hasNextFolder());
	}

}
