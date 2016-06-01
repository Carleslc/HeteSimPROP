package domini;

import java.util.Map.Entry;
import java.io.Serializable;

/**
 * Classe genèrica amb dos par\u00E0metres que implementa l'interf\u00EDcie Entry<K, V>.
 * El primer par\u00E0metre \u00E9s la clau i el segon, el valor.
 * 
 * @author Carla Claverol
 */

public class Pair<K extends Comparable<K> & Serializable, V> implements Entry<K, V>, Comparable<Pair<K,V>>, Serializable {
	
	private static final long serialVersionUID = -1708108678640588754L;
	
	private K key;
	private V value;
	
	
	/**
	 * Constructor de la classe.
	 * @param key. Clau del pair.
	 * @param value. Valor del pair.
	 */
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Consultor de la clau del pair.
	 * @return Retorna la clau del pair.
	 */
	@Override
	public K getKey() {
		return key;
	}

	/**
	 * Consultor del valor del pair.
	 * @return Retorna el valor del pair.
	 */
	@Override
	public V getValue() {
		return value;
	}
	
	/**
	 * Modificador de la clau del pair.
	 * @param key. Nova clau del pair.
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**
	 * Modificador del valor del pair.
	 * @param value. Nou valor del pair.
	 * @return Retorna el valor antic del pair.
	 */
	@Override
	public V setValue(V value) {
		V oldv = this.value;
		this.value = value;
		return oldv;
	}
	
	/**
	 * Comparador de la classe. Compara nom\u00E9s segons les claus.
	 * @param p. Pair amb el que comparem el nostre pair.
	 * @return Retorna 0 si els dos pairs s\u00F3n iguals, un valor negatiu si el pair original
	 * 			\u00E9s m\u00E9s gran que p o un valor positiu si el pair original \u00E9s m\u00E9s petit que p
	 */
	@Override
	public int compareTo(Pair<K, V> p) {
		return -key.compareTo(p.getKey());
	}
	
	/**
	 * Representaci\u00F3 en String del pair.
	 * @return Retorna un String que representa el pair.
	 */
	public String toString() {
		String aux = "[" + getKey().toString() + ", " + getValue().toString() + "]";
		return aux;
	}

}