package domini;

import java.io.Serializable;

public abstract class Node implements Serializable{

	private static final long serialVersionUID = -6708769476959213782L;
	
	protected String nom;
	protected Integer id;
	protected String label;
	
	public Node(){
		
	}
	
	public Node(int id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public Node(int id, String nom, String label) {
		this.id = id;
		this.nom = nom;
		this.label = label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public String getNom() {
		return nom;
	}

	public int getId() {
		return id;
	}

	public String toString(){
		String auxiliar=this.getClass().getSimpleName() + " " + getId() + ": " + getNom();
		return auxiliar;
	}
}
