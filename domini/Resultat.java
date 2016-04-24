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
 * La classe Resultat nomes es una classe que conte informacio sobre un resultat pero que no realitza
 * totes les comprovacions sobre la coherencia d'aquests. E‰s a dir, un objecte de la classe por contenir
 * informacio incoherent. L'unica propietat que sempre es mante es que la llista sera decreixent segons la 
 * rellevancia de cada tupla. Si per exemple es canvia el threshold, no es reajustaran els resultats al nou
 * threshold, o si es canvia la rellevancia d'una tupla, no es comprovara que aquesta s'ajusti al threshold.
 */
public class Resultat implements Serializable {
	
	private static final long serialVersionUID = 6293912609608481009L;
	
	private Node dada;
	private ControladorPaths controladorPaths;
	private String nomPath;
	private String nomGraf;
	private ArrayList<Pair<Double, Node>> resultats;
	private Threshold threshold;
	
	
	
	/**
	 * Comparador de pairs de forma decreixent
	 */
	Comparator<Pair<Double, Node>> c = new Comparator<Pair<Double,Node>>() {
	    @Override
	    public int compare(final Pair<Double, Node> o1, Pair<Double, Node> o2) {
			if (o1.getKey().doubleValue() > o2.getKey().doubleValue()) return -1;
			if (o1.getKey().doubleValue() == o2.getKey().doubleValue()) return 0;
			return 1;	    	
	    }
	};
	
	/** Constructor sense threshold, que ordena la llista resultats
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nom del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevancia i nodes ordenada decreixentment per rellevancia, els 
	 * nodes son copies dels nodes originals
	 */
	public Resultat(Node dada, String nomPath, ControladorPaths controladorPaths, String nomGraf, ArrayList<Pair<Double, Node>> resultats) {
		this.dada = dada;
		this.nomPath = nomPath;
		this.controladorPaths = controladorPaths;
		this.nomGraf = nomGraf;
		this.threshold = null;
		resultats.sort(c);
		this.resultats = resultats;
	}
	
	/**
	 * Constructor amb threshold, que ordena la llista resultats i elimina els que no superen el threshold
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nom del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevancia i nodes ordenada decreixentment per rellevancia, els 
	 * nodes son copies dels nodes originals
	 * @param threshold usat en la consulta
	 */
	
	public Resultat(Node dada, String nomPath, ControladorPaths controladorPaths, String nomGraf, ArrayList<Pair<Double, Node>> resultats, Threshold threshold) {
		this.dada = dada;
		this.nomPath = nomPath;
		this.controladorPaths = controladorPaths;
		this.nomGraf = nomGraf;
		resultats.sort(c);
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
	public void setPath(String nomPath) {
		this.nomPath = nomPath;
	}
	
	/**
	 * Getter de path
	 * @returns node sobre el que s'ha fet la consulta
	 */
	public String getPath() {
		return nomPath;
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
	 * Retorna el total de tuples de dada i rellevancia que conte el resultat.
	 * @return el total de tuples de dada i rellevancia que conte el resultat.
	 */
	public int size() {
		return resultats.size();
	}
	
	/**
	 * Retorna si el resultat no conte cap tupla de dada i rellevancia.
	 * @return si el resultat no conte cap tupla de dada i rellevancia.
	 */
	public boolean isEmpty() {
		return resultats.isEmpty();
	}
	
	/**
	 * Retorna el resultat en una posicio. La primera posicio es la 0.
	 * @param index que indica la posicio de la tupla a la llista.
	 * @return la tupla que hi ha a l'index indicat.
	 * @throws IndexOutOfBoundsException si l'index esta fora de rang.
	 */
	public Pair<Double, Node> get(int index) throws IndexOutOfBoundsException {
		return resultats.get(index);
	}

	
	/**
	 * Canvia el resultat d'€™una posicio. Retorna si index < size(). 
	 * S'€™ha de tenir en compte que despres d'€™aquesta crida si el resultat
	 * es true llavors es probable que aquesta posicio hagi canviat degut 
	 * a l'€™ordre dels resultats.
	 * @param index del paramtetre que es vol substituir
	 * @param resultat pel que es vol substituir el resultat contigut a l'index del parametre
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
	 * Esborra totes les tuples de dada i rellevancia de resultats.
	 */
	public void clear() {
		resultats.clear();
	}
	
	/**
	 * Afegeix un resultat, i el col'·loca de forma ordenada a la llista
	 * @param p es la tupla que es vol afegir.
	 */
	
	public void afegir(Pair<Double, Node> p) {
		int pos = Collections.binarySearch(resultats,p,c);
		if (pos < 0) pos = -(pos) -1;
		resultats.add(pos,p);
	}
	
	/**
	 * Esborra un resultat. Retorna si l'index es troba dins del rang correcte.
	 * @param index de la tupla que es vol esborrar.
	 * @return si l'index es troba dins del rang correcte.
	 */
	public boolean esborrar(int index) {
		if (index < resultats.size()) {
			resultats.remove(index);	
			return true;
		}
		return false;
	}
	/**
	 * Canvia el resultat d'€™una posicio. Retorna si index < size(). 
	 * S'€™ha de tenir en compte que despres d'€™aquesta crida si el resultat es true 
	 * llavors es probable que aquesta posicio hagi canviat degut a l'€™ordre dels resultats.
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
	 * @param n n es el nombre de resultats amb mes rellevancia que s'han de conservar
	 */
	public void filtrarElsPrimers(int n) {
		while (n < resultats.size()) {
			esborrar(n);
		}
	}
	
	/**
	 * Elimina tots els resultats excepte els n ultims.
	 * @param n es el nombre de resultats amb menys rellevancia que s'han de conservar
	 */
	public void filtrarElsUltims(int n) {
		while (n < resultats.size()) {
			esborrar(0);
		}
	}
	
	/**
	 * Elimina tots els resultats menys els resultats que tenen el node amb l'€™etiqueta label.
	 * @param label de les tuples del resultat que no s'han d'eliminar
	 */
	
	public void filtrarPerEtiqueta(String label) {
		ArrayList<Pair<Double, Node>> aux = new ArrayList<Pair<Double, Node>>();
		for (Pair<Double, Node> p : resultats) {
			if (p.getValue().getLabel().equals(label)) aux.add(p);
		}
		resultats = aux;
	}
	
	/**
	 * Elimina tots els resultats menys els que tenen una rellevancia entre min i max, ambdos incluits.
	 * @param min es el minim de rellevancia
	 * @param max es el maxim de rellevancia
	 */
	
	public void filtrarPerRellevancia(double min, double max) {
		while(get(0).getKey() > max || isEmpty()) {
			esborrar(0);
		}		
		while(get(size()-1).getKey() < min || isEmpty()) {
			esborrar(size()-1);
		}
	}
	
	/**
	 * Retorna un String que representa aquest objecte.
	 */
	@Override
	public String toString() {
		String s = "RESULTATS:\n\n";
		s = s + "Dada : " + dada.getNom() + "\n";
		s = s + "Path : " + nomPath + "\n";
		String def = controladorPaths.consultarDefinicio(nomPath);
		if (def != null)
			s = s + "Descripcio del path: " + def + "\n";
		s = s + "Nom del graf : " + nomGraf + "\n";
		if (threshold != null) {
			threshold.toString();
		}
		s = s + "Parelles : Rellevancia-dada\n";
		int i = 0;
		for (Pair<Double,Node> p: resultats) {
			s = s + i + ". " + p.getKey().toString() + "  " + p.getValue().getNom() + "\n";
			++i;
		}
		return s;
	}

}
