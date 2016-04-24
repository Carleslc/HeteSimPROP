package drivers;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map.Entry;

import domini.HeteSim;
import domini.Autor;
import domini.Graf;
import domini.Paper;
import domini.Matriu;

/**
 * Driver per provar el HeteSim.
 * @author Carlos Lazaro, Guillem Castro
 */
public class DriverHeteSim extends Driver {

	public static void main(String[] args) {
		HeteSim hs = crearHeteSim();
		
		int opcio;
		do {
			print("Escolleix una opcio:\n"
					+ "1. Crear nou HeteSim\n"
					+ "2. Joc de proves per defecte (de l'article)\n"
					+ "3. getGraf\n"
					+ "4. clausura(path)\n"
					+ "5. heteSim(node, node, path)\n"
					+ "6. heteSimAmbIdentificadors(node, path)\n"
					+ "7. heteSimAmbNoms(node, path)\n"
					+ "8. guardarClausures(system_path)\n"
					+ "9. carregarClausures(system_path)\n"
					+ "10. Afegir Autors\n"
					+ "11. Afegir Papers\n"
					+ "12. Afegir Adjacencia Paper-Autor\n"
					+ "13. Eliminar Adjacencia Paper-Autor\n"
					+ "14. Sortir del driver\n"
					+ "Opcio = ");
			
			opcio = nextInt();
			
			println();
			
			try {
				switch (opcio) {
					case 1:
						hs = crearHeteSim();
						break;
					case 2:
						jocDeProves();
						break;
					case 3:
						Graf g = hs.getGraf();
						println("Matriu adjacencia Paper-Autor");
						print(g.consultarMatriuPaperAutor());
						break;
					case 4: {
						print("Path: (nomes amb Autors i Papers): ");
						try {
							println(hs.clausura(nextWord().toUpperCase()));
						} catch (Exception e) {
							println("\nPath incorrecte.");
						}
						break;
					}
					case 5: {
						println("Entre quin Paper y Autor vols fer el calcul?");
						print("Primer Node (Paper o Autor): ");
						int paper = nextInt();
						print("Ultim Node (Paper o Autor): ");
						int autor = nextInt();
						print("Introdueix el path: ");
						String path = nextWord();
						try {
							println("Rellevancia: " + String.format(Locale.UK, "%.2f", hetesimNodes(paper, autor, path.toUpperCase(), hs)));
						} catch (IllegalArgumentException e) {
							println("\nAquests nodes no existeixen.");
						}
						break;
					}
					case 6: {
						println("De quin Node (Paper o Autor) vols obtenir les rellevancies? ");
						print("Introdueix el primer Node (Paper o Autor");
						int node = nextInt();
						print("Introdueix el path: ");
						String path = nextWord();
						try {
							printAmbID(heteSimId(node, path.toUpperCase(), hs), node, path);
						} catch (IllegalArgumentException e) {
							println("\nAquests nodes no existeixen.");
						}
						break;
					}
					case 7: {
						println("De quin Node (Paper o Autor) vols obtenir les rellevancies? ");
						print("Introdueix el primer Node (Paper o Autor)");
						int node = nextInt();
						print("Introdueix el path: ");
						String path = nextWord();
						try {
							printAmbNom(heteSimNom(node, path.toUpperCase(), hs), node, path);
						} catch (IllegalArgumentException e) {
							println("\nAquests nodes no existeixen.");
						}
						break;
					}
					case 8: {
						print("Escriu el path i fitxer on guardarles: ");
						String system_path = nextLine();
						hs.guardarClausures(system_path);
						File f = new File(system_path);
						f.deleteOnExit();
						println("El fitxer " + f.getAbsolutePath() + " sera eliminat al sortir del driver.");
						break;
					}
					case 9: {
						print("Escriu el path i fitxer on estan guardades: ");
						hs.carregarClausures(nextLine());
						println("Clausures carregades correctament.");
						break;
					}
					case 10: {
						print("Quants autors vols afegir?: ");
						int autors = nextInt();
						afegirAutors(autors, hs);
						break;
					}
					case 11: {
						print("Quants Papers vols afegir?: ");
						int papers = nextInt();
						afegirPapers(papers, hs);
						break;
					}
					case 12: {
						println("Entre quins Papers i Autors vols afegir una adjacencia? ");
						print("Paper: ");
						int paper = nextInt();
						print("Autor: ");
						int autor = nextInt();
						try {
							afegirAdjacencia(paper, autor, hs);
						} catch (Exception e) {
							println("\nAquests nodes no existeixen.");
						}
						break;
					}
					case 13: {
						println("Entre quins Papers i Autors vols eliminar una adjacencia? ");
						print("Paper: ");
						int paper = nextInt();
						print("Autor: ");
						int autor = nextInt();
						try {
							eliminarAdjacencia(paper, autor, hs);
						} catch (Exception e) {
							println("\nAquests nodes no existeixen.");
						}
						break;
					}
					case 14:
						break;
					default:
						println("Introdueix una opcio de la 1 a la 14.");
				}
			} catch (Exception e) {
				println("\nHi ha hagut un error:");
				print(e);
			}
			
			println();
		} while (opcio != 14);
		
		close();
	}
	
