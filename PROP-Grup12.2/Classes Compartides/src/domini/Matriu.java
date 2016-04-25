//ADECUAR EL PACKAGE
package domini;

/**
 *
 * @author Rubén Ajenjo
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Matriu<T extends Number> implements Serializable {
    private static final long serialVersionUID = 7736151765065187089L;
    private int cols,rows;
    private ArrayList<HashMap<Integer,T> > matriu;  
    private ArrayList<Double> rowSqrtNorm;
    private ArrayList<Double> colSqrtNorm;
    private boolean changes = false;
    private int type;
    private T elem;
    
    
    /**
     * Crea una matriu de tamany files x columnes i la emplena amb elem.
     * @param files  Numero de files de la matriu del parametre implicit.
     * @param columnes Numero de columnes de la matriu del parametre implicit.
     * @param elem Element de tipus <b><font color=#4a6782>T</font></b> amb el que s'omple la matriu del parametre implicit. 
     */
    public Matriu(int files, int columnes, T elem){
    	this.elem = elem;
        cols = columnes;
        rows = files;
        matriu = new ArrayList<>();
        for (int i=0; i < files; ++i){
            HashMap<Integer,T> m = new HashMap<>();                                   
            if (elem.intValue() != 0){
                for (int j = 0; j < columnes; ++j){
                    m.put(j, elem);
                }                
            }
            matriu.add(m);                
        }  
        changes = true;
    }    
    /**
    * 
    * Retorna l’element tipus <b><font color=#4a6782>T</font></b> a la posició (fila, columna).
    * @param  fila  Fila de la matriu
    * @param  columna Columna de la matriu
    * @return null si [fila][columna] exedeix els limits de la matriu. Un element tipus T en cas contrari.   
    */
    
    public T get(int fila, int columna){
        T x = null;
        if (fila < rows && columna < cols){
            x = matriu.get(fila).get(columna);
            if (x == null){
                return cast(0d);
            }
        }
        return x; 
    }
    /**
    * 
    * Posa l’element tipus <b><font color=#4a6782>T</font></b> a la posició (fila, columna).
    * @param fila  Fila de la matriu.
    * (Precondition: Fila es una fila valida de la matriu.)
    * @param columna Columna de la matriu.
    * (Precondition: Columna es una columna valida de la matriu.)
    * @param elem Element del tipus T que es posara a la posicio [fila][columna] de la matriu.
    */
    
    public void set(int fila, int columna, T elem){        
        matriu.get(fila).put(columna, elem);            
        changes = true;
    }
    /**
    * 
    * Retorna la norma del tipus <b><font color=#4a6782>T</font></b> d'una determinada fila.
    * @param fila  Fila de la matriu.
    * (Precondition: Fila es una fila valida de la matriu.)
    * @return Element tipus <b><font color=#4a6782>T</font></b> que es la norma de una fila.
    */
    public T getNormaFila(int fila){
        if (changes) recalculateNorms();
        return cast(rowSqrtNorm.get(fila));
    }
    /**
    * 
    * Retorna la norma del tipus <b><font color=#4a6782>T</font></b> d'una determinada columna.
    * @param columna Columna de la matriu.
    * (Precondition: columna es una columna valida de la matriu.)
    * @return Element tipus <b><font color=#4a6782>T</font></b> que es la norma de una columna.
    */
    public T getNormaColumna(int columna){
        if (changes) recalculateNorms();
        return cast(colSqrtNorm.get(columna));
    }
    /**
    * 
    * Afegeix una fila a la matriu amb elem en cada posició i retorna l’índex de la nova fila. 
    * Reaprofita els espais lliures per tal de mantenir el tamany de la matriu el mes acurat possible.
    * @param elem Element del tipus <b><font color=#4a6782>T</font></b> amb el que s'omple la fila    
    * @return Index de la nova fila inserida a la matriu.
    */
    public int afegirFila(T elem){
        HashMap<Integer,T> m = new HashMap<>();         
        if (elem.intValue() != 0){
            for (int j = 0; j < cols; ++j) m.put(j, elem);                
        }
        matriu.add(m);
        rows++;
        return rows - 1;
    }
    /**
    * 
    * Afegeix una columna a la matriu amb elem en cada posició i retorna l’índex de la nova fila. 
    * Reaprofita els espais lliures per tal de mantenir el tamany de la matriu el mes acurat possible.
    * @param elem Element del tipus <b><font color=#4a6782>T</font></b> amb el que s'omple la fila 
    * @return Index de la nova columna inserida a la matriu.
    */
    public int afegirColumna(T elem){       
        if (elem.intValue() != 0){
            for (HashMap<Integer,T> m : matriu){
                m.put(cols, elem);
            }       
        }
        cols++;
        return cols - 1;   
    }
    /**
    *     
    * Retorna una fila sencera tal com està a la matriu.
    * @param fila Fila de la matriu que es vol obtenir.
    * @return Retorna una fila de la matriu de tipus <b><font color=#4a6782>T</font></b>.
    */
    public ArrayList<T> getFila(int fila){
       ArrayList<T> a = new ArrayList<>();
       HashMap<Integer,T> m = new HashMap<>();
       m = matriu.get(fila);
       for (int i = 0; i < cols; ++i){
           T x = m.get(i);
           byte b = 0;
           if (x == null) a.add(cast((double)b));
           else a.add(x);
       }
       return a;
    }
    /**
    *     
    * Retorna una columna sencera tal com està a la matriu.
    * @param columna Columna de la matriu que es vol obtenir.
    * @return Retorna una columna de la matriu de tipus <b><font color=#4a6782>T</font></b>.
    */
    public ArrayList<T> getColumna(int columna){
        ArrayList<T> a = new ArrayList<>();
        for (HashMap<Integer,T> m : matriu){
            T x = m.get(columna);
            byte b = 0;
            if (x == null) a.add(cast((double)b));
            else a.add(x);
        }
        return a; 
    }
    /**
    *     
    * Retorna una fila normalitzada de la matriu.
    * @param fila Fila de la matriu que es vol obtenir.
    * @return Retorna una fila normalitzada de la matriu de tipus <b><font color=#4a6782>T</font></b>.
    */
    public ArrayList<T> getFilaNormalitzada(int fila){
       return this.normalitzadaPerFiles().getFila(fila);
    }
    /**
    *     
    * Retorna una columna de la matriu normalitzada.
    * @param columna Columna de la matriu que es vol obtenir.
    * @return Retorna una columna normalitzada de la matriu de tipus <b><font color=#4a6782>T</font></b>.
    */
    public ArrayList<T> getColumnaNormalitzada(int columna){
        Matriu<T> aux = normalitzadaPerColumnes();        
        return aux.getColumna(columna);
    }
    /**
    *     
    * Esborra una fila sencera tal com està a la matriu.
    * @param fila Fila de la matriu que es vol esborrar.
    * @return Retorna true si ho ha pogut fet. False en cas contrari.
    */
    public boolean esborrarFila(int fila){
       if (fila >= rows) return false;
       HashMap<Integer,T> m = new HashMap<>();
       matriu.set(fila, m);
       return true;
    }
    /**
    *     
    * Esborra una columna sencera tal com està a la matriu.
    * @param columna Columna de la matriu que es vol esborrar.
    * @return Retorna true si ho ha pogut fet. False en cas contrari.
    */
    public boolean esborrarColumna(int columna){
        if (columna >= cols) return false;
        for(HashMap<Integer,T> m : matriu){
            m.remove(columna);
        }
        return true;
    }
    /**
    *     
    * Retorna el numero de files de la matriu.
    */
    public int getFiles() {return rows;}
    /**
    *     
    * Retorna el numero de columnes de la matriu.
    */
    public int getColumnes() {return cols;}
    /**
    *     
    * Retorna el resultat de multiplicar el parametre implicit per la matriu de tipus <b><font color=#4a6782>T</font></b> m
    * @param m Matriu de tipus <b><font color=#4a6782>T</font></b> que es vol multiplicar per el parametre implicit.
    * @return Retorna el resultat de multiplicar el parametre implicit per m.
    */
    public Matriu<T> multiplicar(Matriu<T> m) throws IllegalArgumentException{
        if (this.getColumnes() != m.getFiles()) 
            throw new IllegalArgumentException("Les columnes de la matriu del parametre implicit han de coincidir amb les files de la matriu que es vol multiplicar.");
        Matriu<T> aux = new Matriu<>(this.getFiles(), m.getColumnes(), cast((double)0));
        Double x = 0d;
        for (int k = 0; k < cols; k++) 
            for (int i = 0; i < rows; i++) 
                for (int j = 0; j < m.cols; j++){
                    Double op1 = (Double)matriu.get(i).get(k);
                    Double op2 = (Double)m.matriu.get(k).get(j);
                    if (op1 != null && op2 != null){
                        Double op3 = (Double)aux.matriu.get(i).get(j);
                        if (op3 != null){
                            x = op3 + op1 * op2;
                            aux.set(i, j, cast(x));
                        }else{
                            x = op1 * op2;
                            aux.set(i,j,cast(x));
                        }
                    }
                }
        aux.changes = true;
        return aux;
    }
    /**
    *     
    * Retorna el resultat de transposar la matriu del parametre implicit.    
    * @return Retorna el parametre implicit transposat.
    */
    public Matriu<T> transposada(){
        Matriu<T> aux = new Matriu<>(cols, rows,cast((double)0));
        int i = 0;
        for (HashMap<Integer,T> m : matriu){            
            for (Integer key : m.keySet()) {
                T x = m.get(key);
                aux.set(key, i, x);
            }
            i++;
        }
        return aux;
    }
    /**
    *     
    * Transposa la matriu del parametre implicit.        
    */
    public void transposar(){
        this.matriu = transposada().matriu;
    }
    /**
    *     
    * Retorna la matriu del parametre implicit normalitzada per files.     
    * @return Retorna la matriu del parametre implicit normalitzada per files.     
    */
    public Matriu<T> normalitzadaPerFiles(){
        if (changes == true){
            recalculateNorms();
            changes = false;
        }
        Matriu<T> aux = new Matriu<>(rows,cols,cast((double)0));
        for (int i = 0; i < rows; ++i){
            HashMap<Integer,T> m = matriu.get(i);
            for (Integer key : m.keySet()) {
                T x = m.get(key);            
                Double kk = x.doubleValue();
                if (rowSqrtNorm.get(i)!= 0d) kk = kk / rowSqrtNorm.get(i);
                aux.set(i, key, cast(kk));
            }
        }
        return aux;
    }
    /**
    *     
    * Retorna la matriu del parametre implicit normalitzada per columnes.     
    * @return Retorna la matriu del parametre implicit normalitzada per columnes.     
    */
    public Matriu<T> normalitzadaPerColumnes(){
        if (changes == true){
            recalculateNorms();
            changes = false;
        }
        //System.out.println(colSqrtNorm.toString());
        Matriu<T> aux = new Matriu<>(rows,cols,cast((double)0));
        for (int i = 0; i < rows; ++i){
            HashMap<Integer,T> m = matriu.get(i);
            for (int j = 0; j < cols; ++j) {
                T x = m.get(j);
                if (x != null){
                    Double kk = x.doubleValue();
                    if (colSqrtNorm.get(j) != 0d){
                        kk = kk / colSqrtNorm.get(j);
                    }                    
                    aux.set(i, j, cast(kk));
                } 
            }
        }
        return aux;
    }
    /**
    *     
    * Normalitza matriu del parametre implicit per files.         
    */
    public void normalitzaPerFiles(){
        this.matriu = this.normalitzadaPerFiles().matriu;
    }
    /**
    *     
    * Normalitza la matriu del parametre implicit per columnes.         
    */
    public void normalitzaPerColumnes(){
        this.matriu = this.normalitzadaPerColumnes().matriu;
    }
        /**
    *     
    * Sigui XY la matriu del parametre implicit. Afegeix una matriu intermedia E i retorna les matrius XE i EY.
    * @param m Matriu del parametre implicit transposada.
    * @return Retorna les matrius XE en la posicio 0 d'un ArrayList i EY en la posicio 1.
    */

    public ArrayList<Matriu<T> > intermedia(Matriu<T> m){
        Matriu<T> midMat1 = new Matriu<>(rows, this.countFilledCells(), cast(0d));        
        Matriu<T> midMat2 = new Matriu<>(m.countFilledCells(), m.getFiles(), cast(0d));
        int k = 0;
        for (int i = 0; i < rows; ++i){
            HashMap<Integer, T> aux = matriu.get(i);
            for (int j = 0; j < cols; ++j){
                T x = aux.get(j);
                if (x != null && (Double)x != 0d){
                    midMat1.set(i, k, cast(1d));
                    midMat2.set(k, j, cast(1d));
                    ++k;
                }
            }
        }                      
        ArrayList<Matriu<T> > res = new ArrayList<>();
        res.add(midMat1);
        res.add(midMat2);
        return res;        
    }
   
    private void recalculateNorms(){
        changes = false;    
        colSqrtNorm = new ArrayList<>(Collections.nCopies(cols, 0d));
        rowSqrtNorm = new ArrayList<>(Collections.nCopies(rows, 0d));
        for (int i = 0; i < rows; ++i){
            Double row = 0d;
            for (int j = 0; j < cols; ++j){
                T x = matriu.get(i).get(j); 
                Double k = 0d;
                if (x != null){                  
                    k = x.doubleValue();
                    Double f = k*k;
                    row += f;
                    colSqrtNorm.set(j,colSqrtNorm.get(j)+f);
                }
                rowSqrtNorm.set(i,Math.sqrt(row));
            }            
        }  
        int i = 0;
        for (Double f : colSqrtNorm){
            f = (double)Math.sqrt(f);
            colSqrtNorm.set(i, f);
            ++i;
        }   
    }
    
    @SuppressWarnings("unchecked")
	private T cast(Double n) {
    	if (elem instanceof Byte)
    		return (T)(Byte)n.byteValue();
    	else
    		return (T)n;
    }
    
    private int countFilledCells(){
        int c = 0;
        for (int i = 0; i < rows; ++i){
            for (int j = 0; j < cols; ++j){
                T x = this.get(i, j);
                if (x != null){
                    boolean isZero = false;
                    String s = x.getClass().toString();
                    if (s.contains("Byte") && (Byte)x == (byte)0){isZero = true;}
                    
                    if (s.contains("Double") && (Double)x == 0d){isZero = true;}
                    if (isZero == false){
                        ++c;               
                    }
                }
            }
        }
        return c;
    }
    
    @Override
    public String toString(){
        String s = new String();        
        for (HashMap<Integer,T> m : matriu){
            s += "[";
            for (int i = 0; i < cols; ++i){
                T x = m.get(i);
                if (x == null) s = s+"0";
                else s = s + x.toString();
                if (i < cols - 1) s+=",";
            }
            s+="]\n";
        }
        s+="\n\n";
        return s;
    }
}
