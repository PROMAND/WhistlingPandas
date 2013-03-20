package pl.byd.promand.Team4.adapter;

import java.util.List;

import pl.byd.promand.Team4.R;
import pl.byd.promand.Team4.domain.TaskState;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskStateAdapter extends ArrayAdapter<TaskState> {
    private final Context context;
    private final List<TaskState> values;
    private final Resources resources;
    // private Spinner typeSpinner;

    public TaskStateAdapter(Context context, List<TaskState> tasksList, Resources resources) { // , Spinner typeSpinner) {
        super(context, R.layout.spinner_item, R.id.spinner_item);
        this.context = context;
        this.values = tasksList;
        this.resources = resources;
        // this.typeSpinner = typeSpinner;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public int getPosition(TaskState item) {
        return values.indexOf(item);
    }

    @Override
    public TaskState getItem(int position) {
        return values.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.spinner_item, null);
        TextView textView = (TextView) view.findViewById(R.id.spinner_item);
        int idText = values.get(position).getFormString();
        String text = resources.getString(idText);
        textView.setText(text);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
