package domini;

import domini.Matriu;
import domini.Node;
import persistencia.ControladorPersistencia;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Controlador de l'algorisme HeteSim.
 * 
 * <p><b>TODO</b> AFEGIR DESCRIPCIO DE L'ALGORISME.
 * 
 * @author Guillem Castro Olivares,<br>
 * 		   Carlos Lazaro Costa
 */
public class HeteSim implements Serializable {
	
	private static final long serialVersionUID = -4776834543552403509L;
	
	protected Graf graf;
	private HashMap<String, Matriu<Double>> clausures;
	
	/**
	 * Crea una instancia de Hetesim per un Graf
	 * @param Graf Graf sobre el que es faran els calculs
	 * @author Guillem Castro
	 */
	public HeteSim(Graf graf) {
		this.graf = graf;
		clausures = new HashMap<>();
	}
	
	/**
	 * Consulta el Graf sobre el que es fan els calculs
	 * @return el Graf que s'havia passat com a parametre al constructor
	 * @author Guillem Castro
	 */
	public Graf getGraf() {
		return this.graf;
	}
	
	/**
	 * Calcula la clausura per un path
	 * @param path el path de la clausura que volem calcular
	 * @return la clausura del path indicat
	 * @throws IllegalArgumentException si <b>path</b> es <code>null</code> o es buit
	 * @author Guillem Castro
	 */
	public Matriu<Double> clausura(String path) throws IllegalArgumentException {
		if (path == null || path.isEmpty()) {
			throw new IllegalArgumentException("'path' no pot ser buit");
		}
		if (clausures.containsKey(path)) {
			return (Matriu<Double>) clausures.get(path);
		}
		
		ArrayList<Matriu<Double>> mPath = matriusPath(path);
		int longitud = mPath.size();
		Matriu<Double> left = mPath.get(0);
		Matriu<Double> right = mPath.get(longitud/2);
		for (int i = 1; i < longitud/2; ++i) {
			left = left.multiplicar(mPath.get(i));
		}
		for (int i = longitud/2 + 1; i < longitud; ++i) {
			right = right.multiplicar(mPath.get(i));
		}
		left = clausura(left, right, path);
		
		clausures.put(path, left);
		
		return left;
	}
	
	/**
	 * Calcula la clausura a partir de les matrius normalitzades esquerra y dreta del algorisme
	 * @param left Matriu de la part esquerra del cami
	 * @param right Matriu de la part dreta del cami
	 * @param path el path a utilitzar en el calcul
	 * @return la clausura associada al path representada per left*right
	 * @author Guillem Castro
	 */
	public Matriu<Double> clausura(Matriu<Double> left, Matriu<Double> right, String path) {
		Matriu<Double> aux = left.multiplicar(right);
		if (path.length() != 2) {
			for (int i = 0; i < aux.getFiles(); ++i) {
				for (int j = 0; j < aux.getColumnes(); ++j)
					aux.set(i, j, (aux.get(i, j)/(left.getNormaFila(i)*right.getNormaColumna(j)) ));
			}
		}
		return aux;
	}
	
	/**
	 * Efectua el calcul del HeteSim entre dos nodes
	 * @param a primer node del path
	 * @param b ultim node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @return la rellevancia de <b>a</b> amb <b>b</b>
	 * @throws IllegalArgumentException si algun parametre es null o algun node no es correspon al path
	 * @author Guillem Castro, Carlos Lazaro
	 */
	public double heteSim(Node a, Node b, String path) throws IllegalArgumentException {
		if (path == null || a == null || b == null) {
			throw new IllegalArgumentException("Els parametres no poden ser null");
		}
		char classA = a.getClass().getSimpleName().charAt(0);
		char classB = b.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classA || path.charAt(path.length()-1) != classB) {
			throw new IllegalArgumentException("Els tipus dels nodes no coincideixen amb els del 'path'");
		}
		
		if (clausures.containsKey(path)) {
			Matriu<Double> clausura = clausures.get(path);
			return clausura.get(a.getId(), b.getId());
		}
		
