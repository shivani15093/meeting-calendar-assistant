package meeting.calender.assistant.app;

import meeting.calender.assistant.app.model.AllCalendars;
import meeting.calender.assistant.app.model.ParticipantCalendar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@SpringBootApplication
public class CalendarApp {
    public static void main(String[] args) {
        SpringApplication.run(CalendarApp.class, args);
    }
    @PostConstruct
    public void getCalendarsOfAllParticipants(){
        AllCalendars allCalendars = AllCalendars.getInstance();
        allCalendars.getCalendars().add(ParticipantCalendar.builder()
                .participantId("p1")
                .meetings(new ArrayList<>())
                .build());
        allCalendars.getCalendars().add(ParticipantCalendar.builder()
                .participantId("p2")
                .meetings(new ArrayList<>())
                .build());
        allCalendars.getCalendars().add(ParticipantCalendar.builder()
                .participantId("p3")
                .meetings(new ArrayList<>())
                .build());
    }
}
