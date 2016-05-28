package presentacio;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JFrame;

import domini.ControladorConsultes;
import domini.ControladorDominiPersistenciaPropi;
import domini.ControladorMultigraf;
import domini.ControladorNodes;
import domini.ControladorPaths;
import domini.ControladorRelacions;

public class ControladorPresentacio {
	
	private final ControladorNodes controladorNodes;
	private final ControladorRelacions controladorRelacions;
	private final ControladorPaths controladorPaths;
	private final ControladorMultigraf controladorMultigraf;
	private final ControladorConsultes controladorConsultes;
	private final ControladorDominiPersistenciaPropi controladorDominiPersistenciaPropi;
	
	private final SelectorConjunts selectorConjunts;
	
	public ControladorPresentacio() throws IOException {
		controladorMultigraf = new ControladorMultigraf();
		controladorNodes = new ControladorNodes(controladorMultigraf);
		controladorRelacions = new ControladorRelacions(controladorMultigraf);
		controladorPaths = new ControladorPaths(controladorMultigraf);
		controladorConsultes = new ControladorConsultes(controladorMultigraf, controladorPaths);
		controladorDominiPersistenciaPropi = new ControladorDominiPersistenciaPropi(controladorMultigraf, controladorPaths, controladorConsultes);
		selectorConjunts = new SelectorConjunts(this);
	}
	
	//**************funcions de controladorNodes****************
	
	/**
	 * Afegeix un autor sense cap etiqueta al graf actual i retorna el seu identificador intern.
	 * @param nom  de l'autor a afegir
	 * @return el seu identificador intern
	 */
	public int afegirAutor(String nom) {
		return controladorNodes.afegirAutor(nom);
	}
	
	/**
	 * Afegeix un autor al graf actual i retorna el seu identificador intern.
	 * @param nom de l'autor a afegir
	 * @param label que tindrà l'autor
	 * @return el seu identificador intern
	 */
	public int afegirAutor(String nom, String label) {
		return controladorNodes.afegirAutor(nom, label);
	}
	
	/**
	 * Afegeix una etiqueta a un autor del graf i retorna el seu identificador intern.
	 * @param label que es vol afegir
	 * @param idAutor es l'id de l'autor
	 * @return identificador intern de l'autor
	 * @throws IllegalArgumentException en cas de que autor no existeixi en el graf actual.
	 */
	public int afegirLabelAutor(String label, int idAutor) throws IllegalArgumentException {
		return controladorNodes.afegirLabelAutor(label, idAutor);
	}
	
	/**
	 * Afegeix una conferència al graf actual sense cap etiqueta i retorna el seu identificador intern.
	 * @param nom de la conferencia a afegir
	 * @return el seu identificador intern
	 */
	public int afegirConferencia(String nom) {
		return controladorNodes.afegirConferencia(nom);
	}
	
	/**
	 * Afegeix una conferència al graf actual i retorna el seu identificador intern.
	 * @param nom de la conferencia a afegir
	 * @param label que tindrà la conferencia
	 * @return el seu identificador intern
	 */
	public int afegirConferencia(String nom, String label) {
		return controladorNodes.afegirConferencia(nom, label);
	}
	
	/**
	 * Afegeix una etiqueta a una conferencia del graf i retorna el seu identificador intern.
	 * Llençara una excepció en cas de que conferencia no existeixi en el graf actual.
	 * @param label que es vol afegir
	 * @param idConferencia es l'id de la conferencia
	 * @return identificador intern de la conferencia
	 * @throws IllegalArgumentException en cas de que la conferencia no existeixi en el graf actual.
	 */
	public int afegirLabelConferencia(String label, int idConferencia) throws IllegalArgumentException {
		return controladorNodes.afegirLabelConferencia(label, idConferencia);
	}
	
	/**
	 * Afegeix un paper al graf actual sense cap etiqueta i retorna el seu identificador intern.
	 * @param nom del paper a afegir
	 * @return el seu identificador intern
	 */
	public int afegirPaper(String nom) {
		return controladorNodes.afegirPaper(nom);
	}
	
	/**
	 * Afegeix un paper al graf actual i retorna el seu identificador intern.
	 * @param nom del paper a afegir
	 * @param label que tindrà el paper
	 * @return el seu identificador intern
	 */
	public int afegirPaper(String nom, String label) {
		return controladorNodes.afegirPaper(nom, label);
	}
	
