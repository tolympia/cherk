import java.io.*;
import java.lang.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.temporal.*;

//this is where all of the functionality of our code is. This contains all the methods for scanning the csv files, matching the teachers with exams, and writing it into a new csv file. It implements the input and output library and the date time library (which we use to convert teacher free blocks into times).  

public class ScheduleMaker {

  public static void main(String[] args) throws FileNotFoundException {
    ArrayList<File> files = new ArrayList<File>(
      Arrays.asList((new File(args[0])), (new File(args[1])))
    );

    makeSchedule(files);
  }

  public static void makeSchedule(ArrayList<File> files)
    throws FileNotFoundException {
    
    //declare new ALs, one to hold exam objects and the other to hold teacher objects
    ArrayList<APExam> examObjects = new ArrayList<>();
    ArrayList<Teacher> teacherObjects = new ArrayList<>();

    //initalizing the examObjcts and teacherObjects (using file given in parameter)
    generateListsOfObjects(files, examObjects, teacherObjects);

    //declaring a map to hold the name of the ap exam as key and list of proctors as value 
    Map<String, List<String>> proctorsAndExams = new HashMap<String, List<String>>();
    //initalizing map using matchUp method
    proctorsAndExams = matchUp(teacherObjects, examObjects);

    //using writeIn method to write to the .csv
    writeIn(proctorsAndExams, examObjects);
  }

  public static void generateListsOfObjects( //takes in list of files and list of examObjects and teacherObjects to populate
    List<File> f,
    List<APExam> examObjects,
    List<Teacher> teacherObjects
  ) throws FileNotFoundException {
    for (int j = 0; j < f.size(); j++) { //does this process for each file in the List
      Scanner fileScan = new Scanner(f.get(j));
      fileScan.nextLine(); //don't want the first line of columns

      while (fileScan.hasNextLine()) {
        String line = fileScan.nextLine(); //saving the current line of the file as a string

        ArrayList<ArrayList<String>> instanceVarValues = new ArrayList<ArrayList<String>>(); //2d array, each array in the 2d array corresponds to a column of the file

        //mainly paying attention to indexes of commas and quotations

        for (int i = 0; i < line.length() - 1; i++) { //iterating through string
          if (line.charAt(i) == '"') { //if the current char is a quote, take the entire substring that is within the quotes, split it by the commas, and make an array
            int nextQuote = line.indexOf("\"", i + 1); //finding the index of the next quote
            instanceVarValues.add(
              new ArrayList<String>(
                Arrays.asList(line.substring(i + 1, nextQuote).split(",")) //split the substringg into a list by the commas
              )
            );
            i = nextQuote + 1; //"moving" i to the end of the quote
          } else { //if there are no commas, just add the substring between the current comma and nextComma to the 2d array as a one element array list.
            int nextComma = line.indexOf(",", i + 1); //index of the next comma

            if (nextComma == -1) {
              nextComma = line.length(); //if we have reached the last comma in the string, then just substring to the end of the line
            }

            instanceVarValues.add(
              new ArrayList<String>(Arrays.asList(line.substring(i, nextComma)))
            ); //adding this list to the 2d list (this array will only have one index, and that's ok, the contructor will handle it
            i = nextComma; //put i at the index of the next comma
          }
        }

        if (((f.get(j)).getName()).contains("eacher")) { //if the file contains "eacher", I am assuming it is the file for teacher frees, and will add to the teacher array list
          teacherObjects.add(new Teacher(instanceVarValues)); //adding an anonymous object to the teacherObjects List, the constructor is meant to handle a 2d list
        } else { //adding to the exam arraylist
          examObjects.add(new APExam(instanceVarValues)); ////adding an anonymous object to the examObjects List, the constructor is meant to handle a 2d list
        }
      }
      fileScan.close();
    }
  }

  //using ms. zhu's helper method to convert blocks to times
  public static ArrayList<LocalTime> blocksToTimes(
    String blockName,
    String date
  ) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    formatter = formatter.withLocale(Locale.US);

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

