package persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.TreeMap;
import domini.Resultat;

/**
 * 
 * @author Guillem Castro
 *
 */

public class CtrlPersistenciaPropi extends ControladorPersistencia {
	
	
	/**
	 * Guarda els resultats a un fitxer corresponent al path donat.
	 * @param filesystem_path Path del sistema que indica el fitxer a on es volen guardar els resultats.
	 * @param resultats Conjunt de resultats a guardar.
	 * @throws IOException Si no es pot escriure al fitxer, llençará una IOException.
	 */
	public static void guardarResultats(String filesystem_path, TreeMap<Date, Resultat> resultats) throws IOException {
		File f = new File(filesystem_path);
		PrintWriter w = new PrintWriter(f);
		w.println("Resultats");
		w.println(resultats.size());
		for (Entry<Date, Resultat> entry : resultats.entrySet()) {
			w.println("##");
	        w.println(entry.getKey().getTime());
	        w.println(entry.getValue());
		}
		w.println("###");
		w.close();
	}
	
	
	/**
	 * Llegeix els resultats del fitxer corresponent al path donat.
	 * @param filesystem_path Path del sistema que indica el fitxer des d'on es volen carregar els resultats.
	 * @return Retorna un TreeMap<Date, Resultat> amb els resultats del fitxer.
	 * @throws IOException Si el fitxer no existeix o no té el format correcte.
	 */
	public static TreeMap<Date, Resultat> carregarResultats (String filesystem_path) throws IOException {
		if (!Files.exists(Paths.get(filesystem_path))) {
			throw new FileNotFoundException("El fitxer representat per 'filesystem_path' no existeix");
		}
		TreeMap<Date, Resultat> res = new TreeMap<>();
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(filesystem_path));
		if (!lines.get(0).equals("Resultats")) {
			throw new IOException("El fitxer no té el format correcte");
		}
		int map_size = Integer.valueOf(lines.get(1));
		int lines_index = 2;
		int lines_size = lines.size();
		for (int i = 0; i < map_size && !lines.get(lines_index).equals("###"); ++i) {
			if (lines.get(lines_index).equals("##")) {
				++lines_index;
				Date d_temp = new Date(Long.valueOf(lines.get(lines_index)));
				++lines_index;
				String resultat = new String();
				while (!lines.get(lines_index).equals("##") && !lines.get(lines_index).equals("###")) {
					resultat += lines.get(lines_index);
					++lines_index;
				}
				Resultat r_temp = new Resultat(resultat);
				res.put(d_temp, r_temp);
			}
			else {
				throw new IOException("El fitxer no té el format correcte");
			}
		}
		return res;
	}
}