	/**
	 * Afegeix una etiqueta a un paper del graf i retorna el seu identificador intern.
	 *  Llençarà una excepció en cas de que paper no existeixi en el graf actual.
	 * @param label que es vol afegir
	 * @param idPaper es l'id del paper
	 * @return identificador intern del paper
	 * @throws IllegalArgumentException en cas de que el paper no existeixi en el graf actual.
	 */
	public int afegirLabelPaper(String label, int idPaper) throws IllegalArgumentException {
		return controladorNodes.afegirLabelPaper(label, idPaper);
	}
	
	/**
	 * Afegeix un terme al graf actual sense cap etiqueta i retorna el seu identificador intern.
	 * @param nom del terme a afegir
	 * @return el seu identificador intern
	 */
	public int afegirTerme(String nom) {
		return controladorNodes.afegirTerme(nom);
	}

	/**
	 * Modifica el nom d’un autor. 
	 * @param nouNom es el nou nom que tindra l'autor
	 * @param idAutor es l'id de l'autor a modificar
	 * @return si ha sigut possible (si existeix).
	 */
	public boolean modificarAutor(String nouNom, int idAutor) {
		return controladorNodes.modificarAutor(nouNom, idAutor);
	}
	
	/**
	 * Modifica el nom d’un paper.
	 * @param nouNom es el nou nom que tindra el paper
	 * @param idPaper es l'id del paper a modificar
	 * @return si ha sigut possible (si existeix).
	 */
	public boolean modificarPaper(String nouNom, int idPaper) {
		return controladorNodes.modificarPaper(nouNom, idPaper);
	}
	
	/**
	 * Modifica el nom d’una conferencia
	 * @param nouNom es el nou nom que tindra la conferencia
	 * @param idConferencia es l'id de la conferencia a modificar
	 * @return si ha sigut possible (si existeix).
	 */
	public boolean modificarConferencia(String nouNom, int idConferencia) {
		return controladorNodes.modificarConferencia(nouNom, idConferencia);
	}
	
	/**
	 * Modifica el nom d’un terme.
	 * @param nouNom es el nou nom que tindra el terme
	 * @param idTerme es l'id del terme a modificar
	 * @return si ha sigut possible (si existeix).
	 */
	public boolean modificarTerme(String nouNom, int idTerme) {
		return controladorNodes.modificarTerme(nouNom, idTerme);
	}
	
	/**
	 * Elimina un autor.
	 * @param idAutor de l'autor a eliminar.
	 * @return si ha sigut possible (si existia).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean eliminarAutor(int idAutor) throws IOException {
		boolean del = controladorNodes.eliminarAutor(idAutor);
		if (del)
			controladorMultigraf.getHeteSim().setUpdateAll("A", false);
		return del;
	}
	
	/**
	 * Elimina un paper.
	 * @param idPaper del paper a eliminar.
	 * @return si ha sigut possible (si existia).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean eliminarPaper(int idPaper) throws IOException {
		boolean del = controladorNodes.eliminarPaper(idPaper);
		if (del)
			controladorMultigraf.getHeteSim().setUpdateAll("P", false);
		return del;
	}
	
	/**
	 * Elimina una conferencia.
	 * @param idConferencia de la conferencia a eliminar.
	 * @return si ha sigut possible (si existia).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean eliminarConferencia(int idConferencia) throws IOException {
		boolean del = controladorNodes.eliminarConferencia(idConferencia);
		if (del)
			controladorMultigraf.getHeteSim().setUpdateAll("C", false);
		return del;
	}
	
	/**
	 * Elimina un terme.
	 * @param idTerme del terme a eliminar.
	 * @return si ha sigut possible (si existia).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean eliminarTerme(int idTerme) throws IOException {
		boolean del = controladorNodes.eliminarTerme(idTerme);
		if (del)
			controladorMultigraf.getHeteSim().setUpdateAll("T", false);
		return del;
	}

	/**
	 * Consulta el nom d’un autor.
	 * @param idAutor de l'autor que es vol consultar
	 * @return el nom de l'autor 
	 */
	public String consultarNomAutor(int idAutor) {
		return controladorNodes.consultarNomAutor(idAutor);
	}
	
	/**
	 * Consulta el nom d’un paper.
	 * @param idPaper del paper que es vol consultar
	 * @return el nom del paper 
	 */
	public String consultarNomPaper(int idPaper) {
		return controladorNodes.consultarNomPaper(idPaper);
	}
	
	/**
	 * Consulta el nom d’una conferencia.
	 * @param idConferencia de la conferencia que es vol consultar
	 * @return el nom de la conferencia 
	 */
	public String consultarNomConferencia(int idConferencia) {
		return controladorNodes.consultarNomConferencia(idConferencia);
	}
	
	/**
	 * Consulta el nom d’un terme.
	 * @param idTerme del terme que es vol consultar
	 * @return el nom del terme 
	 */
	public String consultarNomTerme(int idTerme) {
		return controladorNodes.consultarNomTerme(idTerme);
	}
	
