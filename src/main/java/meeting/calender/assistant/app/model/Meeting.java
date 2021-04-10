package meeting.calender.assistant.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    private String meetingId;
    private String meetingOwnerId;
    private TimeInterval timings;
    private String meetingAgenda;
    private List<String> participantIds;
}
