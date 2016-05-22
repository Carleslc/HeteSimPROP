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
		config();
		
		JButton btnGestorConjDades = new JButton("Gestor de conjunts de dades");
		btnGestorConjDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorConjuntDades frame = new GestorConjuntDades();
				configurarNovaFinestra(frame);
			}
		});
		btnGestorConjDades.setBounds(102, 60, 223, 35);
		contentPane.add(btnGestorConjDades);
		
		JButton btnGestorRelacions = new JButton("Gestor de relacions");
		btnGestorRelacions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorRelacions frame = new GestorRelacions(ctrl);
				configurarNovaFinestra(frame);
			}
		});
		btnGestorRelacions.setBounds(102, 143, 223, 35);
		contentPane.add(btnGestorRelacions);
	}
	
	private void config() {
		setTitle("Gestor de dades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void configurarNovaFinestra(JFrame frame) {
		frame.setVisible(true);
		setVisible(false);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				setVisible(true);
			}
		});
	}
}
