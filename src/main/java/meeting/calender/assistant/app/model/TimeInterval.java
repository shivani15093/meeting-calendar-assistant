package meeting.calender.assistant.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Date;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeInterval implements Comparable<TimeInterval> {
    private Date startTime;
    private Date endTime;
    public double getDurationOfMeeting(){
        //return difference in minutes
        return (getEndTime().getTime() - getStartTime().getTime())/60000;
    }
    @Override
    public int compareTo(TimeInterval o) {
        return Comparator.comparing(TimeInterval::getStartTime)
                .thenComparing(TimeInterval::getEndTime)
                .compare(this, o);
    }

}
