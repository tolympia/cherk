import java.time.*;
import java.util.*; 
import java.text.SimpleDateFormat;

public class ConsolidateFreesHelperMethod{
    public static void main(String[] args){
        ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>(); 
        LocalTime hour1 = LocalTime.of(9, 00, 00,00); 
        LocalTime hour2 = LocalTime.of(10, 00, 00, 00);  
        ArrayList<LocalTime> time1 = new ArrayList<LocalTime>(); 
        time1.add(hour1); 
        time1.add(hour2); 
        LocalTime hour3 = LocalTime.of(10, 00, 00,00); 
        LocalTime hour4 = LocalTime.of(11, 00, 00, 00); 
        ArrayList<LocalTime> time2 = new ArrayList<LocalTime>(); 
        time2.add(hour3); 
        time2.add(hour4);  
        LocalTime hour5 = LocalTime.of(1, 00, 00,00); 
        LocalTime hour6 = LocalTime.of(2, 00, 00, 00); 
        ArrayList<LocalTime> time3 = new ArrayList<LocalTime>(); 
        time3.add(hour5); 
        time3.add(hour6);  
        freesTimes.add(time1); 
        freesTimes.add(time2);
        freesTimes.add(time3);
        sort(freesTimes); 
    }

    public static ArrayList<ArrayList<String>> consolidateFrees(Teacher teacher, String date){
        // List<String> frees = teacher.getFreePeriods();
        // List<String> freesOnDay = date.getFrees(); //this will be using Mrs. Zhu's code 
        
        
        // for (int i=0; i<frees.length; i++){
        //     if (!freesOnDay.contains(frees.get(i))){
        //         frees.remove(frees.get(i));
        //     }
        // }

        // ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>();
        // for (int i=0; i<frees.length; i++){
        //     ArrayList<LocalTime> startAndEnd = new ArrayList<>();
            
        //     LocalTime start = new LocalTime(getStart(frees.get(i)));//using mrs zhu's code to turn block into start time
        //     LocalTime end = new LocalTime(getEnd(frees.get(i)));//using mrs zhu's code to turn block into end time
        //     startAndEnd.add(start);
        //     startAndEnd.add(end); 
        //     freesTimes.add(startAndEnd);
        // }

        // ArrayList<ArrayList<LocalTime>> timesInOrder = sort(freeTimes);//using robyn's sort elper methodsw
        ArrayList<ArrayList<String>> consolidatedFrees = new ArrayList<ArrayList<String>>();
        // for (int i=1; i<timesInOrder.length()-1; i++){
        //     ArrayList<LocalTime> timeFrame1 = timesInOrder.get(i-1);
        //     ArrayList<LocalTime> timeFrame2 = timesInOrder.get(i);
        //     LocalTime time1End = timeFrame1.get(1);
        //     LocalTime time2Start = timeFrame2.get(0);

        //     ArrayList<LocalTime> newTimeBlock = new ArrayList<>();
        //     if(time2Start.minusMinutes(11).isBefore(time1End)){
        //         newTimeBlock.add(timeFrame1.get(0));
        //         newTimeBlock.add(timeFrame2.get(1));
        //     }
        //     else{
        //         newTimeBlock.add(timeFrame1.get(0));
        //         newTimeBlock.add(time1End);
        //     }
        //     consolidatedFrees.add(newTimeBlock);
        // }

        return consolidatedFrees;

    }

    public static ArrayList<ArrayList<LocalTime>> sort(ArrayList<ArrayList<LocalTime>> freesTimes){
        convertStandardTo24(freesTimes); 
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
        //convertMilitaryToStandard(freesTimes); 
        System.out.println(sortedTimes); 
        return sortedTimes; 
    }

    //can I just hard code it so it can deal with times 1, 2, 3, 4 ?
    public static ArrayList<ArrayList<LocalTime>> convertStandardTo24(ArrayList<ArrayList<LocalTime>> standardTimes) {
        ArrayList<ArrayList<LocalTime>> militaryTimes = new ArrayList<ArrayList<LocalTime>>(); 

        for (int i = 0; i<standardTimes.size(); i++) {
            for (int j = 0; j<standardTimes.get(0).size(); j++) {;
                LocalTime currTime = standardTimes.get(i).get(j); 
                int hour = currTime.getHour(); 
                if (hour<7) {
                    militaryTimes.get(i).add(currTime.plusHours(12));
                }
                else {
                    militaryTimes.get(i).add(currTime); 
                }
            }
        }
        return militaryTimes; 
    }

    public static ArrayList<ArrayList<LocalTime>> convert24ToStandard(ArrayList<ArrayList<LocalTime>> militaryTimes) {
        ArrayList<ArrayList<LocalTime>> standardTimes = new ArrayList<ArrayList<LocalTime>>(); 
        return standardTimes; 
    }
}