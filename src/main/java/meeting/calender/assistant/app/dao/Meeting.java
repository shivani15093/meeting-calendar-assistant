package meeting.calender.assistant.app.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    private String meetingId;
    private String meetingOwnerId;
    private Date startTime;
    private Date endTime;
    private String meetingAgenda;
    private List<String> participantIds;
}
