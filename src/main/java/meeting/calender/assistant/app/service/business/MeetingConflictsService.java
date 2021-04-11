package meeting.calender.assistant.app.service.business;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MeetingConflictsService {
    public List<String> getConflictingParticipantsFor(String meetId);
}
