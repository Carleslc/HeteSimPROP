package domini;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import persistencia.ControladorPersistencia;
import persistencia.ControladorPersistenciaPropi;

/**
 * @author Arnau Badia Sampera
 */
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
	 * Obté el path on s'ha de guardar el graf amb nomGraf per defecte
	 * @param nomGraf del graf a guardar
	 * @return la string que conté el path desitjat
	 */
	public String construirPath(String nomGraf) {
		return ControladorDominiPersistenciaPropi.DEFAULT_DIRECTORY_GRAFS + "graf_" + nomGraf + ".dat";
	}

	/**
	 * Consulta l'ID del graf actual.
	 * @return l'ID del graf actual o null si no hi ha cap seleccionat.
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
		hetesim = new HeteSim(graf, HeteSim.DEFAULT_DIRECTORI_CLAUSURES + idActual);
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
		hetesim = new HeteSim(graf, HeteSim.DEFAULT_DIRECTORI_CLAUSURES + idActual);
	}
	
	/**
	 * Consulta si un graf est� carregat.
	 * @param nomGraf que és el nom del Graf que es vol consultar.
	 * @return si el graf existeix
	 */
	public boolean exists(String nomGraf) {
		Pattern pattern = Pattern.compile(nomGraf, Pattern.CASE_INSENSITIVE + Pattern.LITERAL);
		for (String graf : grafs) {
			if (pattern.matcher(graf).find())
				return true;
		}
		return false;
	}
	
	/**
	 * Selecciona el graf amb nom "nomGraf" com a graf actual.
	 * @param nomGraf que és el nom del Graf que es vol seleccionar.
	 * @return si ha sigut possible la selecció del graf (el graf existia).
	 */
	public boolean seleccionarGraf(String nomGraf) throws IOException {
		if (nomGraf == null)
			return false;
		if (!nomGraf.equals(idActual)) {
			if (idActual != null)
				guardar(construirPath(idActual));
			if (!grafs.contains(nomGraf)) return false;
			carregar(construirPath(nomGraf));
			idActual = nomGraf;
			hetesim = new HeteSim(graf, HeteSim.DEFAULT_DIRECTORI_CLAUSURES + idActual);
		}
		return true;
	}
	
	/**
	 * Carrega els noms dels grafs guardats en el directori donat. 
	 * @param dir on estan continguts els grafs
	 * @throws FileNotFoundException si dir �s null o no �s un directori
	 */
	public void carregarNoms(String dir) throws FileNotFoundException {
		grafs.addAll(ControladorPersistenciaPropi.getNomsGrafs(dir));
	}
	
	/**
	 * Guarda el graf actual al path donat.
	 * Si el fitxer existia es sobreescriu.
	 * @param path path on es vol guardar el graf.
	 * @throws IOException en cas de no poder-se escriure en el fitxer.
	 */
	@Override
	public void guardar(String path) throws IOException {
		if (idActual != null)
			super.guardar(path);
	}
	
	/**
	 * Esborra el fitxer on esta guardat el graf actual
	 * @throws IOException en cas de no poder-se esborrar el fitxer.
	 */
	public void esborrarFitxerGraf() throws IOException {
		ControladorPersistencia.esborrarFitxer(construirPath(idActual));
		grafs.remove(idActual);
		idActual = null;
		graf = null;
	}
}
