package pl.byd.promand.Team4.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;

public class Utils {
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.mm.yyyyy");
	
	// Counter for test tasks population
	private static int counter = 1;
	
	public static SimpleDateFormat getDateformatter() {
		return dateFormatter;
	}
	
	public static Date parseDateFromString(String dateString) throws ParseException {
		return getDateformatter().parse(dateString);
	}
	
	public static String convertToString(Date date) {
		return getDateformatter().format(date);
	}


	public static void populateWithTestData(Collection<ITaskListItem> paramList) {
		for (TaskType type : TaskType.values()) {
			for (TaskPriority priority : TaskPriority.values()) {
				for (TaskState state : TaskState.values()) {
				String name = state + " - " + priority + " " + counter;
				Task task = new Task(name , "Person " + counter, "Description " + counter, Calendar
						.getInstance().getTime(), Calendar.getInstance().getTime(),
						priority, type, state);
				paramList.add(task);
				counter++;
				}
			}
		}
	}


	public static void addSeparators(List<ITaskListItem> tasksList2) {
		List<TaskState> states = new ArrayList<TaskState>();
		for (ITaskListItem item : tasksList2) {
			if (item instanceof Task) {
				Task cur = (Task)item;
				TaskState curState = cur.getState();
				states.add(curState);
			}
		}
		List<TaskListSeparator> separators = new ArrayList<TaskListSeparator>();
		for (TaskState state : states) {
			switch (state) {
			case A:
				if (!separators.contains(TaskListSeparator.SEPARATOR_ASSIGNED)) {
					separators.add(TaskListSeparator.SEPARATOR_ASSIGNED);
				}
				break;
			case F:
				if (!separators.contains(TaskListSeparator.SEPARATOR_FINISHED)) {
					separators.add(TaskListSeparator.SEPARATOR_FINISHED);
				}
				break;
			case IP:
				if (!separators.contains(TaskListSeparator.SEPARATOR_IN_PROGRESS)) {
					separators.add(TaskListSeparator.SEPARATOR_IN_PROGRESS);
				}
				break;
			case R:
				if (!separators.contains(TaskListSeparator.SEPARATOR_REJECTED)) {
					separators.add(TaskListSeparator.SEPARATOR_REJECTED);
				}
				break;

			default:
				throw new RuntimeException("Unsupported state: " + state);
			}
		}
		/*
		separators.add(TaskListSeparator.SEPARATOR_IN_PROGRESS);
		separators.add(TaskListSeparator.SEPARATOR_ASSIGNED);
		separators.add(TaskListSeparator.SEPARATOR_REJECTED);
		*/
		tasksList2.addAll(separators);
	}

}
