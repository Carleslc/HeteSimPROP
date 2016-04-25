package domini;

public class Autor extends Node{

	private static final long serialVersionUID = -6978253558223190647L;

	public Autor(){
		super();
	}
	public Autor(int id, String nom) {
		super(id,nom);
	}

	public Autor(int id, String nom, String label) {
		super(id,nom,label);	
	}
}
