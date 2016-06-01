package presentacio;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import domini.Pair;

import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DecimalFormat;

/**
 * Vista que mostra tots els resultats de l'última consulta, ordenats decreixentment per rellevància.
 * Permet modificar els resultats, esborrar-los o afegir-ne de nous, a més d'aplicar un filtre
 * per tal que només es mostrin els primers resultats, els últims o els que tinguin una determinada etiqueta.
 * També permet exportar els resultats.
 * @author Carla Claverol
 *
 */

public class Resultats extends JFrame {

	private static final long serialVersionUID = 6679289486869864394L;
	private JPanel contentPane;
	ControladorPresentacio ctrl;
	JTextField textPrimers;
	JTextField textUltims;
	JComboBox<String> cbEtiquetes;
	private static final String[] etiquetes = {"- Sel·lecciona una etiqueta -", "Database", "Data Mining", "AI", "Information Retrieval"};
	private static final String[] columnes = {"Dada", "Rellevància", "", "", ""};
	private static final DecimalFormat df = new DecimalFormat("#.####");
	DefaultTableModel model;
	private JTable table;
	private ArrayList<Entry<Double, Entry<Integer, String>>> resultat;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio ctrl = new ControladorPresentacio();
					ctrl.afegir("AP");
					ctrl.afegirGraf("test");
					int na = ctrl.afegirAutor("anna");
					int np = ctrl.afegirPaper("El misterio del bug");
					ctrl.afegirPaper("hola");
					ctrl.afegirLabelPaper("Database", np);
					ctrl.afegirAdjacenciaPaperAutor(np, na);
					ctrl.consulta("AP", na, false);
					Resultats frame = new Resultats(ctrl);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor.
	 * @param ctrl. El ControladorPresentacio del programa.
	 */
	public Resultats(ControladorPresentacio ctrl) {
		this.ctrl = ctrl;
		resultat = ctrl.consultarResultat();
		setTitle("Resultats");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension min = new Dimension(650, 300);
		setMinimumSize(min);
		setBounds(100, 100, min.width, 450);
		JFrame ref = this;
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{90, 33, 16, 30, 89, 40, 99, 0};
		gbl_contentPane.rowHeights = new int[]{23, 2, 23, 23, 23, 2, 23, 95, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		//etiqueta relació
		JLabel lblRelacio = new JLabel("Relació: " + ctrl.getPath());
		GridBagConstraints gbc_lblRelacio = new GridBagConstraints();
		gbc_lblRelacio.insets = new Insets(0, 0, 5, 5);
		gbc_lblRelacio.gridwidth = 3;
		gbc_lblRelacio.gridx = 0;
		gbc_lblRelacio.gridy = 0;
		contentPane.add(lblRelacio, gbc_lblRelacio);
		
		//etiqueta dada
		JLabel lblDada = new JLabel("Dada: " + ctrl.getDada());
		GridBagConstraints gbc_lblDada = new GridBagConstraints();
		gbc_lblDada.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDada.insets = new Insets(0, 0, 5, 5);
		gbc_lblDada.gridwidth = 3;
		gbc_lblDada.gridx = 3;
		gbc_lblDada.gridy = 0;
		contentPane.add(lblDada, gbc_lblDada);
		
		//botó per exportar
		JButton btnExportar = new JButton("Exportar");
		btnExportar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser exportar = new JFileChooser();
				exportar.setCurrentDirectory(new File(System.getProperty("user.home")));
				int option = exportar.showSaveDialog(contentPane);
				if (option == JFileChooser.APPROVE_OPTION) {
					String file = exportar.getSelectedFile().toString();
					try {
						ctrl.exportarResultat(file, resultat);
					}
					catch (Exception exc) {
						new ErrorMessage(contentPane, "S'ha produït un error en l'exportació.\nTorna-ho a intentar.");
					}
				}
			}
		});
		GridBagConstraints gbc_btnExportar = new GridBagConstraints();
		gbc_btnExportar.anchor = GridBagConstraints.NORTH;
		gbc_btnExportar.insets = new Insets(0, 0, 5, 0);
		gbc_btnExportar.gridx = 6;
		gbc_btnExportar.gridy = 0;
		contentPane.add(btnExportar, gbc_btnExportar);
		
		//separador
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.anchor = GridBagConstraints.NORTH;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 7;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		contentPane.add(separator, gbc_separator);
		
		//radioButtons
		ButtonGroup filtres = new ButtonGroup();
		
		//radioButton per filtrar els primers
		JRadioButton rdbtnFiltrarPrimers = new JRadioButton("Filtrar els");
		GridBagConstraints gbc_rdbtnFiltrarPrimers = new GridBagConstraints();
		gbc_rdbtnFiltrarPrimers.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnFiltrarPrimers.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnFiltrarPrimers.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFiltrarPrimers.gridx = 0;
		gbc_rdbtnFiltrarPrimers.gridy = 2;
		contentPane.add(rdbtnFiltrarPrimers, gbc_rdbtnFiltrarPrimers);
		filtres.add(rdbtnFiltrarPrimers);
		
		//textField per filtrar els primers
		textPrimers = new JTextField();
		GridBagConstraints gbc_textPrimers = new GridBagConstraints();
		gbc_textPrimers.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPrimers.insets = new Insets(0, 0, 5, 5);
		gbc_textPrimers.gridx = 1;
		gbc_textPrimers.gridy = 2;
		contentPane.add(textPrimers, gbc_textPrimers);
		textPrimers.setColumns(10);
		
		//etiqueta primers
		JLabel lblPrimers = new JLabel("primers");
		GridBagConstraints gbc_lblPrimers = new GridBagConstraints();
		gbc_lblPrimers.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrimers.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrimers.gridwidth = 2;
		gbc_lblPrimers.gridx = 2;
		gbc_lblPrimers.gridy = 2;
		contentPane.add(lblPrimers, gbc_lblPrimers);
		
		//radioButton per filtrar els últims
		JRadioButton rdbtnFiltrarUltims = new JRadioButton("Filtrar els");
		GridBagConstraints gbc_rdbtnFiltrarUltims = new GridBagConstraints();
		gbc_rdbtnFiltrarUltims.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnFiltrarUltims.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnFiltrarUltims.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFiltrarUltims.gridx = 0;
		gbc_rdbtnFiltrarUltims.gridy = 3;
		contentPane.add(rdbtnFiltrarUltims, gbc_rdbtnFiltrarUltims);
		filtres.add(rdbtnFiltrarUltims);
		
		//textField per filtrar els últims
		textUltims = new JTextField();
		GridBagConstraints gbc_textUltims = new GridBagConstraints();
		gbc_textUltims.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUltims.insets = new Insets(0, 0, 5, 5);
		gbc_textUltims.gridx = 1;
		gbc_textUltims.gridy = 3;
		contentPane.add(textUltims, gbc_textUltims);
		textUltims.setColumns(10);
		
		//etiqueta últims
		JLabel lblUltims = new JLabel("últims");
		GridBagConstraints gbc_lblUltims = new GridBagConstraints();
		gbc_lblUltims.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblUltims.insets = new Insets(0, 0, 5, 5);
		gbc_lblUltims.gridwidth = 2;
		gbc_lblUltims.gridx = 2;
		gbc_lblUltims.gridy = 3;
		contentPane.add(lblUltims, gbc_lblUltims);
		
		//radioButton per filtrar per etiquetes
		JRadioButton rdbtnFiltrarEtiquetes = new JRadioButton("Filtrar per etiquetes:");
		GridBagConstraints gbc_rdbtnFiltrarEtiquetes = new GridBagConstraints();
		gbc_rdbtnFiltrarEtiquetes.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnFiltrarEtiquetes.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnFiltrarEtiquetes.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFiltrarEtiquetes.gridwidth = 2;
		gbc_rdbtnFiltrarEtiquetes.gridx = 0;
		gbc_rdbtnFiltrarEtiquetes.gridy = 4;
		contentPane.add(rdbtnFiltrarEtiquetes, gbc_rdbtnFiltrarEtiquetes);
		filtres.add(rdbtnFiltrarEtiquetes);
		
		//comboBox per filtrar per etiquetes
		cbEtiquetes = new JComboBox<>(etiquetes);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 4;
		contentPane.add(cbEtiquetes, gbc_comboBox);
		cbEtiquetes.setSelectedItem("- Sel·lecciona una etiqueta -");
		
		//radioButton per no aplicar cap filtre
		JRadioButton rdbtnSenseFiltres = new JRadioButton("Sense filtres", true);
		GridBagConstraints gbc_rdbtnSenseFiltres = new GridBagConstraints();
		gbc_rdbtnSenseFiltres.anchor = GridBagConstraints.NORTH;
		gbc_rdbtnSenseFiltres.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnSenseFiltres.gridx = 6;
		gbc_rdbtnSenseFiltres.gridy = 2;
		contentPane.add(rdbtnSenseFiltres, gbc_rdbtnSenseFiltres);
		filtres.add(rdbtnSenseFiltres);
		
		//botó per aplicar el filtre
		JButton btnAplicarFiltre = new JButton("Aplicar filtre");
		btnAplicarFiltre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rdbtnFiltrarPrimers.isSelected()) {
					filtrarPrimers();
				}
				else if (rdbtnFiltrarUltims.isSelected()) {
					filtrarUltims();
				}
				else if (rdbtnFiltrarEtiquetes.isSelected()) {
					filtrarEtiquetes();
				}
				else {
					resultat = ctrl.consultarResultat();
					esborrarTaula();
					fillTable();
				}
			}
		});
		GridBagConstraints gbc_btnAplicarFiltre = new GridBagConstraints();
		gbc_btnAplicarFiltre.insets = new Insets(0, 0, 5, 0);
		gbc_btnAplicarFiltre.gridx = 6;
		gbc_btnAplicarFiltre.gridy = 4;
		contentPane.add(btnAplicarFiltre, gbc_btnAplicarFiltre);
		
		//separador
		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.anchor = GridBagConstraints.NORTH;
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridwidth = 7;
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 5;
		contentPane.add(separator_1, gbc_separator_1);
		
		//scrollPane
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 7;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		//table
		model = new DefaultTableModel(new Object[][]{}, columnes);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBackground(SystemColor.menu);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel cModel = table.getColumnModel();
		cModel.getColumn(0).setPreferredWidth(80);
		cModel.getColumn(1).setCellRenderer(centerRenderer);
		cModel.getColumn(1).setPreferredWidth(10);
		cModel.getColumn(2).setPreferredWidth(100);
		cModel.getColumn(3).setPreferredWidth(50);
		cModel.getColumn(4).setPreferredWidth(50);
		table.setDefaultRenderer(String.class, centerRenderer);
		fillTable();
		
		//botó per afegir una dada
		JButton btnAfegirDada = new JButton("Afegir dada");
		btnAfegirDada.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TipusDada tipus = ctrl.getTipusNode();
				AfegirResultat frame = new AfegirResultat(ctrl, tipus);
				frame.setVisible(true);
				setEnabled(false);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						setEnabled(true);
						setVisible(true);
						Pair<Double, Pair<Integer, String>> nouRes = frame.getNouResultat();
						if (nouRes.getValue().getKey() != -1) {
							resultat = ctrl.consultarResultat();
							double rellevancia = nouRes.getKey();
							String[] fila = createRow(nouRes.getValue().getValue(), rellevancia);
							int index = getIndex(rellevancia, 0, resultat.size()-1);
							model.insertRow(index, fila);
						}
					}
				});
			}
		});
		GridBagConstraints gbc_btnAfegirDada = new GridBagConstraints();
		gbc_btnAfegirDada.anchor = GridBagConstraints.NORTH;
		gbc_btnAfegirDada.insets = new Insets(0, 0, 5, 0);
		gbc_btnAfegirDada.gridx = 6;
		gbc_btnAfegirDada.gridy = 6;
		contentPane.add(btnAfegirDada, gbc_btnAfegirDada);
		
		//informació addicional
		@SuppressWarnings("serial")
		Action showAddInfo = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());
				int id = resultat.get(row).getValue().getKey();
				TipusDada tipus = ctrl.getTipusNode();
				InformacioAddicional frame = new InformacioAddicional(ctrl, id, tipus);
				frame.setVisible(true);
				ref.setEnabled(false);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						ref.setEnabled(true);
						ref.setVisible(true);
					}
				});
			}
		};
		
		new ButtonColumn(table, showAddInfo, 2);
		
		//modificar dada
		@SuppressWarnings("serial")
		Action modificar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());
				TipusDada tipus = ctrl.getTipusNode();
				
				int id = resultat.get(row).getValue().getKey();
				String dada = resultat.get(row).getValue().getValue();
				double rellevancia = resultat.get(row).getKey();
				
				ModificarResultat frame = new ModificarResultat(ctrl, tipus, id, dada, rellevancia, row);
				frame.setVisible(true);
				ref.setEnabled(false);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						ref.setEnabled(true);
						ref.setVisible(true);
						resultat = ctrl.consultarResultat();
						
						if (frame.getNouId() != id)  model.setValueAt(frame.getNovaDada(), row, 0);
						
						double novaRellevancia = frame.getNovaRellevancia();
						if (novaRellevancia != rellevancia) {
							model.setValueAt(df.format(novaRellevancia), row, 1);
							model.moveRow(row, row, getIndex(novaRellevancia, 0, resultat.size()-1));
						}
					}
				});
			}
		};
		
		new ButtonColumn(table, modificar, 3);
		
		//esborrar dada
		@SuppressWarnings("serial")
		Action esborrar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(contentPane, "Segur que vols esborrar?", "Esborrar resultat", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (option == JOptionPane.OK_OPTION) {
					int row = Integer.valueOf(e.getActionCommand());
					ctrl.esborrar(row);
					resultat.remove(row);
					model.removeRow(row);
				}
			}
		};
		
		new ButtonColumn(table, esborrar, 4);
	}
	
	
	/**
	 * Crea una fila de la taula table amb els valors indicats (però no afegeix la fila a la taula).
	 * @param nom. El nom de la dada del resultat que volem afegir a la taula.
	 * @param rellevancia. La rellevància del resultat que volem afegir a la taula.
	 * @return un vector que representa una fila de table, amb el nom de la dada i la rellevància
	 * indicats i els botons d'informació addicional, de modificar el resultat i d'esborrar el resultat.
	 */
	private String[] createRow(String nom, double rellevancia) {
		String[] fila = new String[5];
		fila[0] = nom;
		fila[1] = df.format(rellevancia);
		fila[2] = "Informació addicional";
		fila[3] = "Modificar";
		fila[4] = "Esborrar";
		return fila;
	}
	
	/**
	 * Afegeix a la taula table tots els elements de resultat.
	 * Per cada element s'afegeix una fila amb el nom de la dada, la rellevància
	 * i els botons d'informació addicional, de modificar el resultat i d'esborrar el resultat.
	 */
	private void fillTable() {
		for(Entry<Double, Entry<Integer, String>> r : resultat) {
			String[] fila = createRow(r.getValue().getValue(), r.getKey());
			model.addRow(fila);
		}
	}
	
	/**
	 * Esborra totes les files de la taula table, i la deixa buida.
	 */
	private void esborrarTaula() {
		while (model.getRowCount() != 0) model.removeRow(0);
	}
	
	/**
	 * Métode per mostrar només els n primers resultats, on n és un nombre introduït
	 * per l'usuari a textPrimers.
	 */
	private void filtrarPrimers() {
		try {
			int n = Integer.parseInt(textPrimers.getText());
			resultat = ctrl.filtrarElsPrimers(n, false);
			esborrarTaula();
			fillTable();
		}
		catch (Exception e) {
			new ErrorMessage(contentPane, "Introdueix un nombre entre 0 i "
											+ resultat.size()
											+ " al camp sel·lecionat.");
		}
	}
	
	/**
	 * Métode per mostrar només els n últims resultats, on n és un nombre introduït
	 * per l'usuari a textUltims.
	 */
	private void filtrarUltims() {
		try {
			int n = Integer.parseInt(textUltims.getText());
			resultat = ctrl.filtrarElsUltims(n, false);
			esborrarTaula();
			fillTable();
		}
		catch (Exception e) {
			new ErrorMessage(contentPane, "Introdueix un nombre entre 0 i "
											+ resultat.size()
											+ " al camp sel·lecionat.");
		}
	}
	
	/**
	 * Métode per mostrar només els resultats la dada dels quals té l'etiqueta indicada
	 * per l'usuari a cbEtiquetes.
	 */
	private void filtrarEtiquetes() {
		String label = cbEtiquetes.getSelectedItem().toString();
		if (label == "- Sel·lecciona una etiqueta -") new ErrorMessage(contentPane, "Sel·lecciona una etiqueta.");
		else {
			resultat = ctrl.filtrarPerEtiqueta (label, false);
			esborrarTaula();
			fillTable();
		}
	}
	
	/**
	 * Cerca dicotòmica per obtenir una fila de table on espot d'insertar un resultat concret
	 * per tal que la taula segueixi ordenada decreixentment per la rellevància dels resultats.
	 * @param rellevancia. La rellevància del resultat que volem insertar a la taula.
	 * @return l'índex d'una fila on es pot insertar un resultat amb la rellevància indicada
	 * 			(totes les files amb índexs inferiors tenen rellevàncies iguals o superiors
	 * 			i totes les files amb índexs superiors tenen rellevàncies iguals o inferiors).
	 */
	private int getIndex(double rellevancia, int left, int right) {
		int m = (left+right)/2;
		if (right < left) return left;
		double rell = resultat.get(m).getKey();
		if (rell == rellevancia) return m;
		if(rell > rellevancia) return getIndex(rellevancia, m+1, right);
		return getIndex(rellevancia, left, m-1);
	}
}
