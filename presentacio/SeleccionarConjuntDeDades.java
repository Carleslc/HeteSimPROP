package presentacio;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class SeleccionarConjuntDeDades extends JComboBox<String> {

	private static final long serialVersionUID = 2964298207715038371L;
	
	private final ControladorPresentacio ctrl;
	private DefaultComboBoxModel<String> comboBoxModel;
	
	public SeleccionarConjuntDeDades(ControladorPresentacio ctrl) {
		this.ctrl = ctrl;
		setToolTipText("Selecciona un conjunt de dades.");
		setBounds(199, 11, 225, 32);
		setEditable(false);
		update();
	}
	
	public void afegirConjuntDeDades(String graf) {
		comboBoxModel.addElement(graf);
	}
	
	public void esborrarConjuntDeDades(int index) {
		comboBoxModel.removeElementAt(index);
	}
	
	public void update() {
		comboBoxModel = new DefaultComboBoxModel<>(new String[] {"- No hi ha cap conjunt seleccionat -"});
		List<String> grafs = ctrl.getNomsGrafs();
		for (String graf : grafs)
			comboBoxModel.addElement(graf);
		setModel(comboBoxModel);
		setSelectedIndex(grafs.isEmpty() ? 0 : 1 + grafs.indexOf(ctrl.getIdActual()));
	}
	
}
