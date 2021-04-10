package meeting.calender.assistant.app.service.impl;

import meeting.calender.assistant.app.model.*;
import meeting.calender.assistant.app.service.business.FreeSlotManagerService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FreeSlotManagerServiceImpl implements FreeSlotManagerService {
    AllCalendars allCalendars = AllCalendars.getInstance();
    @Override
    public List<TimeInterval> fetchFreeSlots(String p1Id, String p2Id, double duration) {
        ParticipantCalendar part1 = allCalendars.getParticipantCalendarByPartId(p1Id);
        ParticipantCalendar part2 = allCalendars.getParticipantCalendarByPartId(p2Id);
        List<TimeInterval> intervals = createAllTimeIntervalsList(part1, part2);
        intervals.sort(TimeInterval::compareTo);
        List<TimeInterval> freeTimeSlots = getNonOverLappingSlots(intervals, duration);
        return freeTimeSlots;
    }
    private List<TimeInterval> getNonOverLappingSlots(List<TimeInterval> intervals, double duration){
        List<TimeInterval> freeTimeSlots = new ArrayList<>();
        for(int i=0; i<intervals.size()-1; i++){
            Date endTime = intervals.get(i).getEndTime();
            Date startTime = intervals.get(i+1).getStartTime();
            if(endTime.before(startTime)
                    && getTimeDiffInMins(startTime, endTime) >= duration){
                Date s = endTime;
                Date e = startTime;
                while(true){
                    int hours = (int)duration/60;
                    int minutes = (int) (duration-(hours*60));
                    Calendar cal = Calendar.getInstance();
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
