package domini;

import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.io.IOException;

import persistencia.ControladorPersistenciaPropi;
import presentacio.TipusDada;
import persistencia.ControladorExportacio;

/**
 * Controlador de les consultes que reslitza el programa.
 * S'encarrega de realitzar les consultes de rellevàncies i gestionar els resultats.
 * 
 * @author Carla Claverol
 */
public class ControladorConsultes {

	public static final String DEFAULT_PATH_RESULTATS = "resultats.dat";

	private TreeMap<Date, Resultat> resultats;
	private Date ultimaConsulta;
	private ControladorMultigraf controladorMultigraf;
	private ControladorPaths controladorPaths;

	/**
	 * Constructor de la classe.
	 * @param controladorMultigraf. El controlador multigraf del programa.
	 * @param controladorPaths. El controlador de paths del programa.
	 */
	public ControladorConsultes(ControladorMultigraf controladorMultigraf,
			ControladorPaths controladorPaths) {
		resultats = new TreeMap<>((d1, d2) -> d2.compareTo(d1)); // decreixentment
		ultimaConsulta = null;
		this.controladorMultigraf = controladorMultigraf;
		this.controladorPaths = controladorPaths;
	}

	/**
	 * Consultora de l'últim resultat.
	 * @return tots els resultats amb el format [Rellevància, [ID_Dada, Nom_Dada]]
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consultarResultat()
			throws IllegalArgumentException {
		return resultats.get(getUltimaConsulta()).getResultats();
	}

	/**
	 * Consultora de la data de la última consulta.
	 * @return Retorna la data de la última consulta.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public Date getUltimaConsulta() throws IllegalArgumentException {
		if (resultats.isEmpty()) throw new IllegalArgumentException("No existeix una última consulta.");
		return ultimaConsulta == null ? resultats.firstKey() : ultimaConsulta;
	}
	
	/**
	 * Modificadora de la última consulta
	 * @param date la data de la consulta
	 * @throws IllegalArgumentException si date és null o no existeix cap consulta en date
	 */
	public void setUltimaConsulta(Date date) throws IllegalArgumentException {
		if (date == null || !resultats.containsKey(date))
			throw new IllegalArgumentException("No existeix la consulta amb data " + date);
		ultimaConsulta = date;
	}

	/**
	 * Consultora d'un resultat s'una data concreta.
	 * @param data La data el resultat de la qual volem consultar.
	 * @return tots els resultats amb el format [Rellevància, [ID_Dada, Nom_Dada]]
	 * @throws IllegalArgumentException si no existeix cap consulta realitzada en la data indicada.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consultarResultat(Date data) 
		throws IllegalArgumentException {
		if (!resultats.containsKey(data))
			throw new IllegalArgumentException("No existeix cap consulta realitzada en la data especificada.");
		ultimaConsulta = data;
		return resultats.get(data).getResultats();
	}

	/**
	 * Consultora de les dates en que s'han realitzat consultes.
	 * @returns retorna una llista de les dates en que s'han realitzat consultes ordenada decreixentment.
	 */
	public LinkedList<Date> consultarDatesConsultes() {
		return new LinkedList<Date>(resultats.keySet());
	}

