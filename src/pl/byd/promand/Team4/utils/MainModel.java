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
	
	// Keep this list sorted!
	private Map<Long, Task> tasksMap = new HashMap<Long, Task>();
	
	private Project project;
	
	/**
	 * Singleton instance
	 */
	private static MainModel _instance = new MainModel();
	
	private MainModel() {
		Utils.populateWithTestData(tasksMap);
		Utils.populateWithTestData(tasksMap); // more items
		
		Utils.updateTasks(tasksMap);
		
		project = Utils.getTestProject();
	}

    public void add(long id, Task task) {
        tasksMap.put(id, task);
    }

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
