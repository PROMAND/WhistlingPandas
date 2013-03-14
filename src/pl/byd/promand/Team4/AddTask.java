package pl.byd.promand.Team4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddTask extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        String[] assigneeArray = new String[3];
        assigneeArray[0] = "Assignee";
        assigneeArray[1] = "Alina";
        assigneeArray[2] = "Kristjan";
        
        String[] typesArray = new String[3];
        typesArray[0] = "Type";
        typesArray[1] = "Bug";
        typesArray[2] = "Feature";

        Spinner typeSpinner = (Spinner)findViewById(R.id.type);
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_item, typesArray);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        
        Spinner assigneeSpinner = (Spinner)findViewById(R.id.assignee);
        ArrayAdapter<String> assigneeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_item, assigneeArray);
        assigneeSpinner.setAdapter(assigneeSpinnerAdapter);
    }
}
