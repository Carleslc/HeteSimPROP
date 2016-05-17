package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.TreeMap;

import domini.Resultat;

/**
 * 
 * @author Guillem Castro
 *
 */

public abstract class ControladorPersistenciaPropi extends ControladorPersistencia {
	
	
	/**
	 * Guarda els resultats a un fitxer corresponent al path donat.
	 * @param filesystem_path Path del sistema que indica el fitxer a on es volen guardar els resultats.
	 * @param resultats Conjunt de resultats a guardar.
	 * @throws IOException Si no es pot escriure al fitxer, llençará una IOException.
	 */
	public static void guardarResultats(String filesystem_path, TreeMap<Date, Resultat> resultats) throws IOException {
		FileOutputStream file = new FileOutputStream(filesystem_path, false);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(resultats);
		out.close();
		file.close();
	}
	
	
	/**
	 * Llegeix els resultats del fitxer corresponent al path donat.
	 * @param filesystem_path Path del sistema que indica el fitxer des d'on es volen carregar els resultats.
	 * @return Retorna un TreeMap<Date, Resultat> amb els resultats del fitxer.
	 * @throws IOException Si el fitxer no existeix o no té el format correcte.
	 */
	@SuppressWarnings("unchecked")
	public static TreeMap<Date, Resultat> carregarResultats (String filesystem_path) throws IOException {
		FileInputStream file = new FileInputStream(filesystem_path);
		ObjectInputStream in = new ObjectInputStream(file);
		
		TreeMap<Date, Resultat> res;
		try {
			res = (TreeMap<Date, Resultat>) in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		} finally {
			in.close();
			file.close();
		}
		
		return res;
	}
}
