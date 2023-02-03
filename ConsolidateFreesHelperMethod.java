import java.time.*;
import java.util.*; 

public class ConsolidateFreesHelperMethod{
    public static void main(String[] args){
        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>(); 
        LocalTime hour1 = LocalTime.parse(formatTime("8:30"));
        LocalTime hour2 = LocalTime.parse(formatTime("9:00"));
        LocalTime hour3 = LocalTime.parse(formatTime("10:00"));
        LocalTime hour4 = LocalTime.parse(formatTime("12:00"));
        LocalTime hour5 = LocalTime.parse(formatTime("9:30"));
        LocalTime hour6 = LocalTime.parse(formatTime("1:00"));
        LocalTime hour7 = LocalTime.parse(formatTime("1:00"));
        LocalTime hour8 = LocalTime.parse(formatTime("3:00"));

        ArrayList<LocalTime> block1 = new ArrayList<>();
        block1.add(hour1);
        block1.add(hour2);
        ArrayList<LocalTime> block2 = new ArrayList<>();
        block2.add(hour3);
        block2.add(hour4);
        ArrayList<LocalTime> block3 = new ArrayList<>();
        block3.add(hour5);
        block3.add(hour6);
        ArrayList<LocalTime> block4 = new ArrayList<>();
        block4.add(hour7);
        block4.add(hour8);


        freesTimes.add(block1);
        freesTimes.add(block2);
        freesTimes.add(block3);
        freesTimes.add(block4);

        sort(freesTimes); 

        ArrayList<ArrayList<String>> teacher = new ArrayList<ArrayList<String>>();

        ArrayList<String> arr1 = new ArrayList(Arrays.asList("Ms z"));
        ArrayList<String> arr2 = new ArrayList(Arrays.asList("compsci"));
        ArrayList<String> arr3 = new ArrayList(Arrays.asList("A", "B", "C"));

        teacher.add(arr1);
        teacher.add(arr2);
        teacher.add(arr3);

        Teacher teacher1 = new Teacher (teacher);

        System.out.println(consolidateFrees(teacher1, "31-01-2022"));//how do you format the date lol



    }

    public static ArrayList<ArrayList<LocalTime>> consolidateFrees(Teacher teacher, String date){
        
        List<String> frees = teacher.getFreePeriods(); //WHERE IS GETFREEPERIODS LOCATED
        List <String> freesOnDay = new ArrayList<String>();
        if (getTimeFromBlockAndDate("A", date)!=null){
            freesOnDay.add("A");
        }
        if (getTimeFromBlockAndDate("B", date)!=null){
            freesOnDay.add("B");
        }
        if (getTimeFromBlockAndDate("C", date)!=null){
            freesOnDay.add("C");
        }
        if (getTimeFromBlockAndDate("D", date)!=null){
            freesOnDay.add("D");
        }
        if (getTimeFromBlockAndDate("E", date)!=null){
            freesOnDay.add("E");
        }
        if (getTimeFromBlockAndDate("F", date)!=null){
            freesOnDay.add("F");
        }
        if (getTimeFromBlockAndDate("G", date)!=null){
            freesOnDay.add("G");
        } 
        //Adding all of the frees occur on that day
        
        for (int i=0; i < frees.size(); i++){
            if (!freesOnDay.contains(frees.get(i))){
                frees.remove(frees.get(i));
            }
        }
        //removing the frees that the teacher doesn't have 

        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>();
        for (int i=0; i < frees.size() ; i++){
            ArrayList<LocalTime> startAndEnd = getTimeFromBlockAndDate(frees.get(i), date);
            freesTimes.add(startAndEnd);
        }
        //arraylist of arraylist of start and end times for the frees the teacher has that day
    
        ArrayList<ArrayList<LocalTime>> timesInOrder = sort(freesTimes);//using robyn's sort elper methodsw
        //sorting the blocks in order of occurrance by time
        ArrayList<ArrayList<LocalTime>> consolidatedFrees = new ArrayList<ArrayList<LocalTime>>();
        //the arraylist of arraylist of localtime objects that i will ultimately return
        for (int i=1; i<timesInOrder.size()-1; i++){
            ArrayList<LocalTime> timeFrame1 = timesInOrder.get(i-1);
            ArrayList<LocalTime> timeFrame2 = timesInOrder.get(i);
            LocalTime time1End = timeFrame1.get(1);
            LocalTime time2Start = timeFrame2.get(0);

            ArrayList<String> newTimeBlock = new ArrayList<>();
            if(time2Start.minusMinutes(11).isBefore(time1End)){ //if we want to consolidate adjacent blocks
                newTimeBlock.add(timeFrame1.get(0));
                newTimeBlock.add(timeFrame2.get(1));
            }
            else{
                newTimeBlock.add(timeFrame1.get(0)); //we want the first block to remain the same and now comapare the second block to the third
                newTimeBlock.add(time1End);
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
        System.out.println(sortedTimes); 
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


//*****MRS ZHUS CODE */
    public static ArrayList<LocalTime> getTimeFromBlockAndDate(String blockName, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy");
        formatter = formatter.withLocale( Locale.US );
        // Convert date to a Date object.
        LocalDate dateObject = LocalDate.parse(date, formatter);
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