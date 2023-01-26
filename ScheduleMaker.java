import java.io.*;
import java.lang.*;
import java.util.*;


public class ScheduleMaker{
    public static void main(String[] args) throws FileNotFoundException{

        File f = new File("APScheduleSample.csv");
        scanFile(f);
        
    }

    public static void scanFile(File f) throws FileNotFoundException{
        Scanner fileScan = new Scanner(f);
        fileScan.nextLine();

        while (fileScan.hasNextLine()){

            String line = fileScan.nextLine();
            //String line = "Mr. Accetta,\"History; Math\",\"A;B;C\"";

            ArrayList<ArrayList<String>> instanceVarValues = new ArrayList<ArrayList<String>>();

            //System.out.println(line);

            for (int i = 0; i < line.length() - 1; i++){   

                if (i == 0){

                     instanceVarValues.add(new ArrayList<String>(Arrays.asList(line.substring(0, line.indexOf(",")))));

                     i = line.indexOf(",") + 1;

                }

                if (line.charAt(i) == '"'){
                    int nextQuote = line.indexOf("\"", i+1);
                    instanceVarValues.add(
                        new ArrayList<String>(
                            Arrays.asList(
                                line.substring(i + 1, nextQuote).split(",")
                                )
                                )
                                );

                //System.out.println(line.substring(i + 1, nextQuote));

                i = nextQuote + 1;
                //i = line.indexOf(",", i + 1);
                //continue;
                }

                else{
                    int nextComma = line.indexOf(",", i + 1);

                    if (nextComma == -1){
                    nextComma = line.length();
                } 
                    instanceVarValues.add(new ArrayList<String>(Arrays.asList(line.substring(i, nextComma))));
                    i = nextComma;
                }


            
            }
            System.out.println(instanceVarValues);

            }

   

        }


    

    public static String testerTimeConverter(String block, int date){
        String timeToRet = "";
        //pretending there are only 2 possible dates and hard coding what time each block would be 
        if(date == 27){
            if(block.equals("A")){
                timeToRet += "8:00-9:00am";
            }
            else if(block.equals("B")){
                timeToRet += "9:00-10:00am";
            }
            else if(block.equals("C")){
                timeToRet += "10:00-11:00am";
            }
        }
        else{
            if(block.equals("D")){
                timeToRet += "11:00-12:00pm";
            }
            else if(block.equals("E")){
                timeToRet += "12:00-1:00pm";
            }
            else if(block.equals("F")){
                timeToRet += "1:00-2:00pm";
            }

            else{
                timeToRet += "2:00-3:00pm";
            }
            
        }
        System.out.println(timeToRet);
        return timeToRet;
    }

    public static ArrayList<String> getColumns(File f) throws FileNotFoundException {
    Scanner fileScan = new Scanner(f); //new scanner

    String[] firstLineArr = fileScan.nextLine().split(","); //assumes first line is header, grabs it and splits it by comma

    ArrayList<String> headers = new ArrayList<>(Arrays.asList(firstLineArr));//make firstLineArr an array list

    fileScan.close();

    return headers;
  }
}