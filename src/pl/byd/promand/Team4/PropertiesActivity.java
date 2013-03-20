package pl.byd.promand.Team4;

import pl.byd.promand.Team4.domain.Project;
import pl.byd.promand.Team4.twitter.AddMemberTweet;
import pl.byd.promand.Team4.twitter.NewProjectTweet;
import pl.byd.promand.Team4.utils.MainModel;
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

import java.util.ArrayList;

public class PropertiesActivity extends PreferenceActivity {
	/**
	 * Called when the activity is first created.
	 */
    private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.properties);

        PreferenceManager.setDefaultValues(PropertiesActivity.this, R.layout.properties, true);

        sp = getApplicationContext().getSharedPreferences("setting", 0);


        EditTextPreference creatorPref = (EditTextPreference) findPreference("creator");
        if (creatorPref.getText() != null) {
            creatorPref.setSummary(creatorPref.getText());
            MainModel.getInstance().setYourself(creatorPref.getText());
        }
        //once you enter the name you can't change it
        creatorPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (sp.getString("creator", "") == "") {
                    preference.setSummary(o.toString());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("creator", o.toString());
                    editor.commit();
                    AddMemberTweet newProjectTweet = new AddMemberTweet(o.toString());
                    MainModel.getInstance().sendTweet(newProjectTweet.getTweet());
                    return true;
                } else {
                    return false;
                }
            }
        });


        EditTextPreference projectPref = (EditTextPreference) findPreference("projectName");
        if (projectPref.getText() != null) {
            projectPref.setSummary(projectPref.getText());
        }
        projectPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (sp.getString("projectName", "") == "") {
                    preference.setSummary(o.toString());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("projectName", o.toString());
                    editor.commit();
                    NewProjectTweet newProjectTweet = new NewProjectTweet(new Project(o.toString(), new ArrayList<String>()));
                    MainModel.getInstance().sendTweet(newProjectTweet.getTweet());
                    return true;
                }  else {
                    return false;
                }
            }
        });

        Preference button = (Preference)findPreference("goToTheMainPage");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text;
                if (sp.getString("creator", "").isEmpty() || sp.getString("projectName", "").isEmpty()) {
                    text = "Please, enter your name and project name before switching to the task list!";
                } else {
                    text = "Saved";
                    Intent allTasks = new Intent(PropertiesActivity.this, MainViewActivity.class);
                    PropertiesActivity.this.finish();
                    startActivity(allTasks);
                }
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                return true;
            }
        });

        ListPreference updatesPref = (ListPreference) findPreference("updates");
        if (updatesPref.getValue() != null) {
            updatesPref.setSummary(updatesPref.getValue().toString());
        }
        updatesPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary(o.toString());
                return true;
            }
        });

    }
}
