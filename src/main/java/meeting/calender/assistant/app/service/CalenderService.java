package meeting.calender.assistant.app.service;

import meeting.calender.assistant.app.dao.Meeting;
import org.springframework.stereotype.Service;

@Service
public interface CalenderService {
    public void scheduleMeeting(Meeting meeting);
    public void findFreeSlots(String participantId1, String participantId2, float duration);
    public void showConflictsInMeeting(String meetingId);
}
