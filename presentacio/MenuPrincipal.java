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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
					MenuPrincipal2 frame = new MenuPrincipal2(ctrl);
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
	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ctrl = ctrl;
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Vols guardar les dades abans de sortir?", "Guardar dades", JOptionPane.YES_NO_CANCEL_OPTION);
				switch (opt) {
				case JOptionPane.YES_OPTION:
					try {
						ctrl.guardarDades();
						dispose();
					} catch (IOException e5) {
						new ErrorMessage(e5.getMessage());
					}
					break;
				case JOptionPane.NO_OPTION:
					dispose();
					break;
				}
			}
		});
		setBounds(100, 100, 465, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
	
		
		//guardar button
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
		
		//Consulta button
		JButton btnNewButton = new JButton("Consulta");
		btnNewButton.setBounds(133, 47, 166, 47);
		btnNewButton.setEnabled(false);
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);
		
		
		
		//About button
		JButton btnNewButton_3 = new JButton("About");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Grup 12.2\n" + "Carla Claverol González\n" + "Guillem Castro Olivares\n" + "Carlos Lázaro Costa\n" + "Arnau Badia Sampera\n");
			}
		});
		btnNewButton_3.setBounds(16, 231, 66, 29);
		contentPane.add(btnNewButton_3);
		
		//proves
		try {
			ctrl.afegirGraf("ei2");
			ctrl.afegirGraf("ei3");
			ctrl.afegirGraf("ei6");
			ctrl.afegirGraf("ei7");		
			ctrl.afegirGraf("ei");
		}
		catch(IOException e) {}
			
	

		final SeleccionarConjuntDeDades comboBox = new SeleccionarConjuntDeDades(ctrl.controladorMultigraf);
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ctrl.seleccionarGraf((String)comboBox.getSelectedItem());
				} catch (IOException e1) {
					new ErrorMessage(e1.getMessage());
				}
			}
		});
	
		comboBox.setToolTipText("");
		comboBox.setBounds(322, 6, 137, 27);
		contentPane.add(comboBox);
		

		//Gestio de dades button
		JButton btnNewButton_1 = new JButton("Gestió de Dades");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorDades frame2 = new GestorDades(ctrl, comboBox);
				frame2.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(133, 106, 166, 47);
		contentPane.add(btnNewButton_1);
		contentPane.add(btnNewButton_2);
	}
}
