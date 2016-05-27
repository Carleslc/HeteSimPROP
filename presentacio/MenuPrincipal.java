package presentacio;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private ControladorPresentacioDomini ctrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacioDomini ctrl = new ControladorPresentacioDomini();
					MenuPrincipal frame = new MenuPrincipal(ctrl);
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
	public MenuPrincipal(ControladorPresentacioDomini ctrl) {
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JButton btnNewButton_2 = new JButton("Guardar");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ctrl.guardarDades();
				}
				catch(IOException e1){
					JOptionPane.showMessageDialog(contentPane, e1.getMessage());
				}
			}
		});
		btnNewButton_2.setBounds(133, 165, 166, 42);
		
		
		JButton btnNewButton = new JButton("Consulta");
		btnNewButton.setBounds(133, 47, 166, 47);
		
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Gestió de Dades");
		btnNewButton_1.setBounds(133, 106, 166, 47);
		contentPane.add(btnNewButton_1);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("About");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Grup 12.2\n" + "Carla Claverol González\n" + "Guillem Castro Olivares\n" + "Carlos Lázaro Costa\n" + "Arnau Badia Sampera\n");
			}
		});
		btnNewButton_3.setBounds(16, 231, 66, 29);
		contentPane.add(btnNewButton_3);
		
		JComboBox<String> comboBox = new SeleccionarConjuntDeDades(ctrl.controladorMultigraf);
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String s = (String)comboBox.getSelectedItem();
				System.out.println(s);
			}
		});
		comboBox.setToolTipText("");
		comboBox.setBounds(322, 6, 137, 27);
		contentPane.add(comboBox);
	}
}
