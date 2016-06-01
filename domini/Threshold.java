package domini;

import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;

/**
 * Classe que implementa un threshold que es podr\u00E0 fer servir per filtrar resultats
 * d'una consulta segons la rellevancia de dos nodes.
 * Cont\u00E9 informaci\u00F3 sobre els dos nodes, el nom d'un path i una rellev\u00E0ncia
 * (una introduïda expl\u00EDcitament o b\u00E9 la rellev\u00E0ncia entre els dos nodes
 * segons el path fent servir el HeteSim del graf amb el que s'est\u00E0 treballant).
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
	 * Constructor de la classe amb rellev\u00E0ncia.
	 * @param rellevancia. La rellevancia del Threshold.
	 * @param a. El primer Node.
	 * @param b. El segon Node.
	 * @param path. El nom del Path del Threshold.
	 */
	public Threshold(double rellevancia, Node a, Node b, String path) {
		this.rellevancia = rellevancia;
		this.a = a;
		this.b = b;
		this.path = path;
	}
	
	/**
	 * Constructor de la classe que calcula la rellev\u00E0ncia entre els dos Nodes.
	 * @param a. El primer Node del Threshold.
	 * @param b. El segon Node.
	 * @param path. El nom del Path del Threshold.
	 * @param hs. El HeteSim del Graf amb el que estem treballant.
	 * @param ignorarClausura. Indica si es vol ignorar la clausura al fer el c\u00E0lcul del threshold.
	 * @throws IllegalArgumentException si path \u00E9s null o b\u00E9 si el Node a
	 * 			no \u00E9s del tipus indicat pel primer tipus de node de path o b\u00E9 si
	 * 			el Node b no \u00E9s del tipus indicat per l'\u00FAlitm tipus de node de path.
	 * @throws IOException si la clausura existeix i no es pot llegir
	 */
	public Threshold(Node a, Node b, String path, HeteSim hs, boolean ignorarClausura)
			throws IllegalArgumentException, InterruptedException, IOException {
		this.a = a;
		this.b = b;
		this.path = path;
		this.hs = hs;
		this.rellevancia = calculateRellevancia(ignorarClausura);
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
	 * Representaci\u00F3 en String del Threshold.
	 * @return Retorna un String que representa el Threshold.
	 */
	public String toString() {
		String aux = "Node A: " + a.toString() + "\nNode B: " +
				b.toString() + "\nPath: " + path + "\nRellevancia: " + rellevancia + "\n";
		return aux;
	}
	
	/**
	 * Calcula la rellev\u00E0ncia entre els dos Nodes segons el Path del Threshold
	 * fent servir el HeteSim d'aquest.
	 * @param ignorarClausura. Indica si es vol ignorar la clausura al fer el c\u00E0lcul de rellev\u00E0ncia.
	 * @return Retorna la rellev\u00E0ncia entre els dos Nodes del Threshold
	 * 			segons el Path d'aquest.
	 * @throws IllegalArgumentException si el nom del path \u00E9s null o b\u00E9 si
	 * 			algun dels Nodes no es correspon amb el path.
	 * @throws IOException si la clausura existeix i no es pot llegir
	 */
	private double calculateRellevancia(boolean ignorarClausura)
			throws IllegalArgumentException, InterruptedException, IOException {
		return hs.heteSim(a, b, path, ignorarClausura);
	}
}
