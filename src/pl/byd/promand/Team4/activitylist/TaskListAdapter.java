package pl.byd.promand.Team4.activitylist;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pl.byd.promand.Team4.R;
import pl.byd.promand.Team4.R.id;
import pl.byd.promand.Team4.R.layout;
import pl.byd.promand.Team4.domain.Task;
import android.content.Context;
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
			tvProjectName.setText(currentTask.getTitle());

			// Assignee
			TextView tvAssignee = (TextView) rowView
					.findViewById(R.id.id_assignee);
			tvAssignee.setText(currentTask.getAssignee());

			// Deadline
			TextView tvDeadline = (TextView) rowView
					.findViewById(R.id.id_deadline);
			tvDeadline.setText(currentTask.getFormattedDeadline());

			// Deadline
			LinearLayout tvPriority = (LinearLayout) rowView
					.findViewById(R.id.id_priority);
			tvPriority.setBackgroundColor(currentTask.getPriority().getColor());

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
