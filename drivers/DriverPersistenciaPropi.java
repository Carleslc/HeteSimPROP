package drivers;

import persistencia.*;
import domini.Resultat;
import domini.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import domini.Autor;
import domini.ControladorGraf;
import domini.ControladorPaths;
import domini.Paper;
import domini.Pair;

public class DriverPersistenciaPropi extends Driver {
	
	public static void main(String[] args) {
		TreeMap<Date, Resultat> resultats = new TreeMap<>();
		int opcio;
		do {
			print("Escolleix una opcio:\n"
					+ "1. Afegir nou Resultat\n"
					+ "2. Guardar Resultats\n"
					+ "3. Carregar Resultats\n"
					+ "4. Imprimir Resultats per pantalla\n"
					+ "5. Sortir del driver\n"
					+ "Opcio = ");
			
			opcio = nextInt();
			
			println();
			
			try {
				switch(opcio) {
				case 1:
					print("Introdueix un nom per la Dada: ");
					String nomDada = nextWord();
					print("Introdueix un path: ");
					String path = nextWord();
					print("Introdueix un nom pel Graf: ");
					String nomGraf = nextWord();
					print("Quantes parelles Node-Rellevancia vols afegir?: ");
					int n = nextInt();
					ArrayList<Pair<Double, Node>> rellevancies = new ArrayList<>(n);
					for (int i = 0; i < n; ++i) {
						print("Introdueix el nom del Node: ");
						String nomNode = nextWord();
						print("Introdueix la rellevancia: ");
						double re = nextDouble();
						rellevancies.add(new Pair<Double, Node>(re, new Autor(i, nomNode)));
					}
					resultats.put(new Date(), new Resultat(new Paper(0, nomDada),path, new ControladorPaths(new ControladorGraf()), nomGraf, rellevancies));
					break;
				case 2:
					print("Escriu el path del sistema i el fitxer on vols guardar els resultats: ");
					String filesystem_path = nextWord();
					ControladorPersistenciaPropi.guardarResultats(filesystem_path, resultats);
					break;
				case 3:
					print("Escriu el path i el fitxer on estan guardats els resultats: ");
					String filesystem_path2 = nextWord();
					resultats = ControladorPersistenciaPropi.carregarResultats(filesystem_path2);
					println("Resultats carregats correctament");
				case 4:
					print(resultats);
					break;
				case 5:
					break;
				default:
					print("Introdueix una opcio de la 1 a la 5");
				}
			} catch(Exception e) {
				println("\nHi ha hagut un error:");
				print(e);
			}
		} while (opcio != 5);
	}
	

}
