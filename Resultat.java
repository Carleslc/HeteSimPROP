package domini;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 * 
 * @author Arnau Badia Sampera
 *
 *La classe Resultat només és una classe que conté informació sobre un resultat però que no realitza
 *totes les comprovacions sobre la coherència d'aquests. És a dir, un objecte de la classe por contenir
 *informació incoherent. L'única propietat que sempre es manté és que la llista serà decreixent segons la 
 *rellevància de cada tupla. Si per exemple es canvia el threshold, no es reajustaran els resultats al nou
 *threshold, o si es canvia la rellevància d'una tupla, no es comprovarà que aquesta s'ajusti al threshold.
 */
public class Resultat implements Serializable {
	private Node dada;
	private Path path;
	private String nomGraf;
	private ArrayList<Pair<Double, Node>> resultats;
	private Threshold threshold;
	
	/** Constructor sense threshold, que ordena la llista resultats
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nom del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevància i nodes ordenada decreixentment per rellevància, els 
	 * nodes són còpies dels nodes originals
	 */
	public Resultat(Node dada, Path path, String nomGraf, ArrayList<Pair<Double, Node>> resultats) {
		this.dada = dada;
		this.path = path;
		this.nomGraf = nomGraf;
		this.threshold = null;
		this.resultats = resultats;
	}
	
	/**
	 * Constructor amb threshold, que ordena la llista resultats i elimina els que no superen el threshold
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nom del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevància i nodes ordenada decreixentment per rellevància, els 
	 * nodes són còpies dels nodes originals
	 * @param threshold usat en la consulta
	 */
	
	public Resultat(Node dada, Path path, String nomGraf, ArrayList<Pair<Double, Node>> resultats, Threshold threshold) {
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
	public void setNode(Node node) {
		this.dada = node;
	}
	
	/**
	 * Getter de node
	 * @returns node sobre el que s'ha fet la consulta
	 */
	public Node getNode() {
		return dada;
	}
	
	/**
	 * Setter de path
	 * @param path pel que es vol canviar 
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	
	/**
	 * Getter de path
	 * @returns node sobre el que s'ha fet la consulta
	 */
	public Path getPath() {
		return path;
	}
	
	
	/**
	 * Setter de nomGraf
	 * @param nomGraf pel que es vol canviar 
	 */
	public void setNomGraf(String nomGraf) {
		this.nomGraf = nomGraf;
	}
	
	/**
	 * Getter de nomGraf
	 * @returns nomGraf sobre el que s'ha fet la consulta
	 */
	public String getNomGraf() {
		return nomGraf;
	}
	
	/**
	 * Setter de resultats
	 * @param Resultat substituts 
	 */
	public void setResultats(ArrayList<Pair<Double, Node>> resultats) {
		this.resultats = resultats;
	}
	
	/**
	 * Getter de resultats
	 * @returns nomGraf sobre el que s'ha fet la consulta
	 */
	public List<Pair<Double, Node>> getResultats() {
		return resultats;
	}
	/**
	 * Setter de threshold
	 * @param threshold es el filtre per rellevancia que s'aplica al resultat
	 */
	public void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}
	/**
	 * Getter de threshold
	 * @return threshold usat en la consulta
	 */
	public Threshold getThreshold() {
		return threshold;
	}
	
	/**
	 * Retorna el total de tuples de dada i rellevància que conté el resultat.
	 * @return el total de tuples de dada i rellevància que conté el resultat.
	 */
	public int size() {
		return resultats.size();
	}
	
	/**
	 * Retorna si el resultat no conté cap tupla de dada i rellevància.
	 * @return si el resultat no conté cap tupla de dada i rellevància.
	 */
	public boolean isEmpty() {
		return resultats.isEmpty();
	}
	
