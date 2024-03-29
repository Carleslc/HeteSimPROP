package presentacio;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import java.util.concurrent.Callable;

public class BounceProgressBarTask<V> extends JProgressBar implements Callable<Void> {
	
	private static final long serialVersionUID = -3253566314659371833L;
	
	private JFrame frame;
	private Callable<V> task;
	private TaskListener<V> listener;
	private String successLabel, failLabel;

	public BounceProgressBarTask(JFrame parent, Callable<V> task, TaskListener<V> listener,
			String progressLabel, String successLabel, String failLabel) {
		frame = parent;
		setMinimum(0);
		setMaximum(100);
		setIndeterminate(true);
		setBackground(UIManager.getColor("control"));
		setForeground(UIManager.getColor("info"));
		setString(progressLabel);
		setStringPainted(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(300, 85);
		
		this.task = task;
		this.listener = listener;
		this.successLabel = successLabel;
		this.failLabel = failLabel;
	}

	public BounceProgressBarTask(JFrame parent, Callable<V> task, TaskListener<V> listener) {
		this(parent, task, listener, "In progress...", "Success", "Error");
	}

	@Override
	public Void call() {
		new Thread(() -> {
			if (task != null) {
				try {
					V result = task.call();
					setIndeterminate(false);
					setValue(0);
					if (listener != null) {
						if (listener.onDone(result))
							setString(successLabel);
						else
							setString(failLabel);
					}
					else
						setString(successLabel);
				} catch(Exception ignore) {
					setString(failLabel);
				}
				String label = getString();
				if (label != null && !label.isEmpty()) {
					setForeground(UIManager.getColor("text"));
					try { Thread.sleep(1000); } catch (Exception ignore) {}
				}
			}
			frame.dispose();
		}).start();
		frame.setVisible(true);
		return null;
	}

	@FunctionalInterface
	public interface TaskListener<V> {
		boolean onDone(V result);
	}
	
}
