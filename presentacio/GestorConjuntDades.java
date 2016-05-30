package presentacio;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GestorConjuntDades extends JFrame {

	private static final long serialVersionUID = 2784181985614807193L;
	private JPanel contentPane;

	public GestorConjuntDades(ControladorPresentacio ctrl) {
		GestorConjuntDades ref = this;
		setTitle("Gestor de conjunts");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 240, 196);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//crear button
		JButton btnNewButton = new JButton("Crear nou Conjunt de Dades");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String s = (String)JOptionPane.showInputDialog(null,
						"Escriu el nom del nou conjunt", "Nou conjunt de dades",
						JOptionPane.OK_CANCEL_OPTION,
						new ImageIcon(ControladorPresentacio.ICON_DISK),
						null, null);
				if (s != null) {
					if (s.isEmpty()) {
						new ErrorMessage("El nom no pot ser buit!");
						mouseClicked(e);
					}
					else {
						try {
							ctrl.afegirGraf(s);
							ctrl.getSelectorConjunts().update();
						} catch(FileNotFoundException ignore) {
						} catch(IOException ex) {
							new ErrorMessage(ex.getMessage());
						}
					}
				}
			}
		});
		btnNewButton.setBounds(10, 11, 215, 40);
		contentPane.add(btnNewButton);

		//modificar button
		JButton btnNewButton_1 = new JButton("Modificar Conjunt de Dades");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ControladorPresentacio.configurarNovaFinestra(ref, new ModificarConjuntDades(ctrl));
			}
		});
		btnNewButton_1.setBounds(10, 62, 215, 40);
		contentPane.add(btnNewButton_1);

		//importar
		JButton btnNewButton_2 = new JButton("Importar Conjunt de Dades");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ControladorPresentacio.configurarNovaFinestra(ref, new Importar(ctrl));
			}
		});
		btnNewButton_2.setBounds(10, 113, 215, 40);
		contentPane.add(btnNewButton_2);
	}
}
