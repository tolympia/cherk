import java.time.*;
import java.util.*; 

public class ConsolidateFreesHelperMethod{
    public static void main(String[] args){
        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>(); 
        LocalTime hour2 = LocalTime.parse(formatTime("9:00"));
        LocalTime hour3 = LocalTime.parse(formatTime("10:00"));
        LocalTime hour4 = LocalTime.parse(formatTime("12:00"));
        LocalTime hour5 = LocalTime.parse(formatTime("12:30"));
        LocalTime hour6 = LocalTime.parse(formatTime("1:00"));
        LocalTime hour1 = LocalTime.parse(formatTime("8:30"));

        freesTimes.add(hour1);
        freesTimes.add(hour2);
        freesTimes.add(hour3);
        freesTimes.add(hour4);
        freesTimes.add(hour5);
        freesTimes.add(hour6);

        sort(freesTimes); 
    }

    public static ArrayList<ArrayList<String>> consolidateFrees(Teacher teacher, String date){
<<<<<<< HEAD
        List<String> frees = teacher.getFreePeriods();
        List<String> freesOnDay = date.getFrees(); //this will be using Mrs. Zhu's code 
=======
        // List<String> frees = teacher.getFreePeriods(); //HOW DO I GET TEACHERS FREES
        List <String> freesOnDay = new List<String>;
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
>>>>>>> 0a08ef6185bb215d63ba76ee79bf98db3f62893d
        
        
        for (int i=0; i<frees.size(); i++){
            if (!freesOnDay.contains(frees.get(i))){
                frees.remove(frees.get(i));
            }
        }

        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>();
        for (int i=0; i<frees.length; i++){
            ArrayList<LocalTime> startAndEnd = new ArrayList<>();
            
            LocalTime start = new LocalTime(getStart(frees.get(i)));//using mrs zhu's code to turn block into start time
            LocalTime end = new LocalTime(getEnd(frees.get(i)));//using mrs zhu's code to turn block into end time
            startAndEnd.add(start);
            startAndEnd.add(end); 
            freesTimes.add(startAndEnd);
        }
    

        ArrayList<ArrayList<LocalTime>> timesInOrder = sort(freeTimes);//using robyn's sort elper methodsw
        ArrayList<ArrayList<String>> consolidatedFrees = new ArrayList<ArrayList<String>>();
        for (int i=1; i<timesInOrder.size()-1; i++){
            ArrayList<LocalTime> timeFrame1 = timesInOrder.get(i-1);
            ArrayList<LocalTime> timeFrame2 = timesInOrder.get(i);
            LocalTime time1End = timeFrame1.get(1);
            LocalTime time2Start = timeFrame2.get(0);

            ArrayList<LocalTime> newTimeBlock = new ArrayList<>();
            if(time2Start.minusMinutes(11).isBefore(time1End)){
                newTimeBlock.add(timeFrame1.get(0));
                newTimeBlock.add(timeFrame2.get(1));
            }
            else{
                newTimeBlock.add(timeFrame1.get(0));
                newTimeBlock.add(time1End);
            }
            consolidatedFrees.add(newTimeBlock);
        }

        return consolidatedFrees;

    }

    public static ArrayList<ArrayList<LocalTime>> sort(ArrayList<ArrayList<LocalTime>> freesTimes){
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
}