  public static Map<String, List<String>> matchUp(List<Teacher> teacherList, List<APExam> examList) {
    //initalize map to hold name of exam and list of proctors
    Map<String, List<String>> examSchedule = new HashMap<String, List<String>>();

    //loop through exam list
    for (int i = 0; i < examList.size(); i++) {
      //initalize empty ArrayList to hold the proctors for a specific exam
      ArrayList<String> proctors = new ArrayList<>();
      //initalize a cloned teacher list
      ArrayList<Teacher> teacherClone = new ArrayList<Teacher>();
      //loop to add all elements from teacher list to teacherClone list

      for (int y = 0; y < teacherList.size(); y++) {
        teacherClone.add(teacherList.get(y));
      }
      //store department, date and start time/ end time of current exam in vars
      String department = examList.get(i).getDepartment();
      String date = examList.get(i).getDate();
      String startTime = examList.get(i).getStartTime();
      String endTime = examList.get(i).getEndTime();

      //store start time of exam and end time of exam as LocalTime objects, converting it to military time
      LocalTime outerTimeStart = convertStandardtoMilitary(LocalTime.parse(formatTime(startTime)));
      LocalTime outerTimeEnd = convertStandardtoMilitary(LocalTime.parse(formatTime(endTime)));

      //check and see if any of the teachers are in the same department as exam and remove if they do
      //loop through teacherClone list
      for (int j = teacherClone.size() - 1; j >= 0; j--) {
        //get list of departments (teacher could be in more than one department) - check each with .contains()
        if ((teacherClone.get(j).getDepartment()).contains(department)){
          teacherClone.remove(j);
        }

      }

      //loop through updated teacher list
      for (int r = teacherClone.size() - 1; r >= 0 ; r--) {
        //combining adjacent free blocks and sorting them in the order of the given - storing as AL of AL of LocalTime (represents one teachers frees)
        ArrayList<ArrayList<LocalTime>> combineFrees = sort(consolidateFrees(teacherClone.get(r), date));

        //loop through combineFrees AL to find start and end time of each individual free of one teacher
        for (int x = 0; x < combineFrees.size(); x++) {
          //get teacher free start and end times and save to LocalTime vars
          LocalTime teacherBlockStart = combineFrees.get(x).get(0); //get start time of combined frees for given teacher
          LocalTime teacherBlockEnd = combineFrees.get(x).get(1);//end of the consolidated block

          //if the exam contains the teacher's free - add that teacher as a proctor of the exam
          if (
            containsTime(
              outerTimeStart,
              outerTimeEnd,
              teacherBlockStart,
              teacherBlockEnd
            )
          ) {
            proctors.add(teacherClone.get(r).getName());//this ArrayList will contain all of the teachers that have some part of the exam time free
            //accounts for fact that not all teachers will have the whole time free and you might need multiple. gives Ms. Berman options and allows her to add "human touch" that program is unable to
          }

        }
      }

      //add name of exam and proctors to map
      examSchedule.put(examList.get(i).getName(), proctors);
    }
    return examSchedule;
  }

  public static void writeIn(Map<String, List<String>> proctorMap, List<APExam> examList) throws FileNotFoundException {
    //create new file with printsteram
    PrintStream p = new PrintStream("ApExamProctorSchedule.csv");
    //print headers into the csv
    p.println(
      "AP Exam," +
      "Exam Date," +
      "Exam Start Time," +
      "Exam End Time," +
      "Proctors"
    );
    //loop through examList to get data to print to .csv
    for (int i = 0; i < examList.size(); i++) {
      //get AP exam object (to get date, time, and name)
      APExam exam = examList.get(i);
      //create list of proctor names (get value from map by using the exam name)
      List<String> proctorNames = proctorMap.get(exam.getName());
      //call formatArray helper method to get the correct formatting of proctor names to print to .csv
      String proctors = arrayFormat(proctorNames);
      //print exam name, date, start time, end time, and proctor list (created with arrayFormat helper method)
      p.println(
        exam.getName() +
        "," +
        exam.getDate() +
        "," +
        exam.getStartTime() +
        "," +
        exam.getEndTime() +
        "," +
        proctors
      );
    }
    //close printstream
    p.close();
  }

