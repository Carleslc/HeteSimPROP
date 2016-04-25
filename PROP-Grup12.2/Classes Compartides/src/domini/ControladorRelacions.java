package domini;

// Autor: Martí Homs Soler

import java.util.*;

public class ControladorRelacions {

    private ControladorGraf controladorGraf;

    public ControladorRelacions(ControladorGraf controladorGraf) {
	this.controladorGraf = controladorGraf;
    }
    public boolean afegirAdjacenciaPaperAutor(int idPaper, int idAutor){
	Graf g = controladorGraf.getGraf();
	Paper p = g.consultarPaper(idPaper);
	Autor a = g.consultarAutor(idAutor);

	if (p != null && a != null) {
	    return g.afegirAdjacencia(p,a);
	}
	else return false;
    }
    public boolean afegirAdjacenciaPaperTerme(int idPaper, int idTerme) {
    	Graf g = controladorGraf.getGraf();
	Paper p = g.consultarPaper(idPaper);
	Terme t = g.consultarTerme(idTerme);

	if (p != null && t != null) {
	    return g.afegirAdjacencia(p,t);
	}
	else return false;
    }
    public boolean setAdjacenciaPaperConferencia(int idPaper, int idConferencia) {
    	Graf g = controladorGraf.getGraf();
	Paper p = g.consultarPaper(idPaper);
	Conferencia c = g.consultarConferencia(idConferencia);

	if (p != null && c != null) {
	    return g.setAdjacencia(p,c);
	}
	else return false;
    }
    public boolean eliminarAdjacenciaPaperAutor(int idPaper, int idAutor) {
	Graf g = controladorGraf.getGraf();
	Paper p = g.consultarPaper(idPaper);
	Autor a = g.consultarAutor(idAutor);

	if (p != null && a != null) {
	    return g.eliminarAdjacencia(p,a);
	}
	else return false;
    }
    public boolean eliminarAdjacenciaPaperTerme(int idPaper, int idTerme) {
	Graf g = controladorGraf.getGraf();
	Paper p = g.consultarPaper(idPaper);
	Terme t = g.consultarTerme(idTerme);

	if (p != null && t != null) {
	    return g.eliminarAdjacencia(p,t);
	}
	else return false;
    }
    public List<String> consultarRelacionsConferencia(int idConferencia) {
	Graf g = controladorGraf.getGraf();
	Matriu<Byte> m = g.consultarMatriuPaperConferencia();
	List<String> ls = new ArrayList<String>();
	int n = m.getFiles();
	int i = 0;
	while (i < n){
	    if (m.get(i,idConferencia) == 1) ls.add(g.consultarPaper(i).getNom());
	    ++i;
	}
	return ls;
    }
    public List<String> consultarRelacionsAutor(int idAutor) {
    	Graf g = controladorGraf.getGraf();
	Matriu<Byte> m = g.consultarMatriuPaperAutor();
	List<String> ls = new ArrayList<String>();
	int n = m.getFiles();
	int i = 0;
	while (i < n){
	    if (m.get(i,idAutor) == 1) ls.add(g.consultarPaper(i).getNom());
	    ++i;
	}
	return ls;
    }
    public List<String> consultarRelacionsTerme(int idTerme) {
        Graf g = controladorGraf.getGraf();
	Matriu<Byte> m = g.consultarMatriuPaperTerme();
	List<String> ls = new ArrayList<String>();
	int n = m.getFiles();
	int i = 0;
	while (i < n){
	    if (m.get(i,idTerme) == 1) ls.add(g.consultarPaper(i).getNom());
	    ++i;
	}
	return ls;
    }
    public List<String> consultarRelacionsPaperAmbAutor(int idPaper) {
	Graf g = controladorGraf.getGraf();
	Matriu<Byte> m = g.consultarMatriuPaperAutor();
	List<String> ls = new ArrayList<String>();
	int n = m.getColumnes();
	int i = 0;
	while (i < n){
	    if (m.get(idPaper,i) == 1) ls.add(g.consultarAutor(i).getNom());
	    ++i;
	}
	return ls;
    }
    public List<String> consultarRelacionsPaperAmbTerme(int idPaper) {
	Graf g = controladorGraf.getGraf();
	Matriu<Byte> m = g.consultarMatriuPaperTerme();
	List<String> ls = new ArrayList<String>();
	int n = m.getColumnes();
	int i = 0;
	while (i < n){
	    if (m.get(idPaper,i) == 1) ls.add(g.consultarTerme(i).getNom());
	    ++i;
	}
	return ls;
    }
    public List<String> consultarRelacionsPaperAmbConferencia(int idPaper) {
	Graf g = controladorGraf.getGraf();
	Matriu<Byte> m = g.consultarMatriuPaperConferencia();
	List<String> ls = new ArrayList<String>();
	int n = m.getColumnes();
	int i = 0;
	while (i < n){
	    if (m.get(idPaper,i) == 1) ls.add(g.consultarConferencia(i).getNom());
	    ++i;
	}
	return ls;
    }
}
