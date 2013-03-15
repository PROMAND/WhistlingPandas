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

        String[] typesArray = new String[2];
        typesArray[0] = "Bug";
        typesArray[1] = "Feature";

        String[] statusArray = new String[3];
        statusArray[0] = "Started";
        statusArray[1] = "Rejected";
        statusArray[2] = "Finished";

		Spinner typeSpinner = (Spinner) findViewById(R.id.type);
		ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.spinner_item, R.id.spinner_item, typesArray);
		typeSpinner.setAdapter(typeSpinnerAdapter);

        Spinner assigneeSpinner = (Spinner) findViewById(R.id.assignee);
        ArrayAdapter<String> assigneeSpinnerAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, R.id.spinner_item, assigneeArray);
        assigneeSpinner.setAdapter(assigneeSpinnerAdapter);

        Spinner statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<String> statusSpinnerAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, R.id.spinner_item, statusArray);
        statusSpinner.setAdapter(statusSpinnerAdapter);

		Button save = (Button) findViewById(R.id.save);

	
	}
}