	/**
	 * Realitza una consulta de rellevàncies a partir d'un node i un path.
	 * @param path El nom del path que es vol fer servir per calcular rellevàncies.
	 * @param idNode L'id del node del que es vol obtenir rellevàncies amb altres nodes.<br>
	 * 			El node és del primer tipus de node del path.
	 * @param ignorarClausura Indica si es vol ignorar la clausura al fer el càlcul.
	 * @returns Retorna el resultat de la consulta,
	 * 			amb la llista de totes les rellevancies i els noms dels nodes
	 * 			(de l'últim tipus de node del path) rellevants pel node
	 * 			identificat per idNode.
	 * @throws IllegalArgumentException si no existeixen el path o el node indicats.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consulta(String path, int idNode,
			boolean ignorarClausura) throws IllegalArgumentException, InterruptedException, IOException {
		
		if (!exists(path)) throw new IllegalArgumentException("El path no existeix.");

		Node n = getNode(path, 0, idNode);
		if (n.getId() == -1) throw new IllegalArgumentException ("El node no existeix.");

		ArrayList<Pair<Double, Node>> res = llistaResultats(n, path, 0d, ignorarClausura);

		Resultat r = new Resultat(n, path, controladorPaths, controladorMultigraf.getIdActual(), res);
		Date d = new Date();
		resultats.put(d, r);
		ultimaConsulta = d;
		return r.getResultats();
	}

	/**
	 * Realitza una consulta de rellevàncies a partir d'un node i un path 
	 * i fent servir un threshold com a filtre.
	 * @param path El nom del path que es vol fer servir per calcular rellevàncies.
	 * @param idNode L'id del node del que es vol obtenir rellevàncies amb altres nodes.<br>
	 * El node és del primer tipus de node del path.
	 * @param idNodeThreshold1 L'id del primer node del threshold.<br>
	 * El node és del primer tipus de node del path del threshold.
	 * @param idNodeThreshold2 L'id del segon node del threshold.<br>
	 * El node és de l'últim tipus de node del path del threshold.
	 * @param thresholdPath El nom del path del threshold.
	 * @param ignorarClausura Indica si es vol ignorar la clausura al fer el càlcul.
	 * @param ignorarClausuraThreshold Indica si es vol ignorar la clausura al fer el càlcul del threshold.
	 * @param min la mínima rellevància que ha de tenir qualsevol resultat que es retorni
	 * @param max la màxima rellevància que ha de tenir qualsevol resultat que es retorni
	 * @returns Retorna el resultat de la consulta,
	 * 			amb la llista de totes les rellevancies i els noms dels nodes
	 * 			(de l'últim tipus de node del path) rellevants pel node
	 * 			identificat per idNode que passen el threshold.
	 * @throws IllegalArgumentException si no existeixen el path o el node indicats
	 * 			o bé algun dels nodes o el path del threshold no existeixen.
	 * @throws IOException
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consulta(String path, int idNode,
			int idNodeThreshold1, int idNodeThreshold2, String thresholdPath,
			double min, double max, boolean ignorarClausura, boolean ignorarClausuraThreshold)
			throws IllegalArgumentException, InterruptedException, IOException {
		
		if (!exists(path)) throw new IllegalArgumentException("El path no existeix.");

		Node n = getNode(path, 0, idNode);
		if (n.getId() == -1) throw new IllegalArgumentException ("El node no existeix.");

		Threshold t = createThreshold(idNodeThreshold1, idNodeThreshold2, thresholdPath, ignorarClausuraThreshold);
		ArrayList<Pair<Double, Node>> res = llistaResultats(n, path, t.getRellevancia(), ignorarClausura);

		Resultat r = new Resultat(n, path, controladorPaths, controladorMultigraf.getIdActual(), res, t)
				.filtrarPerRellevancia(min, max, true);
		Date d = new Date();
		resultats.put(d, r);
		ultimaConsulta = d;
		return r.getResultats();
	}

	/**
	 * Esborra la consulta indicada.
	 * @param data La data la consulta de la qual volem esborrar.
	 * @returns Retorna cert si s'ha pogut esborrar la consulta (existeix una consulta amb la data indicada)
	 * 			i fals altrament.
	 */
	public boolean esborrarConsulta(Date data) {
		if (!resultats.containsKey(data)) return false;
		if (data.equals(getUltimaConsulta())) ultimaConsulta = null;
		resultats.remove(data);
		return true;
	}
	
