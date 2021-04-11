package meeting.calender.assistant.app.service.impl;

import meeting.calender.assistant.app.model.AllCalendars;
import meeting.calender.assistant.app.model.Meeting;
import meeting.calender.assistant.app.model.ParticipantCalendar;
import meeting.calender.assistant.app.model.TimeInterval;
import meeting.calender.assistant.app.service.business.FreeSlotManagerService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FreeSlotManagerServiceImpl implements FreeSlotManagerService {
    AllCalendars allCalendars = AllCalendars.getInstance();
    @Override
    public List<TimeInterval> fetchFreeSlots(String p1Id, String p2Id, double duration) {
        ParticipantCalendar part1 = allCalendars.getParticipantCalendarByPartId(p1Id);
        ParticipantCalendar part2 = allCalendars.getParticipantCalendarByPartId(p2Id);
        List<TimeInterval> intervals = createAllTimeIntervalsList(part1, part2);
        List<TimeInterval> freeTimeSlots = getNonOverLappingSlots(intervals, duration);
        return freeTimeSlots;
    }
    private List<TimeInterval> getNonOverLappingSlots(List<TimeInterval> intervals, double duration){
        List<TimeInterval> freeTimeSlots = new ArrayList<>();

        //check from beginning to end
        Map<Integer, List<TimeInterval>> dayMap = new HashMap<>();
        for(TimeInterval ti : intervals){
            if(dayMap.containsKey(ti.getStartTime().getDate())){
                List<TimeInterval> t = dayMap.get(ti.getStartTime().getDate());
                t.add(ti);
                dayMap.put(ti.getStartTime().getDate(), t);
            }else{
                List<TimeInterval> t = new ArrayList<>();
                t.add(ti);
                dayMap.put(ti.getStartTime().getDate(), t);
            }
        }
        for(Map.Entry<Integer, List<TimeInterval>> entry : dayMap.entrySet()){
            Calendar cal = Calendar.getInstance();
            cal.setTime(entry.getValue().get(0).getStartTime());
            cal.set(Calendar.HOUR_OF_DAY, 00);
            cal.set(Calendar.MINUTE, 00);
            Date start1 = cal.getTime();
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 00);
            Date end1 = cal.getTime();
            TimeInterval startPadding = TimeInterval.builder()
                    .startTime(start1)
                    .endTime(end1)
                    .build();
            cal.set(Calendar.HOUR_OF_DAY, 20);
            cal.set(Calendar.MINUTE, 00);
            Date start2 = cal.getTime();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            Date end2 = cal.getTime();
            TimeInterval endPadding = TimeInterval.builder()
                    .startTime(start2)
                    .endTime(end2)
                    .build();
            entry.getValue().add(startPadding);
            entry.getValue().add(endPadding);
            entry.getValue().sort(TimeInterval::compareTo);
        }
        for(Map.Entry<Integer, List<TimeInterval>> entry : dayMap.entrySet()){
            for(int i=0; i< entry.getValue().size()-1; i++){
                Date endTime = entry.getValue().get(i).getEndTime();
                Date startTime = entry.getValue().get(i+1).getStartTime();
                if(endTime.before(startTime)
                        && getTimeDiffInMins(startTime, endTime) >= duration){
                    Date s = endTime;
                    Date e = startTime;
                    while(true){
                        int hours = (int)duration/60;
                        int minutes = (int) (duration-(hours*60));
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
                        cal.setTime(s);
                        cal.add(Calendar.HOUR, hours);
                        cal.add(Calendar.MINUTE, minutes);
                        e = cal.getTime();
                        if(s.before(startTime) && (e.before(startTime) || e.equals(startTime))){
                            freeTimeSlots.add(new TimeInterval(s, e));
                        }else{
                            break;
                        }
                        s=e;
                    }
                }
            }
        }
        return freeTimeSlots;
    }
    private List<TimeInterval> createAllTimeIntervalsList(ParticipantCalendar part1, ParticipantCalendar part2){
        List<TimeInterval> intervals = new ArrayList<>();
        for(Meeting m : part1.getMeetings()){
            intervals.add(m.getTimings());
        }
        for(Meeting m : part2.getMeetings()){
            intervals.add(m.getTimings());
        }
        return intervals;
    }
    private double getTimeDiffInMins(Date d1, Date d2){
        return (d1.getTime() - d2.getTime())/60000;
    }
}
