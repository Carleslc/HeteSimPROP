package Pair;

import java.util.Map.Entry;

public class Pair<K extends Comparable<K>, V extends Comparable<V>> implements Entry<K, V>, Comparable< Pair<K,V> > {
	
	private K key;
	private V value;
	
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return value;
	}
	
	public void setKey(K key) {
		this.key = key;
	}

	@Override
	public V setValue(V value) {
		// TODO Auto-generated method stub
		V oldv = this.value;
		this.value = value;
		return oldv;
	}
	
	@Override
	public int compareTo(Pair<K, V> p) {
		// TODO Auto-generated method stub
		if (key.equals(p.getKey()))
			return value.compareTo(p.getValue());
		return key.compareTo(p.getKey());
	}
	
	public String toString() {
		String aux = getKey().toString() + " " + getValue().toString();
		return aux;
	}

}
