package domini;

/**
 * @author Arnau Badia Sampera
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Resultat implements Serializable {

	private static final long serialVersionUID = -6077434227265383645L;
	
	private Node dada;
	private Path path;
	private String nomGraf;
	private ArrayList<Pair<Double, Node>> resultats;
	private Threshold threshold;
	
	/** Constructor sense threshold
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nom del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevancia i nodes ordenada decreixentment per rellevancia, els 
	 * nodes son copies dels nodes originals
	 */
	Resultat(Node dada, Path path, String nomGraf, ArrayList<Pair<Double, Node>> resultats) {
		this.dada = dada;
		this.path = path;
		this.nomGraf = nomGraf;
		this.resultats = resultats;
		this.threshold = null;
	}
	
	/**
	 * Constructor amb threshold
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nom del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevancia i nodes ordenada decreixentment per rellevancia, els 
	 * nodes son copies dels nodes originals
	 * @param threshold usat en la consulta
	 */
	Resultat(Node dada, Path path, String nomGraf, ArrayList<Pair<Double, Node>> resultats, Threshold threshold) {
		this.dada = dada;
		this.path = path;
		this.nomGraf = nomGraf;
		this.resultats = resultats;
		this.threshold = threshold;
	}
	
	
	/**
	 * Setter de node
	 * @param node pel que es vol canviar 
	 */
	void setNode(Node node) {
		this.dada = node;
	}
	
	/**
	 * Getter de node
	 * @returns node sobre el que s'ha fet la consulta
	 */
	Node getNode() {
		return dada;
	}
	
	/**
	 * Setter de path
	 * @param path pel que es vol canviar 
	 */
	void setPath(Path path) {
		this.path = path;
	}
	
	/**
	 * Getter de path
	 * @returns node sobre el que s'ha fet la consulta
	 */
	Path getPath() {
		return path;
	}
	
	
	/**
	 * Setter de nomGraf
	 * @param nomGraf pel que es vol canviar 
	 */
	void setNomGraf(String nomGraf) {
		this.nomGraf = nomGraf;
	}
	
	/**
	 * Getter de nomGraf
	 * @returns nomGraf sobre el que s'ha fet la consulta
	 */
	String getNomGraf() {
		return nomGraf;
	}
	
	/**
	 * Setter de resultats
	 * @param Resultat substituts 
	 */
	void setResultats(ArrayList<Pair<Double, Node>> resultats) {
		this.resultats = resultats;
	}
	
	/**
	 * Getter de resultats
	 * @returns nomGraf sobre el que s'ha fet la consulta
	 */
	List<Pair<Double, Node>> getResultats() {
		return resultats;
	}
	
	void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}
	
	Threshold getThreshold() {
		return threshold;
	}
	
	int size() {
		return resultats.size();
	}
	
	boolean isEmpty() {
		return resultats.isEmpty();
	}
	
	//retrona null ?!
	Pair<Double, Node> get(int index) throws IndexOutOfBoundsException {
		return resultats.get(index);
	}
	
	/**
	 * Canvia el resultat d’una posicio. Retorna si index < size(). 
	 * S’ha de tenir en compte que despres d’aquesta crida si el resultat
	 *  es true llavors es probable que aquesta posicio hagi canviat degut 
	 *  a l’ordre dels resultats.
	*/
	boolean set(int index, Pair<Double, Node> resultat) {
		if (index < resultats.size()) {
			esborrar(index);
			afegir(resultat);
			return true;
		}
		return false;
	}

	void clear() {
		resultats.clear();
	}
	
	void afegir(Pair<Double, Node> p) {
		int pos = Collections.binarySearch(resultats,p);
		resultats.add(pos,p);
	}
		
	boolean esborrar(int index) {
		if (index < resultats.size()) {
			resultats.remove(index);	
			return true;
		}
		return false;
	}
	
	//aqui la llista deixa de ser ordenada
	boolean setRellevancia(int index, Double rellevancia) {
		if (index < resultats.size()) {
			Pair<Double,Node> p = resultats.get(index);
			p.setKey(rellevancia);
			esborrar(index);
			afegir(p);
			return true;
		}
		return false;
	}
	
	void setDada(int index, Node Dada) {
		if (index < resultats.size()) {
			resultats.get(index).setValue(Dada);	
		}
	}
	
	void filtrarElsPrimers(int n) {
		for (int i = n; i < resultats.size(); ++i) {
			esborrar(i);
		}
	}
	
	void filtrarElsUltims(int n) {
		for (int i = 0; i < (resultats.size() - n); ++i) {
			esborrar(i);
		}
	}
	
	void filtrarPetEtiqueta(String label) {
		for (int i = 0; i < resultats.size(); ++i) {
			if (!resultats.get(i).getValue().getLabel().equals(label)) {
				esborrar(i);
			}
		}
	}
	
	void filtrarPerRellevancia(double min, double max) {
		for (int i = 0; resultats.get(i).getKey().doubleValue() > max; ++i) {
			esborrar(i);
		}
		for (int i = resultats.size() ; resultats.get(i).getKey().doubleValue() < min ; ++i) {
			esborrar(i);
		}
	}

	@Override
	public String toString() {
		String s = "RESULTATS:\n\n";
		s = s + "Dada : " + dada.getNom() + "\n";
		s = s + "Path : " + path.getPath() + "\n";
		s = s + "Nom del graf : " + nomGraf + "\n";
		if (!threshold.equals(null)) {
			threshold.toString();
		}
		s = s + "Parelles : Rellevancia-dada\n";
		int i = 1;
		for (Pair<Double,Node> p: resultats) {
			s = s + i + ". " + p.toString() + "\n";
			++i;
		}
		return s;
	}
	
}
