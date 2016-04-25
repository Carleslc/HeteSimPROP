package persistencia;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import domini.Graf;
import domini.Matriu;
import domini.Path;

/**
 * Permet intercanviar dades amb la memoria secundaria.
 * @author Carlos Lazaro Costa
 */
public abstract class ControladorPersistencia {
	
	/**
	 * Llegeix el fitxer corresponent al path donat.
	 * @param filesystem_path el path del fitxer
	 * @return totes les línies del fitxer
	 * @throws FileNotFoundException si no es troba el fitxer
	 * @throws IOException si no es pot llegir el fitxer
	 */
	public static ArrayList<String> llegirFitxer(String filesystem_path) throws IOException {
		FileReader fr = new FileReader(filesystem_path);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<String> lines = new ArrayList<>();
		
		String line = br.readLine();
		while (line != null) {
			lines.add(line);
			line = br.readLine();
		}
		
		br.close();
		fr.close();
		
		return lines;
	}
	
	/**
	 * Guarda el graf serialitzable a un fitxer corresponent al path donat,
	 * sobreescrivint-lo si ja existeix.
	 * @param filesystem_path el path del fitxer
	 * @param graf el graf serialitzable
     * @throws FileNotFoundException si no es troba el fitxer o no es pot crear
     * @throws IOException si no es pot escriure en el fitxer
	 */
	public static void guardarGraf(String filesystem_path, Graf graf) throws IOException {
		FileOutputStream file = new FileOutputStream(filesystem_path, false);
		ObjectOutputStream out = new ObjectOutputStream(file);
		
		out.writeObject(graf);
		
		out.close();
		file.close();
	}
	
	/**
	 * Llegeix un graf serialitzat del fitxer corresponent al path donat.
	 * @param filesystem_path el path del fitxer
	 * @return el graf llegit
	 * @throws FileNotFoundException si no es troba el fitxer
	 * @throws IOException si no es pot llegir el fitxer, no hi ha un objecte serializat
	 * amb el format correcte o no es troba la classe serialitzada.
	 */
	public static Graf carregarGraf(String filesystem_path) throws IOException {
		FileInputStream file = new FileInputStream(filesystem_path);
		ObjectInputStream in = new ObjectInputStream(file);
		
		Graf graf;
		try {
			graf = (Graf) in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(e);
		} finally {
			in.close();
			file.close();
		}
		
		return graf;
	}
	
	/**
	 * Guarda una coleccio de paths a un fitxer corresponent al path donat,
	 * sobreescrivint-lo si ja existeix.
	 * @param filesystem_path el path del fitxer
	 * @param paths la coleccio de paths
     * @throws FileNotFoundException si no es troba el fitxer o no es pot crear
     * @throws IOException si no es pot escriure en el fitxer
	 */
	public static void guardarPaths(String filesystem_path, Collection<Path> paths) throws IOException {
		FileOutputStream file = new FileOutputStream(filesystem_path, false);
		ObjectOutputStream out = new ObjectOutputStream(file);

		for (Path p : paths)
			out.writeObject(p);
		
		out.close();
		file.close();
	}
	
	/**
	 * Llegeix una coleccio de paths serialitzats del fitxer corresponent al path donat.
	 * @param filesystem_path el path del fitxer
	 * @return la coleccio de paths llegit
	 * @throws FileNotFoundException si no es troba el fitxer
	 * @throws IOException si no es pot llegir el fitxer, no hi ha un objecte serializat
	 * amb el format correcte o no es troba la classe serialitzada.
	 */
	public static Collection<Path> carregarPaths(String filesystem_path) throws IOException {
		FileInputStream file = new FileInputStream(filesystem_path);
		ObjectInputStream in = new ObjectInputStream(file);
		
		List<Path> paths = new LinkedList<>();
	    try {
	        while (true) paths.add((Path) in.readObject());
	    } catch (EOFException ignored) {
	        // quan s'acabin de llegir tots els paths arribarí aquí
	    } catch (ClassNotFoundException e) {
	    	throw new IOException(e);
	    } finally {
	    	in.close();
	    	file.close();
	    }
		
		return paths;
	}
	
