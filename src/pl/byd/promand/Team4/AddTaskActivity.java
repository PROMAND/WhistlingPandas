package pl.byd.promand.Team4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pl.byd.promand.Team4.adapter.TaskStateAdapter;
import pl.byd.promand.Team4.adapter.TaskTypeAdapter;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.utils.Constants;
import pl.byd.promand.Team4.utils.MainModel;
import pl.byd.promand.Team4.utils.Utils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class AddTaskActivity extends SherlockActivity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Task task = null;
		Intent intent = getIntent();
		if (intent != null) {
			Bundle data = intent.getExtras();
			if (data != null) {
					task = data.getParcelable(Constants.INTENT_EXTRA_TASK);
			}
		}
		setContentView(R.layout.add_task);


        Button button1=(Button)findViewById(R.id.cancelAdd);
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AddTaskActivity.this.finish();
            }
        });
		// Assignee
		List<String> testProjectMembers = Utils.getTestProject().getMembers();
		String[] assigneeArray = new String[testProjectMembers.size()]; //  - 1]; // Not including logged in user himself
		// int arrayIdx = 0;
		for (int i = 0; i < assigneeArray.length; i++) {
			String cur = testProjectMembers.get(i);
			assigneeArray[i] = cur;
			/*
			if (!cur.equals(MainModel.getInstance().getProject().getYourself())) {
				// arrayIdx++;
			}
			*/
		}
        Spinner assigneeSpinner = (Spinner) findViewById(R.id.assignee);
        ArrayAdapter<String> assigneeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_item, assigneeArray);
        assigneeSpinner.setAdapter(assigneeSpinnerAdapter);
        // assigneeSpinner.add

        // Task type
        TaskType[] taskTypeValues = TaskType.values();
		Spinner typeSpinner = (Spinner) findViewById(R.id.type);
		TaskTypeAdapter typeSpinnerAdapter = new TaskTypeAdapter(this, Arrays.asList(taskTypeValues),getResources());
		typeSpinner.setAdapter(typeSpinnerAdapter);
		
        // Task state
        TaskState[] taskStateValues = TaskState.values();
        Spinner stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
		TaskStateAdapter stateSpinnerAdapter = new TaskStateAdapter(this, Arrays.asList(taskStateValues),getResources());
		stateSpinner.setAdapter(stateSpinnerAdapter);
        
        // Task priority 
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioPriority);
        TaskPriority[] taskPriorityValues = TaskPriority.values();
        for (TaskPriority item : taskPriorityValues) {
            RadioButton rb = new RadioButton(this);
            String priorityText = getResources().getString(item.getFormString());
            rb.setText(priorityText);
            rb.setTextSize(25);
            if (task != null && item.equals(task.getPriority())) {
            	// rb.refreshDrawableState();
            	rb.setSelected(true); // TODO
            }
            rg.addView(rb);
        }
        rg.setSelected(true);
        
        // Task deadline
        DatePicker deadlinePicker=(DatePicker) findViewById(R.id.deadline);
        Calendar cal=Calendar.getInstance();
        /*
        cal.set(Calendar.YEAR, 1984);
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.DATE, 23);
        */
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DATE);
        deadlinePicker.updateDate(year, month, day);
        // deadlinePicker.updateDate(year,2, 23);
        
        if (task != null) {
    		typeSpinner.setSelection(task.getType().ordinal());
            stateSpinner.setSelection(task.getState().ordinal());
            cal.setTime(task.getDeadLine());
		}

		Button save = (Button) findViewById(R.id.save);

	
	}
}
