package pl.byd.promand.Team4;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
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
import twitter4j.Status;

import java.util.ArrayList;
import java.util.Map;

public class PropertiesActivity extends SherlockPreferenceActivity {
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

        //set username
        EditTextPreference creatorPref = (EditTextPreference) findPreference("creator");
        if (creatorPref.getText() != null) {
            creatorPref.setSummary(creatorPref.getText());
            MainModel.getInstance().setYourself(creatorPref.getText());
        }
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
        //set project name
        String projectName = "";
        String accountName = MainModel.getInstance().getAccoutname();
        Status newProjectStatus = MainModel.getInstance().getTweetByText("NP from:"+accountName);
        String[] projectNameInfo = newProjectStatus.getText().split(";");
        projectName =  projectNameInfo[1];
        EditTextPreference projectPref = (EditTextPreference) findPreference("projectName");
        if (projectPref.getText() != null) {
            projectPref.setSummary(projectPref.getText());
        } else if (projectName != null && projectName != "") {
            projectPref.setSummary(projectName);
        }
        final boolean thereIsProjectName = projectName==""?false:true;
        projectPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (sp.getString("projectName", "") == "" && !thereIsProjectName) {
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
                if (sp.getString("creator", "").isEmpty() ||
                        (sp.getString("projectName", "").isEmpty() && !thereIsProjectName)) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_onetaskview, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_back:
                Intent allTasks =new Intent(PropertiesActivity.this, MainViewActivity.class);
                startActivity(allTasks);
                PropertiesActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
