package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class GestorDades extends JFrame {

	private static final long serialVersionUID = 2697285126957563652L;
	private JPanel contentPane;
	
	public GestorDades(ControladorPresentacio ctrl) {
		config();
		
		JButton btnGestorConjDades = new JButton("Gestor de conjunts de dades");
		btnGestorConjDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorConjuntDades frame = new GestorConjuntDades(ctrl);
				configurarNovaFinestra(frame);
			}
		});
		
		JButton btnGestorRelacions = new JButton("Gestor de relacions");
		btnGestorRelacions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorRelacions frame = new GestorRelacions(ctrl);
				configurarNovaFinestra(frame);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnGestorRelacions, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnGestorConjDades, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnGestorConjDades, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnGestorRelacions, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setResizable(false);
	}
	
	private void config() {
		setTitle("Gestor de dades");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 235, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
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
