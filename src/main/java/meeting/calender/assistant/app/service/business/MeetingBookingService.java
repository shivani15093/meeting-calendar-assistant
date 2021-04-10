package meeting.calender.assistant.app.service.business;

import meeting.calender.assistant.app.model.Meeting;
import org.springframework.stereotype.Service;

@Service
public interface MeetingBookingService {
    public String bookMeeting(Meeting meetingDetails);
}
