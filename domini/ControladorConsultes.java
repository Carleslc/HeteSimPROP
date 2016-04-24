package domini;

/**
 * @author Carla Claverol
 */

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.io.IOException;

import persistencia.*;

public class ControladorConsultes {
	
	public static String DEFAULT_PATH_RESULTATS = "resultats.dat";
	private TreeMap<Date, Resultat> resultats;
	private Date ultimaConsulta;
	private ControladorMultigraf controladorMultigraf;
	private ControladorPaths controladorPaths;
	
	public ControladorConsultes(ControladorMultigraf controladorMultigraf, ControladorPaths controladorPaths) {
		resultats = new TreeMap<>();
		ultimaConsulta = null;
		this.controladorMultigraf = controladorMultigraf;
		this.controladorPaths = controladorPaths;
	}
	
	public String consultarResultat() {
		return resultats.get(ultimaConsulta).toString();
	}
	
	public String consultarResultat(Date data) throws IllegalArgumentException {
		if (!resultats.containsKey(data))
			throw new IllegalArgumentException("No existeix cap consulta realitzada en la data especificada.");
		ultimaConsulta = data;
		return resultats.get(data).toString();
	}
	
	public String consulta(String path, int idNode) throws IllegalArgumentException {
		Node n = getNode(path, 0, idNode);
		if (n == null) throw new IllegalArgumentException ("El node no existeix.");
		
		ArrayList<Pair<Double, Node>> res = llistaResultats(n, path, 1.);
		
		Resultat r = new Resultat(n, path, controladorMultigraf.getNom(), res);
		Date d = new Date();
		resultats.put(d, r);
		ultimaConsulta = d;
		return r.toString();
	}
	
	public String consulta(String path, int idNode, int idNodeThreshold1, int idNodeThreshold2, String thresholdPath) throws IllegalArgumentException {
		Node n = getNode(path, 0, idNode);
		if (n == null) throw new IllegalArgumentException ("El node no existeix.");
		
		Threshold t = createThreshold(idNodeThreshold1, idNodeThreshold2, thresholdPath);
		ArrayList<Pair<Double, Node>> res = llistaResultats(n, path, t.getRellevancia());
		
		Resultat r = new Resultat(n, path, controladorMultigraf.getNom(), res, t);
		Date d = new Date();
		resultats.put(d, r);
		ultimaConsulta = d;
		return r.toString();
	}
	
	public boolean esborrarConsulta(Date data) {
		if (!resultats.containsKey(data)) return false;
		if (data.equals(ultimaConsulta)) ultimaConsulta = null;
		resultats.remove(data);
		return true;
	}
	
	public void filtrarElsPrimers(int n) {
		resultats.get(ultimaConsulta).filtrarElsPrimers(n);
	}
	
	public void filtrarElsUltims(int n) {
		resultats.get(ultimaConsulta).filtrarElsUltims(n);
	}
	
	public void filtrarPerEtiqueta(String label) {
		resultats.get(ultimaConsulta).filtrarPerEtiqueta(label);
	}
	
	public void filtrarPerRellevancia(double min, double max) {
		resultats.get(ultimaConsulta).filtrarPerRellevancia(min, max);
	}
	
	public void clear() {
		resultats.get(ultimaConsulta).clear();
	}
	
	public void afegir(double rellevancia, int idNode) {
		String path = resultats.get(ultimaConsulta).getPath();
		Node n = getNode(path, path.length()-1, idNode);
		Pair<Double, Node> p = new Pair<>(rellevancia, n);
		resultats.get(ultimaConsulta).afegir(p);
	}
	
	public void esborrar(int index) {
		resultats.get(ultimaConsulta).esborrar(index);
	}
	
	public String getTipusNode() {
		String path = resultats.get(ultimaConsulta).getPath();
		if (path.charAt(path.length()-1) == 'A') return "Autor";
		if (path.charAt(path.length()-1) == 'C') return "Conferencia";
		if (path.charAt(path.length()-1) == 'T') return "Terme";
		return "Paper";
	}
	
	public boolean setRellevancia(int index, double rellevancia) {
		return resultats.get(ultimaConsulta).setRellevancia(index, rellevancia);
	}
	
	public boolean setDada(int index, int idNode) {
		Resultat r = resultats.get(ultimaConsulta);
		if (index < 0 || index >= r.size()) return false;
		
		String path = resultats.get(ultimaConsulta).getPath();
		Node n = getNode(path, path.length()-1, idNode);
		if (n == null) return false;
		
		r.setDada(index, n);
		return true;
	}
	
