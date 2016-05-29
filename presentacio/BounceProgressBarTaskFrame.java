package presentacio;

import java.awt.Image;
import java.util.concurrent.Callable;

import javax.swing.JFrame;

import presentacio.BounceProgressBarTask.TaskListener;

public class BounceProgressBarTaskFrame<V> extends JFrame implements Callable<Void> {

	private static final long serialVersionUID = -7295456121065738601L;

	private BounceProgressBarTask<V> barTask;
	
	public static void main(String[] args) {
		try {
		new BounceProgressBarTaskFrame<Integer>("Exemple",
				() -> {
					Thread.sleep(5000);
					return 3;
				},
				(result) -> {
					System.out.println(result);
					return true;
				},
				"Treballant...",
				"Finalitzat!",
				"Error!").call();
		} catch (Exception e) {
			new ErrorMessage(e.getMessage());
		}
	}
	
	public BounceProgressBarTaskFrame(Callable<V> task, TaskListener<V> listener, String progressLabel, String successLable, String failLabel) {
		barTask = new BounceProgressBarTask<>(this, task, listener, progressLabel, successLable, failLabel);
		getContentPane().add(barTask);
	}
	
	public BounceProgressBarTaskFrame(Callable<V> task) {
		this(task, "");
	}
	
	public BounceProgressBarTaskFrame(Callable<V> task, String progressLabel) {
		this(task, null, progressLabel, "", "");
	}
	
	public BounceProgressBarTaskFrame(Callable<V> task, TaskListener<V> listener, String progressLabel) {
		this(task, listener, progressLabel, "", "");
	}
	
	public BounceProgressBarTaskFrame(String title, Callable<V> task, TaskListener<V> listener, String progressLabel, String successLabel, String failLabel) {
		this(task, listener, progressLabel, successLabel, failLabel);
		setTitle(title);
	}
	
	public BounceProgressBarTaskFrame(Image icon, String title, Callable<V> task, TaskListener<V> listener, String progressLabel, String successLabel, String failLabel) {
		this(task, listener, progressLabel, successLabel, failLabel);
		setTitle(title);
		setIconImage(icon);
	}

	@Override
	public Void call() {
		return barTask.call();
	}

}
