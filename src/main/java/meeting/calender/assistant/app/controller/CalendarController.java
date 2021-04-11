package meeting.calender.assistant.app.controller;

import meeting.calender.assistant.app.model.Meeting;
import meeting.calender.assistant.app.model.TimeInterval;
import meeting.calender.assistant.app.service.business.FreeSlotManagerService;
import meeting.calender.assistant.app.service.business.MeetingBookingService;
import meeting.calender.assistant.app.service.business.MeetingConflictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class CalendarController {
    @Autowired
    MeetingBookingService meetingBookingService;
    @Autowired
    MeetingConflictsService meetingConflictsService;
    @Autowired
    FreeSlotManagerService freeSlotManagerService;
    @RequestMapping(method = RequestMethod.POST, value = "/bookMeeting")
    public ResponseEntity<String> bookMeeting(@RequestBody Meeting request) {
        return new ResponseEntity<>(meetingBookingService.bookMeeting(request), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/freeSlots/{partId1}/{partId2}/{duration}")
    public ResponseEntity<List<TimeInterval>> getFreeSlots(@PathVariable String partId1
            , @PathVariable String partId2, @PathVariable double duration) {
        return new ResponseEntity<>(freeSlotManagerService.fetchFreeSlots(partId1, partId2, duration), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/conflictingParticipants/{meetId}")
    public ResponseEntity<List<String>> getConflictingParticipants(@PathVariable String meetId) {
        return new ResponseEntity<>(meetingConflictsService.getConflictingParticipantsFor(meetId), HttpStatus.OK);
    }
}
