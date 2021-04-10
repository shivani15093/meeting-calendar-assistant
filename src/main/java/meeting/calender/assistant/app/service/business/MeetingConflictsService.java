package meeting.calender.assistant.app.service.business;

import meeting.calender.assistant.app.model.Meeting;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MeetingConflictsService {
    public List<String> getConflictingParticipantsFor(Meeting meeting);
}
