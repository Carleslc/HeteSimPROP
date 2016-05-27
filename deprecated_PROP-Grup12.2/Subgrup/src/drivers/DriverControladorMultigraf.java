package drivers;

import domini.ControladorMultigraf;

public final class DriverControladorMultigraf extends Driver {

	public static void main(String[] args) {
		ControladorMultigraf m = new ControladorMultigraf();

		int opcio;
		do {
			print("Escull una opcio:\n"
					+ "1. getIdActual()\n"
					+ "2. getNomsGrafs()\n"
					+ "3. getHeteSim()\n"
					+ "4. afegirGraf(nomGraf)\n"
					+ "5. importar(nomGraf,directori)\n"
					+ "6. seleccionarGraf(String nomGraf)\n"
					+ "7. carregar(String directori)\n"
					+ "8. guardar(String directori)\n"
					+ "9. Sortir del driver\n"
					+ "Opcio = ");

			opcio = nextInt();

			println();

			try {
				switch (opcio) {
				case 1: {
					print(m.getIdActual());
					break;
				}
				case 2: {
					print(m.getNomsGrafs());
					break;
				}
				case 3:
					print("Retorna el hetesim actual");
					print(m.getHeteSim());
					break;
				case 4:
					print("NomGraf: ");
					String nomGraf = nextLine();
					m.afegirGraf(nomGraf);
					break;

				case 5: 
					print("NomGraf: ");
					String nomGra = nextLine();
					print("directori: ");
					String directori = nextLine();
					m.importar(nomGra,directori);
					break;

				case 6: 
					print("NomGraf: ");
					String nomGr = nextLine();
					m.seleccionarGraf(nomGr);
					break;

				case 7: 
					print("directori: ");
					String dir = nextLine();
					m.carregar(dir);
					break;
				case 8:
					print("directori: ");
					String dire = nextLine();
					m.guardar(dire);
					break;

				default:
					println("Introdueix una opcio del 1 al 9.");
				}
			} catch (Exception e) {
				println("\nHi ha hagut un error:");
				print(e);
			}

			println();
		} while (opcio != 9);

		close();
	}




}

