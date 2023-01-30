import java.time.*;

public class ConsolidateFreesHelperMethod{
    public static void main(String[] args){

    }

    public static ArrayList<ArrayList<String>> consolidateFrees(Teacher teacher, String date){
        List<String> frees = teacher.getFreePeriods();
        List<String> freesOnDay = date.getFrees(); //this will be using Mrs. Zhu's code 
        
        
        for (int i=0; i<frees.length; i++){
            if (!freesOnDay.contains(frees.get(i))){
                frees.remove(frees.get(i));
            }
        }

        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>;
        for (int i=0; i<frees.length; i++){
            ArrayList<LocalTime> startAndEnd = new ArrayList<>;
            
            LocalTime start = new LocalTime(getStart(frees.get(i)));//using mrs zhu's code to turn block into start time
            LocalTime end = new LocalTime(getEnd(frees.get(i)));//using mrs zhu's code to turn block into end time
            startAndEnd.add(start);
            startAndEnd.add(end); 
            freesTimes.add(startAndEnd);
        }

        ArrayList<ArrayList<LocalTime>> timesInOrder = sort(freeTimes);//using robyn's sort elper methodsw
        ArrayList<ArrayList<String>> consolidatedFrees = new ArrayList<ArrayList<>>;
        for (int i=1; i<timesInOrder.length()-1; i++){
            ArrayList<LocalTime> timeFrame1 = timesInOrder.get(i-1);
            ArrayList<LocalTime> timeFrame2 = timesInOrder.get(i);
            LocalTime time1End = timeFrame1.get(1);
            LocalTime time2Start = timeFrame2.get(0);

            ArrayList<LocalTime> newTimeBlock = new ArrayList<>;
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
        //https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html
        //this link should help a lot with finding date time librar tools robyn!
    }
}