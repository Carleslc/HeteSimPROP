package domini;

/**
 * @authors Guillem Castro, Carlos Lazaro
 */

import domini.Graf;
import domini.Matriu;
import domini.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Hetesim implements Serializable{
	
	protected Graf graf;
	private HashMap<String, Matriu<Double>> clausures;
	
	/**
	 * Crea una instancia de Hetesim con graf
	 * @param graf Graf con el que se harán los cálculos
	 * @author Guillem Castro
	 */
	public Hetesim(Graf graf) {
		this.graf = graf;
		clausures = new HashMap<>();
	}
	
	/**
	 * Devuelve el grafo pasado como parámetro al constructor
	 * @return Devuelve el Graf pasado como parámetro al constructor
	 * @author Guillem Castro
	 */
	public Graf getGraf() {
		return this.graf;
	}
	
	/**
	 * Devuelve la clausura de un path indicado
	 * @param path El path de la clausura que buscamos
	 * @return Devuelve la clausura del path indicado
	 * @throws IllegalArgumentException Cuando 'path' es null o es un Path vacio
	 * @author Guillem Castro
	 */
	public Matriu<Double> clausura(String path) throws IllegalArgumentException {
		if (path == null || path.length() == 0) {
			throw new IllegalArgumentException("'path' no pot ser buit");
		}
		if (clausures.containsKey(path)) {
			return (Matriu<Double>) clausures.get(path);
		}
				
		ArrayList<Matriu<Double>> mPath = matriusPath(path);
		int longitud = mPath.size();
		Matriu<Double> left = mPath.get(0);
		Matriu<Double> right = mPath.get(longitud/2);
		for (int i = 1; i < longitud/2; ++i) {
			left = left.multiplicar(mPath.get(i));
		}
		for (int i = longitud/2 + 1; i < longitud; ++i) {
			right = right.multiplicar(mPath.get(i));
		}
		left = clausura(left, right, (path.length()) == 2);
		
		clausures.put(path, left);
		
		return left;
	}
	
	/**
	 * Devuelve la clausura a partir de las matrices normalizadas izquierda y derecha del algoritmo Hetesim
	 * @param left Matriu de la parte izquierda del camino
	 * @param right Matriu de la parte derecha del camino
	 * @param DOSelementos Indica si el path tiene dos elementos
	 * @return Se devuelve la clausura asociada al path representado por left*right
	 * @author Guillem Castro
	 */
	public Matriu<Double> clausura(Matriu<Double> left, Matriu<Double> right, boolean DOSelementos) {
		left = left.multiplicar(right);
		if (!DOSelementos) { //Creo que esto va así
			for (int i = 0; i < left.getFiles(); ++i) {
				for (int j = 0; j < left.getColumnes(); ++j) {
					left.set(i, j, (left.get(i, j)/  (left.getNormaFila(i)*left.getNormaColumna(j)) ));
				}
			}
		}
		return left;
	}
	
	/**
	 * Efectua el calculo de HeteSim entre dos nodos 'a' y 'b'
	 * @param a Primer nodo del path
	 * @param b Último nodo del path
	 * @param path Path que deseamos usar para el calculo de HeteSim
	 * @return Devuelve la relevancia de 'a' con 'b'
	 * @throws IllegalArgumentException
	 */
	public double heteSim(Node a, Node b, String path) throws IllegalArgumentException {
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
	
	public ArrayList<Entry<Double, String>> heteSimAmbIdentificadors(Node n, String path) throws IllegalArgumentException {
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
	
	/*Función para testear matriusPath()*/
	public String TestMatriusPath(String path) {
		ArrayList<Matriu<Double>> m = matriusPath(path);
		String res = m.toString();
		return res;
	}
	
	/**
	 * Devuelve una lista con todas las matrices necesarias para calcular
	 * HeteSim del path dado
	 * @param path Path del que se quiere obtener sus matrices
	 * @return Devuelve un ArrayList<Matriu<Double>> con las matrices correspondientes
	 * a 'path'
	 * @author Guillem Castro
	 */
	private ArrayList<Matriu<Double>> matriusPath(String path) {
		int longitud = path.length() - 1;
		ArrayList<Matriu<Double>> mPath;
		mPath = new ArrayList<>(longitud);
		for (int i = 0; i < longitud; ++i) {
			char charAti = path.charAt(i);
			if (charAti == 'A') {
				Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperAutor().transposada());
				mPath.add(i,m);
			}
			else if (charAti == 'C') {
				Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperConferencia().transposada());
				mPath.add(i,m);
			}
			else if (charAti == 'T') {
				Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperTerme().transposada());
				mPath.add(i,m);
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
				mPath.add(i,m);
			}
		}
		if (longitud%2 != 0) {
			ArrayList<Matriu<Double>> E = mPath.get(longitud/2).intermedia(mPath.get(longitud/2).transposada());
			mPath.set(longitud/2, E.get(0));
			mPath.add(longitud/2 + 1, E.get(1));
			++longitud;
		}
		int i;
		for (i = 0; i < (longitud/2); ++i) {
			mPath.get(i).normalitzaPerFiles();
		}
		for (int j = i; j < longitud; ++j) {
			mPath.get(i).normalitzaPerColumnes();
		}
		return mPath;
	}
	
	/**
	 * Convierte una Matriu<Byte> en Matriu<Double>
	 * @param m Matriu<Byte>  a transformar
	 * @return Se devuelve una Matriu<Double> con los mismos valores de m
	 * @author Guillem Castro
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
