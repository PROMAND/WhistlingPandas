package pl.byd.promand.Team4.twitter;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskType;

public class CreateTaskTweet extends TaskTweet {

	private final Task task;

	public CreateTaskTweet(Task addedTask) {
		super(TweetType.CT);
		this.task = addedTask;
	}
	
	public Task getTask() {
		return task;
	}

}
