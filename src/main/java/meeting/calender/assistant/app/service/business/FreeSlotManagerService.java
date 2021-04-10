package meeting.calender.assistant.app.service.business;

import meeting.calender.assistant.app.model.TimeInterval;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FreeSlotManagerService {
    public List<TimeInterval> fetchFreeSlots(String p1Id, String p2Id, double duration);
}
