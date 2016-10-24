package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.File;

public class ReadStacktrace {
	private File[] folder;
	private int nbfile;
	private int position;

	public ReadStacktrace(File folder) {
		this.folder = folder.listFiles();
		position = -1;
		nbfile = folder.listFiles().length;
	}

	public File nextFile() throws LengthFileInFolder, NoStackTraceFile, EndElementsInBuckets {
		position++;
		if (position < nbfile) {
			File file = this.folder[position];
			return getStacktrace(file);
		}
		else {
			throw new EndElementsInBuckets();
		}
	}

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
