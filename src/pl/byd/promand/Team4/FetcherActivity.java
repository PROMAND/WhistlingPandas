package pl.byd.promand.Team4;

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
		case R.id.refresh:
//			System.out.println("refresh");
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