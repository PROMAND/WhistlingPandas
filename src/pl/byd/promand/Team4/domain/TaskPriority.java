package pl.byd.promand.Team4.domain;

import android.graphics.Color;

public enum TaskPriority {
	
	LOW(Color.GREEN), MEDIUM(Color.YELLOW), HIGH(Color.RED);
	
	private int color;
	
	private TaskPriority(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}

}
