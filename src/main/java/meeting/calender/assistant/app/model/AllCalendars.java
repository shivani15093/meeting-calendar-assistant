package meeting.calender.assistant.app.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllCalendars {
    // private instance, so that it can be
    // accessed by only by getInstance() method
    private static AllCalendars singleInstance;
    private List<ParticipantCalendar> calendars;
    private AllCalendars(List<ParticipantCalendar> calendars) {
        this.calendars = calendars;
    }
    synchronized public static AllCalendars getInstance(){
        // To ensure only one instance is created
        if (singleInstance == null)
        {
            singleInstance = new AllCalendars(new ArrayList<ParticipantCalendar>());
        }
        return singleInstance;
    }
    public void bookMeetingWithParticipants(Meeting meeting){
        for(ParticipantCalendar part : getInstance().getCalendars()){
            if(meeting.getParticipantIds().contains(part.getParticipantId())){
                part.getMeetings().add(meeting);
            }
        }
    }
    public ParticipantCalendar getParticipantCalendarByPartId(String participantId){
        for(ParticipantCalendar part : getInstance().getCalendars()){
            if(participantId.equalsIgnoreCase(part.getParticipantId())){
                return part;
            }
        }
        return null;
    }
}
