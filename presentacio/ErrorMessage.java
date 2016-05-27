package presentacio;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ErrorMessage extends Message {
	
	static {
		defaultTitle = "Error";
	}
	
	public ErrorMessage(String message) {
		this(message, defaultTitle);
	}
	
	public ErrorMessage(String message, String title) {
		this(message, title, true);
	}
	
	public ErrorMessage(String message, String title, boolean executeNow) {
		this(null, message, title, executeNow);
	}
	
	public ErrorMessage(Component parentComponent, String message) {
		this(parentComponent, message, defaultTitle, true);
	}
	
	public ErrorMessage(Component parentComponent, String message, String title) {
		this(parentComponent, message, title, true);
	}
	
	public ErrorMessage(Component parentComponent, String message, String title, boolean executeNow) {
		super(parentComponent, message, title, JOptionPane.ERROR_MESSAGE, executeNow);
	}
	
}
