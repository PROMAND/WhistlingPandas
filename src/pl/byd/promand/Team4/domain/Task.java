package pl.byd.promand.Team4.domain;

import java.util.Date;

public class Task {

	private String title, assignee, description;
	
	private Date created, deadLine;
	
	private TaskPriority priority;
	
	private TaskType type;

	public Task(String title, String assignee, String description,
			Date created, Date deadLine, TaskPriority priority, TaskType type) {
		super();
		this.title = title;
		this.assignee = assignee;
		this.description = description;
		this.created = created;
		this.deadLine = deadLine;
		this.priority = priority;
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

}