  public static String arrayFormat(List<String> proctors) {
    //initalize an empty string to be returned
    String proctorNames = "";
    //loop through proctor list to format the list of proctors in order to better readability
    for (int i = 0; i < proctors.size(); i++) {
      //add commas in between each element and concatenate to proctorNames var
      proctorNames += proctors.get(i) + ", ";
    }
    return proctorNames;
  }

  public static boolean containsTime(
    LocalTime examStart,
    LocalTime examEnd,
    LocalTime teacherStart,
    LocalTime teacherEnd
  ) {
    //first condition: if all the exam fits within the teachers free
    //second condition: if all the teacher's free fits within the exam
    //third condition: if the teacher's free ends 30 mins after exam starts
    //fourth condition: if the teacher's free starts 30 min before exam ends

    if (//if the entire exam is within a teachers freeblock
        (examStart.isAfter(teacherStart) &&
        examEnd.isBefore(teacherEnd))
      ||
        //if the entire freeblock is within the exam block
        (teacherStart.isAfter(examStart) &&
        teacherEnd.isBefore(examEnd)) 
    ) {
      return true;
    } 

    if ((teacherEnd.isAfter(examStart)) && (teacherEnd.isBefore(examEnd))){//if the end of the teachers free block is more than 30 mins into the exam
      if (ChronoUnit.MINUTES.between(examStart, teacherEnd) >= 30){
      return true;
      }
      return false;
    }

    if ((teacherStart.isAfter(examStart)) && (teacherEnd.isBefore(examEnd))){//if the start of the teachers free block is more than 30 mins at the end of the exam
      if (ChronoUnit.MINUTES.between(teacherStart, examEnd) >= 30){
      return true;
      }
      return false;
    }
    
    else {
      return false;
    }
  }

  //formats time "7:59" as "07:59" which is the proper format for input for localTime objects
  public static String formatTime(String time) {
    if (time.length() == 5) {
      return time;
    } else {
      String newTime = "0" + time;
      return newTime;
    }
  }

