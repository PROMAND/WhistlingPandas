package pl.byd.promand.Team4.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import pl.byd.promand.Team4.LoginActivity;
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
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

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

	private TasksViewMode tasksViewMode = TasksViewMode.ALL;
	
	/**
	 * Singleton instance
	 */
	private static MainModel _instance = new MainModel();
	
	public static Twitter twitter;
	public static SharedPreferences mSharedPreferences;
	/**
	 * Your own username
	 */
	private String yourself
	;
	/**
	 * Private constructor for the singleton instance
	 */
	private MainModel() {
		/*
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
		Project tweetedProject = unmarshalledProjectTweets.get(0).getProject();
		String projectName = tweetedProject.getName();
		// String yourself = unmarshalledAddMemberTweets.get(0).getMemberName();
		project = new Project(projectName, tweetedProject.getMembers());
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
		*/
	}
	
	public void setYourself(String yourself) {
		this.yourself = yourself;
	}
	
	public String getYourself() {
		return yourself;
	}
	
	/**
	 * 
	 * Adds a task to this central context
	 * 
	 * @param id Task id
	 * @param task Task object
	 */
	public void addTask(Task task) {
		if (task == null) {
			throw new NullPointerException("Task is null");
		}
		Long id = task.getId();
		if (id == null) {
			throw new NullPointerException("Id is null");
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
		String me = getYourself();
		switch (this.tasksViewMode) {
		case ALL:
			// Nothing to do
			break;
		case CREATED_BY_ME:
			List<Task> tmp = new ArrayList<Task>();
			for (Task cur : tasksAsList) {
				if (cur.getCreator().equals(me)) {
					tmp.add(cur);
				}
			}
			tasksAsList = tmp;
			break;
		case ASSIGNED_TO_ME:
			List<Task> tmp2 = new ArrayList<Task>();
			for (Task cur : tasksAsList) {
				if (cur.getAssignee().equals(me)) {
					tmp2.add(cur);
				}
			}
			tasksAsList = tmp2;
			break;
		default:
			throw new IllegalArgumentException("Unknown view mode: " + tasksViewMode);
		}
		Log.i("THREADS", "main model, returning " + tasksAsList.size());
		/*
		 9,223,372,036,854,775,807
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
		tasksMap.clear();
		// Setting state as context state
		project = unmarshalledProjectTweets.get(0).getProject();
		for (AddMemberTweet cur: unmarshalledAddMemberTweets) {
			project.getMembers().add(cur.getMemberName());
		}
		for (CreateTaskTweet cur : unmarshalledCreateTaskTweets) {
			Task curTask = cur.getTask();
			tasksMap.put(cur.getTask().getId(), curTask);
		}
		try {
		for (UpdateTaskTweet cur : unamrshalledUpdateTaskTweets) {
			updateTask(cur);
		}
		} catch (Exception e) {
			Log.e("Parsing", "Updating task failed: " + e.getMessage());
		}
	}
	
	/**
	 * Retrieves user twitter post form twitter
	 * @return twitter posts
	 */
	
	public ResponseList<Status> getTweets() {
		try {
			Paging paging = new Paging(1, 1000);
			ResponseList<Status> ht = twitter.getUserTimeline(paging); // getHomeTimeline();
			String name = Thread.currentThread().getName();
			Log.i("thread", name);
			// ht.wait();
			return ht;
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Seding twitter post
	 * @param tweet String post message.
	 * @return id of post upon success, 0 on failure.
	 */
	
	public long sendTweet(String tweet) {

		try {
			Status response = twitter.updateStatus(tweet);
			return response.getId();

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return 0;

	}
	

	public Status getTweetById(long tweetID) {
		try {
			Status tweet = twitter.showStatus(tweetID);
			if (tweet == null) { //
				return null;
			} else {
				return tweet;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnectTwitter() {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.remove(Constants.PREF_KEY_TOKEN);
		editor.remove(Constants.PREF_KEY_SECRET);

		editor.commit();
	}

	public void setTasksViewMode(TasksViewMode mode) {
		this.tasksViewMode  = mode;
	}

}
