package domini;

public class Paper extends Node{

	private static final long serialVersionUID = -3505903393666871963L;

	public Paper(){
		super();
	}
	public Paper(int id, String nom) {
		super(id,nom);
	}

	public Paper(int id, String nom, String label) {
		super(id,nom,label);	
	}
}
