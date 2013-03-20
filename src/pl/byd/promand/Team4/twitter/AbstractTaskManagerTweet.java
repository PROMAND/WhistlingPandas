package pl.byd.promand.Team4.twitter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.utils.Constants;
import pl.byd.promand.Team4.utils.Utils;

/**
 * 
 * Task manager custom tweet object
 * 
 * @author veskikri
 *
 */
public abstract class AbstractTaskManagerTweet {
	
	/**
	 * Type of the tweet
	 */
	private final TweetType type;
	
	/**
	 * 
	 * Constructor for custom tweet
	 * 
	 * @param type Tweet type
	 */
	public AbstractTaskManagerTweet(TweetType type) {
		this.type = type;
	}
	
	/**
	 * 
	 * Marshals tweet into custom tweet <code>String</code> format
	 * 
	 * @return This tweet as a string
	 */
	public abstract String getTweet();
	
	/**
	 * 
	 * Retrieves the custom tweet type 
	 * 
	 * @return Type
	 */
	public TweetType getType() {
		return type;
	}
	
	/**
	 * 
	 * Parses project tweet represented by <code>String</code>
	 * 
	 * @param tweet Custom-formatted tweet
	 * @return Custom tweet object
	 */
	public static AbstractTaskManagerTweet parseTweet(String tweet) {
		String[] strings = tweet.split(Constants.SEPARATOR, -1);
		TweetType type = TweetType.valueOf(strings[0]);
		switch (type) {
		case NP:
			// TODO Where get yourself from? 
			String projectName = strings[1];
			Project project = new Project(projectName, new ArrayList<String>());
			return new NewProjectTweet(project);
		case AM:
			String memberName = strings[1];
			return new AddMemberTweet(memberName);
		case CT:
			Task addedTask = CreateTaskTweet.parseTask(strings);
			return new CreateTaskTweet(addedTask);
		case UT:
			Task update = UpdateTaskTweet.parseTaskUpdate(strings);
			return new UpdateTaskTweet(update);
		default:
			throw new RuntimeException("Unknown tweet type: " + type);
		}
	}

}