	private static HeteSim crearHeteSim() {
		Graf g = new Graf();
		HeteSim h = new HeteSim(g);
		return h;
	}
	
	private static void afegirAutors(int autors, HeteSim hs) {
		Graf g = hs.getGraf();
		for (int i = 0; i < autors; ++i)
			g.afegeix(new Autor(i, "A" + String.valueOf(i)));
		int size = g.consultaMidaAutor();
		if (size > 0)
			println("Autors actuals: ID 0 - " + (size - 1));
		else
			println("No hi ha cap autor afegit.");
	}
	
	private static void afegirPapers(int papers, HeteSim hs) {
		Graf g = hs.getGraf();
		for (int i = 0; i < papers; ++i)
			g.afegeix(new Paper(i, "P" + String.valueOf(i)));
		int size = g.consultaMidaPaper();
		if (size > 0)
			println("Papers actuals: ID 0 - " + (size - 1));
		else
			println("No hi ha cap paper afegit.");
	}
	
	private static void afegirAdjacencia(int paper, int autor, HeteSim hs) {
		Graf g = hs.getGraf();
		g.afegirAdjacencia(g.consultarPaper(paper), g.consultarAutor(autor));
	}
	
	private static void eliminarAdjacencia(int paper, int autor, HeteSim hs) {
		Graf g = hs.getGraf();
		g.eliminarAdjacencia(g.consultarPaper(paper), g.consultarAutor(autor));
	}
	
	private static double hetesimNodes(int paper, int autor, String path, HeteSim hs) throws IllegalArgumentException {
		if (path.startsWith("P") && path.endsWith("A"))
			return hs.heteSim(hs.getGraf().consultarPaper(paper), hs.getGraf().consultarAutor(autor), path);
		else if (path.startsWith("P") && path.endsWith("P"))
			return hs.heteSim(hs.getGraf().consultarPaper(paper), hs.getGraf().consultarPaper(autor), path);
		else if (path.startsWith("A") && path.endsWith("P"))
			return hs.heteSim(hs.getGraf().consultarAutor(paper), hs.getGraf().consultarPaper(autor), path);
		else if (path.startsWith("A") && path.endsWith("A"))
			return hs.heteSim(hs.getGraf().consultarAutor(paper), hs.getGraf().consultarAutor(autor), path);
		return 0.0;
	}
	
	private static ArrayList<Entry<Double, Integer>> heteSimId(int node, String path, HeteSim hs) throws IllegalArgumentException {
		if (path.startsWith("A"))
			return hs.heteSimAmbIdentificadors(hs.getGraf().consultarAutor(node), path);
		else
			return hs.heteSimAmbIdentificadors(hs.getGraf().consultarPaper(node), path);
	}
	
