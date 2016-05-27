package presentacio;

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

	public BounceProgressBarTaskFrame(String title, Callable<V> task, TaskListener<V> listener, String progressLabel, String successLable, String failLabel) {
		super(title);
		barTask = new BounceProgressBarTask<>(this, task, listener, progressLabel, successLable, failLabel);
		getContentPane().add(barTask);
	}

	@Override
	public Void call() {
		return barTask.call();
	}

}
