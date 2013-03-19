package pl.byd.promand.Team4.twitter;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.utils.Constants;
import pl.byd.promand.Team4.utils.MainModel;

public class UpdateTaskTweet  extends AbstractTaskManagerTweet {

	private final Task task;

	public UpdateTaskTweet(Task addedTask) {
		super(TweetType.UT);
		this.task = addedTask;
	}
	
	public Task getTask() {
		return task;
	}
	
	/*
	 * 
	public String getTweet() {
		// Deadline
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getFormattedDeadline());
		// Title
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getTitle());
		// Description
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getDescription());
		return sb.toString();
	}
	 * 
	 * */
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
	
}
