package domini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import domini.ControladorDominiPersistencia;
import persistencia.ControladorPersistencia;

/**
 * 
 * @author Arnau Badia Sampera
 *
 */
public class ControladorDominiPersistenciaPropi extends ControladorDominiPersistencia {

	private static final long serialVersionUID = -325380240675964323L;
	
	private static final String DIRECTORI_CLAUSURES_TEMPORAL = "clausures_temp/";
	
	public static final String DEFAULT_DIRECTORY_GRAFS = "grafs/";
	
	private ControladorConsultes controladorConsultes;

	/**
	 * Constructor 
	 * @param ctrlGraf
	 * @param ctrlPaths
	 * @param ctrlConsultes
	 * @throws IOException si hi ha un error al carregar les clausures
	 */
	public ControladorDominiPersistenciaPropi(ControladorMultigraf ctrlGraf,
			ControladorPaths ctrlPaths, ControladorConsultes ctrlConsultes) throws IOException {
		super(ctrlGraf,ctrlPaths);
		controladorConsultes = ctrlConsultes;
	}

	/**
	 * Guarda totes les dades que es poden (fa una crida als mètodes de guardar grafs
	 * i clausures, paths i resultats) als fitxers corresponents als paths per defecte.
	 * Si algun fitxer existia es sobreescriu.
	 * @throws IOException en cas de no poder-se escriure en els fitxers.
	 */
	@Override
	public void guardarDades() throws IOException {
		ControladorMultigraf controladorGraf = ((ControladorMultigraf)this.controladorGraf);
		controladorGraf.guardar(controladorGraf.construirPath(controladorGraf.getIdActual()));
		controladorPaths.guardarPaths(DEFAULT_FILEPATH_PATHS);
		controladorConsultes.guardarResultats();
		saveStatusClausures();
	}

	/**
	 * Carrega totes les dades (fa una crida als mètodes de carregar grafs i 
	 * clausures, paths i resultats) pels fitxers per defecte existents. 
	 * @throws IOException en cas de no poder llegir els fitxers.
	 */
	@Override
	public void carregarDades() throws IOException {
		new File(DEFAULT_DIRECTORY_GRAFS).mkdirs();
		((ControladorMultigraf)controladorGraf).carregarNoms(DEFAULT_DIRECTORY_GRAFS);
		new File(DEFAULT_FILEPATH_PATHS).createNewFile();
		controladorPaths.carregarPaths(DEFAULT_FILEPATH_PATHS);
		new File(ControladorConsultes.DEFAULT_PATH_RESULTATS).createNewFile();
		controladorConsultes.carregarResultats();
		saveStatusClausures();
	}

	/**
	 * Deixa l'estat de les clausures tal i com estàven abans d'iniciar el programa.
	 * @throws IOException si no es poden guardar les clausures
	 */
	public void reestablirClausures() throws IOException {
		ControladorPersistencia.copiar(DIRECTORI_CLAUSURES_TEMPORAL, HeteSim.DEFAULT_DIRECTORI_CLAUSURES);
	}
	
	/**
	 * Guarda l'estat de les clausures.
	 * @throws IOException si no s'ha pogut actualitzar l'estat de les clausures
	 */
	private void saveStatusClausures() throws IOException {
		File dirClausures = new File(HeteSim.DEFAULT_DIRECTORI_CLAUSURES);
		if (dirClausures.exists() && dirClausures.isDirectory())
			ControladorPersistencia.copiar(HeteSim.DEFAULT_DIRECTORI_CLAUSURES, DIRECTORI_CLAUSURES_TEMPORAL);
		else
			throw new FileNotFoundException("No s'ha trobat el directori "
					+ HeteSim.DEFAULT_DIRECTORI_CLAUSURES);
	}
	
	/**
	 * Esborra tots els fitxers temporals creats pel programa.
	 */
	public void esborrarFitxersTemporals() {
		ControladorPersistencia.esborrarFitxer(DIRECTORI_CLAUSURES_TEMPORAL);
	}

}
