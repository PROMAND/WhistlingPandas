package pl.byd.promand.Team4.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.widget.RadioGroup;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;

public class Utils {
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyyy");
	
	// Counter for test tasks population
	private static int counter = 0;
	
	private static Random r = new Random();
	
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
					counter++;
				String name = state + " - " + priority + " " + counter;
				Calendar cal = Calendar.getInstance();
				
				int year = cal.get(Calendar.YEAR) - r.nextInt(5);
				int month = r.nextInt(11);
				int date = r.nextInt(27);

		        cal.set(Calendar.YEAR, year);
		        cal.set(Calendar.MONTH, month);
		        cal.set(Calendar.DATE, date);
				/*
				long diff = 100000 * (type.ordinal() + 1) * (priority.ordinal() + 1) * (state.ordinal());
				long newTime = cal.getTimeInMillis() - diff;
				cal.setTimeInMillis(newTime);//.add(Calendar.DAY_OF_MONTH, -diff );
		        */
				Date deadLine = cal.getTime();
				Task task = new Task(name , "Person " + counter, "Description " + counter, Calendar
						.getInstance().getTime(), deadLine ,
						priority, type, state);
				paramList.add(task);
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
			case S:
				if (!separators.contains(TaskListSeparator.SEPARATOR_IN_PROGRESS)) {
					separators.add(TaskListSeparator.SEPARATOR_IN_PROGRESS);
				}
				break;
			case RE:
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

	public static Project getTestProject() {
		String projectName = "UI revamp";
		List<String> membersList = new ArrayList<String>();
		/*
		membersList.add("Kristjan");
		membersList.add("Macija");
		membersList.add("Gitautas");
		membersList.add("Alina");
		*/
		for (int i = 1; i <= counter; i++) {
			membersList.add("Person " + i);
		}
		Project ret = new Project(projectName, membersList, membersList.get(0));
		return ret;
	}

}
