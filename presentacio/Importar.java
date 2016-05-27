package presentacio;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public class Importar extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private String selDir;
	private JTextField textField_1;



	/**
	 * Create the frame.
	 */
	public Importar(ControladorPresentacioDomini ctrl, SeleccionarConjuntDeDades sd) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(181, 109, 194, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Escollir directori amb dades d'importació");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selDir = selectedDir();
				textField_1.setText(selDir);
			}
		});
		
		
		
		btnNewButton.setBounds(86, 35, 289, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNomDelGraf = new JLabel("Nom del graf:");
		lblNomDelGraf.setBounds(86, 115, 106, 16);
		contentPane.add(lblNomDelGraf);
		
		JButton btnCancellar = new JButton("Cancel·lar");
		btnCancellar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancellar.setBounds(75, 197, 117, 29);
		contentPane.add(btnCancellar);
		
		JButton btnAcceptar = new JButton("Importa graf");
		btnAcceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String error = "";

				File f = new File(textField_1.getText());
				if (!f.exists() || !f.isDirectory()) { 
					error += "El directori no existeix.\n";
				}

				if (error != "") {
					new ErrorMessage("No s'ha seleccionat un directori correcte.");
				}

				try {
					ctrl.importar(textField.getText(), textField_1.getText());
					sd.update();
				} catch (IOException e1) {
					new ErrorMessage("Error a l'importar el graf");
				}
			}
		});
		btnAcceptar.setBounds(232, 197, 143, 29);
		contentPane.add(btnAcceptar);
		
		textField_1 = new JTextField();
		textField_1.setBounds(181, 69, 194, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDirectori = new JLabel("Directori:");
		lblDirectori.setBounds(86, 76, 106, 16);
		contentPane.add(lblDirectori);
	}
		
	private String selectedDir() {
	    JFileChooser fc = new JFileChooser();
	    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    fc.setCurrentDirectory(new File(System.getProperty("user.home")));
	    int ret = fc.showOpenDialog(new JPanel());
	    if (ret == JFileChooser.APPROVE_OPTION) {
	      return fc.getSelectedFile().getPath();
	    } 
	    else return null;
	  }
}
		
		