	private static ArrayList<Entry<Double, String>> heteSimNom(int node, String path, HeteSim hs) throws IllegalArgumentException {
		if (path.startsWith("A"))
			return hs.heteSimAmbNoms(hs.getGraf().consultarAutor(node), path);
		else
			return hs.heteSimAmbNoms(hs.getGraf().consultarPaper(node), path);
	}

	private static void jocDeProves() {
		String path = "PA";
		Graf g = new Graf();
		for (int i = 0; i < 3; ++i)
			g.afegeix(new Paper(i, "P" + i));
		for (int i = 0; i < 4; ++i)
			g.afegeix(new Autor(i, "A" + i));
		
		g.afegirAdjacencia(g.consultarPaper(0), g.consultarAutor(0));
		g.afegirAdjacencia(g.consultarPaper(0), g.consultarAutor(1));
		g.afegirAdjacencia(g.consultarPaper(1), g.consultarAutor(1));
		g.afegirAdjacencia(g.consultarPaper(1), g.consultarAutor(2));
		g.afegirAdjacencia(g.consultarPaper(1), g.consultarAutor(3));
		g.afegirAdjacencia(g.consultarPaper(2), g.consultarAutor(3));
		
		HeteSim hs = new HeteSim(g);
				
		println("Mitjançant llistes");
		for (int i = 0; i < g.consultaMidaPaper(); ++i) {
			for (int j = 0; j < g.consultaMidaAutor(); ++j)
				print(String.format(Locale.UK, "%.2f", hs.heteSim(g.consultarPaper(i), g.consultarAutor(j), path)) + ", ");
			println();
		}
		
		println("\nHeteSim amb ID");
		for (int i = 0; i < g.consultaMidaPaper(); ++i)
			printAmbID(hs.heteSimAmbIdentificadors(g.consultarPaper(i), path), i, path);
		
		println("\nMitjançant clausura");
		print(hs.clausura(path));
		
		println("\nHeteSim amb Noms");
		for (int i = 0; i < g.consultaMidaPaper(); ++i)
			printAmbNom(hs.heteSimAmbNoms(g.consultarPaper(i), path), i, path);
	}

	private static void printAmbID(ArrayList<Entry<Double, Integer>> a, int id, String path) {
		print(getTipusNode(path, true) + " " + id + " amb " + getTipusNode(path, false) + " [");
		for (int j = 0; j < a.size(); ++j) {
			Entry<Double, Integer> e = a.get(j);
			print(e.getValue() + ": " + String.format(Locale.UK, "%.2f", e.getKey()) + (j < a.size() - 1 ? ", " : ""));
		}
		println("]");
	}
	
	private static void printAmbNom(ArrayList<Entry<Double, String>> a, int id, String path) {
		print(getTipusNode(path, true) + " " + id + " amb " + getTipusNode(path, false) + " [");
		for (int j = 0; j < a.size(); ++j) {
			Entry<Double, String> e = a.get(j);
			print(e.getValue() + ": " + String.format(Locale.UK, "%.2f", e.getKey()) + (j < a.size() - 1 ? ", " : ""));
		}
		println("]");
	}
	
	private static void print(Matriu<? extends Number> m) {
		for (int i = 0; i < m.getFiles(); ++i) {
			for (int j = 0; j < m.getColumnes(); ++j)
				print(String.format(Locale.UK, "%.2f", m.get(i,  j).doubleValue()) + (j < m.getColumnes() - 1 ? ", " : ""));
			println();
		}
	}
	
	private static String getTipusNode(String path, boolean first) {
		if (!path.isEmpty()) {
			switch (path.charAt(first ? 0 : path.length() - 1)) {
				case 'A': return "Autor";
				case 'P': return "Paper";
				case 'T': return "Terme";
				case 'C': return "Conferencia";
			}
		}
		return "Node";
	}
	
}
