package presentacio;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GestorConjuntDades extends JFrame {

	private static final long serialVersionUID = 2784181985614807193L;
	private JPanel contentPane;

	public GestorConjuntDades(ControladorPresentacio ctrl) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//crear button
		JButton btnNewButton = new JButton("Crear nou Conjunt de Dades");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String s = JOptionPane.showInputDialog(btnNewButton, "Crear nou Conjunt");
				try {
					ctrl.afegirGraf(s);
					ctrl.getSelectorConjunts().update();
				} catch(FileNotFoundException ignore) {
				} catch(IOException ex) {
					new ErrorMessage(ex.getMessage());
				}
			}
		});
		btnNewButton.setBounds(116, 48, 229, 60);
		contentPane.add(btnNewButton);
		
		//modificar button
		JButton btnNewButton_1 = new JButton("Modificar Conjunt de Dades");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModificarConjuntDades x = new ModificarConjuntDades(ctrl);
				x.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(116, 115, 229, 60);
		contentPane.add(btnNewButton_1);
		
		//importar
		JButton btnNewButton_2 = new JButton("Importar Conjunt de Dades");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Importar x = new Importar(ctrl);
				x.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(116, 187, 229, 53);
		contentPane.add(btnNewButton_2);
	}
}
