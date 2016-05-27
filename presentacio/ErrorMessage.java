package presentacio;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ErrorMessage {
	
	public ErrorMessage(String message) {
		this(message, "Error");
	}
	
	public ErrorMessage(String message, String title) {
		this(null, message, title);
	}
	
	public ErrorMessage(Component parentComponent, String message, String title) {
		JOptionPane.showMessageDialog(parentComponent, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
}
