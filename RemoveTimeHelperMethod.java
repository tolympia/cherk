import java.util.*;
import java.time.*;

public class RemoveTimeHelperMethod{
  //NOT NEEDED ANYMORE
    public static void main(String[] args){
        //take overall exam time, and once a time has been matched, remove it from overall time
        //need exam object 
        //total time of exam
        
        APExam sampleExam = new APExam(Arrays.asList("AP World History","May 1 2023", "9:00", "12:00", "History"));
        System.out.println(sampleExam.getName());
        
    }

    public static int removeTime(APExam examObject){
        //total time of exam
        //nt totalExamTime = examObject.getEndTime() - examObject.getStartTime();
    }

    /*public static Map<String, List<String>> matchUp(List<Teacher> teacherList, List<APExam> examList){
    //initalize map to hold name of exam and list of proctors
    Map<String, List<String>> examSchedule = new HashMap<String, List<String>>();
    //loop through exam list
    for(int i=0; i<examList.size(); i++){
      ArrayList<String> proctors = new ArrayList<>();
      //clone teacher list
      ArrayList<Teacher> teacherClone = new ArrayList<Teacher>();
      for(int y=0; y<teacherList.size(); y++){
        teacherClone.add(teacherList.get(y));
      }
      //store department, date and start time/ end time of current exam
      String department = examList.get(i).getDepartment();
      String date = examList.get(i).getDate();
      String startTime = examList.get(i).getStartTime();
      String endTime = examList.get(i).getEndTime();
      LocalTime outerTimeStart = LocalTime.parse(formatTime(startTime));
      LocalTime outerTimeEnd = LocalTime.parse(formatTime(endTime));
      LocalTime totalExamTime = outerTimeEnd.minusMinutes(outerTimeStart); 
      //using the calendar class so we can subtract time
      Date date = new Date();
      Calendar cal = new Calendar();
      //check and see if any of the teachers are in the same department as exam and remove if they do
      for(int j = teacherClone.size()-1; j >= 0; j--){
        List<String> departments = teacherClone.get(j).getDepartment();
        for(int c =0; c<departments.size(); c++){
          if(department == departments.get(c)){
            teacherClone.remove(j);
          }
        }
      }
      //loop through updated teacher list
      for(int r = 0; r<teacherClone.size(); r++){
        //if exam contains free blocks - assign teacher to exam in that time
        //convert strings to integers
          //how do i convert if its a range of times
        ArrayList<ArrayList<LocalTime>> combineFrees = consolidateFrees(teacherClone.get(i), date);

        for(int x=0; x < combineFrees.size(); x++){
          LocalTime teacherBlockStart = combineFrees.get(x).get(0); //get start time of combined frees for given teacher
          LocalTime teacherBlockEnd = combineFrees.get(x).get(1);
          LocalTime totalTeacherFreeTime = teacherBlockEnd.minusMinutes(teacherBlockStart); 

          if(containsTime(outerTimeStart, outerTimeEnd, teacherBlockStart, teacherBlockEnd)){
            proctors.add(teacherClone.get(r).getName());
            if(totalTeacherFreeTime < totalExamTime){
              //remove the time the teacher is free from the overall time of exam
              totalExamTime = totalExamTime.minusMinutes(totalTeacherFreeTime);
            }
          }
          
          
        }
      }
      //add name of exam and proctors to map
      System.out.println(proctors);
      examSchedule.put(examList.get(i).getName(), proctors);
    }
    return examSchedule;
  }

    //going to need to see how rest of match up forms to get a better idea of how to implement this*/
}