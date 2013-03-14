package pl.byd.promand.Team4.domain;

import android.graphics.Color;

public enum TaskPriority {

	// Keep in the order as in tasks list view!
	
	H(Color.RED), M(Color.YELLOW), L(Color.GREEN);
	
	private int color;
	
	private TaskPriority(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}

}
