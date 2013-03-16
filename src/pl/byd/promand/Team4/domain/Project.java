package pl.byd.promand.Team4.domain;

import java.util.List;

public class Project {
	
	private final String 
	/**
	 * Name of the project
	 */
	name,
	/**
	 * Your own username
	 */
	yourself
	;
	
	private final List<String> members;
	
	public Project(String name, List<String> members, String yourself) {
		this.name = name;
		this.members = members;
		this.yourself = yourself;
	}

	public String getName() {
		return name;
	}

	public List<String> getMembers() {
		return members;
	}
	
	public String getYourself() {
		return yourself;
	}

}
