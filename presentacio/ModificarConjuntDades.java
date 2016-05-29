package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
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
		JFrame ref = this;
		setTitle("Modificar dades");
		setIconImage(ControladorPresentacio.ICON_MAIN);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 255, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// selector de conjunts
		JComboBox<String> comboBox = ctrl.getSelectorConjunts().newSelector(ref);
		contentPane.add(comboBox);

		// esborrar conjunt
		JButton btnEsborrar = new JButton("Esborrar conjunt");
		btnEsborrar.setBounds(10, 220, 229, 41);
		btnEsborrar.setIcon(new ImageIcon(ControladorPresentacio.ICON_WARNING));
		btnEsborrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else {
					int opt = JOptionPane.showConfirmDialog(null,
							"Totes les dades que has modificat des de que vas importar aquest graf es perdr�n."
							+ "\nRealment vols esborrar-lo?",
							"Confirmaci�", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opt == JOptionPane.YES_OPTION) {
						try {
							ctrl.esborrarFitxerGraf();
							ctrl.getSelectorConjunts().update();
						} catch (IOException ex) {
							new ErrorMessage(ex.getMessage());
						}
					}
				}
			}
		});
		contentPane.add(btnEsborrar);

		// afegir dades
		JButton btnAfegirDades = new JButton("Afegir dades");
		btnAfegirDades.setBounds(10, 56, 229, 41);
		btnAfegirDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else
					ControladorPresentacio.configurarNovaFinestra(ref, new AfegirDada(ctrl));
			}
		});
		contentPane.add(btnAfegirDades);

		// esborrar dades
		JButton btnEsborrarDades = new JButton("Esborrar dades");
		btnEsborrarDades.setBounds(10, 108, 229, 41);
		btnEsborrarDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else
					ControladorPresentacio.configurarNovaFinestra(ref, new EsborrarDada(ctrl));
			}
		});
		contentPane.add(btnEsborrarDades);

		// modificar dades
		JButton btnModificarDades = new JButton("Modificar dades");
		btnModificarDades.setBounds(10, 160, 229, 41);
		btnModificarDades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					new ErrorMessage(contentPane, "Selecciona un conjunt de dades primer.", "Cap conjunt seleccionat");
				else
					ControladorPresentacio.configurarNovaFinestra(ref, new ModificarDada(ctrl));
			}
		});
		contentPane.add(btnModificarDades);
	}
}
