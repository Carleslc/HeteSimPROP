package presentacio;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class ModificarDada extends JFrame {

	private static final long serialVersionUID = -1898384655878892557L;
	private JPanel contentPane;
	private JTextField txtNom;
	private JTextField txtNouNom;
	private JTable table;
	private JButton btnGuardar;
	private JComboBox<String> comboBoxetiq;
	private JComboBox<String> comboBoxtipus;
	private static final String[] tipus = {"Autor", "Conferencia", "Terme", "Paper"};
	private static final String[] colNames = {"Tipus", "ID", "Nom", "Eliminar"};
	private static final String[] etiquetes = {"Database", "Data Mining", "AI", "Information Retrieval"};
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private Integer selectedID;
	private String selectedType;
	private String nomOriginal;
	private String nouNom;
	private ControladorPresentacio cntrl;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio cntrl = new ControladorPresentacio();
					cntrl.importar("GRAF", "/Users/guillemcastro/Downloads/DBLP_four_area");
					ModificarDada frame = new ModificarDada(cntrl);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModificarDada(ControladorPresentacio cntrl) {
		this.cntrl = cntrl;
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setTitle("Modificar Dada");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblTipus = new JLabel("Tipus:");
		GridBagConstraints gbc_lblTipus = new GridBagConstraints();
		gbc_lblTipus.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipus.anchor = GridBagConstraints.EAST;
		gbc_lblTipus.gridx = 0;
		gbc_lblTipus.gridy = 0;
		contentPane.add(lblTipus, gbc_lblTipus);
		
		comboBoxtipus = new JComboBox<>(tipus);
		GridBagConstraints gbc_comboBoxtipus = new GridBagConstraints();
		gbc_comboBoxtipus.gridwidth = 3;
		gbc_comboBoxtipus.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxtipus.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxtipus.gridx = 1;
		gbc_comboBoxtipus.gridy = 0;
		contentPane.add(comboBoxtipus, gbc_comboBoxtipus);
		
		comboBoxtipus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("rawtypes")
				JComboBox src = (JComboBox) e.getSource();
				selectedType = (String) src.getSelectedItem();
				System.out.println("." + txtNom.getText() + ".");
				if (!txtNom.getText().equals(""))
					nomOriginal = txtNom.getText();
				if (nomOriginal != null) {
					seleccionarDada();
				}
			}
		});
		
		JLabel lblNom = new JLabel("Nom:");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		contentPane.add(lblNom, gbc_lblNom);
		
		txtNom = new JTextField();
		GridBagConstraints gbc_txtNom = new GridBagConstraints();
		gbc_txtNom.gridwidth = 3;
		gbc_txtNom.insets = new Insets(0, 0, 5, 0);
		gbc_txtNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNom.gridx = 1;
		gbc_txtNom.gridy = 1;
		contentPane.add(txtNom, gbc_txtNom);
		txtNom.setColumns(10);
		
		txtNom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField src = (JTextField) e.getSource();
				nomOriginal = src.getText();
				
				if (selectedType != null) {
					seleccionarDada();
				}
			}
		});
		
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.weightx = 1.0;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 4;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 2;
		contentPane.add(separator, gbc_separator);
		
		JLabel lblNouNom = new JLabel("Nou nom:");
		GridBagConstraints gbc_lblNouNom = new GridBagConstraints();
		gbc_lblNouNom.anchor = GridBagConstraints.EAST;
		gbc_lblNouNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNouNom.gridx = 0;
		gbc_lblNouNom.gridy = 3;
		contentPane.add(lblNouNom, gbc_lblNouNom);
		
		txtNouNom = new JTextField();
		GridBagConstraints gbc_txtNouNom = new GridBagConstraints();
		gbc_txtNouNom.gridwidth = 3;
		gbc_txtNouNom.insets = new Insets(0, 0, 5, 0);
		gbc_txtNouNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNouNom.gridx = 1;
		gbc_txtNouNom.gridy = 3;
		contentPane.add(txtNouNom, gbc_txtNouNom);
		txtNouNom.setColumns(10);
		txtNouNom.setEnabled(false);
		
		txtNouNom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField src = (JTextField) e.getSource();
				if (src.isEnabled()) {
					nouNom = src.getText();
				}
			}
			
		});
		
		JLabel lblEtiqueta = new JLabel("Etiqueta:");
		GridBagConstraints gbc_lblEtiqueta = new GridBagConstraints();
		gbc_lblEtiqueta.anchor = GridBagConstraints.EAST;
		gbc_lblEtiqueta.insets = new Insets(0, 0, 5, 5);
		gbc_lblEtiqueta.gridx = 0;
		gbc_lblEtiqueta.gridy = 4;
		contentPane.add(lblEtiqueta, gbc_lblEtiqueta);
		
		comboBoxetiq = new JComboBox<>();
		GridBagConstraints gbc_comboBoxetiq = new GridBagConstraints();
		gbc_comboBoxetiq.gridwidth = 3;
		gbc_comboBoxetiq.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxetiq.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxetiq.gridx = 1;
		gbc_comboBoxetiq.gridy = 4;
		contentPane.add(comboBoxetiq, gbc_comboBoxetiq);
		comboBoxetiq.setEnabled(false);
		
		comboBoxetiq.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> src = (JComboBox<String>) e.getSource();
				if (src.isEnabled()) {
					String label = (String) src.getSelectedItem();
					switch (selectedType) {
					case "Autor":
						cntrl.afegirLabelAutor(label, selectedID);
						break;
					case "Paper":
						cntrl.afegirLabelPaper(label, selectedID);
						break;
					case "Conferencia":
						cntrl.afegirLabelConferencia(label, selectedID);
						break;
					default:
						new ErrorMessage("Els Termes no poden tenir etiqueta");
					}
				}
				
			}
			
		});
		
		JLabel lblRelacions = new JLabel("Relacions:");
		GridBagConstraints gbc_lblRelacions = new GridBagConstraints();
		gbc_lblRelacions.insets = new Insets(0, 0, 5, 5);
		gbc_lblRelacions.gridx = 0;
		gbc_lblRelacions.gridy = 5;
		contentPane.add(lblRelacions, gbc_lblRelacions);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		btnGuardar = new JButton("Guardar");
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 7;
		contentPane.add(btnGuardar, gbc_btnGuardar);
		btnGuardar.setEnabled(false);
	}
	
	void configurarTable() {
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setEnabled(false);
	}
	
	void enableComponents() {
		table.setEnabled(true);
		btnGuardar.setEnabled(true);
		comboBoxetiq.setEnabled(true);
		txtNouNom.setEnabled(true);
	}
	
	void seleccionarDada() {
		SeleccionarDada sd = new SeleccionarDada(cntrl, nomOriginal, selectedType);
		sd.setVisible(true);
		sd.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("closed");
				selectedID = sd.getResultat();
				if (selectedID == -1) {
					new ErrorMessage("Has de seleccionar una dada!");
				}
				if (selectedType != null && selectedID != -1) {
					enableComponents();
					txtNom.setEnabled(false);
					comboBoxtipus.setEnabled(false);
				}
			}
		});
	}

}
