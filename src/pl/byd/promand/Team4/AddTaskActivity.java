package pl.byd.promand.Team4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
		setContentView(R.layout.add_task);

		String[] assigneeArray = new String[3];
		assigneeArray[0] = "Assignee";
		assigneeArray[1] = "Alina";
		assigneeArray[2] = "Malina";

		String[] typesArray = new String[3];
		typesArray[0] = "Type";
		typesArray[1] = "Bug";
		typesArray[2] = "Feature";

		Spinner typeSpinner = (Spinner) findViewById(R.id.type);
		ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.spinner_item, R.id.spinner_item, typesArray);
		typeSpinner.setAdapter(typeSpinnerAdapter);

		Spinner assigneeSpinner = (Spinner) findViewById(R.id.assignee);
		ArrayAdapter<String> assigneeSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.spinner_item, R.id.spinner_item, assigneeArray);
		assigneeSpinner.setAdapter(assigneeSpinnerAdapter);

		Button save = (Button) findViewById(R.id.save);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = getApplicationContext();
				CharSequence text = "Task is saved!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				//future functionality, non needed now
				/*
				EditText title = (EditText) findViewById(R.id.title);
				Spinner assignee = (Spinner) findViewById(R.id.assignee);
				DatePicker deadline = (DatePicker) findViewById(R.id.deadline);
				Spinner type = (Spinner) findViewById(R.id.type);
				RadioGroup radioPriority = (RadioGroup) findViewById(R.id.radioPriority);
				EditText description = (EditText) findViewById(R.id.description);

				String deadlineString = deadline.toString();
				System.out.println(deadlineString);
				*/
			}
		});
	
	}
}
