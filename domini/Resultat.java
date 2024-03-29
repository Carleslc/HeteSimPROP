package domini;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 
 * @author Arnau Badia Sampera
 *
 * La classe Resultat nomes es una classe que conte informacio sobre un resultat pero que no realitza
 * totes les comprovacions sobre la coherencia d'aquests. Es a dir, un objecte de la classe por contenir
 * informacio incoherent. L'unica propietat que sempre es mante es que la llista sera decreixent segons la 
 * rellevancia de cada tupla. Si per exemple es canvia el threshold, no es reajustaran els resultats al nou
 * threshold, o si es canvia la rellevancia d'una tupla, no es comprovara que aquesta s'ajusti al threshold.
 */
public class Resultat implements Serializable, Iterable<Entry<Double, Entry<Integer, String>>> {

	private static final long serialVersionUID = 4300240155279574283L;

	private Node dada;
	private ControladorPaths controladorPaths;
	private String nomPath;
	private String nomGraf;
	private List<Pair<Double, Node>> resultats;
	private Threshold threshold;

	/** Constructor sense threshold, que ordena la llista resultats
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nomPath del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevancia i nodes ordenada decreixentment per rellevancia, els 
	 * nodes son copies dels nodes originals
	 */
	public Resultat(Node dada, String nomPath, ControladorPaths controladorPaths,
			String nomGraf, ArrayList<Pair<Double, Node>> resultats) {
		this(dada, nomPath, controladorPaths, nomGraf, resultats, null, true);
	}

	/**
	 * Constructor amb threshold, que ordena la llista resultats i elimina els que no superen el threshold
	 * @param Dada sobre la que s'ha fet la consulta
	 * @param Path usat en la consulta
	 * @param nomPath del Graf sobre el que es fa la consulta
	 * @param llista de parelles de rellevancia i nodes ordenada decreixentment per rellevancia, els 
	 * nodes son copies dels nodes originals
	 * @param threshold usat en la consulta
	 */
	public Resultat(Node dada, String nomPath, ControladorPaths controladorPaths,
			String nomGraf, ArrayList<Pair<Double, Node>> resultats, Threshold threshold) {
		this(dada, nomPath, controladorPaths, nomGraf, resultats, threshold, true);
	}

