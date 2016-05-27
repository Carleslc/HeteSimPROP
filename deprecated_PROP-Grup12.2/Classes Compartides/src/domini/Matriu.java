package domini;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class Matriu implements Serializable {

	private static final long serialVersionUID = 7736151765065187089L;

	InternalMatrix normalMat;
	InternalMatrix traspMat;
	ArrayList<InternalMatrix> midMat;
	
	private Matriu(InternalMatrix m, InternalMatrix trasp) {
		normalMat = new InternalMatrix(m.AA, m.IA, m.JA, m.rows, m.cols);
		traspMat = new InternalMatrix(trasp.AA, trasp.IA, trasp.JA, trasp.rows, trasp.cols);
	}

	public Matriu(int files, int columnes, double elem){
		normalMat = new InternalMatrix(files, columnes, elem);
		traspMat = new InternalMatrix(columnes, files, elem);
	}

	public int afegirColumna(double elem){
		traspMat.afegirFila(elem);
		return normalMat.afegirColumna(elem);
	}

	public int afegirFila(double elem){
		traspMat.afegirColumna(elem);
		return normalMat.afegirFila(elem);
	}

	public boolean esborrarColumna(int columna){
		normalMat.esborrarColumna(columna);
		return traspMat.esborrarFila(columna);
	}

	public boolean esborrarFila(int fila){
		normalMat.esborrarFila(fila);
		return traspMat.esborrarColumna(fila);
	}

	public double get (int fila, int columna){
		return normalMat.get(fila, columna);
	}

	public ArrayList<Double> getColumna(int columna){
		return normalMat.getColumna(columna);
	}

	public ArrayList<Double> getColumnaNormalitzada(int columna){
		return normalMat.getColumnaNormalitzada(columna);
	}

	public int getColumnes(){
		return normalMat.getColumnes();
	}

	public ArrayList<Double> getFila(int fila){
		return normalMat.getFila(fila);
	}

	public ArrayList<Double> getFilaNormalitzada(int fila){
		return normalMat.getFilaNormalitzada(fila);
	}

	public int getFiles(){
		return normalMat.getFiles();
	}

	public double getNormaColumna(int columna){
		return normalMat.getNormaColumna(columna);
	}

	public double getNormaFila(int fila){
		return normalMat.getNormaFila(fila);
	}

	public ArrayList<Matriu> intermedia(){
		return normalMat.intermedia();
	}

	public Matriu multiplicar(Matriu m) throws IllegalArgumentException{
		return normalMat.multiplicar(m);
	}

	public void normalitzaPerColumnes(){
		normalMat.normalitzaPerColumnes();
	}

	public void normalitzaPerFiles(){
		normalMat.normalitzaPerFiles();
	}

	public Matriu normalitzadaPerColumnes(){
		return normalMat.normalitzadaPerColumnes();
	}

	public Matriu normalitzadaPerFiles(){
		return normalMat.normalitzadaPerFiles();
	}

	public void set(int fila, int columna, double elem){
		normalMat.set(fila, columna, elem);
		if (traspMat != null) traspMat.set(columna, fila, elem);
	}

	public Matriu transposada() {
		if (traspMat == null) {
			traspMat = new InternalMatrix(normalMat.cols, normalMat.rows, 0d);
			for (int i = 0; i < normalMat.rows; ++i) {
				int rowPtr = normalMat.IA.get(i);
				List<Integer> A = normalMat.JA.subList(rowPtr, normalMat.IA.get(i + 1));//obtener columnas de la fila
				for (int j : A)
					traspMat.set(j, i, normalMat.AA.get(rowPtr++));
			}
		}
		return new Matriu(traspMat, normalMat);
	}
	
	public void transposar(){
		InternalMatrix aux = normalMat;
		normalMat = traspMat;
		traspMat = aux;
	}

	@Override
	public String toString(){
		return normalMat.toString();
	}

	//Clase interna privada para poder tener 2 sparse matrix, normal y traspuesta.
	//Para acelerar las operaciones criticas de calculo.
	private class InternalMatrix implements Serializable{

		private static final long serialVersionUID = -4263718699196325657L;

		private int cols,rows;

		/** valores no-cero */
		private ArrayList<Double> AA;
		/** indices de columnas */
		private ArrayList<Integer> JA;
		/** punteros de filas*/
		private ArrayList<Integer> IA;
		private ArrayList<Double> rowSqrtNorm;
		private ArrayList<Double> colSqrtNorm;
		private boolean changes = false;

		public InternalMatrix(ArrayList<Double> AA, ArrayList<Integer> IA, ArrayList<Integer> JA, int files, int columnes) {
			rowSqrtNorm = new ArrayList<>(files);
			colSqrtNorm = new ArrayList<>(columnes);
			this.AA = new ArrayList<>(AA);
			this.JA = new ArrayList<>(JA);
			this.IA = new ArrayList<>(IA);
			rows = files;
			cols = columnes;
			changes = true;
		}

		public InternalMatrix(int files, int columnes, double elem){
			rowSqrtNorm = new ArrayList<>(files);
			colSqrtNorm = new ArrayList<>(columnes);
			cols = columnes;
			rows = files;
			IA = new ArrayList<>(files + 1);
			if (elem != 0d){
				int tfc = files*columnes;
				AA = new ArrayList<>(tfc);
				JA = new ArrayList<>(tfc);
				for (int i = 0; i < files; ++i){
					for (int j = 0; j < columnes; ++j){
						AA.add(elem);
						JA.add(j);
					}
					IA.add(columnes * i);
				}
				IA.add(AA.size());
			}else{
				AA = new ArrayList<>();
				JA = new ArrayList<>();
				for (int i = 0; i < files + 1; ++i) IA.add(0);
			}
			changes = true;
		}

		/**
		 * Retorna lâelement tipus <b><font color=#4a6782>T</font></b> a la posiciÃ³ (fila, columna).
		 * @param  fila  Fila de la matriu
		 * @param  columna Columna de la matriu
		 * @return null si [fila][columna] exedeix els limits de la matriu. Un element tipus T en cas contrari.
		 */
		public double get(int fila, int columna) {
			int rowPtr = IA.get(fila);
			int index = binarySearch(JA.subList(rowPtr, IA.get(fila + 1)), columna);
			return index >= 0 ? AA.get(index + rowPtr) : 0d;
		}

		/**
		 * Posa lâelement tipus <b><font color=#4a6782>T</font></b> a la posiciÃ³ (fila, columna).
		 * @param fila  Fila de la matriu.
		 * (Precondition: Fila es una fila valida de la matriu.)
		 * @param columna Columna de la matriu.
		 * (Precondition: Columna es una columna valida de la matriu.)
		 * @param elem Element del tipus T que es posara a la posicio [fila][columna] de la matriu.
		 */
		public void set(int fila, int columna, double elem) {
			if (fila > rows - 1 || columna > cols -1) return;
			int rowPtr = IA.get(fila);
			int rowPtrEnd = IA.get(fila + 1);
			int index = binarySearch(JA.subList(rowPtr, rowPtrEnd), columna);
			if (index >= 0)
				AA.set(index + rowPtr, elem);
			else {
				for (int i = fila + 1; i < IA.size(); ++i) IA.set(i, IA.get(i) + 1);
				JA.subList(rowPtr, rowPtrEnd).add(columna);
				AA.subList(rowPtr, rowPtrEnd).add(elem);
			}
			changes = true;
		}

		/**
		 * Retorna la norma del tipus <b><font color=#4a6782>T</font></b> d'una determinada fila.
		 * @param fila  Fila de la matriu.
		 * (Precondition: Fila es una fila valida de la matriu.)
		 * @return Element tipus <b><font color=#4a6782>T</font></b> que es la norma de una fila.
		 */
		public double getNormaFila(int fila){
			if (changes) recalculateNorms();
			return rowSqrtNorm.get(fila);
		}

		/**
		 * Retorna la norma del tipus <b><font color=#4a6782>T</font></b> d'una determinada columna.
		 * @param columna Columna de la matriu.
		 * (Precondition: columna es una columna valida de la matriu.)
		 * @return Element tipus <b><font color=#4a6782>T</font></b> que es la norma de una columna.
		 */
		public double getNormaColumna(int columna){
			if (changes) recalculateNorms();
			return colSqrtNorm.get(columna);
		}

		/**
		 * Afegeix una fila a la matriu amb elem en cada posiciÃ³ i retorna lâÃ­ndex de la nova fila.
		 * Reaprofita els espais lliures per tal de mantenir el tamany de la matriu el mes acurat possible.
		 * @param elem Element del tipus <b><font color=#4a6782>T</font></b> amb el que s'omple la fila
		 * @return Index de la nova fila inserida a la matriu.
		 */
		public int afegirFila(double elem){
			IA.add(IA.get(IA.size()-1));
			if (elem != 0d) {
				for (int i = 0; i < cols; ++i)
					this.set(rows, i, elem);
			}
			return rows++;
		}

		/**
		 * Afegeix una columna a la matriu amb elem en cada posiciÃ³ i retorna lâÃ­ndex de la nova fila.
		 * Reaprofita els espais lliures per tal de mantenir el tamany de la matriu el mes acurat possible.
		 * @param elem Element del tipus <b><font color=#4a6782>T</font></b> amb el que s'omple la fila
		 * @return Index de la nova columna inserida a la matriu.
		 */
		public int afegirColumna(double elem){
			if (elem != 0d){
				for (int i = 0; i < rows; ++i) this.set(i, cols, elem);
			}
			return cols++;
		}

		/**
		 * Retorna una fila sencera tal com estÃ  a la matriu.
		 * @param fila Fila de la matriu que es vol obtenir.
		 * @return Retorna una fila de la matriu de tipus <b><font color=#4a6782>T</font></b>.
		 */
		public ArrayList<Double> getFila(int fila){
			ArrayList<Double> a = new ArrayList<>(cols);
			for (int i = 0; i < cols; ++i)
				a.add(this.get(fila, i));
			return a;
		}

		/**
		 * Retorna una columna sencera tal com estÃ  a la matriu.
		 * @param columna Columna de la matriu que es vol obtenir.
		 * @return Retorna una columna de la matriu de tipus <b><font color=#4a6782>T</font></b>.
		 */
		public ArrayList<Double> getColumna(int columna){
			ArrayList<Double> a = new ArrayList<>(rows);
			for (int i = 0; i < rows; ++i) a.add(this.get(i, columna));
			return a;
		}

		/**
		 * Retorna una fila normalitzada de la matriu.
		 * @param fila Fila de la matriu que es vol obtenir.
		 * @return Retorna una fila normalitzada de la matriu de tipus <b><font color=#4a6782>T</font></b>.
		 */
		public ArrayList<Double> getFilaNormalitzada(int fila) {
			ArrayList<Double> filaNorm = new ArrayList<>(Collections.nCopies(cols, 0d));
			double normaFila = getNormaFila(fila);
			int rowPtr = IA.get(fila);
			List<Integer> A = JA.subList(rowPtr, IA.get(fila + 1));//obtener columnas de la fila
			for (int j : A)
				filaNorm.set(j, AA.get(rowPtr++)/normaFila);
			return filaNorm;
		}

		/**
		 * Retorna una columna de la matriu normalitzada.
		 * @param columna Columna de la matriu que es vol obtenir.
		 * @return Retorna una columna normalitzada de la matriu de tipus <b><font color=#4a6782>T</font></b>.
		 */
		public ArrayList<Double> getColumnaNormalitzada(int columna) {
			ArrayList<Double> columnaNorm = new ArrayList<>(rows);
			double normaColumna = getNormaColumna(columna);
			for (int i = 0; i < rows; ++i) {
				int rowPtr = IA.get(i);
				int index = binarySearch(JA.subList(rowPtr, IA.get(i + 1)), columna);
				columnaNorm.add(index >= 0 ? AA.get(index + rowPtr)/normaColumna : 0d);
			}
			return columnaNorm;
		}

		/**
		 * Esborra una fila sencera tal com estÃ  a la matriu.
		 * @param fila Fila de la matriu que es vol esborrar.
		 * @return Retorna true si ho ha pogut fet. False en cas contrari.
		 */
		public boolean esborrarFila(int fila) {
			if (fila >= rows) return false;
			IA.set(fila, -1);
			return true;
		}

		/**
		 * Esborra una columna sencera tal com estÃ  a la matriu.
		 * @param columna Columna de la matriu que es vol esborrar.
		 * @return Retorna true si ho ha pogut fet. False en cas contrari.
		 */
		public boolean esborrarColumna(int columna){
			for (int i = 0; i < JA.size(); ++i) if (JA.get(i) == columna) JA.set(i, -1);
			return true;
		}

		/**
		 * Retorna el numero de files de la matriu.
		 */
		public int getFiles() {return rows;}

		/**
		 * Retorna el numero de columnes de la matriu.
		 */
		public int getColumnes() {return cols;}

		/**
		 * Retorna el resultat de multiplicar el parametre implicit per la matriu de tipus <b><font color=#4a6782>T</font></b> m
		 * @param m Matriu de tipus <b><font color=#4a6782>T</font></b> que es vol multiplicar per el parametre implicit.
		 * @return Retorna el resultat de multiplicar el parametre implicit per m.
		 */
		public Matriu multiplicar(Matriu m) throws IllegalArgumentException{
			//System.out.println("Mult "+rows+"x"+cols+" . "+m.normalMat.rows+"x"+m.normalMat.cols);
			if (this.getColumnes() != m.getFiles()) throw new IllegalArgumentException();
			Matriu aux = new Matriu(getFiles(), m.getColumnes(), 0d);
			aux.traspMat = null;
			List<Integer> A;
			//System.out.println("All ready. Starting mul.");
			int rowPointer = 0;
			aux.normalMat.AA = new ArrayList<>();
			aux.normalMat.JA = new ArrayList<>();
			aux.normalMat.IA = new ArrayList<>();
			ArrayList<Double> auxAA = aux.normalMat.AA;
			ArrayList<Double> mAA = m.normalMat.AA;
			ArrayList<Integer> mIA = m.normalMat.IA;
			ArrayList<Integer> mJA = m.normalMat.JA;
			ArrayList<Integer> auxJA = aux.normalMat.JA;
			for (int i = 0; i < rows; ++i) {
				aux.normalMat.IA.add(rowPointer);
				int rowStart = IA.get(i);
				A = JA.subList(rowStart, IA.get(i + 1));//obtener columnas de fila i
				int Asize = A.size();
				boolean roll = Asize%2 == 0;
				int cls = aux.getColumnes();
				for (int j = 0; j < cls; ++j) {
					double x = 0d;
					int kk = 0;
					if (roll) {
						for (;kk < Asize; kk += 2) {
							int fila = A.get(kk);
							int rowPtr = mIA.get(fila);
							int index = binarySearch(mJA.subList(rowPtr, mIA.get(fila + 1)), j);
							if (index >= 0) x += AA.get(rowStart + kk) * mAA.get(index + rowPtr);
							fila = A.get(kk + 1);
							rowPtr = mIA.get(fila);
							index = binarySearch(mJA.subList(rowPtr, mIA.get(fila + 1)), j);
							if (index >= 0) x += AA.get(rowStart + kk + 1) * mAA.get(index + rowPtr);
						}
					}
					else {
						for (;kk < Asize; ++kk) {
							int fila = A.get(kk);
							int rowPtr = mIA.get(fila);
							int index = binarySearch(mJA.subList(rowPtr, mIA.get(fila + 1)), j);
							if (index >= 0) x += AA.get(rowStart + kk) * mAA.get(index + rowPtr);
						}
					}
					if (x > 0) {
						auxAA.add(x);
						auxJA.add(j);
						rowPointer++;
					}
				}
			}
			aux.normalMat.IA.add(aux.normalMat.JA.size());
			return aux;
		}

		/**
		 * Retorna la matriu del parametre implicit normalitzada per files.
		 * @return Retorna la matriu del parametre implicit normalitzada per files.
		 */
		public Matriu normalitzadaPerFiles(){
			if (changes) recalculateNorms();
			Matriu aux = new Matriu(rows,cols,0d);
			for (int i = 0; i < rows; ++i){
				int rowPtr = IA.get(i);
				List<Integer> A = JA.subList(rowPtr, IA.get(i + 1));//obtener columnas de fila i
				for (int j : A) aux.set(i, j, AA.get(rowPtr++)/rowSqrtNorm.get(i));
			}
			return aux;
		}

		/**
		 * Retorna la matriu del parametre implicit normalitzada per columnes.
		 * @return Retorna la matriu del parametre implicit normalitzada per columnes.
		 */
		public Matriu normalitzadaPerColumnes(){
			if (changes) recalculateNorms();
			Matriu aux = new Matriu(rows,cols, 0d);
			for (int i = 0; i < rows; ++i){
				int rowPtr = IA.get(i);
				List<Integer> A = JA.subList(rowPtr, IA.get(i + 1));//obtener columnas de fila i
				for (int j : A)
					aux.set(i, j, AA.get(rowPtr++)/colSqrtNorm.get(j));
			}
			return aux;
		}

		/**
		 * Normalitza matriu del parametre implicit per files.
		 */
		public void normalitzaPerFiles(){
			Matriu m = this.normalitzadaPerFiles();
			normalMat = m.normalMat;
			traspMat = m.traspMat;
		}

		/**
		 * Normalitza la matriu del parametre implicit per columnes.
		 */
		public void normalitzaPerColumnes(){
			Matriu m = this.normalitzadaPerColumnes();
			normalMat = m.normalMat;
			traspMat = m.traspMat;
		}

		/**
		 * Sigui XY la matriu del parametre implicit. Afegeix una matriu intermedia E i retorna les matrius XE i EY.
		 * @param m Matriu del parametre implicit transposada.
		 * @return Retorna les matrius XE en la posicio 0 d'un ArrayList i EY en la posicio 1.
		 */

		private void recalculateNorms(){
			//System.out.println("RECALCULATE "+AA.size()+" NORMS");
			changes = false;
			colSqrtNorm = new ArrayList<>(Collections.nCopies(cols + 1, 0d));
			rowSqrtNorm = new ArrayList<>(Collections.nCopies(rows + 1, 0d));
			int end = IA.size() - 1;
			ListIterator<Integer> it = IA.listIterator();
			int rowStart = it.hasNext() ? it.next() : 0, rowEnd;
			for (int i = 0; i < end; ++i){
				rowEnd = it.next();
				double rv = 0d;
				for (int j = rowStart; j < rowEnd; ++j){
					double x = AA.get(j);
					x *= x;
					rv += x;
					int ja = JA.get(j);
					colSqrtNorm.set(ja, colSqrtNorm.get(ja) + x);
				}
				rowStart = rowEnd;
				rowSqrtNorm.set(i, Math.sqrt(rv));
			}
			int i = 0;
			for (double f : colSqrtNorm)
				colSqrtNorm.set(i++, Math.sqrt(f));
		}
		
		public ArrayList<Matriu> intermedia(){
			int AAsize = AA.size();
			Matriu midMat1 = new Matriu(rows, AAsize, 0d);
			Matriu midMat2 = new Matriu(AAsize, cols, 0d);
			int k = 0;
			for (int i = 0; i < rows; ++i) {
				int rowPtr = IA.get(i);
				for (int j : JA.subList(rowPtr, IA.get(i + 1))) {
					midMat1.set(i, k, 1d);
					midMat2.set(k, j, 1d);
					++k;
				}
			}
			ArrayList<Matriu > res = new ArrayList<>();
			res.add(midMat1);
			res.add(midMat2);
			return res;
		}
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < rows; ++i){
				sb.append("[");
				for (int j = 0; j < cols; ++j){
					sb.append(String.format(Locale.UK, "%.2f", get(i, j)));
					sb.append(j < cols - 1 ? ", " : "]\n");
				}
			}
			return sb.toString();
		}
		
	}
	
	private static final int binarySearch(List<Integer> l, int i) {
		int low = 0;
        int high = l.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = l.get(mid);

            if (midVal < i)
                low = mid + 1;
            else if (midVal > i)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -1;  // key not found
	}

}
