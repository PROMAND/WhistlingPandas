package pl.byd.promand.Team4.domain;

import pl.byd.promand.Team4.R;
import android.graphics.Color;

public enum TaskPriority {

	// Keep in the order as in tasks list view!
	/*
	H( 0xffF0B2B2, 0xffDB4D4D, 0xff520000 ,R.string.taskpriority_high), 
	M( 0xffFFEB99, 0xffFFD633, 0xff997A00, R.string.taskpriority_medium), 
	L( 0xff99D699, 0xff006B00, 0xff003D00, R.string.taskpriority_low);
	*/
	H( 0xccF0B2B2, 0xccDB4D4D, 0xcc520000 ,R.string.taskpriority_high), 
	M( 0xccFFEB99, 0xccFFD633, 0xcc997A00, R.string.taskpriority_medium), 
	L( 0xcc99D699, 0xcc006B00, 0xcc003D00, R.string.taskpriority_low);
	
	private final int color1, color2, color3, formString;
	
	private TaskPriority(int color1, int color2, int color3, int formString) {
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
		this.formString = formString;
	}
	
	private int getColor1() {
		return color1;
	}
	
	private int getColor2() {
		return color2;
	}
	
	private int getColor3() {
		return color3;
	}
	
	public int[] getColors() {
		int[] ret = {color1, color2 ,color3};
		return ret ;
	}

	public int getFormString() {
		return formString;
	}

}