	/**
	 * Consulta la label d'un autor
	 * @param idAutor de l'autor
	 * @return label 
	 */
    public String consultarLabelAutor(int idAutor){
	    return controladorNodes.consultarLabelAutor(idAutor);
	}
    
    /**
	 * Consulta la label d'un paper
	 * @param idPaper del paper
	 * @return label 
	 */
	public String consultarLabelPaper(int idPaper){
	    return controladorNodes.consultarLabelPaper(idPaper);
	}
	
	/**
	 * Consulta la label d'una conferencia
	 * @param idConferencia de la conferencia
	 * @return label 
	 */
	public String consultarLabelConferencia(int idConferencia){
		  return controladorNodes.consultarLabelConferencia(idConferencia);
	}

	
	//**************funcions de controladorRelacions****************
	
	
	/**
	 * Afegeix una adjacencia al graf actual entre un paper i un autor.
	 * @param idPaper del paper
	 * @param idAutor de l'autor
	 * @return si ha sigut possible (si ambdos existeixen).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean afegirAdjacenciaPaperAutor(int idPaper, int idAutor) throws IOException {
		boolean add = controladorRelacions.afegirAdjacenciaPaperAutor(idPaper, idAutor);
		if (add)
			controladorMultigraf.getHeteSim().setUpdateAll("AP|PA", false);
		return add;
	}
	
	/**
	 * Afegeix una adjacencia al graf actual entre un paper i un terme.
	 * @param idPaper del paper
	 * @param idTerme del terme
	 * @return si ha sigut possible (si ambdos existeixen).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean afegirAdjacenciaPaperTerme(int idPaper, int idTerme) throws IOException {
		boolean add = controladorRelacions.afegirAdjacenciaPaperTerme(idPaper, idTerme);
		if (add)
			controladorMultigraf.getHeteSim().setUpdateAll("TP|PT", false);
		return add;
	}

	/**
	 * Estableix una adjacencia al graf actual entre un paper i una conferencia.
	 * En cas de que hi hagués prèviament una altra relació entre aquests dos nodes 
	 * aquesta és eliminada ja que un paper només pot estar relacionat amb una conferència.
	 * @param idPaper del paper
	 * @param idConferencia de la conferencia
	 * @return si ha sigut possible (si ambdos existeixen).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean setAdjacenciaPaperConferencia(int idPaper, int idConferencia) throws IOException {
		boolean set = controladorRelacions.setAdjacenciaPaperConferencia(idPaper, idConferencia);
		if (set)
			controladorMultigraf.getHeteSim().setUpdateAll("CP|PC", false);
		return set;
	}
	
	/**
	 * Retorna si els dos nodes estan relacionats
	 * @param idPaper
	 * @param idAutor
	 * @return si els dos nodes estan relacionats
	 */
	public boolean existeixRelacioPaperAutor(int idPaper, int idAutor) {
		return controladorRelacions.existeixRelacioPaperAutor(idPaper, idAutor);
	}
	
	/**
	 * Retorna si els dos nodes estan relacionats
	 * @param idPaper
	 * @param idTerme
	 * @return si els dos nodes estan relacionats
	 */
	public boolean existeixRelacioPaperTerme(int idPaper, int idTerme) {
		return controladorRelacions.existeixRelacioPaperAutor(idPaper, idTerme);
	}
	
	/**
	 * Retorna si els dos nodes estan relacionats
	 * @param idPaper
	 * @param idConferencia
	 * @return si els dos nodes estan relacionats
	 */
	public boolean existeixRelacioPaperConferencia(int idPaper, int idConferencia) {
		return controladorRelacions.existeixRelacioPaperAutor(idPaper, idConferencia);
	}
	
	
	/**
	 * Elimina la adjacencia entre un paper i un autor.
	 * @param idPaper del paper
	 * @param idAutor de l'autor
	 * @return si ha sigut possible (si ambdos existeixen).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean eliminarAdjacenciaPaperAutor(int idPaper, int idAutor) throws IOException {
		boolean del = controladorRelacions.eliminarAdjacenciaPaperAutor(idPaper, idAutor);
		if (del)
			controladorMultigraf.getHeteSim().setUpdateAll("AP|PA", false);
		return del;
	}
	
	/**
	 * Elimina la adjacencia entre un paper i un terme.
	 * @param idPaper del paper
	 * @param idTerme del terme
	 * @return si ha sigut possible (si ambdos existeixen).
	 * @throws IOException si no es poden llegir/escriure els fitxers de clausures
	 */
	public boolean eliminarAdjacenciaPaperTerme(int idPaper, int idTerme) throws IOException {
		boolean del = controladorRelacions.eliminarAdjacenciaPaperTerme(idPaper, idTerme);
		if (del)
			controladorMultigraf.getHeteSim().setUpdateAll("TP|PT", false);
		return del;
	}

