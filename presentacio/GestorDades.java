package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GestorDades extends JFrame {

	private static final long serialVersionUID = 2697285126957563652L;
	private JPanel contentPane;
	
	public GestorDades(ControladorPresentacio ctrl) {
		setTitle("Gestor de dades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		content_pane();
		JFrame ref = this;
		
		JButton btnGestorConjDades = new JButton("Gestor de conjunts de dades");
		btnGestorConjDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorConjuntDades frame = new GestorConjuntDades(ctrl);
				ControladorPresentacio.configurarNovaFinestra(ref, frame);
			}
		});
		btnGestorConjDades.setBounds(102, 60, 223, 35);
		contentPane.add(btnGestorConjDades);
		
		JButton btnGestorRelacions = new JButton("Gestor de relacions");
		btnGestorRelacions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorRelacions frame = new GestorRelacions(ctrl);
				ControladorPresentacio.configurarNovaFinestra(ref, frame);
			}
		});
		btnGestorRelacions.setBounds(102, 143, 223, 35);
		contentPane.add(btnGestorRelacions);
	}
	
	private void content_pane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
