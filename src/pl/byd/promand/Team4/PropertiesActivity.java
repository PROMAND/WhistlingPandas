package pl.byd.promand.Team4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.preference.*;
import android.content.SharedPreferences;

import com.actionbarsherlock.app.SherlockActivity;

public class PropertiesActivity extends PreferenceActivity {
	/**
	 * Called when the activity is first created.
	 */
    private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.properties);
        addPreferencesFromResource(R.layout.properties);

        //textView = (TextView) findViewById(R.id.txtPrefs);
        String[] projects = new String[2];
        projects[0] = "PROMAND";
        projects[1] = "Whistling pandas";

        sp = getApplicationContext().getSharedPreferences("setting", 0);

        ListPreference projectsPref = (ListPreference) findPreference("currentProject");
        projectsPref.setEntries(projects);
        projectsPref.setEntryValues(projects);
        projectsPref.setTitle(R.string.projectOptions);
        String projectValue = sp.getString("projects", getString(R.string.notSet));
        projectsPref.setSummary(projectValue);
        if (projectValue != null) {
            projectsPref.setValue(projectValue);
        }
        projectsPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary(o.toString());
                return false;
            }
        });

        ListPreference updatesPref = (ListPreference) findPreference("updates");
        updatesPref.setEntries(R.array.updatesOptions);
        updatesPref.setEntryValues(R.array.updatesOptionsValues);
        updatesPref.setTitle(R.string.updatesOptions);
        String updateValue = sp.getString("updateTime", getString(R.string.notSet));
        updatesPref.setSummary(updateValue);
        if (updateValue != "") {
             updatesPref.setValue(updateValue);
        }
        updatesPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary(o.toString());
                return false;
            }
        });

        CheckBoxPreference wifiPref = (CheckBoxPreference) findPreference("wifiUpdate");
        boolean wifiValue = sp.getBoolean("wifiValue", true);
        wifiPref.setChecked(wifiValue);

        CheckBoxPreference notifyPref = (CheckBoxPreference) findPreference("taskNotification");
        boolean notifyValue = sp.getBoolean("notifyValue", false);
        notifyPref.setChecked(notifyValue);
	}

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences.Editor edit = sp.edit();

        ListPreference projectsPref = (ListPreference) findPreference("currentProject");
        String projectValue = projectsPref.getValue();
        edit.putString("projects", projectValue);

        ListPreference updatePref = (ListPreference) findPreference("updates");
        String updateValue = updatePref.getValue();
        edit.putString("updateTime", updateValue);

        CheckBoxPreference wifiPref = (CheckBoxPreference) findPreference("wifiUpdate");
        boolean wifiValue = wifiPref.isChecked();
        edit.putBoolean("wifiValue", wifiValue);

        CheckBoxPreference notifyPref = (CheckBoxPreference) findPreference("taskNotification");
        boolean notifyValue = wifiPref.isChecked();
        edit.putBoolean("notifyValue", notifyValue);

        edit.commit();
    }
}
