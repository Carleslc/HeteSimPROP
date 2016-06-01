package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;

public class SeleccionarConferencia extends JFrame {

	private static final long serialVersionUID = -1154857645777734392L;
	
	private JPanel contentPane;
	private JTextField textField;
	private Integer resultat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeleccionarConferencia frame = new SeleccionarConferencia(new ControladorPresentacio(), "Conf", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SeleccionarConferencia(ControladorPresentacio cntrl, String nomConferencia, String nomPaper) {
		resultat = -1;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 693, 134);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomDeLa = new JLabel("Nom de la Conferencia:");
		lblNomDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomDeLa.setBounds(5, 44, 141, 14);
		contentPane.add(lblNomDeLa);
		
		textField = new JTextField();
		textField.setBounds(156, 41, 511, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Seleccionar");
		btnNewButton.setBounds(298, 71, 123, 23);
		btnNewButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				SeleccionarDada sd = new SeleccionarDada(cntrl, textField.getText(), TipusDada.Conferencia);
				sd.setVisible(true);
				sd.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						SeleccionarDada src = (SeleccionarDada) e.getSource();
						resultat = src.getResultat();
						dispose();
					}
				});
			}
			
		});
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Per eliminar una relaci\u00F3 de la Conferencia " + nomConferencia + " i el Paper " +
				nomPaper + "has de seleccionar una nova Conferencia pel Paper");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(5, 0, 672, 36);
		contentPane.add(lblNewLabel);

		setTitle("Seleccionar Conferencia");
	}
	
	public Integer getResultat() {
		return resultat;
	}
}
