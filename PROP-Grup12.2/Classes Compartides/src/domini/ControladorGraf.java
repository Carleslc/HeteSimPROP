package domini;

/**
 *
 * Author: David Pinilla Caparrós
 */

import persistencia.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.*; // Expresions regulars per a importacions
import java.nio.file.*; // Gestión ficheros


public class ControladorGraf {
    protected Graf graf;

    //Constructor per defecte
    public ControladorGraf(){
        graf = new Graf();
    };

    public Graf getGraf() { return graf; };
    /* Retorna el graf.
     * @return Retorna una referència al graf actual.
     */

    private HashMap<Integer,Integer> importarAutors( String dir ) throws FileNotFoundException,IOException{
        HashMap<Integer,Integer> id_migration = new HashMap<>();
        String file = Paths.get(dir,"author.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        Pattern regex = Pattern.compile("(\\d+)\\t(.+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID | grupo 2 - Nombre autor
                Autor a = new Autor();
                a.setNom(m.group(2));
                int new_id = graf.afegeix(a);
                Integer old_id = Integer.parseInt(m.group(1));
                id_migration.put(old_id,new_id);
            }
            else throw new IOException();
        }

        return id_migration;
    }

    private HashMap<Integer,Integer> importarConferencies( String dir ) throws FileNotFoundException,IOException{
        HashMap<Integer,Integer> id_migration = new HashMap<>();
        String file = Paths.get(dir,"conf.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        Pattern regex = Pattern.compile("(\\d+)\\t(.+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID | grupo 2 - Nombre conferencia
                Conferencia c = new Conferencia();
                c.setNom(m.group(2));
                int new_id = graf.afegeix(c);
                Integer old_id = Integer.parseInt(m.group(1));
                id_migration.put(old_id,new_id);
            }
            else throw new IOException();
        }
        return id_migration;
    }

    private HashMap<Integer,Integer> importarPapers( String dir ) throws FileNotFoundException,IOException{
        HashMap<Integer,Integer> id_migration = new HashMap<>();
        String file = Paths.get(dir,"paper.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        Pattern regex = Pattern.compile("(\\d+)\\t(.+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID | grupo 2 - Nombre paper
                Paper p = new Paper();
                p.setNom(m.group(2));
                int new_id = graf.afegeix(p);
                Integer old_id = Integer.parseInt(m.group(1));
                id_migration.put(old_id,new_id);
            }
            else throw new IOException();
        }

        return id_migration;
    }

    private HashMap<Integer,Integer> importarTermes( String dir ) throws FileNotFoundException,IOException{
        HashMap<Integer,Integer> id_migration = new HashMap<>();
        String file = Paths.get(dir,"term.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        Pattern regex = Pattern.compile("(\\d+)\\t(.+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID | grupo 2 - Nombre paper
                Terme t = new Terme();
                t.setNom(m.group(2));
                int new_id = graf.afegeix(t);
                Integer old_id = Integer.parseInt(m.group(1));
                id_migration.put(old_id,new_id);
            }
            else throw new IOException();
        }

        return id_migration;
    }

    private void importarRelacionsPaperAutor( String dir, HashMap<Integer,Integer> idMapP,
                                              HashMap<Integer,Integer> idMapA) throws FileNotFoundException,IOException{
        String file = Paths.get(dir,"paper_author.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        Pattern regex = Pattern.compile("(\\d+)\\t(\\d+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID Paper | grupo 2 - ID Autor
                int idP = idMapP.get(Integer.parseInt(m.group(1)));
                int idA = idMapA.get(Integer.parseInt(m.group(2)));
                Paper p = graf.consultarPaper(idP);
                Autor a = graf.consultarAutor(idA);
                graf.afegirAdjacencia(p,a);
            }
            else throw new IOException();
        }
    }

