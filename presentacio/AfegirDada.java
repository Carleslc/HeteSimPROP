package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import domini.Pair;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;

public class AfegirDada extends JFrame {

	private static final long serialVersionUID = 5530817789038554299L;

	private JPanel contentPane;
	private JTextField txtIntrodueixUnNom;
	private JTable table;
	private JButton btnAfegirDada;
	private static final String[] tipus = {"Selecciona el tipus de dada", "Autor", "Paper", "Conferencia", "Terme"};
	private static final String[] tipus_paper = {"Autor", "Conferencia", "Terme"};
	private static final String[] tipus_altra = {"Paper"};
	private static final String[] etiquetes = {"", "Database", "Data Mining", "AI", "Information Retrieval"};
	private static final Object[] newRow = {"", "", "Esborrar"};
	private DefaultTableModel tableModel;
	private ArrayList<Pair<Integer,String>> adjacencies;
	private String tipus_dada;
	private String nom;
	private String etiqueta;
	private ControladorPresentacio cntrl;
	private JScrollPane scrollPane;
	// FIXME Si no se usará mejor quitarlo
	// private MyComboBoxEditor mc;
	boolean teConferencia = false;
	Integer idCOnferencia;
	boolean saved = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AfegirDada frame = new AfegirDada(new ControladorPresentacio());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public AfegirDada(ControladorPresentacio cntrl) {
		this.cntrl = cntrl;
		adjacencies = new ArrayList<>();
		setTitle("Afegir Dada");
		setIconImage(ControladorPresentacio.ICON_ADD);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 477, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!saved) {
					String[] opcions = {"Cancelar", "No", "Sí"};
					int n= JOptionPane.showOptionDialog(e.getComponent(), "Vols guardar la dada abans de sortir?", "Guardar abans de sortir?", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, opcions, opcions[2]);
					System.out.println(n);
					if (n == 2) {
						guardarDades();
						dispose();
					}
					else if (n == 1) {
						dispose();
					}
				}
				else {
					dispose();
				}
			}
		});

		JLabel lblTipus = new JLabel("Tipus");
		GridBagConstraints gbc_lblTipus = new GridBagConstraints();
		gbc_lblTipus.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipus.anchor = GridBagConstraints.EAST;
		gbc_lblTipus.gridx = 0;
		gbc_lblTipus.gridy = 0;
		contentPane.add(lblTipus, gbc_lblTipus);

		JComboBox<String> comboBoxtipus = new JComboBox<>(tipus);
		GridBagConstraints gbc_comboBoxtipus = new GridBagConstraints();
		gbc_comboBoxtipus.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxtipus.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxtipus.gridx = 1;
		gbc_comboBoxtipus.gridy = 0;
		contentPane.add(comboBoxtipus, gbc_comboBoxtipus);

		comboBoxtipus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>) e.getSource();
				if (!cb.getSelectedItem().equals(tipus[0])) {
					String tipus_dada_temp = (String) cb.getSelectedItem();
					if (tipus_dada != null && ((tipus_dada_temp.equals("Paper") && !tipus_dada.equals("Paper")) || (!tipus_dada_temp.equals("Paper") && tipus_dada.equals("Paper")))) {
						for (int i = tableModel.getRowCount() -1; i >= 0; --i)
							tableModel.removeRow(i);
						tipus_dada = tipus_dada_temp;
						table = new JTable();
						scrollPane.setViewportView(table);
						configurarTable();
					}
					tipus_dada = tipus_dada_temp;
					if (!table.isEnabled()) {
						table.setEnabled(true);
						configurarTable();
					}
					btnAfegirDada.setEnabled(true);
				}
			}

		});

		btnAfegirDada = new JButton("Guardar Dada");
		GridBagConstraints gbc_btnAfegirDada = new GridBagConstraints();
		gbc_btnAfegirDada.insets = new Insets(0, 0, 5, 0);
		gbc_btnAfegirDada.gridx = 3;
		gbc_btnAfegirDada.gridy = 0;
		contentPane.add(btnAfegirDada, gbc_btnAfegirDada);
		btnAfegirDada.setEnabled(false);;

		btnAfegirDada.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JButton src = (JButton) e.getSource();
				if (src.isEnabled()) {
					guardarDades();
					saved = true;
				}
			}
		});

		JLabel lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		contentPane.add(lblNom, gbc_lblNom);


		txtIntrodueixUnNom = new JTextField();
		txtIntrodueixUnNom.setText("Introdueix un nom");
		GridBagConstraints gbc_txtIntrodueixUnNom = new GridBagConstraints();
		gbc_txtIntrodueixUnNom.insets = new Insets(0, 0, 5, 5);
		gbc_txtIntrodueixUnNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIntrodueixUnNom.gridx = 1;
		gbc_txtIntrodueixUnNom.gridy = 1;
		contentPane.add(txtIntrodueixUnNom, gbc_txtIntrodueixUnNom);
		txtIntrodueixUnNom.setColumns(10);

		txtIntrodueixUnNom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) e.getSource();
				if (!t.getText().equals("") && !t.getText().equals("Introdueix un nom"))
					nom = t.getText();
				else
					new ErrorMessage("El nom no pot ser buit!");
			}

		});

		JLabel lblEtiqueta = new JLabel("Etiqueta");
		GridBagConstraints gbc_lblEtiqueta = new GridBagConstraints();
		gbc_lblEtiqueta.insets = new Insets(0, 0, 5, 5);
		gbc_lblEtiqueta.anchor = GridBagConstraints.EAST;
		gbc_lblEtiqueta.gridx = 2;
		gbc_lblEtiqueta.gridy = 1;
		contentPane.add(lblEtiqueta, gbc_lblEtiqueta);

		JComboBox<String> comboBoxetiqueta = new JComboBox<>(etiquetes);
		GridBagConstraints gbc_comboBoxetiqueta = new GridBagConstraints();
		gbc_comboBoxetiqueta.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxetiqueta.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxetiqueta.gridx = 3;
		gbc_comboBoxetiqueta.gridy = 1;
		contentPane.add(comboBoxetiqueta, gbc_comboBoxetiqueta);

		comboBoxetiqueta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> src = (JComboBox<String>) e.getSource();
				etiqueta = (String)src.getSelectedItem();
				System.out.println(etiqueta);
			}

		});

		JLabel lblRelacions = new JLabel("Relacions");
		GridBagConstraints gbc_lblRelacions = new GridBagConstraints();
		gbc_lblRelacions.insets = new Insets(0, 0, 5, 5);
		gbc_lblRelacions.gridx = 1;
		gbc_lblRelacions.gridy = 2;
		contentPane.add(lblRelacions, gbc_lblRelacions);

		JButton btnAfegirRelaci = new JButton("Afegir Relaci\u00F3");
		GridBagConstraints gbc_btnAfegirRelaci = new GridBagConstraints();
		gbc_btnAfegirRelaci.insets = new Insets(0, 0, 5, 0);
		gbc_btnAfegirRelaci.gridx = 3;
		gbc_btnAfegirRelaci.gridy = 2;
		contentPane.add(btnAfegirRelaci, gbc_btnAfegirRelaci);

		btnAfegirRelaci.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (table.isEnabled()){
					tableModel.addRow(newRow);
					adjacencies.add(tableModel.getRowCount()-1, null);
				}
			}

		});

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		lblRelacions.setLabelFor(table);
		scrollPane.setViewportView(table);

		table.setEnabled(false);
		String[] colNames = {"Tipus", "Nom", "Esborrar"};
		tableModel = new DefaultTableModel(colNames, 0);
	}

	private void configurarTable() {
		table.setModel(tableModel);
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setCellEditor(new MyComboBoxEditor((tipus_dada.equals("Paper"))?tipus_paper:tipus_altra));

		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				TableModel src = (TableModel) e.getSource();
				//System.out.println("ROW: " + e.getFirstRow() + " COLUMN: " + e.getColumn());
				//System.out.println("ROW CONTENT: " + " " +  src.getValueAt(e.getFirstRow(), 0) + src.getValueAt(e.getFirstRow(), 1) + " " + src.getValueAt(e.getFirstRow(), 2));
				//System.out.println(src.getValueAt(e.getFirstRow(), 0).equals(""));
				if (e.getColumn() >= 0 && e.getColumn() < 2 && e.getFirstRow() >= 0 && e.getFirstRow() < src.getRowCount()) {
					int column = e.getColumn();
					int row = e.getFirstRow();
					if (column == 0 && !src.getValueAt(row, column).equals(null)) {
						if (src.getValueAt(row, 1).equals(null) || src.getValueAt(row, 1).equals(""))
							adjacencies.set(row, new Pair<Integer, String>(null, (String)src.getValueAt(row, column)));
						else {
							if (!src.getValueAt(row, 0).equals("Conferencia") || !teConferencia || (teConferencia && adjacencies.get(row).getKey() != null && adjacencies.get(row).getKey() == idCOnferencia))
								consultarID((String)src.getValueAt(row, 1), (String)src.getValueAt(row, 0), row);
							else {
								new ErrorMessage("El Paper ja té una Conferencia relacionada!\nModifica l'existent per cambiar de Conferencia");
								tableModel.removeRow(row);
								adjacencies.remove(row);
							}
						}
					}
					if (column == 1) {
						if (!src.getValueAt(row, 0).equals(null) && !src.getValueAt(row, 0).equals("")) {
							if (!src.getValueAt(row, 0).equals("Conferencia") || !teConferencia || (teConferencia && adjacencies.get(row).getKey() != null && adjacencies.get(row).getKey() == idCOnferencia))
								consultarID((String)src.getValueAt(row, 1), (String)src.getValueAt(row, 0), row);
							else {
								new ErrorMessage("El Paper ja té una Conferencia relacionada!\nModifica l'existent per cambiar de Conferencia");
								tableModel.removeRow(row);
								adjacencies.remove(row);
							}
						}
					}
				}
			}

		});


		Action delete = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4857200046003157922L;

			public void actionPerformed(ActionEvent e)
			{
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				((DefaultTableModel)table.getModel()).removeRow(modelRow);
				if (adjacencies.get(modelRow) != null)
					System.out.println("DELETE: "+ adjacencies.get(modelRow).getKey() + " " + adjacencies.get(modelRow).getValue());
				else
					System.out.println("DELETE: null");
				adjacencies.remove(modelRow);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
	}

	private void consultarID(String nomDada, String tipusDada, int row) {
		SeleccionarDada sd = new SeleccionarDada(cntrl, nomDada, tipusDada);
		sd.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				SeleccionarDada src = (SeleccionarDada) e.getSource();
				System.out.println("closed");
				setEnabled(true);
				if (!src.isEmpty()) {
					Integer res = src.getResultat();
					System.out.println(res);
					if (!res.equals(-1)) {
						if (tipusDada.equals("Conferencia")) {
							idCOnferencia = res;
							teConferencia = true;
						}
						adjacencies.set(row, new Pair<Integer, String>(res, (String)tableModel.getValueAt(row, 0)));
					}
					else {
						new ErrorMessage("Has de seleccionar una dada!");
						// TODO Eliminar relació de la llista
					}
				}
				else
					System.out.println("empty");
			}
		});

		sd.setVisible(true);
		setEnabled(false);

	}

	private void guardarDades() {
		if (tipus_dada != null) {
			if (!txtIntrodueixUnNom.getText().equals("") && !txtIntrodueixUnNom.equals("Introdueix un nom")) {
				nom = txtIntrodueixUnNom.getText();
				System.out.println("Estamos guardando un: " + tipus_dada + ", Con nombre: " + nom);
				int id = -1;
				switch(tipus_dada) {
				case "Autor":
					if (etiqueta != null && !etiqueta.equals("")) id = cntrl.afegirAutor(nom, etiqueta);
					else id = cntrl.afegirAutor(nom);
					guardarAdjacencies(id);
					break;
				case "Conferencia":
					if (etiqueta != null && !etiqueta.equals("")) id = cntrl.afegirConferencia(nom, etiqueta);
					else id = cntrl.afegirConferencia(nom);
					guardarAdjacencies(id);
					break;
				case "Paper":
					if (etiqueta != null && !etiqueta.equals("")) id = cntrl.afegirPaper(nom, etiqueta);
					else id = cntrl.afegirPaper(nom);
					guardarAdjacencies(id);
					break;
				case "Terme":
					if (etiqueta == null || etiqueta.equals("")) id = cntrl.afegirTerme(nom);
					else id = cntrl.afegirTerme(nom);
					guardarAdjacencies(id);
					break;
				}
				System.out.println("El id resultante es: " + id);
			}
			else {
				new ErrorMessage("El nom no pot estar buit!");
			}
		}
		else {
			new ErrorMessage("Has de seleccionar un tipus de dada!");
		}
	}

	private void guardarAdjacencies(int id) {
		if (adjacencies != null) {
			if (tipus_dada.equals("Autor")) {
				for (int i = 0; i < adjacencies.size(); ++i) {
					if (adjacencies.get(i) != null) {
						Integer id_ad = adjacencies.get(i).getKey();
						if (id_ad != null) {
							try {
								cntrl.afegirAdjacenciaPaperAutor(id_ad, id);
							} catch (IOException e) {
								new ErrorMessage(e.getMessage());
							}
						}
					}
				}
			}

			else if (tipus_dada.equals("Conferencia")) {
				for (int i = 0; i < adjacencies.size(); ++i) {
					if (adjacencies.get(i) != null) {
						Integer id_ad = adjacencies.get(i).getKey();
						if (id_ad != null) {
							try {
							cntrl.setAdjacenciaPaperConferencia(id_ad, id);
							} catch (Exception e) {
								new ErrorMessage(e.getMessage());
							}
						}
					}
				}
			}

			else if (tipus_dada.equals("Terme")) {
				for (int i = 0; i < adjacencies.size(); ++i) {
					if (adjacencies.get(i) != null) {
						Integer id_ad = adjacencies.get(i).getKey();
						if (id_ad != null) {
							try {
								cntrl.afegirAdjacenciaPaperTerme(id_ad, id);
							} catch (IOException e) {
								new ErrorMessage(e.getMessage());
							}
						}
					}
				}
			}

			else if (tipus_dada.equals("Paper")) {
				for (int i = 0; i < adjacencies.size(); ++i) {
					if (adjacencies.get(i) != null) {
						Integer id_ad = adjacencies.get(i).getKey();
						String tipus_ad = adjacencies.get(i).getValue();
						if (id_ad != null && tipus_ad != null) {
							try{
								switch (tipus_ad) {
								case "Autor":
									cntrl.afegirAdjacenciaPaperAutor(id, id_ad);
									System.out.println(tipus_ad + cntrl.consultarNomAutor(id_ad));
									break;
								case "Conferencia":
									cntrl.setAdjacenciaPaperConferencia(id, id_ad);
									System.out.println(tipus_ad + cntrl.consultarNomConferencia(id_ad));
									break;
								case "Terme":
									boolean res = cntrl.afegirAdjacenciaPaperTerme(id, id_ad);
									System.out.println(tipus_ad + " " + cntrl.consultarNomTerme(id_ad) + " " + res);
									break;
								}
							} catch (Exception e) {
								new ErrorMessage(e.getMessage());
							}
						}
					}
				}
			}
		}
	}

	private class MyComboBoxEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 610218728899535248L;
		
		public MyComboBoxEditor(String[] items) {
			super(new JComboBox<>(items));
		}
	}

}