	/**
	 * Guarda un mapa de clausures <id, clausura> a un fitxer corresponent al path donat,
	 * sobreescrivint-lo si ja existeix.
	 * @param filesystem_path el path del fitxer
	 * @param clausures les clausures a guardar
     * @throws FileNotFoundException si no es troba el fitxer o no es pot crear
     * @throws IOException si no es pot escriure en el fitxer
	 */
	public static void guardarClausures(String filesystem_path, Map<String, Matriu<Double>> clausures) throws IOException {
		FileOutputStream file = new FileOutputStream(filesystem_path, false);
		ObjectOutputStream out = new ObjectOutputStream(file);
		
		for (Entry<String, Matriu<Double>> e : clausures.entrySet()) {
			out.writeObject(e.getKey()); // id
			out.writeObject(e.getValue()); // clausura
		}
		
		out.close();
		file.close();
	}
	
	/**
	 * Llegeix les clausures serialitzades del fitxer corresponent al path donat.
	 * @param filesystem_path el path del fitxer
	 * @return el mapa de clausures <id, clausura>
	 * @throws FileNotFoundException si no es troba el fitxer
	 * @throws IOException si no es pot llegir el fitxer, no hi ha un objecte serializat
	 * amb el format correcte o no es troba la classe serialitzada.
	 */
	public static Map<String, Matriu<Double>> carregarClausures(String filesystem_path) throws IOException {
		FileInputStream file = new FileInputStream(filesystem_path);
		ObjectInputStream in = new ObjectInputStream(file);
		
		Map<String, Matriu<Double>> clausures = new HashMap<>();
	    try {
	        while (true) {
	        	// Llegeix l'identificador
	        	String id = (String) in.readObject();
	        	// Llegeix la matriu i fa la conversio a Matriu<Double> (per evitar el warning unchecked types)
	        	Matriu<?> fileMatrix = (Matriu<?>) in.readObject();
	        	
	        	int files = fileMatrix.getFiles();
	        	int columnes = fileMatrix.getColumnes();
	        	Matriu<Double> clausura = new Matriu<Double>(files, columnes, 0D);
	        	for (int i = 0; i < fileMatrix.getFiles(); ++i)
	        		for (int j = 0; j < fileMatrix.getColumnes(); ++j)
	        			clausura.set(i, j, (Double)fileMatrix.get(i, j));
	        	
	        	clausures.put(id, clausura);
	        }
	    } catch (EOFException ignored) {
	        // quan s'acabin de llegir totes les clausures arribara aqui
	    } catch (ClassNotFoundException e) {
	    	throw new IOException(e);
	    } finally {
	    	in.close();
	    	file.close();
	    }
		
		return clausures;
	}
	
	// MINI-TEST
	public static void main(String[] args) {
		String path = "test.dat";
		try {
			guardarGraf(path, new Graf());
			Graf g = carregarGraf(path);
			System.out.println(g + "\n");
			
			ArrayList<Path> paths = new ArrayList<>();
			paths.add(new Path("APA"));
			paths.add(new Path("PCPAPT"));
			guardarPaths(path, paths);
			llegirFitxer(path).forEach(System.out::println); // Llegira basura
			System.out.println();
			paths = new ArrayList<>(carregarPaths(path));
			paths.forEach(System.out::println);
			System.out.println();
			
			Matriu<Double> clausura = new Matriu<Double>(4, 6, .75);
			Map<String, Matriu<Double>> mapaClausures = new LinkedHashMap<>();
			mapaClausures.put("TEST_CLAUSURA", clausura);
			guardarClausures(path, mapaClausures);
			mapaClausures = carregarClausures(path);
			mapaClausures.entrySet().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			File f = new File(path);
			if (f.exists())
				f.delete();
		}
	}
}
