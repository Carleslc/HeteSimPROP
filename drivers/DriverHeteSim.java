package drivers;

import java.util.ArrayList;
import java.util.Arrays;
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
		HeteSim hs = null;
		
		int opcio;
		
		do {
			print("Escolleix una opcio:\n"
				+ "1. Crear HeteSim\n"
				+ "2. Test per defecte (de l'article)\n"
				+ "3. Sortir del Driver");
			
			opcio = nextInt();
			
			if (opcio == 1)
				hs = crearHeteSim();
			else if (opcio == 2)
				defaultTest();
			else if (opcio == 3)
				return;
			println();
		} while (opcio != 1);
		
		do {
			print("Escolleix una opcio:\n"
					+ "1. Crear HeteSim\n"
					+ "2. Test per defecte (de l'article)\n"
					+ "3. getGraf\n"
					+ "4. clausura(path)\n"
					+ "5. clausura(left, right, path)\n"
					+ "6. heteSim(node, node, path)\n"
					+ "7. heteSimAmbIdentificadors(node, path)\n"
					+ "8. heteSimAmbNoms(node, path)\n"
					+ "9. guardarClausures(system_path)\n"
					+ "10. carregarClausures(system_path)\n"
					+ "11. Afegir Autors\n"
					+ "12. Afegir Papers\n"
					+ "13. Afegir Adjacencia Papers-Autors\n"
					+ "14. Sortir del driver\n"
					+ "Opcio = ");
			
			opcio = nextInt();
			
			println();
			
			try {
				switch (opcio) {
					case 1: {
						hs = crearHeteSim();
						break;
					}
					case 2:
						defaultTest();
						break;
					case 3:
						println(hs.getGraf());
						break;
					case 4: {
						print("Path: (només amb Autors i Papers): ");
						System.out.println((hs.clausura(nextWord().toUpperCase())));
						break;
					}
					case 5: {
						// TODO
						break;
					}
					case 6: {
						println("Entre quin Paper y Autor vols fer el càlcul? ");
						print("Primer Node (Paper o Autor): ");
						int paper = nextInt();
						print("Últim Node (Paper o Autor): ");
						int autor = nextInt();
						print("Introdueix el path: ");
						String path = nextWord();
						System.out.println(hetesimNodes(paper, autor, path.toUpperCase(), hs));
						break;
					}
					case 7:
						println("De quin Node (Paper o Autor) vols obtenir les rellevàncies? ");
						print("Introdueix el primer Node (Paper o Autor");
						int node = nextInt();
						print("Introdueix el path: ");
						String path = nextWord();
						System.out.println(hetesimid(node, path.toUpperCase(), hs));
						break;
					case 8:
						println("De quin Node (Paper o Autor) vols obtenir les rellevàncies? ");
						print("Introdueix el primer Node (Paper o Autor)");
						int node2 = nextInt();
						print("Introdueix el path: ");
						String path2 = nextWord();
						System.out.println(hetesimnom(node2, path2.toUpperCase(), hs));
						break;
					case 9:
						print("Escriu el path i fitxer on guardarles: ");
						hs.guardarClausures(nextLine());
						break;
					case 10:
						print("Escriu el path i fitxer on estan guardades: ");
						hs.carregarClausures(nextLine());
						break;
					case 11:
						print("Quants autors vols afegir?: ");
						int autors = nextInt();
						afegirAutors(autors, hs);
						break;
					case 12:
						print("Quants Papers vols afegir?: ");
						int papers = nextInt();
						afegirPapers(papers, hs);
						break;
					case 13:
						println("Entre quins Papers i Autors vols afegir una adjacencia? ");
						print("Paper: ");
						int paper = nextInt();
						print("Autor: ");
						int autor = nextInt();
						afegirAdjacencia(paper, autor, hs);
						break;
					case 14: 
						break;
					default:
						println("Introdueix una opcio de la 1 a la 14.");
				}
			} catch (Exception e) {
				println("Hi ha hagut un error: " + e + "\n" +
						Arrays.toString(e.getStackTrace()));
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
		for (int i = 0; i < autors; ++i) {
			g.afegeix(new Autor(i, "A"+String.valueOf(i)));
		}
	}
	
	private static void afegirPapers(int papers, HeteSim hs) {
		Graf g = hs.getGraf();
		for (int i = 0; i < papers; ++i) {
			g.afegeix(new Paper(i, "P"+String.valueOf(i)));
		}
	}
	
	private static void afegirAdjacencia(int paper, int autor, HeteSim hs) {
		Graf g = hs.getGraf();
		g.afegirAdjacencia(g.consultarPaper(paper), g.consultarAutor(autor));
	}
	
	private static double hetesimNodes(int paper, int autor, String path, HeteSim hs) {
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
	
	private static ArrayList<Entry<Double, Integer>> hetesimid (int node, String path, HeteSim hs) {
		if (path.startsWith("A")) {
			return hs.heteSimAmbIdentificadors(hs.getGraf().consultarAutor(node), path);
		}
		else {
			return hs.heteSimAmbIdentificadors(hs.getGraf().consultarPaper(node), path);
		}
	}
	
	private static ArrayList<Entry<Double, String>> hetesimnom (int node, String path, HeteSim hs) {
		if (path.startsWith("A")) {
			return hs.heteSimAmbNoms(hs.getGraf().consultarAutor(node), path);
		}
		else {
			return hs.heteSimAmbNoms(hs.getGraf().consultarPaper(node), path);
		}
	}

	private static void defaultTest() {
		String path = "PA";
		Graf g = new Graf();
		for (int i = 0; i < 3; ++i)
			g.afegeix(new Paper(i, "Paper" + i));
		for (int i = 0; i < 4; ++i)
			g.afegeix(new Autor(i, "Autor" + i));
		
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
		for (int i = 0; i < g.consultaMidaPaper(); ++i) {
			ArrayList<Entry<Double, Integer>> m = hs.heteSimAmbIdentificadors(g.consultarPaper(i), path);
			print(getTipusNode(path, true) + " " + i + " amb " + getTipusNode(path, false) + " [");
			for (int j = 0; j < m.size(); ++j) {
				Entry<Double, Integer> e = m.get(j);
				print(e.getValue() + ": " + String.format(Locale.UK, "%.2f", e.getKey()) + (j < m.size() - 1 ? ", " : ""));
			}
			println("]");
		}
		
		println("\nMitjançant clausura");
		print(hs.clausura(path));
		
		println("\nHeteSim amb Noms");
		for (int i = 0; i < g.consultaMidaPaper(); ++i) {
			ArrayList<Entry<Double, String>> m = hs.heteSimAmbNoms(g.consultarPaper(i), path);
			print(getTipusNode(path, true) + " " + i + " amb " + getTipusNode(path, false) + " [");
			for (int j = 0; j < m.size(); ++j) {
				Entry<Double, String> e = m.get(j);
				print(e.getValue() + ": " + String.format(Locale.UK, "%.2f", e.getKey()) + (j < m.size() - 1 ? ", " : ""));
			}
			println("]");
		}
	}

	private static void print(Matriu<Double> m) {
		for (int i = 0; i < m.getFiles(); ++i) {
			for (int j = 0; j < m.getColumnes(); ++j)
				print(String.format(Locale.UK, "%.2f", m.get(i,  j)) + (j < m.getColumnes() - 1 ? ", " : ""));
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
