package pl.byd.promand.Team4;

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
        creatorPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                preference.setSummary(o.toString());
                return true;
            }
        });
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("creator", creatorPref.getText());
        editor.commit();


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
