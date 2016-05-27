package persistencia;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import domini.Resultat;

/**
 * Controlador per exportar resultats amb un format llegible.
 * @author Carleslc
 */
public abstract class ControladorExportacio {
	
	/**
	 * Exporta el resultat corresponent al path donat.
	 * @param filesystem_path el path del fitxer a escriure (se sobreescriu si ja existia)
	 * @param date la data del resultat
	 * @param resultat el resultat a exportar
	 * @throws IOException si no es pot escriure el fitxer o hi ha altres problemes d'entrada/sortida
	 */
	public static void exportar(String filesystem_path, Date date, Resultat resultat) throws IOException {
		if (resultat != null) {
			BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileOutputStream(filesystem_path, false)));
			bw.write("Data de creacio: " + new SimpleDateFormat().format(date) + "\n"); // dd/MM/yy HH:mm
			bw.write(resultat.toString());
			bw.close();
		}
	}
}
