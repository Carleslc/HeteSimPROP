package presentacio;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import domini.ControladorMultigraf;

public class SeleccionarConjuntDeDades extends JComboBox<String> {

	private static final long serialVersionUID = 2964298207715038371L;
	
	public SeleccionarConjuntDeDades(ControladorMultigraf ctrlMultigraf) {
		setToolTipText("Selecciona un conjunt de dades.");
		setBounds(199, 11, 225, 32);
		setEditable(false);
		final DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(new String[] {"- No hi ha cap conjunt seleccionat -"});
		List<String> grafs = ctrlMultigraf.getNomsGrafs();
		for (String graf : grafs)
			comboBoxModel.addElement(graf);
		setModel(comboBoxModel);
		setSelectedIndex(grafs.isEmpty() ? 0 : 1 + grafs.indexOf(ctrlMultigraf.getIdActual()));
	}
	
}
