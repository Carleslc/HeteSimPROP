package presentacio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

/**
 * Vista per modificar un resultat concret de l'�ltima consulta.
 * Es poden modificar tant la dada com la rellev�ncia del resultat.
 * @author Carla Claverol
 *
 */

public class ModificarResultat extends JFrame {

	private static final long serialVersionUID = -785002974683500375L;
	
	private JPanel contentPane;
	private ControladorPresentacio ctrl;
	private String tipus;
	private int id;
	private String nom;
	private double rellevancia;
	private int index;

	
	/**
	 * Constructor.
	 * @param ctrl. El ControladorPresentacio del programa.
	 * @param tipus. El tipus de la dada del resultat que es vol modificar (Autor, Paper, Conferencia o Terme).
	 * @param id. L'id de la dada original del resultat que es vol modificar.
	 * @param nom. El nom de la dada original del resultat que es vol modificar.
	 * @param rellevancia. La rellev�ncia original del resultat que es vol modificar.
	 * @param index. La posici� original del resultat que es vol modificar en el vector de resultats
	 * 				de l'�ltima consulta.
	 */
	public ModificarResultat(ControladorPresentacio ctrl, String tipus, int id, String nom, double rellevancia, int index) {
		this.ctrl = ctrl;
		this.tipus = tipus;
		this.id = id;
		this.nom = nom;
		this.rellevancia = rellevancia;
		this.index = index;
		
		setTitle("Modificar resultat");
		setIconImage(ControladorPresentacio.ICON_ADD);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 266, 130);
		setResizable(false);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//etiqueta dada
		JLabel lblDada = new JLabel("Dada:");
		lblDada.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDada.setBounds(10, 11, 51, 14);
		contentPane.add(lblDada);
		
		//etiqueta rellev�ncia
		JLabel lblRellevancia = new JLabel("Rellev�ncia:");
		lblRellevancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRellevancia.setBounds(10, 36, 71, 14);
		contentPane.add(lblRellevancia);
		
		//comboBox per sel�leccionar la nova dada
		String[] dades = getDades();
		JComboBox<String> comboBox = new JComboBox<>(dades);
		comboBox.setSelectedItem(nom);
		comboBox.setBounds(71, 8, 168, 20);
		contentPane.add(comboBox);
		
		//spinner per escollir la nova rellev�ncia
		JSpinner spinner_rell = new JSpinner();
		spinner_rell.setModel(new SpinnerNumberModel(0f, 0f, 1f, 0.01f));
		spinner_rell.setBounds(91, 33, 148, 20);
		contentPane.add(spinner_rell);
		
		//bot� per acceptar
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String novaDada = comboBox.getSelectedItem().toString();
				double novaRell = (double)spinner_rell.getValue();
				
				List<Integer> llistaIds = llistaId(novaDada);
				
				if (llistaIds.size() == 1) {
					int nouId = llistaIds.get(0);
					modificar(nouId, novaDada, novaRell);
				}
				else {
					SeleccionarDada frame = new SeleccionarDada(ctrl, novaDada, tipus);
					frame.setVisible(true);
					setEnabled(false);
					frame.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent e) {
							setEnabled(true);
							setVisible(true);
							int nouId = frame.getResultat();
							modificar(nouId, novaDada, novaRell);
						}
					});
				}
			}
		});
		btnAccept.setBounds(139, 61, 100, 23);
		contentPane.add(btnAccept);
		
		//bot� per cancel�lar
		JButton btnCancel = new JButton("Cancel�lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(10, 61, 100, 23);
		contentPane.add(btnCancel);
	}
	
	/**
	 * Consultora de l'identificador de la dada del resultat que volem modificar.
	 * @return l'identificador de la dada del resultat que volem modificar: el nou si s'ha modificat
	 * 			o l'original si no.
	 */
	public int getNouId() {
		return id;
	}
	
	/**
	 * Consultora del nom de la dada del resultat que volem modificar.
	 * @return el nom de la dada del resultat que volem modificar: el nou si s'ha modificat
	 * 			o l'original si no.
	 */
	public String getNovaDada() {
		return nom;
	}
	
	/**
	 * Consultora de la rellev�ncia del resultat que volem modificar.
	 * @return la rellev�ncia del resultat que volem modificar: la nova si s'ha modificat o l'original si no.
	 */
	public double getNovaRellevancia() {
		return rellevancia;
	}

	
	/**
	 * M�tode per obtenir els noms de totes les dades del tipus indicat.
	 * @return un array amb els noms de totes les dades del conjunt de dades actual que siguin
	 * 			del tipus indicat per l'atibut tipus de la classe, sense repeticions.
	 */
	private String[] getDades() {
		TreeMap<Integer, String> map;
		
		if (tipus == "Autor") map = ctrl.consultarAutors();
		else if (tipus == "Conferencia") map = ctrl.consultarConferencies();
		else if (tipus == "Paper") map = ctrl.consultarPapers();
		else map = ctrl.consultarTermes();
		
		Set<String> dades = new HashSet<String>(map.values());
		return dades.toArray(new String[0]);
	}
	
	/**
	 * M�tode per obtenir els identificadors de totes les dades del tipus indicat amb un nom concret.
	 * @param dada. El nom de les dades de les quals volem obtenir els identificadors.
	 * @return una llista amb els identificadors de totes les dades del conjunt de dades actual
	 * 			que tenen el nom indicat i que s�n del tipus indicat per l'atribut tipus de la classe.
	 */
	private List<Integer> llistaId(String dada) {
		List<Integer> id;
		
		if (tipus == "Autor") id = ctrl.consultarAutor(dada);
		else if (tipus == "Conferencia") id = ctrl.consultarConferencia(dada);
		else if (tipus == "Paper") id = ctrl.consultarPaper(dada);
		else id = ctrl.consultarTerme(dada);
		
		return id;
	}
	
	/**
	 * Modifica el resultat indicat de l'�ltima consulta si aquesta existeix i l'usuari ha
	 * introdu�t algun canvi, i tanca la finestra. Altrament, mostra un missatge d'error.
	 * @param nouId. L'identificador de la dada per la qual volem modificar la dada del resultat indicat.
	 * @param novaDada. El nom de la dada per la qual volem modificar la dada del resultat indicat.
	 * @param novaRell. La rellev�ncia per la qual volem modificar la rellev�ncia del resultat indicat.
	 */
	private void modificar(int nouId, String novaDada, double novaRell) {
		try {
			DecimalFormat df = new DecimalFormat("#.####");
			if (nouId == id && df.format(novaRell).equals(df.format(rellevancia))) {
				new ErrorMessage(contentPane, "No has fet cap modificaci�.\nEscull una dada o una rellev�ncia diferents.", "No has modificat res!");
			}
			else {
				ctrl.setDada(index, nouId);
				ctrl.setRellevancia(index, novaRell);
				id = nouId;
				nom = novaDada;
				rellevancia = novaRell;
				dispose();
			}
		}
		catch (Exception e) {
			new ErrorMessage(contentPane, e.getMessage());
		}
	}
}
