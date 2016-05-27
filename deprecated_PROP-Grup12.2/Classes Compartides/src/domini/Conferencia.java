package domini;

public class Conferencia extends Node{

	private static final long serialVersionUID = -553013046374731147L;

	public Conferencia(){
		super();
	}
	public Conferencia(int id, String nom) {
		super(id,nom);
	}

	public Conferencia(int id, String nom, String label) {
		super(id,nom,label);	
	}
}
