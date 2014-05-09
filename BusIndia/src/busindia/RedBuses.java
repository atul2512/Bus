package busindia;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.utils.URIBuilder;

/**
 * This is the class to query bus sites and extract vital information
 *
 * @author Atul Agrawal
 */
public class RedBuses {

    StationList station_list;

    public RedBuses() {
        station_list = new StationList();
    }

    public class Station {

        String station_name;
        String id;
    }

    public class StationList {

        Station[] stations;
        int length;
    }

    public enum RedBus {

        URLBASE("www.redbus.in/booking/SearchResultsJSON.aspx"),
        FROMID("fromcityid"),
        FROMNAME("fromcityname"),
        TOCITYID("tocityid"),
        TOCITYNAME("tocityname");

        private String value;

        RedBus(String s) {
            this.value = s;
        }

        String get_value() {
            return value;
        }

    }

    final int MAX_STATION = 5500;

    public Station[] get_station(String filename) {
        BufferedReader reader;
        try {
            System.out.println("Reading file name"+filename);
            reader = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to found file");

            return null;
        }

        final int totalargs = 2;
        final int offset = 1;
        int count = 0;
        Station[] stations = new Station[MAX_STATION];

        while (true) {
            try {
                String[] data = parse_file(totalargs, offset, reader);
                 
                if (data == null) {
                    System.out.println("Null Data FOund:Breaking");
                    reader.close();
                    break;
                }
                else{
                }

                Station k = new Station();
                k.station_name = data[0];
                k.id = data[1];
                stations[count] = k;

                count++;
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

        }
        Station[] Tstations = new Station[count];int f=0;
        for(Station s:Tstations)
            Tstations[f++]=stations[f];
        return Tstations;

    }

    public String[] parse_file(int total_args, int offset, BufferedReader reader) throws IOException {
        String[] s = new String[total_args];
        String line;
        for (int i = 0; i < total_args; i++) {
            line = reader.readLine();
            if (line == null) {
                return null;
            } else {
                s[i] = line;
            }
        }
        for (int j = 0; j < offset; j++) {
            reader.readLine();
        }
        return s;
    }
    /*
     Typical Json request GET www.redbus.in/booking/SearchResultsJSON.aspx/
     ?fromcityid=462&fromcityname=Mumbai&tocityid=210&tocityname=Goa&doj=30-Jan-2014&bustype=any&opId=0
     */

    public String get_redbus_data(String origin, String origin_id, String dest, String dest_id, String date) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(RedBus.URLBASE.get_value())
                .setParameter(RedBus.FROMID.get_value(), origin_id)
                .setParameter(RedBus.FROMNAME.get_value(), origin)
                .setParameter(RedBus.TOCITYID.get_value(), dest_id)
                .setParameter(RedBus.TOCITYNAME.get_value(), dest)
                .setParameter("bustype", "any")
                .setParameter("doj", date)
                .setParameter("opId", "0");

        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException ex) {
        }

        String url = null;
        try {
            url = uri.toURL().toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RedBuses.class.getName()).log(Level.SEVERE, null, ex);
        }

        String response = get_request(url);

        write_file("data/" + origin_id + "-" + origin + "-" + dest_id + "-" + dest + ".txt", response);
        append_file("log/date-log.txt", origin_id + "-" + origin + "-" + dest_id + "-" + dest + "------" + date);

        return response;
    }

    public void append_file(String filename, String s) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            out.println(s);
        } catch (IOException ex) {
                System.out.println("LOG FILE NOT FOUND TO UPDATE DAT");
                ex.printStackTrace();
        }
    }

    public void write_file(String filename, String s) {
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

    public String get_request(String url) {
        String response=null;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(url);
            // System.out.println("GET :"+url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            response = httpclient.execute(httpGet, responseHandler);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("ERROR On URL:" + url);
            return null;
        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("ERROR On URL:" + url);
        }
        
        return response;
    }

   

}