    private void importarRelacionsPaperConferencia( String dir, HashMap<Integer,Integer> idMapP,
                                              HashMap<Integer,Integer> idMapC) throws FileNotFoundException,IOException{
        String file = Paths.get(dir,"paper_conf.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        Pattern regex = Pattern.compile("(\\d+)\\t(\\d+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID Paper | grupo 2 - ID Conferencia
                int idP = idMapP.get(Integer.parseInt(m.group(1)));
                int idC = idMapC.get(Integer.parseInt(m.group(2)));
                Paper p = graf.consultarPaper(idP);
                Conferencia c = graf.consultarConferencia(idC);
                graf.afegirAdjacencia(p,c);
            }
            else throw new IOException();
        }
    }

    private void importarRelacionsPaperTerme( String dir, HashMap<Integer,Integer> idMapP,
                                                    HashMap<Integer,Integer> idMapT) throws FileNotFoundException,IOException{
        String file = Paths.get(dir,"paper_term.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        Pattern regex = Pattern.compile("(\\d+)\\t(\\d+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID Paper | grupo 2 - ID Terme
                int idP = idMapP.get(Integer.parseInt(m.group(1)));
                int idT = idMapT.get(Integer.parseInt(m.group(2)));
                Paper p = graf.consultarPaper(idP);
                Terme t = graf.consultarTerme(idT);
                graf.afegirAdjacencia(p,t);
            }
            else throw new IOException();
        }
    }

    private void importarLabelsPaper( String dir, HashMap<Integer,Integer> idMapP)
            throws FileNotFoundException,IOException{
        String file = Paths.get(dir,"paper_label.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        //ControladorNodes ctrlnodes = new ControladorNodes(this);
        Pattern regex = Pattern.compile("(\\d+)\\t(\\d+)\\t(.+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID Paper | grupo 2 - Label
                int idP = idMapP.get(Integer.parseInt(m.group(1)));
                Paper p = graf.consultarPaper(idP);
                String label = m.group(2);
                p.setLabel(label);
                //ctrlnodes.afegirLabel(label,p);
            }
            else throw new IOException();
        }
    }

    private void importarLabelsAutor( String dir, HashMap<Integer,Integer> idMapA)
            throws FileNotFoundException,IOException{
        String file = Paths.get(dir,"author_label.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        //ControladorNodes ctrlnodes = new ControladorNodes(this);
        Pattern regex = Pattern.compile("(\\d+)\\t(\\d+)\\t(.+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID Paper | grupo 2 - Label
                int idA = idMapA.get(Integer.parseInt(m.group(1)));
                Autor a = graf.consultarAutor(idA);
                String label = m.group(2);
                a.setLabel(label);
                //ctrlnodes.afegirLabel(label,a);
            }
            else throw new IOException();
        }
    }

    private void importarLabelsConferencia( String dir, HashMap<Integer,Integer> idMapC)
            throws FileNotFoundException,IOException{
        String file = Paths.get(dir,"conf_label.txt").normalize().toString();
        ArrayList<String> dades_fitxer = ControladorPersistencia.llegirFitxer(file);
        //ControladorNodes ctrlnodes = new ControladorNodes(this);
        Pattern regex = Pattern.compile("(\\d+)\\t(\\d+)\\t(.+)");
        for ( String linea : dades_fitxer ){
            Matcher m = regex.matcher(linea);
            if ( m.matches() ){
                // grupo 1 - ID Paper | grupo 2 - Label
                int idC = idMapC.get(Integer.parseInt(m.group(1)));
                Conferencia c = graf.consultarConferencia(idC);
                String label = m.group(2);
                c.setLabel(label);
                //ctrlnodes.afegirLabel(label,c);
            }
            else throw new IOException();
        }
    }





