package Hetesim;

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
		return (Matriu<Double>) clausures.get(path);
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
		
		if (clausures.containsKey(path)) {
			ArrayList<Double> fila = clausures.get(path).getFila(n.getId());
		}
		return null;
	}
}
