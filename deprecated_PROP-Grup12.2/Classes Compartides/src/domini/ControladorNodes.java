package domini;


public class ControladorNodes {
	private ControladorGraf ControladorGraf;
	
	public ControladorNodes(ControladorGraf controladorGraf){
		this.ControladorGraf=controladorGraf;
	}
	public int afegirAutor(String nom){
		Autor a=new Autor();
		a.setNom(nom);
		Graf g=ControladorGraf.getGraf();
		int x=g.afegeix(a);
		
		return x;

	}
	public int afegirAutor(String nom, String label){
		Autor a=new Autor();
		a.setNom(nom);
		a.setLabel(label);
		Graf g=ControladorGraf.getGraf();
		int x=g.afegeix(a);
		
		return x;
		
	}
	public int afegirLabel(String label, Autor autor) throws IllegalArgumentException{
		
	        Graf g=ControladorGraf.getGraf();
	        if (g.contains(autor)) {
				autor.setLabel(label);
				return autor.getId();
			}
	        else return -1;
	    
	}
	public int afegirConferencia(String nom){
		Conferencia c=new Conferencia();
		c.setNom(nom);
		Graf g=ControladorGraf.getGraf();
		int x=g.afegeix(c);
		
		return x;
	}
	public int afegirConferencia(String nom, String label){
		Conferencia c=new Conferencia();
		c.setNom(nom);
		c.setLabel(label);
		Graf g=ControladorGraf.getGraf();
		int x=g.afegeix(c);
		
		return x;
	}
	public int afegirLabel(String label, Conferencia conferencia) throws IllegalArgumentException{
			Graf g=ControladorGraf.getGraf();
	        if (g.contains(conferencia)) {
				conferencia.setLabel(label);
				return conferencia.getId();
			}
	        else return -1;
	}
	
	public int afegirPaper(String nom){
		Paper p=new Paper();
		p.setNom(nom);
		Graf g=ControladorGraf.getGraf();
		int x=g.afegeix(p);
		
		return x;
	}
	public int afegirPaper(String nom, String label){
		Paper p=new Paper();
		p.setNom(nom);
		p.setLabel(label);
		Graf g=ControladorGraf.getGraf();
		int x=g.afegeix(p);
		
		return x;
		
	}
	public int afegirLabel(String label, Paper paper) throws IllegalArgumentException {
		Graf g=ControladorGraf.getGraf();
        if (g.contains(paper)) {
			paper.setLabel(label);
			return paper.getId();
		}
    
        else return -1;
	}
	public int afegirTerme(String nom){
		Terme t=new Terme();
		t.setNom(nom);
		Graf g=ControladorGraf.getGraf();
		int x=g.afegeix(t);
		
		return x;
	}
	public boolean modificarAutor(String nouNom,int idAutor){
		 Graf g=ControladorGraf.getGraf();
		 Autor aux = g.consultarAutor(idAutor);
		 if (aux.getId() == -1) return false;
		else {
		aux.setNom(nouNom); 
		return true;
		}
	}
	public boolean modificarPaper(String nouNom,int idPaper){
		Graf g=ControladorGraf.getGraf();
		Paper aux = g.consultarPaper(idPaper);
		if (aux.getId() == -1) return false;
		else {
			aux.setNom(nouNom); 
			return true;
		}

	}
	public boolean modificarConferencia(String nouNom, int idConferencia){
		 Graf g=ControladorGraf.getGraf();
		 Conferencia aux = g.consultarConferencia(idConferencia);
		 if (aux.getId()== -1) return false;
		 else {
			 aux.setNom(nouNom); 
			 return true;
		 }
	}
	
	public boolean modificarTerme(String nouNom, int idTerme){
		 Graf g=ControladorGraf.getGraf();
		 Terme aux = g.consultarTerme(idTerme);
		 if (aux.getId() == -1) return false;
		 else {
			 aux.setNom(nouNom); 
			 return true;
		 }

	}
	public boolean eliminarAutor(int idAutor){
		Graf g=ControladorGraf.getGraf();
	    Autor aux = g.consultarAutor(idAutor);
	    if (aux.getId() == -1) return false;
	    else {
	    	g.elimina(aux);
	    	
	    	return true;
	    }
	}
	public boolean eliminarPaper(int idPaper){
		Graf g=ControladorGraf.getGraf();
	    Paper aux = g.consultarPaper(idPaper);
	    if (aux.getId() == -1) return false;
	    else {
	    	g.elimina(aux);
	    	
	    	return true;
	    }
	}
	public boolean eliminarConferencia(int idConferencia){
		Graf g=ControladorGraf.getGraf();
	    Conferencia aux = g.consultarConferencia(idConferencia);
	    if (aux.getId() == -1) return false;
	    else {
	    	g.elimina(aux);
	    	
	    	return true;
	    }
	}
	public boolean eliminarTerme(int idTerme){
		Graf g=ControladorGraf.getGraf();
	    Terme aux = g.consultarTerme(idTerme);
	    if (aux.getId() == -1) return false;
	    else {
	    	g.elimina(aux);
	    	
	    	return true;
	    }
	}

	public String consultarNomAutor(int idAutor){
	    Graf g=ControladorGraf.getGraf();
	    Autor aux = g.consultarAutor(idAutor);
	    if (aux.getId() == -1) return null;   //si no existeix retornem null
	    else return aux.getNom();
	}
	public String consultarNomPaper(int idPaper){
		Graf g=ControladorGraf.getGraf();
	    Paper aux = g.consultarPaper(idPaper);
	    if (aux.getId() == -1) return null;   //si no existeix retornem null
	    else return aux.getNom();
	}
	public String consultarNomConferencia(int idConferencia){
		Graf g=ControladorGraf.getGraf();
	    Conferencia aux = g.consultarConferencia(idConferencia);
	    if (aux.getId() == -1) return null;   //si no existeix retornem null
	    else return aux.getNom();
	}
	public String consultarNomTerme(int idTerme){
		Graf g=ControladorGraf.getGraf();
	    Terme aux = g.consultarTerme(idTerme);
	    if (aux.getId() == -1) return null;   //si no existeix retornem null
	    else return aux.getNom();
	}

}