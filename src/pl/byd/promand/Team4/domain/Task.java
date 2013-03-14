package pl.byd.promand.Team4.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;

/**
 * 
 * Represents one task
 * 
 * @author veskikri
 *
 */
public class Task implements ITaskListItem {

	private String title, assignee, description;
	
	private Date created, deadLine;
	
	private TaskPriority priority;
	
	private TaskType type;
	
	private TaskState state;

	public Task(String title, String assignee, String description,
			Date created, Date deadLine, TaskPriority priority, TaskType type, TaskState state) {
		super();
		this.title = title;
		this.assignee = assignee;
		this.description = description;
		this.created = created;
		this.deadLine = deadLine;
		this.priority = priority;
		this.type = type;
		this.state = state;
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

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public String getFormattedDeadline() {
		SimpleDateFormat dt1 = new SimpleDateFormat("dd.mm.yyyyy");
		String ret = dt1.format(getDeadLine());
		return ret;
	}

	@Override
	public int compareTo(ITaskListItem another) {
		int thisState = this.getState().ordinal();
		if (another instanceof TaskListSeparator) {
			TaskListSeparator anotherSeparator = (TaskListSeparator)another;
			int diff = thisState - anotherSeparator.getState().ordinal();
			if (Math.abs(diff) > 0) {
			diff = -diff;
			return  diff;
			}
			return +1;
		} else if (another instanceof Task) {
			Task anotherTask = (Task)another;
			int diff = thisState - anotherTask.getState().ordinal();	
			if (Math.abs(diff) > 0) {
				return diff;
			} else {
				int priorityDiff = this.getPriority().ordinal() - anotherTask.getPriority().ordinal();
				if (Math.abs(priorityDiff) > 0) {
				return priorityDiff;
				} else {
					return this.getDeadLine().compareTo(anotherTask.getDeadLine());
				}
			}
		}
		throw new RuntimeException("Unknown task item class: " + another.getClass().getCanonicalName());
	}
	
	@Override
	public int hashCode() {
		int ret = this.getState().ordinal() * 10 + this.getPriority().ordinal();
		return super.hashCode();
	}

}
