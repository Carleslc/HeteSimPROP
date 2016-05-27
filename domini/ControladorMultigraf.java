package domini;

/**
 * @author Arnau Badia Sampera
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ControladorMultigraf extends ControladorGraf {

	private String idActual;
	private HashSet<String> grafs;
	
	/**
	 * Constructor 
	 */
	public ControladorMultigraf() throws IOException {
		grafs = new HashSet<>();
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
	 * Retorna una llista amb els noms de tots els grafs carregats.
	 * @return llista amb els noms de tots els grafs carregats.
	 */
	public List<String> getNomsGrafs() {
		return new ArrayList<String>(grafs);
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
		hetesim = new HeteSim(graf);
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
		hetesim = new HeteSim(graf);
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
		hetesim = new HeteSim(graf);
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
		if (idActual != null) {
			super.guardar(path);
		}
	}
}
