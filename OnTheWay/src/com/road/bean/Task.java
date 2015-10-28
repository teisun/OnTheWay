package com.road.bean;

/**
 * 拥有id的任务
 * @author zhou
 *
 */
public abstract class Task implements Runnable {
	
	protected int taskId;

	public Task(int id) {
		this.taskId = id;
	}
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	
	
}
