package pl.byd.promand.Team4.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.byd.promand.Team4.activitylist.ITaskListItem;

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
	private List<ITaskListItem> tasksList = new ArrayList<ITaskListItem>();
	
	/**
	 * Singleton instance
	 */
	private static MainModel _instance = new MainModel();
	
	private MainModel() {
		Utils.populateWithTestData(tasksList);
		Utils.populateWithTestData(tasksList); // more items
		Utils.addSeparators(tasksList);
		Collections.sort(tasksList);
	}
	
	public List<ITaskListItem> getTasksList() {
		return tasksList;
	}
	
	public static MainModel getInstance() {
		return _instance;
	}

}
