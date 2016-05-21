package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class GestorDades extends JFrame {

	private static final long serialVersionUID = 2697285126957563652L;
	private JPanel contentPane;

	public GestorDades(ControladorPresentacioDomini ctrl) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Gestor de conjunts de dades");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorConjuntDades frame = new GestorConjuntDades(ctrl);
				frame.setVisible(true);
				setVisible(false);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						setVisible(true);
					}
				});
			}
		});
		btnNewButton.setBounds(102, 60, 223, 35);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Gestor de relacions");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorRelacions frame = new GestorRelacions(ctrl);
				frame.setVisible(true);
				setVisible(false);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosed(WindowEvent e) {
						setVisible(true);
					}
				});
			}
		});
		btnNewButton_1.setBounds(102, 143, 223, 35);
		contentPane.add(btnNewButton_1);
	}
}