		ArrayList<Matriu<Double>> matrius = matriusPath(path);
		ArrayList<Double> fila = matrius.get(0).getFila(a.getId());
		for (int i = 1; i < matrius.size()/2; ++i) {
			Matriu<Double> next = matrius.get(i);
			ArrayList<Double> res = new ArrayList<>(next.getColumnes());
			for (int j = 0; j < next.getColumnes(); ++j)
				res.add(0d);
			for (int j = 0; j < res.size(); ++j) {
				for (int k = 0; k < fila.size(); ++k)
					res.set(j, res.get(j) + fila.get(k)*next.get(k, j));
			}
			fila = res;
		}
		
		ArrayList<Double> columna = matrius.get(matrius.size() - 1).getColumna(b.getId());
		for (int i = matrius.size() - 2; i >= matrius.size()/2; --i) {
			Matriu<Double> next = matrius.get(i);
			ArrayList<Double> res = new ArrayList<>(next.getFiles());
			for (int j = 0; j < next.getFiles(); ++j)
				res.add(0d);
			for (int j = 0; j < next.getFiles(); ++j) {
				for (int k = 0; k < next.getColumnes(); ++k) {
					res.set(j, res.get(j) + columna.get(k)*next.get(j, k));
				}
			}
			columna = res;
		}
		
		double res = 0d;
		for (int i = 0; i < fila.size(); ++i)
			res += fila.get(i)*columna.get(i);
		
		double sumaFila = 0d;
		for (double d : fila)
			sumaFila += d*d;
		
		double sumaColumna = 0d;
		for (double d : columna)
			sumaColumna += d*d;
		
		double norma = Math.sqrt(sumaFila)*Math.sqrt(sumaColumna);
		
