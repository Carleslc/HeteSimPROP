package domini;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Classe que implementa un threshold que es podrà fer servir per filtrar resultats
 * d'una consulta segons la rellevancia de dos nodes.
 * Conté informació sobre els dos nodes, el nom d'un path i una rellevància
 * (una introduïda explícitament o bé la rellevància entre els dos nodes
 * segons el path fent servir el HeteSim del graf amb el que s'està treballant).
 * 
 * @author Carla Claverol
 */


public class Threshold implements Serializable {
	
	private static final long serialVersionUID = -7710703940760522487L;
	
	private double rellevancia;
	private Node a;
	private Node b;
	private String path;
	private HeteSim hs;
	
	/**
	 * Constructor de la classe amb rellevància.
	 * @param rellevancia. La rellevancia del Threshold.
	 * @param a. El primer Node.
	 * @param b. El segon Node.
	 * @param path. El nom del Path del Threshold.
	 * @param hs. El HeteSim del Graf amb el que estem treballant.
	 */
	public Threshold(double rellevancia, Node a, Node b, String path, HeteSim hs) {
		this.rellevancia = rellevancia;
		this.a = a;
		this.b = b;
		this.path = path;
		this.hs = hs;
	}
	
	/**
	 * Constructor de la classe que calcula la rellevància entre els dos Nodes.
	 * @param a. El primer Node del Threshold.
	 * @param b. El segon Node.
	 * @param path. El nom del Path del Threshold.
	 * @param hs. El HeteSim del Graf amb el que estem treballant.
	 * @throws IllegalArgumentException si path és null o bé si el Node a
	 * 			no és del tipus indicat pel primer tipus de node de path o bé si
	 * 			el Node b no és del tipus indicat per l'úlitm tipus de node de path.
	 */
	public Threshold(Node a, Node b, String path, HeteSim hs) throws IllegalArgumentException {
		this.a = a;
		this.b = b;
		this.path = path;
		this.hs = hs;
		this.rellevancia = calculateRellevancia();
	}
	
	/**
	 * Consultor dels Nodes del Threshold.
	 * @return Retorna una llista amb els dos Nodes del Threshold.
	 */
	public ArrayList<Node> getNodes(){
		ArrayList<Node> l = new ArrayList<>();
		l.add(a);
		l.add(b);
		return l;
	}
	
	/**
	 * Consultor del Path del Threshold.
	 * @return Retorna el nom del Path del Threshold.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Consultor de la rellevancia del Threshold.
	 * @return Retorna la rellevancia del Threshold.
	 */
	public double getRellevancia() {
		return rellevancia;
	}
	
	/**
	 * Modificador de la rellevancia del Threshold.
	 * @param rellevancia. Nova rellevancia del Threshold.
	 */
	public void setRellevancia(double rellevancia) {
		this.rellevancia = rellevancia;
	}
	
	/**
	 * Representació en String del Threshold.
	 * @return Retorna un String que representa el Threshold.
	 */
	public String toString() {
		String aux = "Node A: " + a.toString() + "\n" + "Node B: " + b.toString() + "\n" + "Path: " + path + "\n" + "Rellevancia: " + rellevancia + "\n";
		return aux;
	}
	
	/**
	 * Calcula la rellevància entre els dos Nodes segons el Path del Threshold
	 * fent servir el HeteSim d'aquest.
	 * @return Retorna la rellevància entre els dos Nodes del Threshold
	 * 			segons el Path d'aquest.
	 * @throws IllegalArgumentException si el nom del path és null o bé si
	 * 			algun dels Nodes no es correspon amb el path.
	 */
	private double calculateRellevancia() throws IllegalArgumentException {
		return hs.heteSim(a, b, path);
	}
}
