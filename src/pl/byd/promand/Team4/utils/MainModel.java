package pl.byd.promand.Team4.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.twitter.AbstractTaskManagerTweet;
import pl.byd.promand.Team4.twitter.AddMemberTweet;
import pl.byd.promand.Team4.twitter.CreateTaskTweet;
import pl.byd.promand.Team4.twitter.NewProjectTweet;
import pl.byd.promand.Team4.twitter.TweetType;
import pl.byd.promand.Team4.twitter.UpdateTaskTweet;

/**
 * 
 * Central context for our application.
 * <br /><br />
 * Singleton instance. Use <code>getInstance</code> to fetch.
 * 
 * @author veskikri
 *
 */
public class MainModel {
	
	/**
	 * Map of tasks with their Id-s as keys
	 */
	private Map<Long, Task> tasksMap = new HashMap<Long, Task>();
	
	/**
	 * Currently active project
	 */
	private Project project;
	
	/**
	 * Singleton instance
	 */
	private static MainModel _instance = new MainModel();
	
	/**
	 * Private constructor for the singleton instance
	 */
	private MainModel() {
		List<AbstractTaskManagerTweet> tweetsToMarshal = new ArrayList<AbstractTaskManagerTweet>();
		// Creating test data
		tweetsToMarshal.add(TestDataPopulator.generateNewProjectTweet());
		tweetsToMarshal.addAll(TestDataPopulator.generateTaskCreationTweets());
		tweetsToMarshal.addAll(TestDataPopulator.generateAddMemberTweets());
		tweetsToMarshal.addAll(TestDataPopulator.generateUpdateTaskTweets());
		// Test marshaling test tweets
		List<String> marshalledTweetStrings = new ArrayList<String>();
		for (AbstractTaskManagerTweet cur : tweetsToMarshal) {
			marshalledTweetStrings.add(cur.getTweet());
		}
		// Test unmarshaling test tweets
		List<AbstractTaskManagerTweet> unmarshalledTweets = new ArrayList<AbstractTaskManagerTweet>();
		for (String cur : marshalledTweetStrings) {
			// Log.i("Dummy", cur);
			unmarshalledTweets.add(AbstractTaskManagerTweet.parseTweet(cur));
		}
		List<NewProjectTweet> unmarshalledProjectTweets = new ArrayList<NewProjectTweet>();
		List<AddMemberTweet> unmarshalledAddMemberTweets = new ArrayList<AddMemberTweet>();
		List<CreateTaskTweet> unmarshalledCreateTaskTweets = new ArrayList<CreateTaskTweet>();
		List<UpdateTaskTweet> unamrshalledUpdateTaskTweets = new ArrayList<UpdateTaskTweet>();
		for (AbstractTaskManagerTweet cur : unmarshalledTweets) {
			switch (cur.getType()) {
			case AM:
				unmarshalledAddMemberTweets.add((AddMemberTweet)cur);
				break;
			case CT:
				unmarshalledCreateTaskTweets.add((CreateTaskTweet)cur);
				break;
			case NP:
				unmarshalledProjectTweets.add((NewProjectTweet)cur);
				break;
			case UT:
				unamrshalledUpdateTaskTweets.add((UpdateTaskTweet)cur);
				break;
			default:
				throw new IllegalArgumentException("Unknown tweet type: " + cur.getType());
			}
		}
		// Setting test state as context state
		project = unmarshalledProjectTweets.get(0).getProject();
		for (AddMemberTweet cur: unmarshalledAddMemberTweets) {
			project.getMembers().add(cur.getMemberName());
		}
		Long idGenerator = Long.valueOf(0);
		for (CreateTaskTweet cur : unmarshalledCreateTaskTweets) {
			Task curTask = cur.getTask();
			curTask.setId(idGenerator);
			tasksMap.put(
					// cur.getTask().getId() // TODO should come from twitter
					idGenerator, curTask);
			idGenerator = Long.valueOf(idGenerator + 1);
		}
		for (UpdateTaskTweet cur : unamrshalledUpdateTaskTweets) {
			updateTask(cur);
		}
	}
	
	/**
	 * 
	 * Adds a task to this central context
	 * 
	 * @param id Task id
	 * @param task Task object
	 */
	public void addTask(Long id, Task task) {
		if (id == null) {
			throw new NullPointerException("Id is null");
		}
		if (task == null) {
			throw new NullPointerException("Task is null");
		}
		if (tasksMap.containsKey(id)) {
			throw new IllegalStateException("A task with id " + id + " already exists");
		}
		tasksMap.put(id, task);
	}
	
	/**
	 * Updates this central repository with parameter task update
	 *  
	 * @param utt Task update object
	 */
	public void updateTask(UpdateTaskTweet utt) {
        //Log.i("taskTitle", utt.getTask().getId().toString());
		Utils.updateTask(utt, tasksMap);
	}
	
	/**
	 * 
	 * Retrieves the sorted tasks list for the main task list view with the separators
	 * 
	 * @return Tasks list with separators
	 */
	public List<ITaskListItem> getTasksList() {
		List<Task> 
		// tasksAsListBeforeParsing 
		tasksAsList
		= new ArrayList<Task>()
		;
		tasksAsList.addAll(tasksMap.values());
		/*
		
		List<String> parsed = new ArrayList<String>();
		for (Task cur : tasksAsListBeforeParsing) {
			CreateTaskTweet ctt = new CreateTaskTweet(cur);
			parsed.add(ctt.getTweet());
		}

		List<Task> tasksAsList = new ArrayList<Task>();
		for (String cur : parsed) {
			AbstractTaskManagerTweet task = AbstractTaskManagerTweet.parseTweet(cur);
			CreateTaskTweet ctt = (CreateTaskTweet)task;
			tasksAsList.add(ctt.getTask());
		}
		*/
		List<TaskListSeparator> separators = Utils.getSeparators(tasksAsList);
		List<ITaskListItem> ret = new ArrayList<ITaskListItem>();
		ret.addAll(tasksAsList);
		ret.addAll(separators);
		Collections.sort(ret);
		return ret;
	}
	
	/**
	 * 
	 * Retrieves the central context for the current project and its associated tasks
	 * 
	 * @return Central context
	 */
	public static MainModel getInstance() {
		return _instance;
	}
	
	/**
	 * 
	 * Retrieves current project
	 * 
	 * @return Project
	 */
	public Project getProject() {
		return project;
	}

	public void setState(
			List<UpdateTaskTweet> unamrshalledUpdateTaskTweets,
			List<AddMemberTweet> unmarshalledAddMemberTweets,
			List<CreateTaskTweet> unmarshalledCreateTaskTweets,
			List<NewProjectTweet> unmarshalledProjectTweets) {
		Log.i("THREADS", "SETTING STATE");
		tasksMap.clear();
		// Setting test state as context state
		project = unmarshalledProjectTweets.get(0).getProject();
		for (AddMemberTweet cur: unmarshalledAddMemberTweets) {
			project.getMembers().add(cur.getMemberName());
		}
		Long idGenerator = Long.valueOf(0);
		for (CreateTaskTweet cur : unmarshalledCreateTaskTweets) {
			Task curTask = cur.getTask();
			curTask.setId(idGenerator);
			tasksMap.put(
					// cur.getTask().getId() // TODO should come from twitter
					idGenerator, curTask);
			idGenerator = Long.valueOf(idGenerator + 1);
		}
		for (UpdateTaskTweet cur : unamrshalledUpdateTaskTweets) {
			updateTask(cur);
		}
	}

}
