package presentacio;

import java.awt.EventQueue;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ModificarResultat extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = -785002974683500375L;
	private JPanel contentPane;
	private JTextField textField;
	private ControladorPresentacio ctrl;
	private String tipus;
	private String dada;
	private double rellevancia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio ctrl = new ControladorPresentacio();
					ctrl.afegirAutor("Anna");
					ctrl.afegirAutor("Maria");
					ctrl.afegirAutor("Joan");
					ctrl.afegirAutor("Anna");
					ctrl.afegirAutor("Albert");
					ModificarResultat frame = new ModificarResultat(ctrl, "Autor", "Anna", 0.5, 0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModificarResultat(ControladorPresentacio ctrl, String tipus, String nom, Double rel, int index) {
		this.ctrl = ctrl;
		this.tipus = tipus;
		dada = nom;
		rellevancia = rel;
		
		setTitle("Afegir resultat");
		setIconImage(ControladorPresentacio.ICON_ADD);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		content_pane();
		
		JLabel lblDada = new JLabel("Dada:");
		lblDada.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDada.setBounds(85, 59, 89, 14);
		contentPane.add(lblDada);
		
		JLabel lblRellevancia = new JLabel("Rellevància:");
		lblRellevancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRellevancia.setBounds(85, 124, 89, 14);
		contentPane.add(lblRellevancia);
		
		String[] dades = getDades();
		JComboBox<String> comboBox = new JComboBox<>(dades);
		comboBox.setSelectedItem(nom);
		comboBox.setBounds(184, 56, 168, 20);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.setText(rel.toString());
		textField.setBounds(184, 121, 65, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					String novaDada = comboBox.getSelectedItem().toString();
					dada = novaDada;
					Double novaRellevancia = Double.parseDouble(textField.getText());
					rellevancia = novaRellevancia;
					
					if (novaRellevancia < 0 || novaRellevancia > 1)
						new ErrorMessage(contentPane, "La rellevància ha de ser un real entre 0 i 1.");
					
					else {
						List<Integer> id = llistaId(novaDada);
						
						if (id.size() == 1) {
							ctrl.setDada(index, id.get(0));
							ctrl.setRellevancia(index, novaRellevancia);
							dispose();
						}
						
						SeleccionarDada frame = new SeleccionarDada(ctrl, novaDada, tipus);
						frame.setVisible(true);
						frame.addWindowListener(new WindowAdapter() {
							public void windowClosed(WindowEvent e) {
								Integer res = frame.getResultat();
								if (res != -1) {
									ctrl.setDada(index, res);
									ctrl.setRellevancia(index, novaRellevancia);
									dispose();
								}
							}
						});
					}
				}
				catch (Exception exc){
					new ErrorMessage(contentPane, "Escull una dada i introdueix la rellevància.");
				}
			}
		});
		btnAccept.setBounds(264, 200, 109, 23);
		contentPane.add(btnAccept);
		
		JButton btnCancel = new JButton("Cancel·lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(56, 200, 118, 23);
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
