package drivers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map.Entry;

import domini.Autor;
import domini.Graf;
import domini.HeteSim;
import domini.Matriu;
import domini.Node;
import domini.Paper;

/**
 * Driver per provar el HeteSim.
 * @author Carlos Lazaro
 */
public class DriverHeteSim extends Driver {

	public static void main(String[] args) {
		HeteSim hs = null;
		
		int opcio;
		
		do {
			print("Escolleix una opcio:\n"
				+ "1. Crear HeteSim\n"
				+ "2. Test per defecte\n"
				+ "3. Sortir del driver\n");
			
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
					+ "2. Test per defecte\n"
					+ "3. getGraf\n"
					+ "4. clausura(path)\n"
					+ "5. clausura(left, right, path)\n"
					+ "6. heteSim(node, node, path)\n"
					+ "7. heteSimAmbIdentificadors(node, path)\n"
					+ "8. heteSimAmbNoms(node, path)\n"
					+ "9. guardarClausures(system_path)\n"
					+ "10. carregarClausures(system_path)\n"
					+ "11. Sortir del driver\n"
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
						print("Path: ");
						print(hs.clausura(nextWord().toUpperCase()));
						break;
					}
					case 5: {
						// TODO
						break;
					}
					case 6: {
						// TODO
						break;
					}
					case 7:
						// TODO
						break;
					case 8:
						// TODO
						break;
					case 9:
						print("Escriu el path i fitxer on guardarles: ");
						hs.guardarClausures(nextLine());
						break;
					case 10:
						print("Escriu el path i fitxer on estan guardades: ");
						hs.carregarClausures(nextLine());
						break;
					case 11: break;
					default:
						println("Introdueix una opcio de la 1 a la 11.");
				}
			} catch (Exception e) {
				println("Hi ha hagut un error: " + e + "\n" +
						Arrays.toString(e.getStackTrace()));
			}
			
			println();
		} while (opcio != 11);
		
		close();
	}
	
	private static HeteSim crearHeteSim() {
		// TODO
		return null;
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
				
		println("Mitjan�ant llistes");
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
		
		println("\nMitjan�ant clausura");
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
