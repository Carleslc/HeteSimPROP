package presentacio;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JLabel;

/**
 * 
 * @author Arnau Badia Sampera
 *
 */
public class Importar extends JFrame {

	private static final long serialVersionUID = -5244204067392716257L;

	private JPanel contentPane;
	private JTextField nomField;
	private String selDir;
	private JTextField directoriField;
	private JButton btnAcceptar;

	public Importar(ControladorPresentacio ctrl) {
		setTitle("Importar conjunt de dades");
		setIconImage(ControladorPresentacio.ICON_DISK);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 325, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nomField = new JTextField();
		nomField.setBounds(112, 88, 187, 28);
		contentPane.add(nomField);
		nomField.setColumns(10);

		JButton btnNewButton = new JButton("Escollir directori amb dades d'importaci\u00F3");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selDir = selectedDir();
				directoriField.setText(selDir);
			}
		});

		btnNewButton.setBounds(10, 11, 289, 29);
		contentPane.add(btnNewButton);

		JLabel lblNomDelGraf = new JLabel("Nom del graf:");
		lblNomDelGraf.setBounds(20, 94, 86, 16);
		contentPane.add(lblNomDelGraf);

		JButton btnCancellar = new JButton("CancelÂ\u00B7lar");
		btnCancellar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		btnCancellar.setBounds(10, 127, 117, 29);
		contentPane.add(btnCancellar);

		btnAcceptar = new JButton("Importa graf");

		btnAcceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnAcceptar.isEnabled()) {
					String graf = nomField.getText();
					String dir = directoriField.getText();
					if (ctrl.exists(graf))
						new ErrorMessage("Aquest graf ja existeix. Canvia el nom.");
					else {
						setEnabled(false);
						new BounceProgressBarTaskFrame<Message>(ControladorPresentacio.ICON_ADD, "Importar",
								() -> {
									try {
										ctrl.importar(graf, dir);
									} catch (FileNotFoundException e1) {
										return new ErrorMessage("No s'ha trobat el conjunt de dades!\n"
												+ "Comprova que has introdu\u00EFt correctament el directori i el nom.",
												"Error a l'importar", false);
									} catch (Exception e2) {
										return new ErrorMessage(e2.getMessage(), "Error a l'importar", false);
									}
									return null;
								},
								(err) -> {
									if (err != null)
										err.show();
									else
										ctrl.getSelectorConjunts().update();
									setEnabled(true);
									return err == null;
								},
								"Important dades...",
								"Dades carregades correctament!",
								"Error!").call();
					}
				}
			}
		});
		btnAcceptar.setBounds(156, 127, 143, 29);
		btnAcceptar.setEnabled(false);
		contentPane.add(btnAcceptar);

		directoriField = new JTextField();
		directoriField.setBounds(88, 49, 211, 28);
		contentPane.add(directoriField);
		directoriField.setColumns(10);

		DocumentListener textListener = new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				blockFields();
			}

			public void removeUpdate(DocumentEvent e) {
				blockFields();
			}

			public void insertUpdate(DocumentEvent e) {
				blockFields();
			}

			private void blockFields() {
				if (directoriField.getDocument().getLength() == 0 || nomField.getDocument().getLength() == 0)
					btnAcceptar.setEnabled(false);
				else
					btnAcceptar.setEnabled(true);
			}
		};

		directoriField.getDocument().addDocumentListener(textListener);
		nomField.getDocument().addDocumentListener(textListener);

		JLabel lblDirectori = new JLabel("Directori:");
		lblDirectori.setBounds(20, 55, 75, 16);
		contentPane.add(lblDirectori);
	}

	private String selectedDir() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		int ret = fc.showOpenDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile().getPath();
		}
		else return null;
	}
}
