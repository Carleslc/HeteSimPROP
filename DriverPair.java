package drivers;

import java.util.Arrays;

public final class DriverPair extends Driver {

	public static void main(String[] args) {
		Pair<String, String> p = null;
		
		int opcio;
		do {
			print("Escolleix una opcio:\n"
					+ "1. Crear pair.\n"
					+ "2. getKey\n"
					+ "3. getValue\n"
					+ "4. setKey\n"
					+ "5. setValue\n"
					+ "6. compareTo\n"
					+ "7. toString\n"
					+ "8. Sortir del driver\n"
					+ "Opcio = ");
			
			opcio = nextInt();
			
			try {
				switch (opcio) {
					case 1:
						print("Key: ");
						String key = nextLine();
						print("Value: ");
						String value = nextLine();
						p = new Pair<>(key, value);
						break;
					case 2:
						print(p.getKey());
						break;
					case 3:
						print(p.getValue());
						break;
					case 4:
						
						break;
					case 5:
						
						break;
					case 6:
						
						break;
					case 7:
						
						break;
					case 8:
						break;
					default:
						System.err.println("Introdueix una opcio del 1 al 8.");
				}
			} catch (Exception e) {
				println("Hi ha hagut un error: " + e + "\n" +
						Arrays.toString(e.getStackTrace()));
			}
			
			System.out.println();
		} while (opcio != 8);
		
		close();
	}
	
	
}
