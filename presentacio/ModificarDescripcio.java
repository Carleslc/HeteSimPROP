package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Vista per modificar la descripci\u00F3 d'una de les relacions del programa.
 * @author Carla Claverol
 *
 */

public class ModificarDescripcio extends JFrame {

	private static final long serialVersionUID = 8426179096403489103L;
	private JPanel contentPane;

	/**
	 * Constructor.
	 * @param ctrl. El ControladorPresentacio del programa.
	 * @param path. El nom de la relaci\u00F3 la descripci\u00F3 de la qual volem modificar.
	 */
	public ModificarDescripcio(ControladorPresentacio ctrl, String path) {
		setTitle("Modificar descripci\u00F3");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 150);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//textField
		JTextField textDescription = new JTextField();
		textDescription.setColumns(10);
		
		//etiqueta
		JLabel lblDescription = new JLabel("Escriu la nova descripci\u00F3:");
		
		//bot\u00F3 per acceptar
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String definicio = textDescription.getText().toString();
				ctrl.modificarDefinicio(path, definicio);
				dispose();
			}
		});

		//bot\u00F3 per cancel\u00B7lar
		JButton btnCancel = new JButton("Cancel\u00B7lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		
		//layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(lblDescription, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(4)
					.addComponent(textDescription, GroupLayout.PREFERRED_SIZE, 341, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(14)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addGap(129)
					.addComponent(btnAccept, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblDescription, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(textDescription, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancel)
						.addComponent(btnAccept)))
		);
		contentPane.setLayout(gl_contentPane);
		setResizable(false);
	}
}
