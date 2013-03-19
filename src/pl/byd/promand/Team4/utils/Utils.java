package pl.byd.promand.Team4.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.widget.RadioGroup;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.twitter.AbstractTaskManagerTweet;
import pl.byd.promand.Team4.twitter.TweetType;
import pl.byd.promand.Team4.twitter.UpdateTaskTweet;

/**
 * 
 * Helper methods
 * 
 * @author veskikri
 *
 */
public class Utils {
	
	/**
	 * Common formatter for date objects
	 */
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
	
	/**
	 * Id counter for populating tasks list with test data 
	 */
	private static int counter = 0;
	
	/**
	 * Helps to generate random test data
	 */
	private static Random r = new Random();
	
	/**
	 * 
	 * Retrieves the next test task id
	 * 
	 * @return Id
	 */
	public static int getNextTestDataTaskId() {
		return counter++;
	}
	
	/**
	 * Retrieves the common formatter for date fields
	 * 
	 * @return Date formatter
	 */
	public static SimpleDateFormat getDateformatter() {
		return dateFormatter;
	}
	
	/**
	 * 
	 * Parses date from string
	 * 
	 * @param dateString Date as a <code>String</code>
	 * 
	 * @return Date as a <code>Date</code> object
	 */
	public static Date parseDateFromString(String dateString) {
		try {
			return getDateformatter().parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("Parsing date failed", e);
		}
	}
	
	/**
	 * 
	 * Converts a date as <code>Date</code> object into <code>String</code>
	 * 
	 * @param date Date
	 * @return Converted value
	 */
	public static String convertToString(Date date) {
		return getDateformatter().format(date);
	}
	
