package pl.byd.promand.Team4.domain;

import pl.byd.promand.Team4.R;
import pl.byd.promand.Team4.R.drawable;

public enum TaskType {
	BUG(R.drawable.bug), FEATURE(R.drawable.feature);
	
	private int image;

	private TaskType(int image) {
		this.image = image;
	}
	
	public int getImage() {
		return image;
	}
	
}
