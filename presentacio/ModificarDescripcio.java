package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModificarDescripcio extends JFrame {

	private static final long serialVersionUID = 8426179096403489103L;
	private JPanel contentPane;
	private JTextField textDescription;

	public ModificarDescripcio(ControladorPresentacio ctrl, String path) {
		config();
		
		textDescription = new JTextField();
		textDescription.setBounds(43, 117, 341, 20);
		contentPane.add(textDescription);
		textDescription.setColumns(10);
		
		JLabel lblDescription = new JLabel("Escriu la nova descripci�:");
		lblDescription.setBounds(43, 69, 341, 14);
		contentPane.add(lblDescription);
		
		JButton btnAccept = new JButton("Acceptar");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String definicio = getDefinicio();
				ctrl.modificarDefinicio(path, definicio);
				dispose();
			}
		});
		btnAccept.setBounds(283, 214, 101, 23);
		contentPane.add(btnAccept);

		JButton btnCancel = new JButton("Cancel�lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(43, 214, 101, 23);
		contentPane.add(btnCancel);
	}
	
	private void config() {
		setTitle("Modificar descripci�");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private String getDefinicio() {
		try {
			String definicio = textDescription.getText().toString();
			return definicio;
		}
		catch (Exception e) {
			return null;
		}
	}

}
