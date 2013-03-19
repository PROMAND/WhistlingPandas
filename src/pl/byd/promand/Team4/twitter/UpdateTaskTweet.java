package pl.byd.promand.Team4.twitter;

import java.util.Calendar;
import java.util.Date;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.utils.Constants;
import pl.byd.promand.Team4.utils.MainModel;
import pl.byd.promand.Team4.utils.Utils;

public class UpdateTaskTweet  extends AbstractTaskManagerTweet {

	private final Task task;

	public UpdateTaskTweet(Task addedTask) {
		super(TweetType.UT);
		this.task = addedTask;
	}
	
	public Task getTask() {
		return task;
	}
	
	public String getTweet() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType());
		// Id
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getId());
		// Priority
		sb.append(Constants.SEPARATOR);
		if (getTask().getPriority() != null) {
			sb.append(getTask().getPriority());
		}
		// State
		sb.append(Constants.SEPARATOR);
		if (getTask().getState() != null) {
			sb.append(getTask().getState());
		}
		// Type
		sb.append(Constants.SEPARATOR);
		if (getTask().getType() != null) {
			sb.append(getTask().getType());
		}
		// Assignee
		sb.append(Constants.SEPARATOR);
		if (getTask().getAssignee()!= null && getTask().getAssignee().length() > 0) {
			sb.append(getTask().getAssignee());
		}
		// Creator - cannot be changed
		// Deadline
		sb.append(Constants.SEPARATOR);
		if (getTask().getDeadLine() != null) {
		sb.append(getTask().getFormattedDeadline());
		}
		// Title
		sb.append(Constants.SEPARATOR);
		if (getTask().getTitle() != null && getTask().getTitle().length() > 0) {
			sb.append(getTask().getTitle());
		}
		// Description
		sb.append(Constants.SEPARATOR);
		if (getTask().getDescription() != null) {
			sb.append(getTask().getDescription());
		}
		return sb.toString();
	}
	
	public static Task parseTaskUpdate(String[] strings) {
		// TODO ask task id
		int idx = 1;
		
		// id - compulsory
		String idString = strings[idx++];
		Long id = Long.valueOf(idString);
		
		// new priority - optional 
		String priorityString = strings[idx++];
		TaskPriority priority = null;
		if (priorityString != null && priorityString.length() > 0) {
			priority = TaskPriority.valueOf(priorityString);
		}
		
		// New state - optional
		String stateString = strings[idx++];
		TaskState state = null;
		if (stateString != null && stateString.length() > 0) {
			state = TaskState.valueOf(stateString);
		}
		
		// New task type - optional
		String typeString = strings[idx++];
		TaskType taskType = null;
		if (typeString != null && typeString.length() > 0) {
			taskType = TaskType.valueOf(typeString);
		}
		
		// Assignee - optional
		String assignee = null; 
		String newAssignee = strings[idx++];
		if (newAssignee != null && newAssignee.length() > 0) {
			assignee = newAssignee;
		}
		
		// Creator - cannot change
		String creator = null;
		
		// Deadline - optional
		String deadLineString = strings[idx++];
		Date deadLine = null;
		if (deadLineString != null && deadLineString.length() > 0) {
			deadLine = Utils.parseDateFromString(deadLineString);
		}
		
		// Title - optional
		String newTitle = strings[idx++];
		String title = null;
		if (newTitle != null && newTitle.length() > 0) {
			title = newTitle;
		}
		
		// Description - optional
		StringBuilder descriptionBuilder = new StringBuilder();
		for (int i = idx; i < strings.length; i++) {
			descriptionBuilder.append(strings[i]);
		}
		String newDescription = descriptionBuilder.toString();
		String description = null;
		if (newDescription.length() > 0) {
		 description =newDescription;
		}
		// TODO get created date from Twitter API
		Date created = Calendar.getInstance().getTime();
		Task ret = new Task(title, assignee, creator, description, created, deadLine, priority, taskType, state);
		ret.setId(id);
		return ret;
	}
	
}
