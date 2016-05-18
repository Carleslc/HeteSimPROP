package presentacio;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 * Aquesta vista permet seleccionar una dada a partir del seu nom (que pot estar repetit)
 * @author Guillem Castro
 *
 */

public class SeleccionarDada extends JFrame {

	private JPanel contentPane;
	private ControladorPresentacioDomini cntrl;
	private String nomDada;
	private String tipus;
	private ArrayList<Integer> resultats;
	private ArrayList<Integer> seleccions;
	private JTable table;
	private DefaultTableModel tableModel;
	

	/**
	 * 
	 * @param cntrl ControladorPresentacioDomini del programa
	 * @param nomDada nom de la dada a seleccionar
	 * @param tipus tipus de la dada a seleccionar
	 * @param id Llista dels ids valors que seran seleccionats, primer l'últim seleccionat (buida al principi, la vista s'encarrega d'omplirla)
	 */
	public SeleccionarDada(ControladorPresentacioDomini cntrl, String nomDada, String tipus, ArrayList<Integer> seleccions) {
		this.cntrl = cntrl;
		this.nomDada = nomDada;
		this.tipus = tipus;
		this.seleccions = seleccions;
		consultarDada();
		
		setTitle("Seleccionar Dada");
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
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		configurarTable();
		
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
	
	private void configurarTable() {
		String[] colnames = {"ID", "Nom", "Informació Adicional"};
		
		if (resultats != null) {
			String[][] data = new String[resultats.size()][3];
			for (int i = 0; i < resultats.size(); ++i) {
				data[i][0] = String.valueOf(resultats.get(i));
				data[i][1] = nomDada;
				data[i][2] = "Informació Adicional";
			}
			tableModel = new DefaultTableModel(data, colnames);
		}
		
		else {
			tableModel = new DefaultTableModel(colnames, 0);
		}
		
		table.setModel(tableModel);
		TableColumn col = table.getColumnModel().getColumn(0);
        
		ListSelectionModel ls = table.getSelectionModel();
		ls.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
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
				seleccions.add(0, Integer.valueOf((String) tableModel.getValueAt(row, 0)));
				
			}
			
		});
        
        Action info = new AbstractAction() {
		    public void actionPerformed(ActionEvent e)
		    {
		        
		    }
		};
		 
		ButtonColumn buttonColumn = new ButtonColumn(table, info, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
	}
	
	private void consultarDada() {
		switch(tipus) {
		case "Autor":
			resultats = (ArrayList<Integer>) cntrl.consultarAutor(nomDada);
			break;
		case "Conferencia":
			resultats = (ArrayList<Integer>) cntrl.consultarConferencia(nomDada);
		case "Paper":
			resultats = (ArrayList<Integer>) cntrl.consultarPaper(nomDada);
		case "Terme":
			resultats = (ArrayList<Integer>) cntrl.consultarTerme(nomDada);
		}
	}
	

}
