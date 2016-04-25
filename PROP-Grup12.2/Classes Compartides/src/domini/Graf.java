package domini;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domini.Graf;
import domini.Autor;
import domini.Conferencia;
import domini.Paper;
import domini.Terme;
import domini.Matriu;

public class Graf implements Serializable{
	
	private static final long serialVersionUID = -7700713211341706733L;
	
	private Matriu<Byte> adjPapersAutors;
	private Matriu<Byte> adjPapersTermes;
	private Matriu<Byte> adjPapersConferencies;
	private HashMap<Integer, Autor> idToAutors;
	private HashMap<Integer, Paper> idToPapers;
	private HashMap<Integer, Conferencia> idToConferencies;
	private HashMap<Integer, Terme> idToTermes;
	private HashMap<String, ArrayList<Autor>> nomToAutors;
	private HashMap<String, ArrayList<Paper>> nomToPapers;
	private HashMap<String, ArrayList<Conferencia>> nomToConferencies;
	private HashMap<String, ArrayList<Terme>> nomToTermes;


	public Graf(){
		adjPapersAutors= new Matriu<>(0,0,(byte)0);
		adjPapersTermes= new Matriu<>(0,0,(byte)0);
		adjPapersConferencies= new Matriu<>(0,0,(byte)0);
		idToAutors= new HashMap<>();
		idToPapers= new HashMap<>();
		idToConferencies= new HashMap<>();
		idToTermes=new HashMap<>();
		nomToAutors=new HashMap<>();
		nomToPapers=new HashMap<>();
		nomToConferencies=new HashMap<>();
		nomToTermes=new HashMap<>();

	}
	public int afegeix(Autor autor){

		int x=adjPapersAutors.afegirColumna((byte)0);
		autor.setId(x);
		idToAutors.put(x,autor);
		String s=autor.getNom();

		if (nomToAutors.containsKey(s)) {
			ArrayList <Autor> a=nomToAutors.get(s);
			a.add(autor); 	 
		}
		else {
			ArrayList <Autor> buit = new ArrayList<Autor>();
			buit.add(autor);
			nomToAutors.put(s, buit );


		}
		return x;
	}




	public int afegeix(Paper paper){

		int x=adjPapersAutors.afegirFila((byte)0);
		adjPapersTermes.afegirFila((byte)0);
		adjPapersConferencies.afegirFila((byte)0);
		paper.setId(x);
		idToPapers.put(x, paper);
		String s=paper.getNom();
		if (nomToPapers.containsKey(s)) {
			ArrayList <Paper> a=nomToPapers.get(s);
			a.add(paper); 	 
		}
		else {
			ArrayList <Paper> buit = new ArrayList<Paper>();
			buit.add(paper);
			nomToPapers.put(s, buit );
		}
		return x;
	}




	public int afegeix(Conferencia conferencia){

		int x=adjPapersConferencies.afegirColumna((byte)0);
		conferencia.setId(x);
		idToConferencies.put(x,conferencia);
		String s=conferencia.getNom();
		if (nomToConferencies.containsKey(s)) {
			ArrayList <Conferencia> a=nomToConferencies.get(s);
			a.add(conferencia); 	 
		}
		else {
			ArrayList <Conferencia> buit = new ArrayList<Conferencia>();
			buit.add(conferencia);
			nomToConferencies.put(s, buit);


		}
		return x;
	}



	public int afegeix(Terme terme){

		int x=adjPapersTermes.afegirColumna((byte)0);
		terme.setId(x);
		idToTermes.put(x,terme);
		String s=terme.getNom();
		if (nomToTermes.containsKey(s)) {
			ArrayList <Terme> a=nomToTermes.get(s);
			a.add(terme); 	 
		}
		else {
			ArrayList <Terme> buit = new ArrayList<Terme>();
			buit.add(terme);
			nomToTermes.put(s, buit );
		}
		return x;
	} 

