package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = -9206328603791933807L;
	
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio ctrl = new ControladorPresentacio();
					MenuPrincipal frame = new MenuPrincipal(ctrl);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal(ControladorPresentacio ctrl) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		contentPane.add(btnNewButton_2);
		
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
	
		JComboBox<String> comboBox = ctrl.getSelectorConjunts().newSelector();
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					btnNewButton.setEnabled(false);
				else
					btnNewButton.setEnabled(true);
			}
		});
		comboBox.setToolTipText("Selecciona un conjunt de dades");
		comboBox.setBounds(322, 6, 137, 27);
		contentPane.add(comboBox);
		

		//Gestio de dades button
		JButton btnNewButton_1 = new JButton("Gestió de Dades");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorDades frame2 = new GestorDades(ctrl);
				frame2.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(133, 106, 166, 47);
		contentPane.add(btnNewButton_1);
	}
}
