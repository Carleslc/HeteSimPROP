package presentacio;

import java.awt.EventQueue;

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

public class GestorRelacions extends JFrame {

	private static final long serialVersionUID = -1153947409010750568L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio ctrl = new ControladorPresentacio();
					ctrl.afegir("AP", "Autoria");
					ctrl.afegir("CPT", "Tem�tica de confer�ncia");
					ctrl.afegir("APA", "Co-autoria");
					ctrl.afegir("APC", "Conferenciant");
					ctrl.afegir("APT", "Expert");
					GestorRelacions frame = new GestorRelacions(ctrl);
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
	public GestorRelacions(ControladorPresentacio ctrl) {
		config();
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		String[] names = new String[] {"Relaci�", "Descripci�", "", ""};
		DefaultTableModel model = new DefaultTableModel(new Object[][]{}, names);
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setBackground(SystemColor.menu);
		
		List<String> paths = ctrl.consultarPaths();
		for(String s : paths) {
			String[] fila = new String[4];
			fila[0] = s.substring(0, s.indexOf(':'));
			fila[1] = s.substring(s.indexOf(':')+2, s.length());
			fila[2] = "Modificar descripci�";
			fila[3] = "Esborrar";
			model.addRow(fila);
		}
		
		@SuppressWarnings("serial")
		Action esborrar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(contentPane, "Segur que vols esborrar?", "Esborrar relaci�", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (option == JOptionPane.OK_OPTION) {
					int row = Integer.valueOf(e.getActionCommand());
					String path = (String)model.getValueAt(row, 0);
					ctrl.esborrar(path);
					model.removeRow(row);
				}
			}
		};
		
		@SuppressWarnings("unused")
		ButtonColumn buttonColumnEsborrar = new ButtonColumn(table, esborrar, 3);
		
		@SuppressWarnings("serial")
		Action modificar = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int row = Integer.valueOf(e.getActionCommand());
				String path = model.getValueAt(row, 0).toString();
				ModificarDescripcio frame = new ModificarDescripcio(ctrl, path);
				frame.setVisible(true);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						model.setValueAt(ctrl.consultarDefinicio(path), row, 1);
					}
				});
			}
		};
		
		@SuppressWarnings("unused")
		ButtonColumn buttonColumnModificar = new ButtonColumn(table, modificar, 2);
		
		JButton btnNewButton = new JButton("Afegir relaci�");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AfegirRelacio frame = new AfegirRelacio(ctrl);
				frame.setVisible(true);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						String path = frame.getNewPath();
						String description = frame.getNewDescription();
						if (path != null) {
							String[] fila = new String[4];
							fila[0] = path;
							fila[1] = description;
							fila[2] = "Modificar descripci�";
							fila[3] = "Esborrar";
							model.addRow(fila);
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
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}
	
	private void config() {
		setTitle("Gestor de relacions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{290, 60, 290, 0};
		gbl_contentPane.rowHeights = new int[]{214, 30, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
	}
}
