/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busindia;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import java.util.*;
import java.util.Map.Entry;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author aul
 */
public class Busindia {

    /**
     * @param args the command line arguments
     */
    boolean stationExist;
    private final NeoDB neodb;

    public Busindia(){
        neodb=null;
    }
    

    public Busindia(boolean stationExist,String db_path,String pathToConfig) {
        this.stationExist = stationExist;
        neodb = new NeoDB(db_path,pathToConfig);
    }

    public enum BusIndia {

        getStationURL("http://www.busindia.com/busindia2/get_from_place_list?startsWith=%25"),
        getBusesPostURL("www.busindia.com/busindia2/busBooking_Availability");

        private String value;

        BusIndia(String s) {
            this.value = s;

        }

        String get_value() {
            return value;
        }

    }

    public static void append_file(String filename, String s) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            out.println(s);
        } catch (IOException ex) {
            System.out.println("LOG FILE NOT FOUND TO UPDATE DAT");
            ex.printStackTrace();
        }
    }

    public static void write_file(String filename, String s) {
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

    public class Station {

        public String cityId;
        public String cityName;
        public String cityCode;
        public String cityDescription;

        public Station(String cityId, String cityName, String cityCode, String cityDescription) {
            this.cityId = cityId;
            this.cityName = cityName;
            this.cityCode = cityCode;
            this.cityDescription = cityDescription;
        }

        public Station() {
        }

    }

    public  List<Station> get_station_list(String filename) {
        List<Station> stations = new ArrayList<Station>();
        String json_data = readData(filename);

        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(json_data);
            JsonNode all_data = rootNode.get("fromPlaceList");
            for (JsonNode data : all_data) {
                Station s = new Station();
                s.cityId = data.get("placeID").asText();
                s.cityCode = data.get("placeCode").asText();
                s.cityName = data.get("placeName").asText();
                s.cityDescription = data.get("placeIDAndCodeAndPlaceName").asText();
                stations.add(s);
            }
            return stations;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String get_request(String url) {
        String response = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(url);
            System.out.println("GET :" + url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpclient.execute(httpGet, responseHandler);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("ERROR On URL:" + url);
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR On URL:" + url);
        }

        return response;
    }

    public static String post_request(String url) {
        String response = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            post.setHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:27.0) Gecko/20100101 Firefox/27.0");
            post.setHeader("Referer", "http://www.busindia.com/busindia2/home");
            post.setHeader("Host", "www.busindia.com");
            post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpclient.execute(post, responseHandler);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("ERROR On URL:" + url);
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR On URL:" + url);
        }

        return response;
    }

    public static void get_stations(String filename) {
        String json_list = get_request(BusIndia.getStationURL.get_value());
        write_file(filename, json_list);
    }
    /**
     * Need to be run when it is requied to download station List.Right now its redundatnt.
     */
    public void run() {
        if (!stationExist) {
            get_stations("stationslist.json");
        }

    }

    /**
     * Po
    
     */
    public static Map<String, String> getbus_post_map(final String checkInDate, final String checkOutDate, final Station from, final Station to, final String maxValidReservDate, final String journeyDate) {
        Map<String, String> params = new HashMap<String, String>() {
            {
                put("adults", "1");
                put("checkInDate", checkInDate);
                put("checkOutDate", checkOutDate);
                put("children", "0");
                put("hiddenBusAdvSearchFlag", "N");

                put("hiddenCurrentDate", checkInDate);
                put("hiddenFromPlaceCode", from.cityCode);
                put("hiddenFromPlaceID", from.cityId);
                put("hiddenFromPlaceName", from.cityName);
                put("hiddenFromPlaceInfo", from.cityDescription);

                put("hiddenToPlaceCode", to.cityCode);
                put("hiddenToPlaceID", to.cityId);
                put("hiddenToPlaceName", to.cityName);
                put("hiddenToPlaceInfo", to.cityDescription);;

                put("hiddenJourneyType", "0");
                put("hiddenMaxNoOfPassengers", "16");

                put("hiddenMaxValidReservDate", maxValidReservDate);
                put("hiddenOnwardJourneyDate", journeyDate);

                put("hiddenOnwardSearchDay", "J");
                put("hiddenOnwardTimeSlab", "00:00-23:59");
                put("hiddenReturnSearchDay", "J");
                put("hiddenTotalChildren", "");
                put("hiddenTotalPassengers", "1");

                put("matchFromPlace", from.cityName);
                put("matchToPlace", to.cityName);

                put("numberOfRooms", "1");
                put("radBookingType", "BUS");
                put("radOnewayOrReturnTrip", "0");
                put("selectCategory", "0");
                put("selectCity", "none");
                put("selectCorp", "0");
                put("selectFromPlace", from.cityId);
                put("selectToPlace", to.cityId);
                put("txtOnwardDate", journeyDate);
                put("txtdeptDateRtrip", journeyDate);

                put("selectMultiTripTimeSlab1", "00:00-23:59");
                put("selectMultiTripTimeSlab2", "00:00-23:59");
                put("selectMultiTripTimeSlab3", "00:00-23:59");
                put("selectOnwardTimeSlab", "00:00-23:59");
                put("selectReturnTimeSlab", "00:00-23:59");

                put("txtReturnDate", "DD/MM/YYYY");
                put("txtdeptDateRtrip1", "DD/MM/YYYY");
                put("txtdeptDateRtrip2", "DD/MM/YYYY");
                put("txtdeptDateRtrip3", "DD/MM/YYYY");
                put("txtretnDateRtrip", "DD/MM/YYYY");

            }
        };

        return params;
    }
   

    

    /**
     * post request from bangalore to goa made on 31/03/2014 for 15/04/2014
     *
     *
     * @param fromCode
     * @param fromName
     * @param toId
     * @param toName
     * @param toCode
     * @param tripDate
     */
    public static void get_buses(String checkInDate, String checkOutDate, Station from, Station to, String maxValidReservDate, String journeyDate, String foldername, String filename) {
        Map<String, String> params = getbus_post_map(checkInDate, checkOutDate, from, to, maxValidReservDate, journeyDate);
        URIBuilder builder = new URIBuilder();
        builder = builder.setScheme("http").setHost(BusIndia.getBusesPostURL.get_value());
        for (Entry<String, String> param : params.entrySet()) {
            builder.setParameter(param.getKey(), param.getValue());
        }
        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException ex) {
            System.out.println("Unable to form uri");
        }
        String url = null;
        try {
            url = uri.toURL().toString();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
//        System.out.println(from.cityName+"->"+to.cityName);
        //       long startTime = System.currentTimeMillis();
        String downloadedData = post_request(url);
    //     long endTime = System.currentTimeMillis();
        //  System.out.println( "Total execution time for fetching data of " + from.cityName + "->" + to.cityName+":"+ (endTime - startTime)/1000 );
        // append_file("timetaken.txt",from.cityName + "->" + to.cityName+":"+ (endTime - startTime)/1000+"\n");

      //  System.out.println(foldername + "/" + filename);
        // startTime = System.currentTimeMillis();        
        write_file(foldername + "/" + filename, downloadedData);
      //  endTime=System.currentTimeMillis();  
        //  System.out.println("Total execution time for writing data: " + (endTime - startTime)/1000 );

    }

    public void start(String stationFile,boolean downloadData,boolean populateData,int threads,String checkInDate,String checkOutDate,String journeyDate,String maxValidreserve,String folderLocataion) {
        List<Station> s = get_station_list(stationFile);
        if(downloadData){
            Downloader[] downloaders = new Downloader[threads];
            int count = 0;
            for (int i = 0; i < downloaders.length; i++) {
                downloaders[i] = new Downloader(checkInDate, checkOutDate, maxValidreserve, journeyDate, folderLocataion, s, s, "Thread:" + (++count));
            }
            for (int i = 0; i < downloaders.length; i++) {
                //downloadData(s,s,checkInDate,checkOutDate,maxValidreserve,journeyDate,folderLocataion,started);
                downloaders[i].start();
            }
            for(Downloader d: downloaders){
                try {
        //System.out.println("Inside join for d:"+d.getDownloaderName());
                    d.join();
                } catch (InterruptedException ex) {
                       ex.printStackTrace();
                }
            }
        }
  //      System.out.println("1");
  //      System.out.println(s.size());
        if(populateData){
            neodb.create_station(s);
            populateData(s,s,folderLocataion);
    //        System.out.println("test");
        }
    }
    
    public void populateData(List<Station> A,List<Station> B,String folderLocation){
        Parser p;
        Set<String> set=new HashSet<String>();
        for(Station from:A){
            String aId=from.cityId;
            for(Station to:B){
                p=new Parser();
                String filename=Downloader.getFileName(from, to);
                String fileLocation=folderLocation+"/" +filename;
              //  System.out.println(fileLocation);
                
                if (checkIfFileExist(fileLocation) && !set.contains(filename)) {
                    //System.out.println("FOr file:"+dataFilename);
                  
                       List<BusInfo> buses=p.parse(fileLocation);
                       
                       if(buses==null) {}
                       else
                            neodb.update_graph_database(buses, from.cityName, from.cityId, to.cityName, to.cityId);
            }
                set.add(filename);
        }
    }
    }

    public static boolean checkIfFileExist(String fileLocation) {
        File f = new File(fileLocation);
        return f.exists() && !f.isDirectory();
    }
    

    public static void main(String[] args) {
       
        new Busindia(true,"database/data2",null).start("stationlist.json",false,true,2,"23/04/2014","24/4/2014","30/04/2014","23/05/2014","data");
    }

}
