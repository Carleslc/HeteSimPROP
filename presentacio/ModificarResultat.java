package presentacio;

import java.awt.EventQueue;
import java.util.ArrayList;
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

public class ModificarResultat extends JFrame {

	private static final long serialVersionUID = -785002974683500375L;
	
	private JPanel contentPane;
	private ControladorPresentacio ctrl;
	private TipusDada tipus;
	private String dada;
	private double rellevancia;

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
					ModificarResultat frame = new ModificarResultat(ctrl, TipusDada.Autor, "Joan", 0.5, 0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModificarResultat(ControladorPresentacio ctrl, TipusDada tipus, String nom, Double rel, int index) {
		this.ctrl = ctrl;
		this.tipus = tipus;
		dada = nom;
		rellevancia = rel;
		
		setTitle("Afegir resultat");
		setIconImage(ControladorPresentacio.ICON_ADD);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 266, 130);
		setResizable(false);
		
		content_pane();
		
		JLabel lblDada = new JLabel("Dada:");
		lblDada.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDada.setBounds(10, 11, 51, 14);
		contentPane.add(lblDada);
		
		JLabel lblRellevancia = new JLabel("Rellevància:");
		lblRellevancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRellevancia.setBounds(10, 36, 71, 14);
		contentPane.add(lblRellevancia);
		
		String[] dades = getDades();
		JComboBox<String> comboBox = new JComboBox<>(dades);
		comboBox.setSelectedItem(nom);
		int original = comboBox.getSelectedIndex();
		comboBox.setBounds(71, 8, 168, 20);
		contentPane.add(comboBox);
		
		JSpinner spinner_rell = new JSpinner();
		spinner_rell.setModel(new SpinnerNumberModel(0f, 0f, 1f, 0.01f));
		spinner_rell.setBounds(91, 33, 148, 20);
		contentPane.add(spinner_rell);
		
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (comboBox.getSelectedIndex() != original) {
						dada = comboBox.getSelectedItem().toString();
						rellevancia = (double)spinner_rell.getValue();
						List<Integer> id = llistaId(dada);
						
						if (id.size() == 1) {
							ctrl.setDada(index, id.get(0));
							ctrl.setRellevancia(index, rellevancia);
							dispose();
						}
						else {
							SeleccionarDada frame = new SeleccionarDada(ctrl, dada, tipus);
							frame.setVisible(true);
							frame.addWindowListener(new WindowAdapter() {
								public void windowClosed(WindowEvent e) {
									Integer res = frame.getResultat();
									if (res != -1) {
										ctrl.setDada(index, res);
										ctrl.setRellevancia(index, rellevancia);
										dispose();
									}
								}
							});
						}
					}
					else
						new ErrorMessage(contentPane, "Escull una dada diferent i introdueix la rellevància.");
				}
				catch (Exception exc) {
					new ErrorMessage(exc.getMessage());
				}
			}
		});
		btnAccept.setBounds(139, 61, 100, 23);
		contentPane.add(btnAccept);
		
		JButton btnCancel = new JButton("Cancel·lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(10, 61, 100, 23);
		contentPane.add(btnCancel);
	}
	
	public String getNovaDada() {
		return dada;
	}
	
	public double getNovaRellevancia() {
		return rellevancia;
	}
	
	
	private void content_pane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
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
}
