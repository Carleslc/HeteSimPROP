package drivers;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Driver {

	protected static Scanner in = new Scanner(System.in);

	public static int nextInt() {
		int i = 0;
		try {
			i = in.nextInt();
			in.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Error: S'ha demanat un enter.");
		}
		return i;
	}
	
	public static double nextDouble() {
		double d = 0;
		try {
			d = in.nextInt();
			in.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Error: S'ha demanat un double.");
		}
		return d;
	}
	
	public static boolean nextBoolean() {
		boolean b = false;
		try {
			b = in.nextBoolean();
			in.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Error: S'ha demanat un boolean.");
		}
		return b;
	}
	
	public static String nextWord() {
		return in.next();
	}
	
	public static String nextLine() {
		return in.nextLine();
	}
	
	public static Scanner getScanner() {
		return in;
	}
	
	public static void print(Object o) {
		System.out.println(o);
	}
	
	public static void print(String s) {
		System.out.print(s);
	}
	
	public static void println(String s) {
		print(s);
		System.out.println();
	}
	
	public static void print(Collection<?> collection) {
		collection.forEach(System.out::println);
	}
	
	public static void print(Map<?, ?> map) {
		map.entrySet().forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
	}
	
	public static void close() {
		in.close();
	}
	
}
