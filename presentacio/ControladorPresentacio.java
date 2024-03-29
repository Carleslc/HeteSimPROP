package presentacio;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFrame;
import domini.ControladorConsultes;
import domini.ControladorDominiPersistenciaPropi;
import domini.ControladorMultigraf;
import domini.ControladorNodes;
import domini.ControladorPaths;
import domini.ControladorRelacions;

/**
 * 
 * @author Arnau Badia Sampera
 *
 */
public class ControladorPresentacio {

	public static final Image ICON_MAIN = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/ocean/computer.gif"));
	public static final Image ICON_ADD = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/ocean/upFolder.gif"));
	public static final Image ICON_DISK = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/ocean/hardDrive.gif"));
	public static final Image ICON_SAVE = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/ocean/floppy.gif"));
	public static final Image ICON_QUESTION = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/Question.gif"));
	public static final Image ICON_WARNING = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/ocean/warning.png"));
	public static final Image ICON_INFO = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/ocean/info.png"));
	public static final Image ICON_ERROR = Toolkit.getDefaultToolkit()
			.createImage(ClassLoader.getSystemResource(
					"javax/swing/plaf/metal/icons/ocean/error.png"));

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
		controladorDominiPersistenciaPropi = new ControladorDominiPersistenciaPropi(
				controladorMultigraf, controladorPaths, controladorConsultes);
		controladorDominiPersistenciaPropi.carregarDades();
		selectorConjunts = new SelectorConjunts(this);
	}

	//**************funcions de controladorNodes****************

	/**
	 * Afegeix un autor sense cap etiqueta al graf actual i retorna el seu identificador intern.
	 * @param nom  de l'autor a afegir
	 * @return el seu identificador intern
	 * @throws IOException si hi ha un error invalidant clausures
	 */
	public int afegirAutor(String nom) throws IOException {
		return controladorNodes.afegirAutor(nom);
	}

	/**
	 * Afegeix un autor al graf actual i retorna el seu identificador intern.
	 * @param nom de l'autor a afegir
	 * @param label que tindr\u00E0 l'autor
	 * @return el seu identificador intern
	 * @throws IOException si hi ha un error invalidant clausures
	 */
	public int afegirAutor(String nom, String label) throws IOException {
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
	 * @throws IOException si hi ha un error invalidant clausures
	 */
	public int afegirConferencia(String nom) throws IOException {
		return controladorNodes.afegirConferencia(nom);
	}

	/**
	 * Afegeix una conferència al graf actual i retorna el seu identificador intern.
	 * @param nom de la conferencia a afegir
	 * @param label que tindr\u00E0 la conferencia
	 * @return el seu identificador intern
	 * @throws IOException si hi ha un error invalidant clausures
	 */
	public int afegirConferencia(String nom, String label) throws IOException {
		return controladorNodes.afegirConferencia(nom, label);
	}

	/**
	 * Afegeix una etiqueta a una conferencia del graf i retorna el seu identificador intern.
	 * Llen\u00E7ara una excepci\u00F3 en cas de que conferencia no existeixi en el graf actual.
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
	 * @throws IOException si hi ha un error invalidant clausures
	 */
	public int afegirPaper(String nom) throws IOException {
		return controladorNodes.afegirPaper(nom);
	}

	/**
	 * Afegeix un paper al graf actual i retorna el seu identificador intern.
	 * @param nom del paper a afegir
	 * @param label que tindr\u00E0 el paper
	 * @return el seu identificador intern
	 * @throws IOException si hi ha un error invalidant clausures
	 */
	public int afegirPaper(String nom, String label) throws IOException {
		return controladorNodes.afegirPaper(nom, label);
	}

	/**
	 * Afegeix una etiqueta a un paper del graf i retorna el seu identificador intern.
	 *  Llen\u00E7ar\u00E0 una excepci\u00F3 en cas de que paper no existeixi en el graf actual.
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
	 * @throws IOException si hi ha un error invalidant clausures
	 */
	public int afegirTerme(String nom) throws IOException {
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
	 * Retorna si existeix la relaci\u00F3 entre els dos nodes
	 * @param idPaper
	 * @param idAutor
	 * @return si existeix la relaci\u00F3 entre els dos nodes
	 */
	public boolean existeixRelacioPaperAutor(int idPaper, int idAutor) {
		return controladorRelacions.existeixRelacioPaperAutor(idPaper, idAutor);
	}

	/**
	 * Retorna si existeix la relaci\u00F3 entre els dos nodes
	 * @param idPaper
	 * @param idTerme
	 * @return si existeix la relaci\u00F3 entre els dos nodes
	 */
	public boolean existeixRelacioPaperTerme(int idPaper, int idTerme) {
		return controladorRelacions.existeixRelacioPaperTerme(idPaper, idTerme);
	}

	/**
	 * Retorna si existeix la relaci\u00F3 entre els dos nodes
	 * @param idPaper
	 * @param idConferencia
	 * @return si existeix la relaci\u00F3 entre els dos nodes
	 */
	public boolean existeixRelacioPaperConferencia(int idPaper, int idConferencia) {
		return controladorRelacions.existeixRelacioPaperConferencia(idPaper, idConferencia);
	}


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
	 * En cas de que hi hagués prèviament una altra relaci\u00F3 entre aquests dos nodes 
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
	 * Modifica la definci\u00F3 d’un path 
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
	public void invalidarClausures() throws IOException {
		controladorMultigraf.getHeteSim().invalidarClausures();
	}

	/**
	 * Actualitza o desactualitza una clausura si est\u00E0 disponible
	 * @param path la clausura a actualitzar/desactualitzar
	 * @param updated si es vol actualitzar o desactualitzar
	 * @throws IOException si no es pot llegir/escriure el fitxer de clausura
	 */
	public void invalidarClausura(String path) throws IOException {
		controladorMultigraf.getHeteSim().setUpdated(path, false);
	}

	/**
	 * Consulta si una clausura est\u00E0 actualitzada
	 * @param path la clausura a comprovar
	 * @return Si la clausura est\u00E0 disponible retorna si est\u00E0 actualitzada.
	 * Retorna <code>false</code> si la clausura no est\u00E0 disponible.
	 */
	public boolean isUpdatedClausura(String path) {
		return controladorMultigraf.getHeteSim().isUpdated(path);
	}

	/**
	 * Consulta si est\u00E0 disponible una clausura
	 * @param path la clausura a comprovar
	 * @return si esta disponible la clausura
	 */
	public boolean existsClausura(String path) {
		return controladorMultigraf.getHeteSim().existsClausura(path);
	}

	/**
	 * Actualitza a disc la clausura per un path
	 * @param path el path de la clausura que volem calcular
	 * @param ignorarClausura true si no es vol utilitzar cap clausura existent
	 * (es calculara com si no hi fos)
	 * @throws IllegalArgumentException si <b>path</b> es <code>null</code> o es buit
	 * @throws InterruptedException si s'aborta el c\u00E0lcul
	 * @throws IOException si la clausura existeix i no es pot llegir
	 */
	public void clausura(String path, boolean ignorarClausura)
			throws IllegalArgumentException, IOException, InterruptedException {
		if (controladorMultigraf.getHeteSim().esGuardenClausures())
			controladorMultigraf.getHeteSim().clausura(path, ignorarClausura);
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
	 * Importa i crea un graf que tindr\u00E0 el nom donat mitjan\u00E7ant els fitxers
	 * del directori que es passen com a par\u00E0metre (autors, papers, conferències, 
	 * labels i relacions). Aquest graf s’estableix com l’actual. 
	 * El graf passa a ser el graf importat i idActual passar\u00E0 a ser nomGraf.
	 * @param nomGraf que és el nom del nou Graf importat
	 * @param directori on es troben els fitxers a patir dels quals es fa la importaci\u00F3.
	 * @throws IOException en cas de que algun fitxer no tingui el format correcte 
	 * o bé no sigui possible llegir-lo.
	 * @throws FileNotFoundException en cas de que algun fitxer no es trobi.
	 */
	public void importar(String nomGraf, String directori) throws IOException {
		controladorMultigraf.importar(nomGraf, directori);
	}

	/**
	 * Consulta si un graf est\u00E0 carregat.
	 * @param nomGraf que és el nom del Graf que es vol consultar.
	 * @return si el graf existeix i est\u00E0 carregat.
	 */
	public boolean exists(String nomGraf) {
		return controladorMultigraf.exists(nomGraf);
	}

	/**
	 * Selecciona el graf amb nom "nomGraf" com a graf actual.
	 * @param nomGraf que és el nom del Graf que es vol seleccionar.
	 * @return si ha sigut possible la selecci\u00F3 del graf (el graf existia).
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

	/** Retorna una llista amb els identificadors de tots els papers amb el nom del par\u00E0metre.
	 * @retrun Retorna una llista amb els identificadors de tots els papers amb el nom del par\u00E0metre.
	 */
	public List<Integer> consultarPaper(String nom){
		return controladorMultigraf.consultarPaper(nom);
	}

	/** Retorna una llista amb els identificadors de totes les conferències amb el nom del par\u00E0metre.
	 * @retrun Retorna una llista amb els identificadors de totes les conferències amb el nom del par\u00E0metre.
	 */
	public List<Integer> consultarConferencia(String nom){
		return controladorMultigraf.consultarConferencia(nom);	    
	}

	/** Retorna una llista amb els identificadors de tots els termes amb el nom del par\u00E0metre.
	 * @retrun Retorna una llista amb els identificadors de tots els termes amb el nom del par\u00E0metre.
	 */
	public List<Integer> consultarTerme(String nom){
		return controladorMultigraf.consultarTerme(nom);
	}

	/** Retorna una llista amb els identificadors de tots els autors amb el nom del par\u00E0metre.
	 * @retrun Retorna una llista amb els identificadors de tots els autors amb el nom del par\u00E0metre.
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
	 * @return Retorna un String que representa el resultat de l'ultima consulta.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consultarResultat() throws IllegalArgumentException {
		return controladorConsultes.consultarResultat();
	}

	/**
	 * Consultora d'un resultat s'una data concreta.
	 * @param data La data el resultat de la qual volem consultar.
	 * @return Retorna un String que representa el resultat de la data indicada.
	 * @throws IllegalArgumentException si no existeix cap consulta realitzada en la data indicada.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consultarResultat(Date data)
			throws IllegalArgumentException {
		return controladorConsultes.consultarResultat(data);
	}

	/**
	 * Consultora de les dates en que s'han realitzat consultes.
	 * @return retorna una llista de les dates en que s'han realitzat consultes ordenada decreixentment.
	 */
	public LinkedList<Date> consultarDatesConsultes() {
		return controladorConsultes.consultarDatesConsultes();
	}
	
	/**
	 * Modificadora de la \u00FAltima consulta
	 * @param date la data de la consulta
	 * @throw IllegalArgumentException si date \u00E9s null o no existeix cap consulta en date
	 */
	public void setUltimaConsulta(Date date) throws IllegalArgumentException {
		controladorConsultes.setUltimaConsulta(date);
	}

	/**
	 * Realitza una consulta de rellevancies a partir d'un node i un path.
	 * @param path El nom del path que es vol fer servir per calcular rellevancies.
	 * @param idNode L'id del node del que es vol obtenir rellevancies amb altres nodes.<br>
	 * 			El node es del primer tipus de node del path.
	 * @param ignorarClausura Indica si es vol ignorar la clausura al fer el c\u00E0lcul.
	 * @param min La rellev\u00E0ncia m\u00EDnima que han de tenir tots els nodes de la llista.
	 * @param max La rellev\u00E0ncia m\u00E0xima que han de tenir tots els nodes de la llista.
	 * @param ignorarClausura Indica si es vol ignorar la clausura al fer el c\u00E0lcul.
	 * @return Retorna un String que representa el resultat de la consulta,
	 * 			amb la llista de tots els nodes (de l'ultim tipus de node del path)
	 * 			rellevants pel node amb id idNode segons el path indicat.
	 * @throws IllegalArgumentException si no existeixen el path o el node indicats.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consulta(String path, int idNode,
			boolean ignorarClausura, double min, double max) throws Exception {
		return controladorConsultes.consulta(path, idNode, ignorarClausura, min, max);
	}

	/**
	 * Realitza una consulta de rellevancies a partir d'un node i un path i fent servir un threshold
	 * com a filtre.
	 * @param path El nom del path que es vol fer servir per calcular rellevancies.
	 * @param idNode L'id del node del que es vol obtenir rellevancies amb altres nodes.
	 * 			El node es del primer tipus de node del path.
	 * @param idNodeThreshold1 L'id del primer node del threshold.<br>
	 * El node es del primer tipus
	 * de node del path del threshold.
	 * @param idNodeThreshold2 L'id del segon node del threshold.<br>
	 * El node es de l'ultim tipus de node del path del threshold.
	 * @param thresholdPath El nom del path del threshold.
	 * @param ignorarClausura Indica si es vol ignorar la clausura al fer el c\u00E0lcul.
	 * @param ignorarClausuraThreshold Indica si es vol ignorar la clausura al fer el c\u00E0lcul del threshold.
	 * @param min la m\u00EDnima rellev\u00E0ncia que ha de tenir qualsevol resultat que es retorni
	 * @param max la m\u00E0xima rellev\u00E0ncia que ha de tenir qualsevol resultat que es retorni
	 * @return Retorna un String que representa el resultat de la consulta,
	 * 			amb la llista de tots els nodes (de l'ultim tipus de node del path)
	 * 			rellevants pel node amb id idNode segons el path indicat que passen el threshold.
	 * @throws IllegalArgumentException si no existeixen el path o el node indicats
	 * 			o be algun dels nodes o el path del threshold no existeixen.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> consulta(String path, int idNode,
			int idNodeThreshold1, int idNodeThreshold2, String thresholdPath, double min, double max,
			boolean ignorarClausura, boolean ignorarClausuraThreshold) throws Exception {
		
		return controladorConsultes.consulta(path, idNode,
				idNodeThreshold1, idNodeThreshold2, thresholdPath, min, max,
				ignorarClausura, ignorarClausuraThreshold);
	}

	/**
	 * Esborra la consulta indicada.
	 * @param data La data la consulta de la qual volem esborrar.
	 * @return Retorna cert si s'ha pogut esborrar la consulta
	 * (existeix una consulta amb la data indicada) i fals altrament.
	 */
	public boolean esborrarConsulta(Date data) {
		return controladorConsultes.esborrarConsulta(data);
	}

	/**
	 * Obt\u00E9 els n primers resultats
	 * @param n nombre de resultats amb mes rellevancia que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al ultim resultat,
	 * si es posa a false l'ultim resultat no es veura modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una \u00FAltima consulta.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> filtrarElsPrimers(int n, boolean aplicar)
			throws IllegalArgumentException {
		return controladorConsultes.filtrarElsPrimers(n, aplicar).getResultats();
	}

	/**
	 * Obt\u00E9 els n ultims resultats
	 * @param n nombre de resultats amb menys rellevancia que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al ultim resultat,
	 * si es posa a false l'ultim resultat no es veura modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una \u00FAltima consulta.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> filtrarElsUltims(int n, boolean aplicar)
			throws IllegalArgumentException {
		return controladorConsultes.filtrarElsUltims(n, aplicar).getResultats();
	}

	/**
	 * Obt\u00E9 tots els resultats que tenen el node amb l'etiqueta label.
	 * @param label de les tuples del resultat que s'han de conservar
	 * @param aplicar si es vol aplicar el filtre al ultim resultat,
	 * si es posa a false l'ultim resultat no es veura modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una \u00FAltima consulta.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> filtrarPerEtiqueta(String label, boolean aplicar)
			throws IllegalArgumentException {
		return controladorConsultes.filtrarPerEtiqueta(label, aplicar).getResultats();
	}

	/**
	 * Obt\u00E9 tots els resultats que tenen una rellevancia entre min i max, ambdos incluits.
	 * @param min es el minim de rellevancia
	 * @param max es el maxim de rellevancia
	 * @param aplicar si es vol aplicar el filtre al ultim resultat,
	 * si es posa a false l'ultim resultat no es veura modificat.
	 * @return el resultat amb el filtratge aplicat
	 * @throws IllegalArgumentException si no existeix una \u00FAltima consulta.
	 */
	public ArrayList<Entry<Double, Entry<Integer, String>>> filtrarPerRellevancia(double min, double max,
			boolean aplicar) throws IllegalArgumentException {
		return controladorConsultes.filtrarPerRellevancia(min, max, aplicar).getResultats();
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
	 * @param rellevancia La rellevancia del node que s'afegeix.
	 * @param idNode L'id del node que s'afegeix.
	 * 			El node es de l'ultim tipus de node del path de l'ultima consulta.
	 * @throws IllegalArgumentException si no existeix una ultima consulta
	 * 			o si no existeix el node indicat.
	 */
	public void afegir(double rellevancia, int idNode) throws IllegalArgumentException {
		controladorConsultes.afegir(rellevancia, idNode);
	}

	/**
	 * Esborra de l'ultima consulta el resultat indicat.
	 * @param index La posicio del resultat que es vol esborrar.
	 * @return Retorna cert si s'ha pogut esborrar el resultat (index es troba dins d'un rang correcte)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean esborrar(int index) throws IllegalArgumentException {
		return controladorConsultes.esborrar(index);
	}
	
	/**
	 * Consultora de la dada de l'\u00FAltima consulta.
	 * @returns Retorna el nom de la dada sobre la que s'ha fet l'\u00FAltima consulta.
	 * @throws IllegalArgumentException si no existeix una \u00FAltima consulta.
	 */
	public String getDada() throws IllegalArgumentException {
		return controladorConsultes.getDada();
	}
	
	/**
	 * Consultora del path de l'\u00FAltima consulta.
	 * @returns Retorna el nom del path fet servir en l'\u00FAltima consulta.
	 * @throws IllegalArgumentException si no existeix una \u00FAltima consulta.
	 */
	public String getPath() throws IllegalArgumentException {
		return controladorConsultes.getPath();
	}

	/**
	 * Consultora del tipus dels nodes dels resultats de l'ultima consulta.
	 * @returns Retorna el tipus dels nodes dels reultats de l'ultima consulta:
	 * 			"Autor", "Conferencia", "Terme" o "Paper".
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public TipusDada getTipusNode() throws IllegalArgumentException{
		return controladorConsultes.getTipusNode();
	}

	/**
	 * Modifica la rellevancia del resultat indicat de l'ultima consulta.
	 * S'ha de tenir en compte que despres d'aquesta crida si retorna cert llavors
	 * es probable que aquesta posicio hagi canviat degut a l'ordre dels resultats.
	 * @param index La posicio del resultat que es vol modificar.
	 * @param rellevancia La rellevancia per la qual es vol modificar l'actual.
	 * @return Retorna cert si s'ha pogut modificar el resultat
	 * 			(index es troba dins d'un rang correcte i 0 <= rellevancia <= 1)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean setRellevancia(int index, double rellevancia) throws IllegalArgumentException {
		return controladorConsultes.setRellevancia(index, rellevancia);
	}

	/**
	 * Modifica la dada del resultat indicat de l'ultima consulta.
	 * @param index La posicio del resultat que es vol modificar.
	 * @param idNode L'id del node pel qual es vol modificar l'actual.
	 * 			El node es de l'ultim tipus de node del path de l'ultima consulta.
	 * @return Retorna cert si s'ha pogut modificar el resultat
	 * 			(index es troba dins d'un rang correcte i el node existeix)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean setDada(int index, int idNode) throws IllegalArgumentException {
		return controladorConsultes.setDada(index, idNode);
	}

	/**
	 * Modifica el nom de la dada del resultat indicat de l'ultima consulta.
	 * @param index La posicio del resultat que es vol modificar.
	 * @param nom El nom pel qual es vol modificar l'actual.
	 * @return Retorna cert si s'ha pogut modificar el resultat (index es troba dins d'un rang correcte)
	 * 			i fals altrament.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 */
	public boolean canviarNom(int index, String nom) throws IllegalArgumentException {
		return controladorConsultes.canviarNom(index, nom);
	}

	/**
	 * Modifica el threshold de l'ultima consulta i refa la consulta.
	 * @param idNode1 L'id del primer node del nou threshold.
	 * 			El node es del primer tipus de node del path del threshold.
	 * @param idNode2 L'id del segon node del nou threshold.
	 * 			El node es de l'ultim tipus de node del path del threshold.
	 * @param path El nom del path del nou threshold.
	 * @param ignorarClausura Indica si es vol ignorar la clausura al fer el c\u00E0lcul del threshold.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setThreshold(int idNode1, int idNode2, String path, boolean ignorarClausura) throws Exception {
		controladorConsultes.setThreshold(idNode1, idNode2, path, ignorarClausura);
	}

	/**
	 * Modifica el path i la dada de l'ultima consulta i refa la consulta.
	 * @param path El nom del path pel qual es vol modificar l'actual.
	 * @param id L'id del node pel qual es vol modificar l'actual.
	 * 			El node es del primer tipus de node de path.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setPath(String path, int id) throws Exception {
		controladorConsultes.setPath(path, id);
	}

	/**
	 * Modifica la dada de l'ultima consulta i refa la consulta.
	 * @param id L'id del node pel qual es vol modificar l'actual.
	 * 			El node es del primer tipus de node del path de l'ultima consulta.
	 * @throws IllegalArgumentException si no existeix una ultima consulta.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void setDada(int id) throws Exception {
		controladorConsultes.setDada(id);
	}

	/**
	 * Exporta uns resultats amb les dades de la \u00FAltima consulta.
	 * @param filesystem_path El fitxer on es vol exportar els resultats.
	 * @param resultats Els resultats a exportar.
	 * @throws IOException si no es pot crear o escriure en el fitxer indicat.
	 * @throws IllegalArgumentException si no existeix una \u00FAltima consulta.
	 */
	public void exportarResultat(String filesystem_path,
			ArrayList<Entry<Double, Entry<Integer, String>>> resultats) throws Exception {
		controladorConsultes.exportarResultat(filesystem_path, resultats);
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
	
	/**
	 * Deixa l'estat de les clausures tal i com est\u00E0ven abans d'iniciar el programa.
	 * @throws IOException si no es poden guardar les clausures
	 */
	public void reestablirClausures() throws IOException {
		controladorDominiPersistenciaPropi.reestablirClausures();
	}
	
	/**
	 * Esborra tots els fitxers temporals creats pel programa.
	 */
	public void esborrarFitxersTemporals() {
		controladorDominiPersistenciaPropi.esborrarFitxersTemporals();
	}

	//**************funcions de presentaci\u00F3****************

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