	/**
	 * Obté els n primers resultats
	 * @param n nombre de resultats amb mes rellevancia que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al últim resultat,
	 * si es posa a false l'ultim resultat no es veurà modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public Resultat filtrarElsPrimers(int n, boolean aplicar) throws IllegalArgumentException {
		return resultats.get(getUltimaConsulta()).filtrarElsPrimers(n, aplicar);
	}

	/**
	 * Obté els n ultims resultats
	 * @param n nombre de resultats amb menys rellevancia que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al últim resultat,
	 * si es posa a false l'ultim resultat no es veurà modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public Resultat filtrarElsUltims(int n, boolean aplicar) throws IllegalArgumentException {
		return resultats.get(ultimaConsulta).filtrarElsUltims(n, aplicar);
	}

	/**
	 * Obté tots els resultats que tenen el node amb l'etiqueta label.
	 * @param label de les tuples del resultat que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al últim resultat,
	 * si es posa a false l'ultim resultat no es veurà modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public Resultat filtrarPerEtiqueta(String label, boolean aplicar) throws IllegalArgumentException {
		return resultats.get(ultimaConsulta).filtrarPerEtiqueta(label, aplicar);
	}

	/**
	 * Obté tots els resultats que tenen una rellevancia entre min i max, ambdos incluits.
	 * @param min es el minim de rellevancia
	 * @param max es el maxim de rellevancia
	 * @param aplicar si es vol aplicar el filtre al últim resultat,
	 * si es posa a false l'ultim resultat no es veurà modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public Resultat filtrarPerRellevancia(double min, double max, boolean aplicar)
			throws IllegalArgumentException {
		return resultats.get(ultimaConsulta).filtrarPerRellevancia(min, max, aplicar);
	}

	/**
	 * Esborra tots els resultats de l'última consulta.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public void clear() throws IllegalArgumentException {
		resultats.get(getUltimaConsulta()).clear();
	}

	/**
	 * Afegeix a l'última consulta un resultat amb la rellevància i el node indicats.
	 * @param rellevancia. La rellevància del node que s'afegeix.
	 * @param idNode L'id del node que s'afegeix.
	 * 			El node és de l'últim tipus de node del path de l'última consulta.
	 * @throws IllegalArgumentException si no existeix una última consulta
	 * 			o si no existeix el node indicat.
	 */
	public void afegir(double rellevancia, int idNode) throws IllegalArgumentException {
		String path = resultats.get(getUltimaConsulta()).getPath();
		Node n = getNode(path, path.length()-1, idNode);
		if (n.getId() == -1) throw new IllegalArgumentException ("El node no existeix.");
		Pair<Double, Node> p = new Pair<>(rellevancia, n);
		resultats.get(getUltimaConsulta()).afegir(p);
	}

