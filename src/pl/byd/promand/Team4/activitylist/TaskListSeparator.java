package pl.byd.promand.Team4.activitylist;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskState;

public class TaskListSeparator implements ITaskListItem {

	public static final TaskListSeparator
	// Assigned
	SEPARATOR_ASSIGNED = new TaskListSeparator("Assigned", TaskState.A),
			// In progress
			SEPARATOR_IN_PROGRESS = new TaskListSeparator("In progress", TaskState.S),
			SEPARATOR_FINISHED = new TaskListSeparator("Finished", TaskState.F),
			SEPARATOR_REJECTED = new TaskListSeparator("Rejected", TaskState.RE);

	private final String separationText;
	
	private final TaskState state;

	private TaskListSeparator(final String sepString, final TaskState state) {
		this.separationText = sepString;
		this.state = state;
	}

	public String getSeparationText() {
		return separationText;
	}

	public TaskState getState() {
		return state;
	}

	@Override
	public int compareTo(ITaskListItem another) {
		int thisState = this.getState().ordinal();
		if (another instanceof TaskListSeparator) {
			TaskListSeparator anotherSeparator = (TaskListSeparator)another;
			return  thisState - anotherSeparator.getState().ordinal();
		} else if (another instanceof Task) {
			Task anotherTask = (Task)another;
			int diff = thisState - anotherTask.getState().ordinal();
			if (Math.abs(diff) > 0) {
				return diff;
			}
			return -1;
		}
		throw new RuntimeException("Unknown task item class: " + another.getClass().getCanonicalName());
	}

}