	private Resultat(Node dada, String nomPath, ControladorPaths controladorPaths,
			String nomGraf, ArrayList<Pair<Double, Node>> resultats, Threshold threshold, boolean sort) {
		this.dada = dada;
		this.nomPath = nomPath;
		this.controladorPaths = controladorPaths;
		this.nomGraf = nomGraf;
		this.threshold = threshold;
		if (sort)
			Collections.sort(resultats);
		this.resultats = resultats;
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
	 * Retorna una llista de resultats [Rellev\u00E0ncia, [ID_Dada, Nom_Dada]]
	 * @return tots els resultats amb el format [Rellev\u00E0ncia, [ID_Dada, Nom_Dada]]
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> getResultats() {
		return Conversions.convert(resultats);
	}

	/**
	 * Retorna el resultat en una posicio. La primera posicio es la 0.
	 * @param index que indica la posicio de la tupla a la llista.
	 * @return la tupla que hi ha a l'index indicat.
	 * @throws IndexOutOfBoundsException si l'index esta fora de rang.
	 */
	public Entry<Double, Node> get(int index) {
		return resultats.get(index);
	}

	/**
	 * Canvia el resultat d'una posicio. Retorna si index < size(). 
	 * S'ha de tenir en compte que despres d'aquesta crida si el resultat
	 * es true llavors es probable que aquesta posicio hagi canviat degut 
	 * a l'ordre dels resultats.
	 * @param index del paramtetre que es vol substituir
	 * @param resultat pel que es vol substituir el resultat contigut a l'index del parametre
	 * @return si 0 <= index < size()
	 */
	public boolean set(int index, Pair<Double, Node> resultat) {
		if (index >= 0 && index < resultats.size()) {
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
	 * Afegeix un resultat, i el col'loca de forma ordenada a la llista
	 * @param p es la tupla que es vol afegir.
	 */

	public void afegir(Pair<Double, Node> p) {
		int pos = Collections.binarySearch(resultats, p);
		if (pos < 0) pos = -(pos) -1;
		resultats.add(pos, p);
	}

	/**
	 * Esborra un resultat. Retorna si l'index es troba dins del rang correcte.
	 * @param index de la tupla que es vol esborrar.
	 * @return si l'index es troba dins del rang correcte.
	 */
	public boolean esborrar(int index) {
		if (index >= 0 && index < resultats.size()) {
			resultats.remove(index);	
			return true;
		}
		return false;
	}
	/**
	 * Canvia el resultat d'una posicio. Retorna si index < size(). 
	 * S'ha de tenir en compte que despres d'aquesta crida si el resultat es true 
	 * llavors es probable que aquesta posicio hagi canviat degut a l'ordre dels resultats.
	 * @param index de la tupla que es vol modificar.
	 * @param rellevancia per la qual es vol substituir l'antiga.
	 * @return si 0 <= index < size() i la rellevancia es valida
	 */
	public boolean setRellevancia(int index, double rellevancia) {
		if (index >= 0 && index < resultats.size() && rellevancia >= 0 && rellevancia <= 1) {
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
	 * @return si 0 <= index < size() i s'ha cambiat la dada
	 */
	public boolean setDada(int index, Node Dada) {
		if (index >= 0 && index < resultats.size()) {
			resultats.get(index).setValue(Dada);
			return true;
		}
		return false;
	}

	/**
	 * Obt\u00E9 els n primers resultats
	 * @param n nombre de resultats amb mes rellevancia que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre a this,
	 * si es posa a false this no es veur\u00E0 modificat.
	 * @return el resultat amb el filtratge aplicat
	 */
	public Resultat filtrarElsPrimers(int n, boolean aplicar) {
		int size = resultats.size();
		List<Pair<Double, Node>> aux = resultats.subList(0, n <= size ? n : size);
		if (aplicar) {
			resultats = aux;
			return this;
		}
		else
			return new Resultat(dada, nomPath, controladorPaths, nomGraf,
					new ArrayList<>(aux), threshold, true);
	}

	/**
	 * Obt\u00E9 els n ultims resultats
	 * @param n nombre de resultats amb menys rellevancia que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al \u00FAltim resultat,
	 * si es posa a false this no es veur\u00E0 modificat.
	 * @return el resultat amb el filtratge aplicat
	 */
	public Resultat filtrarElsUltims(int n, boolean aplicar) {
		int size = resultats.size();
		int from = size - n;
		if (from < 0)
			from = 0;
		List<Pair<Double, Node>> aux = resultats.subList(from, size);
		if (aplicar) {
			resultats = aux;
			return this;
		}
		else
			return new Resultat(dada, nomPath, controladorPaths, nomGraf,
					new ArrayList<>(aux), threshold, true);
	}

	/**
	 * Obt\u00E9 tots els resultats que tenen el node amb l'etiqueta label.
	 * @param label de les tuples del resultat que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al \u00FAltim resultat,
	 * si es posa a false this no es veur\u00E0 modificat.
	 * @return el resultat amb el filtratge aplicat
	 */
	public Resultat filtrarPerEtiqueta(String label, boolean aplicar) {
		ArrayList<Pair<Double, Node>> aux = resultats.stream()
				.filter(p -> label.equalsIgnoreCase(p.getValue().getLabel()))
				.collect(Collectors.toCollection(ArrayList::new));
		if (aplicar) {
			resultats = aux;
			return this;
		}
		else
			return new Resultat(dada, nomPath, controladorPaths, nomGraf,
					aux, threshold, true);
	}

	/**
	 * Obt\u00E9 tots els resultats que tenen una rellevancia entre min i max, ambdos incluits.
	 * @param min es el minim de rellevancia
	 * @param max es el maxim de rellevancia
	 * @param aplicar si es vol aplicar el filtre al \u00FAltim resultat,
	 * si es posa a false this no es veur\u00E0 modificat.
	 * @return el resultat amb el filtratge aplicat
	 */
	public Resultat filtrarPerRellevancia(double min, double max, boolean aplicar) {
		ArrayList<Pair<Double, Node>> aux = resultats.stream()
				.filter(p -> p.getKey() >= min && p.getKey() <= max)
				.collect(Collectors.toCollection(ArrayList::new));
		if (aplicar) {
			resultats = aux;
			return this;
		}
		else
			return new Resultat(dada, nomPath, controladorPaths, nomGraf,
					aux, threshold, true);
	}

	/**
	 * Retorna un String que representa aquest objecte.
	 */
	@Override
	public String toString() {
		return toString(getResultats());
	}

	/**
	 * Retorna un String que representa aquest objecte per� amb els resultats
	 * que es passen per par\u00E0metre.
	 */
	public String toString(ArrayList<Entry<Double, Entry<Integer, String>>> resultats) {
		StringBuilder sb = new StringBuilder("RESULTATS:\n");
		sb.append("Dada: ").append(dada.getNom()).append("\n");
		sb.append("Path: ").append(nomPath).append("\n");
		String def = controladorPaths.consultarDefinicio(nomPath);
		if (def != null)
			sb.append("Descripci\u00F3 del path: ").append(def).append("\n");
		sb.append("Conjunt de dades: ").append(nomGraf).append("\n");
		if (threshold != null)
			sb.append(threshold.toString());
		sb.append("Parelles rellev\u00E0ncia-dada\n");
		int i = 0;
		DecimalFormat df = new DecimalFormat("#.####");
		for (Entry<Double, Entry<Integer, String>> p : resultats) {
			// i. rellev\u00E0ncia nomNode (ID idNode)
			// rellev\u00E0ncia amb 4 decimals
			sb.append(i++).append(". ").append(df.format(p.getKey())).append("  ")
			.append(p.getValue().getValue()).append(" (ID ").append(p.getValue().getKey()).append(")\n");
		}
		return sb.toString();
	}

	@Override
	public Iterator<Entry<Double, Entry<Integer, String>>> iterator() {
		return getResultats().iterator();
	}

	/**
	 * Classe per convertir els resultats a tipus b\u00E0sics
	 * de Java que es puguin passar a altres capes.
	 */
	private static class Conversions {

		public static ArrayList<Entry<Double, Entry<Integer, String>>> convert(List<Pair<Double, Node>> list) {
			ArrayList<Entry<Double, Entry<Integer, String>>> aux = new ArrayList<>(list.size());
			for (Entry<Double, Node> res : list)
				aux.add(convert(res));
			return aux;
		}

		private static Entry<Double, Entry<Integer, String>> convert(Entry<Double, Node> p) {
			return new Pair<>(p.getKey(), convert(p.getValue()));
		}

		private static Pair<Integer, String> convert(Node n) {
			return new Pair<>(n.getId(), n.getNom());
		}
	}

}