package pl.byd.promand.Team4;

import pl.byd.promand.Team4.activitylist.TaskListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.addTask:
            Intent addTaskActivity =new Intent(FetcherActivity.this, AddTaskActivity.class);
            startActivity(addTaskActivity);
			return true;
		case R.id.allTasks:
			Log.i(TAG, "allTasks");
            Intent allTasks =new Intent(FetcherActivity.this, TaskListActivity.class);
            startActivity(allTasks);
			return true;
		case R.id.myTasks:
			Log.i(TAG, "Mystasks");
			return true;
		case R.id.invite:
            Intent inviteActivity =new Intent(FetcherActivity.this, InviteActivity.class);
            startActivity(inviteActivity);
			return true;
		case R.id.settings:
            Intent i=new Intent(FetcherActivity.this, AddTaskActivity.class);
            startActivity(i);
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