	/**
	 * Esborra de l'última consulta el resultat indicat.
	 * @param index La posició del resultat que es vol esborrar.
	 * @returns Retorna cert si s'ha pogut esborrar el resultat (index es troba dins d'un rang correcte)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public boolean esborrar(int index) throws IllegalArgumentException {
		return resultats.get(getUltimaConsulta()).esborrar(index);
	}
	
	/**
	 * Consultora de la dada de l'última consulta.
	 * @returns Retorna el nom de la dada sobre la que s'ha fet l'última consulta.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public String getDada() throws IllegalArgumentException {
		return resultats.get(getUltimaConsulta()).getNode().getNom();
	}
	
	/**
	 * Consultora del path de l'última consulta.
	 * @returns Retorna el nom del path fet servir en l'última consulta.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public String getPath() throws IllegalArgumentException {
		return resultats.get(getUltimaConsulta()).getPath();
	}
	
	/**
	 * Consulta si la última consulta té threshold.
	 * @return si la última consulta té threshold.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public boolean hasThreshold() throws IllegalArgumentException {
		return resultats.get(getUltimaConsulta()).getThreshold() != null;
	}
	
	/**
	 * Consultora del path del threshold de l'última consulta.
	 * @returns Retorna el path de la consulta o null si la consulta no té threshold.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public String getPathThreshold() throws IllegalArgumentException {
		Threshold t = resultats.get(getUltimaConsulta()).getThreshold();
		return t == null ? null : t.getPath();
	}
	
	/**
	 * Consultora de les dades del threshold de l'última consulta.
	 * @returns Retorna els noms de les dades del threshold sobre la que s'ha fet la consulta
	 * o null si la consulta no té threshold.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public Entry<String, String> getDadesThreshold() throws IllegalArgumentException {
		Threshold t = resultats.get(getUltimaConsulta()).getThreshold();
		if (t != null) {
			List<String> dades = t.getNodes().stream().map(n -> n.getNom()).collect(Collectors.toList());
			return new Pair<>(dades.get(0), dades.get(1));
		}
		return null;
	}
	
	/**
	 * Consultora de la rellevància del threshold de l'última consulta.
	 * @returns Retorna la rellevància del threshold sobre la que s'ha fet la consulta
	 * o 1 o null si la consulta no té threshold.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public double getRellevanciaThreshold() throws IllegalArgumentException {
		Threshold t = resultats.get(getUltimaConsulta()).getThreshold();
		return t == null ? 1 : t.getRellevancia();
	}

	/**
	 * Consultora del tipus dels nodes dels resultats de l'última consulta.
	 * @returns Retorna el tipus dels nodes dels reultats de l'última consulta:
	 * 			"Autor", "Conferencia", "Terme" o "Paper".
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public TipusDada getTipusNode() throws IllegalArgumentException{
		String path = resultats.get(getUltimaConsulta()).getPath();
		if (path.charAt(path.length()-1) == 'A') return TipusDada.Autor;
		if (path.charAt(path.length()-1) == 'C') return TipusDada.Conferencia;
		if (path.charAt(path.length()-1) == 'T') return TipusDada.Terme;
		return TipusDada.Paper;
	}

	/**
	 * Modifica la rellevància del resultat indicat de l'última consulta.
	 * S'ha de tenir en compte que després d'aquesta crida si retorna cert llavors
	 * és probable que aquesta posició hagi canviat degut a l'ordre dels resultats.
	 * @param index La posició del resultat que es vol modificar.
	 * @param rellevancia La rellevancia per la qual es vol modificar l'actual.
	 * @returns Retorna cert si s'ha pogut modificar el resultat
	 * 			(index es troba dins d'un rang correcte i 0 <= rellevancia <= 1)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public boolean setRellevancia(int index, double rellevancia) throws IllegalArgumentException {
		return resultats.get(getUltimaConsulta()).setRellevancia(index, rellevancia);
	}

	/**
	 * Modifica la dada del resultat indicat de l'última consulta.
	 * @param index La posició del resultat que es vol modificar.
	 * @param idNode L'id del node pel qual es vol modificar l'actual.
	 * 			El node és de l'últim tipus de node del path de l'última consulta.
	 * @returns Retorna cert si s'ha pogut modificar el resultat
	 * 			(index es troba dins d'un rang correcte i el node existeix)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public boolean setDada(int index, int idNode) throws IllegalArgumentException {
		Resultat r = resultats.get(getUltimaConsulta());

		String path = r.getPath();
		Node n = getNode(path, path.length()-1, idNode);
		if (n.getId() == -1) return false;

		return r.setDada(index, n);
	}

	/**
	 * Modifica el nom de la dada del resultat indicat de l'última consulta.
	 * @param index La posició del resultat que es vol modificar.
	 * @param nom El nom pel qual es vol modificar l'actual.
	 * @returns Retorna cert si s'ha pogut modificar el resultat (index es troba dins d'un rang correcte)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public boolean canviarNom(int index, String nom) throws IllegalArgumentException {
		Resultat r = resultats.get(getUltimaConsulta());
		if (index < 0 || index >= r.size()) return false;
		r.get(index).getValue().setNom(nom);
		return true;
	}

	/**
	 * Modifica el threshold de l'última consulta i refà la consulta.
	 * @param idNode1 L'id del primer node del nou threshold.<br>
	 * 			El node és del primer tipus de node del path del threshold.
	 * @param idNode2 L'id del segon node del nou threshold.<br>
	 * 			El node és de l'últim tipus de node del path del threshold.
	 * @param path El nom del path del nou threshold.
	 * @param ignorarClausura. Indica si es vol ignorar la clausura al fer el càlcul del threshold.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 * @throws IOException 
	 */
	public void setThreshold(int idNode1, int idNode2, String path, boolean ignorarClausura)
			throws IllegalArgumentException, InterruptedException, IOException {
		Threshold t = createThreshold(idNode1, idNode2, path, ignorarClausura);
		Resultat r = resultats.get(getUltimaConsulta());
		ArrayList<Pair<Double, Node>> res = llistaResultats(r.getNode(),
				r.getPath(), t.getRellevancia(), false);
		r.setThreshold(t);
		r.setResultats(res);
	}

