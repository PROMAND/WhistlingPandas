package pl.byd.promand.Team4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.*;

public class FetcherActivity extends SherlockActivity {
	
	String TAG = "FetcherActivity";
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.addTask:
			Log.i(TAG, "addTask");
			return true;
		case R.id.allTasks:
			Log.i(TAG, "allTasks");
			return true;
		case R.id.myTasks:
			Log.i(TAG, "Mystasks");
			return true;
		case R.id.invite:
			Log.i(TAG, "Invite");
			return true;
		case R.id.settings:
			System.out.println("settings");
			return true;
		case R.id.refresh:
			System.out.println("refresh");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.header);

	}


}