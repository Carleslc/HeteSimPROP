package domini;

/**
 * @author Arnau Badia Sampera
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import persistencia.ControladorPersistencia;

public class ControladorMultigraf extends ControladorGraf {

	private String idActual;
	private HashSet<String> grafs;
	private HeteSim heteSim; 
	
	
	/**
	 * Constructor 
	 */
	public ControladorMultigraf() {
		super();
		grafs = new HashSet<>();
		heteSim = new HeteSim(graf);
	}

	/**
	 * Obté el path on s'ha de guardar el graf amb nomGraf
	 * @param nomGraf del graf a guardar
	 * @return la string que conté el path desitjat
	 */
	private String construirPath(String nomGraf) {
		String s = ControladorDominiPersistenciaPropi.DEFAULT_DIRECTORY_GRAFS;
		s = s + "graf_" + idActual + ".dat";
		return s;
	}

	/**
	 * Consulta l'ID del graf actual.
	 * @return l'ID del graf actual.
	 */
	public String getIdActual() {
		return idActual;
	}
	
	/**
	 * Marca les clausures del graf actual com a invalides (ja que s'ha modificat el graf)
	 */
	public void clausuresInvalides() {
		HeteSim.clausuresInvalides();
	}
	
	/**
	 * Retorna una llista amb els noms de tots els grafs carregats.
	 * @return llista amb els noms de tots els grafs carregats.
	 */
	public List<String> getNomsGrafs() {
		return new ArrayList<String>(grafs);
		}
	/**
	 * Retorna el controlador de l’algorisme HeteSim del graf actual.
	 * @return el controlador de l’algorisme HeteSim del graf actual.
	 */
	public HeteSim getHeteSim() {
		return heteSim;
	}
	
	/**
	 * Crea una nova entrada a grafs amb el nou graf de nom nomGraf i 
	 * una altra a controladors (els dos seran buits).
	 * El graf actual i l'id actual passen a ser els d'aquest graf.
	 * @param nomGraf es el nom del nou graf.
	 */
	public void afegirGraf(String nomGraf) throws IOException{
		if (idActual != null) {
			guardar(construirPath(idActual));
		}
		graf = new Graf();
		grafs.add(nomGraf);
		heteSim = new HeteSim(graf);
		idActual = nomGraf;
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
		if (idActual != null) {
			guardar(construirPath(idActual));
		}
		super.importar(directori);
		grafs.add(nomGraf);
		idActual = nomGraf;
		heteSim = new HeteSim(graf);
	}
	
	/**
	 * Selecciona el graf amb nom "nomGraf" com a graf actual.
	 * @param nomGraf que és el nom del Graf que es vol seleccionar.
	 * @return si ha sigut possible la selecció del graf (el graf existia).
	 */
	public boolean seleccionarGraf(String nomGraf) throws IOException {
		if (idActual != null) {
			guardar(construirPath(idActual));
		}
		if (!grafs.contains(nomGraf)) return false;
		carregar(construirPath(nomGraf));
		idActual = nomGraf;
		heteSim = new HeteSim(graf);
		return true;
	}
	
	/**
	 * Carrega el graf guardat en el fitxer corresponent al path donat. 
	 * @param path on estan continguts els fitxers a carregar
	 * @throws IOException si no es pot llegir algun fitxer
	 * @throws FileNotFoundException si no es troba algun fitxer
	 */
	@Override
	public void carregar(String path) throws IOException {
		super.carregar(path);
	}
	
	/**
	 * Guarda el graf actual al fitxer corresponent al path donat.
	 * Si algun fitxer existia es sobreescriu.
	 * @throws IOException en cas de no poder-se escriure algun fitxer.
	 */
	@Override
	public void guardar(String path) throws IOException {
		super.guardar(path);
	}
	
	/**
	 * Carrega tots els grafs amb les seves clausures dels fitxers corresponents al directori donat. 
	 * @param directori on estan continguts els fitxers a carregar
	 * @throws IOException si no es pot llegir algun fitxer
	 * @throws FileNotFoundException si no es troba algun fitxer
	 
	@Override
	public void carregar(String directori) throws IOException {
		File f = new File(directori);
		if (f.isDirectory() && f.exists()) {
			File[] fitxers = f.listFiles();
			for (File fitxer: fitxers) {
				String fileName = fitxer.getName();
				if (fileName.matches("graf_.+\\.dat")) {
					String nomGraf = fileName.substring(5, fileName.indexOf(".dat"));
					graf = ControladorPersistencia.carregarGraf(directori + "\\" + fileName);
					grafs.put(nomGraf, graf);
					HeteSim heteSim = new HeteSim(graf);
					heteSim.carregarClausures(directori + "\\" + "clausures_" + nomGraf + ".dat");
					controladors.put(nomGraf, heteSim);
				}
			}
		}
	}
	
	/**
	 * Guarda tots els grafs i les seves clausures en el directori donat.
	 * Si algun fitxer existia es sobreescriu.
	 * @throws IOException en cas de no poder-se escriure algun fitxer.
	 
	@Override
	public void guardar(String directori) throws IOException {
		for (String key: grafs.keySet()) {
			ControladorPersistencia.guardarGraf(directori + "/" + "graf_"+ key + ".dat",grafs.get(key));
			controladors.get(key).guardarClausures(directori + "/" + "clausures_" + key + ".dat");
		}
	}
	*/
}