	/**
	 * Modifica el path i la dada de l'última consulta i refà la consulta.
	 * @param path El nom del path pel qual es vol modificar l'actual.
	 * @param id L'id del node pel qual es vol modificar l'actual.<br>
	 * 			El node és del primer tipus de node de path.
	 * @throws IllegalArgumentException si no existeix una última consulta, 
	 * 			o bé no existeixen el path o el node indicats.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setPath(String path, int id)
			throws IllegalArgumentException, InterruptedException, IOException {
		if (!exists(path)) throw new IllegalArgumentException("El path no existeix.");

		Resultat r = resultats.get(getUltimaConsulta());
		Node n = getNode(path, 0, id);
		if (n.getId() == -1) throw new IllegalArgumentException ("El node no existeix.");

		Double filtre = 0.;
		Threshold t = r.getThreshold();
		if (t != null) filtre = t.getRellevancia();
		ArrayList<Pair<Double, Node>> res = llistaResultats(n, path, filtre, false);
		r.setPath(path);
		r.setNode(n);
		r.setResultats(res);
	}

	/**
	 * Modifica la dada de l'última consulta i refà la consulta.
	 * @param id L'id del node pel qual es vol modificar l'actual.
	 * 			El node és del primer tipus de node del path de l'última consulta.
	 * @throws IllegalArgumentException si no existeix una última consulta,
	 * 			o bé no existeix el node indicat.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setDada(int id)
			throws IllegalArgumentException, InterruptedException, IOException {
		Resultat r = resultats.get(getUltimaConsulta());
		Node n = getNode(r.getPath(), 0, id);
		if (n.getId() == -1) throw new IllegalArgumentException ("El node no existeix.");

		Double filtre = 0.;
		Threshold t = r.getThreshold();
		if (t != null) filtre = t.getRellevancia();
		ArrayList<Pair<Double, Node>> res = llistaResultats(n, r.getPath(), filtre, false);
		r.setNode(n);
		r.setResultats(res);
	}

	/**
	 * Exporta uns resultats amb les dades de la última consulta.
	 * @param filesystem_path El fitxer on es vol exportar els resultats.
	 * @param resultats Els resultats a exportar.
	 * @throws IOException si no es pot crear o escriure en el fitxer indicat.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public void exportarResultat(String filesystem_path,
			ArrayList<Entry<Double, Entry<Integer, String>>> resultats)
			throws IOException, IllegalArgumentException {
		Date ultimaConsulta = getUltimaConsulta();
		ControladorExportacio.exportar(filesystem_path, ultimaConsulta,
				this.resultats.get(ultimaConsulta).toString(resultats));
	}
	
	/**
	 * Exporta els resultats de la última consulta.
	 * @param filesystem_path El fitxer on es vol exportar els resultats.
	 * @throws IOException si no es pot crear o escriure en el fitxer indicat.
	 * @throws IllegalArgumentException si no existeix una última consulta.
	 */
	public void exportarResultat(String filesystem_path)
			throws IOException, IllegalArgumentException {
		Date ultimaConsulta = getUltimaConsulta();
		ControladorExportacio.exportar(filesystem_path, ultimaConsulta,
				this.resultats.get(ultimaConsulta).toString());
	}

	/**
	 * Guarda totes les consultes al fitxer per defecte.
	 * @throws IOException si no es pot crear o escriure en el fitxer per defecte.
	 */
	public void guardarResultats() throws IOException {
		ControladorPersistenciaPropi.guardarResultats(DEFAULT_PATH_RESULTATS, resultats);
	}

	/**
	 * Carrega totes les consultes del fitxer per defecte.
	 * @throws IOException si no existeix el fitxer per defecte
	 * o si no es pot llegir o no té el format correcte.
	 */
	public void carregarResultats() throws IOException {
		resultats = ControladorPersistenciaPropi.carregarResultats(DEFAULT_PATH_RESULTATS);
	}

	/**
	 * Fa una còpia del node indicat.
	 * @param n El node que es vol copiar.
	 * @returns Retorna una còpia del node indicat.
	 */
	private Node copia(Node n) {
		Node res;
		if (n.getClass().getSimpleName().equals("Autor")) res = new Autor();
		else if (n.getClass().getSimpleName().equals("Conferencia")) res = new Conferencia();
		else if (n.getClass().getSimpleName().equals("Terme")) res = new Terme();
		else res = new Paper();
		res.setId(n.getId());
		res.setNom(n.getNom());
		res.setLabel(n.getLabel());
		return res;
	}

