package presentacio;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domini.Pair;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JSpinner;

/**
 * Vista per afegir un resultat (dada i rellevància) a l'última consulta.
 * @author Carla Claverol
 *
 */

public class AfegirResultat extends JFrame {

	private static final long serialVersionUID = 2629156937342951370L;
	
	private JPanel contentPane;
	private ControladorPresentacio ctrl;
	private String tipus;
	private int id = -1;
	private String nom;
	private double rellevancia;
	

	/**
	 * Constructor.
	 * @param ctrl. El ControladorPresentacio del programa.
	 * @param tipus. El tipus de dada que es vol afegir al resultat (Autor, Paper, Conferencia o Terme).
	 */
	public AfegirResultat(ControladorPresentacio ctrl, String tipus) {
		this.ctrl = ctrl;
		this.tipus = tipus;

		setTitle("Afegir resultat");
		setResizable(false);
		setIconImage(ControladorPresentacio.ICON_ADD);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 136);

		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//etiqueta dada
		JLabel lblDada = new JLabel("Dada:");
		lblDada.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDada.setBounds(10, 11, 54, 14);
		contentPane.add(lblDada);

		//etiqueta rellevància
		JLabel lblRellevancia = new JLabel("Rellevància:");
		lblRellevancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRellevancia.setBounds(10, 36, 79, 14);
		contentPane.add(lblRellevancia);

		//comboBox per sel·leccionar la dada
		String[] dades = getDades();
		JComboBox<String> comboBox = new JComboBox<>(dades);
		comboBox.insertItemAt("- Sel·lecciona una dada -", 0);
		comboBox.setSelectedItem("- Sel·lecciona una dada -");
		comboBox.setBounds(74, 8, 160, 20);
		contentPane.add(comboBox);

		//spinner per escollir la rellevància
		JSpinner spinner_rell = new JSpinner();
		spinner_rell.setModel(new SpinnerNumberModel(0f, 0f, 1f, 0.01f));
		spinner_rell.setBounds(99, 33, 135, 20);
		contentPane.add(spinner_rell);

		//botó per acceptar
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() != 0) {
					String nouNom = comboBox.getSelectedItem().toString();
					double novaRell = (Double)spinner_rell.getValue();

					List<Integer> ids = llistaId(nouNom);

					if (ids.size() == 1) {
						id = ids.get(0);
						afegir(nouNom, novaRell);
					}
					else {
						SeleccionarDada frame = new SeleccionarDada(ctrl, nouNom, tipus);
						frame.setVisible(true);
						setEnabled(false);
						frame.addWindowListener(new WindowAdapter() {
							public void windowClosed(WindowEvent e) {
								setEnabled(true);
								setVisible(true);
								id = frame.getResultat();
								if (id != -1) {
									afegir(nouNom, novaRell);
								}
							}
						});
					}
				}
				else
					new ErrorMessage(contentPane, "Escull una dada i introdueix la rellevància.");
			}
		});
		btnAccept.setBounds(132, 73, 102, 23);
		contentPane.add(btnAccept);

		//botó per cancel·lar
		JButton btnCancel = new JButton("Cancel·lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(10, 73, 102, 23);
		contentPane.add(btnCancel);
	}

	/**
	 * Consultora del resultat afegit a l'última consulta.
	 * @return la rellevància i l'identificador i el nom de la dada del resultat afegit.
	 * 			Si no s'ha afegit cap resultat, l'identificador serà -1, la rellevància 0 i el nom null.
	 */
	public Pair<Double,Pair<Integer, String>> getNouResultat() {
		Pair<Integer, String> dada = new Pair<>(id, nom);
		Pair<Double, Pair<Integer, String>> res = new Pair<>(rellevancia, dada);
		return res;
	}


	/**
	 * Métode per obtenir els noms de totes les dades del tipus indicat.
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
	 * Métode per obtenir els identificadors de totes les dades del tipus indicat amb un nom concret.
	 * @param nom. El nom de les dades de les quals volem obtenir els identificadors.
	 * @return una llista amb els identificadors de totes les dades del conjunt de dades actual
	 * 			que tenen el nom indicat i que són del tipus indicat per l'atribut tipus de la classe.
	 */
	private List<Integer> llistaId(String nom) {
		List<Integer> id;

		if (tipus == "Autor") id = ctrl.consultarAutor(nom);
		else if (tipus == "Conferencia") id = ctrl.consultarConferencia(nom);
		else if (tipus == "Paper") id = ctrl.consultarPaper(nom);
		else id = ctrl.consultarTerme(nom);

		return id;
	}
	
	/**
	 * Afegeix un resultat a l'última consulta si aquesta existeix i el resultat encara
	 * no forma part d'ella, i tanca la finestra. Altrament, mostra un missatge d'error.
	 * @param nouNom. El nom de la dada del resultat que volem afegir.
	 * @param novaRellevancia. La rellevància del resultat que volem afegir.
	 */
	private void afegir(String nouNom, double novaRellevancia) {
		try {
			if (jaAfegit()){
				id = -1;
				new ErrorMessage(contentPane, "Aquest resultat ja està afegit.");
			}
			else{
				ctrl.afegir(novaRellevancia, id);
				nom = nouNom;
				rellevancia = novaRellevancia;
				dispose();
			}
		}
		catch (Exception e) {
			id = -1;
			new ErrorMessage(contentPane, e.getMessage());
		}
	}
	
	/**
	 * Métode per consultar si el resultat que volem afegir ja forma part de lúltima consulta.
	 * @return cert si l'atribut de la classe id és l'identificador d'algun resultat de
	 * 			l'última consulta, i fals altrament.
	 */
	private boolean jaAfegit() {
		for (Entry<Double, Entry<Integer, String>> res : ctrl.consultarResultat()) {
			if (res.getValue().getKey() == id)  return true;
		}
		return false;
	}
}
