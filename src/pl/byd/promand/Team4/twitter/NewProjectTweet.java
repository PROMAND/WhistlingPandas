package pl.byd.promand.Team4.twitter;

import pl.byd.promand.Team4.utils.Constants;

public class NewProjectTweet extends AbstractTaskManagerTweet {
	
	private final String projectName;
	
	public NewProjectTweet(String projectName) {
		super(TweetType.NP);
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public String getTweet() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType());
		// Member name
		sb.append(Constants.SEPARATOR);
		sb.append(getProjectName());
		return sb.toString();
	}

}