	/**
	 * Retorna el node amb l'id indicat del tipus indicat pel caràcter en la posició index del path.
	 * @param path El path en el que es consulta el tipus del node.
	 * @param index Posició del caràcter del path que ens indica el tipus del node.
	 * @param id Id del node que volem consultar.
	 * @returns Retorna el node amb l'id indicat del tipus indicat pel caràcter en la posició index del path
	 * 			(-1 si el node no existeix).
	 */
	private Node getNode(String path, int index, int id) {
		Node n;
		if (path.charAt(index) == 'A') n = controladorMultigraf.getGraf().consultarAutor(id);
		else if (path.charAt(index) == 'C') n = controladorMultigraf.getGraf().consultarConferencia(id);
		else if (path.charAt(index) == 'T') n = controladorMultigraf.getGraf().consultarTerme(id);
		else n = controladorMultigraf.getGraf().consultarPaper(id);
		return n;
	}

	/**
	 * Indica si un path existeix en el sistema.
	 * @param path El path que volem saber si existeix.
	 * @returns Retorna cert si el path existeix al sistema i fals altrament.
	 */
	private boolean exists(String path) {
		return controladorPaths.consultarPaths().contains(path);
	}

	/**
	 * Crea un threshold amb el path i els nodes indicats.
	 * @param idNode1 L'id del primer node del threshold.
	 * 			El node és del primer tipus de node de path.
	 * @param idNode2 L'id del segon node del threshold.
	 * 			El node és de l'últim tipus de node de path.
	 * @param path El nom del path del threshold.
	 * @param ignorarClausura Indica si es vol ignorar la clausura al fer el càlcul del threshold.
	 * @returns Retorna el threshold que s'ha creat.
	 * @throws IllegalArgumentException si el path o els nodes indicats no existeixen.
	 * @throws IOException si no es pot calcular la rellevancia
	 */
	private Threshold createThreshold(int idNode1, int idNode2, String path, boolean ignorarClausura)
			throws IllegalArgumentException, InterruptedException, IOException {
		if (!exists(path)) throw new IllegalArgumentException("El path del threshold no existeix.");

		Node n1 = getNode(path, 0, idNode1);
		Node n2 = getNode(path, path.length()-1, idNode2);
		if (n1 .getId() == -1 || n2.getId() == -1) throw new IllegalArgumentException ("El node no existeix.");

		return new Threshold(n1, n2, path, controladorMultigraf.getHeteSim(), ignorarClausura);
	}

	/**
	 * Realitza una consulta de tots els nodes rellevants per un node concret
	 * segons un path i aplicant un filtre.
	 * @param n El node per al que volem trobar nodes rellevants.
	 * @param path El path que volem fer servir per realitzar la consulta.
	 * @param filtre La rellevància mínima que han de tenir tots els nodes de la llista.
	 * @param ignorarClausura si es vol ignorar la clausura.
	 * @returns Retorna una llista ordenada de parells de rellevància i node
	 * 			de tots els nodes rellevants per n segons path que tenen una rellevància major que 0
	 * 			i superior o igual a filtre.
	 */
	private ArrayList<Pair<Double, Node>> llistaResultats(Node n, String path, double filtre,
			boolean ignorarClausura)
			throws InterruptedException, IOException {
		HeteSim hs = controladorMultigraf.getHeteSim();
		ArrayList<Entry<Double, Integer>> resultatshs = hs.heteSimAmbIdentificadors(n, path, ignorarClausura);
		ArrayList<Pair<Double, Node>> res = new ArrayList<>();

		for(Entry<Double, Integer> reshs : resultatshs) {
			if (reshs.getKey() < filtre || reshs.getKey() == 0) break;

			Node nodeResultat = copia(getNode(path, path.length()-1, reshs.getValue()));

			Pair<Double, Node> p = new Pair<>(reshs.getKey(), nodeResultat);
			res.add(p);
		}

		return res;
	}
}
