package presentacio;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JTable;
import java.awt.GridBagConstraints;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.SystemColor;
import javax.swing.JScrollPane;

/**
 * Vista per gestionar les relacions del programa. Permet veure les relacions existents,
 * esborrar-ne, modificar les seves descripcions i afegir noves relacions.
 * @author Carla Claverol
 *
 */

public class GestorRelacions extends JFrame {

	private static final long serialVersionUID = -1153947409010750568L;
	private JPanel contentPane;
	private DefaultTableModel model;
	private JTable table;
	private static final String[] columnes = {"Relació", "Descripció", "", ""};

	/**
	 * Constructor.
	 * @param ctrl. El ControladorPresentacio del programa.
	 */
	public GestorRelacions(ControladorPresentacio ctrl) {
		setTitle("Gestor de relacions");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JFrame ref = this;

		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{290, 60, 290, 0};
		gbl_contentPane.rowHeights = new int[]{214, 30, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		//scrollPane
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		//table
		model = new DefaultTableModel(new Object[][]{}, columnes);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBackground(SystemColor.menu);
		
		//fill table
		List<String> paths = ctrl.consultarPaths();
		for(String s : paths) {
			addRow(s, ctrl.consultarDefinicio(s));
		}
		
		//esborrar relació
		@SuppressWarnings("serial")
		Action esborrar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(contentPane, "Segur que vols esborrar?", "Esborrar relació", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (option == JOptionPane.OK_OPTION) {
					int row = Integer.valueOf(e.getActionCommand());
					String path = (String)model.getValueAt(row, 0);
					ctrl.esborrar(path.toUpperCase());
					model.removeRow(row);
				}
			}
		};
		@SuppressWarnings("unused")
		ButtonColumn buttonColumnEsborrar = new ButtonColumn(table, esborrar, 3);
		
		//modificar descripció de la relació
		@SuppressWarnings("serial")
		Action modificar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());
				String path = model.getValueAt(row, 0).toString();
				ModificarDescripcio frame = new ModificarDescripcio(ctrl, path);
				frame.setVisible(true);
				ref.setEnabled(false);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						ref.setEnabled(true);
						ref.setVisible(true);
						model.setValueAt(ctrl.consultarDefinicio(path), row, 1);
					}
				});
			}
		};
		@SuppressWarnings("unused")
		ButtonColumn buttonColumnModificar = new ButtonColumn(table, modificar, 2);
		
		//afegir relació
		JButton btnAfegir = new JButton("Afegir relació");
		btnAfegir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AfegirRelacio frame = new AfegirRelacio(ctrl);
				frame.setVisible(true);
				ref.setEnabled(false);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						ref.setEnabled(true);
						ref.setVisible(true);
						String path = frame.getNewPath();
						String description = frame.getNewDescription();
						if (path != null) {
							addRow(path, description);
						}
					}
				});
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 1;
		contentPane.add(btnAfegir, gbc_btnNewButton);
	}

	
	/**
	 * Afegeix una fila a la taula table amb el nom i la descripció d'una relació.
	 * @param path. El nom de la relació que volem afegir a la taula.
	 * @param description. La descripció de la relació que volem afegir a la taula.
	 */
	private void addRow(String path, String description) {
		String[] fila = new String[4];
		fila[0] = path;
		fila[1] = description;
		fila[2] = "Modificar descripció";
		fila[3] = "Esborrar";
		model.addRow(fila);
	}
}