  public static ArrayList<ArrayList<LocalTime>> consolidateFrees(
    Teacher teacher,
    String date
  ) {
    List<String> frees = teacher.getFreePeriods(); //getting the free periods of the teacher and storing them in frees

    //getting the frees that occur on the input date
    List<String> freesOnDay = new ArrayList<String>(); //storing them in an arraylist
    if ((getTimeFromBlockAndDate("A", date) != null) && (frees.contains("A"))) {
      freesOnDay.add("A");
    }
    if ((getTimeFromBlockAndDate("B", date) != null) && (frees.contains("B"))) {
      freesOnDay.add("B");
    }
    if ((getTimeFromBlockAndDate("C", date) != null) && (frees.contains("C"))) {
      freesOnDay.add("C");
    }
    if ((getTimeFromBlockAndDate("D", date) != null) && (frees.contains("D"))) {
      freesOnDay.add("D");
    }
    if ((getTimeFromBlockAndDate("E", date) != null) && (frees.contains("E"))) {
      freesOnDay.add("E");
    }
    if ((getTimeFromBlockAndDate("F", date) != null) && (frees.contains("F"))) {
      freesOnDay.add("F");
    }
    if ((getTimeFromBlockAndDate("G", date) != null) && (frees.contains("G"))) {
      freesOnDay.add("G");
    }

    ArrayList<ArrayList<LocalTime>> freesTimes = new ArrayList<ArrayList<LocalTime>>();

    //creating an arraylist of arraylists of localtime objects to store the starta nd end time of each free block the teacher has on that day
    for (int i = 0; i < frees.size(); i++) {
      ArrayList<LocalTime> startAndEnd = getTimeFromBlockAndDate(
        frees.get(i),
        date
      );

      if (startAndEnd != null){
      freesTimes.add(startAndEnd);
      }
    }

    ArrayList<ArrayList<LocalTime>> timesInOrder = sort(freesTimes); //sorting free blocks the teacher has in order of time (because G can be before A)
 
    ArrayList<ArrayList<LocalTime>> consolidatedFrees = new ArrayList<ArrayList<LocalTime>>(); //the arraylist of arraylist of localtime objects that i will ultimately return

    if(timesInOrder != null){
    for (int i = 0; i < timesInOrder.size(); i++) {

      ArrayList<LocalTime> timeFrame1;
      ArrayList<LocalTime> timeFrame2;
      LocalTime time1End;
      LocalTime time2Start;

      if (consolidatedFrees.size() != 0) { //compare current block to consolidated frees block
        timeFrame1 = consolidatedFrees.get(consolidatedFrees.size() - 1);
        timeFrame2 = timesInOrder.get(i);//next free block time
        time1End = timeFrame1.get(1);//time first block ends
        time2Start = timeFrame2.get(0);//time second block starts
      } 
      else { //compare it to previous block
        
        timeFrame1 = timesInOrder.get(i);//cur block time
        
        if (i != timesInOrder.size()-1){//next free block time
        timeFrame2 = timesInOrder.get(i + 1);
        }

        else{
          timeFrame2 = timesInOrder.get(i);//if it's the last block in the list, find the time the last block ends as opposed to the time the second block starts
        }
        time1End = timeFrame1.get(1);//time first block ends
        time2Start = timeFrame2.get(0);//time second block starts
      }

      //getting all of the localtime objects for start and end of two time blocks being compared

      ArrayList<LocalTime> newTimeBlock = new ArrayList<>(); //consolidated time block if consolidation needs to occur

      if (time2Start.minusMinutes(11).isBefore(time1End)) { //if we want to consolidate adjacent blocks
        newTimeBlock.add(timeFrame1.get(0)); //new block start = first block start
        newTimeBlock.add(timeFrame2.get(1)); //new block end = second block end
        consolidatedFrees.add(newTimeBlock);
      } else {
        consolidatedFrees.add(timesInOrder.get(i));
      }
    }

    for (int i = consolidatedFrees.size() - 1; i > 0; i--) { //removes duplicate times
      if (
        consolidatedFrees
          .get(i)
          .get(0)
          .equals(consolidatedFrees.get(i - 1).get(0))
      ) {
        consolidatedFrees.remove(i - 1);
      }
    }
    }
    System.out.println(consolidatedFrees);
    return consolidatedFrees;
  }

  public static ArrayList<ArrayList<LocalTime>> sort(
    ArrayList<ArrayList<LocalTime>> freesTimes
  ) {
    convertStandardtoMilitary(freesTimes); //converting standard time to military time so that 1:00 is after 11:00
    ArrayList<ArrayList<LocalTime>> sortedTimes = new ArrayList<ArrayList<LocalTime>>(); //creating an arraylist of arraylists of localtime objects to hold sorted input

    while (freesTimes.size() > 0) {
      LocalTime minStart = freesTimes.get(0).get(0); //getting current start time of current small arraylist
      LocalTime minEnd = freesTimes.get(0).get(1); //getting current start time of current small arraylist
      for (int i = 0; i < freesTimes.size(); i++) {
        //if the start of the first free is before the start of the next free then we want to make min start the smaller start and its associated end the smaller end
        //looping through until everything is sorted
        if (freesTimes.get(i).get(0).isBefore(minStart)) {
          minStart = freesTimes.get(i).get(0);
          minEnd = freesTimes.get(i).get(1);
        }
      }
      ArrayList<LocalTime> min = new ArrayList<LocalTime>();
      min.add(minStart); //adding object to small arraylist
      min.add(minEnd); //adding object to small arraylist
      sortedTimes.add(min); //adding small arraylist (min) to larger arraylist
      freesTimes.remove(min); //adjusting the freeTimes list for the loop
    }

    convertMilitaryToStandard(sortedTimes);

    return sortedTimes;
  }

