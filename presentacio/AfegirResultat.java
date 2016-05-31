package presentacio;

import java.awt.EventQueue;
import java.util.HashSet;
import java.util.List;
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

public class AfegirResultat extends JFrame {

	private static final long serialVersionUID = 2629156937342951370L;
	
	private JPanel contentPane;
	private ControladorPresentacio ctrl;
	private String tipus;
	private double rellevancia;
	private int id;
	private String nom;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio ctrl = new ControladorPresentacio();
					ctrl.afegirGraf("test");
					ctrl.afegirAutor("Anna");
					ctrl.afegirAutor("Maria");
					ctrl.afegirAutor("Joan");
					ctrl.afegirAutor("Anna");
					ctrl.afegirAutor("Albert");
					AfegirResultat frame = new AfegirResultat(ctrl, "Autor");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AfegirResultat(ControladorPresentacio ctrl, String tipus) {
		this.ctrl = ctrl;
		this.tipus = tipus;

		setTitle("Afegir resultat");
		setResizable(false);
		setIconImage(ControladorPresentacio.ICON_ADD);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 136);

		content_pane();

		JLabel lblDada = new JLabel("Dada:");
		lblDada.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDada.setBounds(10, 11, 54, 14);
		contentPane.add(lblDada);

		JLabel lblRellevancia = new JLabel("Rellevància:");
		lblRellevancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRellevancia.setBounds(10, 36, 79, 14);
		contentPane.add(lblRellevancia);

		String[] dades = getDades();
		JComboBox<String> comboBox = new JComboBox<>(dades);
		comboBox.insertItemAt("- Selecciona una dada -", 0);
		comboBox.setSelectedItem("- Selecciona una dada -");
		comboBox.setBounds(74, 8, 160, 20);
		contentPane.add(comboBox);

		JSpinner spinner_rell = new JSpinner();
		spinner_rell.setModel(new SpinnerNumberModel(0f, 0f, 1f, 0.01f));
		spinner_rell.setBounds(99, 33, 135, 20);
		contentPane.add(spinner_rell);

		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					if (comboBox.getSelectedIndex() != 0) {
						nom = comboBox.getSelectedItem().toString();
						rellevancia = (Double)spinner_rell.getValue();
	
						List<Integer> ids = llistaId(nom);
	
						if (ids.size() == 1) {
							id = ids.get(0);
							ctrl.afegir(rellevancia, id);
							dispose();
						}
						else {
							SeleccionarDada frame = new SeleccionarDada(ctrl, nom, tipus);
							frame.setVisible(true);
							setEnabled(false);
							frame.addWindowListener(new WindowAdapter() {
								public void windowClosed(WindowEvent e) {
									id = frame.getResultat();
									if (id != -1) {
										ctrl.afegir(rellevancia, id);
										dispose();
									}
								}
							});
						}
					}
					else
						new ErrorMessage(contentPane, "Escull una dada i introdueix la rellevància.");
				}
				catch (Exception exc) {
					new ErrorMessage(exc.getMessage());
				}
			}
		});
		btnAccept.setBounds(132, 73, 102, 23);
		contentPane.add(btnAccept);

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

	public Pair<Double,Pair<Integer, String>> getNouResultat() {
		Pair<Integer, String> dada = new Pair<>(id, nom);
		Pair<Double, Pair<Integer, String>> res = new Pair<>(rellevancia, dada);
		return res;
	}


	private void content_pane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	private String[] getDades() {
		TreeMap<Integer, String> map;

		if (tipus == "Autor") map = ctrl.consultarAutors();
		else if (tipus == "Conferencia") map = ctrl.consultarConferencies();
		else if (tipus == "Paper") map = ctrl.consultarPapers();
		else map = ctrl.consultarTermes();

		Set<String> dades = new HashSet<String>(map.values());
		return dades.toArray(new String[0]);
	}

	private List<Integer> llistaId(String dada) {
		List<Integer> id;

		if (tipus == "Autor") id = ctrl.consultarAutor(dada);
		else if (tipus == "Conferencia") id = ctrl.consultarConferencia(dada);
		else if (tipus == "Paper") id = ctrl.consultarPaper(dada);
		else id = ctrl.consultarTerme(dada);

		return id;
	}
}
