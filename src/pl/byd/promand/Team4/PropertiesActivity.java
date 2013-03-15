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

public class PropertiesActivity extends SherlockActivity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.properties);

		String[] projects = new String[3];
		projects[0] = "Set current project";
		projects[1] = "PROMAND";
		projects[2] = "Whistling pandas";

		String[] updates = new String[4];
		updates[0] = "1 minute";
		updates[1] = "5 minutes";
		updates[2] = "10 minutes";
		updates[3] = "15 minutes";

		Spinner projectsSpinner = (Spinner) findViewById(R.id.currentProject);
		ArrayAdapter<String> projectsSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.spinner_item, R.id.spinner_item, projects);
		projectsSpinner.setAdapter(projectsSpinnerAdapter);

		Spinner updatesSpinner = (Spinner) findViewById(R.id.updates);
		ArrayAdapter<String> updatesSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.spinner_item, R.id.spinner_item, updates);
		updatesSpinner.setAdapter(updatesSpinnerAdapter);

		Button save = (Button) findViewById(R.id.saveProperties);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = getApplicationContext();
				CharSequence text = "Changes are saved!";
				int duration = Toast.LENGTH_SHORT;
				
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
	
	}
}
