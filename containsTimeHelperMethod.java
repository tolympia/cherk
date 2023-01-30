import java.time.Localtime;

public class containsTimeHelperMethod{
    public static void main(String[] args){
        String outerStart = "7:59";
        String outerEnd = "11:01";
        String innerStart = "8:00";
        String innerEnd = "11:00";
        System.out.println(containsTime(outerStart, outerEnd, innerStart, innerEnd));
    }
    public static boolean containsTime(String outerTimeStart, String outerTimeEnd, String innerTimeStart, String innerTimeEnd){
        LocalTime outerStart = Localtime.parse(outerTimeStart);
        LocalTime outerEnd = Localtime.parse(outerTimeEnd); 
        LocalTime innerStart = Localtime.parse(innerTimeStart); 
        LocalTime innerEnd = Localtime.parse(innerTimeEnd);

        if (outerTimeStart.isBefore(innerTimeStart) && outerTimeEnd.isAfter(innerTimeEnd)){
            return true;
        }
        else{
            return false;
        }
    }

}