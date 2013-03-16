package pl.byd.promand.Team4.domain;

import pl.byd.promand.Team4.R;

public enum TaskState {
	
	// Keep in the order as in tasks list view!
	
	// In progress
	S(R.string.taskstate_started), 
	// Rejected
	RE(R.string.taskstate_rejected),
	// Assigned
	A(R.string.taskstate_assigned), 
	// Finished
	F(R.string.taskstate_finished);
	
	private final int formString;

	private TaskState(int formString) {
		this.formString = formString;
	}
	
	public int getFormString() {
		return formString;
	}

}
