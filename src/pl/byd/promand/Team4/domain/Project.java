package pl.byd.promand.Team4.domain;

import java.util.List;

public class Project {
	
	private final String 
	/**
	 * Name of the project
	 */
	name;
	
	private final List<String> members;
	
	public Project(String name, List<String> members) {
		this.name = name;
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public List<String> getMembers() {
		return members;
	}

}
