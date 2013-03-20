package pl.byd.promand.Team4.adapter;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pl.byd.promand.Team4.R;
import pl.byd.promand.Team4.R.id;
import pl.byd.promand.Team4.R.layout;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskType;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class TaskTypeAdapter extends ArrayAdapter<TaskType> {
    private final Context context;
    private final List<TaskType> values;
    private final Resources resources;
    // private Spinner typeSpinner;

    public TaskTypeAdapter(Context context, List<TaskType> tasksList, Resources resources) { // , Spinner typeSpinner) {
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
    public int getPosition(TaskType item) {
        return values.indexOf(item);
    }

    @Override
    public TaskType getItem(int position) {
        return values.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = View.inflate(context, R.layout.spinner_item, null);
        TextView textView = (TextView) view.findViewById(id.spinner_item);
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
