package fr.univlille1.m2iagl.dureyelfakawi.read;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NonExistantFolder;

public class StacktracesReaderTest {

	@Test
	public void testGetName() throws NonExistantFolder, EndBucketsException, LengthFileInFolderException,
			NoStackTraceFileException, EndElementsInBucketsException {
		String file = new File(Constantes.LOCATION_BUCKETS).listFiles()[0].listFiles()[0].getName();
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		assertEquals(file, analyse.getName());
	}

	@Test
	public void testHasNextFile() throws NonExistantFolder, EndBucketsException, LengthFileInFolderException,
			NoStackTraceFileException, EndElementsInBucketsException {
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		analyse.nextFile();
		assertEquals(true, analyse.hasNextFile());
	}

	@Test
	public void testNextFile() throws NonExistantFolder, EndBucketsException, LengthFileInFolderException,
			NoStackTraceFileException, EndElementsInBucketsException {
		File file = new File(Constantes.LOCATION_BUCKETS).listFiles()[0].listFiles()[0].listFiles()[0];
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		assertEquals(file, analyse.nextFile());
	}

	@Test(expected = EndElementsInBucketsException.class)
	public void testNextFileEndBucketsException() throws NonExistantFolder, EndBucketsException,
			LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException {
		File[] file = new File(Constantes.LOCATION_BUCKETS).listFiles()[0].listFiles();
		int length = file.length;
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		for (int i = 0; i < length; i++) {
			analyse.nextFile();
		}
		analyse.nextFile();
		analyse.hasNextFile();
	}

	@Test
	public void testGetStacktrace() throws NonExistantFolder, EndBucketsException, LengthFileInFolderException,
			NoStackTraceFileException, EndElementsInBucketsException {
		File file = new File(Constantes.LOCATION_BUCKETS).listFiles()[0].listFiles()[0];
		File stacktraceFile = file.listFiles()[0];
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		File stacktrace = analyse.getStacktrace(file);
		assertEquals(stacktraceFile, stacktrace);
	}

	@Test(expected = LengthFileInFolderException.class)
	public void testGetStacktraceLengthFileInFolderException() throws NonExistantFolder, EndBucketsException,
			LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException {
		File nofolderContainsStackTrace = new File(Constantes.LOCATION_BUCKETS).listFiles()[0];
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		analyse.getStacktrace(nofolderContainsStackTrace);
	}

	@Test(expected = NoStackTraceFileException.class)
	public void testGetStacktraceNoStackTraceFileException() throws NonExistantFolder, EndBucketsException,
			LengthFileInFolderException, NoStackTraceFileException, EndElementsInBucketsException {
		File folderBuckets = new File(Constantes.LOCATION_BUCKETS).listFiles()[0];
		File stacktraceFolderTest=new File(folderBuckets.getPath()+"/1");
		stacktraceFolderTest.mkdirs();
		File stacktraceFile=new File(stacktraceFolderTest.getAbsolutePath()+"/test.txt");
		try {
			stacktraceFile.createNewFile();

		} catch (Exception e) {
			e.printStackTrace();
		}		
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		analyse.getStacktrace(stacktraceFolderTest);
	}

}
