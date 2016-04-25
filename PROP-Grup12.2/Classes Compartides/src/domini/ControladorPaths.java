package domini;

import java.util.*;

import persistencia.ControladorPersistencia;

import java.io.*;

public class ControladorPaths implements Serializable{

	private static final long serialVersionUID = 7268800273742255851L;

	private TreeMap<String, Path> paths;

    public ControladorPaths(ControladorGraf controladorGraf) {
        paths = new TreeMap<String, Path>();
    }

    public boolean afegir(String path, String definicio) {
        if (paths.containsKey(path) || path.length() <= 1) return false;
        else {
            boolean segpap = (path.charAt(0) == 'A' || path.charAt(0) == 'C' || path.charAt(0) == 'T');
            if (!segpap && path.charAt(0) != 'P') return false;
            for (int i = 1; i < path.length(); ++i) {
                if (segpap) {
                    if (path.charAt(i) != 'P') return false;
                    segpap = false;
                }
                else {
                    if (path.charAt(i) != 'A' && path.charAt(i) != 'C' && path.charAt(i) != 'T') return false;
                    segpap = true;
                }
            }
            Path p = new Path(path, definicio);
            paths.put (path, p);
        }
        return true;
    }

    public boolean afegir(String path) {
        if (paths.containsKey(path) || path.length() <= 1) return false;
        else {
            boolean segpap = (path.charAt(0) == 'A' || path.charAt(0) == 'C' || path.charAt(0) == 'T');
            if (!segpap && path.charAt(0) != 'P') return false;
            for (int i = 1; i < path.length(); ++i) {
                if (segpap) {
                    if (path.charAt(i) != 'P') return false;
                    segpap = false;
                }
                else {
                    if (path.charAt(i) != 'A' && path.charAt(i) != 'C' && path.charAt(i) != 'T') return false;
                    segpap = true;
                }
            }
            Path p = new Path(path);
            paths.put (path, p);
        }
        return true;
    }

    public boolean modificarDefinicio(String path, String definicio) {
        if (paths.containsKey(path)) paths.get(path).setDefinicio(definicio);
        else return false;
        return true;
    }

    public boolean esborrar(String path) {
        if (paths.containsKey(path)) paths.remove(path);
        else return false;
        return true;
    }

    public List<String> consultarPaths() {
        List<String> l = new ArrayList<String>();
        Collection<Path> s = paths.values();
        for (Path p : s) {
            l.add(p.toString());
        }
        return l;
    }

    public String consultarDefinicio(String path) {
        if (paths.containsKey(path)) return paths.get(path).getDefinicio();
        return null;
    }

    public void guardarPaths(String filesystem_path) throws IOException{
        ControladorPersistencia.guardarPaths (filesystem_path, paths.values());
    }

    public void carregarPaths(String filesystem_path) throws IOException{
        Collection<Path> t = ControladorPersistencia.carregarPaths(filesystem_path);
        if (!t.isEmpty()) {
            paths = new TreeMap<String, Path>();
            for (Path p : t) {
                paths.put (p.getPath(), p);
            }
        }
    }
}
