package pl.byd.promand.Team4.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;

import pl.byd.promand.Team4.activitylist.ITaskListItem;
import pl.byd.promand.Team4.activitylist.TaskListSeparator;
import pl.byd.promand.Team4.utils.Utils;

/**
 * 
 * Represents one task
 * 
 * @author veskikri
 *
 */
public class Task implements ITaskListItem, Parcelable {
	// TODO ask task id

	private String title, assignee, creator, description;
	
	private Date created, deadLine;
	
	private TaskPriority priority;
	
	private TaskType type;
	
	private TaskState state;
	
	private Long id;

    public Task(long id, String title, String assignee, String creator, String description,
                Date created, Date deadLine, TaskPriority priority, TaskType type, TaskState state) {
        super();
        this.id = id;
        this.title = title;
        this.assignee = assignee;
        this.creator = creator;
        this.description = description;
        this.created = created;
        this.deadLine = deadLine;
        this.priority = priority;
        this.type = type;
        this.state = state;
    }

    public Task(String title, String assignee, String creator, String description,
                Date created, Date deadLine, TaskPriority priority, TaskType type, TaskState state) {
        super();
        this.title = title;
        this.assignee = assignee;
        this.creator = creator;
        this.description = description;
        this.created = created;
        this.deadLine = deadLine;
        this.priority = priority;
        this.type = type;
        this.state = state;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Task[");

		// priority
		sb.append("state=");
		sb.append(getState());
		sb.append(", ");
		
		// priority
		sb.append("priority=");
		sb.append(getPriority());
		sb.append(", ");
/*		
		// title
		sb.append("title=");
		sb.append(getTitle());
		sb.append(", ");
		
		// assignee
		sb.append("assignee=");
		sb.append(getAssignee());
		sb.append(", ");
		
		// type
		sb.append("type=");
		sb.append(getType());
		sb.append(", ");
		
		// priority
		sb.append("deadline=");
		sb.append(getFormattedDeadline());
		sb.append(", ");
	*/	
		sb.append("]");
		return sb.toString();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public TextWatcher getAssigneeWatcher() {
		TextWatcher watcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		};
		return watcher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreator() {
		return creator;
	}

	public String getFormattedDeadline() {
		String ret = Utils.convertToString(getDeadLine());
		return ret;
	}

	@Override
	public int compareTo(ITaskListItem another) {
		int thisState = this.getState().ordinal();
		if (another instanceof TaskListSeparator) {
			TaskListSeparator anotherSeparator = (TaskListSeparator)another;
			int diff = thisState - anotherSeparator.getState().ordinal();
			if (Math.abs(diff) > 0) {
			// diff = -diff;
			return  diff;
			}
			return +1;
		} else if (another instanceof Task) {
			Task anotherTask = (Task)another;
			int diff = thisState - anotherTask.getState().ordinal();	
			if (Math.abs(diff) > 0) {
				return diff;
			} else {
				int priorityDiff = this.getPriority().ordinal() - anotherTask.getPriority().ordinal();
				if (Math.abs(priorityDiff) > 0) {
				return priorityDiff;
				} else {
					return this.getDeadLine().compareTo(anotherTask.getDeadLine());
				}
			}
		}
		throw new RuntimeException("Unknown task item class: " + another.getClass().getCanonicalName());
	}
	
	@Override
	public int hashCode() {
		int ret = this.getState().ordinal() * 10 + this.getPriority().ordinal();
		return super.hashCode();
	}

	@Override
	public int describeContents() {
		return 0;
	}

    // Parcelling part
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
        		this.getId().toString(), // Index 0
        		this.getTitle(), // Index 0
        		this.getAssignee(), // 1
        		this.getCreator(), // 2
        		this.getDescription(), // 3
        		this.getPriority().toString(), // 4
        		this.getType().toString(), // 5
        		this.getState().toString(), // 6
        		Utils.convertToString(getCreated()), // 7
        		Utils.convertToString(getDeadLine()), // 8 + 1
        });
	}
	
    public Task(Parcel in){
        String[] data = new String[10];
        in.readStringArray(data);
        int idx = 0;
        this.id = Long.valueOf(data[idx++]);
        this.title = data[idx++];
        this.assignee = data[idx++];
        this.creator = data[idx++];
        this.description = data[idx++];
        this.priority = TaskPriority.valueOf(data[idx++]);
        this.type = TaskType.valueOf(data[idx++]);
        this.state = TaskState.valueOf(data[idx++]);
		this.created = Utils.parseDateFromString(data[idx++]);
	    this.deadLine = Utils.parseDateFromString(data[idx++]);
    }

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Task createFromParcel(Parcel in) {
            return new Task(in); 
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

}
