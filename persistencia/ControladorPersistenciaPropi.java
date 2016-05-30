package persistencia;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domini.Resultat;

/**
 * 
 * @author Guillem Castro
 *
 */
public abstract class ControladorPersistenciaPropi extends ControladorPersistencia {

	/**
	 * Consulta els noms de tots els grafs disponibles en un directori recursivament.
	 * <br>Es considerar‡n grafs els fitxers amb el format <code>graf_NOM.dat</code>
	 * on <code>NOM</code> Ès el que s'afegeix a la llista de noms retornada.
	 * @param dir el directori dels grafs
	 * @return una llista amb tots els noms de tots els grafs disponibles a <b>dir</b>
	 * @throws FileNotFoundException si dir Ès null o no Ès un directori
	 */
	public static List<String> getNomsGrafs(String dir) throws FileNotFoundException {
		if (dir == null)
			throw new FileNotFoundException("El directori no pot ser null!");

		File d = new File(dir);

		List<String> noms = new LinkedList<>();
		if (d.exists()) {
			if (!d.isDirectory())
				throw new FileNotFoundException(dir + " no Ès un directori!");

			for (File f : d.listFiles()) {
				if (!f.isDirectory()) {
					Pattern pattern = Pattern.compile("graf_(.*)\\.dat");
					Matcher matcher = pattern.matcher(f.getName());
					if (matcher.find())
						noms.add(matcher.replaceAll("$1"));
				}
				else
					noms.addAll(getNomsGrafs(f.getPath()));
			}
		}
		return noms;
	}

	/**
	 * Guarda els resultats a un fitxer corresponent al path donat.
	 * @param filesystem_path Path del sistema que indica el fitxer a on es volen guardar els resultats.
	 * @param resultats Conjunt de resultats a guardar.
	 * @throws IOException Si no es pot escriure al fitxer, llen√ßar√° una IOException.
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
	 * @throws IOException Si el fitxer no existeix o no t√© el format correcte.
	 */
	@SuppressWarnings("unchecked")
	public static TreeMap<Date, Resultat> carregarResultats(String filesystem_path) throws IOException {
		TreeMap<Date, Resultat> res = new TreeMap<>();
		try {
			FileInputStream file = new FileInputStream(filesystem_path);
			ObjectInputStream in = new ObjectInputStream(file);

			try {
				res = (TreeMap<Date, Resultat>) in.readObject();
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			} finally {
				in.close();
				file.close();
			}
		} catch (EOFException ignore) {
			// No hi ha cap resultat escrit
		}
		return res;
	}
}