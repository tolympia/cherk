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

  public static void generateListsOfObjects(
    List<File> f,
    List<APExam> examObjects,
    List<Teacher> teacherObjects
  ) throws FileNotFoundException {
    for (int j = 0; j < f.size(); j++) {
      Scanner fileScan = new Scanner(f.get(j));
      fileScan.nextLine();

      while (fileScan.hasNextLine()) {
        String line = fileScan.nextLine();

        ArrayList<ArrayList<String>> instanceVarValues = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < line.length() - 1; i++) {
          if (line.charAt(i) == '"') {
            int nextQuote = line.indexOf("\"", i + 1);
            instanceVarValues.add(
              new ArrayList<String>(
                Arrays.asList(line.substring(i + 1, nextQuote).split(","))
              )
            );
            i = nextQuote + 1; //"moving" i to the end of the quote
          } 
          else {
            int nextComma = line.indexOf(",", i + 1);

            if (nextComma == -1) {
              nextComma = line.length();
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
}
