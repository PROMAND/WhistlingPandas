package pl.byd.promand.Team4.twitter;

import java.util.Date;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.utils.Constants;
import pl.byd.promand.Team4.utils.MainModel;
import pl.byd.promand.Team4.utils.Utils;

public class CreateTaskTweet extends AbstractTaskManagerTweet {

	private final Task task;

	public CreateTaskTweet(Task addedTask) {
		super(TweetType.CT);
		this.task = addedTask;
	}
	
	public Task getTask() {
		return task;
	}
	
	public String getTweet() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType());
		// Priority
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getPriority());
		// State
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getState());
		// Type
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getType());
		// Assignee
		sb.append(Constants.SEPARATOR);
		sb.append(getTask().getAssignee());
		// Creator
		sb.append(Constants.SEPARATOR);
		sb.append(MainModel.getInstance().getProject().getYourself());
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

}
