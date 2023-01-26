import java.io.*;
import java.lang.*;
import java.util.*;

public class Teacher extends ScheduleMaker {
    //initalize instance variables for class including name, department, how many exams proctored, and what their free blocsk are
    private String name; 
    private List<String> department; 
    private List<String> freePeriods; 
    private int examsProctored; 

    //4 argument constructor to create basic teacher objects
    public Teacher (String name, List<String> department, List<String> freePeriods, int examsProctored) {
        this.name = name; 
        this.department = department; 
        this.freePeriods = freePeriods; 
        this.examsProctored = examsProctored; 
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

    //function to return the number of exams the specific teacher has proctored
    public int getExamsProctored() {
        return examsProctored; 
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
    
    //function to set the number of exams proctored to the instance variable
    public void setExamsProctored (int newExamsProctored)
        this.examsProctored = newExamsProctored; 
}

