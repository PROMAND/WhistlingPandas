package pl.byd.promand.Team4.activitylist;


import java.util.List;

import pl.byd.promand.Team4.R;
import pl.byd.promand.Team4.domain.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<Task> {
	private final Context context;
	private final List<Task> values;

	public TaskListAdapter(Context context, List<Task> values) {
		super(context, R.layout.task_item);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public int getCount() {
		return values.size();
	}
	
	@Override
	public int getPosition(Task item) {
		return values.indexOf(item);
	}
	
	@Override
	public Task getItem(int position) {
		return values.get(position);
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.task_item, parent, false);
		TextView tvProjectName = (TextView) rowView.findViewById(R.id.id_projectname);
		
		// Title
		Task currentTask = values.get(position);
		tvProjectName.setText(currentTask.getTitle());
		
		// Assignee
		TextView tvAssignee = (TextView) rowView.findViewById(R.id.id_assignee);
		tvAssignee.setText(currentTask.getAssignee());
		
		// Deadline
		TextView tvDeadline = (TextView) rowView.findViewById(R.id.id_deadline);
		tvDeadline.setText(currentTask.getFormattedDeadline());

		// Deadline
		LinearLayout tvPriority = (LinearLayout) rowView.findViewById(R.id.id_priority);
		tvPriority.setBackgroundColor(currentTask.getPriority().getColor());
		
		
		// Image for task type
		ImageView iTaskType = (ImageView) rowView.findViewById(R.id.logo);
		iTaskType.setImageResource(currentTask.getType().getImage());
		
		return rowView;
	}
}