	/**
	 * 
	 * Populates tasks list with test data
	 * 
	 * @param tasksList
	 */
	public static void populateWithTestData(Map<Long, Task> tasksList) {
		// Collection<ITaskListItem> paramList = new ArrayList<ITaskListItem>();
		// List<String> members = MainModel.getInstance().getProject().getMembers();
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
				Task task = new Task(name , "Person " + counter, "Person " + 1, "Description " + counter, Calendar
						.getInstance().getTime(), deadLine ,
						priority, type, state);
				task.setId(Long.valueOf(counter));
				// paramList.add(task);
				tasksList.put(task.getId(), task);
				}
			}
		}
	}

	/**
	 * 
	 * Adds separators to a task list for showing in main task view (e.g. adds a "Finished" separator befor the tasks in finished state)
	 * <br /><br />
	 * If a task state is not present, the separator will not be added
	 * 
	 * @param tasksList List of tasks without the separators
	 * @return List of separators
	 */
	public static List<TaskListSeparator> getSeparators(List<Task> tasksList) {
		List<TaskListSeparator> separators = new ArrayList<TaskListSeparator>();
		List<TaskState> states = new ArrayList<TaskState>();
		for (ITaskListItem item : tasksList) {
			if (item instanceof Task) {
				Task cur = (Task)item;
				TaskState curState = cur.getState();
				states.add(curState);
			}
		}
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
		return separators;
		// separators.add(TaskListSeparator.SEPARATOR_IN_PROGRESS);
		// separators.add(TaskListSeparator.SEPARATOR_ASSIGNED);
		// separators.add(TaskListSeparator.SEPARATOR_REJECTED);
		
		// tasksList2.addAll(separators);
		/*
		for (TaskListSeparator cur: separators) {
			int keyInt = cur.getState().ordinal() + 1;
			keyInt = -keyInt;
			Long key = Long.valueOf(keyInt);
			tasksList.put(key, cur);
		}
		*/
	}

	/**
	 * 
	 * Generates a test project
	 * 
	 * @return Project
	 */
	public static Project getTestProject() {
		String projectName = "UI revamp";
		List<String> membersList = new ArrayList<String>();
		for (int i = 1; i <= counter; i++) {
			String personName = "Person " + i;
			membersList.add(personName);
			
		}
		Project ret = new Project(projectName, membersList, membersList.get(0));
		return ret;
	}

	/**
	 * 
	 * Creates random updates, marshals them into <code>String</code>
	 * 
	 * @param tasksMap
	 */
	public static void updateTasks(Map<Long, Task> tasksMap) {
		List<UpdateTaskTweet> updates = new ArrayList<UpdateTaskTweet>();
		for (Task task : tasksMap.values()) {
			boolean toUpdate = r.nextBoolean();
			if (toUpdate) {
				String assignee = null;
				boolean changeAssignee = r.nextBoolean();
				if (changeAssignee) {
					assignee = "Code man";
				}
			String title = "U " + task.getTitle(),    
					creator = null, // Creator cannot be changed 
					description = "U " + task.getDescription();
			Date created = null; // created cannot be changed
			Date deadLine = null; 
			boolean changeDeadline = r.nextBoolean();
			if (changeDeadline) {
				Calendar deadLineCalendar = Calendar.getInstance();
				deadLineCalendar.set(Calendar.DATE, 1);
				deadLineCalendar.set(Calendar.MONTH, 1);
				deadLineCalendar.set(Calendar.YEAR, 1999);
				deadLine = deadLineCalendar.getTime();
			}
			TaskPriority priority = TaskPriority.values()[r.nextInt(TaskPriority.values().length)]; 
			TaskType type = TaskType.values()[r.nextInt(TaskType.values().length)]; 
			TaskState state =TaskState.values()[r.nextInt(TaskState.values().length)]; 
			Task change = new Task( title,  assignee,  creator,  description,
					 created,  deadLine,  priority,  type,  state);
			change.setId(task.getId());
			UpdateTaskTweet utt = new UpdateTaskTweet(change);
			updates.add(utt);
			}
		}
		List<String> updateStrings = new ArrayList<String>();
		for (UpdateTaskTweet utt : updates) {
			updateStrings.add(utt.getTweet());
		}
		for (String s : updateStrings) {
		UpdateTaskTweet utt = (UpdateTaskTweet) AbstractTaskManagerTweet.parseTweet(s);
		updateTask(tasksMap, utt );
		}
		
	}

	private static void updateTask(Map<Long, Task> tasksMap, UpdateTaskTweet utt) {
		TweetType tt = utt.getType();
		if (!tt.equals(TweetType.UT)) {
			throw new IllegalArgumentException("Not an update: " + tt);
		}
		Task update = utt.getTask();
		Long id = update.getId();
		if (id == null) {
			throw new IllegalArgumentException("Id is not set");
		}
		Task task = tasksMap.get(id);
		if (task == null) {
			throw new IllegalArgumentException("Task with id " + id + " not found");
		}
		String newAssignee = update.getAssignee();
		if (newAssignee != null && newAssignee.length() > 0) {
			task.setAssignee(newAssignee);
		}
		Date newCreated = update.getCreated();
		if (newCreated != null) {
			task.setCreated(newCreated);
		}
		String newCreator = update.getCreator();
		if (newCreator != null && newCreator.length() > 0) {
			task.setCreator(newCreator);
		}
		Date newDeadline = update.getDeadLine(); 
		if (newDeadline != null) {
			task.setDeadLine(newDeadline);
		}
		String newDescription = update.getDescription();
		if (newDescription != null) {
			task.setDescription(newDescription);
		}
		TaskPriority newPriority = update.getPriority();
		if (newPriority != null) {
			task.setPriority(newPriority);
		}
		TaskState newState = update.getState();
		if (newState != null) {
			task.setState(newState);
		}
		String newTitle = update.getTitle();
		if (newTitle != null && newTitle.length() > 0) {
			task.setTitle(newTitle);
		}
		TaskType newType = update.getType();
		if (newType != null) {
			update.setType(newType );
		}
	}

}
