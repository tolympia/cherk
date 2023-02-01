import java.time.*;

public class ContainsTimeHelperMethod{
    public static void main(String[] args){
        String outerStart = "7:59";
        String outerEnd = "11:01";
        String innerStart = "08:00";
        String innerEnd = "11:00";
        //System.out.println(containsTime(outerStart, outerEnd, innerStart, innerEnd));
        //System.out.println(formatTime(outerStart));
        //System.out.println(formatTime(outerEnd));
    }
   public static boolean containsTime(LocalTime outerTimeStart, LocalTime outerTimeEnd, LocalTime innerTimeStart, LocalTime innerTimeEnd){

    if (outerTimeStart.isBefore(innerTimeStart) && outerTimeEnd.isAfter(innerTimeEnd)){
      return true;
    }
    else{
      return false;
    }
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

}