		return res/norma;
	}
	
	/**
	 * Efectua el calcul del HeteSim entre un node i els altres
	 * @param n primer node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @return llista ordenada per rellevancies on cada element es la rellevancia
	 * <br>que te n amb un altre node del tipus de l'ultim del path,
	 * <br>representat pel seu identificador.
	 * @throws IllegalArgumentException si algun parametre es null o el node no es correspon al path
	 * @author Guillem Castro, Carlos Lazaro
	 */
	public ArrayList<Entry<Double, Integer>> heteSimAmbIdentificadors(Node n, String path) throws IllegalArgumentException {
		if (path == null || n == null) {
			throw new IllegalArgumentException("Els parametres no poden ser null");
		}
		char classNode = n.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classNode) {
			throw new IllegalArgumentException("El tipus del node no coincideix amb el primer del 'path'");
		}
		
		if (clausures.containsKey(path)) {
			Matriu<Double> clausura = clausures.get(path);
			ArrayList<Pair<Double, Integer>> aux = new ArrayList<>();
			ArrayList<Double> res = clausura.getFila(n.getId());
			for (int i = 0; i < res.size(); ++i)
				aux.add(new Pair<>(res.get(i), i));
			return new ArrayList<>(aux);
		}
		
		ArrayList<Matriu<Double>> matrius = matriusPath(path);
		ArrayList<Double> fila = matrius.get(0).getFila(n.getId());
		for (int i = 1; i < matrius.size()/2; ++i) {
			Matriu<Double> next = matrius.get(i);
			ArrayList<Double> res = new ArrayList<>(next.getColumnes());
			for (int j = 0; j < next.getColumnes(); ++j)
				res.add(0D);
			for (int j = 0; j < res.size(); ++j) {
				for (int k = 0; k < fila.size(); ++k)
					res.set(j, res.get(j) + fila.get(k)*next.get(k, j));
			}
			fila = res;
		}
		
		int longitud = matrius.size();
		Matriu<Double> right = matrius.get(longitud/2);
		
		for (int i = longitud/2 + 1; i < longitud; ++i) {
			right = right.multiplicar(matrius.get(i));
		}
		
		ArrayList<Double> res = new ArrayList<>(right.getColumnes());
		for (int j = 0; j < right.getColumnes(); ++j)
			res.add(0D);
		for (int j = 0; j < res.size(); ++j) {
			for (int k = 0; k < fila.size(); ++k)
				res.set(j, res.get(j) + fila.get(k)*right.get(k, j));
		}
		
		double sumaFila = 0d;
		for (double d : fila)
			sumaFila += d*d;
		
		double normaFila = Math.sqrt(sumaFila);
		
		for (int i = 0; i < res.size(); ++i)
			res.set(i, res.get(i)/(normaFila*right.getNormaColumna(i)));
		
		ArrayList<Pair<Double, Integer>> aux = new ArrayList<>();
		
		for (int i = 0; i < res.size(); ++i)
			aux.add(new Pair<>(res.get(i), i));
		
		return new ArrayList<>(aux);
	}
	
	/**
	 * Efectua el calcul del HeteSim entre un node i els altres
	 * @param n primer node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @return llista ordenada per rellevancies on cada element es la rellevancia
	 * <br>que te n amb un altre node del tipus de l'ultim del path,
	 * <br>representat pel seu nom.
	 * @throws IllegalArgumentException si algun parametre es null o el node no es correspon al path
	 * @author Carlos Lazaro
	 */
	public ArrayList<Entry<Double, String>> heteSimAmbNoms(Node n, String path) throws IllegalArgumentException {
		if (path == null || n == null) {
			throw new IllegalArgumentException("Els parametres no poden ser null");
		}
		char classNode = n.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classNode) {
			throw new IllegalArgumentException("El tipus del node no coincideix amb el primer del 'path'");
		}
		
		if (clausures.containsKey(path)) {
			Matriu<Double> clausura = clausures.get(path);
			ArrayList<Pair<Double, String>> aux = new ArrayList<>();
			ArrayList<Double> res = clausura.getFila(n.getId());
			for (int i = 0; i < res.size(); ++i)
				aux.add(new Pair<>(res.get(i), consultarNomById(i, path)));
			return new ArrayList<Entry<Double, String>>(aux);
		}
		
		ArrayList<Matriu<Double>> matrius = matriusPath(path);
		ArrayList<Double> fila = matrius.get(0).getFila(n.getId());
		for (int i = 1; i < matrius.size()/2; ++i) {
			Matriu<Double> next = matrius.get(i);
			ArrayList<Double> res = new ArrayList<>(next.getColumnes());
			for (int j = 0; j < next.getColumnes(); ++j)
				res.add(0D);
			for (int j = 0; j < res.size(); ++j) {
				for (int k = 0; k < fila.size(); ++k)
					res.set(j, res.get(j) + fila.get(k)*next.get(k, j));
			}
			fila = res;
		}
		
		int longitud = matrius.size();
		Matriu<Double> right = matrius.get(longitud/2);
		
		for (int i = longitud/2 + 1; i < longitud; ++i) {
			right = right.multiplicar(matrius.get(i));
		}
		
		ArrayList<Double> res = new ArrayList<>(right.getColumnes());
		for (int j = 0; j < right.getColumnes(); ++j)
			res.add(0D);
		for (int j = 0; j < res.size(); ++j) {
			for (int k = 0; k < fila.size(); ++k)
				res.set(j, res.get(j) + fila.get(k)*right.get(k, j));
		}
		
		double sumaFila = 0d;
		for (double d : fila)
			sumaFila += d*d;
		
		double normaFila = Math.sqrt(sumaFila);
		
		for (int i = 0; i < res.size(); ++i)
			res.set(i, res.get(i)/(normaFila*right.getNormaColumna(i)));
		
		ArrayList<Pair<Double, String>> aux = new ArrayList<>();
		
		for (int i = 0; i < res.size(); ++i)
			aux.add(new Pair<>(res.get(i), consultarNomById(i, path)));
		
		return new ArrayList<>(aux);
	}
	
	/**
	 * Consulta el nom d'un node a partir del seu id i path.
	 * @param id el identificador del node a consultar
	 * @param path el path de la consulta
	 * @return el nom del node o null si el path es buit
	 * @author Carlos Lazaro
	 */
	private String consultarNomById(int id, String path) {
		if (!path.isEmpty()) {
			switch (path.charAt(path.length()-1)) {
				case 'P':
					return graf.consultarPaper(id).getNom();
				case 'A':
					return graf.consultarAutor(id).getNom();
				case 'C':
					return graf.consultarConferencia(id).getNom();
				case 'T':
					return graf.consultarTerme(id).getNom();
			}
		}
		return null;
	}

	/**
	 * Separa el path i retorna una llista amb totes les matrius necessaries per calcular
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @return una llista amb les matrius corresponents a <b>path</b>
	 * @author Guillem Castro
	 */
	private ArrayList<Matriu<Double>> matriusPath(String path) {
		int longitud = path.length() - 1;
		ArrayList<Matriu<Double>> mPath;
		mPath = new ArrayList<>(longitud);
		for (int i = 0; i < longitud; ++i) {
			char charAti = path.charAt(i);
			if (charAti == 'A') {
				Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperAutor().transposada());
				mPath.add(i,m);
			}
			else if (charAti == 'C') {
				Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperConferencia().transposada());
				mPath.add(i,m);
			}
			else if (charAti == 'T') {
				Matriu<Double> m = creaMatriuDouble(graf.consultarMatriuPaperTerme().transposada());
				mPath.add(i,m);
			}
			else if (charAti == 'P') {
				char charAti1 = path.charAt(i+1);
				Matriu<Double> m = null;
				if (charAti1 == 'A') {
					m = creaMatriuDouble(graf.consultarMatriuPaperAutor());
				}
				if (charAti1 == 'C') {
					m = creaMatriuDouble(graf.consultarMatriuPaperConferencia());
				}
				if (charAti1 == 'T') {
					m = creaMatriuDouble(graf.consultarMatriuPaperTerme());
				}
				mPath.add(i,m);
			}
		}
		if (longitud%2 != 0) {
			ArrayList<Matriu<Double>> E = mPath.get(longitud/2).intermedia(mPath.get(longitud/2).transposada());
			mPath.set(longitud/2, E.get(0));
			mPath.add(longitud/2 + 1, E.get(1));
			++longitud;
		}
		int i;
		for (i = 0; i < (longitud/2); ++i) {
			mPath.get(i).normalitzaPerFiles();
		}
		for (int j = i; j < longitud; ++j) {
			mPath.get(i).normalitzaPerColumnes();
		}
		return mPath;
	}
	
	/**
	 * Transforma una Matriu[Byte] en Matriu[Double]
	 * @param m Matriu[Byte] a transformar
	 * @return una Matriu[Double] amb els mateixos valors de <b>m</b>
	 * @author Guillem Castro
	 */
	private Matriu<Double> creaMatriuDouble(Matriu<Byte> m) {
		int files = m.getFiles();
		int cols = m.getColumnes();
		Matriu<Double> res = new Matriu<Double>(files, cols, 0d);
		for (int i = 0; i < files; ++i) {
			for (int j = 0; j < cols; ++j) {
				res.set(i, j, (double) m.get(i, j));
			}
		}
		return res;
	}

	/**
	 * Guarda les clausures en un fitxer del sistema
	 * @param filesystem_path path del fitxer on guardar les clausures
	 * @throws IOException si el fitxer es un directori, el fitxer no es pot escriure
	 * <br>o hi ha altres problemes d'entrada/sortida
	 * @author Carlos Lazaro
	 */
	public void guardarClausures(String filesystem_path) throws IOException {
		ControladorPersistencia.guardarClausures(filesystem_path, clausures);
	}

	/**
	 * Carrega les clausures des d'un fitxer del sistema
	 * @param filesystem_path path del fitxer on guardar les clausures
	 * @throws IOException si el fitxer no existeix, el fitxer es un directori,
	 * el fitxer no es pot llegir o hi ha altres problemes d'entrada/sortida
	 * @author Carlos Lazaro
	 */
	public void carregarClausures(String filesystem_path) throws IOException {
		clausures = (HashMap<String, Matriu<Double>>) ControladorPersistencia.carregarClausures(filesystem_path);
	}
}