package pl.byd.promand.Team4.twitter;

import pl.byd.promand.Team4.domain.Task;

public class UpdateTaskTweet  extends AbstractTaskManagerTweet {

	private final Task task;

	public UpdateTaskTweet(Task addedTask) {
		super(TweetType.UT);
		this.task = addedTask;
	}
	
	public Task getTask() {
		return task;
	}
}
