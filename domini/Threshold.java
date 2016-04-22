package domini;

import java.io.Serializable;

/**
 * @author Carla Claverol
 */

import java.util.ArrayList;

public class Threshold implements Serializable {
	
	private static final long serialVersionUID = -7710703940760522487L;
	
	private double rellevancia;
	private Node a;
	private Node b;
	private String path;
	private HeteSim hs;
	
	public Threshold(double rellevancia, Node a, Node b, String path, HeteSim hs) {
		this.rellevancia = rellevancia;
		this.a = a;
		this.b = b;
		this.path = path;
		this.hs = hs;
	}
	
	public Threshold(Node a, Node b, String path, HeteSim hs) {
		this.a = a;
		this.b = b;
		this.path = path;
		this.hs = hs;
		this.rellevancia = calculateRellevancia();
	}
	
	public ArrayList<Node> getNodes(){
		ArrayList<Node> l = new ArrayList<>();
		l.add(a);
		l.add(b);
		return l;
	}
	
	public String getPath() {
		return path;
	}
	
	public double getRellevancia() {
		return rellevancia;
	}
	
	public void setRellevancia(double rellevancia) {
		this.rellevancia = rellevancia;
	}
	
	public String toString() {
		String aux = a.toString() + "\n" + b.toString() + "\n" + path + "\n" + String.valueOf(rellevancia);
		return aux;
	}
	
	private double calculateRellevancia() {
		return hs.heteSim(a, b, path);
	}
}
