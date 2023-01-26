import java.io.*;
import java.lang.*;
import java.util.*;

public class Teacher extends ScheduleMaker {
    //instance variables
    private String name; 
    private List<String> department; 
    private List<String> freePeriods; 
    private int examsProctored; 
    

    //constructors
    public Teacher() {
        this("Mr. Accetta","A,B", "History, Math", "1"); 
    }

    public APExam (String name, List<String> department, List<String> freePeriods, int examsProctored) {
        this.name = name; 
        this.department = department; 
        this.freePeriods = freePeriods; 
        this.examsProctored = examsProctored; 
    }


    //"getter" methods
    public String getName () {
        return name; 
    }

    public List<String> getDepartment() {
        return department; 
    }

    public List<String> getFreePeriods() {
        return freePeriods; 
    }

    public int getExamsProctored() {
        return examsProctored; 
    }


    //"setter" methods 
    public void setName (String newName) {
        name = newName; 
    }

    public void setDepartment(List<String> newDepartment) {
        department = newDepartment; 
    }

    public void setFreePeriods(List<String> newFreePeriods) {
        freePeriods = newFreePeriods; 
    }
    
    public void setExamsProctored (int newExamsProctored)
        examsProctored = newExamsProctored; 
}

