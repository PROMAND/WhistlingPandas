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
import pl.byd.promand.Team4.twitter.CreateTaskTweet;
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
		Utils.populateWithTestData(tasksMap);
		Utils.populateWithTestData(tasksMap); // more items
		project = Utils.getTestProject();
		
		Utils.updateTasks(tasksMap);
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
		tasksAsListBeforeParsing 
		// tasksAsList
		= new ArrayList<Task>()
		;
		tasksAsListBeforeParsing.addAll(tasksMap.values());
		
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

}
