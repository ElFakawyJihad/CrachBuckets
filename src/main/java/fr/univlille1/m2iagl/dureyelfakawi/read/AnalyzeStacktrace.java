package fr.univlille1.m2iagl.dureyelfakawi.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.FilePath;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Method;
import fr.univlille1.m2iagl.dureyelfakawi.model.parsing.Parameter;

/**
 * 
 * Classe qui permet l'analyse du fichier de StackTrace
 *
 */
public class AnalyzeStacktrace {
	private BufferedReader buffered;
	// Ligne du probléme
	private int ligneFrom;

	/**
	 * 
	 * @param stacktrace
	 *            le fichier de trace à analyser
	 * @throws FileNotFoundException
	 *             renvoie une erreur si le fichier n'existe pas
	 */
	public AnalyzeStacktrace(File stacktrace) throws FileNotFoundException {
		FileReader reader = new FileReader(stacktrace);
		buffered = new BufferedReader(reader);
	}

	public AnalyzeStacktrace() {
	}

	// TODO Recupéré Ligne et Lib
	public FilePath getPath(String line) {
		int index;
		if ((index = line.indexOf("at")) != -1) {
			int terminer = line.indexOf(".c");
			if (terminer != -1) {
				line.substring(index, terminer+2);
				
			}
		}
		return null;

	}
	//A finir
	public ArrayList<Parameter> getParametersPath(String parameters){
		ArrayList<Parameter> parametersList=new ArrayList<Parameter>();
		if (parameters.contains(Constantes.NOLOCALS)){
			return parametersList;
		}
		parameters.split("");
		return parametersList;
	}

	/**
	 * 
	 * @param line
	 *            couche contenant un lib
	 * @return la librairie from appelé.
	 */
	public String getLibFrom(String line) {
		int index;
		if ((index = line.indexOf("from")) != -1) {
			String fromLib = line.substring(index + 4);
			int fin = fromLib.indexOf("so");
			if (fin != -1) {
				ligneFrom = Integer.parseInt(fromLib.substring(fin + 2));
				return fromLib.substring(0, fin + 2);
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * @param line
	 *            la couche concernée
	 * @return renvoie la ligne pour un from
	 */
	public int getLigne(String line) {
		getLibFrom(line);
		return this.ligneFrom;
	}

	/***
	 * 
	 * @param line
	 *            la couche concernée
	 * @return renvoie le niveau de couche
	 */
	private int getNumCouche(String line) {
		int debutIndex = line.indexOf('#');
		if (debutIndex != -1) {
			int finIndex = line.indexOf(' ');
			String stringNumCouche = line.substring(debutIndex + 1, finIndex);
			return Integer.parseInt(stringNumCouche);
		}
		return -1;
	}

	/**
	 * 
	 * @param line
	 *            la couche concernée
	 * @return renvoie la méthode contenant son nom et les noms des paramètres
	 */
	public Method getMethod(String line) {
		int index;
		if ((index = line.indexOf("in")) != -1) {
			// On recupere le nom de la methode
			String nameMethod = line.substring(index + 2);
			int finMethod = nameMethod.indexOf('(');
			String nameMethodWithoutParam = nameMethod.substring(0, finMethod);
			// --------------------------------------------------
			// On récupére les Paramètres------------------------
			int finParam = nameMethod.indexOf(')');
			String params = nameMethod.substring(finMethod + 1, finParam);
			ArrayList<Parameter> parametres = getParametres(params);
			return new Method(nameMethodWithoutParam, parametres);
		}
		return null;
	}

	/**
	 * 
	 * @param param
	 *            contient l'ensemble des paramètres (nom+valeur)
	 * @return Listes des noms de paramètres .
	 */
	private ArrayList<Parameter> getParametres(String param) {
		// On récupere les differents paramètres
		ArrayList<Parameter> retour = new ArrayList<Parameter>();
		String[] params = param.split(",");
		for (int i = 0; i < params.length; i++) {
			// On récupére les paramétres
			String nomParam = params[i].split("=")[0];
			retour.add(new Parameter(nomParam.trim()));
		}
		return retour;
	}

	/**
	 * 
	 * @return l'ensemble des couches concernée
	 * @throws IOException
	 *             retourne une exception si le fichier est vide.
	 */
	public ArrayList<String> initCouchesList() throws IOException {
		ArrayList<String> couchesListes = new ArrayList<String>();
		String couches = this.initCouches();
		int debut = 0;
		int fin = 0;
		while ((fin = couches.indexOf('#', 1)) != -1) {
			debut = couches.indexOf("#");
			couchesListes.add(couches.substring(debut, fin));
			couches = couches.substring(fin);
		}
		couchesListes.add(couches.substring(debut));
		return couchesListes;
	}

	/**
	 * 
	 * @return on récupére l'ensemble des lignes de la stacktrace
	 * @throws IOException
	 *             retourne une exception si le fichier est vide.
	 */
	private String initCouches() throws IOException {
		String line = "";
		String actuel = "";
		while ((actuel = this.buffered.readLine()) != null) {
			// On récupére chaque ligne du fichier
			line = line + actuel + "\n";
		}
		return line;
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(new AnalyzeStacktrace()
				.getNumCouche("#0  0xb732c2eb in g_hash_table_foreach_remove_or_steal (hash_table=0x80fed80,"));
	}

}
