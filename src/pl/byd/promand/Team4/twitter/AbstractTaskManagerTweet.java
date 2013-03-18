package pl.byd.promand.Team4.twitter;

import java.util.Calendar;
import java.util.Date;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.utils.Utils;

public abstract class AbstractTaskManagerTweet {
	
	private final TweetType type;
	
	public AbstractTaskManagerTweet(TweetType type) {
		this.type = type;
	}
	
	public TweetType getType() {
		return type;
	}
	
	public static AbstractTaskManagerTweet parseTweet(String tweet) {
		String[] strings = tweet.split(",");
		TweetType type = TweetType.valueOf(strings[0]);
		switch (type) {
		case NP:
			String projectName = strings[1];
			return new NewProjectTweet(projectName);
		case AM:
			String memberName = strings[1];
			return new AddMemberTweet(memberName);
		case CT:
			Task addedTask = parseTask(strings);
			return new CreateTaskTweet(addedTask);
		case UT:
			Task update = parseTaskUpdate(strings);
			return new UpdateTaskTweet(update);
		default:
			throw new RuntimeException("Unknown tweet type: " + type);
		}
	}

	private static Task parseTaskUpdate(String[] strings) {
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

	private static Task parseTask(String[] strings) {
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

}
