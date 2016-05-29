package presentacio;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

/**
 * Aquesta vista permet seleccionar una dada a partir del seu nom (que pot estar repetit)
 * @author Guillem Castro
 *
 */

public class SeleccionarDada extends JFrame {

	private static final long serialVersionUID = -5536518442887894820L;

	private JPanel contentPane;
	private ControladorPresentacio cntrl;
	private String nomDada;
	private String tipus;
	private ArrayList<Integer> resultats;
	private Integer seleccio;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Constructor
	 * @param cntrl ControladorPresentacioDomini del programa
	 * @param nomDada nom de la dada a seleccionar
	 * @param tipus tipus de la dada a seleccionar
	 */
	public SeleccionarDada(ControladorPresentacio cntrl, String nomDada, String tipus) {
		this.cntrl = cntrl;
		this.nomDada = nomDada;
		this.tipus = tipus;
		this.seleccio = -1;
		consultarDada();

		setTitle("Seleccionar Dada");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		int size = (resultats!=null)?resultats.size():0;

		JLabel lblNewLabel = new JLabel("S'han trobat " + size + " dades amb aquest nom, selecciona la que desitgis:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		configurarTable();

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(table);

		JButton btnAcceptar = new JButton("Acceptar");
		GridBagConstraints gbc_btnAcceptar = new GridBagConstraints();
		gbc_btnAcceptar.gridx = 0;
		gbc_btnAcceptar.gridy = 2;
		contentPane.add(btnAcceptar, gbc_btnAcceptar);

		btnAcceptar.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				dispose();
			}

		});

	}
	/**
	 * Consulta el resultat de la selecci� de dades
	 * @return el ID de la dada seleccionada, -1 si cap dada ha sigut seleccionada
	 */
	public Integer getResultat() {
		return seleccio;
	}

	private void configurarTable() {
		String[] colnames = {"ID", "Nom", "Informaci� Adicional"};

		if (resultats != null) {
			String[][] data = new String[resultats.size()][3];
			for (int i = 0; i < resultats.size(); ++i) {
				data[i][0] = String.valueOf(resultats.get(i));
				data[i][1] = nomDada;
				data[i][2] = "Informaci� Adicional";
			}
			tableModel = new DefaultTableModel(data, colnames);
		}

		else {
			tableModel = new DefaultTableModel(colnames, 0);
		}

		table = new JTable(tableModel);
		table.setRowHeight(20);

		Action action = new AbstractAction() {
			private static final long serialVersionUID = -4401620008910150712L;

			public void actionPerformed(ActionEvent e)
			{
				System.out.println("tipus: " + tipus.toLowerCase());
				InformacioAddicional ia = new InformacioAddicional(cntrl, seleccio, tipus.toLowerCase());
				ia.setVisible(true);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, action, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		ListSelectionModel ls = table.getSelectionModel();
		ls.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println("LS");
				ListSelectionModel ls = (ListSelectionModel) e.getSource();
				int row = -1;
				int minIndex = ls.getMinSelectionIndex();
				int maxIndex = ls.getMaxSelectionIndex();
				for (int i = minIndex; i <= maxIndex; i++) {
					if (ls.isSelectedIndex(i)) {
						row = i;
					}
				}

				System.out.println(row);
				seleccio = Integer.valueOf((String) tableModel.getValueAt(row, 0));

			}

		});
	}

	private void consultarDada() {
		switch(tipus) {
		case "Autor":
			resultats = (ArrayList<Integer>) cntrl.consultarAutor(nomDada);
			break;
		case "Conferencia":
			resultats = (ArrayList<Integer>) cntrl.consultarConferencia(nomDada);
			break;
		case "Paper":
			resultats = (ArrayList<Integer>) cntrl.consultarPaper(nomDada);
			break;
		case "Terme":
			resultats = (ArrayList<Integer>) cntrl.consultarTerme(nomDada);
			break;
		default:
			resultats = null;
		}
	}
}
