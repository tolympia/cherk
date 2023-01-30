import java.time.*;

public class ConsolidateFreesHelperMethod{
    public static void main(String[] args){

    }

    public static ArrayList<ArrayList<String>> consolidateFrees(Teacher teacher, String date){
        List<String> frees = teacher.getFreePeriods();
        List<String> freesOnDay = date.getFrees(); //this will be using Mrs. Zhu's code 
        
        
        for (int i=0; i<frees.length; i++){
            if (!freesOnDay.contains(frees[i])){
                frees.remove(frees[i]);
            }
        }

        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>;
        for (int i=0; i<frees.length; i++){
            ArrayList<LocalTime> startAndEnd = new ArrayList<>;
            
            LocalTime start = new LocalTime(getStart(frees[i]));//using mrs zhu's code to turn block into start time
            LocalTime end = new LocalTime(getEnd(frees[i]));//using mrs zhu's code to turn block into end time
            startAndEnd.add(start);
            startAndEnd.add(end); 
            freesTimes.add(startAndEnd);
        }

        ArrayList<ArrayList<LocalTime>> timesInOrder = new ArrayList<ArrayList<LocalTime>>;
        timesInOrder = sort(freesTimes);
        

    }

    public static ArrayList<ArrayList<LocalTime>> sort(ArrayList<ArrayList<LocalTime>> freesTimes){
        //https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html
        //this link should help a lot with finding date time librar tools robyn!
    }
}