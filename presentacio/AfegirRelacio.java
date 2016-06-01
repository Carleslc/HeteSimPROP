package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Vista per afegir una nova relaci\u00F3 al programa.
 * @author Carla Claverol
 *
 */

public class AfegirRelacio extends JFrame {

	private static final long serialVersionUID = 4110536141701497895L;
	private JPanel contentPane;
	private String newPath;
	private String newDescription;

	/**
	 * Constructor.
	 * @param ctrl. El ControladorPresentacio del programa.
	 */
	public AfegirRelacio(ControladorPresentacio ctrl) {
		setTitle("Afegir relaci\u00F3");
		setIconImage(ControladorPresentacio.ICON_ADD);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 160);
		
		//contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//textField pel nom de la relaci\u00F3
		JTextField textPath = new JTextField();
		textPath.setColumns(10);
		
		//textField per la descripci\u00F3 de la relaci\u00F3
		JTextField textDescription = new JTextField();
		textDescription.setColumns(10);
		
		//etiqueta relaci\u00F3
		JLabel lblPath = new JLabel("Relaci\u00F3:");
		lblPath.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//etiqueta descripci\u00F3
		JLabel lblDescription = new JLabel("Descripci\u00F3:");
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//bot\u00F3 per acceptar
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String path = textPath.getText().trim();
				String description = textDescription.getText();
				boolean b = ctrl.afegir(path.toUpperCase(), description);
				if (!b) new ErrorMessage(contentPane, "Path incorrecte o repetit!");
				else {
					newPath = path;
					newDescription = description;
					dispose();
				}
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
					.addComponent(lblPath, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textPath, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addComponent(lblDescription, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textDescription, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addGap(69)
					.addComponent(btnAccept, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(8)
							.addComponent(lblPath))
						.addComponent(textPath, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(8)
							.addComponent(lblDescription))
						.addComponent(textDescription, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancel)
						.addComponent(btnAccept)))
		);
		contentPane.setLayout(gl_contentPane);
		setResizable(false);
	}
	
	/**
	 * Consultora del nom de la relaci\u00F3 afegida.
	 * @return el nom de la relaci\u00F3 afegida, o null si no s'ha afegit cap relaci\u00F3.
	 */
	public String getNewPath() {
		return newPath;
	}
	
	/**
	 * Consultora de la descripci\u00F3 de la relaci\u00F3 afegida.
	 * @return la descripci\u00F3 de la relaci\u00F3 afegida, o null si no s'ha afegit cap relaci\u00F3.
	 */
	public String getNewDescription() {
		return newDescription;
	}
}
