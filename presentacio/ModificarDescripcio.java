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
		setTitle("Modificar descripció");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		content_pane();
		
		textDescription = new JTextField();
		textDescription.setBounds(9, 42, 341, 30);
		contentPane.add(textDescription);
		textDescription.setColumns(10);
		
		JLabel lblDescription = new JLabel("Escriu la nova descripció:");
		lblDescription.setBounds(10, 11, 340, 20);
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
		btnAccept.setBounds(249, 83, 101, 23);
		contentPane.add(btnAccept);

		JButton btnCancel = new JButton("Cancel·lar");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(19, 83, 101, 23);
		contentPane.add(btnCancel);
	}
	

	private void content_pane() {
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
