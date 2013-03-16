package pl.byd.promand.Team4.domain;

import pl.byd.promand.Team4.R;
import pl.byd.promand.Team4.R.drawable;

public enum TaskType {
	
	BUG(R.drawable.bug, R.string.tasktype_bug), FEATURE(R.drawable.feature, R.string.tasktype_feature);
	
	private final int image, formString;

	private TaskType(int image, int formString) {
		this.image = image;
		this.formString = formString;
	}
	
	public int getImage() {
		return image;
	}
	
	public int getFormString() {
		return formString;
	}
	
}
