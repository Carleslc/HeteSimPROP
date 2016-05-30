package presentacio;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = -9206328603791933807L;

	private JPanel contentPane;
	private ControladorPresentacio ctrl;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControladorPresentacio ctrl = new ControladorPresentacio();
					MenuPrincipal frame = new MenuPrincipal(ctrl);
					frame.setVisible(true);
				} catch (Exception uncaught) {
					new ErrorMessage(uncaught.getMessage());
				}
			}
		});
	}

	public MenuPrincipal(ControladorPresentacio ctrl) {
		this.ctrl = ctrl;
		setDefaultStyle();
		MenuPrincipal ref = this;
		setBounds(100, 100, 255, 327);
		setTitle("Men� Principal");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int opt = JOptionPane.showConfirmDialog(ref, "Vols guardar els canvis abans de sortir?",
						"Guardar dades", JOptionPane.YES_NO_CANCEL_OPTION);
				switch (opt) {
					case JOptionPane.YES_OPTION:
						guardarDades();
						dispose();
						break;
					case JOptionPane.NO_OPTION:
						try {
							ctrl.reestablirClausures();
						} catch (IOException e1) {
							new ErrorMessage(e1.getMessage());
						}
						dispose();
						break;
				}
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//guardar button
		JButton btnNewButton_2 = new JButton("Guardar");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				guardarDades();
			}
		});
		btnNewButton_2.setBounds(41, 179, 166, 42);
		contentPane.add(btnNewButton_2);

		//Consulta button
		JButton btnNewButton = new JButton("Consulta");
		btnNewButton.setBounds(41, 63, 166, 47);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnNewButton.isEnabled())
					ControladorPresentacio.configurarNovaFinestra(ref, new Consulta(ctrl));
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);

		//About button
		JButton btnNewButton_3 = new JButton("About");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Message(contentPane, "Grup 12.2\n"
						+ "Carla Claverol González\n"
						+ "Guillem Castro Olivares\n"
						+ "Carlos Lázaro Costa\n" + "Arnau Badia Sampera\n",
						"About");
			}
		});
		btnNewButton_3.setBounds(83, 248, 80, 30);
		contentPane.add(btnNewButton_3);

		// selector de conjunts
		JComboBox<String> comboBox = ctrl.getSelectorConjunts().newSelector(this);
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (comboBox.getSelectedIndex() == 0)
					btnNewButton.setEnabled(false);
				else
					btnNewButton.setEnabled(true);
			}
		});
		contentPane.add(comboBox);

		//Gestio de dades button
		JButton btnNewButton_1 = new JButton("Gestió de Dades");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ControladorPresentacio.configurarNovaFinestra(ref, new GestorDades(ctrl));
			}
		});
		btnNewButton_1.setBounds(41, 121, 166, 47);
		contentPane.add(btnNewButton_1);
	}

	private void guardarDades() {
		setEnabled(false);
		try {
			new BounceProgressBarTaskFrame<Void>(ControladorPresentacio.ICON_SAVE,
					"Guardar dades",
					() -> {
						ctrl.guardarDades();
						return null;
					},
					(v) -> {setEnabled(true); return true;},
					"Guardant dades...",
					"Dades guardades correctament", "No s'han pogut guardar les dades!").call();
		} catch (Exception ignore) {}
	}

	private final void setDefaultStyle() {
		setIconImage(ControladorPresentacio.ICON_MAIN);
		try {
			// Nimbus L&F style
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// Base Background
			UIManager.put("control", Color.getHSBColor(170/360f, 0.05f, 0.97f));
			// ToolTip Background
			UIManager.put("info", Color.getHSBColor(185/360f, 0.15f, 0.97f));
			// Buttons Background
			UIManager.put("nimbusBase", Color.getHSBColor(200/360f, 0.15f, 0.65f));
			// ComboBox Highlight Background
			UIManager.put("ComboBox:\"ComboBox.listRenderer\"[Selected].background", Color.LIGHT_GRAY);
			// Default colors
			UIManager.getLookAndFeelDefaults().put("nimbusOrange", UIManager.getColor("nimbusBase"));
			UIManager.getLookAndFeelDefaults().put("nimbusGreen", Color.getHSBColor(100/360f, 0.65f, 0.85f));
		} catch (Exception notFoundThenUseDefault) {}
	}
}
