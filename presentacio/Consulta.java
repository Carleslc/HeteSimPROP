package presentacio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class Consulta extends JFrame {

	private static final long serialVersionUID = -5805100415335714861L;

	private static final int MILLIS_DELAY_TO_SELECT_DADA = 1000;

	private final ControladorPresentacio ctrl;

	private JFrame ref;
	private final JPanel contentPane;
	private final JSpinner spinnerMinim, spinnerMaxim;
	private final JLabel lblRellevanciaMinima, lblRellevanciaMaxima, lblDada1,
		lblDada2, lblRelacioThreshold, lblDada, lblRelacio;
	private final JComboBox<String> comboBox_relacioThreshold, comboBox_relacio, selector;
	private final JComboBox<Date> comboBox_consultaAnterior;
	private final JButton btnConsultar, btnEsborrarCamps;
	private final JToggleButton tglbtnAfegirFiltre;
	private final JTextField dada, dada1, dada2;
	private boolean alreadySelected, onlyClausura, ignorarClausura, ignorarClausura2;

	private int idDada, idDada1, idDada2;

	public Consulta(ControladorPresentacio ctrl) {
		this.ctrl = ctrl;
		ref = this;

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
					checkStatus();
				}
			}
		});
		contentPane.add(selector, "cell 0 0,growx");

		// Consultes anteriors
		JLabel lblRecuperarConsultaAnterior = new JLabel("Recuperar consulta anterior");
		contentPane.add(lblRecuperarConsultaAnterior, "flowx,cell 0 1");

		comboBox_consultaAnterior = new JComboBox<>();
		updateConsultesAnteriors();
		comboBox_consultaAnterior.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED
						&& comboBox_consultaAnterior.getSelectedIndex() != 0) {
					ctrl.setUltimaConsulta((Date)comboBox_consultaAnterior.getSelectedItem());
					ControladorPresentacio.configurarNovaFinestra(ref, new Resultats(ctrl));
					comboBox_consultaAnterior.setSelectedIndex(0);
				}
			}
		});
		contentPane.add(comboBox_consultaAnterior, "cell 0 1,growx,aligny center");

		// Afegir dades
		lblRelacio = new JLabel("Relaci\u00F3");
		contentPane.add(lblRelacio, "flowx,cell 0 3,alignx left");

		comboBox_relacio = new JComboBox<>();
		LinkedList<String> paths = new LinkedList<String>();
		paths.add("");
		paths.addAll(ctrl.consultarPaths());
		String[] relacions = new String[paths.size()];
		relacions = paths.toArray(relacions);
		comboBox_relacio.setModel(new DefaultComboBoxModel<>(relacions));
		comboBox_relacio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					checkStatus();
			}
		});
		contentPane.add(comboBox_relacio, "cell 0 3,growx,aligny center");

		lblDada = new JLabel("Dada");
		contentPane.add(lblDada, "cell 0 3,alignx right,aligny center");

		// El sistema espera MILLIS_DELAY_TO_SELECT_DADA ms despr\u00E9s d'haver introduit
		// el nom i llavors selecciona la dada o l'envia a seleccionar la dada si hi ha repetits
		dada = new JTextField();
		dada.getDocument().addDocumentListener(new DadaListener(0));
		contentPane.add(dada, "cell 0 3,growx,aligny center");

		// Threshold
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 5,grow");
		panel.setLayout(new MigLayout("", "[grow][grow][grow][87px,grow][grow][grow][grow]", "[grow][grow][][grow][grow][grow][grow][grow]"));
		panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		lblRellevanciaMinima = new JLabel("Rellev\u00E0ncia m\u00EDnima");
		panel.add(lblRellevanciaMinima, "flowy,cell 4 0,alignx center");

		spinnerMinim = new JSpinner();
		spinnerMinim.setModel(new SpinnerNumberModel(0f, 0f, 1f, 0.01f));
		panel.add(spinnerMinim, "flowy,cell 5 0 2 1,growx,aligny center");

		lblRellevanciaMaxima = new JLabel("Rellev\u00E0ncia m\u00E0xima");
		panel.add(lblRellevanciaMaxima, "cell 4 0,alignx center,aligny top");

		lblRelacioThreshold = new JLabel("Relaci\u00F3");
		panel.add(lblRelacioThreshold, "flowx,cell 0 2 7 1,alignx left");

		comboBox_relacioThreshold = new JComboBox<>();
		comboBox_relacioThreshold.setModel(new DefaultComboBoxModel<>(relacions));
		comboBox_relacioThreshold.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					checkStatus();
			}
		});
		panel.add(comboBox_relacioThreshold, "cell 0 2 7 1,growx");

		lblDada1 = new JLabel("Dada 1");
		panel.add(lblDada1, "flowx,cell 0 4 4 1,alignx center,aligny center");

		dada1 = new JTextField();
		dada1.getDocument().addDocumentListener(new DadaListener(1));
		panel.add(dada1, "cell 0 4 4 1,growx,aligny center");

		lblDada2 = new JLabel("Dada 2");
		panel.add(lblDada2, "flowx,cell 4 4 3 1,alignx center,aligny center");

		dada2 = new JTextField();
		dada2.getDocument().addDocumentListener(new DadaListener(2));
		panel.add(dada2, "cell 4 4 3 1,growx,aligny center");

		spinnerMaxim = new JSpinner();
		spinnerMaxim.setModel(new SpinnerNumberModel(1f, 0f, 1f, 0.01f));
		panel.add(spinnerMaxim, "cell 5 0 2 1,growx,aligny center");

		tglbtnAfegirFiltre = new JToggleButton("Afegir filtre");
		tglbtnAfegirFiltre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkStatus();
			}
		});
		panel.add(tglbtnAfegirFiltre, "cell 0 0 4 1,growx,aligny center");

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
		btnEsborrarCamps.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clear();
			}
		});
		contentPane.add(btnEsborrarCamps, "cell 0 6,growx,aligny center");

		// Consultar
		btnConsultar = new JButton("Consultar \u27A4");
		btnConsultar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnConsultar.isEnabled()) {
					setEnabled(false);
					Component parent = e.getComponent();
					String path = comboBox_relacio.getSelectedItem().toString();
					doStep(parent, path, "Consulta", StepConsulta.RESOLUCIO_THRESHOLD);
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

		clear();
	}
	
	private void updateConsultesAnteriors() {
		LinkedList<Date> datesConsultesAnteriors = ctrl.consultarDatesConsultes();
		datesConsultesAnteriors.addFirst(null);
		Date[] dates = new Date[datesConsultesAnteriors.size()];
		dates = datesConsultesAnteriors.toArray(dates);
		comboBox_consultaAnterior.setModel(new DefaultComboBoxModel<>(dates));
	}
	
	private void doStep(Component parent, String path, String tipusClausura, StepConsulta nextStep) {
		if (nextStep == StepConsulta.RESOLUCIO_THRESHOLD)
			ignorarClausura = false;
		else
			ignorarClausura2 = false;
		if (ctrl.existsClausura(path)) {
			if (!ctrl.isUpdatedClausura(path)) {
				int opt = JOptionPane.showOptionDialog(contentPane, "<html>S'ha detectat "
						+ "una clausura per la relaci\u00F3 <b>" + path + "</b> però "
						+ "hi ha hagut canvis sobre<br>el conjunt de dades <b>"
						+ selector.getSelectedItem().toString()
						+ "</b> que podr\u00EDen afectar als futurs c\u00E0lculs amb aquesta clausura.<br>"
						+ "<i>Vols recalcular la clausura <b>" + path +
						"</b>?</i></html>", "Calcular clausura (" + tipusClausura + ")",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
						null, new String[] {
								"Recalcular Clausura",
								"Utilitzar Clausura sense recalcular",
				"Ignorar Clausura"}, "Recalcular Clausura");
				switch (opt) {
					case JOptionPane.YES_OPTION: // Recalcular
						calcularClausura(parent, path, getNext(nextStep));
						break;
					case JOptionPane.CANCEL_OPTION: // Ignorar
						if (nextStep == StepConsulta.RESOLUCIO_THRESHOLD)
							ignorarClausura = true;
						else
							ignorarClausura2 = true;
						getNext(nextStep).run();
						break;
					default:
						// En altre cas: Utilitzar sense recalcular
						getNext(nextStep).run();
				}
			}
			else
				getNext(nextStep).run();
		}
		else {
			if (path != null && !path.isEmpty()) {
				int opt = JOptionPane.showConfirmDialog(contentPane, "<html>No s'ha detectat cap"
						+ " clausura per la relaci\u00F3 <b>" + path + "</b> i conjunt de dades <b>"
						+ selector.getSelectedItem().toString()
						+ "</b>,<br><i>Vols calcular la clausura <b>" + path
						+ "</b> per agilitzar futures consultes?</i></html>", "Calcular clausura",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (opt == JOptionPane.YES_OPTION)
					calcularClausura(parent, path, getNext(nextStep));
				else {
					if (nextStep == StepConsulta.RESOLUCIO_THRESHOLD)
						ignorarClausura = true;
					else
						ignorarClausura2 = true;
					getNext(nextStep).run();
				}
			}
			else
				getNext(nextStep).run();
		}
	}
	
	private Runnable getNext(StepConsulta next) {
		return next == StepConsulta.RESOLUCIO_THRESHOLD ?
				() -> {
					if (!onlyClausura) {
						if (tglbtnAfegirFiltre.isSelected()) { // Amb threshold
							String pathThreshold = comboBox_relacioThreshold.getSelectedItem().toString();
							doStep(ref, pathThreshold, "Threshold", StepConsulta.CONSULTA);
						}
						else
							consulta();
					}
				}
				: () -> {
					consulta();
				};
	}
	
	private void calcularClausura(Component parent, String path, Runnable nextStep) {
		new TaskConsole<>(ControladorPresentacio.ICON_DISK,
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
				},
				(v) -> {
					nextStep.run();
				}).call();
	}

	private void consulta() {
		if (!onlyClausura) {
			if (tglbtnAfegirFiltre.isSelected()) { // Amb threshold
				consulta(ref, comboBox_relacio.getSelectedItem().toString(), idDada, ignorarClausura,
						idDada1, idDada2, comboBox_relacioThreshold.getSelectedItem().toString(),
						(Double)spinnerMinim.getValue(),
						(Double)spinnerMaxim.getValue(), ignorarClausura2);
			}
			else
				consulta(ref, comboBox_relacio.getSelectedItem().toString(), idDada, ignorarClausura,
						idDada1, idDada2, null, 0, 1, ignorarClausura);
			
		}
	}
	
	private void consulta(Component parent, String path, int idDada, boolean ignorarClausura,
			int idNodeThreshold1, int idNodeThreshold2, String thresholdPath, double min, double max,
			boolean ignorarClausuraThreshold) {
		new TaskConsole<Void>(ControladorPresentacio.ICON_DISK,
				"Realitzant consulta... ",
				() -> {
					try {
						parent.setEnabled(false);
						if (thresholdPath == null || thresholdPath.isEmpty())
							ctrl.consulta(path, idDada, ignorarClausura, min, max);
						else
							ctrl.consulta(path, idDada, idNodeThreshold1, idNodeThreshold2,
									thresholdPath, min, max, ignorarClausura, ignorarClausuraThreshold);
						ControladorPresentacio.configurarNovaFinestra(ref, new Resultats(ctrl));
					} catch (Exception ex) {
						throw ex;
					} finally {
						parent.setEnabled(true);
					}
					return null;
				}, (v) -> {
					updateConsultesAnteriors();
				}).call();
	}

	private void setThresholdDisabled() {
		lblRellevanciaMinima.setEnabled(false);
		lblRellevanciaMaxima.setEnabled(false);
		lblRelacioThreshold.setEnabled(false);
		lblDada1.setEnabled(false);
		lblDada2.setEnabled(false);
		spinnerMinim.setEnabled(false);
		spinnerMaxim.setEnabled(false);
		dada1.setEnabled(false);
		dada2.setEnabled(false);
		comboBox_relacioThreshold.setEnabled(false);
	}

	private void setConsultaEnabled(boolean enable) {
		lblDada.setEnabled(enable);
		lblRelacio.setEnabled(enable);
		comboBox_relacio.setEnabled(enable);
		dada.setEnabled(enable);
		enableConsultaButton(enable);
		btnEsborrarCamps.setEnabled(enable);
		tglbtnAfegirFiltre.setEnabled(enable);
		if (!enable)
			setThresholdDisabled();
	}

	private void enableConsultaButton(boolean enable) {
		btnConsultar.setEnabled(enable);
		btnConsultar.setBackground(UIManager.getColor(enable ? "nimbusGreen" : "nimbusBase"));
	}

	private void clear() {
		comboBox_relacio.setSelectedIndex(0);
		dada.setText("");
		dada1.setText("");
		dada2.setText("");
		idDada = idDada1 = idDada2 = -1;
		comboBox_relacioThreshold.setSelectedIndex(0);
		spinnerMinim.setValue(0d);
		spinnerMaxim.setValue(1d);
		onlyClausura = false;
		tglbtnAfegirFiltre.setSelected(false);
		checkStatus();
	}

	/**
	 * Comprova i modifica l'estat per fer una selecci\u00F3 pas a pas de les dades.
	 */
	private void checkStatus() {
		setConsultaEnabled(false);
		// Si hi ha graf seleccionat
		if (selector.getSelectedIndex() != 0) {
			lblRelacio.setEnabled(true);
			comboBox_relacio.setEnabled(true);
			btnEsborrarCamps.setEnabled(true);
			// Si hi ha path seleccionat
			if (comboBox_relacio.getSelectedIndex() != 0) {
				lblDada.setEnabled(true);
				dada.setEnabled(true);
				// Si hi ha dada seleccionada
				if (idDada >= 0) {
					onlyClausura = false;
					tglbtnAfegirFiltre.setEnabled(true);
					// Si hi ha filtre
					if (tglbtnAfegirFiltre.isSelected()) {
						lblRellevanciaMinima.setEnabled(true);
						lblRellevanciaMaxima.setEnabled(true);
						spinnerMinim.setEnabled(true);
						spinnerMaxim.setEnabled(true);
						lblRelacioThreshold.setEnabled(true);
						comboBox_relacioThreshold.setEnabled(true);
						// Si la relaci\u00F3 del threshold est\u00E0 seleccionada
						if (comboBox_relacioThreshold.getSelectedIndex() != 0) {
							lblDada1.setEnabled(true);
							lblDada2.setEnabled(true);
							dada1.setEnabled(true);
							dada2.setEnabled(true);
							// Si les dades del threshold estan seleccionades
							if (idDada1 >= 0 && idDada2 >= 0)
								enableConsultaButton(true);
						}
						else
							enableConsultaButton(true);
					}
					else
						enableConsultaButton(true);
				}
				else {
					onlyClausura = true;
					enableConsultaButton(true);
				}
			}
		}
	}

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

	private enum StepConsulta {
		RESOLUCIO_THRESHOLD,
		CONSULTA
	}

	private class DadaListener implements DocumentListener {

		private Timer dadaTaskTimer;

		private JTextField dadaField;
		private JComboBox<String> path;
		private int numeroDada;
		private DocumentListener ref;

		public DadaListener(int numeroDada) {
			ref = this;
			this.numeroDada = numeroDada;
			dadaTaskTimer = new Timer();
			switch (numeroDada) {
			case 0: dadaField = dada; path = comboBox_relacio; break;
			case 1: dadaField = dada1; path = comboBox_relacioThreshold; break;
			default: dadaField = dada2; path = comboBox_relacioThreshold; break;
			}
		}

		@Override public void insertUpdate(DocumentEvent e) { reschedule(); }
		@Override public void removeUpdate(DocumentEvent e) { reschedule(); }
		@Override public void changedUpdate(DocumentEvent e) { reschedule(); }

		private void reschedule() {
			if (!dadaField.getText().isEmpty())
				enableConsultaButton(false);
			dadaTaskTimer.cancel();
			dadaTaskTimer = new Timer();
			dadaTaskTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					String txt = dadaField.getText();
					if (!txt.isEmpty()) {
						String pathSelected = (String)path.getSelectedItem();
						SeleccionarDada frame = new SeleccionarDada(ctrl, txt,
								getTipusDada(pathSelected.charAt(numeroDada == 2 ?
										pathSelected.length() - 1 : 0)));
						setEnabled(false);
						frame.setVisible(true);
						frame.addWindowListener(new WindowAdapter() {
							public void windowClosed(WindowEvent e) {
								int res = frame.getResultat();
								if (res != -1) {
									switch (numeroDada) {
									case 0: idDada = res; break;
									case 1: idDada1 = res; break;
									default: idDada2 = res; break;
									}
									dadaField.getDocument().removeDocumentListener(ref);
									dadaField.setText(frame.getNomDada());
									dadaField.getDocument().addDocumentListener(ref);
									frame.dispose();
								}
								else {
									if (!frame.isEmpty())
										new ErrorMessage("Has de seleccionar una dada!");
									dadaField.setText("");
									switch (numeroDada) {
										case 0: idDada = -1; break;
										case 1: idDada1 = -1; break;
										default: idDada2 = -1; break;
									}
								}
								setEnabled(true);
								setVisible(true);
								checkStatus();
							}
						});
					}
				}
			}, MILLIS_DELAY_TO_SELECT_DADA);
		}
	}
}
