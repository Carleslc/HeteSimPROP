package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JList;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InformacioAddicional extends JFrame {

	private static final long serialVersionUID = -1799024988380129469L;

	private JPanel contentPane;
	private TipusDada seltipus;

	public InformacioAddicional(ControladorPresentacio ctrl, int id, TipusDada tipus) {
		setTitle("Dades Relacionades");
		setIconImage(ControladorPresentacio.ICON_INFO);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 484, 300);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{146, 136, 157, 0};
		gbl_contentPane.rowHeights = new int[]{27, 224, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblTipusNodeRelacionat = new JLabel("Tipus de node relacionat:");
		GridBagConstraints gbc_lblTipusNodeRelacionat = new GridBagConstraints();
		gbc_lblTipusNodeRelacionat.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipusNodeRelacionat.anchor = GridBagConstraints.EAST;
		gbc_lblTipusNodeRelacionat.gridx = 1;
		gbc_lblTipusNodeRelacionat.gridy = 0;
		contentPane.add(lblTipusNodeRelacionat, gbc_lblTipusNodeRelacionat);

		//ScrollPane
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);


		//desplegable
		JComboBox<String> comboBox = new JComboBox<String>();

		//afegeixo items al combobox
		if (tipus == TipusDada.Paper) {
			comboBox.addItem("- Selecciona tipus - ");
			comboBox.addItem(TipusDada.Autor.toString());
			comboBox.addItem(TipusDada.Terme.toString());
			comboBox.addItem(TipusDada.Conferencia.toString());
			comboBox.setSelectedItem("- Selecciona tipus - ");;
		}
		else comboBox.addItem("papers");

		//listener que nomes actua si la dada sobre la qual es consulten relacions es un paper
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipus == TipusDada.Paper && comboBox.getSelectedIndex() != 0) {
					seltipus = TipusDada.valueOf(comboBox.getSelectedItem().toString());
					switch (seltipus) {
						case Terme:
							String[] s1 = new String [ctrl.consultarRelacionsPaperAmbTerme(id).size()];
							ctrl.consultarRelacionsPaperAmbTerme(id).toArray(s1);
							JList<String> list1 = new JList<String>(s1);
							scrollPane.setViewportView(list1);
							break;
						case Autor:
							String[] s2 = new String [ctrl.consultarRelacionsPaperAmbAutor(id).size()];
							ctrl.consultarRelacionsPaperAmbAutor(id).toArray(s2);
							JList<String> list2 = new JList<String>(s2);
							scrollPane.setViewportView(list2);
							break;
						case Conferencia:
							String[] s3 = new String [ctrl.consultarRelacionsPaperAmbConferencia(id).size()];
							ctrl.consultarRelacionsPaperAmbConferencia(id).toArray(s3);
							JList<String> list3 = new JList<String>(s3);
							scrollPane.setViewportView(list3);
							break;
						default:
							break;
					}
				}
			}
		});

		//Si la dada sobre la qual es consulten relacions no es un paper (es una conferencia, terme o autor)
		switch (tipus) {
			case Terme:
				String[] s1 = new String [ctrl.consultarRelacionsTerme(id).size()];
				ctrl.consultarRelacionsTerme(id).toArray(s1);
				JList<String> list1 = new JList<String>(s1);
				scrollPane.setViewportView(list1);
				break;
			case Autor:
				String[] s2 = new String [ctrl.consultarRelacionsAutor(id).size()];
				ctrl.consultarRelacionsAutor(id).toArray(s2);
				JList<String> list2 = new JList<String>(s2);
				scrollPane.setViewportView(list2);
				break;
			case Conferencia:
				String[] s3 = new String [ctrl.consultarRelacionsConferencia(id).size()];
				ctrl.consultarRelacionsConferencia(id).toArray(s3);
				JList<String> list3 = new JList<String>(s3);
				scrollPane.setViewportView(list3);
				break;
			default:
				break;
		}

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		contentPane.add(comboBox, gbc_comboBox);
	}
}