	/**
	 * Consulta totes les relacions d’una conferencia.
	 * @param idConferencia de la conferencia
	 * @return una llista que conté els noms de tots els papers relacionats.
	 */
	public List<String> consultarRelacionsConferencia(int idConferencia) {
		return controladorRelacions.consultarRelacionsConferencia(idConferencia);
	}
	
	/**
	 * Consulta totes les relacions d’un autor.
	 * @param idAutor de l'autor
	 * @return una llista que conté els noms de tots els papers relacionats.
	 */
	public List<String> consultarRelacionsAutor(int idAutor) {
		return controladorRelacions.consultarRelacionsAutor(idAutor);
	}
	
	/**
	 * Consulta totes les relacions d’un terme.
	 * @param idTerme del terme
	 * @return una llista que conté els noms de tots els papers relacionats.
	 */
	public List<String> consultarRelacionsTerme(int idTerme) {
		return controladorRelacions.consultarRelacionsTerme(idTerme);
	}
	
	/**
	 * Consulta totes les relacions d’un paper amb autors.
	 * @param idPaper del paper
	 * @return una llista que conté els noms dels autors relacionats amb el paper.
	 */
	public List<String> consultarRelacionsPaperAmbAutor(int idPaper) {
		return controladorRelacions.consultarRelacionsPaperAmbAutor(idPaper);
	}
	
	/**
	 * Consulta totes les relacions d’un paper amb termes.
	 * @param idPaper del paper
	 * @return una llista que conté els noms dels termes relacionats amb el paper.
	 */
	public List<String> consultarRelacionsPaperAmbTerme(int idPaper) {
		return controladorRelacions.consultarRelacionsPaperAmbTerme(idPaper);
	}
	
	/**
	 * Consulta totes les relacions d’un paper amb conferencies.
	 * @param idPaper del paper
	 * @return una llista que conté els noms de les conferencies relacionades amb el paper.
	 */
	public List<String> consultarRelacionsPaperAmbConferencia(int idPaper) {
		return controladorRelacions.consultarRelacionsPaperAmbConferencia(idPaper);
	}
	
	//**************funcions de controladorPaths****************

	/**
	 * Afegeix un nou path 
	 * @param path
	 * @param definicio del path
	 * @return si ha sigut possible (el path no existia).
	 */
	public boolean afegir(String path, String definicio) {
		return controladorPaths.afegir(path, definicio);
	}
	
	/**
	 * Afegeix un nou path sense definicio
	 * @param path
	 * @return si ha sigut possible (el path no existia).
	 */
	public boolean afegir(String path) {
		return controladorPaths.afegir(path);
	}

	/**
	 * Modifica la definció d’un path 
	 * @param path
	 * @param definicio nova del path
	 * @return si ha sigut possible (el path existeix).
	 * @return
	 */
	public boolean modificarDefinicio(String path, String definicio) {
		return controladorPaths.modificarDefinicio(path, definicio);
	}

	/**
	 * Esborra un path
	 * @param path
	 * @return si ha sigut possible (el path existia).
	 */
	public boolean esborrar(String path) {
		return controladorPaths.esborrar(path);
	}

	/**
	 * Retorna una llista amb tots els paths
	 * @return una llista amb tots els paths
	 */
	public List<String> consultarPaths() {
		return controladorPaths.consultarPaths();
	}
	
	/**
	 * Retorna la definicio d’un path
	 * @param path
	 * @return la definicio del path
	 */
	public String consultarDefinicio(String path) {
		return controladorPaths.consultarDefinicio(path);
	}

	/**
	 * Guarda tots els paths en el fitxer donat. Si el fitxer existia es sobreescriu. 
	 * @param filesystem_path es el path del fitxer on es volen guardar els paths.
	 * @throws IOException en cas de no poder-se escriure en el fitxer.
	 */
	public void guardarPaths(String filesystem_path) throws IOException {
		controladorPaths.guardarPaths(filesystem_path);
	}

	/**
	 * Carrega tots els paths des d’un fitxer.
	 * @param filesystem_path es el path del fitxer des d'on es volen carregar els paths.
	 * @throws FileNotFoundException en cas de que no es trobi el fitxer
	 * @throws IOException si no es pot llegir el fitxer
	 */
	public void carregarPaths(String filesystem_path) throws IOException {
		controladorPaths.carregarPaths(filesystem_path);
	}

