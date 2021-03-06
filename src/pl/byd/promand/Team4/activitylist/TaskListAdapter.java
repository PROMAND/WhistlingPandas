package pl.byd.promand.Team4.activitylist;

import java.util.List;

import pl.byd.promand.Team4.R;
import pl.byd.promand.Team4.domain.Task;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<ITaskListItem> {
	private final Context context;
	private final List<ITaskListItem> values;

	public TaskListAdapter(Context context, List<ITaskListItem> tasksList) {
		super(context, R.layout.task_item);
		this.context = context;
		this.values = tasksList;
	}

	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public int getPosition(ITaskListItem item) {
		return values.indexOf(item);
	}

	@Override
	public ITaskListItem getItem(int position) {
		return values.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView;

		// Title
		ITaskListItem current = values.get(position);
		if (current instanceof Task) {
			rowView = inflater.inflate(R.layout.task_item, parent, false);
			TextView tvProjectName = (TextView) rowView
					.findViewById(R.id.id_projectname);
			Task currentTask = (Task) current;
			String taskTitleVal = currentTask.getTitle();
			
			//WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			// Display display = wm.getDefaultDisplay();
			int maskedLayout = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
		    if (maskedLayout == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
		    	if (taskTitleVal.length() > 28) {
		    		taskTitleVal = taskTitleVal.substring(0, 24) + "...";
		    	}
		    } else if (maskedLayout == Configuration.SCREENLAYOUT_SIZE_LARGE) {
		    	if (taskTitleVal.length() > 45) {
		    		taskTitleVal = taskTitleVal.substring(0, 42) + "...";
		    	}
		    }
		    // 
			
			tvProjectName.setText(taskTitleVal);
			// Log.i("THREADS", "title=" + currentTask.getTitle());

			// Assignee
			TextView tvAssignee = (TextView) rowView
					.findViewById(R.id.id_assignee);
			tvAssignee.setText(currentTask.getAssignee());

			// Deadline
			TextView tvDeadline = (TextView) rowView
					.findViewById(R.id.id_deadline);
			String deadLineString = currentTask.getFormattedDeadline();
			// Log.i("deadline", deadLineString);
			tvDeadline.setText(deadLineString);

			// Priority
			LinearLayout tvPriority = (LinearLayout) rowView
					.findViewById(R.id.id_priority);
			GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, currentTask.getPriority().getColors());
			tvPriority.setBackgroundDrawable(g);
			
			// Image for task type
			ImageView iTaskType = (ImageView) rowView.findViewById(R.id.logo);
			iTaskType.setImageResource(currentTask.getType().getImage());
		} else if (current instanceof TaskListSeparator) {
			rowView = inflater.inflate(R.layout.tasklist_separator, parent,
					false);
			TaskListSeparator currentSeparator = (TaskListSeparator) current;
			// Text on seperator
			TextView tvAssignee = (TextView) rowView
					.findViewById(R.id.id_separatortext);
			tvAssignee.setText(currentSeparator.getSeparationText());
		} else {
			throw new RuntimeException("Unknown task list item class:"
					+ current.getClass().getCanonicalName());
		}
		return rowView;
	}
}
