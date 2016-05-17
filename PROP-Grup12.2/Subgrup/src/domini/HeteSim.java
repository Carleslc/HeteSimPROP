package domini;

import domini.Matriu;
import domini.Node;
import persistencia.ControladorPersistencia;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Controlador de l'algorisme HeteSim.
 * 
 * @author Guillem Castro Olivares,<br>
 * 		   Carlos Lazaro Costa
 */
public class HeteSim implements Serializable {

	private static final long serialVersionUID = -4776834543552403509L;

	protected Graf graf;
	private HashMap<String, Matriu> clausures;
	private boolean updated;

	/**
	 * Crea una instancia de Hetesim per un Graf
	 * @param Graf Graf sobre el que es faran els calculs
	 * @author Guillem Castro
	 */
	public HeteSim(Graf graf) {
		this.graf = graf;
		setUpdated(true);
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
	 * @author Carlos Lazaro
	 */
	public Matriu clausura(String path) throws IllegalArgumentException {
		return clausura(path, false);
	}

	/**
	 * Efectua el calcul del HeteSim entre dos nodes
	 * @param a primer node del path
	 * @param b ultim node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @return la rellevancia de <b>a</b> amb <b>b</b>
	 * @throws IllegalArgumentException si algun parametre es null o algun node no es correspon al path
	 * @author Carlos Lazaro
	 */
	public double heteSim(Node a, Node b, String path) throws IllegalArgumentException {
		return heteSim(a, b, path, false);
	}

	/**
	 * Efectua el calcul del HeteSim entre un node i els altres
	 * @param n primer node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @return llista ordenada per rellevancies on cada element es la rellevancia
	 * <br>que te n amb un altre node del tipus de l'ultim del path,
	 * <br>representat pel seu identificador.
	 * @throws IllegalArgumentException si algun parametre es null o el node no es correspon al path
	 * @author Carlos Lazaro
	 */
	public ArrayList<Entry<Double, Integer>> heteSimAmbIdentificadors(Node n, String path) throws IllegalArgumentException {
		return heteSimAmbIdentificadors(n, path, false);
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
		return heteSimAmbNoms(n, path, false);
	}

	/**
	 * Calcula la clausura per un path
	 * @param path el path de la clausura que volem calcular
	 * @param ignorarClausura true si no es vol utilitzar cap clausura existent (es calculara com si no hi fos)
	 * @return la clausura del path indicat
	 * @throws IllegalArgumentException si <b>path</b> es <code>null</code> o es buit
	 * @author Guillem Castro, Carlos Lazaro
	 */
	public Matriu clausura(String path, boolean ignorarClausura) throws IllegalArgumentException {
		if (path == null || path.isEmpty())
			throw new IllegalArgumentException("'path' no pot ser buit");
		path = path.toUpperCase();

		if (!ignorarClausura && clausures.containsKey(path))
			return (Matriu) clausures.get(path);

		long start = System.nanoTime();
		
		boolean symmetric = isSymmetric(path);
		ArrayList<Matriu> mPath = matriusPath(path, symmetric);
		
		long elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo desarrollar path " + elapsed + "s");
		
		start = System.nanoTime();
		
		int longitud = mPath.size();
		Matriu left = mPath.get(0);
		
		for (int i = 1; i < longitud/2; ++i)
			left = left.multiplicar(mPath.get(i));
		
		elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo multiplicaciones LEFT " + elapsed + "s");
		
		start = System.nanoTime();
		
		Matriu right;
		if (symmetric)
			right = left.transposada();
		else {
			right = mPath.get(longitud/2);
			for (int i = longitud/2 + 1; i < longitud; ++i){
				right = right.multiplicar(mPath.get(i));
			}
		}
		elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo multiplicaciones RIGHT " + elapsed + "s");

		start = System.nanoTime();

		left = left.normalitzadaPerFiles();                
		right = right.normalitzadaPerColumnes();
		elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo normalizar " + elapsed + "s");

		start = System.nanoTime();
		left = clausura(left, right, path);
		elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo aplicar clausura " + elapsed + "s");

		clausures.put(path, left);

		return left;
	}

	/**
	 * Comprova si un path es simetric.
	 * @param path el path a comprovar
	 * @return si el path es simetric.
	 * @author Carlos Lazaro
	 */
	private boolean isSymmetric(String path) {
		int l = path.length() - 1;
		if (l%2 != 0) return false;

		for (int i = 0; i < l/2; ++i) 
			if (path.charAt(i) != path.charAt(l - i))
				return false;
		return true;
	}

	/**
	 * Calcula la clausura a partir de les matrius normalitzades esquerra y dreta del algorisme
	 * @param left Matriu de la part esquerra del cami
	 * @param right Matriu de la part dreta del cami
	 * @param path el path a utilitzar en el calcul
	 * @return la clausura associada al path representada per left*right
	 * @author Guillem Castro, Carlos Lazaro
	 */
	public Matriu clausura(Matriu left, Matriu right, String path) {
		long start = System.nanoTime();
		Matriu aux = left.multiplicar(right);
		long elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo clausura: left*right " + elapsed + "s");
		start = System.nanoTime();
		if (path.length() != 2) {
			for (int i = 0; i < aux.getFiles(); ++i) {
				double normaFila = left.getNormaFila(i);
				for (int j = 0; j < aux.getColumnes(); ++j) {
					double e = aux.get(i, j);
					if (e != 0d)
						aux.set(i, j, e/(normaFila*right.getNormaColumna(j)));
				}
			}
		}
		elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo clausura: normalizar " + elapsed + "s");
		return aux;
	}

	/**
	 * Efectua el calcul del HeteSim entre dos nodes
	 * @param a primer node del path
	 * @param b ultim node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @param ignorarClausura true si no es vol utilitzar cap clausura existent (es calculara com si no hi fos)
	 * @return la rellevancia de <b>a</b> amb <b>b</b>
	 * @throws IllegalArgumentException si algun parametre es null o algun node no es correspon al path
	 * @author Guillem Castro, Carlos Lazaro
	 */
	public double heteSim(Node a, Node b, String path, boolean ignorarClausura) throws IllegalArgumentException {
		if (path == null || a == null || b == null)
			throw new IllegalArgumentException("Els parametres no poden ser null");
		path = path.toUpperCase();
		char classA = a.getClass().getSimpleName().charAt(0);
		char classB = b.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classA || path.charAt(path.length()-1) != classB)
			throw new IllegalArgumentException("Els tipus dels nodes no coincideixen amb els del 'path'");

		if (!ignorarClausura && clausures.containsKey(path)) {
			Matriu clausura = clausures.get(path);
			return clausura.get(a.getId(), b.getId());
		}

		ArrayList<Matriu> matrius = matriusPath(path, false);
		ArrayList<Double> fila = matrius.get(0).getFila(a.getId());
		for (int i = 1; i < matrius.size()/2; ++i) {
			Matriu next = matrius.get(i);
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
			Matriu next = matrius.get(i);
			ArrayList<Double> res = new ArrayList<>(next.getFiles());
			for (int j = 0; j < next.getFiles(); ++j)
				res.add(0d);
			for (int j = 0; j < next.getFiles(); ++j) {
				for (int k = 0; k < next.getColumnes(); ++k)
					res.set(j, res.get(j) + columna.get(k)*next.get(j, k));
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

		Double d = res/(Math.sqrt(sumaFila)*Math.sqrt(sumaColumna));

		return d.isNaN() ? 0d : d;
	}

	/**
	 * Efectua el calcul del HeteSim entre un node i els altres
	 * @param n primer node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @param ignorarClausura true si no es vol utilitzar cap clausura existent (es calculara com si no hi fos)
	 * @return llista ordenada per rellevancies on cada element es la rellevancia
	 * <br>que te n amb un altre node del tipus de l'ultim del path,
	 * <br>representat pel seu identificador.
	 * @throws IllegalArgumentException si algun parametre es null o el node no es correspon al path
	 * @author Guillem Castro, Carlos Lazaro
	 */
	public ArrayList<Entry<Double, Integer>> heteSimAmbIdentificadors(Node n, String path, boolean ignorarClausura) throws IllegalArgumentException {
		if (path == null || n == null)
			throw new IllegalArgumentException("Els parametres no poden ser null");
		path = path.toUpperCase();
		char classNode = n.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classNode)
			throw new IllegalArgumentException("El tipus del node no coincideix amb el primer del 'path'");

		if (!ignorarClausura && clausures.containsKey(path)) {
			Matriu clausura = clausures.get(path);
			ArrayList<Pair<Double, Integer>> aux = new ArrayList<>();
			ArrayList<Double> res = clausura.getFila(n.getId());
			for (int i = 0; i < res.size(); ++i)
				aux.add(new Pair<>(res.get(i), i));
			Collections.sort(aux);
			return new ArrayList<>(aux);
		}

		ArrayList<Matriu> matrius = matriusPath(path, false);
		ArrayList<Double> fila = matrius.get(0).getFila(n.getId());
		for (int i = 1; i < matrius.size()/2; ++i) {
			Matriu next = matrius.get(i);
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
		Matriu right = matrius.get(longitud/2);

		for (int i = longitud/2 + 1; i < longitud; ++i)
			right = right.multiplicar(matrius.get(i));

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

		for (int i = 0; i < res.size(); ++i) {
			Double d = res.get(i)/(normaFila*right.getNormaColumna(i));
			res.set(i, d.isNaN() ? 0d : d);
		}

		ArrayList<Pair<Double, Integer>> aux = new ArrayList<>();

		for (int i = 0; i < res.size(); ++i)
			aux.add(new Pair<>(res.get(i), i));

		Collections.sort(aux);
		return new ArrayList<>(aux);
	}

	/**
	 * Efectua el calcul del HeteSim entre un node i els altres
	 * @param n primer node del path
	 * @param path path que desitjem utilitzar pel calcul del HeteSim
	 * @param ignorarClausura true si no es vol utilitzar cap clausura existent (es calculara com si no hi fos)
	 * @return llista ordenada per rellevancies on cada element es la rellevancia
	 * <br>que te n amb un altre node del tipus de l'ultim del path,
	 * <br>representat pel seu nom.
	 * @throws IllegalArgumentException si algun parametre es null o el node no es correspon al path
	 * @author Carlos Lazaro
	 */
	public ArrayList<Entry<Double, String>> heteSimAmbNoms(Node n, String path, boolean ignorarClausura) throws IllegalArgumentException {
		if (path == null || n == null)
			throw new IllegalArgumentException("Els parametres no poden ser null");
		path = path.toUpperCase();
		char classNode = n.getClass().getSimpleName().charAt(0);
		if (path.charAt(0) != classNode)
			throw new IllegalArgumentException("El tipus del node no coincideix amb el primer del 'path'");

		if (!ignorarClausura && clausures.containsKey(path)) {
			Matriu clausura = clausures.get(path);
			ArrayList<Pair<Double, String>> aux = new ArrayList<>();
			ArrayList<Double> res = clausura.getFila(n.getId());
			for (int i = 0; i < res.size(); ++i)
				aux.add(new Pair<>(res.get(i), consultarNomById(i, path)));
			Collections.sort(aux);
			return new ArrayList<>(aux);
		}

		ArrayList<Matriu> matrius = matriusPath(path, false);
		ArrayList<Double> fila = matrius.get(0).getFila(n.getId());
		for (int i = 1; i < matrius.size()/2; ++i) {
			Matriu next = matrius.get(i);
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
		Matriu right = matrius.get(longitud/2);

		for (int i = longitud/2 + 1; i < longitud; ++i)
			right = right.multiplicar(matrius.get(i));

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

		for (int i = 0; i < res.size(); ++i) {
			Double d = res.get(i)/(normaFila*right.getNormaColumna(i));
			res.set(i, d.isNaN() ? 0d : d);
		}

		ArrayList<Pair<Double, String>> aux = new ArrayList<>();

		for (int i = 0; i < res.size(); ++i)
			aux.add(new Pair<>(res.get(i), consultarNomById(i, path)));

		Collections.sort(aux);
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
		path = path.toUpperCase();
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
	 * @param symmetric true si es vol que la mitad dreta de la llista
	 * de matrius d'un path que se sap que es simetric sigui null
	 * @return una llista amb les matrius corresponents a <b>path</b>
	 * @author Guillem Castro, Carlos Lazaro
	 */
	private ArrayList<Matriu> matriusPath(String path, boolean symmetric) {
		int longitud = path.length() - 1;
		ArrayList<Matriu> mPath;
		mPath = new ArrayList<>(longitud);
		for (int i = 0; i < longitud; ++i) {
			if (symmetric && i >= longitud/2)
				mPath.add(null);
			else {
				char charAti = path.charAt(i);
				if (charAti == 'A') {
					Matriu m = graf.consultarMatriuPaperAutor().transposada();
					mPath.add(i,m);
				}
				else if (charAti == 'C') {
					Matriu m = graf.consultarMatriuPaperConferencia().transposada();
					mPath.add(i,m);
				}
				else if (charAti == 'T') {
					Matriu m = graf.consultarMatriuPaperTerme().transposada();
					mPath.add(i,m);

				}
				else if (charAti == 'P') {
					char charAti1 = path.charAt(i+1);
					Matriu m = null;
					if (charAti1 == 'A') {
						m = graf.consultarMatriuPaperAutor();
					}
					else if (charAti1 == 'C') {
						m = graf.consultarMatriuPaperConferencia();
					}
					else if (charAti1 == 'T') {
						m = graf.consultarMatriuPaperTerme();
					}
					mPath.add(i,m);
				}
			}
		}
		if (longitud%2 != 0) {
			long start = System.nanoTime();
			ArrayList<Matriu> E = mPath.get(longitud/2).intermedia();
			long elapsed = (System.nanoTime() - start) / 1000000000;
			System.out.println("Tiempo intermedia: " + elapsed + "s");
			mPath.set(longitud/2, E.get(0));
			mPath.add(longitud/2 + 1, E.get(1));
			++longitud;
		}
		return mPath;
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
	 * @param filesystem_path path del fitxer on estan guardades les clausures
	 * @throws IOException si el fitxer no existeix, el fitxer es un directori,
	 * el fitxer no es pot llegir o hi ha altres problemes d'entrada/sortida
	 * @author Carlos Lazaro
	 */
	public void carregarClausures(String filesystem_path) throws IOException {
		clausures = (HashMap<String, Matriu>) ControladorPersistencia.carregarClausures(filesystem_path);
	}

	/**
	 * Esborra totes les clausures d'aquest HeteSim i, si filesystem_path existeix, l'esborra.
	 * @param filesystem_path path del fitxer on estan guardades les clausures
	 * @throws IOException en cas de que no es pugui esborrar el fitxer de clausures
	 * @author Carlos Lazaro
	 */
	public void eliminarClausures(String filesystem_path) {
		clausures.clear();
		if (filesystem_path != null) {
			File f = new File(filesystem_path);
			if (f.exists())
				f.delete();
		}
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
		// TODO canviar les clausures del disc?
	}
}
