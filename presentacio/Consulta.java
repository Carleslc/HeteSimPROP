package presentacio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class Consulta extends JFrame {

	private static final long serialVersionUID = -5805100415335714861L;

	private ControladorPresentacio ctrl;

	private JPanel contentPane;
	private JSpinner spinnerMinim, spinnerMaxim;
	private JLabel lblRellevanciaMinima, lblRellevanciaMaxima, lblDada1,
	lblDada2, lblRelacioThreshold, lblDada, lblRelacio;
	private JComboBox<String> comboBox_dada1, comboBox_dada2, comboBox_relacioThreshold,
	comboBox_dada, comboBox_relacio, selector;
	private JButton btnConsultar, btnEsborrarCamps;
	private JToggleButton tglbtnAfegirFiltre;
	private boolean alreadySelected;

	public Consulta(ControladorPresentacio ctrl) {
		this.ctrl = ctrl;
		setTitle("Consulta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension min = new Dimension(480, 390);
		setBounds(100, 100, min.width, min.height);
		setMinimumSize(min);
		setIconImage(ControladorPresentacio.ICON_MAIN);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]",
				"[grow][grow][grow][grow][grow][grow][grow]"));

		// Selector de conjunts
		selector = ctrl.getSelectorConjunts().newSelector(this);
		alreadySelected = selector.getSelectedIndex() != 0;
		selector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!alreadySelected) {
					alreadySelected = selector.getSelectedIndex() != 0;
					setConsultaEnabled(alreadySelected);
				}
			}
		});
		contentPane.add(selector, "cell 0 0,growx");

		// Afegir dades
		JLabel lblRecuperarConsultaAnterior = new JLabel("Recuperar consulta anterior");
		contentPane.add(lblRecuperarConsultaAnterior, "flowx,cell 0 1");

		JComboBox<Date> comboBox_consultaAnterior = new JComboBox<>();
		LinkedList<Date> datesConsultesAnteriors = ctrl.consultarDatesConsultes();
		datesConsultesAnteriors.addFirst(null);
		Date[] dates = new Date[datesConsultesAnteriors.size()];
		dates = datesConsultesAnteriors.toArray(dates);
		comboBox_consultaAnterior.setModel(new DefaultComboBoxModel<>(dates));
		contentPane.add(comboBox_consultaAnterior, "cell 0 1,growx,aligny center");

		lblRelacio = new JLabel("Relaci\u00F3");
		contentPane.add(lblRelacio, "flowx,cell 0 3,alignx left");

		comboBox_relacio = new JComboBox<>();
		LinkedList<String> paths = new LinkedList<String>();
		paths.add("");
		paths.addAll(ctrl.consultarPaths());
		String[] relacions = new String[paths.size()];
		relacions = paths.toArray(relacions);
		comboBox_relacio.setModel(new DefaultComboBoxModel<>(relacions));
		contentPane.add(comboBox_relacio, "cell 0 3,growx,aligny center");

		lblDada = new JLabel("Dada");
		contentPane.add(lblDada, "cell 0 3,alignx right,aligny center");

		comboBox_dada = new JComboBox<>();
		contentPane.add(comboBox_dada, "cell 0 3,growx,aligny center");

		// Threshold
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 5,grow");
		panel.setLayout(new MigLayout("",
				"[grow][grow][grow][87px,grow][grow][grow][grow][grow][grow][grow]",
				"[grow][grow][grow][grow][grow][grow][grow]"));
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		lblRellevanciaMinima = new JLabel("Rellev\u00E0ncia m\u00EDnima");
		panel.add(lblRellevanciaMinima, "flowy,cell 7 0,growx,aligny center");

		spinnerMinim = new JSpinner();
		spinnerMinim.setModel(new SpinnerNumberModel(0f, 0f, 1f, 0.01f));
		panel.add(spinnerMinim, "flowy,cell 8 0 2 1,growx,aligny center");

		lblRellevanciaMaxima = new JLabel("Rellev\u00E0ncia m\u00E0xima");
		panel.add(lblRellevanciaMaxima, "cell 7 0,growx,aligny center");

		lblDada1 = new JLabel("Dada 1");
		panel.add(lblDada1, "flowy,cell 0 2 6 1,growx,aligny center");

		lblDada2 = new JLabel("Dada 2");
		panel.add(lblDada2, "cell 7 2 3 1,growx,aligny center");

		comboBox_dada1 = new JComboBox<>();
		panel.add(comboBox_dada1, "cell 0 3 6 1,growx,aligny center");

		comboBox_dada2 = new JComboBox<>();
		panel.add(comboBox_dada2, "cell 7 3 3 1,growx,aligny center");

		lblRelacioThreshold = new JLabel("Relaci\u00F3");
		panel.add(lblRelacioThreshold, "flowx,cell 0 5 10 1,growx");

		comboBox_relacioThreshold = new JComboBox<>();
		comboBox_relacioThreshold.setModel(new DefaultComboBoxModel<>(relacions));
		panel.add(comboBox_relacioThreshold, "cell 0 6 10 1,growx");

		spinnerMaxim = new JSpinner();
		spinnerMaxim.setModel(new SpinnerNumberModel(1f, 0f, 1f, 0.01f));
		panel.add(spinnerMaxim, "cell 8 0 2 1,growx,aligny center");

		tglbtnAfegirFiltre = new JToggleButton("Afegir filtre");
		tglbtnAfegirFiltre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setThresholdEnabled(tglbtnAfegirFiltre.isSelected());
			}
		});
		panel.add(tglbtnAfegirFiltre, "cell 0 0 6 1,growx,aligny center");

		// Opcions de consulta
		JButton btnEnrere = new JButton("\u25C0 Enrere ");
		btnEnrere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		contentPane.add(btnEnrere, "flowx,cell 0 6,growx,aligny center");

		btnEsborrarCamps = new JButton("Esborrar tots els camps");
		btnEsborrarCamps.setIcon(new ImageIcon(ControladorPresentacio.ICON_WARNING));
		contentPane.add(btnEsborrarCamps, "cell 0 6,growx,aligny center");

		// Consultar
		btnConsultar = new JButton("Consultar \u27A4");
		btnConsultar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnConsultar.isEnabled()) {
					Object pathObj = comboBox_relacio.getSelectedItem();
					String path = pathObj == null ? "" : pathObj.toString();
					if (!path.isEmpty()) {
						@SuppressWarnings("unused") // S'usarà quan es facin les consultes
						boolean ignorarClausura = false;
						if (ctrl.existsClausura(path)) {
							if (!ctrl.isUpdatedClausura(path)) {
								int opt = JOptionPane.showOptionDialog(contentPane, "<html>S'ha detectat "
										+ "una clausura per la relació <b>" + path + "</b> però "
										+ "hi ha hagut canvis sobre<br>el conjunt de dades <b>"
										+ selector.getSelectedItem().toString()
										+ "</b> que podríen afectar als futurs càlculs amb aquesta clausura.<br>"
										+ "<i>Vols recalcular la clausura <b>" + path +
										"</b>?</i></html>", "Calcular clausura",
										JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
										null, new String[] {
												"Recalcular Clausura",
												"Utilitzar Clausura sense recalcular",
								"Ignorar Clausura"}, "Recalcular Clausura");
								switch (opt) {
								case JOptionPane.YES_OPTION: // Recalcular
									calcularClausura(e.getComponent(), path);
									break;
								case JOptionPane.CANCEL_OPTION: // Ignorar
									ignorarClausura = true;
									break;
									// En altre cas: Utilitzar sense recalcular
								}
							}
						}
						else {
							int opt = JOptionPane.showConfirmDialog(contentPane, "<html>No s'ha detectat cap"
									+ " clausura per la relació <b>" + path + "</b> i conjunt de dades <b>"
									+ selector.getSelectedItem().toString() + "</b>,<br>"
									+ "<i>Vols calcular la clausura <b>" + path +
									"</b> per agilitzar futures consultes?</i></html>", "Calcular clausura",
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (opt == JOptionPane.YES_OPTION)
								calcularClausura(e.getComponent(), path);
						}

						// TODO Consultes (comprovar paràmetres i realitzar consulta)
					}
					else
						new ErrorMessage(contentPane, "No pots realitzar cap consulta"
								+ " sense seleccionar una relació!");
				}
			}
		});
		// Main button, green background if Nimbus enabled and painted permantently as focused
		btnConsultar.setFocusPainted(true);
		btnConsultar.requestFocusInWindow();
		btnConsultar.requestFocus();
		getRootPane().setDefaultButton(btnConsultar);
		UIDefaults def = new UIDefaults();
		Object painter = UIManager.get("Button[Default+Focused].backgroundPainter");
		Object painterMouseOver = UIManager.get("Button[Default+Focused+MouseOver].backgroundPainter");
		def.put("Button[Enabled].backgroundPainter", painter);
		def.put("Button[MouseOver].backgroundPainter", painterMouseOver);
		def.put("Button[Default].backgroundPainter", painter);
		def.put("Button[Default+MouseOver].backgroundPainter", painterMouseOver);
		btnConsultar.putClientProperty("Nimbus.Overrides", def);
		//
		contentPane.add(btnConsultar, "cell 0 6,growx,aligny center");

		setConsultaEnabled(selector.getSelectedIndex() != 0);
		boolean enableThreshold = false;
		tglbtnAfegirFiltre.setSelected(enableThreshold);
		setThresholdEnabled(enableThreshold);
	}

	private void setThresholdEnabled(boolean enable) {
		lblRellevanciaMinima.setEnabled(enable);
		lblRellevanciaMaxima.setEnabled(enable);
		lblRelacioThreshold.setEnabled(enable);
		lblDada1.setEnabled(enable);
		lblDada2.setEnabled(enable);
		spinnerMinim.setEnabled(enable);
		spinnerMaxim.setEnabled(enable);
		comboBox_dada1.setEnabled(enable);
		comboBox_dada2.setEnabled(enable);
		comboBox_relacioThreshold.setEnabled(enable);
	}

	private void setConsultaEnabled(boolean enable) {
		lblDada.setEnabled(enable);
		lblRelacio.setEnabled(enable);
		comboBox_relacio.setEnabled(enable);
		comboBox_dada.setEnabled(enable);
		btnConsultar.setEnabled(enable);
		btnConsultar.setBackground(UIManager.getColor(enable ? "nimbusGreen" : "nimbusBase"));
		btnEsborrarCamps.setEnabled(enable);
		tglbtnAfegirFiltre.setEnabled(enable);
	}

	private void calcularClausura(Component parent, String path) {
		new TaskConsole<Void>(ControladorPresentacio.ICON_DISK,
			"Clausura " + path + " (" + selector.getSelectedItem().toString() + ")",
			() -> {
				try {
					parent.setEnabled(false);
					ctrl.clausura(path, true);
				} catch (Exception ex) {
					throw ex;
				} finally {
					parent.setEnabled(true);
				}
				return null;
			}).call();
	}
	
	@SuppressWarnings("unused") // TODO
	private TipusDada getTipusDada(char c) {
		c = Character.toUpperCase(c);
		switch (c) {
			case 'P': return TipusDada.Paper;
			case 'A': return TipusDada.Autor;
			case 'C': return TipusDada.Conferencia;
			case 'T': return TipusDada.Terme;
		}
		return null;
	}
}
