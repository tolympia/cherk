// TOZ, January 2023
// Represents a specific date and the classes/blocks occurring on that date.
import java.util.*;
import java.time.*;


public abstract class Schedule {
  public static LocalDate firstDayOfSchool;
  public static LocalDate lastDayOfSchool;

  protected LocalDate date;  // This particular date, e.g. May 1, 2023.
  public int dayType = -1;  // Which day in the rotation is it? e.g. Day 3.
  public ArrayList<Block> blocks;  // Block objects in today's schedule.
  public ArrayList<String> blockNames;  // Names of courses in today's schedule.

  public abstract int getDayType();
  public abstract boolean isThereBlockToday(String name);

  protected void printSummary() {
    System.out.println("DAY TYPE: " + this.dayType);
    System.out.println("BLOCKS:");
    for (int i = 0; i < this.blockNames.size(); i++) {
      System.out.print("[" + blockNames.get(i) +  "] ");
      System.out.println(blocks.get(i).toString());
    }
  }
}
