import java.time.*;
import java.util.*;
public class TesterTimeConverter{
    public static void main(String[] args){
        System.out.println(getTimeFromBlockAndDate("G", "2023-05-12"));
    }

    public static ArrayList<LocalTime> getTimeFromBlockAndDate(String blockName, String date) {
        // Convert date to a Date object.
        LocalDate dateObject = LocalDate.parse(date);
        // Get schedule for this date.
        USSchedule schedule = getUSScheduleForDate(dateObject);
        // Get list of block names.
        ArrayList<String> blockNames = schedule.blocksForDayType();

        // Find the index of the desired block within all the blocks.
        int blockIndex = blockNames.indexOf(blockName);
        if (blockIndex == -1) {
            // This block (e.g. A) does not occur on this date, so return null.
            return null;
        }
        // Get the corresponding Block object, which contains start and end times.
        Block thisBlock = schedule.blocks.get(blockIndex);
        LocalTime startTime = thisBlock.startTime;
        LocalTime endTime = thisBlock.endTime;
        return new ArrayList<LocalTime>(Arrays.asList(startTime, endTime));
    }

    private static USSchedule getUSScheduleForDate(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();

        USSchedule schedule;

        // Determine if this is an adjusted schedule day.
        if (USSchedule.adjustedSchedules.containsKey(date)) {
            String adjustedType = USSchedule.adjustedSchedules.get(date);
            schedule = new USAdjusted1(date, true);
        } else {
            boolean flexDay = day == DayOfWeek.MONDAY || day == DayOfWeek.TUESDAY || day == DayOfWeek.THURSDAY;
            if (flexDay) {
                schedule = new USFlexDay(date, true);
            } else if (day == DayOfWeek.FRIDAY) {
                schedule = new USFriday(date, true);
            } else {  // Wed schedule
                schedule = new USWednesday(date, true);
            }
            if (schedule.getDayType() == -1) {
                // Not a real school day.
                return null;
            }
        }
        return schedule;
    }
}