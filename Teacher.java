import java.io.*;
import java.lang.*;
import java.util.*;

public class Teacher extends ScheduleMaker {
    //initalize instance variables for class including name, department, how many exams proctored, and what their free blocsk are
    private String name; 
    private List<String> department; 
    private List<String> freePeriods; 
    private int examsProctored; 
    private static int maxNumProctored = 0;
    //private int differenceFromMax;



    //4 argument constructor to create basic teacher objects
    public Teacher (ArrayList<ArrayList<String>> instanceVars) {

        this.name = (instanceVars.get(0)).get(0);
        this.department = instanceVars.get(1); 
        this.freePeriods = instanceVars.get(2); 
    }

    //function to return the name of the teacher
    public String getName () {
        return name; 
    }


    //function to return the list of departments 
     public List<String> getDepartment() {
        return department; 
    }

    //function to return the list of free blocks the teacher has
    public List<String> getFreePeriods() {
        return freePeriods; 
    }

    public int getNumExamsProctored() {
        return examsProctored; 
    }

    public int getDifferenceFromMax() {
        return maxNumProctored - examsProctored; 
    }



    public void incrementNumExamsProctored() {
        examsProctored++; 
        if (this.examsProctored > maxNumProctored){

            maxNumProctored = this.examsProctored;

        }

    }




    //function to set the name of a teacher to instance variable
    public void setName (String newName) {
        this.name = newName; 
    }

    //function to set a list of the departments a teacher is a part of to the instance varibale
    public void setDepartment(List<String> newDepartment) {
        this.department = newDepartment; 
    }

    //function to set a list of the free blocks a teacher has to the instance varibale
    public void setFreePeriods(List<String> newFreePeriods) {
        this.freePeriods = newFreePeriods; 
    }
    

      public String toString() {
        String toRet  = ""; //to return 
        toRet += "Name: " + name + "\n"; 
        toRet += "Department: " + department + "\n";
        toRet += "free periods: " + freePeriods + "\n";
        toRet += "exams proctored: " + examsProctored + "\n";
        return toRet; 
    }



}

