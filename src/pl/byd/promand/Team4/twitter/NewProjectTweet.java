package pl.byd.promand.Team4.twitter;

import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.utils.Constants;

public class NewProjectTweet extends AbstractTaskManagerTweet {
	
	private final Project project;
	
	public NewProjectTweet(Project project) {
		super(TweetType.NP);
		this.project = project;
	}
	
	public Project getProject() {
		return project;
	}

	@Override
	public String getTweet() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType());
		// Member name
		sb.append(Constants.SEPARATOR);
		sb.append(getProject().getName());
		return sb.toString();
	}

}
