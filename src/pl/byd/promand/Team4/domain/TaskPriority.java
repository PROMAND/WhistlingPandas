package pl.byd.promand.Team4.domain;

import pl.byd.promand.Team4.R;
import android.graphics.Color;

public enum TaskPriority {

	// Keep in the order as in tasks list view!
	
	H(Color.RED, R.string.taskpriority_high), 
	M(Color.YELLOW, R.string.taskpriority_medium), 
	L(Color.GREEN, R.string.taskpriority_low);
	
	private final int color, formString;
	
	private TaskPriority(int color, int formString) {
		this.color = color;
		this.formString = formString;
	}
	
	public int getColor() {
		return color;
	}

	public int getFormString() {
		return formString;
	}

}
