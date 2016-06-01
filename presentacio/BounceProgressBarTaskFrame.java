package presentacio;

import java.awt.Image;
import java.util.concurrent.Callable;

import javax.swing.JFrame;

import presentacio.BounceProgressBarTask.TaskListener;

public class BounceProgressBarTaskFrame<V> extends JFrame implements Callable<Void> {

	private static final long serialVersionUID = -7295456121065738601L;

	private BounceProgressBarTask<V> barTask;
	
	public BounceProgressBarTaskFrame(Callable<V> task, TaskListener<V> listener, String progressLabel, String successLable, String failLabel) {
		MenuPrincipal.setDefaultStyle();
		barTask = new BounceProgressBarTask<>(this, task, listener, progressLabel, successLable, failLabel);
		getContentPane().add(barTask);
	}
	
	public BounceProgressBarTaskFrame(Callable<V> task) {
		this(task, "");
	}
	
	public BounceProgressBarTaskFrame(Callable<V> task, String progressLabel, String successLabel, String failLabel) {
		this(task, null, progressLabel, successLabel, failLabel);
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
	
	public BounceProgressBarTaskFrame(String title, Callable<V> task, String progressLabel, String successLabel, String failLabel) {
		this(title, task, null, progressLabel, successLabel, failLabel);
	}
	
	public BounceProgressBarTaskFrame(Image icon, String title, Callable<V> task, TaskListener<V> listener, String progressLabel, String successLabel, String failLabel) {
		this(task, listener, progressLabel, successLabel, failLabel);
		setTitle(title);
		setIconImage(icon);
	}
	
	public BounceProgressBarTaskFrame(Image icon, String title, Callable<V> task, String progressLabel, String successLabel, String failLabel) {
		this(icon, title, task, null, progressLabel, successLabel, failLabel);
	}
	
	public BounceProgressBarTaskFrame(Image icon, String title, Callable<V> task, String progressLabel) {
		this(icon, title, task, null, progressLabel, "", "");
	}

	@Override
	public Void call() {
		return barTask.call();
	}

}
