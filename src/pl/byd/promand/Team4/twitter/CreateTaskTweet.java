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

public class CreateTaskTweet extends AbstractTaskManagerTweet {

	private final Task task;

	public CreateTaskTweet(Task addedTask) {
		super(TweetType.CT);
		this.task = addedTask;
	}
	
	public Task getTask() {
		return task;
	}

	public static Task parseTask(String[] strings) {
		// TODO ask task id
		int idx = 1;
		String priorityString = strings[idx++];
		TaskPriority priority = TaskPriority.valueOf(priorityString);
		String stateString = strings[idx++];
		TaskState state = TaskState.valueOf(stateString);
		String typeString = strings[idx++];
		TaskType taskType = TaskType.valueOf(typeString);
		String assignee = strings[idx++];
		String creator = strings[idx++];
		String deadLineString = strings[idx++];
		Date deadLine = Utils.parseDateFromString(deadLineString);
		String title = strings[idx++];
		StringBuilder descriptionBuilder = new StringBuilder();
		for (int i = idx; i < strings.length; i++) {
			descriptionBuilder.append(strings[i]);
		}
		String description = descriptionBuilder.toString();
		// TODO get created from Twitter API
		Date created = Calendar.getInstance().getTime();
		Task ret = new Task(title, assignee, creator, description, created, deadLine, priority, taskType, state);
		return ret;
	}

	@Override
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
		sb.append(getTask().getCreator());
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
