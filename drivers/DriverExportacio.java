package drivers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import domini.Autor;
import domini.ControladorGraf;
import domini.ControladorPaths;
import domini.Node;
import domini.Pair;
import domini.Resultat;
import persistencia.ControladorExportacio;
import persistencia.ControladorPersistencia;

/**
 * Driver per provar el controlador d'exportacio.
 * @author Carleslc
 */
public class DriverExportacio extends Driver {

	public static void main(String[] args) {
		println("Primerament crea un resultat buit:");
		Resultat r = null;
		try {
			r = crearResultat();
		} catch (IOException e) {
			println("Hi ha hagut un error!");
			print(e);
		}
		
		String filePath = null;
		
		int opt;
		do {
			print("\nEscolleix una opcio:\n"
					+ "1. Crear nou resultat sense tuples\n"
					+ "2. Afegit tupla al resultat\n"
					+ "3. Exportar resultat\n"
					+ "4. Llegir fitxer exportat\n"
					+ "5. Sortir del driver\n"
					+ "Opcio = ");

			opt = nextInt();

			println();
			try {
				switch (opt) {
					case 1:
						r = crearResultat();
						break;
					case 2:
						r.afegir(crearTupla());
						break;
					case 3:
						print("Escriu el path i fitxer on exportar el resultat: ");
						filePath = nextLine();
						ControladorExportacio.exportar(filePath, new Date(), r);
						File f = new File(filePath);
						f.deleteOnExit();
						println("El fitxer " + f.getAbsolutePath() + " sera eliminat al sortir del driver.");
						break;
					case 4:
						if (filePath != null)
							mostrarFitxer(filePath);
						else
							println("No has exportat cap resultat.");
						break;
					case 5:
						break;
					default:
						println("Introdueix una opcio de la 1 a la 5.");
				}
			} catch (Exception e) {
				println("\nHi ha hagut un error:");
				print(e);
			}
			println();
		} while (opt != 5);
		
		close();
	}

	private static void mostrarFitxer(String filesystem_path) throws IOException {
		ControladorPersistencia.llegirFitxer(filesystem_path).forEach(System.out::println);
	}
	
	private static Resultat crearResultat() throws IOException {
		Node n = crearNode();
		print("Nom del path: ");
		String nomp = nextLine().toUpperCase();
		print("Nom del graf: ");
		String nom = nextLine();
		return new Resultat(n, nomp, new ControladorPaths(new ControladorGraf()), nom, new ArrayList<Pair<Double, Node>>());
	}

	private static Pair<Double, Node> crearTupla() {
		print("Rellevancia: ");
		double rel = nextDouble();
		Node n = crearNode();
		return new Pair<Double, Node>(rel, n);
	}
	
	private static Node crearNode() {
		print("Nom Dada: ");
		String nom = nextLine();
		print("Etiqueta (si no es vol -> click return): ");
		String label = nextLine();
		return new Autor(0,nom,label);
	}
}
