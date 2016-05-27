package presentacio;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Message {

	protected static String defaultTitle = "Informació";
	
	private Component parentComponent;
	private String message, title;
	private int messageType;
	
	public Message(String message) {
		this(message, defaultTitle);
	}
	
	public Message(String message, String title) {
		this(null, message, title, JOptionPane.INFORMATION_MESSAGE, true);
	}
	
	public Message(String message, String title, boolean executeNow) {
		this(null, message, title, executeNow);
	}
	
	public Message(Component parentComponent, String message) {
		this(parentComponent, message, defaultTitle);
	}
	
	public Message(Component parentComponent, String message, String title) {
		this(parentComponent, message, title, true);
	}
	
	public Message(Component parentComponent, String message, String title, boolean executeNow) {
		this(parentComponent, message, title, JOptionPane.INFORMATION_MESSAGE, executeNow);
	}
	
	public Message(Component parentComponent, String message, String title, int messageType, boolean executeNow) {
		this.parentComponent = parentComponent;
		this.message = message;
		this.title = title;
		this.messageType = messageType;
		if (executeNow)
			show();
	}
	
	public void show() {
		JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
	}
	
}
