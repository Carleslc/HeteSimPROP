package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import persistencia.ControladorPersistencia;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ModificarConjuntDades extends JFrame {

	private static final long serialVersionUID = -532006997376444843L;

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarConjuntDades frame = new ModificarConjuntDades(new ControladorPresentacio());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModificarConjuntDades(ControladorPresentacio ctrl) {
		setTitle("Modificar conjunt de dades");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<String> comboBox = new SeleccionarConjuntDeDades(ctrl);
		contentPane.add(comboBox);

		JButton btnEsborrar = new JButton("Esborrar conjunt");
		btnEsborrar.setBounds(10, 10, 155, 33);
		btnEsborrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else {
					int opt = JOptionPane.showConfirmDialog(null,
							"El conjunt de dades s'esborrar� completament de disc.\nRealment vols esborrar-lo?",
							"Confirmaci�", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opt == JOptionPane.YES_OPTION) {
						try {
							ctrl.esborrarFitxerGraf();
						} catch (IOException ex) {
							new ErrorMessage(ex.getMessage());
						}
					}
				}
			}
		});
		contentPane.add(btnEsborrar);

		JButton btnAfegirDades = new JButton("Afegir dades");
		btnAfegirDades.setBounds(129, 103, 162, 33);
		btnAfegirDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else
					new AfegirDada(ctrl).setVisible(true);
			}
		});
		contentPane.add(btnAfegirDades);

		JButton btnEsborrarDades = new JButton("Esborrar dades");
		btnEsborrarDades.setBounds(129, 147, 162, 33);
		btnEsborrarDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else
					new EsborrarDada(ctrl).setVisible(true);
			}
		});
		contentPane.add(btnEsborrarDades);

		JButton btnModificarDades = new JButton("Modificar dades");
		btnModificarDades.setBounds(129, 191, 162, 33);
		btnModificarDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else
					new ModificarDada(ctrl).setVisible(true);
			}
		});
		contentPane.add(btnModificarDades);
	}
}
