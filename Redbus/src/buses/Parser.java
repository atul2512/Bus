/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author aul
 *
 */
public class Parser {

    static Pattern time_pattern;

    public Parser() {
        time_pattern = Parser.get_time_pattern();

    }

    public static List<RedBuses> get_data(String json_data) {
        if (json_data == null) {
            return null;
        }
        List<RedBuses> buses = new ArrayList<>();

        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode rootNode = m.readTree(json_data);
            if (rootNode.path("status").intValue() != 200) {
                return null;
            }
            JsonNode all_data = rootNode.get("data");

            for (int i = 0; i < all_data.size(); i++) {
                try {
                    List<RedBuses.Points> bps = new ArrayList<>();
                    List<RedBuses.Points> dps = new ArrayList<>();
                    List<Integer> fare = new ArrayList<>();
                    JsonNode data = all_data.get(i);
                    JsonNode bp_node = data.get("BPLst");
                    JsonNode dp_node = data.get("DPLst");
                    JsonNode fare_node = data.get("FrLst");
                    for (JsonNode node : bp_node) {
                        bps.add(new RedBuses().new Points(node.path("ID").asText(), node.path("Loc").toString(), node.path("Tm").asInt()));
                    }
                    for (JsonNode node : dp_node) {
                        dps.add(new RedBuses().new Points(node.path("ID").asText(), node.path("Loc").toString(), node.path("Tm").asInt()));
                    }
                    for (JsonNode node : fare_node) {
                        fare.add(node.asInt());
                        //System.out.println("1"+node.asInt()+"2 "+node.asText()+" "+node.toString()+" "+node.intValue()+" "+fare_node.asInt()+" "+fare_node.intValue());
                    }

                    boolean isAc = data.path("IsAc").asBoolean();
                    boolean isNAc = data.path("IsNAc").asBoolean();
                    boolean isSlpr = data.path("IsSlpr").asBoolean();
                    float rating = (data.path("Rtg").path("totRt").floatValue());
                    //    System.out.println(rating);
                    if (rating == 0.0) {
                        rating = (Float.parseFloat(data.path("Rtg").path("Op").asText()));
                        //       System.out.println(rating);
//                    System.out.println("Rating:"+rating);
                    }
                    List<Integer> time = get_time(data);
                    //        System.out.println("DpTm:"+time.get(0)+"ArTm:"+time.get(1)+" DurTm:"+time.get(2));
                    RedBuses b = new RedBuses(data.path("RtId").asText(), time.get(2), time.get(0), time.get(1), bps, dps, fare, isAc, isNAc, isSlpr, rating);
                    buses.add(b);
                    //         System.out.println("BUS");
                    //         System.out.println(b.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return buses;
        } catch (IOException ex) {
            //System.out.println(ex.toString());
            return null;
        } catch (Exception ex) {
           // System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * '/Date(1397701800000)/'
     *
     * @param data
     * @return
     */
    public static List<Integer> get_time(JsonNode data) {
        String date = "Date";
        List<Integer> time = new ArrayList<>();
        boolean isDateFormat = false;
        String dpTm = data.path("DpTm").textValue();
        String arTm = data.path("ArTm").textValue();
        if (dpTm == null) {
            arTm = data.path("ArTm").asText();
            dpTm = data.path("DpTm").asText();
        }
        if (arTm.contains(date)) {
            isDateFormat = true;
        }
        arTm = get_ints(arTm);
        //System.out.println(arTm);
        dpTm = get_ints(dpTm);
        //System.out.println(dpTm);
        if (isDateFormat) {
            time.add(convertMilis(dpTm));
            time.add(convertMilis(arTm));
            time.add(get_duration(arTm, dpTm));
        } else {
            String durTm = data.path("DurTm").asText();

            time.add(Integer.parseInt(dpTm));
            time.add(Integer.parseInt(arTm));
            time.add(Integer.parseInt(durTm));

        }
        return time;
    }

    public static int get_duration(String a, String d) {
        long time = Long.parseLong(a) - Long.parseLong(d);
        int minute = (int) time / (1000 * 60);
        return minute;
    }

    public static String get_ints(String time) {
        //System.out.println(time);
        try {
            Integer.parseInt(time);
            return time;
        } catch (Exception ex) {
            int startBracket = time.indexOf('(');
            int closeBracket = time.indexOf(')');
            return time.substring(startBracket + 1, closeBracket);
        }
    }

    public static Pattern get_time_pattern() {
        return Pattern.compile("\\d+");

    }

    public static int convertMilis(String millis) {
        long time = Long.parseLong(millis);
        Date date = new Date(time);
        return date.getHours() * 60 + date.getMinutes();
    }

    public void test() {
        String file1 = "/home/aul/Desktop/a1.json";
        String file2 = "/home/aul/Desktop/a2.json";
        List<RedBuses> buses = get_data(readData(file2));
        System.out.println("For:" + file1 + "\n");
        for (RedBuses b : buses) {
            System.out.println("---BUS-----");
            System.out.println(b.toString());
        }
        /*
         List<RedBuses> buses1=get_data(readData(file2));
         System.out.println("For:"+file2+"\n");
         for(RedBuses b: buses1){
         b.toString();
         System.out.println("\n");
         }*/

    }

    public static String readData(String dataFilename) {
        try {
            //   String json_data = buses.get_redbus_data(stations[i].station_name, stations[i].id, stations[j].station_name, stations[j].id, date);
            String json_data = FileUtils.readFileToString(new File(dataFilename));
            return json_data;
        } catch (IOException ex) {
            System.out.println("Unable to read file:" + dataFilename);
            ex.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        new Parser().test();
    }

    public Integer convertTimetoStandardFormat(String time) {

        if (time.equals("12:00 AM") || time.equals("00:00 AM")) {
            return 0;
        }

        try {
            boolean isAM;
            int a = 12 * 60;
            isAM = (time.contains("AM") || time.contains("am"));
            if (isAM) {
                a = 0;
            }
            if (isAM && time.substring(0, 2).equals("12")) {
                return Integer.parseInt(time.substring(3, 5));
            }
            int intTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3, 5)) + a;
            return intTime;
        } catch (Exception ex) {
            System.out.println("unable to convert " + time + " to string");
            return null;
        }

    }
}
