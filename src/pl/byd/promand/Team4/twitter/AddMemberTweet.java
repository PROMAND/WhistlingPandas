package pl.byd.promand.Team4.twitter;

import pl.byd.promand.Team4.utils.Constants;

public class AddMemberTweet extends AbstractTaskManagerTweet {
	
	private final String memberName;
	
	public AddMemberTweet(String memberName) {
		super(TweetType.AM);
		this.memberName = memberName;
	}
	
	public String getMemberName() {
		return memberName;
	}

	public String getTweet() {
		StringBuilder sb = new StringBuilder();
		sb.append(getType());
		// Member name
		sb.append(Constants.SEPARATOR);
		sb.append(getMemberName());
		return sb.toString();
	}

}
