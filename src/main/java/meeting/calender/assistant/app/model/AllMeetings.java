package meeting.calender.assistant.app.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllMeetings {
    // private instance, so that it can be
    // accessed by only by getInstance() method
    private static AllMeetings singleInstance;
    private List<Meeting> meetings;
    private AllMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
    synchronized public static AllMeetings getInstance(){
        // To ensure only one instance is created
        if (singleInstance == null)
        {
            singleInstance = new AllMeetings(new ArrayList<Meeting>());
        }
        return singleInstance;
    }
    public void addMeetingToAllMeetings(Meeting meeting){
        getInstance().getMeetings().add(meeting);
    }
}
