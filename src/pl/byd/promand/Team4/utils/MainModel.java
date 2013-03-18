package pl.byd.promand.Team4.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.twitter.AbstractTaskManagerTweet;
import pl.byd.promand.Team4.twitter.CreateTaskTweet;

/**
 * 
 * Central context for our application.
 * <br />
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
		project = Utils.getTestProject();
	}
	
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
	
	public static MainModel getInstance() {
		return _instance;
	}
	
	public Project getProject() {
		return project;
	}

}
