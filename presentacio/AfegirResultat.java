package presentacio;

import java.util.ArrayList;
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
 * Vista per afegir un resultat (dada i rellev\u00E0ncia) a l'\u00FAltima consulta.
 * @author Carla Claverol
 *
 */

public class AfegirResultat extends JFrame {

	private static final long serialVersionUID = 2629156937342951370L;
	
	private JPanel contentPane;
	private ControladorPresentacio ctrl;
	private TipusDada tipus;
	private int id = -1;
	private String nom;
	private double rellevancia;
	

	/**
	 * Constructor.
	 * @param ctrl. El ControladorPresentacio del programa.
	 * @param tipus. El tipus de dada que es vol afegir al resultat (Autor, Paper, Conferencia o Terme).
	 */
	public AfegirResultat(ControladorPresentacio ctrl, TipusDada tipus) {
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

		//etiqueta rellev\u00E0ncia
		JLabel lblRellevancia = new JLabel("Rellev\u00E0ncia:");
		lblRellevancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRellevancia.setBounds(10, 36, 79, 14);
		contentPane.add(lblRellevancia);

		//comboBox per sel\u00B7leccionar la dada
		String[] dades = getDades();
		JComboBox<String> comboBox = new JComboBox<>(dades);
		comboBox.insertItemAt("- Sel\u00B7lecciona una dada -", 0);
		comboBox.setSelectedItem("- Sel\u00B7lecciona una dada -");
		comboBox.setBounds(74, 8, 160, 20);
		contentPane.add(comboBox);

		//spinner per escollir la rellev\u00E0ncia
		JSpinner spinner_rell = new JSpinner();
		spinner_rell.setModel(new SpinnerNumberModel(0f, 0f, 1f, 0.01f));
		spinner_rell.setBounds(99, 33, 135, 20);
		contentPane.add(spinner_rell);

		//bot\u00F3 per acceptar
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
					new ErrorMessage(contentPane, "Escull una dada i introdueix la rellev\u00E0ncia.");
			}
		});
		btnAccept.setBounds(132, 73, 102, 23);
		contentPane.add(btnAccept);

		//bot\u00F3 per cancel\u00B7lar
		JButton btnCancel = new JButton("Cancel\u00B7lar");
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
	 * Consultora del resultat afegit a l'\u00FAltima consulta.
	 * @return la rellev\u00E0ncia i l'identificador i el nom de la dada del resultat afegit.
	 * 			Si no s'ha afegit cap resultat, l'identificador ser\u00E0 -1, la rellev\u00E0ncia 0 i el nom null.
	 */
	public Pair<Double,Pair<Integer, String>> getNouResultat() {
		Pair<Integer, String> dada = new Pair<>(id, nom);
		Pair<Double, Pair<Integer, String>> res = new Pair<>(rellevancia, dada);
		return res;
	}


	/**
	 * M\u00E9tode per obtenir els noms de totes les dades del tipus indicat.
	 * @return un array amb els noms de totes les dades del conjunt de dades actual que siguin
	 * 			del tipus indicat per l'atibut tipus de la classe, sense repeticions.
	 */
	private String[] getDades() {
		TreeMap<Integer, String> map = new TreeMap<>();
		
		switch (tipus) {
			case Autor:
				map = ctrl.consultarAutors();
				break;
			case Conferencia:
				map = ctrl.consultarConferencies();
				break;
			case Paper:
				map = ctrl.consultarPapers();
				break;
			case Terme:
				map = ctrl.consultarTermes();
				break;
			default:
				break;
		}
		
		Set<String> dades = new HashSet<String>(map.values());
		return dades.toArray(new String[dades.size()]);
	}

	/**
	 * M\u00E9tode per obtenir els identificadors de totes les dades del tipus indicat amb un nom concret.
	 * @param nom. El nom de les dades de les quals volem obtenir els identificadors.
	 * @return una llista amb els identificadors de totes les dades del conjunt de dades actual
	 * 			que tenen el nom indicat i que s\u00F3n del tipus indicat per l'atribut tipus de la classe.
	 */
	private List<Integer> llistaId(String dada) {
		List<Integer> id = new ArrayList<>();

		switch (tipus) {
			case Autor:
				id = ctrl.consultarAutor(dada);
				break;
			case Conferencia:
				id = ctrl.consultarConferencia(dada);
				break;
			case Paper:
				id = ctrl.consultarPaper(dada);
				break;
			case Terme:
				id = ctrl.consultarTerme(dada);
				break;
			default:
				break;
		}

		return id;
	}
	
	/**
	 * Afegeix un resultat a l'\u00FAltima consulta si aquesta existeix i el resultat encara
	 * no forma part d'ella, i tanca la finestra. Altrament, mostra un missatge d'error.
	 * @param nouNom. El nom de la dada del resultat que volem afegir.
	 * @param novaRellevancia. La rellev\u00E0ncia del resultat que volem afegir.
	 */
	private void afegir(String nouNom, double novaRellevancia) {
		try {
			if (jaAfegit()){
				id = -1;
				new ErrorMessage(contentPane, "Aquest resultat ja est\u00E0 afegit.");
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
	 * M\u00E9tode per consultar si el resultat que volem afegir ja forma part de l\u00FAltima consulta.
	 * @return cert si l'atribut de la classe id \u00E9s l'identificador d'algun resultat de
	 * 			l'\u00FAltima consulta, i fals altrament.
	 */
	private boolean jaAfegit() {
		for (Entry<Double, Entry<Integer, String>> res : ctrl.consultarResultat()) {
			if (res.getValue().getKey() == id)  return true;
		}
		return false;
	}
}