  //**MRS ZHUS CODE EDITED BY CASS */
  public static ArrayList<LocalTime> getTimeFromBlockAndDate(
    String blockName,
    String date
  ) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    //formatter = formatter.withLocale( Locale.US );
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

  public static USSchedule getUSScheduleForDate(LocalDate date) {
    DayOfWeek day = date.getDayOfWeek();

    USSchedule schedule;

    // Determine if this is an adjusted schedule day.
    if (USSchedule.adjustedSchedules.containsKey(date)) {
      String adjustedType = USSchedule.adjustedSchedules.get(date);
      schedule = new USAdjusted1(date, true);
    } else {
      boolean flexDay =
        day == DayOfWeek.MONDAY ||
        day == DayOfWeek.TUESDAY ||
        day == DayOfWeek.THURSDAY;
      if (flexDay) {
        schedule = new USFlexDay(date, true);
      } else if (day == DayOfWeek.FRIDAY) {
        schedule = new USFriday(date, true);
      } else { // Wed schedule
        schedule = new USWednesday(date, true);
      }
      if (schedule.getDayType() == -1) {
        // Not a real school day.
        return null;
      }
    }
    return schedule;
  }

  public static ArrayList<ArrayList<LocalTime>> convertMilitaryToStandard(
    ArrayList<ArrayList<LocalTime>> militaryTimes
  ) {
    //ArrayList of ArrayLists to hold converted times (so that each sub ArrayList holds a start and end time of a free block)
    ArrayList<ArrayList<LocalTime>> standardTimes = new ArrayList<ArrayList<LocalTime>>();

    for (int i = 0; i < militaryTimes.size(); i++) {
      ArrayList<LocalTime> currArr = new ArrayList<LocalTime>();
      for (int j = 0; j < militaryTimes.get(i).size(); j++) {
        LocalTime time = militaryTimes.get(i).get(j);
        int hour = time.getHour();
        if (hour > 12) { //ex. if time is 14:30...
          time.minusHours(12); //changes it to 2:30
        }
        currArr.add(time);
      }
      standardTimes.add(currArr); //fills new ArrayList of ArrayLists
    }

    return standardTimes;
  }



  public static ArrayList<ArrayList<LocalTime>> convertStandardtoMilitary(
    ArrayList<ArrayList<LocalTime>> standardTimes
  ) {
    //ArrayList of ArrayLists to hold converted times (so that each sub ArrayList holds a start and end time of a free block)

    ArrayList<ArrayList<LocalTime>> militaryTimes = new ArrayList<ArrayList<LocalTime>>();

    for (int i = 0; i < standardTimes.size(); i++) {
      ArrayList<LocalTime> currArr = new ArrayList<LocalTime>();

      for (int j = 0; j < standardTimes.get(i).size(); j++) {
        LocalTime time = (standardTimes.get(i)).get(j);
        int hour = time.getHour();

        if (hour < 7) { //ex. if time is 3:00 (during school)...
          time.plusHours(12); //changes it to 15:00
        }

        currArr.add(time);
      }
      militaryTimes.add(currArr); //fills new ArrayList of ArrayLists
    }
    return militaryTimes;
  }


   public static LocalTime convertStandardtoMilitary(LocalTime standardTime) {
    //ArrayList of ArrayLists to hold converted times (so that each sub ArrayList holds a start and end time of a free block)

    LocalTime militaryTime = standardTime;

        int hour = standardTime.getHour();

        if (hour < 7) { //ex. if time is 3:00 (during school)...
          militaryTime = standardTime.plusHours(12); //changes it to 15:00
        }     
    
    return militaryTime;
  }
}