	public boolean canviarNom(int index, String nom) {
		Resultat r = resultats.get(ultimaConsulta);
		if (index < 0 || index >= r.size()) return false;
		r.get(index).getValue().setNom(nom);
		return true;
	}
	
	public void setThreshold(int idNode1, int idNode2, String path) {
		Threshold t = createThreshold(idNode1, idNode2, path);
		Resultat r = resultats.get(ultimaConsulta);
		ArrayList<Pair<Double, Node>> res = llistaResultats(r.getNode(), r.getPath(), t.getRellevancia());
		r.setThreshold(t);
		r.setResultats(res);
	}
	
	public void setPath(String path, int id) {
		Resultat r = resultats.get(ultimaConsulta);
		Node n = getNode(path, 0, id);
		ArrayList<Pair<Double, Node>> res = llistaResultats(n, path, r.getThreshold().getRellevancia());
		r.setPath(path);
		r.setNode(n);
		r.setResultats(res);
	}
	
	public void setDada(int id) {
		Resultat r = resultats.get(ultimaConsulta);
		Node n = getNode(r.getPath(), 0, id);
		ArrayList<Pair<Double, Node>> res = llistaResultats(n, r.getPath(), r.getThreshold().getRellevancia());
		r.setNode(n);
		r.setResultats(res);
	}
	
	void exportarResultat(String filesystem_path) throws IOException {
		ControladorExportacions c = new ControladorExportacions(controladorPaths);
		c.exportar(filesystem_path, ultimaConsulta, resultats.get(ultimaConsulta));
	}
	
	void guardarResultats() throws IOException {
		ControladorPersistenciaPropi c = new ControladorPersistenciaPropi();
		c.guardarResultats(DEFAULT_PATH_RESULTATS, resultats);
	}
	
	void carregarResultats() throws IOException {
		ControladorPersistenciaPropi c = new ControladorPersistenciaPropi();
		resultats = c.carregarResultats(DEFAULT_PATH_RESULTATS);
	}
	
	private Node copia(Node n, String tipus) {
		Node res;
		if (tipus == "Autor") res = new Autor();
		else if (tipus == "Conferencia") res = new Conferencia();
		else if (tipus == "Terme") res = new Terme();
		else res = new Paper();
		res.setId(n.getId());
		res.setNom(n.getNom());
		res.setLabel(n.getLabel());
		return res;
	}
	
	private Node getNode(String path, int index, int id) {
		Node n;
		if (path.charAt(index) == 'A') n = controladorMultigraf.getGraf().consultarAutor(id);
		else if (path.charAt(index) == 'C') n = controladorMultigraf.getGraf().consultarConferencia(id);
		else if (path.charAt(index) == 'T') n = controladorMultigraf.getGraf().consultarTerme(id);
		else n = controladorMultigraf.getGraf().consultarPaper(id);
		return n;
	}
	
	private Threshold createThreshold(int idNode1, int idNode2, String path) {
		Node n1 = getNode(path, 0, idNode1);
		Node n2 = getNode(path, path.length()-1, idNode2);
		return new Threshold(n1, n2, path, controladorMultigraf.getHeteSim());
	}
	
	private ArrayList<Pair<Double, Node>> llistaResultats(Node n, String path, Double filtre) {
		HeteSim hs = controladorMultigraf.getHeteSim();
		ArrayList<Entry<Double, Integer>> resultatshs = hs.heteSimAmbIdentificadors(n, path);
		ArrayList<Pair<Double, Node>> res = new ArrayList<>();
		
		for(Entry<Double, Integer> reshs : resultatshs) {
			if (reshs.getKey() < filtre) break;
			
			Node nodeResultat = getNode(path, path.length()-1, reshs.getValue());
			if (path.charAt(path.length()-1) == 'A') nodeResultat = copia(nodeResultat, "Autor");
			else if (path.charAt(path.length()-1) == 'C') nodeResultat = copia(nodeResultat, "Conferencia");
			else if (path.charAt(path.length()-1) == 'T') nodeResultat = copia(nodeResultat, "Terme");
			else if(path.charAt(path.length()-1) == 'P') nodeResultat = copia(nodeResultat, "Paper");
			
			Pair<Double, Node> p = new Pair<>(reshs.getKey(), nodeResultat);
			res.add(p);
		}
		
		return res;
	}
}
