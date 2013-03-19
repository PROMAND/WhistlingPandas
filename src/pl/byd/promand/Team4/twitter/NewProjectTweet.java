package pl.byd.promand.Team4.twitter;

public class NewProjectTweet extends AbstractTaskManagerTweet {
	
	private final String projectName;
	
	public NewProjectTweet(String projectName) {
		super(TweetType.NP);
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		return projectName;
	}

}