	//**************funcions de controladorMultigraf****************
	
	
	/**
	 * Consulta l'ID del graf actual.
	 * @return l'ID del graf actual.
	 */
	public String getIdActual() {
		return controladorMultigraf.getIdActual();
	}
	
	/**
	 * Marca les clausures del graf actual com a invalides (ja que s'ha modificat el graf)
	 * @throws IOException 
	 */
	public void clausuresInvalides() throws IOException {
		controladorMultigraf.getHeteSim().invalidarClausures();
	}
	
	/**
	 * Retorna una llista amb els noms de tots els grafs carregats.
	 * @return llista amb els noms de tots els grafs carregats.
	 */
	public List<String> getNomsGrafs() {
		return controladorMultigraf.getNomsGrafs();
	}
	
	/**
	 * Crea una nova entrada a grafs amb el nou graf de nom nomGraf i una altra a controladors (els dos seran buits).
	 * El graf actual i l'id actual passen a ser els d'aquest graf.
	 * @param nomGraf es el nom del nou graf.
	 */
	public void afegirGraf(String nomGraf) throws IOException {
		controladorMultigraf.afegirGraf(nomGraf);
	}
	
	
	/**
	 * Importa i crea un graf que tindrà el nom donat mitjançant els fitxers
	 * del directori que es passen com a paràmetre (autors, papers, conferències, 
	 * labels i relacions). Aquest graf s’estableix com l’actual. 
	 * El graf passa a ser el graf importat i idActual passarà a ser nomGraf.
	 * @param nomGraf que és el nom del nou Graf importat
	 * @param directori on es troben els fitxers a patir dels quals es fa la importació.
	 * @throws IOException en cas de que algun fitxer no tingui el format correcte 
	 * o bé no sigui possible llegir-lo.
	 * @throws FileNotFoundException en cas de que algun fitxer no es trobi.
	 */
	public void importar(String nomGraf, String directori) throws IOException {
		controladorMultigraf.importar(nomGraf, directori);
	}
	
	/**
	 * Consulta si un graf est� carregat.
	 * @param nomGraf que és el nom del Graf que es vol consultar.
	 * @return si el graf existeix i est� carregat.
	 */
	public boolean exists(String nomGraf) {
		return controladorMultigraf.exists(nomGraf);
	}
	
	/**
	 * Selecciona el graf amb nom "nomGraf" com a graf actual.
	 * @param nomGraf que és el nom del Graf que es vol seleccionar.
	 * @return si ha sigut possible la selecció del graf (el graf existia).
	 */
	public boolean seleccionarGraf(String nomGraf) throws IOException {
		return controladorMultigraf.seleccionarGraf(nomGraf);
	}
	
	/**
	 * Carrega el grafs amb les seves clausures dels fitxers corresponents al directori donat. 
	 * @param directori on estan continguts els fitxers a carregar
	 * @throws IOException si no es pot llegir algun fitxer
	 * @throws FileNotFoundException si no es troba algun fitxer
	 */
	public void carregar(String directori) throws IOException, FileNotFoundException {
		controladorMultigraf.carregar(directori);

	}
	
	
	/**
	 * Guarda tots els grafs i les seves clausures en el directori donat.
	 * Si algun fitxer existia es sobreescriu.
	 * @throws IOException en cas de no poder-se escriure algun fitxer.
	 */
	public void guardar(String directori) throws IOException {
		controladorMultigraf.guardar(directori);
	}

	/**
	 * Esborra el fitxer on esta guardat el graf actual
	 * @throws IOException en cas de no poder-se esborrar el fitxer.
	 */
	public void esborrarFitxerGraf() throws IOException {
		controladorMultigraf.esborrarFitxerGraf();
	}
	
	//**************funcions de controladorGraf****************

	/** Retorna una llista amb els identificadors de tots els papers amb el nom del paràmetre.
	 * @retrun Retorna una llista amb els identificadors de tots els papers amb el nom del paràmetre.
	 */
	public List<Integer> consultarPaper(String nom){
		return controladorMultigraf.consultarPaper(nom);
	}
	
	/** Retorna una llista amb els identificadors de totes les conferències amb el nom del paràmetre.
     * @retrun Retorna una llista amb els identificadors de totes les conferències amb el nom del paràmetre.
     */
    public List<Integer> consultarConferencia(String nom){
		return controladorMultigraf.consultarConferencia(nom);	    
	}
    
    /** Retorna una llista amb els identificadors de tots els termes amb el nom del paràmetre.
     * @retrun Retorna una llista amb els identificadors de tots els termes amb el nom del paràmetre.
     */
    public List<Integer> consultarTerme(String nom){
		return controladorMultigraf.consultarTerme(nom);
	}
    
