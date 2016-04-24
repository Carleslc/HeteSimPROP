package resultat;

	import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


	public final class DriverResultat extends Driver {
		
		public static void main(String[] args) {
			Node n = new Autor(0,"Joan","hola");
			Node n1 = new Autor(0,"Pere", "1");
			Node n2 = new Autor(0,"Marta","1");
			Node n3 = new Autor(0,"Sara","2");
			Node n4 = new Autor(0,"Carla","3");
			Pair<Double, Node> p1 = new Pair<Double, Node>(0.9,n1);
			Pair<Double, Node> p2 = new Pair<Double, Node>(0.54,n2);
			Pair<Double, Node> p3 = new Pair<Double, Node>(0.6,n3);
			Pair<Double, Node> p4 = new Pair<Double, Node>(0.2,n4);
			ArrayList<Pair<Double, Node>> l = new ArrayList<Pair<Double, Node>>();
			l.add(p1);l.add(p2);l.add(p3);l.add(p4);
			Threshold t = new Threshold(0,n,n,"APAPA",new HeteSim());
			Resultat r = new Resultat(n,"APA",new ControladorPaths(new ControladorGraf()),"Graf1",l,t);
			print("El driver comença amb un objecte Resultat de mostra:\n");
			print(r.toString());
			println();
			
			int opcio;
			do {
				print("Escull una opcio:\n"
						+ "1. Crear un nou resultat sense tuples\n"
						+ "2. SetNode\n"
						+ "3. getNode\n"
						+ "4. setPath\n"
						+ "5. getPath\n"
						+ "6. setNomGraf\n"
						+ "7. getNomGraf\n"
						+ "8. setResultats\n"
						+ "9. getResultats\n"
						+ "10. setThreshold\n"
						+ "11. getThreshold\n"
						+ "12. size\n"
						+ "13. isEmpty()\n"
						+ "14. get(index)\n"
						+ "15. set(index,pair)\n"
						+ "16. clear()\n"
						+ "17. afegir(pair)\n"
						+ "18. esborrar(index)\n"
						+ "19. setRellevancia(index,rellevancia)\n"
						+ "20. setDada(index,dada)\n"
						+ "21. filtrarElsPrimers(n)\n"
						+ "22. filtrarElsUltims(n)\n"
						+ "23. filtrarPerEtiqueta(label)\n"
						+ "24. filtrarPerRellevancia(min,max)\n"
						+ "25. imprimir resultats\n"
						+ "26. Sortir del driver\n"
						+ "Opcio = ");
				
				opcio = nextInt();
				
				println();
				
				try {
					switch (opcio) {
						case 1: {
							r = crearResultat();
							break;
						}
						case 2:
							r.setNode(crearNode());
							break;
						case 3:
							print(r.getNode());
							break;
						
						case 4: 
							print("NomPath: ");
							String nomPath = nextLine();
							r.setPath(nomPath);
							break;
						
						case 5: 
							print(r.getPath());
							break;
						
						case 6: 
							print("NomGraf: ");
							String nomGraf = nextLine();
							r.setNomGraf(nomGraf);
							break;
						case 7:
							print(r.getNomGraf());
							break;
						case 8:
							print("Funcio no disponible al driver, s'ha d'anar afegint o esborrant tuples d'una en una amb "
									+ "els mètodes corresponents\n");
							break;
						case 9:
							print(r.getResultats());
							break;
						case 10:
							print("Threshold es sempre 0 al driver\n");
							break;
						case 11:
							print(r.getThreshold());
							break;
						case 12:
							print(r.size());
							break;
						case 13:
							print(r.isEmpty());
							break;
						case 14:
							print("Entra index: ");
							int i = nextInt();
							print(r.get(i));
							break;
						case 15:
							print("Entra index: ");
							int in = nextInt();
							print("Entra parella: \n");
							Pair<Double,Node> pa = crearTupla();
							r.set(in,pa);
							break;
						case 16:
							r.clear();
							break;
						case 17:
							Pair<Double,Node> pai = crearTupla();
							r.afegir(pai);
							break;
						case 18:
							print("Entra index: ");
							int j = nextInt();
							print(r.esborrar(j));
							break;
						case 19:
							print("Entra index: ");
							int k = nextInt();
							print("Rellevancia: ");
							Double rell = nextDouble();
							r.setRellevancia(k,rell);
							break;
						case 20:
							print("Entra index: ");
							int e = nextInt();
							Node nnnn = crearNode();
							r.setDada(e,nnnn);
							break;
						case 21:
							print("Entra n: ");
							int nnn = nextInt();
							r.filtrarElsPrimers(nnn);
							break;
						case 22:
							print("Entra n: ");
							int nn = nextInt();
							r.filtrarElsUltims(nn);
							break;
						case 23:
							print("Entra label: ");
							String label = nextLine();
							r.filtrarPerEtiqueta(label);
							break;
						case 24:
							print("Entra min: ");
							double min = nextDouble();
							print("Entra max: ");
							double max = nextDouble();
							r.filtrarPerRellevancia(min,max);
							break;
						case 25:
							print(r.toString());
							break;
						default:
							println("Introdueix una opcio del 1 al 8.");
					}
				} catch (Exception e) {
					println("Hi ha hagut un error: " + e + "\n" +
							Arrays.toString(e.getStackTrace()));
				}
				
				println();
			} while (opcio != 26);
			
			close();
		}

		private static Resultat crearResultat() {
			Node n = crearNode();
			print("\n");
			print("Nom del path: ");
			String nomp = nextLine();
			print("\n");
			print("Nom del graf: ");
			String nom = nextLine();
			print("\n");
			ArrayList<Pair<Double, Node>> lis = new ArrayList<Pair<Double, Node>>();
			return new Resultat(n,nomp,new ControladorPaths(new ControladorGraf()),nom,lis);
		}
		
		private static Node crearNode() {
			print("Nom Dada: ");
			String nom = nextLine();
			print("Etiqueta (si no es vol -> click return): ");
			String label = nextLine();
				return new Autor(0,nom,label);
		}
		
		
		private static Pair<Double, Node> crearTupla() {
			print("Rellevancia: ");
			double rel = nextDouble();
			Node n = crearNode();
				return new Pair<Double, Node>(rel,n);
		}
		
		
		
		
	}

