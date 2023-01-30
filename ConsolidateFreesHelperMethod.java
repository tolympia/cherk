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

        ArrayList<ArrayList<String>> freesTimes = new ArrayList<ArrayList<String>>;
        for (int i=0; i<frees.length; i++){
            ArrayList<LocalTime> startAndEnd = new ArrayList<>;
            
            LocalTime start = new LocalTime(getStart(frees[i]));//using mrs zhu's code to turn block into start time
            LocalTime end = new LocalTime(getEnd(frees[i]));//using mrs zhu's code to turn block into end time
            startAndEnd.add(start);
            startAndEnd.add(end); 
            freesTimes.add(startAndEnd);
        }

    }
}