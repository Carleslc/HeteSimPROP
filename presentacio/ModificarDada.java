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
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
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
	private static final String[] tipus = {"Selecciona el tipus de dada", "Autor", "Conferencia", "Terme", "Paper"};
	private static final String[] colNames = {"Tipus", "Nom", "Esborrar"};
	private static final Object[] newRow = {"", "", "Esborrar"};
	private static final String[] etiquetes = {"Database", "Data Mining", "AI", "Information Retrieval"};
	private static final String[] tipus_paper = {"Autor", "Conferencia", "Terme"};
	private static final String[] tipus_altra = {"Paper"};
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private Integer selectedID;
	private String selectedType;
	private String nomOriginal;
	private String nouNom;
	private ControladorPresentacio cntrl;
	private JButton btnAfegirRelacio;
	private ArrayList<Pair<Integer,String>> adjacencies;
	private boolean teConferencia = false;
	private Integer idConferencia = -1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio cntrl = new ControladorPresentacio();
					cntrl.afegirPaper("aa");
					cntrl.afegirConferencia("op");
					cntrl.afegirConferencia("op2");
					cntrl.setAdjacenciaPaperConferencia(0, 0);
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
		adjacencies = new ArrayList<>();
		setTitle("Modificar Dada");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_comboBoxtipus.gridwidth = 4;
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
				if (!src.getSelectedItem().equals(tipus[0])) {
					selectedType = (String) src.getSelectedItem();
					System.out.println("." + txtNom.getText() + ".");
					if (!txtNom.getText().equals(""))
						nomOriginal = txtNom.getText();
					if (nomOriginal != null) {
						seleccionarDada();
					}
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
		gbc_txtNom.gridwidth = 4;
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
		gbc_separator.gridwidth = 5;
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
		gbc_txtNouNom.gridwidth = 4;
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
		
		comboBoxetiq = new JComboBox<>(etiquetes);
		GridBagConstraints gbc_comboBoxetiq = new GridBagConstraints();
		gbc_comboBoxetiq.gridwidth = 4;
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
		
		btnAfegirRelacio = new JButton("Afegir Relaci\u00F3");
		GridBagConstraints gbc_btnAfegirRelaci = new GridBagConstraints();
		gbc_btnAfegirRelaci.insets = new Insets(0, 0, 5, 5);
		gbc_btnAfegirRelaci.gridx = 1;
		gbc_btnAfegirRelaci.gridy = 5;
		contentPane.add(btnAfegirRelacio, gbc_btnAfegirRelaci);
		btnAfegirRelacio.setEnabled(false);
		
		btnAfegirRelacio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnAfegirRelacio.isEnabled()) {
					tableModel.addRow(newRow);
					adjacencies.add(tableModel.getRowCount()-1, null);
				}
			}
		});
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		tableModel = new DefaultTableModel(colNames, 0); 
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		
		btnGuardar = new JButton("Guardar");
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 7;
		contentPane.add(btnGuardar, gbc_btnGuardar);
		btnGuardar.setEnabled(false);
		
		btnGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JButton src = (JButton)e.getSource();
				if (src.isEnabled()) {
					nouNom = txtNouNom.getText();
					if (nouNom != null && !nouNom.equals("")) {
						switch (selectedType) {
						case "Autor":
							cntrl.modificarAutor(nouNom, selectedID);
							break;
						case "Paper":
							cntrl.modificarPaper(nouNom, selectedID);
							break;
						case "Conferencia":
							cntrl.modificarConferencia(nouNom, selectedID);
							break;
						case "Terme":
							cntrl.modificarTerme(nouNom, selectedID);
							break;
						}
					}
					disableComponents();	
				}
			}
		});
	}
	
	void configurarTable() {
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setCellEditor(new MyComboBoxEditor((selectedType.equals("Paper"))?tipus_paper:tipus_altra));
		
		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				System.out.println("Vamos a modificar, teConferencia = " + teConferencia);
				TableModel src = (TableModel) e.getSource();
				if (e.getColumn() >= 0 && e.getColumn() < 2 && e.getFirstRow() >= 0 && e.getFirstRow() < src.getRowCount()) {
					int column = e.getColumn();
					int row = e.getFirstRow();
					if (column == 0 && !src.getValueAt(row, column).equals(null)) {
						if (src.getValueAt(row, 1).equals(null) || src.getValueAt(row, 1).equals(""))
							adjacencies.set(row, new Pair<Integer, String>(null, (String)src.getValueAt(row, column)));
						else {
							if ((!tableModel.getValueAt(row, 0).equals("Conferencia")) || !teConferencia || (tableModel.getValueAt(row, 0).equals("Conferencia") && teConferencia && adjacencies.get(row).getKey()!= null && idConferencia == adjacencies.get(row).getKey())) {
								System.out.println("Añadiendo adjacencia1");
								SeleccionarDada sd = new SeleccionarDada(cntrl, (String)src.getValueAt(row, 1), (String)src.getValueAt(row, 0));
								sd.setVisible(true);
								setEnabled(false);
								sd.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e) {
										SeleccionarDada src = (SeleccionarDada) e.getSource();
										Integer id = src.getResultat();
										if (!src.isEmpty()) {
											if (id != -1) {
												if (adjacencies.get(row).getKey() != null && id != adjacencies.get(row).getKey() &&  adjacencies.get(row).getKey() != -1) 
													esborrarAdjacencia(adjacencies.get(row).getKey(), adjacencies.get(row).getValue());
												afegirAdjacencia(id, (String)tableModel.getValueAt(row, 0));
												adjacencies.set(row, new Pair<Integer, String>(id, (String)tableModel.getValueAt(row, 0)));
											}
											else {
												new ErrorMessage("Has de seleccionar una dada!");
												adjacencies.remove(row);
												tableModel.removeRow(row);
											}
										}
										else {
											adjacencies.remove(row);
											tableModel.removeRow(row);
										}
										setEnabled(true);
									}
								});
							}
							else {
								new ErrorMessage("El Paper " + cntrl.consultarNomPaper(selectedID) + " ja té una Conferencia relacionada");
								tableModel.removeRow(row);
								adjacencies.remove(row);
							}
						}
					}
					if (column == 1) {
						if (!src.getValueAt(row, 0).equals(null) && !src.getValueAt(row, 0).equals("")) {
							if ((!tableModel.getValueAt(row, 0).equals("Conferencia")) || !teConferencia || (teConferencia && adjacencies.get(row).getKey()!= null && idConferencia == adjacencies.get(row).getKey())) { 
								System.out.println("Añadiendo adjacencia2");
								SeleccionarDada sd = new SeleccionarDada(cntrl, (String)src.getValueAt(row, 1), (String)src.getValueAt(row, 0));
								sd.setVisible(true);
								setEnabled(false);
								sd.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e) {
										SeleccionarDada src = (SeleccionarDada) e.getSource();
										Integer id = src.getResultat();
										if (!src.isEmpty()) {
											if (id != -1) {
												if (adjacencies.get(row).getKey() != null && id != adjacencies.get(row).getKey() &&  adjacencies.get(row).getKey() != -1) 
													esborrarAdjacencia(adjacencies.get(row).getKey(), adjacencies.get(row).getValue());
												afegirAdjacencia(id, (String)tableModel.getValueAt(row, 0));
												adjacencies.set(row, new Pair<Integer, String>(id, (String)tableModel.getValueAt(row, 0)));
											}
											else {
												new ErrorMessage("Has de seleccionar una dada!");
												adjacencies.remove(row);
												tableModel.removeRow(row);
											}
										}
										else {
											adjacencies.remove(row);
											tableModel.removeRow(row);
										}
										setEnabled(true);
									}
								});
							}
							else {
								new ErrorMessage("El Paper " + cntrl.consultarNomPaper(selectedID) + " ja té una Conferencia relacionada");
								tableModel.removeRow(row);
								adjacencies.remove(row);
							}
						}
					}
				}
			}
		});
		
		Action delete = new AbstractAction() {
			private static final long serialVersionUID = 4857200046003157922L;

			public void actionPerformed(ActionEvent e)
			{
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				if (adjacencies.get(modelRow)==null || adjacencies.get(modelRow).getKey() == null || adjacencies.get(modelRow).getValue() == null) {
					tableModel.removeRow(modelRow);
					adjacencies.remove(modelRow);
				}
				else if (selectedType.equals("Conferencia") && adjacencies.get(modelRow) != null && adjacencies.get(modelRow).getKey() != null && !tableModel.getValueAt(modelRow, 1).equals("")) {
					SeleccionarConferencia sc = new SeleccionarConferencia(cntrl, cntrl.consultarNomConferencia(selectedID), (String)tableModel.getValueAt(modelRow, 1));
					sc.setVisible(true);
					
					sc.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							SeleccionarConferencia src = (SeleccionarConferencia) e.getSource();
							Integer new_conf = src.getResultat();
							if (new_conf != -1) {
								try {
									cntrl.setAdjacenciaPaperConferencia(adjacencies.get(modelRow).getKey(), new_conf);
									tableModel.removeRow(modelRow);
								} catch (IOException e1) {
									new ErrorMessage(e1.getMessage());
								}
							}
							else {
								new ErrorMessage("No has seleccionat cap Conferencia nova!");
							}
						}
					});
				}
				else if (adjacencies.get(modelRow) != null && adjacencies.get(modelRow).getKey() != null && adjacencies.get(modelRow).getValue() != null) {
					if (tableModel.getValueAt(modelRow, 0).equals("Conferencia")) {
						new ErrorMessage("Un Paper sempre ha de tenir una adjacencia amb Conferencia!"
								+ "\nModifica l'adjacencia per cambiar de Conferencia");
					}
					else {
						System.out.println("OPCION A");
						Integer id_ad = adjacencies.get(modelRow).getKey();
						String tipus_ad = adjacencies.get(modelRow).getValue();
						esborrarAdjacencia(id_ad, tipus_ad);
						adjacencies.remove(modelRow);
						((DefaultTableModel)table.getModel()).removeRow(modelRow);
					}
				}
				else if (adjacencies.get(modelRow) != null && adjacencies.get(modelRow).getKey() != null && !tableModel.getValueAt(modelRow, 1).equals("") &&
					!tableModel.getValueAt(modelRow, 1).equals(null) && !tableModel.getValueAt(modelRow, 0).equals("") &&
					!tableModel.getValueAt(modelRow, 0).equals(null)) {
						if (tableModel.getValueAt(modelRow, 0).equals("Conferencia")) {
							new ErrorMessage("Un Paper sempre ha de tenir una adjacencia amb Conferencia!"
									+ "\nModifica l'adjacencia per cambiar de Conferencia");
						}
						else {
							System.out.println("OPCION B");
							SeleccionarDada sd = new SeleccionarDada(cntrl, (String) tableModel.getValueAt(modelRow, 1), (String) tableModel.getValueAt(modelRow, 0));
							String tipus_ad = (String) tableModel.getValueAt(modelRow, 0);
							sd.setVisible(true);
							sd.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									SeleccionarDada src = (SeleccionarDada) e.getSource();
									Integer id_ad = src.getResultat();
									if (id_ad != -1) {
										esborrarAdjacencia(id_ad, tipus_ad);
										adjacencies.remove(modelRow);
										((DefaultTableModel)table.getModel()).removeRow(modelRow);
									}
									else
										new ErrorMessage("No has seleccionat cap dada!");
								}
							});
						}
				}
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
	}
	
	void afegirAdjacencia(Integer id_ad, String tipus_ad) {
		switch(tipus_ad) {
		case "Paper":
			afegirAdjacenciaambPaper(id_ad);
			break;
		case "Autor":
			afegirAdjacenciaambAutor(id_ad);
			break;
		case "Terme":
			afegirAdjacenciaambTerme(id_ad);
			break;
		case "Conferencia":
			afegirAdjacenciaambConferencia(id_ad);
		}
	}
	
	void afegirAdjacenciaambPaper(Integer id_ad) {
		switch(selectedType) {
		case "Autor":
			try {
				if (!cntrl.afegirAdjacenciaPaperAutor(id_ad, selectedID))
					new ErrorMessage("No s'ha pogut afegir la relació");
			}
			catch(IOException e) {
				new ErrorMessage(e.getMessage());
			}
			break;
		case "Terme":
			try {
				cntrl.afegirAdjacenciaPaperTerme(id_ad, selectedID);
			}
			catch(IOException e) {
				new ErrorMessage(e.getMessage());
			}
			break;
		case "Conferencia":
			if (cntrl.consultarRelacionsPaperAmbConferencia(id_ad).size() >= 1) {
				int option = JOptionPane.showConfirmDialog(null, "El Paper ja té una relació amb una altra Conferencia,\n estàs segur de cambiar-la?", "Cambiar de Conferencia", JOptionPane.YES_NO_OPTION);
				if (option == 0) {
					try {
						cntrl.setAdjacenciaPaperConferencia(id_ad, selectedID);
					} catch (IOException e) {
						new ErrorMessage(e.getMessage());
					}
				}
			}
			else {
				try {
					cntrl.setAdjacenciaPaperConferencia(id_ad, selectedID);
				} catch (IOException e) {
					new ErrorMessage(e.getMessage());
				}
			}
		}
	}
	
	void afegirAdjacenciaambAutor(Integer id_ad) {
		try {
			cntrl.afegirAdjacenciaPaperAutor(selectedID, id_ad);
		}
		catch(IOException e) {
			new ErrorMessage(e.getMessage());
		}
	}
	
	void afegirAdjacenciaambTerme(Integer id_ad) {
		try {
			cntrl.afegirAdjacenciaPaperTerme(selectedID, id_ad);
		}
		catch(IOException e) {
			new ErrorMessage(e.getMessage());
		}
	}
	
	void afegirAdjacenciaambConferencia(Integer id_ad) {
		System.out.println("teConferencia: " + teConferencia);
		try {
			teConferencia = true;
			idConferencia = id_ad;
			cntrl.setAdjacenciaPaperConferencia(selectedID, id_ad);
		} catch (IOException e) {
			new ErrorMessage(e.getMessage());
		}
	}
	
	void esborrarAdjacencia(Integer id_ad, String tipus_ad) {
		switch(tipus_ad) {
		case "Paper":
			esborrarAdjacenciaambPaper(id_ad);
			break;
		case "Autor":
			esborrarAdjacenciaambAutor(id_ad);
			break;
		case "Terme":
			esborrarAdjacenciaambTerme(id_ad);
			break;
		case "Conferencia":
			esborrarAdjacenciaambConferencia(id_ad);
		}
	}
	
	void esborrarAdjacenciaambPaper(Integer id_ad) {
		System.out.println("Borrando " + selectedType + " " + selectedID + " con Paper " + id_ad);
		switch (selectedType) {
		case "Autor":
			try {
				cntrl.eliminarAdjacenciaPaperAutor(id_ad, selectedID);
			} catch (IOException e1) {
				new ErrorMessage(e1.getMessage());
			}
			break;
		case "Terme":
			try {
				cntrl.eliminarAdjacenciaPaperTerme(id_ad, selectedID);
			} catch (IOException e) {
				new ErrorMessage(e.getMessage());
			}
			break;
		}
	}
	
	void esborrarAdjacenciaambConferencia(Integer id_ad) {
		
	}
	
	void esborrarAdjacenciaambAutor(Integer id_ad) {
		try {
			cntrl.eliminarAdjacenciaPaperAutor(selectedID, id_ad);
		} catch (IOException e) {
			new ErrorMessage(e.getMessage());
		}
	}
	
	void esborrarAdjacenciaambTerme(Integer id_ad) {
		try {
			cntrl.eliminarAdjacenciaPaperTerme(selectedID, id_ad);
		} catch (IOException e) {
			new ErrorMessage(e.getMessage());
		}
	}
	
	void enableComponents() {
		table.setEnabled(true);
		btnGuardar.setEnabled(true);
		if(!selectedType.equals("Terme"))
			comboBoxetiq.setEnabled(true);
		txtNouNom.setEnabled(true);
		btnAfegirRelacio.setEnabled(true);
		configurarTable();
		omplirTable();
	}
	
	void disableComponents() {
		table.setEnabled(false);
		btnGuardar.setEnabled(false);
		comboBoxetiq.setEnabled(false);
		txtNouNom.setEnabled(false);
		btnAfegirRelacio.setEnabled(false);
		tableModel.getDataVector().clear();
		comboBoxtipus.setEnabled(true);
		txtNom.setEnabled(true);
		txtNouNom.setText("");
		txtNom.setText("");
		selectedID = null;
		selectedType = null;
		nomOriginal = null;
		nouNom = null;
		adjacencies = new ArrayList<>();
		teConferencia = false;
		idConferencia = -1;
	}
	
	void omplirTable() {
		switch (selectedType) {
		case "Paper":
			omplirTable((ArrayList<String>) cntrl.consultarRelacionsPaperAmbAutor(selectedID), "Autor");
			omplirTable((ArrayList<String>) cntrl.consultarRelacionsPaperAmbConferencia(selectedID), "Conferencia");
			omplirTable((ArrayList<String>) cntrl.consultarRelacionsPaperAmbTerme(selectedID), "Terme");
			break;
		case "Autor":
			omplirTable((ArrayList<String>) cntrl.consultarRelacionsAutor(selectedID), "Paper");
			break;
		case "Conferencia":
			System.out.println("Posibles antes de llamada: " + cntrl.consultarRelacionsConferencia(selectedID));
			omplirTable((ArrayList<String>) cntrl.consultarRelacionsConferencia(selectedID), "Paper");
			break;
		case "Terme":
			omplirTable((ArrayList<String>) cntrl.consultarRelacionsTerme(selectedID), "Paper");
			break;
		}
	}
	
	void omplirTable(ArrayList<String> data, String tipus) {
		for (String node : data) {
			tableModel.addRow(new String[]{tipus, node, "Esborrar"});
			ArrayList<Integer> adj = null;
			ArrayList<Integer> posibles = null;
			switch (tipus) {
			case "Paper":
				posibles = (ArrayList<Integer>) cntrl.consultarPaper(node);
				adj = adjAmbPaper(posibles);
				break;
			case "Autor":
				posibles = (ArrayList<Integer>) cntrl.consultarAutor(node);
				adj = adjAmbAutor(posibles);
				break;
			case "Terme":
				posibles = (ArrayList<Integer>) cntrl.consultarTerme(node);
				adj = adjAmbTerme(posibles);
				break;
			case "Conferencia":
				posibles = (ArrayList<Integer>) cntrl.consultarConferencia(node);
				adj = adjAmbConferencia(posibles);
				idConferencia = adj.get(0);
				break;
			}
			for (Integer id_node : adj)
				adjacencies.add(new Pair<Integer, String>(id_node, tipus));
		}
	}
	
	ArrayList<Integer> adjAmbPaper(ArrayList<Integer> posibles) {
		ArrayList<Integer> res = new ArrayList<>();
		System.out.println("Posibles: " + posibles);
		for (Integer paper : posibles) {
			switch (selectedType) {
			case "Autor":
				if (cntrl.existeixRelacioPaperAutor(paper, selectedID))
					res.add(paper);
				break;
			case "Terme":
				if (cntrl.existeixRelacioPaperTerme(paper, selectedID))
					res.add(paper);
				break;
			case "Conferencia":
				if (cntrl.existeixRelacioPaperConferencia(paper, selectedID))
					res.add(paper);
				break;
			}
		}
		return res;
	}
	
	ArrayList<Integer> adjAmbAutor (ArrayList<Integer> posibles) {
		ArrayList<Integer> res = new ArrayList<>();
		for (Integer autor : posibles) {
			if (cntrl.existeixRelacioPaperAutor(selectedID, autor))
				res.add(autor);
		}
		return res;
	}
	
	ArrayList<Integer> adjAmbConferencia (ArrayList<Integer> posibles) {
		ArrayList<Integer> res = new ArrayList<>();
		for (Integer conferencia : posibles) {
			if (cntrl.existeixRelacioPaperConferencia(selectedID, conferencia))
				res.add(conferencia);
		}
		return res;
	}
	
	ArrayList<Integer> adjAmbTerme (ArrayList<Integer> posibles) {
		ArrayList<Integer> res = new ArrayList<>();
		for (Integer terme : posibles) {
			if (cntrl.existeixRelacioPaperAutor(selectedID, terme))
				res.add(terme);
		}
		return res;
	}
	
	void seleccionarDada() {
		SeleccionarDada sd = new SeleccionarDada(cntrl, nomOriginal, selectedType);
		sd.setVisible(true);
		sd.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				selectedID = sd.getResultat();
				if (selectedID == -1) {
					new ErrorMessage("Has de seleccionar una dada!");
				}
				if (selectedType != null && selectedID != -1) {
					enableComponents();
					txtNom.setEnabled(false);
					comboBoxtipus.setEnabled(false);
				}
				if (selectedType.equals("Paper")) {
					System.out.println("Size relacions = " + cntrl.consultarRelacionsPaperAmbConferencia(selectedID).size());
					if (cntrl.consultarRelacionsPaperAmbConferencia(selectedID) == null) teConferencia = false;
					else teConferencia = cntrl.consultarRelacionsPaperAmbConferencia(selectedID).size()>=1?true:false;
				}
			}
		});
	}
	
	private class MyComboBoxEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 610218728899535248L;

		public MyComboBoxEditor(String[] items) {
			super(new JComboBox<>(items));
		}
	}

}
