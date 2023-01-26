import java.io.*;
import java.lang.*;
import java.util.*;

public class APExam extends ScheduleMaker {
    //instance variables
    private String name; //private or public
    private String date; 
    private String startTime; 
    private String endTime; 
    private String department; 

    //constructors
    // public APExam() {
    //     this("AP World History","May 1 2023", "9:00", "12:00", "History" ); 
    // }

    public APExam (ArrayList<ArrayList<String>> instanceVars) {
        this.name = (instanceVars.get(0)).get(0); 
        this.department = (instanceVars.get(1)).get(0);
        this.date = (instanceVars.get(2)).get(0); 
        this.startTime = (instanceVars.get(3)).get(0); 
        this.endTime = (instanceVars.get(4)).get(0); 
    }

    //"getter" methods
    public String getName () {
        return name; 
    }

    public String getDate() {
        return date; 
    }

    public String getStartTime() {
        return startTime; 
    }

    public String getEndTime() {
        return endTime; 
    }

    public String getDepartment() {
        return department; 
    }

    //"setter" methods 
    public void setName (String newName) {
        name = newName; 
    }

    public void setDate(String newDate) {
        date = newDate; 
    }

    public void setStartTime(String newStartTime) {
        startTime = newStartTime; 
    }
    
    public void setEndTime(String newEndTime) {
        endTime = newEndTime; 
    }

    public void setDepartment(String newDepartment) {
        department = newDepartment; 
    }

    public String toString() {
        String toRet  = ""; //to return 
        toRet += "Name: " + name + "\n"; 
        toRet += "Date: " + date + "\n";
        toRet += "Start time: " + startTime + "\n";
        toRet += "End time: " + endTime + "\n";
        toRet += "Department: " + department + "\n";
        return toRet; 
    }
}   