    public void importar( String directori ) throws FileNotFoundException, IOException{
    /* Importa i crea el graf mitjançant els fitxers del directori que es passen com a paràmetre
     (autors, papers, conferències, labels i relacions).
     @param directori Directori que conté els fitxers amb el format de base de dades TODO
         */

        graf = new Graf ();
        Path dir = Paths.get(directori);
        directori = dir.normalize().toString();
        if (! Files.exists(dir)) throw new FileNotFoundException();
        if ( ! Files.isDirectory(dir) ) throw new IOException();
        HashMap<Integer,Integer> idMapP = importarPapers(directori);
        HashMap<Integer,Integer> idMapA = importarAutors(directori);
        HashMap<Integer,Integer> idMapC = importarConferencies(directori);
        HashMap<Integer,Integer> idMapT = importarTermes(directori);
        importarRelacionsPaperAutor(directori,idMapP,idMapA);
        importarRelacionsPaperConferencia(directori,idMapP,idMapC);
        importarRelacionsPaperTerme(directori,idMapP,idMapT);
        importarLabelsAutor(directori,idMapA);
        importarLabelsConferencia(directori,idMapC);
        importarLabelsPaper(directori,idMapP);

    }

    public void carregar ( String directori ) throws FileNotFoundException,IOException{
        graf = ControladorPersistencia.carregarGraf(directori);
    }

    public void guardar ( String directori ) throws IOException{
        ControladorPersistencia.guardarGraf(directori, graf);
    }

    public List<Integer> consultarPaper(String nom){
    /* Retorna una llista amb els identificadors de tots els papers amb el nom del paràmetre.
     * @retrun Retorna una llista amb els identificadors de tots els papers amb el nom del paràmetre.
     */
        List<Paper> llistaPapers = graf.consultarPaper(nom);
        List<Integer> llistaIDPapers = new ArrayList<Integer>();
        for (Paper p : llistaPapers) llistaIDPapers.add(p.getId());
        return llistaIDPapers;
    }

    public List<Integer> consultarConferencia(String nom){
    /* Retorna una llista amb els identificadors de totes les conferències amb el nom del paràmetre.
     * @retrun Retorna una llista amb els identificadors de totes les conferències amb el nom del paràmetre.
     */
        List<Conferencia> llistaConferencies = graf.consultarConferencia(nom);
        List<Integer> llistaIDConferencies = new ArrayList<Integer>();
        for (Conferencia c : llistaConferencies) llistaIDConferencies.add(c.getId());
        return llistaIDConferencies;
    }

    public List<Integer> consultarTerme(String nom){
    /* Retorna una llista amb els identificadors de tots els termes amb el nom del paràmetre.
     * @retrun Retorna una llista amb els identificadors de tots els termes amb el nom del paràmetre.
     */
        List<Terme> llistaTermes = graf.consultarTerme(nom);
        List<Integer> llistaIDTermes = new ArrayList<Integer>();
        for (Terme t : llistaTermes) llistaIDTermes.add(t.getId());
        return llistaIDTermes;
    }

    public List<Integer> consultarAutor(String nom){
    /* Retorna una llista amb els identificadors de tots els autors amb el nom del paràmetre.
     * @retrun Retorna una llista amb els identificadors de tots els autors amb el nom del paràmetre.
     */
        List<Autor> llistaAutors = graf.consultarAutor(nom);
        List<Integer> llistaIDAutors = new ArrayList<Integer>();
        for (Autor a : llistaAutors) llistaIDAutors.add(a.getId());
        return llistaIDAutors;
    }

    public int consultarMidaAutors(){
    /* Consulta quants autors hi ha en el graf actual.
     * @return Retrona el nombre d'autors del graf
     */
        return graf.consultaMidaAutor();
    }

    public int consultarMidaPapers(){
    /* Consulta quants papers hi ha en el graf actual.
     * @return Retrona el nombre de papers del graf
     */
        return graf.consultaMidaPaper();
    }

    public int consultarMidaTermes(){
    /* Consulta quants termes hi ha en el graf actual.
     * @return Retrona el nombre de termes del graf
     */
        return graf.consultaMidaTerme();
    }

    public int consultarMidaConferencies(){
    /* Consulta quantes conferències hi ha en el graf actual.
     * @return Retrona el nombre de conferències del graf
     */
        return graf.consultaMidaConferencia();
    }



    public static void main(String[] args){
        ControladorGraf ctrlGraf = new ControladorGraf();

    }

}

