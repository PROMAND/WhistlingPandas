package pl.byd.promand.Team4.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.twitter.AbstractTaskManagerTweet;
import pl.byd.promand.Team4.twitter.AddMemberTweet;
import pl.byd.promand.Team4.twitter.CreateTaskTweet;
import pl.byd.promand.Team4.twitter.NewProjectTweet;
import pl.byd.promand.Team4.twitter.UpdateTaskTweet;

public class TestDataPopulator {
	
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
	
	public static List<String> getMarshaledProject() {
		List<AbstractTaskManagerTweet> tweetsToMarshal = new ArrayList<AbstractTaskManagerTweet>();
		// Creating test data
		tweetsToMarshal.add(TestDataPopulator.generateNewProjectTweet());
		tweetsToMarshal.addAll(TestDataPopulator.generateTaskCreationTweets());
		tweetsToMarshal.addAll(TestDataPopulator.generateAddMemberTweets());
		tweetsToMarshal.addAll(TestDataPopulator.generateUpdateTaskTweets());
		// Test marshaling test tweets
		List<String> ret = new ArrayList<String>();
		for (AbstractTaskManagerTweet cur : tweetsToMarshal) {
			ret.add(cur.getTweet());
		}
		return ret;
	}

	/**
	 * 
	 * Generates add member tweets
	 * 
	 * @return Project
	 */
	public static List<AddMemberTweet> generateAddMemberTweets() {
		List<AddMemberTweet> ret = new ArrayList<AddMemberTweet>();
		for (int i = 1; i <= counter; i++) {
			String personName = "Person " + i;
			ret.add(new AddMemberTweet(personName));
		}
		return ret ;
	}
	
	/**
	 * 
	 * Populates tasks list with test data
	 * 
	 * @param tasksList
	 */
	public static List<CreateTaskTweet> generateTaskCreationTweets() { // List<Task> tasksList) {
		// Collection<ITaskListItem> paramList = new ArrayList<ITaskListItem>();
		// List<String> members = MainModel.getInstance().getProject().getMembers();
		List<CreateTaskTweet> ret = new ArrayList<CreateTaskTweet>();
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
				// tasksList.add(task);
				ret.add(new CreateTaskTweet(task));
				}
			}
		}
		return ret;
	}

	/**
	 * 
	 * Generates a test project
	 * 
	 * @return Project
	 */
	public static NewProjectTweet generateNewProjectTweet() {
		String projectName = "UI revamp";
		Project project = new Project(projectName, new ArrayList<String>());
		NewProjectTweet ret = new NewProjectTweet(project);
		return ret;
	}

	/**
	 * 
	 * Creates random updates
	 */
	public static List<UpdateTaskTweet> generateUpdateTaskTweets() {
		List<UpdateTaskTweet> updates = new ArrayList<UpdateTaskTweet>();
		for (int i = 0; i < counter; i++) {
			boolean toUpdate = r.nextBoolean();
			if (toUpdate) {
				String assignee = null;
				boolean changeAssignee = r.nextBoolean();
				if (changeAssignee) {
					assignee = "Code man";
				}
			String title = "U " + counter,    
					creator = null, // Creator cannot be changed 
					description = "D " + counter;
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
			change.setId(Long.valueOf(i));
			UpdateTaskTweet utt = new UpdateTaskTweet(change);
			updates.add(utt);
			}
		}
		List<String> updateStrings = new ArrayList<String>();
		for (UpdateTaskTweet utt : updates) {
			updateStrings.add(utt.getTweet());
		}
		List<UpdateTaskTweet> ret = new ArrayList<UpdateTaskTweet>();
		for (String s : updateStrings) {
		UpdateTaskTweet utt = (UpdateTaskTweet) AbstractTaskManagerTweet.parseTweet(s);
		ret.add(utt);
		// updateTask(utt, tasksMap);
		// MainModel.getInstance().updateTask(utt);
		}
		return ret;
		
	}
	/*
	public static List<UpdateTaskTweet> generateUpdateTaskTweets(Map<Long, Task> tasksMap) {
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
		List<UpdateTaskTweet> ret = new ArrayList<UpdateTaskTweet>();
		for (String s : updateStrings) {
		UpdateTaskTweet utt = (UpdateTaskTweet) AbstractTaskManagerTweet.parseTweet(s);
		ret.add(utt);
		// updateTask(utt, tasksMap);
		// MainModel.getInstance().updateTask(utt);
		}
		return ret;
		
	}
*/
}