    /** Retorna una llista amb els identificadors de tots els autors amb el nom del paràmetre.
     * @retrun Retorna una llista amb els identificadors de tots els autors amb el nom del paràmetre.
     */
    public List<Integer> consultarAutor(String nom){
		return controladorMultigraf.consultarAutor(nom);
	}
    
    /**
     * Retorna un treemap amb els id i noms dels papers
     * @return un treemap amb els id i noms dels papers
     */
    public TreeMap<Integer,String> consultarPapers(){
		return controladorMultigraf.consultarPapers();
    }

    /**
     * Retorna un treemap amb els id i noms dels autors
     * @return un treemap amb els id i noms dels autors
     */
    public TreeMap<Integer,String> consultarAutors(){
		return controladorMultigraf.consultarAutors();
    }

    /**
     * Retorna un treemap amb els id i noms de les conferencies
     * @return un treemap amb els id i noms de les conferencies
     */
    public TreeMap<Integer,String> consultarConferencies(){
		return controladorMultigraf.consultarConferencies();
    }

    /**
     * Retorna un treemap amb els id i noms dels termes
     * @return un treemap amb els id i noms dels termes
     */
    public TreeMap<Integer,String> consultarTermes(){
		return controladorMultigraf.consultarTermes();
    }
    
    /** Consulta quants autors hi ha en el graf actual.
     * @return Retrona el nombre d'autors del graf
     */
    public int consultarMidaAutors(){
		return controladorMultigraf.consultarMidaAutors();
	}
    
    /** Consulta quants papers hi ha en el graf actual.
     * @return Retrona el nombre de papers del graf
     */
    public int consultarMidaPapers(){
		return controladorMultigraf.consultarMidaPapers();   
	}
    
    /** Consulta quants termes hi ha en el graf actual.
     * @return Retrona el nombre de termes del graf
     */
    public int consultarMidaTermes(){
		return controladorMultigraf.consultarMidaTermes();      
	}
    
    /** Consulta quantes conferències hi ha en el graf actual.
     * @return Retrona el nombre de conferències del graf
     */
    public int consultarMidaConferencies(){
		return controladorMultigraf.consultarMidaConferencies();
	}

	
	

	//**************funcions de controladorConsultes****************


	/**
	 * Consultora de l'ultim resultat.
	 * @returns Retorna un String que representa el resultat de l'ultima consulta.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public String consultarResultat() throws IllegalArgumentException {
		return controladorConsultes.consultarResultat();
		}
	
	/**
	 * Consultora d'un resultat s'una data concreta.
	 * @param data. La data el resultat de la qual volem consultar.
	 * @returns Retorna un String que representa el resultat de la data indicada.
	 * @throws IllegalArgumentException si no existeix cap consulta realitzada en la data indicada.
	 */
	public String consultarResultat(Date data) throws IllegalArgumentException {
		return controladorConsultes.consultarResultat(data);
	}
	
	/**
	 * Consultora de les dates en que s'han realitzat consultes.
	 * @returns Retorna un String que representa totes les dates en que s'han realitzat consultes.
	 */
	public String consultarDates() {
		return controladorConsultes.consultarDates();
	}
	
	/**
	 * Realitza una consulta de rellevancies a partir d'un node i un path.
	 * @param path. El nom del path que es vol fer servir per calcular rellevancies.
	 * @param idNode. L'id del node del que es vol obtenir rellevancies amb altres nodes.
	 * 			El node es del primer tipus de node del path.
	 * @returns Retorna un String que representa el resultat de la consulta,
	 * 			amb la llista de tots els nodes (de l'ultim tipus de node del path)
	 * 			rellevants pel node amb id idNode segons el path indicat.
	 * @throws IllegalArgumentException si no existeixen el path o el node indicats.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public String consulta(String path, int idNode) throws IllegalArgumentException, InterruptedException, IOException {
		return controladorConsultes.consulta(path, idNode);
	}
	
	/**
	 * Realitza una consulta de rellevancies a partir d'un node i un path i fent servir un threshold com a filtre.
	 * @param path. El nom del path que es vol fer servir per calcular rellevancies.
	 * @param idNode. L'id del node del que es vol obtenir rellevancies amb altres nodes.
	 * 			El node es del primer tipus de node del path.
	 * @param idNodeThreshold1. L'id del primer node del threshold. El node es del primer tipus de node del path del threshold.
	 * @param idNodeThreshold2. L'id del segon node del threshold. El node es de l'ultim tipus de node del path del threshold.
	 * @param thresholdPath. El nom del path del threshold.
	 * @returns Retorna un String que representa el resultat de la consulta,
	 * 			amb la llista de tots els nodes (de l'ultim tipus de node del path)
	 * 			rellevants pel node amb id idNode segons el path indicat que passen el threshold.
	 * @throws IllegalArgumentException si no existeixen el path o el node indicats
	 * 			o be algun dels nodes o el path del threshold no existeixen.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public String consulta(String path, int idNode, int idNodeThreshold1, int idNodeThreshold2, String thresholdPath) throws IllegalArgumentException, InterruptedException, IOException {
		return controladorConsultes.consulta(path, idNode, idNodeThreshold1, idNodeThreshold2, thresholdPath);
	}
	
	/**
	 * Esborra la consulta indicada.
	 * @param data. La data la consulta de la qual volem esborrar.
	 * @returns Retorna cert si s'ha pogut esborrar la consulta (existeix una consulta amb la data indicada)
	 * 			i fals altrament.
	 */
	public boolean esborrarConsulta(Date data) {
		return controladorConsultes.esborrarConsulta(data);
	}
	
