package presentacio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JScrollPane;

public class TaskConsole<V> extends JFrame implements Callable<Void> {

	private static final long serialVersionUID = 6048639893281544248L;
	
	private Callable<V> task;
	private TaskListener<V> listener;
	
	private JTextArea textArea;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TaskConsole<>("Test", () -> {
						for (int i = 1; i <= 100; ++i) {
							System.err.println(i + "%");
							try { Thread.sleep(50); } catch (Exception ignore) {}
						}
						System.err.println("Done!");
						try { Thread.sleep(1000); } catch (Exception ignore) {}
						return null;
					}).call();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public TaskConsole(String title, Callable<V> task) {
		this(null, title, task, null);
	}
	
	public TaskConsole(Image icon, String title, Callable<V> task) {
		this(icon, title, task, null);
	}
	
	public TaskConsole(Image icon, String title, Callable<V> task, TaskListener<V> listener) {
		this.task = task;
		this.listener = listener;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(600, 400);
		setTitle(title);
		setIconImage(icon == null ? ControladorPresentacio.ICON_MAIN : icon);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setForeground(Color.BLUE);
		getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(textArea);
		getContentPane().add(scrollPane);
	}
	
	private class Console extends OutputStream {
		@Override
		public void write(int b) throws IOException {
			textArea.append(String.valueOf((char)b));
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

	@Override
	public Void call() {
		new Thread(() -> {
			if (task != null) {
				setVisible(true);
				PrintStream out = System.out, err = System.err;
				PrintStream printStream = new PrintStream(new Console());
				try {
					System.setOut(printStream);
					System.setErr(printStream);
					V result = task.call();
					if (listener != null)
						listener.onDone(result);
					dispose();
				} catch (Exception e) {
					textArea.setForeground(Color.RED);
					e.printStackTrace();
				} finally {
					System.setOut(out);
					System.setErr(err);
				}
			}
		}).start();
		return null;
	}
	
	@FunctionalInterface
	public interface TaskListener<V> {
		void onDone(V result);
	}
}
