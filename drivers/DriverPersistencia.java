package drivers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import domini.Autor;
import domini.Graf;
import domini.HeteSim;
import domini.Matriu;
import domini.Paper;
import domini.Path;
import persistencia.ControladorPersistencia;

/**
 * Driver per provar el controlador de persistencia.
 * @author Carleslc
 */
public class DriverPersistencia extends Driver {

	// MINI-TEST
	public static void main(String[] args) {
		String path = "test.dat";
		try {
			ControladorPersistencia.guardarGraf(path, new Graf());
			Graf g = ControladorPersistencia.carregarGraf(path);
			System.out.println(g + "\n");

			ArrayList<Path> paths = new ArrayList<>();
			paths.add(new Path("APA"));
			paths.add(new Path("PCPAPT"));
			ControladorPersistencia.guardarPaths(path, paths);
			mostrarFitxer(path); // Llegirà basura
			System.out.println();
			paths = new ArrayList<>(ControladorPersistencia.carregarPaths(path));
			paths.forEach(System.out::println);
			System.out.println();

			ControladorPersistencia.guardarClausura(HeteSim.DEFAULT_DIRECTORI_CLAUSURES, "PCPAPT", new Matriu(2, 3, .11), true);
			ControladorPersistencia.guardarClausura(HeteSim.DEFAULT_DIRECTORI_CLAUSURES, "APA", new Matriu(7, 4, .75), false);
			ControladorPersistencia.actualitzaClausura(HeteSim.DEFAULT_DIRECTORI_CLAUSURES, "APA", true);
			System.out.println(ControladorPersistencia.carregarClausures(HeteSim.DEFAULT_DIRECTORI_CLAUSURES));
			System.out.println();
			System.out.println(ControladorPersistencia.carregarClausura(HeteSim.DEFAULT_DIRECTORI_CLAUSURES, "PCPAPT"));
			ControladorPersistencia.esborrarClausura(HeteSim.DEFAULT_DIRECTORI_CLAUSURES, "APA");
			ControladorPersistencia.esborrarClausura(HeteSim.DEFAULT_DIRECTORI_CLAUSURES, "PCPAPT");
			new File(HeteSim.DEFAULT_DIRECTORI_CLAUSURES).deleteOnExit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			File f = new File(path);
			if (f.exists())
				f.delete();
		}
	}
	
	@Deprecated
	public static void deprecated_main(String[] args) {
		
		String pathPaths = null, pathClausures = null, pathGraf = null;
		
		int opt;
		do {
			print("Escolleix una opcio:\n"
					+ "1. llegirFitxer\n"
					+ "2. guardarGraf\n"
					+ "3. carregarGraf\n"
					+ "4. guardarPaths\n"
					+ "5. carregarPaths\n"
					+ "6. carregarClausures\n"
					+ "7. Sortir del driver\n"
					+ "Opcio = ");

			opt = nextInt();

			println();
			try {
				switch (opt) {
					case 1: {
						print("Escriu el path i fitxer a llegir: ");
						String filePath = nextLine();
						println();
						mostrarFitxer(filePath);
						println();
						break;
					}
					case 2: {
						Graf g = new Graf();
						print("Afegeix un paper de prova: ");
						g.afegeix(new Paper(0, nextLine()));
						print("Afegeix un autor de prova: ");
						g.afegeix(new Autor(0, nextLine()));
						g.afegirAdjacencia(g.consultarPaper(0), g.consultarAutor(0));
						println("S'ha possat una adjacencia entre aquests dos nodes.");
						print("\nEscriu el path i fitxer on guardar el graf: ");
						pathGraf = nextLine();
						ControladorPersistencia.guardarGraf(pathGraf, g);
						File f = new File(pathGraf);
						f.deleteOnExit();
						println("El fitxer " + f.getAbsolutePath() + " sera eliminat al sortir del driver.");
						break;
					}
					case 3: {
						if (pathGraf != null) {
							Graf g = ControladorPersistencia.carregarGraf(pathGraf);
							println("Graf carregat:");
							print(g);
						}
						else
							println("No has guardat cap graf.");
						break;
					}
					case 4: {
						ArrayList<Path> paths = new ArrayList<>();
						println("Afegeix paths per guardar. Posa un 0 per acabar.");
						String path;
						while (!(path = nextLine()).equals("0"))
							paths.add(new Path(path, "DescripcioPerDefecte"));
						print("Escriu el path i fitxer on guardar els paths: ");
						pathPaths = nextLine();
						ControladorPersistencia.guardarPaths(pathPaths, paths);
						File f = new File(pathPaths);
						f.deleteOnExit();
						println("El fitxer " + f.getAbsolutePath() + " sera eliminat al sortir del driver.");
						break;
					}
					case 5: {
						if (pathPaths != null) {
							println("Paths carregats:");
							ControladorPersistencia.carregarPaths(pathPaths).forEach(System.out::println);
						}
						else
							println("No has guardat cap path.");
						break;
					}
					case 6: {
						if (pathClausures != null) {
							println("Clausures carregades:");
							ControladorPersistencia.carregarClausures(pathClausures).entrySet().forEach(e -> {
								println(e.getKey());
								print(e.getValue());
							});
						}
						else
							println("No has guardat cap clausura.");
						break;
					}
					case 7:
						break;
					default:
						println("Introdueix una opcio de la 1 a la 7.");
				}
			} catch (Exception e) {
				println("Hi ha hagut un error:");
				print(e);
			}
			println();
		} while (opt != 7);
		
		close();
	}

	@SuppressWarnings("unused")
	private static Map<String, Matriu> crearClausures() {
		Map<String, Matriu> mapaClausures = new LinkedHashMap<>();
		print("Cuantes clausures vols afegir?: ");
		int nc = nextInt();
		for (int i = 0; i < nc; ++i) {
			String nomClausura = "Clausura_" + i;
			print("Files de la matriu de " + nomClausura + ": ");
			int f = nextInt();
			print("Columnes de la matriu de " + nomClausura + ": ");
			int c = nextInt();
			Matriu clausura = new Matriu(f, c, 0d);
			println("Introdueix tots els valors de la matriu per files (decimals amb comes). Afegeix un enter després de cada valor:");
			for (int ii = 0; ii < f; ++ii) {
				for (int jj = 0; jj < c; ++jj)
					clausura.set(ii, jj, nextDouble());
			}
			mapaClausures.put(nomClausura, clausura);
		}
		return mapaClausures;
	}

	private static void print(Graf g) {
		println("Papers:");
		for (int i = 0; i < g.consultaMidaPaper(); ++i)
			println("- " + g.consultarPaper(i).getNom());
		println("Autors:");
		for (int i = 0; i < g.consultaMidaAutor(); ++i)
			println("- " + g.consultarAutor(i).getNom());
		println("Matriu adjacencia Paper-Autor");
		print(g.consultarMatriuPaperAutor());
	}
	
	private static void print(Matriu m) {
		for (int i = 0; i < m.getFiles(); ++i) {
			for (int j = 0; j < m.getColumnes(); ++j)
				print(String.format(Locale.UK, "%.2f", m.get(i,  j)) + (j < m.getColumnes() - 1 ? ", " : ""));
			println();
		}
	}
	
	private static void mostrarFitxer(String filesystem_path) throws IOException {
		ControladorPersistencia.llegirFitxer(filesystem_path).forEach(System.out::println);
	}
	
}
