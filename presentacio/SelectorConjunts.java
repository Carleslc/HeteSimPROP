package presentacio;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class SelectorConjunts {
	
	private List<SeleccionarConjuntDeDades> selectors;
	private DefaultComboBoxModel<String> model;
	private ControladorPresentacio ctrl;
	
	public SelectorConjunts(ControladorPresentacio ctrl) {
		this.ctrl = ctrl;
		selectors = new LinkedList<>();
	}
	
	public JComboBox<String> newSelector() {
		SeleccionarConjuntDeDades scd = new SeleccionarConjuntDeDades();
		selectors.add(scd);
		update();
		scd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ctrl.seleccionarGraf((String)scd.getSelectedItem());
					update();
				} catch (FileNotFoundException ignore) {
				} catch (IOException ex) {
					new ErrorMessage(ex.getMessage());
				}
			}
		});
		return scd;
	}
	
	public void update() {
		model = new DefaultComboBoxModel<>(new String[] {"- No hi ha cap conjunt seleccionat -"});
		List<String> grafs = ctrl.getNomsGrafs();
		for (String graf : grafs)
			model.addElement(graf);
		int index = grafs.isEmpty() ? 0 : 1 + grafs.indexOf(ctrl.getIdActual());
		for (JComboBox<String> scd : selectors) {
			scd.setModel(model);
			scd.setSelectedIndex(index);
		}
	}
	
	private class SeleccionarConjuntDeDades extends JComboBox<String> {
		private static final long serialVersionUID = 2964298207715038371L;
		
		public SeleccionarConjuntDeDades() {
			setToolTipText("Selecciona un conjunt de dades.");
			setBounds(200, 11, 225, 32);
			setEditable(false);
		}
	}
	
}
