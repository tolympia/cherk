import java.io.*;
import java.lang.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class ConsolidateFreesHelperMethod{
    public static void main(String[] args){

        ArrayList<ArrayList<String>> teacher = new ArrayList<ArrayList<String>>();

        ArrayList<String> arr1 = new ArrayList<String> (Arrays.asList("Ms z"));
        ArrayList<String> arr2 = new ArrayList<String> (Arrays.asList("compsci"));
        ArrayList<String> arr3 = new ArrayList<String> (Arrays.asList("A", "B", "C"));

        teacher.add(arr1);
        teacher.add(arr2);
        teacher.add(arr3);

        Teacher teacher1 = new Teacher (teacher);

        ArrayList<ArrayList<LocalTime>> frees = consolidateFrees(teacher1, "02/01/2023");//how do you format the date lol

        System.out.println(frees);

    }

    public static ArrayList<ArrayList<LocalTime>> consolidateFrees(Teacher teacher, String date){
        
        List<String> frees = teacher.getFreePeriods(); 
        List <String> freesOnDay = new ArrayList<String>();

        //if the block happens on that day and the teacher is free
        if ((getTimeFromBlockAndDate("A", date)!=null) && (frees.contains("A"))){
            freesOnDay.add("A");
        }
        if ((getTimeFromBlockAndDate("B", date)!=null) && (frees.contains("B"))){
            freesOnDay.add("B");
        }
        if ((getTimeFromBlockAndDate("C", date)!=null) && (frees.contains("C"))){
            freesOnDay.add("C");
        }
        if ((getTimeFromBlockAndDate("D", date)!=null) && (frees.contains("D"))){
            freesOnDay.add("D");
        }
        if ((getTimeFromBlockAndDate("E", date)!=null) && (frees.contains("E"))){
            freesOnDay.add("E");
        }
        if ((getTimeFromBlockAndDate("F", date)!=null) && (frees.contains("F"))){
            freesOnDay.add("F");
        }
        if ((getTimeFromBlockAndDate("G", date)!=null) && (frees.contains("G"))){
            freesOnDay.add("G");
        } 

        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>();
        for (int i=0; i < frees.size() ; i++){
            ArrayList<LocalTime> startAndEnd = getTimeFromBlockAndDate(frees.get(i), date);

            if (startAndEnd != null){
            freesTimes.add(startAndEnd);
            }
        }


        //arraylist of arraylist of start and end times for the frees the teacher has that day
        ArrayList<ArrayList<LocalTime>> timesInOrder = new ArrayList<ArrayList<LocalTime>>();//using robyn's sort helper methods
        timesInOrder = sort(freesTimes);
        //sorting the blocks in order of occurrance by time

        ArrayList<ArrayList<LocalTime>> consolidatedFrees = new ArrayList<ArrayList<LocalTime>>();
        //the arraylist of arraylist of localtime objects that i will ultimately return

        
        for (int i=1; i<timesInOrder.size(); i++){
            ArrayList<LocalTime> timeFrame1 = timesInOrder.get(i-1);

            ArrayList<LocalTime> timeFrame2 = timesInOrder.get(i);

            LocalTime time1End = timeFrame1.get(1);

            LocalTime time2Start = timeFrame2.get(0);

            ArrayList<LocalTime> newTimeBlock = new ArrayList<LocalTime>();
            if(time2Start.minusMinutes(11).isBefore(time1End)){ //if we want to consolidate adjacent blocks
                newTimeBlock.add(timeFrame1.get(0));
                newTimeBlock.add(timeFrame2.get(1));
            }
            else{
                newTimeBlock.add(timeFrame1.get(0)); //we want the first block to remain the same and now comapare the second block to the third
                newTimeBlock.add(timeFrame1.get(1));
            }
            consolidatedFrees.add(newTimeBlock);
            //adding localtime objects for new consolidated time block to arralist
        }
        return consolidatedFrees;
    }

    public static ArrayList<ArrayList<LocalTime>> sort(ArrayList<ArrayList<LocalTime>> freesTimes){

        convertStandardtoMilitary(freesTimes); 

        ArrayList<ArrayList<LocalTime>> sortedTimes = new ArrayList<ArrayList<LocalTime>>(); 
        
        while (freesTimes.size()>0) {
            LocalTime minStart = freesTimes.get(0).get(0);
            LocalTime minEnd = freesTimes.get(0).get(1);
            for (int i = 0; i<freesTimes.size(); i++) {
                if (freesTimes.get(i).get(0).isBefore(minStart)) {
                    minStart = freesTimes.get(i).get(0); 
                    minEnd = freesTimes.get(i).get(1); 
                }
                
            }
            ArrayList<LocalTime> min = new ArrayList<LocalTime>(); 
            min.add(minStart); 
            min.add(minEnd); 
            sortedTimes.add(min); 
            freesTimes.remove(min); 
        }
    
        convertMilitaryToStandard(sortedTimes); 

        return sortedTimes; 
    }

    public static String formatTime(String time){
        if (time.length()==5){
            return time;
        }
        else{
            String newTime = "0" + time;
            return newTime;
        }
    }

    public static String dayType(LocalDate date){

        DayOfWeek day = date.getDayOfWeek();

        String s = "";

        if (USSchedule.adjustedSchedules.containsKey(date)) {
            s = "adjusted";
        } 

        else {
            boolean flexDay = day == DayOfWeek.MONDAY || day == DayOfWeek.TUESDAY || day == DayOfWeek.THURSDAY;
            if (flexDay) {
                s = "flex";
            } else if (day == DayOfWeek.FRIDAY) {
                s = "friday";
            } else {  // Wed schedule
                s = "wednesday";
            }
        }
        return s;
    }


//*****MRS ZHUS CODE */
    public static ArrayList<LocalTime> getTimeFromBlockAndDate(String blockName, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Convert date to a Date object.

        //String pattern = "MM-dd-yyyy";
         LocalDate dateObject = LocalDate.parse(date, formatter);
        // Get schedule for this date.
        USSchedule schedule;
        // Get list of block names.

        String s = dayType(dateObject);

        if (s.equals("adjusted")){
            schedule = new USAdjusted1(dateObject , true);
        }

        else {
            if (s.equals("flex")) {
                schedule = new USFlexDay(dateObject, true);
            } else if (s.equals("friday")) {
                schedule = new USFriday(dateObject, true);
            } else {  
                schedule = new USWednesday(dateObject, true);
            }

            if (schedule.getDayType() == -1) {
                // Not a real school day.
                return null; //return nothing, not a real school day
            }
        }


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

    public static ArrayList<ArrayList<LocalTime>> convertMilitaryToStandard (ArrayList<ArrayList<LocalTime>> militaryTimes ) {
        //ArrayList of ArrayLists to hold converted times (so that each sub ArrayList holds a start and end time of a free block)
        ArrayList<ArrayList<LocalTime>> standardTimes = new ArrayList<ArrayList<LocalTime>> (); 

        for (int i=0; i<militaryTimes.size(); i++) {
            ArrayList<LocalTime> currArr = new ArrayList<LocalTime>(); 
            for (int j=0; j<militaryTimes.get(i).size(); j++) {
                LocalTime time = militaryTimes.get(i).get(j); 
                int hour = time.getHour(); 
                if (hour>12) { //ex. if time is 14:30... 
                    time.minusHours(12); //changes it to 2:30 
                }
                currArr.add(time); 
            }
            standardTimes.add(currArr); //fills new ArrayList of ArrayLists 
        }

        return standardTimes; 

    }

    public static ArrayList<ArrayList<LocalTime>> convertStandardtoMilitary(ArrayList<ArrayList<LocalTime>> standardTimes ) {
        //ArrayList of ArrayLists to hold converted times (so that each sub ArrayList holds a start and end time of a free block)
        ArrayList<ArrayList<LocalTime>> militaryTimes = new ArrayList<ArrayList<LocalTime>> (); 

        for (int i=0; i<standardTimes.size(); i++) {
            ArrayList<LocalTime> currArr = new ArrayList<LocalTime>(); 

            for (int j=0; j<standardTimes.get(i).size(); j++) {
                LocalTime time = standardTimes.get(i).get(j); 
                int hour = time.getHour(); 
                if (hour<7) { //ex. if time is 3:00 (during school)...
                    time.plusHours(12); //changes it to 15:00
                }
                currArr.add(time); 

            }
            militaryTimes.add(currArr); //fills new ArrayList of ArrayLists 

        }

        
        return militaryTimes; 

    }


}