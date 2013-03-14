package pl.byd.promand.Team4.activitylist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.actionbarsherlock.app.SherlockListActivity;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskType;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;

public class TaskListActivity extends SherlockListActivity {
	
	private List<Task> tasksList = new ArrayList<Task>();
	
	private static int counter = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateWithTestData(tasksList);
		populateWithTestData(tasksList); // more items
		setListAdapter(new TaskListAdapter(this, tasksList));
	}

	private void populateWithTestData(List<Task> paramList) {
		for (TaskType type : TaskType.values()) {
			for (TaskPriority priority : TaskPriority.values()) {
				Task task = new Task("Task " + counter, "Person " + counter, "Description " + counter, Calendar
						.getInstance().getTime(), Calendar.getInstance().getTime(),
						priority, type);
				paramList.add(task);
				counter++;
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