	/**
	 * Elimina tots els resultats de l'ultima consulta excepte els n primers.
	 * @param n. El nombre de resultats de mes rellevancia que no s'esborraran.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public void filtrarElsPrimers(int n) throws IllegalArgumentException {
		controladorConsultes.filtrarElsPrimers(n);
	}
	
	/**
	 * Elimina tots els resultats de l'ultima consulta excepte els n ultims.
	 * @param n. El nombre de resultats de menys rellevancia que no s'esborraran.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public void filtrarElsUltims(int n) throws IllegalArgumentException {
		controladorConsultes.filtrarElsUltims(n);
	}
	
	/**
	 * Elimina tots els resultats de l'ultima consulta excepte els resultats els nodes dels quals tenen l'etiqueta label.
	 * @param label. L'etiqueta dels nodes dels resultats que no s'han d'eliminar.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public void filtrarPerEtiqueta(String label) throws IllegalArgumentException {
		controladorConsultes.filtrarPerEtiqueta(label);
	}
	
	/**
	 * Elimina tots els resultats de l'ultima consulta excepte els que tenen una rellevancia entre min i max, ambdos inclosos.
	 * @param min. La rellevancia minima dels resultats que no s'han d'eliminar.
	 * @param max. La rellevancia maxima dels resultats que no s'han d'eliminar.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public void filtrarPerRellevancia(double min, double max) throws IllegalArgumentException {
		controladorConsultes.filtrarPerRellevancia(min, max);
	}
	
	/**
	 * Esborra tots els resultats de l'ultima consulta.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public void clear() throws IllegalArgumentException {
		controladorConsultes.clear();
	}
	
	/**
	 * Afegeix a l'ultima consulta un resultat amb la rellevancia i el node indicats.
	 * @param rellevancia. La rellevancia del node que s'afegeix.
	 * @param idNode. L'id del node que s'afegeix.
	 * 			El node es de l'ultim tipus de node del path de l'ultima consulta.
	 * @throws IllegalArgumentException si no existeix una ultima consulta
	 * 			o si no existeix el node indicat.
	 */
	public void afegir(double rellevancia, int idNode) throws IllegalArgumentException {
		controladorConsultes.afegir(rellevancia, idNode);
	}
	
