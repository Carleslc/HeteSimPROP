package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AfegirRelacio extends JFrame {

	private static final long serialVersionUID = 4110536141701497895L;
	private JPanel contentPane;
	private String newPath;
	private String newDescription;

	public AfegirRelacio(ControladorPresentacio ctrl) {
		config();
		
		JTextField textPath = new JTextField();
		textPath.setBounds(192, 53, 110, 20);
		contentPane.add(textPath);
		textPath.setColumns(10);
		
		JTextField textDescription = new JTextField();
		textDescription.setBounds(192, 127, 110, 20);
		contentPane.add(textDescription);
		textDescription.setColumns(10);
		
		JLabel lblPath = new JLabel("Relaci�:");
		lblPath.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPath.setBounds(86, 56, 101, 14);
		contentPane.add(lblPath);
		
		JLabel lblDescription = new JLabel("Descripci�:");
		lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescription.setBounds(86, 130, 101, 14);
		contentPane.add(lblDescription);
		
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String path = textPath.getText().trim();
				String description = textDescription.getText();
				boolean b = ctrl.afegir(path.toUpperCase(), description);
				if (!b) JOptionPane.showMessageDialog(contentPane, "Error!\nPath incorrecte o repetit!");
				else {
					newPath = path;
					newDescription = description;
					dispose();
				}
			}
		});
		btnAccept.setBounds(281, 211, 101, 23);
		contentPane.add(btnAccept);
		
		JButton btnCancel = new JButton("Cancel�lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(46, 211, 101, 23);
		contentPane.add(btnCancel);
	}
	
	public String getNewPath() {
		return newPath;
	}
	
	public String getNewDescription() {
		return newDescription;
	}
	
	private void config() {
		setTitle("Afegir relaci�");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
