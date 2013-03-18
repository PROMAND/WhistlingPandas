package pl.byd.promand.Team4;

import android.os.Bundle;
import android.widget.*;
import android.preference.PreferenceActivity;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import android.content.*;
import android.preference.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alina
 * Date: 3/18/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesInfoActivity extends SherlockActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.properties_info);

        Button btnPrefs = (Button) findViewById(R.id.btnPrefs);
        TextView t = (TextView) findViewById(R.id.txtPrefs);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(PropertiesInfoActivity.this);
//
//        String username = prefs.getString("username", "Default NickName");
//        String passw = prefs.getString("password", "Default Password");
//        boolean checkBox = prefs.getBoolean("checkBox", false);
//        String listPrefs = prefs.getString("listpref", "Default list prefs");
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("Username: " + username + "\n");
//        builder.append("Password: " + passw + "\n");
//        builder.append("Keep me logged in: " + String.valueOf(checkBox) + "\n");
//        builder.append("List preference: " + listPrefs);
//
//        t.setText(builder.toString());
    }
}
