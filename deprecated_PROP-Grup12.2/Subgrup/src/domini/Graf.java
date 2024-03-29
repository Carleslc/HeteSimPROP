package domini;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Stub de Graf
 * @author Carleslc
 */
public class Graf implements Serializable {

	private static final long serialVersionUID = -5976485947632957778L;

	int papersId, autorsId;
	
	Matriu<Byte> adjPaperAutors; 
	HashMap<Integer, Paper> papers;
	HashMap<Integer, Autor> autors;
	
	public Graf() {
		adjPaperAutors = new Matriu<Byte>();
		papers = new HashMap<Integer, Paper>();
		autors = new HashMap<Integer, Autor>();
		papersId = autorsId = 0;
	}
	
	public Paper consultarPaper(int i) {
		return papers.get(i);
	}

	public void afegeix(Autor autor) {
		adjPaperAutors.afegirColumna((byte)0);
		autor.setId(autorsId);
		autors.put(autorsId++, autor);
	}

	public Autor consultarAutor(int i) {
		return autors.get(i);
	}

	public void afegirAdjacencia(Paper p, Autor a) {
		adjPaperAutors.set(p.getId(), a.getId(), (byte)1);
	}
	
	public void eliminarAdjacencia(Paper p, Autor a) {
		adjPaperAutors.set(p.getId(), a.getId(), (byte)0);
	}

	public void afegeix(Paper paper) {
		adjPaperAutors.afegirFila((byte)0);
		paper.setId(papersId);
		papers.put(papersId++, paper);
	}

	public Matriu<Byte> consultarMatriuPaperAutor() {
		return adjPaperAutors;
	}

	public Matriu<Byte> consultarMatriuPaperConferencia() {
		return null;
	}

	public Matriu<Byte> consultarMatriuPaperTerme() {
		return null;
	}

	public int consultaMidaPaper() {
		return papers.size();
	}

	public int consultaMidaAutor() {
		return autors.size();
	}

	public Node consultarConferencia(int id) {
		return null;
	}

	public Node consultarTerme(int id) {
		return null;
	}
	
}
