package pl.byd.promand.Team4.twitter;

import java.util.Calendar;
import java.util.Date;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.utils.Utils;

public abstract class TaskTweet {
	
	private final TweetType type;
	
	public TaskTweet(TweetType type) {
		this.type = type;
	}
	
	public TweetType getType() {
		return type;
	}
	
	public static TaskTweet parseTweet(String tweet) {
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
			String priorityString = strings[1];
			TaskPriority priority = TaskPriority.valueOf(priorityString);
			String stateString = strings[2];
			TaskState state = TaskState.valueOf(stateString);
			String typeString = strings[3];
			TaskType taskType = TaskType.valueOf(typeString);
			String assignee = strings[4];
			String deadLineString = strings[5];
			Date deadLine = Utils.parseDateFromString(deadLineString);
			String title = strings[6];
			StringBuilder descriptionBuilder = new StringBuilder();
			for (int i = 7; i < strings.length; i++) {
				descriptionBuilder.append(strings[i]);
			}
			String description = descriptionBuilder.toString();
			// TODO get created from Twitter API
			Date created = Calendar.getInstance().getTime();
			Task addedTask = new Task(title, assignee, description, created, deadLine, priority, taskType, state);
			return new CreateTaskTweet(addedTask);
		case UT:
			// TODO
		default:
			throw new RuntimeException("Unknown tweet type: " + type);
		}
	}

}
