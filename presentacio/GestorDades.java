package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class GestorDades extends JFrame {

	private static final long serialVersionUID = 2697285126957563652L;
	private JPanel contentPane;
	
	public GestorDades(ControladorPresentacio ctrl) {
		setTitle("Gestor de dades");
		setIconImage(ControladorPresentacio.ICON_MAIN);
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
		
		JButton btnGestorRelacions = new JButton("Gestor de relacions");
		btnGestorRelacions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestorRelacions frame = new GestorRelacions(ctrl);
				ControladorPresentacio.configurarNovaFinestra(ref, frame);
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
	

	private void content_pane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
}
