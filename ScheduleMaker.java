import java.io.*;
import java.lang.*;
import java.util.*;

public class ScheduleMaker {

  public static void main(String[] args) throws FileNotFoundException {
    ArrayList<File> files = new ArrayList<File>(
      Arrays.asList((new File(args[0])), (new File(args[1])))
    );

    ArrayList<APExam> examObjects = new ArrayList<>();
    ArrayList<Teacher> teacherObjects = new ArrayList<>();

    generateListsOfObjects(files, examObjects, teacherObjects);

    for (APExam exam : examObjects) {
      System.out.println(exam);
    }
    for (Teacher teacher : teacherObjects) {
      System.out.println(teacher);
    }

    System.out.println(((examObjects.get(0))).getName());


  }

  public static void generateListsOfObjects(//takes in list of files and list of examObjects and teacherObjects to populate
    List<File> f,
    List<APExam> examObjects,
    List<Teacher> teacherObjects
  ) throws FileNotFoundException {
    for (int j = 0; j < f.size(); j++) {
      Scanner fileScan = new Scanner(f.get(j));
      fileScan.nextLine();//don't want the first line of columns

      while (fileScan.hasNextLine()) {
        String line = fileScan.nextLine();//saving the current line of the file as a string

        ArrayList<ArrayList<String>> instanceVarValues = new ArrayList<ArrayList<String>>();//2d array, each array in the 2d array corresponds to a column of the file

        for (int i = 0; i < line.length() - 1; i++) {//iterating through string
          if (line.charAt(i) == '"') {//if the current char is a quote, take the entire substring that is within the quotes, spllit it by the commas, and make an array
            int nextQuote = line.indexOf("\"", i + 1);
            instanceVarValues.add(
              new ArrayList<String>(
                Arrays.asList(line.substring(i + 1, nextQuote).split(","))
              )
            );
            i = nextQuote + 1; //"moving" i to the end of the quote
          } 
          else {//if there are no commas, just add the element to the 2d array as a one element array list. 
            int nextComma = line.indexOf(",", i + 1);

            if (nextComma == -1) {
              nextComma = line.length();//if we have reacherd the last comma in the string, then the
            }

            instanceVarValues.add(
              new ArrayList<String>(Arrays.asList(line.substring(i, nextComma))));
            i = nextComma;
          }
        }

        if (((f.get(j)).getName()).contains("eacher")) {
          teacherObjects.add(new Teacher(instanceVarValues));
        } 
        else {
          examObjects.add(new APExam(instanceVarValues));
        }
      }
      fileScan.close();
    }
   
  }

  public static String testerTimeConverter(String block, int date){
    String timeToRet = "";
    //pretending there are only 2 possible dates and hard coding what time each block would be
    if (date == 27) {
      if (block.equals("A")) {
        timeToRet += "8:00-9:00am";
      } else if (block.equals("B")) {
        timeToRet += "9:00-10:00am";
      } else if (block.equals("C")) {
        timeToRet += "10:00-11:00am";
      }
    } else {
      if (block.equals("D")) {
        timeToRet += "11:00-12:00pm";
      } else if (block.equals("E")) {
        timeToRet += "12:00-1:00pm";
      } else if (block.equals("F")) {
        timeToRet += "1:00-2:00pm";
      } else {
        timeToRet += "2:00-3:00pm";
      }
    }
    System.out.println(timeToRet);
    return timeToRet;
  }

  public static ArrayList<String> getColumns(File f)
    throws FileNotFoundException {
    Scanner fileScan = new Scanner(f); //new scanner

    String[] firstLineArr = fileScan.nextLine().split(","); //assumes first line is header, grabs it and splits it by comma

    ArrayList<String> headers = new ArrayList<>(Arrays.asList(firstLineArr)); //make firstLineArr an array list

    fileScan.close();

    return headers;
  }

  //to be implemented in matchUp()
  //is this necessary? Isn't it easier to just remove a teacher the way it is currently being done in matchup
  public static void removeTeacher(List<Teacher> teacherList, Teacher teacher) { 
    teacherList.remove(teacher); 
  }

  public static Map<String, List<String>> matchUp(List<Teacher> teacherList, List<APExam> examList){
    //initalize map to hold name of exam and list of proctors
    Map<String, List<String>> examSchedule = new HashMap<String, List<String>>();
    //loop through exam list
    for(int i=0; i<examList.size(); i++){
      //clone teacher list
      ArrayList<Teacher> teacherClone = (ArrayList)teacherList.clone();
      //store department, date and start time/ end time of current exam
      String department = examList.get(i).getDepartment();
      String date = examList.get(i).getDate();
      String startTime = examList.get(i).getStartTime();
      String endTime = examList.get(i).getEndTime();
      //check and see if any of the teachers are in the same department as exam and remove if they do
      for(int j=0; j<teacherClone.size(); j++){
        ArrayList<String> departments = teacherClone.get(j).getDepartment();
        for(int c =0; c<departments.size(); c++){
          if(department == departments.get(c)){
            teacherClone.remove(j);
            j--;
          }
        }
      }
      //loop through updated teacher list
      for(int r = 0; i<teacherClone.size(); i++){
        //convert free blocks to times on date of exams
        ArrayList<ArrayList<String>> freeTime = testerTimeConverter(  );
        
        //if exam contains free blocks - assign teacher to exam in that time
        //convert strings to integers
          //how do i convert if its a range of times
        ArrayList<String> proctors = new ArrayList<>();
        for(int i=0; i<freeTime.size(); i++){
          String teacherBlockStart = freeTime.get(i).get(0);
          String teacherBlockEnd = freeTime.get(i).get(1);
          if(containsTime(startTime, endTime, teacherBlockStart, teacherBlockEnd)){
            proctors.add(teacherClone.get(i));
          }
          //remove that time from total time of exam
          //robyn to make remove time method
        }
      }
      //add name of exam and proctors to map
      examSchedule.add(examList.get(i).getName(), proctors);
    }
  }
}
