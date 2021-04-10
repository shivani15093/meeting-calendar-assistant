package meeting.calender.assistant.app.service.impl;

import meeting.calender.assistant.app.model.AllCalendars;
import meeting.calender.assistant.app.model.AllMeetings;
import meeting.calender.assistant.app.model.Meeting;
import meeting.calender.assistant.app.service.business.MeetingBookingService;

public class MeetingBookingServiceImpl implements MeetingBookingService {
    AllCalendars allCalendars = AllCalendars.getInstance();
    AllMeetings allMeetings = AllMeetings.getInstance();
    @Override
    public String bookMeeting(Meeting meetingDetails) {
        allMeetings.addMeetingToAllMeetings(meetingDetails);
        allCalendars.bookMeetingWithParticipants(meetingDetails);
        return meetingDetails.getMeetingId();
    }
}
