package domini;

/**
 * @author Arnau Badia Sampera
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persistencia.ControladorPersistencia;

public class ControladorMultigraf extends ControladorGraf {

	private String idActual;
	private HashMap<String, Graf> grafs;
	private HashMap<String, HeteSim> controladors;
	
	
	/**
	 * Constructor 
	 */
	public ControladorMultigraf() {
		super();
		grafs = new HashMap<>();
		controladors = new HashMap<>();
		idActual = "";
	}
	
	/**
	 * Retorna una llista amb els noms de tots els grafs carregats.
	 * @return llista amb els noms de tots els grafs carregats.
	 */
	public List<String> getNomsGrafs() {
		return new ArrayList<String>(grafs.keySet());
	}
	/**
	 * Retorna el controlador de l’algorisme HeteSim del graf actual.
	 * @return el controlador de l’algorisme HeteSim del graf actual.
	 */
	public HeteSim getHeteSim() {
		return controladors.get(idActual);
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
		super.importar(directori);
		grafs.put(nomGraf, graf);
		idActual = nomGraf;
	}
	
	/**
	 * Selecciona el graf amb nom "nomGraf" com a graf actual.
	 * @param nomGraf que és el nom del Graf que es vol seleccionar.
	 * @return si ha sigut possible la selecció del graf (el graf existia).
	 */
	public boolean seleccionarGraf(String nomGraf) {
		if (grafs.containsKey(nomGraf)) {
			idActual = nomGraf;
			graf = grafs.get(nomGraf);
			return true;
		}
		else return false;
	}

	/**
	 * Carrega tots els grafs amb les seves clausures dels fitxers corresponents al directori donat. 
	 * @param directori on estan continguts els fitxers a carregar
	 * @throws IOException si no es pot llegir algun fitxer
	 * @throws FileNotFoundException si no es troba algun fitxer
	 */
	@Override public void carregar(String directori) throws IOException {
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
	 */
	@Override public void guardar(String directori) throws IOException {
		for (String key: grafs.keySet()) {
			ControladorPersistencia.guardarGraf(directori + "/" + "graf_"+ key + ".dat",grafs.get(key));
			controladors.get(key).guardarClausures(directori + "/" + "clausures_" + key + ".dat");
		}
	}
}
