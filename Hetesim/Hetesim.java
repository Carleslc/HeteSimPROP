package Hetesim;

/**
 * @authors Guillem Castro, Carlos Lazaro
 */

import Graf.Graf;
import Matriu.Matriu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Node.*;

public class Hetesim {
	
	protected Graf graf;
	private HashMap<String, Matriu<Double>> clausures;
	
	public Hetesim(Graf graf) {
		this.graf = graf;
		clausures = new HashMap<>();
	}
	
	public Graf getGraf() {
		return this.graf;
	}
	
	/**
	 * Devuelve la clausura de un path indicado
	 * @param path El path de la clausura que buscamos
	 * @return Devuelve la clausura del path indicado
	 * @throws IllegalArgumentException
	 */
	Matriu<Double> clausura(String path) throws IllegalArgumentException {
		if (path == null) {
			throw new IllegalArgumentException("'path' no pot ser null");
		}
		if (clausures.containsKey(path)) {
			return (Matriu<Double>) clausures.get(path);
		}
		
		//calcular clausura...
		return null;
	}
	
	/**
	 * Efectua el calculo de HeteSim entre dos nodos 'a' y 'b'
	 * @param a Primer nodo del path
	 * @param b Último nodo del path
	 * @param path Path que deseamos usar para el calculo de HeteSim
	 * @return Devuelve la relevancia de 'a' con 'b'
	 * @throws IllegalArgumentException
	 */
	double heteSim(Node a, Node b, String path) throws IllegalArgumentException {
		if (path == null || a == null || b == null) {
			throw new IllegalArgumentException("Els parametres no poden ser null");
		}
		char classA = a.getClass().getSimpleName().charAt(0);
		char classB = b.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classA || path.charAt(path.length()-1) != classB) {
			throw new IllegalArgumentException("Els tipus dels nodes no coincideixen amb els del 'path'");
		}
		
		if (clausures.containsKey(path)) {
			Matriu<Double> clausura = clausures.get(path);
			return clausura.get(a.getId(), b.getId());
		}
		
		//calcular HeteSim...
		
		return 0; //Esta linea hay que borrarla
	}
	
	ArrayList<Entry<Double, String>> heteSimAmbIdentificadors(Node n, String path) throws IllegalArgumentException {
		if (path == null || n == null) {
			throw new IllegalArgumentException("Els parametres no poden ser null");
		}
		char classNode = n.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classNode) {
			throw new IllegalArgumentException("El tipus del node no coincideix amb el primer del 'path'");
		}
		
		//calcular HeteSim...
		
		return null; //Esta linea hay que borrarla
	}
	
	/**
	 * Devuelve una lista con todas las matrices necesarias para calcular
	 * HeteSim del path dado
	 * @param path Path del que se quiere obtener sus matrices
	 * @return Devuelve un ArrayList<Matriu<Double>> con las matrices correspondientes
	 * a 'path'
	 */
	private ArrayList<Matriu<Double>> matriusPath(String path) {
		int longitud = path.length() - 1;
		ArrayList<Matriu<Double>> mPath = null;
		if (longitud%2 == 0) { //longitud par -> no hace falta añadir Empty
			for (int i = 0; i < longitud; ++i) {
				mPath = new ArrayList<>(longitud);
				char charAti = path.charAt(i);
				if (charAti == 'A') {
					Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperAutor().transposada());
					if (i+1 <= (longitud)/2) {
						m.normalitzaPerFiles();
						mPath.set(i, m);
					}
					else {
						m.normalitzaPerColumnes();
						mPath.set(i, m);
					}
				}
				else if (charAti == 'C') {
					Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperConferencia().transposada());
					if (i+1 <= (longitud)/2) {
						m.normalitzaPerFiles();
						mPath.set(i, m);
					}
					else {
						m.normalitzaPerColumnes();
						mPath.set(i, m);
					}
				}
				else if (charAti == 'T') {
					Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperTerme().transposada());
					if (i+1 <= (longitud)/2) {
						m.normalitzaPerFiles();
						mPath.set(i, m);
					}
					else {
						m.normalitzaPerColumnes();
						mPath.set(i, m);
					}
				}
				else if (charAti == 'P') {
					char charAti1 = path.charAt(i+1);
					Matriu<Double> m = null;
					if (charAti1 == 'A') {
						m = creaMatriuDouble(graf.consultarMatriuPaperAutor());
					}
					if (charAti1 == 'C') {
						m = creaMatriuDouble(graf.consultarMatriuPaperConferencia());
					}
					if (charAti1 == 'T') {
						m = creaMatriuDouble(graf.consultarMatriuPaperTerme());
					}
					if (i+1 <= (longitud)/2) {
						m.normalitzaPerFiles();
						mPath.set(i, m);
					}
					else {
						m.normalitzaPerColumnes();
						mPath.set(i, m);
					}
				}
			}
		}
		else {
			mPath = new ArrayList<>(longitud+1);
			//añadir elemento Empty y poner matrices en ArrayList
		}
		return mPath;
	}
	
	/*
	 * Esta funcion es necesaria hasta que tengamos una creadora
	 * por copia o que la funcion normalizar devuelva una Matriu<Double>
	 */
	private Matriu<Double> creaMatriuDouble(Matriu<Byte> m) {
		int files = m.getFiles();
		int cols = m.getColumnes();
		Matriu<Double> res = new Matriu<Double>(files, cols, 0d);
		for (int i = 0; i < files; ++i) {
			for (int j = 0; j < cols; ++j) {
				res.set(i, j, (double) m.get(i, j));
			}
		}
		return res;
	}
}
