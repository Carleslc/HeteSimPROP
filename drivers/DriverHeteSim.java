package drivers;

import java.util.Arrays;
import java.util.Locale;

import domini.Autor;
import domini.Graf;
import domini.HeteSim;
import domini.Matriu;
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
				+ "2. Test per defecte\n");
			
			opcio = nextInt();
			
			if (opcio == 1)
				hs = crearHeteSim();
			else if (opcio == 2)
				defaultTest();
			
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
			g.afegeix(new Paper(i, String.valueOf(i)));
		for (int i = 0; i < 4; ++i)
			g.afegeix(new Autor(i, String.valueOf(i)));
		
		g.afegirAdjacencia(g.consultarPaper(0), g.consultarAutor(0));
		g.afegirAdjacencia(g.consultarPaper(0), g.consultarAutor(1));
		
		g.afegirAdjacencia(g.consultarPaper(1), g.consultarAutor(1));
		g.afegirAdjacencia(g.consultarPaper(1), g.consultarAutor(2));
		
		g.afegirAdjacencia(g.consultarPaper(2), g.consultarAutor(2));
		g.afegirAdjacencia(g.consultarPaper(2), g.consultarAutor(3));
		
		HeteSim hs = new HeteSim(g);
		
		println("Mitjançant llistes");
		for (int i = 0; i < g.consultaMidaPaper(); ++i) {
			for (int j = 0; j < g.consultaMidaAutor(); ++j)
				print(String.format(Locale.UK, "%.2f", hs.heteSim(g.consultarPaper(i), g.consultarAutor(j), path)) + ", ");
			println();
		}
		
		println("\nMitjançant clausura");
		print(hs.clausura(path));
	}

	private static void print(Matriu<Double> m) {
		for (int i = 0; i < m.getFiles(); ++i) {
			for (int j = 0; j < m.getColumnes(); ++j)
				print(String.format(Locale.UK, "%.2f", m.get(i,  j)) + (j < m.getColumnes() - 1 ? ", " : ""));
			println();
		}
	}
	
}
