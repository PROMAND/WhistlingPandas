package pl.byd.promand.Team4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.preference.*;

import com.actionbarsherlock.app.SherlockActivity;

public class PropertiesActivity extends PreferenceActivity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.properties);
        addPreferencesFromResource(R.layout.properties);

        //textView = (TextView) findViewById(R.id.txtPrefs);
        String[] projects = new String[2];
        projects[0] = "PROMAND";
        projects[1] = "Whistling pandas";


        String[] updates = new String[5];
        updates[0] = "1 minutes";
        updates[1] = "5 minutes";
        updates[2] = "10 minutes";
        updates[3] = "15 minutes";
        updates[4] = "20 minutes";


        ListPreference projectsPref = (ListPreference) findPreference("currentProject");//new ListPreference(this);
        projectsPref.setKey("keyName"); //Refer to get the pref value
        projectsPref.setEntries(projects);
        projectsPref.setEntryValues(projects);
        projectsPref.setTitle("Set current project");
        projectsPref.setSummary("Project which be preloaded as soon as application starts");


        ListPreference updatesPref = (ListPreference) findPreference("updates");//new ListPreference(this);
        updatesPref.setKey("keyName"); //Refer to get the pref value
        updatesPref.setEntries(updates);
        updatesPref.setEntryValues(updates);
        updatesPref.setTitle("Updates");
        updatesPref.setSummary("How frequently updates should be checked on server");
          /*
        Button cancel=(Button)findViewById(R.id.cancelProperties);
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PropertiesActivity.this.finish();
            }
        });

		Spinner projectsSpinner = (Spinner) findViewById(R.id.currentProject);
		ArrayAdapter<String> projectsSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.spinner_item, R.id.spinner_item, projects);
		projectsSpinner.setAdapter(projectsSpinnerAdapter);


		Spinner updatesSpinner = (Spinner) findViewById(R.id.updates);
		ArrayAdapter<String> updatesSpinnerAdapter = new ArrayAdapter<String>(
				this, R.layout.spinner_item, R.id.spinner_item, updates);
		updatesSpinner.setAdapter(updatesSpinnerAdapter);

          */
		/*
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
		*/
	
	}
}
