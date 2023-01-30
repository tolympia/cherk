import java.time;

public class containsTimeHelperMethod{
    public static void main(String[] args){

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