package drivers;

import java.util.Arrays;

import domini.Pair;

public final class DriverPair extends Driver {

	public static void main(String[] args) {
		Pair<String, String> p = crearPair();
		println();
		
		int opcio;
		do {
			print("Escolleix una opcio:\n"
					+ "1. Crear pair\n"
					+ "2. getKey\n"
					+ "3. getValue\n"
					+ "4. setKey\n"
					+ "5. setValue\n"
					+ "6. compareTo\n"
					+ "7. toString\n"
					+ "8. Sortir del driver\n"
					+ "Opcio = ");
			
			opcio = nextInt();
			
			println();
			
			try {
				switch (opcio) {
					case 1: {
						p = crearPair();
						break;
					}
					case 2:
						println(p.getKey());
						break;
					case 3:
						println(p.getValue());
						break;
					case 4: {
						print("Key: ");
						String key = nextLine();
						p.setKey(key);
						break;
					}
					case 5: {
						print("Value: ");
						String value = nextLine();
						p.setKey(value);
						break;
					}
					case 6: {
						println("Pair: " + p);
						print("Pair2 Key: ");
						String key = nextLine();
						print("Pair2 Value: ");
						String value = nextLine();
						Pair<String, String> p2 = new Pair<>(key, value);
						int compare = p.compareTo(p2);
						if (compare < 0)
							println("Pair < Pair2");
						else if (compare > 0)
							println("Pair > Pair2");
						else
							println("Pair equals Pair2");
						break;
					}
					case 7:
						println(p.toString());
						break;
					case 8:
						break;
					default:
						println("Introdueix una opcio del 1 al 8.");
				}
			} catch (Exception e) {
				println("Hi ha hagut un error: " + e + "\n" +
						Arrays.toString(e.getStackTrace()));
			}
			
			println();
		} while (opcio != 8);
		
		close();
	}

	private static Pair<String, String> crearPair() {
		print("Key: ");
		String key = nextLine();
		print("Value: ");
		String value = nextLine();
		return new Pair<>(key, value);
	}
	
	
}
