package pl.byd.promand.Team4.activitylist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class TaskListActivity extends ListActivity {
	
	// Keep this list sorted!
	private List<ITaskListItem> tasksList; //  = new ArrayList<Task>();
	
	private static int counter = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tasksList = new ArrayList<ITaskListItem>();
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