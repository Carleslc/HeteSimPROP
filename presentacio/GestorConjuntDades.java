package presentacio;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GestorConjuntDades extends JFrame {

	private JPanel contentPane;
	private ControladorPresentacioDomini ctrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestorConjuntDades frame = new GestorConjuntDades();
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
	public GestorConjuntDades() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Crear nou Conjunt de Dades");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String s = JOptionPane.showInputDialog(btnNewButton, "Crear nou Conjunt");
				try {
					ctrl.afegirGraf(s);
				}
				catch(IOException e1) {
					JOptionPane.showConfirmDialog(btnNewButton, e1.getMessage());
				}
			}
		});
		btnNewButton.setBounds(116, 48, 229, 60);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Modificar Conjunt de Dades");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InformacioAddicional x = new InformacioAddicional();
			}
		});
		btnNewButton_1.setBounds(116, 115, 229, 60);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Importar Conjunt de Dades");
		btnNewButton_2.setBounds(116, 187, 229, 53);
		contentPane.add(btnNewButton_2);
	}
}
