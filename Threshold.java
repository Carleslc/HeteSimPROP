package domini;

/**
 * @author Carla Claverol
 */

import java.util.ArrayList;

public class Threshold {
	
	private double rellevancia;
	private Node a;
	private Node b;
	private Path path;
	private HeteSim hs;
	
	public Threshold(double rellevancia, Node a, Node b, Path path, HeteSim hs) {
		this.rellevancia = rellevancia;
		this.a = a;
		this.b = b;
		this.path = path;
		this.hs = hs;
	}
	
	public Threshold(Node a, Node b, Path path, HeteSim hs) {
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
	
	public Path getPath() {
		return path;
	}
	
	public double getRellevancia() {
		return rellevancia;
	}
	
	public void setRellevancia(double rellevancia) {
		this.rellevancia = rellevancia;
	}
	
	public String toString() {
		String aux = a.toString() + "\n" + b.toString() + "\n" + path.toString() + "\n" + String.valueOf(rellevancia);
		return aux;
	}
	
	private double calculateRellevancia() {
		return hs.heteSim(a, b, path.getPath());
	}
}
