package fr.univlille1.m2iagl.dureyelfakawi.read;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.FilePath;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Parameter;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.EndElementsInBucketsException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.LengthFileInFolderException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NoStackTraceFileException;
import fr.univlille1.m2iagl.dureyelfakawi.read.exception.NonExistantFolder;

public class AnalyzeStacktraceTest {
	private static final String LINE = "#3  0x0613b648 in *__GI___assert_fail (assertion=0x1c5b65 \"ret != inval_id\",file=0x1c5b29 \"../../src/xcb_io.c\", line=378, function=0x1c5ce4 \"_XAllocID\") at assert.c:81 buf = 0x8e6f6d0 \"gnome-appearance-properties: ../../src/xcb_io.c:378: _XAllocID: Assert-makro \"ret != inval_id\" ei pidä paikkaansa.\n";
	private static final String LINE2= "#6  0xb75510f3 in ?? () from /usr/lib/libglib-2.0.so.0";
	@Test
	public void testGetStacktraceName() throws NonExistantFolder, EndBucketsException, LengthFileInFolderException,
			NoStackTraceFileException, EndElementsInBucketsException, FileNotFoundException {
		String file = new File(Constantes.LOCATION_BUCKETS).listFiles()[0].listFiles()[0].listFiles()[0]
				.getName();
		BucketsReader buckets = new BucketsReader();
		buckets.openFolder(Constantes.LOCATION_BUCKETS);
		StacktracesReader analyse = new StacktracesReader(buckets.nextFolder());
		File f = analyse.nextFile();
		AnalyzeStacktrace analyze = new AnalyzeStacktrace(f);
		assertEquals(file, analyze.getStacktraceName()+".txt");
	}

	@Test
	public void testGetPath() {
		ArrayList<Parameter> parameters=new ArrayList<Parameter>();
		parameters.add(new Parameter("buf"));
		FilePath path=new FilePath("assert.c",parameters);
		FilePath path2= new AnalyzeStacktrace().getPath(LINE);
		assertEquals(path,path2);
	}

	@Test
	public void testGetParametersPath() {
		String parms="buf = 0x8e6f6d0 \"gnome-appearance-properties: ../../src/xcb_io.c:378: _XAllocID: Assert-makro \"ret != inval_id\" ei pidä paikkaansa.\n";
		ArrayList<Parameter> parameters=new ArrayList<Parameter>();
		parameters.add(new Parameter("buf"));
		FilePath path=new FilePath("assert.c",parameters);
		ArrayList<Parameter> path2= new AnalyzeStacktrace().getParametersPath(parms);
		assertEquals(parameters,path2);
	}

	@Test
	public void testGetLibFrom() {
		String path=" /usr/lib/libglib-2.0.so";
		String path2= new AnalyzeStacktrace().getLibFrom(LINE2);
		assertEquals(path,path2);
		assertEquals(0,new AnalyzeStacktrace().getLigne(LINE2));
	}

	@Test
	public void testGetLigne() {
		assertEquals(81, new AnalyzeStacktrace().getLigne(LINE));
	}

	@Test
	public void testGetNumCouche() {
		assertEquals(3, new AnalyzeStacktrace().getNumCouche(LINE));

	}

	@Test
	public void testGetMethod() {
		Method method=new Method("*__GI___assert_fail",new ArrayList<Parameter>());
		assertEquals(method, new AnalyzeStacktrace().getMethod(LINE));
	}

}
