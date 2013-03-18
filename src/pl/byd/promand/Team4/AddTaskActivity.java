package pl.byd.promand.Team4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pl.byd.promand.Team4.adapter.TaskPriorityAdapter;
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
import com.actionbarsherlock.view.Menu;

public class AddTaskActivity extends SherlockActivity {


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_onetaskview, menu);
		return true;
	}
	
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

		// Assignee
		List<String> testProjectMembers = Utils.getTestProject().getMembers();
		String[] assigneeArray = new String[testProjectMembers.size()];
		for (int i = 0; i < assigneeArray.length; i++) {
			String cur = testProjectMembers.get(i);
			assigneeArray[i] = cur;
		}
        Spinner assigneeSpinner = (Spinner) findViewById(R.id.assignee);
        ArrayAdapter<String> assigneeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_item, assigneeArray);
        assigneeSpinner.setAdapter(assigneeSpinnerAdapter);

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
        TaskPriority[] taskPriorityValues = TaskPriority.values();
        Spinner prioritySpinner = (Spinner) findViewById(R.id.id_edittask_priority);
		TaskPriorityAdapter prioritySpinnerAdapter = new TaskPriorityAdapter(this, Arrays.asList(taskPriorityValues),getResources());
		prioritySpinner.setAdapter(prioritySpinnerAdapter);
		
		/*
		 * the code below doesn't work
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioPriority);
        TaskPriority[] taskPriorityValues = TaskPriority.values();
        for (TaskPriority item : taskPriorityValues) {
            RadioButton rb = new RadioButton(this);
            String priorityText = getResources().getString(item.getFormString());
            rb.setText(priorityText);
            rb.setTextSize(15);
            if (task != null && item.equals(task.getPriority())) {
            	// rb.refreshDrawableState();
            	rb.setSelected(true); // TODO
            }
            rg.addView(rb);
        }
        rg.setSelected(true);
        */
        
        // Task deadline
        DatePicker deadlinePicker=(DatePicker) findViewById(R.id.deadline);
        Calendar cal=Calendar.getInstance();
        
        if (task != null) {
        	// Editing a task
        	
        	EditText titleText=(EditText) findViewById(R.id.title);
        	titleText.setText(task.getTitle());
        	EditText descText=(EditText) findViewById(R.id.description);
        	descText.setText(task.getDescription());
        	
    		typeSpinner.setSelection(task.getType().ordinal());
            stateSpinner.setSelection(task.getState().ordinal());
            prioritySpinner.setSelection(task.getPriority().ordinal());

            if (assigneeArray.length > 0) {
            for (int i = 0; i < assigneeArray.length; i++) {
            	String cur = assigneeArray[i];
            	if (cur.equals(task.getAssignee())) {
            		assigneeSpinner.setSelection(i);
            	}
            }	
            }
            
            cal.setTime(task.getDeadLine());
		}
        
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DATE);
        deadlinePicker.updateDate(year, month, day);
	}
	
}