	/**
	 * Esborra de l'ultima consulta el resultat indicat.
	 * @param index. La posicio del resultat que es vol esborrar.
	 * @returns Retorna cert si s'ha pogut esborrar el resultat (index es troba dins d'un rang correcte)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean esborrar(int index) throws IllegalArgumentException {
		return controladorConsultes.esborrar(index);
	}
	
	/**
	 * Consultora del tipus dels nodes dels resultats de l'ultima consulta.
	 * @returns Retorna el tipus dels nodes dels reultats de l'ultima consulta:
	 * 			"Autor", "Conferencia", "Terme" o "Paper".
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public String getTipusNode() throws IllegalArgumentException{
		return controladorConsultes.getTipusNode();
	}
	
	/**
	 * Modifica la rellevancia del resultat indicat de l'ultima consulta.
	 * S'ha de tenir en compte que despres d'aquesta crida si retorna cert llavors
	 * es probable que aquesta posicio hagi canviat degut a l'ordre dels resultats.
	 * @param index. La posicio del resultat que es vol modificar.
	 * @param rellevancia. La rellevancia per la qual es vol modificar l'actual.
	 * @returns Retorna cert si s'ha pogut modificar el resultat
	 * 			(index es troba dins d'un rang correcte i 0 <= rellevancia <= 1)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean setRellevancia(int index, double rellevancia) throws IllegalArgumentException {
		return controladorConsultes.setRellevancia(index, rellevancia);
	}
	
	/**
	 * Modifica la dada del resultat indicat de l'ultima consulta.
	 * @param index. La posicio del resultat que es vol modificar.
	 * @param idNode. L'id del node pel qual es vol modificar l'actual.
	 * 			El node es de l'ultim tipus de node del path de l'ultima consulta.
	 * @returns Retorna cert si s'ha pogut modificar el resultat
	 * 			(index es troba dins d'un rang correcte i el node existeix)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean setDada(int index, int idNode) throws IllegalArgumentException {
		return controladorConsultes.setDada(index, idNode);
	}
	
	/**
	 * Modifica el nom de la dada del resultat indicat de l'ultima consulta.
	 * @param index. La posicio del resultat que es vol modificar.
	 * @param nom. El nom pel qual es vol modificar l'actual.
	 * @returns Retorna cert si s'ha pogut modificar el resultat (index es troba dins d'un rang correcte)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean canviarNom(int index, String nom) throws IllegalArgumentException {
		return controladorConsultes.canviarNom(index, nom);
	}
	
	/**
	 * Modifica el threshold de l'ultima consulta i refa la consulta.
	 * @param idNode1. L'id del primer node del nou threshold.
	 * 			El node es del primer tipus de node del path del threshold.
	 * @param idNode2. L'id del segon node del nou threshold.
	 * 			El node es de l'ultim tipus de node del path del threshold.
	 * @param path. El nom del path del nou threshold.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setThreshold(int idNode1, int idNode2, String path) throws IllegalArgumentException, InterruptedException, IOException {
		controladorConsultes.setThreshold(idNode1, idNode2, path);
	}
	
	/**
	 * Modifica el path i la dada de l'ultima consulta i refa la consulta.
	 * @param path. El nom del path pel qual es vol modificar l'actual.
	 * @param id. L'id del node pel qual es vol modificar l'actual.
	 * 			El node es del primer tipus de node de path.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setPath(String path, int id) throws IllegalArgumentException, InterruptedException, IOException {
		controladorConsultes.setPath(path, id);
	}
	
	/**
	 * Modifica la dada de l'ultima consulta i refa la consulta.
	 * @param id. L'id del node pel qual es vol modificar l'actual.
	 * 			El node es del primer tipus de node del path de l'ultima consulta.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setDada(int id) throws IllegalArgumentException, InterruptedException, IOException {
		controladorConsultes.setDada(id);
	}
	
	/**
	 * Exporta l'ultima consulta al fitxer indicat.
	 * @param filesystem_path. El fitxer on es vol exportar la consulta.
	 * @throws IOException si no es pot crear o escriure en el fitxer indicat.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public void exportarResultat(String filesystem_path) throws IOException, IllegalArgumentException {
		controladorConsultes.exportarResultat(filesystem_path);
	}
	
	/**
	 * Guarda totes les consultes al fitxer per defecte.
	 * @throws IOException si no es pot crear o escriure en el fitxer per defecte.
	 */
	public void guardarResultats() throws IOException {
		controladorConsultes.guardarResultats();
	}
	
	/**
	 * Carrega totes les consultes del fitxer per defecte.
	 * @throws IOException si no existeix el fitxer per defecte o si no es pot llegir o no te el format correcte.
	 */
	public void carregarResultats() throws IOException {
		controladorConsultes.carregarResultats();
	}

	//**************funcions de controladorDominiPersistenciaPropi****************
	
	/**
	 * Guarda totes les dades que es poden (fa una crida als mètodes de guardar grafs
	 * i clausures, paths i resultats) als fitxers corresponents als paths per defecte.
	 * Si algun fitxer existia es sobreescriu.
	 * @throws IOException en cas de no poder-se escriure en els fitxers.
	 */
	public void guardarDades() throws IOException {
		controladorDominiPersistenciaPropi.guardarDades();
	}

	/**
	 * Carrega totes les dades (fa una crida als mètodes de carregar grafs i 
	 * clausures, paths i resultats) pels fitxers per defecte existents. 
	 * @throws IOException en cas de no poder llegir els fitxers.
	 */
	public void carregarDades() throws IOException {
		controladorDominiPersistenciaPropi.carregarDades();
	}

	//**************funcions de presentació****************

	public static void configurarNovaFinestra(JFrame from, JFrame to) {
		to.setVisible(true);
		from.setVisible(false);
		to.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				from.setVisible(true);
			}
		});
	}
	
	public SelectorConjunts getSelectorConjunts() {
		return selectorConjunts;
	}

}
