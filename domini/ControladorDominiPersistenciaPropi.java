package domini;

import java.io.File;
import java.io.IOException;
import domini.ControladorDominiPersistencia;

/**
 * 
 * @author Arnau Badia Sampera
 *
 */
public class ControladorDominiPersistenciaPropi extends ControladorDominiPersistencia {

	private static final long serialVersionUID = -325380240675964323L;

	public static final String DEFAULT_DIRECTORY_GRAFS = "grafs/";
	private ControladorConsultes controladorConsultes;
	
	/**
	 * Constructor 
	 * @param ctrlGraf
	 * @param ctrlPaths
	 * @param ctrlConsultes
	 */
	public ControladorDominiPersistenciaPropi(ControladorMultigraf ctrlGraf, ControladorPaths ctrlPaths, ControladorConsultes ctrlConsultes) {
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
	}

}