	public boolean elimina(Autor autor){
		if(contains(autor)){
			int x=autor.getId();
			if(adjPapersAutors.esborrarColumna(x)){
				idToAutors.remove(x);
				String s=autor.getNom();
				ArrayList <Autor> a=nomToAutors.get(s);
				for(int i = 0; i < a.size(); ++i){
					if (a.get(i).getId() == autor.getId() && a.get(i).getNom().equals(autor.getNom())){
						a.remove(i);
						autor.setId(-1);
					}
				}
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	
	public boolean elimina(Paper paper){
		if(contains(paper)){
			int x=paper.getId();
			if(adjPapersAutors.esborrarFila(x) && adjPapersConferencies.esborrarFila(x)
					&& adjPapersTermes.esborrarFila(x)){
				idToPapers.remove(x);
				String s=paper.getNom();
				ArrayList <Paper> a=nomToPapers.get(s);
				for(int i = 0; i < a.size(); ++i){
					if (a.get(i).getId() == paper.getId() && a.get(i).getNom().equals(paper.getNom())){
						a.remove(i);
						paper.setId(-1);
					}
				}
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	
	public boolean elimina(Conferencia conferencia){
		if(contains(conferencia)){
			int x=conferencia.getId();
			if(adjPapersConferencies.esborrarColumna(x)){
				idToConferencies.remove(x);
				String s=conferencia.getNom();
				ArrayList <Conferencia> a=nomToConferencies.get(s);
				for(int i = 0; i < a.size(); ++i){
					if (a.get(i).getId() == conferencia.getId() && a.get(i).getNom().equals(conferencia.getNom())){
						a.remove(i);
						conferencia.setId(-1);
					}
				}
				return true;
			}
			else return false;
		}
		else return false;
	}
	public boolean elimina(Terme terme){
		if(contains(terme)){
			int x=terme.getId();
			if(adjPapersTermes.esborrarColumna(x)){
				idToTermes.remove(x);
				String s=terme.getNom();
				ArrayList <Terme> a=nomToTermes.get(s);
				for(int i = 0; i < a.size(); ++i){
					if (a.get(i).getId() == terme.getId() && a.get(i).getNom().equals(terme.getNom())){
						a.remove(i);
						terme.setId(-1);
					}
				}
				return true;
			}
			else return false;
		}
		else return false;
	}
	
	
	public int consultaMidaAutor(){
		return idToAutors.size();
	}
	public int consultaMidaPaper(){
		return idToPapers.size();
	}
	public int consultaMidaConferencia(){
		return idToConferencies.size();
	}
	public int consultaMidaTerme(){
		return idToTermes.size();
	}
	public boolean afegirAdjacencia(Paper paper, Autor autor) {
		if (contains(autor) && contains(paper)) {
			int f = paper.getId();
			int c = autor.getId();
			adjPapersAutors.set(f, c, (byte)1);
			return true;
		}
		else return false;
	}

	public boolean afegirAdjacencia(Paper paper, Conferencia conferencia) {
		if (contains(conferencia) && contains(paper)) {
			int f = paper.getId();
			int c = conferencia.getId();
			adjPapersConferencies.set(f, c, (byte)1);
			return true;
		}
		else return false;
	}

	public boolean afegirAdjacencia(Paper paper, Terme terme) {
		if (contains(terme) && contains(paper)) {
			int f = paper.getId();
			int c = terme.getId();
			adjPapersTermes.set(f, c, (byte)1);
			return true;
		}
		else return false;
	}
	public boolean setAdjacencia(Paper paper, Conferencia conferencia){
		if (contains(conferencia) && contains(paper)) {
			boolean para = false;
			int i = paper.getId();
			int j = 0;
			while (!para && j <=  consultaMidaConferencia()){
				if (adjPapersConferencies.get(i, j) != 0) {
					para = true;
					adjPapersConferencies.set(i, j, (byte)0);
				}
				++j;
			}
			afegirAdjacencia(paper, conferencia);
			return true;
		}
		else return false;

	}

	public boolean eliminarAdjacencia(Paper paper, Autor autor) {
		if (contains(autor) && contains(paper)) {
			int f = paper.getId();
			int c = autor.getId();
			adjPapersAutors.set(f, c, (byte)0);
			return true;
		}
		else return false;
	}

	public boolean eliminarAdjacencia(Paper paper, Conferencia conferencia) {
		if (contains(conferencia) && contains(paper)) {
			int f = paper.getId();
			int c = conferencia.getId();
			adjPapersConferencies.set(f, c, (byte)0);
			return true;
		}
		else return false;
	}

	public boolean eliminarAdjacencia(Paper paper, Terme terme) {
		if (contains(terme) && contains(paper)) {
			int f = paper.getId();
			int c = terme.getId();
			adjPapersTermes.set(f, c, (byte)0);
			return true;
		}
		return false;
	}
	public boolean contains(Autor autor){
		int x=autor.getId();
		if(idToAutors.containsKey(x) && idToAutors.get(x).getNom().equals(autor.getNom())) return true;
		else return false;
	}
	public boolean contains(Paper paper){
		int x= paper.getId();
		if(idToPapers.containsKey(x) && idToPapers.get(x).getNom().equals(paper.getNom())) return true;
		else return false;
	}
	public boolean contains(Conferencia conferencia){
		int x=conferencia.getId();
		if(idToConferencies.containsKey(x) && idToConferencies.get(x).getNom().equals(conferencia.getNom())) return true;
		else return false;
	}
	public boolean contains(Terme terme){
		int x=terme.getId();
		if(idToTermes.containsKey(x) && idToTermes.get(x).getNom().equals(terme.getNom())) return true;
		else return false;
	}
	public Autor consultarAutor(int idAutor){
		if (idToAutors.containsKey(idAutor)) return idToAutors.get(idAutor);
		else {
			Autor autorerror = new Autor(-1, " ");
			return autorerror;
		}
	}
	public List<Autor> consultarAutor(String nom){
		if (nomToAutors.containsKey(nom)) return nomToAutors.get(nom);
		else {
			List<Autor> aux = new ArrayList<>();
			return aux;

		}
	}
	public Paper consultarPaper(int idPaper){
		if (idToPapers.containsKey(idPaper)) return idToPapers.get(idPaper);
		else {
			Paper papererror = new Paper(-1, " ");
			return papererror;
		}
	}
	public List<Paper> consultarPaper(String nom){
		if (nomToPapers.containsKey(nom)) return nomToPapers.get(nom);
		else {
			List<Paper> aux = new ArrayList<>();
			return aux;

		}
	}
	public Conferencia consultarConferencia(int idConferencia){
		if (idToConferencies.containsKey(idConferencia)) return idToConferencies.get(idConferencia);
		else {
			Conferencia conferror = new Conferencia(-1, " ");
			return conferror;
		}
	}
	public List<Conferencia> consultarConferencia(String nom){
		if (nomToConferencies.containsKey(nom)) return nomToConferencies.get(nom);
		else {
			List<Conferencia> aux = new ArrayList<>();
			return aux;

		}
	}
	public Terme consultarTerme(int idTerme){
		if (idToTermes.containsKey(idTerme)) return idToTermes.get(idTerme);
		else {
			Terme termeferror = new Terme(-1, " ");
			return termeferror;
		}
	}
	public List<Terme> consultarTerme(String nom){
		if (nomToTermes.containsKey(nom)) return nomToTermes.get(nom);
		else {
			List<Terme> aux = new ArrayList<>();
			return aux;

		}
	}
	public Matriu<Byte> consultarMatriuPaperAutor(){
		return adjPapersAutors;
	}
	public Matriu<Byte> consultarMatriuPaperConferencia(){
		return adjPapersConferencies;	
	}
	public Matriu<Byte> consultarMatriuPaperTerme(){
		return adjPapersTermes;
	}

}
