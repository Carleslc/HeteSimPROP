package drivers;

import domini.Pair;

public class DriverPair extends Driver {

	public static void main(String[] args) {
		
		print("Introdueix la clau del pair (un double): ");
		double key = Double.parseDouble(nextLine());
		print ("Introdueix el valor del pair (una string): ");
		String value = nextLine();
		Pair<Double, String> p = new Pair<>(key, value);
		
		int num;
		do {
			println("Per obtenir la clau del pair prem 1.");
			println("Per obtenir el valor del pair prem 2.");
			println("Per canviar la clau del pair prem 3.");
			println("Per canviar el valor del pair prem 4.");
			println("Per comparar el pair amb un altre pair prem 5.");
			println("Per passar el pair a string prem 6.");
			println("Per sortir prem 0.");
			
			num = nextInt();
			
			switch (num) {
			
				case 0: 
					break;
			
				case 1:
					println("La clau del pair és: " + p.getKey());
					break;
			
				case 2:
					println("El valor del pair és: " + p.getValue());
					break;
			
				case 3: {
					print("Introdueix la nova clau del pair(double): ");
					p.setKey(Double.parseDouble(nextLine()));
					println("La clau s'ha modificat. La nova clau és " + p.getKey());
					break;
				}
			
				case 4: {
					print("Introdueix el nou valor del pair(string): ");
					String old = p.setValue(nextLine());
					println("El valor s'ha modificat. L'antic valor és " + old + " i el nou valor és " + p.getValue());
					break;
				}
			
				case 5: {
					print("Introdueix la clau del pair amb el que vols comparar (double): ");
					key = Double.parseDouble(nextLine());
					print("Introdueix el valor del pair amb el que vols comparar (String): ");
					value = nextLine();
					Pair<Double, String> p2 = new Pair<>(key, value);
					println("Pair1: key = " + p.getKey() + ", value = " + p.getValue());
					println("Pair2: key = " + p2.getKey() + ", value = " + p2.getValue());
					int comp = p.compareTo(p2);
					if (comp < 0) println("El Pair2 és més gran que el Pair1.");
					else if (comp > 0) println("El Pair2 és més petit que el Pair1.");
					else println("El Pair2 és igual que el Pair1.");
					break;
				}
			
				case 6:
					println("El pair és: " + p.toString());
					break;
				
				default:
					println ("Error! Has d'introduir un nombre del 0 al 6!");
			}
		} while (num != 0);
		
		close();
	}

}
