package presentacio;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class SelectorConjunts {
	
	private LinkedList<SeleccionarConjuntDeDades> selectors;
	private DefaultComboBoxModel<String> model;
	private ControladorPresentacio ctrl;
	private boolean updating;

	static {
		UIManager.put("ComboBox:\"ComboBox.listRenderer\"[Selected].background", Color.LIGHT_GRAY);
	}
	
	public SelectorConjunts(ControladorPresentacio ctrl) {
		this.ctrl = ctrl;
		selectors = new LinkedList<>();
		updating = false;
	}

	public JComboBox<String> newSelector(JFrame parent) {
		final SeleccionarConjuntDeDades scd = new SeleccionarConjuntDeDades();
		selectors.add(scd);
		update();
		scd.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!updating && e.getStateChange() == ItemEvent.SELECTED) {
					int index = scd.getSelectedIndex();
					if (index != 0) {
						new BounceProgressBarTaskFrame<Boolean>(ControladorPresentacio.ICON_DISK,
								"Seleccionar graf",
								() -> {
									try {
										updating = true;
										parent.setEnabled(false);
										ctrl.seleccionarGraf((String)scd.getSelectedItem());
										update();
									} catch (FileNotFoundException ignore) {
									} catch (IOException ex) {
										new ErrorMessage(ex.getMessage());
										return false;
									} finally {
										parent.setEnabled(true);
									}
									return true;
								},
								(b) -> {return b;},
								"Carregant conjunt de dades...", "",
								"Error al llegir les dades!").call();
					}
					else
						update(); // evita seleccionar el primer de nou
				}
			}
		});
		return scd;
	}

	public void update() {
		updating = true;
		model = new DefaultComboBoxModel<>(new String[] {"- No hi ha cap conjunt seleccionat -"});
		List<String> grafs = ctrl.getNomsGrafs();
		for (String graf : grafs)
			model.addElement(graf);
		int index = grafs.isEmpty() ? 0 : 1 + grafs.indexOf(ctrl.getIdActual());
		for (JComboBox<String> scd : selectors) {
			scd.setModel(model);
			scd.setSelectedIndex(index);
		}
		updating = false;
	}

	private class SeleccionarConjuntDeDades extends JComboBox<String> {
		private static final long serialVersionUID = 2964298207715038371L;

		public SeleccionarConjuntDeDades() {
			setToolTipText("Selecciona un conjunt de dades.");
			setBounds(13, 10, 225, 30);
			((JLabel)getRenderer()).setHorizontalAlignment(JLabel.CENTER);
			setEditable(false);
		}
	}

}
