package pl.byd.promand.Team4;
import java.util.*;

import android.preference.PreferenceManager;
import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListAdapter;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.domain.TaskPriority;
import pl.byd.promand.Team4.domain.TaskState;
import pl.byd.promand.Team4.domain.TaskType;
import pl.byd.promand.Team4.utils.Constants;
import pl.byd.promand.Team4.utils.MainModel;
import pl.byd.promand.Team4.utils.TasksViewMode;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainViewActivity extends SherlockListActivity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case R.id.settingsView:
            Intent settingsView =new Intent(MainViewActivity.this, PropertiesActivity.class);
            startActivity(settingsView);
    		MainViewActivity.this.finish();
            return true;
		case R.id.addTask:
            Intent addTaskActivity =new Intent(MainViewActivity.this, AddTaskActivity.class);
            startActivityForResult(addTaskActivity, Constants.REFRESH_MAIN_SCREEN);
    		MainViewActivity.this.finish();
			return true;
        case R.id.allTasks:
        	MainModel.getInstance().setTasksViewMode(TasksViewMode.ALL);
            Intent allTasks =new Intent(MainViewActivity.this, MainViewActivity.class);
            startActivity(allTasks);
    		MainViewActivity.this.finish();
            return true;
        case R.id.assignedToMe:
        	MainModel.getInstance().setTasksViewMode(TasksViewMode.ASSIGNED_TO_ME);
            Intent assignedToMe =new Intent(MainViewActivity.this, MainViewActivity.class);
            startActivity(assignedToMe);
    		MainViewActivity.this.finish();
            return true;
        case R.id.iAssigned:
        	MainModel.getInstance().setTasksViewMode(TasksViewMode.CREATED_BY_ME);
            Intent iAssigned =new Intent(MainViewActivity.this, MainViewActivity.class);
            startActivity(iAssigned);
    		MainViewActivity.this.finish();
            return true;
		case R.id.invite:
            Intent inviteActivity =new Intent(MainViewActivity.this, InviteActivity.class);
            startActivity(inviteActivity);
    		MainViewActivity.this.finish();
			return true;
		case R.id.refresh:
            //refreshes only local taskMap, not twitter     SharedPreferences prefs = MainModel.mSharedPreferences;

            MainModel.getInstance().parseTwitterPosts();     //TODO uncomment when function will be present
            setListAdapter(new TaskListAdapter(this, MainModel.getInstance().getTasksList()));
			return true;
        case R.id.logout:
            Intent lg = new Intent(MainViewActivity.this, LoginActivity.class);
            startActivity(lg);
    		MainViewActivity.this.finish();
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu_activity, menu);
		return true;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainModel.getInstance().updateByFrequency(); 
		setListAdapter(new TaskListAdapter(this, MainModel.getInstance().getTasksList()));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Object selectedInstance = getListAdapter().getItem(position);
		if (selectedInstance instanceof Task) {
		Task selectedValue = (Task) selectedInstance;

		// Toast.makeText(this, selectedValue.getTitle(), Toast.LENGTH_SHORT).show();
        Intent addTaskActivity = new Intent(MainViewActivity.this, AddTaskActivity.class);
        addTaskActivity.putExtra(Constants.INTENT_EXTRA_TASK, selectedValue);
        startActivityForResult(addTaskActivity, Constants.REFRESH_MAIN_SCREEN);
		}
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REFRESH_MAIN_SCREEN) {
            setListAdapter(new TaskListAdapter(this, MainModel.getInstance().getTasksList()));
        }
    }

}