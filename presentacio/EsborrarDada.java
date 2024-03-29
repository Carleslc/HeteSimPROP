package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class EsborrarDada extends JFrame {

	private static final long serialVersionUID = -4984707809082087185L;
	private JPanel contentPane;
	private TipusDada selectedType;
	private Integer selectedID;
	private ControladorPresentacio cntrl;
	private DefaultTableModel tableModel;
	private static final String[] colnames = {"ID", "Nom", "Informaci\u00F3 Adicional"};
	private static final String[] tipus = {"Selecciona el tipus de dada", "Autor", "Conferencia", "Terme", "Paper"};
	private JTable table;

	public EsborrarDada(ControladorPresentacio cntrl) {
		this.cntrl = cntrl;
		final int height = 570;
		final int minWidth = 600;
		setMinimumSize(new Dimension(minWidth, height));
		setBounds(100, 100, minWidth, height);
		setTitle("Esborrar Dada");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{95, 50, 289, 0};
		gbl_contentPane.rowHeights = new int[]{20, 170, 41, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblTipus = new JLabel("Tipus:");
		GridBagConstraints gbc_lblTipus = new GridBagConstraints();
		gbc_lblTipus.anchor = GridBagConstraints.EAST;
		gbc_lblTipus.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipus.gridx = 0;
		gbc_lblTipus.gridy = 0;
		contentPane.add(lblTipus, gbc_lblTipus);

		JComboBox<String> llistatipus = new JComboBox<>(tipus);
		GridBagConstraints gbc_llistatipus = new GridBagConstraints();
		gbc_llistatipus.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		gbc_llistatipus.weightx = 1.0;
		gbc_llistatipus.anchor = GridBagConstraints.NORTH;
		gbc_llistatipus.fill = GridBagConstraints.HORIZONTAL;
		gbc_llistatipus.insets = new Insets(0, 0, 5, 0);
		gbc_llistatipus.gridx = 2;
		gbc_llistatipus.gridy = 0;
		contentPane.add(llistatipus, gbc_llistatipus);

		llistatipus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> src = (JComboBox<String>) e.getSource();
				if (!src.getSelectedItem().equals(tipus[0])) {
					selectedType = TipusDada.valueOf((String)src.getSelectedItem());
					table.setEnabled(true);
					buidarTable();
					omplirTable();
				}
			}

		});

		configurarTable();

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.anchor = GridBagConstraints.CENTER;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);

		scrollPane.setViewportView(table);

		JButton btnEsborrar = new JButton("Esborrar");
		btnEsborrar.setForeground(Color.BLACK);
		btnEsborrar.setBackground(Color.WHITE);
		btnEsborrar.setIcon(new ImageIcon(ControladorPresentacio.ICON_WARNING));
		GridBagConstraints gbc_btnEsborrar = new GridBagConstraints();
		gbc_btnEsborrar.insets = new Insets(0, 0, 5, 0);
		gbc_btnEsborrar.gridwidth = 9;
		gbc_btnEsborrar.fill = GridBagConstraints.BOTH;
		gbc_btnEsborrar.weightx = 1.0;
		gbc_btnEsborrar.gridx = 0;
		gbc_btnEsborrar.gridy = 8;
		contentPane.add(btnEsborrar, gbc_btnEsborrar);

		btnEsborrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedID != null && selectedType != null) {
					String[] opcions = {"Cancelar", "No", "S\u00ED"};
					int n = JOptionPane.showOptionDialog(e.getComponent(), "Est\u00E0s segur d'esborrar aquesta dada?", "Est\u00E0s segur d'esborrar aquesta dada?", 
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, opcions, opcions[1]);
					if (n == 2) {
						table.setEnabled(false);
						esborrarDada();
						buidarTable();
						omplirTable();
						table.setEnabled(true);
					}
				}
			}
		});

	}

	@SuppressWarnings("serial")
	private void configurarTable() {
		tableModel = new DefaultTableModel(colnames, 0);
		table = new JTable(tableModel);
		table.setRowHeight(20);
		table.setEnabled(false);
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				InformacioAddicional ia = new InformacioAddicional(cntrl, selectedID, selectedType);
				ia.setVisible(true);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, action, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		ListSelectionModel ls = table.getSelectionModel();
		ls.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (table.isEnabled()) {
					ListSelectionModel ls = (ListSelectionModel) e.getSource();
					int row = -1;
					int minIndex = ls.getMinSelectionIndex();
					int maxIndex = ls.getMaxSelectionIndex();
					for (int i = minIndex; i <= maxIndex; i++) {
						if (ls.isSelectedIndex(i)) {
							row = i;
						}
					}
					if (row < tableModel.getRowCount() && row >= 0)
						selectedID = Integer.valueOf((String) tableModel.getValueAt(row, 0));
				}
			}

		});
	}

	private void buidarTable() {
		table.setEnabled(false);
		for (int i = tableModel.getRowCount()-1; i >= 0; --i)
			tableModel.removeRow(i);
		table.setEnabled(true);
	}

	private void omplirTable() {

		TreeMap<Integer, String> data = null;

		switch (selectedType) {
		case Autor:
			data = cntrl.consultarAutors();
			break;
		case Conferencia:
			data = cntrl.consultarConferencies();
			break;
		case Paper:
			data = cntrl.consultarPapers();
			break;
		case Terme:
			data = cntrl.consultarTermes();
			break;
		}

		if (data != null) {
			for (Entry<Integer, String> i : data.entrySet()) {
				String[] row = {i.getKey().toString(), i.getValue(), "Informaci\u00F3 Adicional"};
				tableModel.addRow(row);
			}
		}
	}

	private void esborrarDada() {
		if (selectedID != null && selectedType != null) {
			try {
				switch (selectedType) {
				case Autor:
					cntrl.eliminarAutor(selectedID);
					break;
				case Conferencia:
					cntrl.eliminarConferencia(selectedID);
					break;
				case Paper:
					cntrl.eliminarPaper(selectedID);
					break;
				case Terme:
					cntrl.eliminarTerme(selectedID);
				}
			} catch (Exception e) {
				new ErrorMessage(e.getMessage());
			}
		}
	}
}
