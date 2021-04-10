package meeting.calender.assistant.app.service.impl;

import meeting.calender.assistant.app.model.AllCalendars;
import meeting.calender.assistant.app.model.AllMeetings;
import meeting.calender.assistant.app.model.Meeting;
import meeting.calender.assistant.app.model.ParticipantCalendar;
import meeting.calender.assistant.app.service.business.MeetingConflictsService;

import java.util.ArrayList;
import java.util.List;

public class MeetingConflictServiceImpl implements MeetingConflictsService {
    AllCalendars allCalendars = AllCalendars.getInstance();
    @Override
    public List<String> getConflictingParticipantsFor(Meeting meeting) {
        List<String> conflictingParticipants = new ArrayList<>();
        for(String partId : meeting.getParticipantIds()){
            if(isConflict(partId, meeting)){
                conflictingParticipants.add(partId);
            }
        }
        return conflictingParticipants;
    }
    private boolean areMeetingConflicting(Meeting m1, Meeting m2) {
        if(m1.getTimings().getStartTime().equals(m2.getTimings().getStartTime())
                && m1.getTimings().getDurationOfMeeting()>0.0
                && m2.getTimings().getDurationOfMeeting()>0.0){
            return true;
        }
        if((m1.getTimings().getStartTime().before(m2.getTimings().getEndTime())
                && m1.getTimings().getStartTime().after(m2.getTimings().getStartTime()))
                || (m2.getTimings().getStartTime().before(m1.getTimings().getEndTime())
                && m2.getTimings().getStartTime().after(m1.getTimings().getStartTime()))){
            return true;
        }
        return false;
    }
    private boolean isConflict(String partId, Meeting meeting){
        ParticipantCalendar pc = allCalendars.getParticipantCalendarByPartId(partId);
        for(Meeting m : pc.getMeetings()){
            if(!m.getMeetingId().equalsIgnoreCase(meeting.getMeetingId())
                    && areMeetingConflicting(m, meeting)){
                return true;
            }
        }
        return false;
    }
}
