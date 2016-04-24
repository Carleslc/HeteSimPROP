package drivers;

import domini.ControladorMultigraf;
import domini.ControladorNodes;
import domini.ControladorPaths;
import domini.ControladorRelacions;
import domini.Node;
import domini.Threshold;

public class DriverThreshold extends Driver {

	public static void main(String[] args) {

		try {

			ControladorMultigraf contrMultigraf = new ControladorMultigraf();

			println("Introdueix el graf:");
			print("Primer, introdueix el nom del graf: ");
			String nomGraf = nextLine();
			contrMultigraf.afegirGraf(nomGraf);

			ControladorNodes contrNodes = new ControladorNodes(contrMultigraf);
			println("Ara, introdueix els noms de tots els autors. Acaba amb -1:");
			String nom = nextLine();
			int id;
			while(!nom.equals("-1")) {
				id = contrNodes.afegirAutor(nom);
				println("S'ha afegit un autor amb id " + id + " i nom " + nom);
				nom = nextLine();
			}

			println("Ara, introdueix els noms de totes les conferències. Acaba amb un -1:");
			nom = nextLine();
			while(!nom.equals("-1")) {
				id = contrNodes.afegirConferencia(nom);
				println("S'ha afegit una conferència amb id " + id + " i nom " + nom);
				nom = nextLine();
			}

			println("Ara, introdueix els noms de tots els papers. Acaba amb un -1:");
			nom = nextLine();
			while(!nom.equals("-1")) {
				id = contrNodes.afegirPaper(nom);
				println("S'ha afegit un paper amb id " + id + " i nom " + nom);
				nom = nextLine();
			}

			println("Ara, introdueix els noms de tots els termes. Acaba amb un -1:");
			nom = nextLine();
			while(!nom.equals("-1")) {
				id = contrNodes.afegirTerme(nom);
				println("S'ha afegit un terme amb id " + id + " i nom " + nom);
				nom = nextLine();
			}

			ControladorRelacions contrRelacions = new ControladorRelacions(contrMultigraf);
			println("Ara, introdueix les relacions entre papers i autors en el següent format:");
			println("idPaper\n" + "idAutor");
			println("Acaba amb un -1:");
			int id1 = nextInt();
			int id2;
			while(id1 != -1) {
				id2 = nextInt();
				if (contrRelacions.afegirAdjacenciaPaperAutor(id1, id2))
					println("S'ha afegit correctament la relació.");
				else {
					println("Hi ha hagut un error. Introdueix els ids en el format correcte:");
					println("idPaper\n" + "idAutor");
				}
				id1 = nextInt();
			}

			println("Ara, introdueix les relacions entre papers i conferències en el següent format:");
			println("idPaper\n" + "idConferència");
			println("Acaba amb un -1:");
			id1 = nextInt();
			while(id1 != -1) {
				id2 = nextInt();
				if (contrRelacions.setAdjacenciaPaperConferencia(id1, id2))
					println("S'ha afegit correctament la relació.");
				else {
					println("Hi ha hagut un error. Introdueix els ids en el format correcte:");
					println("idPaper\n" + "idConferència");
				}
				id1 = nextInt();
			}

			println("Ara, introdueix les relacions entre papers i termes en el següent format:");
			println("idPaper\n" + "idTerme");
			println("Acaba amb un -1:");
			id1 = nextInt();
			while(id1 != -1) {
				id2 = nextInt();
				if (contrRelacions.afegirAdjacenciaPaperTerme(id1, id2))
					println("S'ha afegit correctament la relació.");
				else {
					println("Hi ha hagut un error. Introdueix els ids en el format correcte:");
					println("idPaper\n" + "idTerme");
				}
				id1 = nextInt();
			}

			ControladorPaths contrPaths = new ControladorPaths(contrMultigraf);
			print("Introdueix el nom del path del threshold: ");
			String path;
			boolean bool;
			do {
				path = nextWord();
				bool = contrPaths.afegir(path);
				if (!bool) print("Path incorrecte. Introdueix un path correcte: ");
			} while (!bool);

			print("Introdueix l'id del primer node: ");
			Node a;
			do {
				id = nextInt();
				if (path.charAt(0) == 'A') a = contrMultigraf.getGraf().consultarAutor(id);
				else if (path.charAt(0) == 'C') a = contrMultigraf.getGraf().consultarConferencia(id);
				else if (path.charAt(0) == 'P') a = contrMultigraf.getGraf().consultarPaper(id);
				else a = contrMultigraf.getGraf().consultarTerme(id);
				if (a.getId() == -1) {
					id = -1;
					print("El node no existeix. Introdueix un id correcte: ");
				}
			} while (id == -1);

			print("Introdueix l'id del segon node: ");
			Node b;
			do {
				id = nextInt();
				if (path.charAt(path.length()-1) == 'A') b = contrMultigraf.getGraf().consultarAutor(id);
				else if (path.charAt(path.length()-1) == 'C') b = contrMultigraf.getGraf().consultarConferencia(id);
				else if (path.charAt(path.length()-1) == 'P') b = contrMultigraf.getGraf().consultarPaper(id);
				else b = contrMultigraf.getGraf().consultarTerme(id);
				if (b.getId() == -1) {
					id = -1;
					print("El node no existeix. Introdueix un id correcte: ");
				}
			} while (id == -1);

			Threshold t = new Threshold(a, b, path, contrMultigraf.getHeteSim());

			int num;
			do {
				println("Per obtenir els nodes del threshold prem 1.");
				println("Per obtenir el nom del path del threshold prem 2.");
				println("Per obtenir la rellevància prem 3.");
				println("Per canviar la rellevància prem 4.");
				println("Per passar el threshold a string prem 5.");
				println("Per sortir prem 0.");

				num = nextInt();

				switch(num) {
				case 0:
					break;

				case 1: {
					println("Els nodes del threshold són:");
					println(t.getNodes().toString());
					break;
				}

				case 2:
					println("El path del threshold és: " + t.getPath());
					break;

				case 3:
					println("La rellevància és: " + t.getRellevancia());
					break;

				case 4: {
					print("Introdueix la nova rellevància: ");
					t.setRellevancia(Double.parseDouble(nextLine()));
					println("La rellevància s'ha modificat. La nova rellevància és " + t.getRellevancia());
					break;
				}

				case 5:
					println("Threshold:\n" + t.toString());
					break;

				default:
					println ("Error! Has d'introduir un nombre del 0 al 5!");
				}
			} while (num != 0);
		}
		catch (Exception e){
			println("\nHi ha hagut un error:");
			print(e);
		} finally {
			close();
		}
	}

}
