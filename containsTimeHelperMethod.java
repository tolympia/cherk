import java.time.*;

public class containsTimeHelperMethod{
    public static void main(String[] args){
        String outerStart = "07:59";
        String outerEnd = "11:01";
        String innerStart = "08:00";
        String innerEnd = "11:00";
        System.out.println(containsTime(outerStart, outerEnd, innerStart, innerEnd));
    }
    public static boolean containsTime(String outerTimeStart, String outerTimeEnd, String innerTimeStart, String innerTimeEnd){
        LocalTime outerStart = LocalTime.parse(outerTimeStart);
        LocalTime outerEnd = LocalTime.parse(outerTimeEnd); 
        LocalTime innerStart = LocalTime.parse(innerTimeStart); 
        LocalTime innerEnd = LocalTime.parse(innerTimeEnd);

        if (outerStart.isBefore(innerStart) && outerEnd.isAfter(innerEnd)){
            return true;
        }
        else{
            return false;
        }
    }

}