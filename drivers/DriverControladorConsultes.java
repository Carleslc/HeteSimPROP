package drivers;

import java.text.SimpleDateFormat;
import java.util.Date;

import domini.ControladorMultigraf;
import domini.ControladorNodes;
import domini.ControladorRelacions;
import domini.ControladorPaths;
import domini.ControladorConsultes;

public class DriverControladorConsultes extends Driver {

	public static void main(String[] args) {
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
		println("Introdueix els paths. Acaba amb un -1:");
		String path = nextWord();
		while(!path.equals("-1")) {
			if (contrPaths.afegir(path))
				println("S'ha afegit el path " + path);
			else
				println("Path incorrecte. Introdueix un path correcte.");
			path = nextWord();
		}

		ControladorConsultes contrConsultes = new ControladorConsultes(contrMultigraf, contrPaths);

		int num = 1;
		do {
			try {
				println("Per fer una consulta sense threshold prem 1.");
				println("Per fer una consulta amb threshold prem 2.");
				println("Per veure totes les dates en que s'han fet consultes prem 3.");
				println("Per veure el resultat de l'última consulta prem 4.");
				println("Per veure el resultat d'una data prem 5.");
				println("Per esborrar una consulta prem 6.");
				println("Per esborrar tots els resultats de l'última consulta tret dels n primers, prem 7.");
				println("Per esborrar tots els resultats de l'última consulta tret dels n últims, prem 8.");
				println("Per filtrar els resultats de l'última consulta segons una etiqueta, prem 9.");
				println("Per filtrar els resultats de l'última consulta segons una rellevància mínima i una màxima, pem 10.");
				println("Per esborrar tots els resultats de l'última consulta prem 11.");
				println("Per afegir un resultat a l'última consulta prem 12.");
				println("Per esborrar un resultat de l'última consulta prem 13.");
				println("Per consultar el tipus del node del resultat de l'última consulta prem 14.");
				println("Per modificar la rellevància d'un resultat de l'última consulta prem 15.");
				println("Per modificar el node d'un resultat de l'última consulta prem 16.");
				println("Per modificar el nom d'un resultat de l'última consulta prem 17.");
				println("Per modificar el threshold de l'última consulta prem 18.");
				println("Per modificar el path i la dada de l'última consulta prem 19.");
				println("Per modificar la dada de l'última consulta prem 20.");
				println("Per exportar el resultat de l'última consulta prem 21.");
				println("Per guardar tots els resultats prem 22.");
				println("Per carregar tots els resultats prem 23.");
				println("Per sortir prem 0.");

				num = nextInt();

				switch(num) {
				case 0:
					break;

				case 1: {
					println("Introdueix el path amb el que vols fer la consulta:");
					path = nextWord();
					print("Introdueix l'id del node amb el que vols fer la consulta: ");
					id = nextInt();
					String resultat = contrConsultes.consulta(path, id);
					println("El resultat de la consulta és:\n" + resultat);
					break;
				}

				case 2: {
					println("Introdueix el path amb el que vols fer la consulta:");
					path = nextWord();
					print("Introdueix l'id del node amb el que vols fer la consulta: ");
					id = nextInt();
					println("Introdueix el node del threshold:");
					String pathT = nextWord();
					print("Introdueix l'id del primer node del threshold: ");
					int idT1 = nextInt();
					print("Introdueix l'id del primer node del threshold: ");
					int idT2 = nextInt();
					String resultat = contrConsultes.consulta(path, id, idT1, idT2, pathT);
					println("El resultat de la consulta és:\n" + resultat);
					break;
				}

				case 3: 
					println("Les dates en que s'han fet consultes són:");
					println(contrConsultes.consultarDates());
					break;

				case 4:
					println("El resultat de l'última consulta és:");
					println(contrConsultes.consultarResultat());
					break;

				case 5: {
					println("Introdueix una data amb el format dd/MM/yy HH:mm");
					SimpleDateFormat df = new SimpleDateFormat();
					Date d = df.parse(nextLine());
					println("El resultat de la data " + d + "és:");
					println(contrConsultes.consultarResultat(d));
					break;
				}

				case 6: {
					println("Introdueix una data amb el format dd/MM/yy HH:mm");
					SimpleDateFormat df = new SimpleDateFormat();
					Date d = df.parse(nextLine());
					if (contrConsultes.esborrarConsulta(d))
						println("S'ha esborrat la consulta de la data " + d);
					else
						println("En la data " + d + "no s'ha fet cap consulta.");
					break;
				}

				case 7: {
					print("Introdueix un enter: ");
					int n = nextInt();
					contrConsultes.filtrarElsPrimers(n);
					println("S'han esborrat tots els resultats de l'última consulta tret dels " + n + " primers.");
					break;
				}

				case 8: {
					print("Introdueix un enter: ");
					int n = nextInt();
					contrConsultes.filtrarElsUltims(n);
					println("S'han esborrat tots els resultats de l'última consulta tret dels " + n + " últims.");
					break;
				}

				case 9: {
					print("Introdueix una etiqueta: ");
					String label = nextWord();
					contrConsultes.filtrarPerEtiqueta(label);
					println("S'han esborrat tots els resultats de l'última consulta tret dels que contenen l'etiqueta " + label);
					break;
				}

				case 10: {
					print("Introdueix la rellevància mínima que vols que tinguin els resultats: ");
					Double min = nextDouble();
					print("Introdueix la rellevància màxima que vols que tinguin els resultats: ");
					Double max = nextDouble();
					contrConsultes.filtrarPerRellevancia(min, max);
					println("S'han esborrat tots els resultats de l'última consulta tret dels que tenen una rellevància major o igual que " + min + " i menor o igual que " + max);
					break;
				}

				case 11: {
					contrConsultes.clear();
					println("S'han esborrat tots els resultats de l'última consulta.");
					break;
				}

				case 12: {
					print("Introdueix l'id del node del resultat que vols afegir: ");
					id = nextInt();
					print("Introdueix la rellevància: ");
					double rellevancia = nextDouble();
					contrConsultes.afegir(rellevancia, id);
					println("S'ha afegit al resultat de l'última consulta el node indicat.");
					break;
				}

				case 13: {
					print("Introdueix l'índex del resultat que vols esborrar.");
					int index = nextInt();
					contrConsultes.esborrar(index);
					println("S'ha esborrat el resultat número " + index + " de lúltima consulta.");
					break;
				}

				case 14:
					println("El node del resultat de l'última consulta és de tipus " + contrConsultes.getTipusNode());
					break;

				case 15: {
					print("Introdueix l'índex del resultat que vols modificar: ");
					int index = nextInt();
					print("Introdueix la nova rellevància: ");
					double rellevancia = nextDouble();
					if (contrConsultes.setRellevancia(index, rellevancia))
						println("S'ha modificat el resultat número " + index + "de l'última consulta. La nova rellevància és " + rellevancia);
					else
						println("No s'ha pogut modificar la rellevància del resultat número " + index);
					break;
				}

				case 16: {
					print("Introdueix l'índex del resultat que vols modificar: ");
					int index = nextInt();
					print("Introdueix l'id del nou node: ");
					id = nextInt();
					if(contrConsultes.setDada(index, id))
						println("S'ha modificat el resultat número " + index + "de l'última consulta. L'id del nou node és " + id);
					else
						println("No s'ha pogut modificar el node del resultat número " + index);
					break;
				}

				case 17: {
					print("Introdueix l'índex del resultat que vols modificar: ");
					int index = nextInt();
					print("Introdueix el nou nom: ");
					nom = nextLine();
					if(contrConsultes.canviarNom(index, nom))
						println("S'ha modificat el resultat número " + index + "de l'última consulta. El noou nom és " + nom);
					else
						println("No s'ha pogut modificar el nom del resultat número " + index);
					break;
				}

				case 18: {
					println("Introdueix el nom del path del threshold:");
					path = nextWord();
					println("Introdueix l'id del primer node del threshold:");
					id1 = nextInt();
					println("Introdueix l'id del segon node del threshold:");
					id2 = nextInt();
					contrConsultes.setThreshold(id1, id2, path);
					println("S'ha modificat el threshold de l'última consulta.");
					break;
				}

				case 19: {
					println("Introdueix el nom del nou path:");
					path = nextWord();
					print("Introdueix l'id de la nova dada: ");
					id = nextInt();
					contrConsultes.setPath(path, id);
					println("S'han modificat el path i la dada de l'ultima consulta.");
					break;
				}

				case 20: {
					print("Introdueix l'id de la nova dada: ");
					id = nextInt();
					contrConsultes.setDada(id);
					println("S'han modificat la dada de l'ultima consulta.");
					break;
				}

				case 21: {
					println("Introdueix el filesystem_path on vols exportar el resultat.");
					String f = nextLine();
					contrConsultes.exportarResultat(f);
					println("S'ha exportat el resultat de l'última consulta.");
					break;
				}

				case 22: {
					contrConsultes.guardarResultats();
					println("S'han guardat tots els resultats.");
					break;
				}

				case 23: {
					contrConsultes.carregarResultats();
					println("S'han carregat tots els resultats.");
					break;
				}

				default:
					println ("Error! Has d'introduir un nombre del 0 al 23!");
				}
			} catch (Exception e) {
				println("Hi ha hagut un error:");
				println(e);
			}
		} while (num != 0);
		close();
	}
}
