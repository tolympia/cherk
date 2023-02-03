import java.io.*;
import java.lang.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class ScheduleMaker {

  public static void main(String[] args) throws FileNotFoundException {
    ArrayList<File> files = new ArrayList<File>(
      Arrays.asList((new File(args[0])), (new File(args[1])))
    );

    ArrayList<APExam> examObjects = new ArrayList<>();
    ArrayList<Teacher> teacherObjects = new ArrayList<>();

    generateListsOfObjects(files, examObjects, teacherObjects);

    System.out.println(matchUp(teacherObjects, examObjects));


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
          else {//if there are no commas, just add the substring between the cirrent comma and nextComma to the 2d array as a one element array list. 
            int nextComma = line.indexOf(",", i + 1);

            if (nextComma == -1) {
              nextComma = line.length();//if we have reached the last comma in the string, then just substring to the end of the array
            }

            instanceVarValues.add(
              new ArrayList<String>(Arrays.asList(line.substring(i, nextComma))));
            i = nextComma;//put i at the index of the next comma
          }
        }

        if (((f.get(j)).getName()).contains("eacher")) {//if the file contains "eacher", I am assuming it is the file for teacher frees, and will add to the teacher array list
          teacherObjects.add(new Teacher(instanceVarValues));
        } 
        else {//adding to the exam arraylist
          examObjects.add(new APExam(instanceVarValues));
        }
      }
      fileScan.close();
    }
   
  }

  
    //using ms. zhu's helper method to convert blocks to times
  public static ArrayList<LocalTime> blocksToTimes(String blockName, String date) {
        // Convert date to a Date object.
        LocalDate dateObject = LocalDate.parse(date);
        // Get schedule for this date.
        USSchedule schedule = getUSScheduleForDate(dateObject);
        // Get list of block names.
        ArrayList<String> blockNames = schedule.blocksForDayType();

        // Find the index of the desired block within all the blocks.
        int blockIndex = blockNames.indexOf(blockName);
        if (blockIndex == -1) {
            // This block (e.g. A) does not occur on this date, so return null.
            return null;
        }
        // Get the corresponding Block object, which contains start and end times.
        Block thisBlock = schedule.blocks.get(blockIndex);
        LocalTime startTime = thisBlock.startTime;
        LocalTime endTime = thisBlock.endTime;
        return new ArrayList<LocalTime>(Arrays.asList(startTime, endTime));
        
        //we need to figure out if we can use getUSScheduleForDate method 
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
      //check and see if any of the teachers are in the same department as exam and remove if they do
      for(int j=0; j<teacherClone.size(); j++){
        List<String> departments = teacherClone.get(j).getDepartment();
        for(int c =0; c<departments.size(); c++){
          if(department == departments.get(c)){
            teacherClone.remove(j);
            j--;
          }
        }
      }
      //loop through updated teacher list
      for(int r = 0; r<teacherClone.size(); r++){
        //if exam contains free blocks - assign teacher to exam in that time
        //convert strings to integers
          //how do i convert if its a range of times
        ArrayList<ArrayList<LocalTime>> combineFrees = consolidateFrees(teacherClone.get(i), date);
        for(int x=0; x<combineFrees.size(); x++){
          LocalTime teacherBlockStart = combineFrees.get(x).get(0); //get start time of combined frees for given teacher
          LocalTime teacherBlockEnd = combineFrees.get(x).get(1);
          if(containsTime(outerTimeStart, outerTimeEnd, teacherBlockStart, teacherBlockEnd)){
            proctors.add(teacherClone.get(x).getName());
          }
          //remove that time from total time of exam
          //robyn to make remove time method
        }
      }
      //add name of exam and proctors to map
      examSchedule.put(examList.get(i).getName(), proctors);
    }
    return examSchedule;
  }


  

  public static void writeIn(Map<String, List<String>> proctorMap, List<APExam> examList) throws FileNotFoundException{
    //create new file with printsteram
    PrintStream p = new PrintStream("ApExamProctorSchedule.csv");
    //print headers into the csv 
    p.println("AP Exam" + "Exam Date" + "Exam Start Time" + "Exam End Time" + "Proctors");
    //loop through map to print to csv
    for(int i=0; i<examList.size(); i++){
      APExam exam = examList.get(i);
      //how do i use .getDate() and .getTime() for specific AP exams
      List<String> proctorNames = proctorMap.get(exam.getName());
      String proctors = arrayFormat(proctorNames);
      p.println(exam.getName() + exam.getDate() + exam.getStartTime() + exam.getEndTime() + proctors);
    }
    p.close();
  }

  public static String arrayFormat(List<String> proctors){
    //initalize an empty string to be returned
    String proctorNames = "";
    //loop through proctor list to format the list of proctors in order to better readability
    for(int i=0; i<proctors.size(); i++){
      proctorNames += proctors.get(i) + ", ";
    }
    return proctorNames;
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

  public static ArrayList<ArrayList<LocalTime>> consolidateFrees(Teacher teacher, String date){
        
      List<String> frees = teacher.getFreePeriods(); //WHERE IS GETFREEPERIODS LOCATED
      List <String> freesOnDay = new ArrayList<String>();
      if (getTimeFromBlockAndDate("A", date)!=null){
          freesOnDay.add("A");
       }
       if (getTimeFromBlockAndDate("B", date)!=null){
           freesOnDay.add("B");
      }
      if (getTimeFromBlockAndDate("C", date)!=null){
          freesOnDay.add("C");
      }
      if (getTimeFromBlockAndDate("D", date)!=null){
          freesOnDay.add("D");
       }
       if (getTimeFromBlockAndDate("E", date)!=null){
           freesOnDay.add("E");
       }
       if (getTimeFromBlockAndDate("F", date)!=null){
          freesOnDay.add("F");
       }
      if (getTimeFromBlockAndDate("G", date)!=null){
          freesOnDay.add("G");
      } 
      //Adding all of the frees occur on that day
      
      for (int i=0; i < frees.size(); i++){
          if (!freesOnDay.contains(frees.get(i))){
              frees.remove(frees.get(i));
           }
      }
       //removing the frees that the teacher doesn't have 

       ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>();
       for (int i=0; i < frees.size() ; i++){
           ArrayList<LocalTime> startAndEnd = getTimeFromBlockAndDate(frees.get(i), date);
          freesTimes.add(startAndEnd);
       }
       //arraylist of arraylist of start and end times for the frees the teacher has that day
    
       ArrayList<ArrayList<LocalTime>> timesInOrder = sort(freesTimes);//using robyn's sort elper methodsw
       //sorting the blocks in order of occurrance by time
       ArrayList<ArrayList<LocalTime>> consolidatedFrees = new ArrayList<ArrayList<LocalTime>>();
      //the arraylist of arraylist of localtime objects that i will ultimately return
      for (int i=1; i<timesInOrder.size()-1; i++){
          ArrayList<LocalTime> timeFrame1 = timesInOrder.get(i-1);
           ArrayList<LocalTime> timeFrame2 = timesInOrder.get(i);
           LocalTime time1End = timeFrame1.get(1);
           LocalTime time2Start = timeFrame2.get(0);

           ArrayList<LocalTime> newTimeBlock = new ArrayList<>();
           if(time2Start.minusMinutes(11).isBefore(time1End)){ //if we want to consolidate adjacent blocks
               newTimeBlock.add(timeFrame1.get(0));
               newTimeBlock.add(timeFrame2.get(1));
           }
           else{
              newTimeBlock.add(timeFrame1.get(0)); //we want the first block to remain the same and now comapare the second block to the third
               newTimeBlock.add(time1End);
           }
           consolidatedFrees.add(newTimeBlock);
           //adding localtime objects for new consolidated time block to arralist
       }
       return consolidatedFrees;
  }

  

  public static ArrayList<ArrayList<LocalTime>> sort(ArrayList<ArrayList<LocalTime>> freesTimes){
       convertStandardtoMilitary(freesTimes); 

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
    
      convertMilitaryToStandard(sortedTimes); 
      System.out.println(sortedTimes); 
       return sortedTimes; 
   }

  //  public static LocalDate formatDate(String date){

  //   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
  //   formatter = formatter.withLocale( Locale.US );  

  //   LocalDate dateObject = LocalDate.parse(date, formatter);




  //  }

//*****MRS ZHUS CODE */
  public static ArrayList<LocalTime> getTimeFromBlockAndDate(String blockName, String date) {

    //creating a date formatter to ensure strings are parsed correctly.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    formatter = formatter.withLocale( Locale.US );  

      // Convert date to a Date object.
      LocalDate dateObject = LocalDate.parse(date, formatter);
      // Get schedule for this date.
      USSchedule schedule = getUSScheduleForDate(dateObject);
      // Get list of block names.
      ArrayList<String> blockNames = schedule.blocksForDayType();

      // Find the index of the desired block within all the blocks.
       int blockIndex = blockNames.indexOf(blockName);
       if (blockIndex == -1) {
           // This block (e.g. A) does not occur on this date, so return null.
           return null;
       }
      // Get the corresponding Block object, which contains start and end times.
       Block thisBlock = schedule.blocks.get(blockIndex);
       LocalTime startTime = thisBlock.startTime;
       LocalTime endTime = thisBlock.endTime;
       return new ArrayList<LocalTime>(Arrays.asList(startTime, endTime));
  }

  private static USSchedule getUSScheduleForDate(LocalDate date) {
      DayOfWeek day = date.getDayOfWeek();

      USSchedule schedule;

      // Determine if this is an adjusted schedule day.
       if (USSchedule.adjustedSchedules.containsKey(date)) {
           String adjustedType = USSchedule.adjustedSchedules.get(date);
           schedule = new USAdjusted1(date, true);
       } else {
           boolean flexDay = day == DayOfWeek.MONDAY || day == DayOfWeek.TUESDAY || day == DayOfWeek.THURSDAY;
           if (flexDay) {
               schedule = new USFlexDay(date, true);
          } else if (day == DayOfWeek.FRIDAY) {
               schedule = new USFriday(date, true);
           } else {  // Wed schedule
               schedule = new USWednesday(date, true);
           }
           if (schedule.getDayType() == -1) {
               // Not a real school day.
               return null;
          }
       }
       return schedule;
  }

  public static ArrayList<ArrayList<LocalTime>> convertMilitaryToStandard (ArrayList<ArrayList<LocalTime>> militaryTimes ) {
       //ArrayList of ArrayLists to hold converted times (so that each sub ArrayList holds a start and end time of a free block)
       ArrayList<ArrayList<LocalTime>> standardTimes = new ArrayList<ArrayList<LocalTime>> (); 
      for (int i=0; i<militaryTimes.size(); i++) {
           ArrayList<LocalTime> currArr = new ArrayList<LocalTime>(); 
           for (int j=0; j<militaryTimes.get(i).size(); j++) {
              LocalTime time = militaryTimes.get(i).get(j); 
               int hour = time.getHour(); 
               if (hour>12) { //ex. if time is 14:30... 
                  time.minusHours(12); //changes it to 2:30 
               }
              currArr.add(time); 
          }
          standardTimes.add(currArr); //fills new ArrayList of ArrayLists 
      }

      return standardTimes; 

  }

  public static ArrayList<ArrayList<LocalTime>> convertStandardtoMilitary(ArrayList<ArrayList<LocalTime>> standardTimes ) {
      //ArrayList of ArrayLists to hold converted times (so that each sub ArrayList holds a start and end time of a free block)
      ArrayList<ArrayList<LocalTime>> militaryTimes = new ArrayList<ArrayList<LocalTime>> (); 
      for (int i=0; i<standardTimes.size(); i++) {
           ArrayList<LocalTime> currArr = new ArrayList<LocalTime>(); 
           for (int j=0; j<standardTimes.get(i).size(); j++) {
              LocalTime time = standardTimes.get(i).get(j); 
              int hour = time.getHour(); 
              if (hour<7) { //ex. if time is 3:00 (during school)...
                  time.plusHours(12); //changes it to 15:00
               }
              currArr.add(time); 
          }
           militaryTimes.add(currArr); //fills new ArrayList of ArrayLists 
       }
       return militaryTimes; 

  }

}
