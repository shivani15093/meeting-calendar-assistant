package meeting.calendar.assistant;

import meeting.calender.assistant.app.model.*;
import meeting.calender.assistant.app.service.business.FreeSlotManagerService;
import meeting.calender.assistant.app.service.business.MeetingBookingService;
import meeting.calender.assistant.app.service.business.MeetingConflictsService;
import meeting.calender.assistant.app.service.impl.FreeSlotManagerServiceImpl;
import meeting.calender.assistant.app.service.impl.MeetingBookingServiceImpl;
import meeting.calender.assistant.app.service.impl.MeetingConflictServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntegrationTests {
    static AllCalendars allCalendars = AllCalendars.getInstance();
    static AllMeetings allMeetings = AllMeetings.getInstance();
    Meeting m1, m2, m3, m4, m5;
    ParticipantCalendar p1, p2, p3;
    public void createMeetings(){

        List<String> grp1 = new ArrayList<>();
        grp1.add("part_1_0101");
        grp1.add("part_2_0101");
        grp1.add("part_3_0101");
        List<String> grp2 = new ArrayList<>();
        grp2.add("part_1_0101");
        grp2.add("part_2_0101");

        List<String> grp3 = new ArrayList<>();
        grp3.add("part_3_0101");
        grp3.add("part_2_0101");

        List<String> grp4 = new ArrayList<>();
        grp4.add("part_1_0101");
        grp4.add("part_3_0101");

        m1 = Meeting.builder()
                .meetingId("m_1")
                .meetingAgenda("Agenda_1")
                .meetingOwnerId("part_1_0101")
                .timings(TimeInterval.builder()
                        .startTime(new Date(2021, 04, 10, 13, 30))
                        .endTime(new Date(2021, 04, 10, 14, 30))
                        .build())
                .participantIds(grp1)
                .build();
        m2 = Meeting.builder()
                .meetingId("m_2")
                .meetingAgenda("Agenda_2")
                .meetingOwnerId("part_2_0101")
                .timings(TimeInterval.builder()
                        .startTime(new Date(2021, 04, 10, 12, 00))
                        .endTime(new Date(2021, 04, 10, 13, 00))
                        .build())
                .participantIds(grp2)
                .build();
        m3 = Meeting.builder()
                .meetingId("m_3")
                .meetingAgenda("Agenda_3")
                .meetingOwnerId("part_3_0101")
                .timings(TimeInterval.builder()
                        .startTime(new Date(2021, 04, 10, 15, 00))
                        .endTime(new Date(2021, 04, 10, 16, 00))
                        .build())
                .participantIds(grp3)
                .build();
        m4 = Meeting.builder()
                .meetingId("m_4")
                .meetingAgenda("Agenda_4")
                .meetingOwnerId("part_3_0101")
                .timings(TimeInterval.builder()
                        .startTime(new Date(2021, 04, 10, 18, 00))
                        .endTime(new Date(2021, 04, 10, 19, 30))
                        .build())
                .participantIds(grp4)
                .build();
        m5 = Meeting.builder()
                .meetingId("m_5")
                .meetingAgenda("Agenda_5")
                .meetingOwnerId("part_2_0101")
                .timings(TimeInterval.builder()
                        .startTime(new Date(2021, 04, 10, 15, 00))
                        .endTime(new Date(2021, 04, 10, 17, 00))
                        .build())
                .participantIds(grp2)
                .build();
    }
    public void createParticipants(){
        p1 = ParticipantCalendar.builder()
                .participantId("part_1_0101")
                .meetings(new ArrayList<>())
                .build();
        p2 = ParticipantCalendar.builder()
                .participantId("part_2_0101")
                .meetings(new ArrayList<>())
                .build();
        p3 = ParticipantCalendar.builder()
                .participantId("part_3_0101")
                .meetings(new ArrayList<>())
                .build();
    }
    @Before
    public void setUp(){
        createMeetings();
        createParticipants();
        // Adding Participants
        allCalendars.getCalendars().add(p1);
        allCalendars.getCalendars().add(p2);
        allCalendars.getCalendars().add(p3);
    }
    MeetingBookingService meetingBookingService = new MeetingBookingServiceImpl();
    @Test
    public void bookMeetingTest(){
        meetingBookingService.bookMeeting(m1);
        meetingBookingService.bookMeeting(m2);
        meetingBookingService.bookMeeting(m3);
        meetingBookingService.bookMeeting(m4);
        meetingBookingService.bookMeeting(m5);
        Assert.assertEquals(5, allMeetings.getMeetings().size());
    }
    FreeSlotManagerService freeSlotManagerService = new FreeSlotManagerServiceImpl();
    @Test
    public void freeSlotTests(){
//        meetingBookingService.bookMeeting(m1);
//        meetingBookingService.bookMeeting(m2);
//        meetingBookingService.bookMeeting(m3);
//        meetingBookingService.bookMeeting(m4);
//        meetingBookingService.bookMeeting(m5);
        List<TimeInterval> fs = freeSlotManagerService.fetchFreeSlots("part_1_0101", "part_2_0101", 30);
        Assert.assertEquals(9, fs.size());
    }
    MeetingConflictsService meetingConflictsService = new MeetingConflictServiceImpl();
    @Test
    public void meetingConflictTests(){
//        meetingBookingService.bookMeeting(m1);
//        meetingBookingService.bookMeeting(m2);
//        meetingBookingService.bookMeeting(m3);
//        meetingBookingService.bookMeeting(m4);
//        meetingBookingService.bookMeeting(m5);
        List<String> conflictingParticipants = meetingConflictsService.getConflictingParticipantsFor(m3.getMeetingId());
        Assert.assertEquals(1, conflictingParticipants.size());
        Assert.assertEquals("part_2_0101", conflictingParticipants.get(0));
    }

}
