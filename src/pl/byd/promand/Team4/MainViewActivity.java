package pl.byd.promand.Team4;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListAdapter;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainViewActivity extends SherlockListActivity {
	
	// Keep this list sorted!
	private List<ITaskListItem> tasksList = new ArrayList<ITaskListItem>();
	
	private static int counter = 1;


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addTask:
            Intent addTaskActivity =new Intent(MainViewActivity.this, AddTaskActivity.class);
            startActivity(addTaskActivity);
			return true;
		case R.id.allTasks:
            Intent allTasks =new Intent(MainViewActivity.this, MainViewActivity.class);
            startActivity(allTasks);
			return true;
		case R.id.myTasks:
			return true;
		case R.id.invite:
            Intent inviteActivity =new Intent(MainViewActivity.this, InviteActivity.class);
            startActivity(inviteActivity);
			return true;
		case R.id.settings:
            Intent i=new Intent(MainViewActivity.this, PropertiesActivity.class);
            startActivity(i);
			return true;
		case R.id.refresh:
			System.out.println("refresh");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu_activity, menu);
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateWithTestData(tasksList);
		populateWithTestData(tasksList); // more items
		addSeparators(tasksList);
		Collections.sort(tasksList);
		setListAdapter(new TaskListAdapter(this, tasksList));
	}

	private void addSeparators(List<ITaskListItem> tasksList2) {
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

	private void populateWithTestData(Collection<ITaskListItem> paramList) {
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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*
		 * String selectedValue = (String) getListAdapter().getItem(position);
		 * Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
		 */
	}
	
	


}