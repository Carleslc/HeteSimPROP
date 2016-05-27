package domini;


import java.io.IOException;
import java.io.Serializable;

/**
 * 
 * @author Arnau Badia Sampera
 *
 */
public class ControladorDominiPersistencia implements Serializable {
	
	
	public static final String DEFAULT_FILEPATH_GRAF ="graf.dat";
	public static final String DEFAULT_FILEPATH_PATHS = "paths.dat";
	public static final String DEFAULT_FILEPATH_CLAUSURES = "clausures.dat";
	protected ControladorGraf controladorGraf;
	protected ControladorPaths controladorPaths;
	private HeteSim controladorHeteSim;
	
	
	/**
	 * Constructor amb ControladorHeteSim
	 * @param controladorGraf
	 * @param controladorPaths
	 * @param controladorHeteSim 
	 */
	public ControladorDominiPersistencia(ControladorGraf controladorGraf, ControladorPaths controladorPaths, HeteSim controladorHeteSim) {
		this.controladorGraf = controladorGraf;
		this.controladorPaths = controladorPaths;
		this.controladorHeteSim = controladorHeteSim;
	}
	
	/**
	 * Constructor base
	 * @param controladorGraf
	 * @param controladorPaths
	 */
	ControladorDominiPersistencia(ControladorGraf controladorGraf, ControladorPaths controladorPaths) {
		this.controladorGraf = controladorGraf;
		this.controladorPaths = controladorPaths;
	}
	
	/**
	 * Guarda totes les dades que es poden (fa una crida als mètodes de guardar graf, paths i clausures) 
	 * als fitxers corresponents als paths per defecte. Si algun fitxer existia es sobreescriu.
	 * @throws IOException si no es pot escriure als fitxers.
	 */
	public void guardarDades() throws IOException {
		controladorGraf.guardar(DEFAULT_FILEPATH_GRAF);
		controladorPaths.guardarPaths(DEFAULT_FILEPATH_PATHS);
		controladorHeteSim.guardarClausures(DEFAULT_FILEPATH_CLAUSURES);
	}
	
	/**
	 * Guarda totes les dades (fa una crida als mètodes de guardar graf, paths i clausures)
	 * als fitxers corresponents als paths donats per paràmetre. Si algun fitxer existía es sobreescriu. 
	 * @param fitxerGraf es el fitxer on es vol guardar el graf
	 * @param fitxerPaths es el fitxer on es volen guardar els paths
	 * @param fitxerClausures es el fitxer on es volen guardar les clausures
	 * @throws IOException si no es pot escriure als fitxers.
	 */
	public void guardarDades(String fitxerGraf, String fitxerPaths, String fitxerClausures) throws IOException {
		controladorGraf.guardar(fitxerGraf);
		controladorPaths.guardarPaths(fitxerPaths);
		controladorHeteSim.guardarClausures(fitxerClausures);
	}
	
	/**
	 * Guarda totes les dades (fa una crida als mètodes de guardar graf i paths) als fitxers
	 * corresponents als paths donats per paràmetre. Si algun fitxer existía es sobreescriu.
	 * @param fitxerGraf es el fitxer on es vol guardar el graf
	 * @param fitxerPaths es el fitxer on es volen guardar els paths
     * @throws IOException si no es pot escriure als fitxers
	 */
	public void guardarDades(String fitxerGraf, String fitxerPaths) throws IOException {
		controladorGraf.guardar(fitxerGraf);
		controladorPaths.guardarPaths(fitxerPaths);
	}
	
	/**
	 * Carrega totes les dades (fa una crida als mètodes de guardar graf, paths i clausures) 
	 * pels fitxers per defecte existents.
	 * @throws IOException si no es pot llegir el fitxer o no hi ha cap objecte serialitzat 
	 * amb format correcte.	
	 */
	public void carregarDades() throws IOException {
		controladorGraf.carregar(DEFAULT_FILEPATH_GRAF);
		controladorPaths.carregarPaths(DEFAULT_FILEPATH_PATHS);
		controladorHeteSim.carregarClausures(DEFAULT_FILEPATH_CLAUSURES);
	}	
	
	/**
	 * Carrega totes les dades (fa una crida als mètodes de guardar graf, paths i clausures)
	 * pels fitxers corresponents als paths donats per paràmetre.
	 * @param fitxerGraf es el fitxer des d'on es carrega el graf
	 * @param fitxerPaths es el fitxer des d'on es carreguen els paths
	 * @param fitxerClausures es el fitxer des d'on es carreguen les clausures
	 * @throws IOException si no es pot llegir el fitxer o no hi ha cap objecte serialitzat 
	 * amb format correcte.
	 * @throws FileNotFoundException en cas de que no es trobi algun fitxer o una altra 
	 */	
	public void carregarDades(String fitxerGraf, String fitxerPaths, String fitxerClausures) throws IOException {
		controladorGraf.carregar(fitxerGraf);
		controladorPaths.carregarPaths(fitxerPaths);
		controladorHeteSim.carregarClausures(fitxerClausures);
	}
	
	/**
	 * Carrega totes les dades (fa una crida als mètodes de guardar graf i paths) 
	 * dels fitxers corresponents als paths donats per paràmetre. 
	 * @param fitxerGraf es el fitxer des d'on es carrega el graf
	 * @param fitxerPaths es el fitxer des d'on es carreguen els paths
	 * @throws IOException si no es pot llegir el fitxer o no hi ha cap objecte serialitzat 
	 * amb format correcte.
	 * @throws FileNotFoundException en cas de que no es trobi algun fitxer o una altra 
	 */
	public void carregarDades(String fitxerGraf, String fitxerPaths) throws IOException {
		controladorGraf.carregar(fitxerGraf);
		controladorPaths.carregarPaths(fitxerPaths);
	}
}
