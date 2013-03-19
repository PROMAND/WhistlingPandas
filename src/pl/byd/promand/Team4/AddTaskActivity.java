package pl.byd.promand.Team4;

import java.util.*;

import android.graphics.Color;
import pl.byd.promand.Team4.adapter.TaskPriorityAdapter;
import pl.byd.promand.Team4.adapter.TaskStateAdapter;
import pl.byd.promand.Team4.adapter.TaskTypeAdapter;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.twitter.CreateTaskTweet;
import pl.byd.promand.Team4.twitter.UpdateTaskTweet;
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

import com.actionbarsherlock.view.MenuItem;
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

		// Assignee
		List<String> testProjectMembers = MainModel.getInstance().getProject().getMembers();
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
		/*
        TaskPriority[] taskPriorityValues = TaskPriority.values();
        Spinner prioritySpinner = (Spinner) findViewById(R.id.id_edittask_priority);
		TaskPriorityAdapter prioritySpinnerAdapter = new TaskPriorityAdapter(this, Arrays.asList(taskPriorityValues),getResources());
		prioritySpinner.setAdapter(prioritySpinnerAdapter);
		*/
		// the code below doesn't work
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioPriority);
        TaskPriority[] taskPriorityValues = TaskPriority.values();
        for (TaskPriority item : taskPriorityValues) {
            RadioButton rb = new RadioButton(this);
            String priorityText = getResources().getString(item.getFormString());
            rb.setText(priorityText);
            rb.setTextSize(30);
            rb.setId(item.getFormString());
            if (task != null && item.equals(task.getPriority())) {
            	rb.setChecked(true);
            }
            rg.addView(rb);
        }
        //rg.setSelected(true);
        
        
        // Task deadline
        DatePicker deadlinePicker=(DatePicker) findViewById(R.id.deadline);
        Calendar cal=Calendar.getInstance();


        EditText titleText=(EditText) findViewById(R.id.title);
        EditText descText=(EditText) findViewById(R.id.description);

        if (task != null) {
        	// Editing a task

        	titleText.setText(task.getTitle());
        	descText.setText(task.getDescription());
        	
    		typeSpinner.setSelection(task.getType().ordinal());
            stateSpinner.setSelection(task.getState().ordinal());
            // prioritySpinner.setSelection(task.getPriority().ordinal());

            if (assigneeArray.length > 0) {
            	Log.i("Assignee", "task assignee" + task.getAssignee());
            for (int i = 0; i < assigneeArray.length; i++) {
            	String cur = assigneeArray[i];
            	Log.i("Assignee", cur);
            	if (cur.equals(task.getAssignee())) {
                	Log.i("Assignee", "selection=" + i);
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

        Button cancel = (Button) findViewById(R.id.cancelTask);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskActivity.this.finish();
            }
        });

        Button save = (Button) findViewById(R.id.saveTask);
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //retrieve info from fields
                EditText titleText = (EditText) findViewById(R.id.title);
                String newTitle = titleText.getText().toString();

                String newCreator = "";

                EditText descText = (EditText) findViewById(R.id.description);
                String newDescription = descText.getText().toString();

                DatePicker deadlinePicker = (DatePicker) findViewById(R.id.deadline);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH, deadlinePicker.getDayOfMonth());
                cal.set(Calendar.MONTH, deadlinePicker.getMonth());
                cal.set(Calendar.YEAR, deadlinePicker.getYear());
                Date newDeadline = new Date(cal.getTimeInMillis());
                Date newCreated = new Date(System.currentTimeMillis());

                Spinner assigneeSpinner = (Spinner) findViewById(R.id.assignee);
                String newAssignee = (String) assigneeSpinner.getSelectedItem().toString();

                Spinner typeSpinner = (Spinner) findViewById(R.id.type);
                TaskType newType = (TaskType)typeSpinner.getSelectedItem();

                Spinner stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
                TaskState newState = (TaskState)stateSpinner.getSelectedItem();

                RadioGroup priorityChecked = (RadioGroup) findViewById(R.id.radioPriority);
                int radioButtonID = priorityChecked.getCheckedRadioButtonId();
                View radioButton = priorityChecked.findViewById(radioButtonID);
                int idx = priorityChecked.indexOfChild(radioButton);
                TaskPriority newPriority = TaskPriority.values()[idx];

                //create task object
                Task task = null;
                Intent intent = getIntent();
                if (intent != null) {
                    Bundle data = intent.getExtras();
                    if (data != null) {
                        task = data.getParcelable(Constants.INTENT_EXTRA_TASK);
                    }
                }
                Task newTask = new Task(task.getId(), newTitle, newAssignee, newCreator, newDescription,
                       newCreated, newDeadline, newPriority, newType, newState);
                CreateTaskTweet newTweet = new CreateTaskTweet(newTask);

                //save the task into the twitter;


                //get unique id from twitter - in my case, get counter value.
                long uniqueId = Utils.getNextValue();

                //save task object into the map
                    if (task != null)  {
                        UpdateTaskTweet utt = new UpdateTaskTweet(newTask);
                        MainModel.getInstance().updateTask(utt);
                    } else {
                        MainModel.getInstance().addTask(uniqueId, newTask);
                    }

                //show message that everything is saved
                Context context = getApplicationContext();
                CharSequence text = "Task is saved!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //return to the main view
                AddTaskActivity.this.finish();
            }
        });
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main_menu_activity, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsView:
                Intent settingsView =new Intent(AddTaskActivity.this, PropertiesActivity.class);
                startActivity(settingsView);
                return true;
            case R.id.addTask:
                Intent addTaskActivity =new Intent(AddTaskActivity.this, AddTaskActivity.class);
                startActivity(addTaskActivity);
                return true;
            case R.id.allTasks:
                Intent allTasks =new Intent(AddTaskActivity.this, MainViewActivity.class);
                startActivity(allTasks);
                return true;
            case R.id.assignedToMe:
                Intent assignedToMe =new Intent(AddTaskActivity.this, MainViewActivity.class);
                startActivity(assignedToMe);
                return true;
            case R.id.iAssigned:
                Intent iAssigned =new Intent(AddTaskActivity.this, MainViewActivity.class);
                startActivity(iAssigned);
                return true;
            case R.id.invite:
                Intent inviteActivity =new Intent(AddTaskActivity.this, InviteActivity.class);
                startActivity(inviteActivity);
                return true;
            case R.id.refresh:
                System.out.println("refresh");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
}