	/**
	 * Retorna el resultat en una posició. La primera posició és la 0.
	 * @param index que indica la posició de la tupla a la llista.
	 * @return la tupla que hi ha a l'index indicat.
	 * @throws IndexOutOfBoundsException si l'index està fora de rang.
	 */
	public Pair<Double, Node> get(int index) throws IndexOutOfBoundsException {
		return resultats.get(index);
	}

	
	/**
	 * Canvia el resultat d’una posició. Retorna si index < size(). 
	 * S’ha de tenir en compte que després d’aquesta crida si el resultat
	 * és true llavors és probable que aquesta posició hagi canviat degut 
	 * a l’ordre dels resultats.
	 * @param index del paràmtetre que es vol substituir
	 * @param resultat pel que es vol substituir el resultat contigut a l'index del paràmetre
	 * @return si index < size()
	 */
	public boolean set(int index, Pair<Double, Node> resultat) {
		if (index < resultats.size()) {
			esborrar(index);
			afegir(resultat);
			return true;
		}
		return false;
	}

	/**
	 * Esborra totes les tuples de dada i rellevància de resultats.
	 */
	void clear() {
		resultats.clear();
	}
	
	/**
	 * Afegeix un resultat, i el col·loca de forma ordenada a la llista
	 * @param p es la tupla que es vol afegir.
	 */
	
	public void afegir(Pair<Double, Node> p) {
		int pos = Collections.binarySearch(resultats,p);
		resultats.add(pos,p);
	}
	
	/**
	 * Esborra un resultat. Retorna si l'índex es troba dins del rang correcte.
	 * @param index de la tupla que es vol esborrar.
	 * @return si l'índex es troba dins del rang correcte.
	 */
	public boolean esborrar(int index) {
		if (index < resultats.size()) {
			resultats.remove(index);	
			return true;
		}
		return false;
	}
	/**
	 * Canvia el resultat d’una posició. Retorna si index < size(). 
	 * S’ha de tenir en compte que després d’aquesta crida si el resultat és true 
	 * llavors és probable que aquesta posició hagi canviat degut a l’ordre dels resultats.
	 * @param index de la tupla que es vol modificar.
	 * @param rellevancia per la qual es vol substituir l'antiga.
	 * @return si index < size(). 
	 */
	public boolean setRellevancia(int index, Double rellevancia) {
		if (index < resultats.size()) {
			Pair<Double,Node> p = resultats.get(index);
			p.setKey(rellevancia);
			esborrar(index);
			afegir(p);
			return true;
		}
		return false;
	}
	
	/**
	 * Canvia la dada en una tupla dels resultats.
	 * @param index de la tupla que es vol canviar.
	 * @param Dada per la que es vol substituir.
	 */
	public void setDada(int index, Node Dada) {
		if (index < resultats.size()) {
			resultats.get(index).setValue(Dada);	
		}
	}
	
	/**
	 * Elimina tots els resultats excepte els n primers.
	 * @param n n es el nombre de resultats amb més rellevància que s'han de conservar
	 */
	public void filtrarElsPrimers(int n) {
		for (int i = n; i < resultats.size(); ++i) {
			esborrar(i);
		}
	}
	
	/**
	 * Elimina tots els resultats excepte els n últims.
	 * @param n es el nombre de resultats amb menys rellevància que s'han de conservar
	 */
	public void filtrarElsÚltims(int n) {
		for (int i = 0; i < (resultats.size() - n); ++i) {
			esborrar(i);
		}
	}
	
	/**
	 * Elimina tots els resultats menys els resultats que tenen el node amb l’etiqueta label.
	 * @param label de les tuples del resultat que no s'han d'eliminar
	 */
	public void filtrarPetEtiqueta(String label) {
		for (int i = 0; i < resultats.size(); ++i) {
			if (!resultats.get(i).getValue().getLabel().equals(label)) {
				esborrar(i);
			}
		}
	}
	
	/**
	 * Elimina tots els resultats menys els que tenen una rellevància entre min i max, ambdós incluïts.
	 * @param min es el mínim de rellevància
	 * @param max es el màxim de rellevància
	 */
	public void filtrarPerRellevància(double min, double max) {
		for (int i = 0; resultats.get(i).getKey().doubleValue() > max; ++i) {
			esborrar(i);
		}
		for (int i = resultats.size() ; resultats.get(i).getKey().doubleValue() < min ; ++i) {
			esborrar(i);
		}
	}
	
	/**
	 * Retorna un String que representa aquest objecte.
	 */
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
