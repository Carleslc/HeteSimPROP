package drivers;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe per testos amb utilitats per escriure i demanar dades.
 * @author Carlos Lazaro
 */
public abstract class Driver {

	protected static Scanner in = new Scanner(System.in);

	/**
	 * Demana un enter i mostra un missatge d'error si no s'introdueix un enter.
	 * @return el enter llegit o 0 si no s'ha introduit un enter.
	 */
	public static int nextInt() {
		int i = 0;
		try {
			i = in.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("\nError: S'ha demanat un enter.");
		} finally {
			in.nextLine();
		}
		return i;
	}
	
	/**
	 * Demana un double i mostra un missatge d'error si no s'introdueix un double.
	 * @return el double llegit o 0.0 si no s'ha introduit un double.
	 */
	public static double nextDouble() {
		double d = 0d;
		try {
			d = in.nextDouble();
		} catch (InputMismatchException e) {
			System.out.println("\nError: S'ha demanat un double.");
		} finally {
			in.nextLine();
		}
		return d;
	}
	
	/**
	 * Demana un boolean i mostra un missatge d'error si no s'introdueix un boolean.
	 * @return el boolean llegit o false si no s'ha introduit un boolean.
	 */
	public static boolean nextBoolean() {
		boolean b = false;
		try {
			b = in.nextBoolean();
		} catch (InputMismatchException e) {
			System.out.println("\nError: S'ha demanat un boolean.");
		} finally {
			in.nextLine();
		}
		return b;
	}
	
	/**
	 * Espera i llegeix fins una separacio (salt de linia, espai, tabulador).
	 * @return la cadena de caracters llegida.
	 */
	public static String nextWord() {
		return in.next();
	}
	
	/**
	 * Espera i llegeix fins un salt de linia.
	 * @return la cadena de caracters llegida.
	 */
	public static String nextLine() {
		return in.nextLine();
	}
	
	/**
	 * Retorna el Scanner utilitzat per fer les lectures.
	 * @return el Scanner utilitzat per fer les lectures.
	 */
	public static Scanner getScanner() {
		return in;
	}
	
	/**
	 * Mostra l'objecte del parametre.
	 * @param o l'objecte a mostrar.
	 */
	public static void print(Object o) {
		System.out.println(o);
	}
	
	/**
	 * Escriu un salt de linia.
	 */
	public static void println() {
		System.out.println();
	}
	
	/**
	 * Mostra l'objecte del parametre i escriu un salt de linia.
	 * @param o l'objecte a mostrar.
	 */
	public static void println(Object o) {
		print(o);
		println();
	}
	
	/**
	 * Mostra el String del parametre.
	 * @param s el String a mostrar.
	 */
	public static void print(String s) {
		System.out.print(s);
	}
	
	/**
	 * Mostra el String del parametre i escriu un salt de linia.
	 * @param s el String a mostrar.
	 */
	public static void println(String s) {
		print(s);
		println();
	}
	
	/**
	 * Mostra la coleccio del parametre.
	 * @param collection la collecio a mostrar.
	 */
	public static void print(Collection<?> collection) {
		collection.forEach(System.out::println);
	}
	
	/**
	 * Mostra la coleccio del parametre i escriu un salt de linia.
	 * @param collection la collecio a mostrar.
	 */
	public static void println(Collection<?> collection) {
		print(collection);
		println();
	}
	
	/**
	 * Mostra el mapa del parametre.
	 * @param map el mapa a mostrar.
	 */
	public static void print(Map<?, ?> map) {
		map.entrySet().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
	}
	
	/**
	 * Mostra el mapa del parametre i escriu un salt de linia.
	 * @param map el mapa a mostrar.
	 */
	public static void println(Map<?, ?> map) {
		print(map);
		println();
	}
	
	/**
	 * Mostra un error.
	 * @param t el error/excepcio a mostrar.
	 */
	public static void print(Throwable t) {
		println(t);
		println("StackTrace:");
		for (StackTraceElement ste : t.getStackTrace())
			print(ste);
	}
	
	/**
	 * Tanca el dispositiu lector.<p>
	 * S'ha de tancar quan ja no s'utilitzara aquesta classe.
	 */
	public static void close() {
		in.close();
	}
	
}
