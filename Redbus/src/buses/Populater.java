/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;

import buses.Buses.Station;
import buses.Buses.StationList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Atul Agrawal
 */
public class Populater {

    StationList station_completed;
    StationList station_uncompleted;
    Buses buses;
    String complete, uncomplete;
    private final NeoDb neodb;
    Parser parser;
    String downloadLocation;
    String downloadcompleteLocation;
    int threadSleep;

    public Populater(String complete, String uncomplete, String db_path,String downloadLocation,int threadSleep) {
        parser = new Parser();
        buses = new Buses();
        this.complete = complete;
        this.uncomplete = uncomplete;
        station_completed = new Buses().get_station(complete);
        station_uncompleted = new Buses().get_station(uncomplete);
        neodb = new NeoDb(db_path);
        this.downloadLocation=downloadLocation;
        this.downloadcompleteLocation=downloadLocation+"_"+"complete";
        this.threadSleep=threadSleep;
    }
    /*
     @aram date Date is of form "31-Jan-2014"
     */

    /**
     *
     * @param date
     */
    public void populate(String date) {

        ///String filename = "log/station_completed.txt";
        Station[] stationsC = station_completed.stations;
        Station[] stationsU = station_uncompleted.stations;

        int lengthC = station_completed.length;
        int lengthU = station_uncompleted.length;

    //    System.out.println("----------\n lengthC:" + lengthC + "\n" + "FirstStation:" + stationsC[0].station_name);
        //   System.out.println("----------\n lengthU:" + lengthU + "\n" + "FirstStation:" + stationsU[0].station_name);
        downloadData(stationsC, stationsU, date, lengthC, lengthU);

        downloadData(stationsU, stationsU, date, lengthU, lengthU);

        downloadData(stationsU, stationsC, date, lengthU, lengthC);

        neodb.new_create_station(station_uncompleted);
        neodb.new_create_station(station_completed);

        populateData(stationsC, stationsU, lengthC, lengthU);
        populateData(stationsU, stationsU, lengthU, lengthU);
        populateData(stationsU, stationsC, lengthU, lengthC);

        update_file(complete, stationsU, lengthU);

        write_file(uncomplete, "");
    }

    private void update_file(String filename, Station[] stations, int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += "\n";
            s += "\n";
            s += stations[i].station_name;
            s += "\n";
            s += stations[i].id;

        }
        append_file(complete, s);
    }

    private void write_file(String filename, String s) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
            writer.print(s);
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FIle not found:" + filename);
        } catch (UnsupportedEncodingException ex) {
        } finally {
            writer.close();
        }

    }

    public void append_file(String filename, String s) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            out.print(s);
        } catch (IOException ex) {
            System.out.println("LOG FILE NOT FOUND TO UPDATE DAT");
            ex.printStackTrace();
        }
    }

    public void downloadData(Station[] A, Station[] B, String date, int lengthA, int lengthB) {
        int j;
        for (int i = 0; i < lengthA; i++) {
            String AName = A[i].station_name;
            String AId = A[i].id;
            for (j = 0; j < lengthB; j++) {

                String BName = B[j].station_name;
                String BId = B[j].id;
                String dataFilename = AId + "-" + AName + "-" + BId + "-" + BName + ".txt";
                String fileLocation=downloadLocation+"/"+dataFilename;
                String movedFile=downloadcompleteLocation+"/"+dataFilename;
                //  System.out.println(dataFilename);
                if (!(AId.equals(BId)) && !(checkIfFileExist(fileLocation) || checkIfFileExist(movedFile))) {
                    try {
                        Thread.sleep(threadSleep);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    if (B[j] == null) {
                        break;
                    }
                    buses.get_redbus_data(AName, AId, BName, BId, date,fileLocation);
                } 
            }
      //      System.out.println("For station:" + A[i].station_name + " total Downloads=" + totalDownloads + " totalExists=" + totalExist);
            j = 0;
        }
    }

    public void populateData(Station[] A, Station[] B, int lengthA, int lengthB) {
        for (int i = 0; i < lengthA; i++) {
            String AName = A[i].station_name;
            String AId = A[i].id;
            for (int j = 0; j < lengthB; j++) {
                String BName = B[j].station_name;
                String BId = B[j].id;
                String dataFilename = AId + "-" + AName + "-" + BId + "-" + BName + ".txt";
               
                String fileLocation=downloadLocation+"/"+dataFilename;
                
                if (checkIfFileExist(fileLocation)) {
                    //System.out.println("FOr file:"+dataFilename);
                    String data = readData(fileLocation);
                    if (data != null) {
                        data = format(data);
                        List<RedBuses> redbus_data = Parser.get_data(data);
                        if (redbus_data != null) {
                            neodb.update_graph_database(redbus_data, AName, AId, BName, BId);
                        }
                        

                    }
                     moveFile(fileLocation,downloadcompleteLocation);
                }
            }
        }
    }
    
    public void moveFile(String src,String dest){
        try {
            File srcFile=new File(src);
            File destDir=new File(dest);
            FileUtils.moveFileToDirectory(srcFile, destDir, true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String readData(String dataFilename) {
        try {
            //   String json_data = buses.get_redbus_data(stations[i].station_name, stations[i].id, stations[j].station_name, stations[j].id, date);
            String json_data = FileUtils.readFileToString(new File(dataFilename));
            return json_data;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Unable to read file:" + dataFilename);
            return null;
        }
    }

    private boolean checkIfFileExist(String fileLocation) {
        File f = new File(fileLocation);
        return f.exists() && !f.isDirectory();
    }

    private String format(String json_data) {
        try {
            if (json_data.charAt(1) == '"' || json_data.charAt(2) == '"' || json_data.charAt(3) == '"') {
                //System.out.println("Adding comma not needed");
                return json_data;
            }
            json_data = json_data.replaceFirst("status", "\"status\"");
            json_data = json_data.replaceFirst("data", "\"data\"");
            return json_data;
        } catch (Exception ex) {
            return null;
        }

    }

    public static void main(String[] args) {
        //new Populater("src/buses/completed_stations.txt", "src/buses/new_stations.txt", "database/final/redbus253").populate("27-Mar-2014");
        new Populater("src/buses/124.txt", "src/buses/123.txt", "database/final/redbus261","datatest4",500).populate("27-April-2014");
     
    }

}
