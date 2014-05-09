/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busindia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author aul
 */
public class Parser {

    List<BusInfo> buses;

    public  List<BusInfo> parse(String filename) {
        try {
            File input = new File(filename);
            Document doc = Jsoup.parse(input, "UTF-8", "http://busindia.com/");
             buses=new ArrayList<>();

            Elements products = doc.getElementsByClass("products");

            if (products == null || products.size()==0) {
          //      System.out.println("Data unavailabe");
                return null;
            }
            System.out.println(products.size());
            for (Element product : products) {
                try {
                    String corp = product.attr("data-crp");
                    int price = Integer.parseInt(product.attr("data-price"));
                    String time = product.attr("data-time");
                    Elements texts = product.getElementsByClass("fareTextSmall");
                    //Elements texts=product.select("div.fareTextSmall");
                    String[] textString = new String[texts.size()];
                    int count = 0;
                    for (Element text : texts) {
                        textString[count++] = text.text();
                    }

                    Element radioService = product.getElementById("radOServiceID");
                    String radOServiceID = radioService.attr("value");          //Note: If seats are sold out there will be no rad)SeviceID and we can't have radOServiceID adn busId info.
                    String busId=radOServiceID.split(",")[10];

                    int dept = getTime(textString[1].split(",")[0].trim());
                    int arr = getTime(textString[2].split(",")[0].trim());
                    int dur = getTime(textString[3].trim());
                    String via = textString[4].trim();
                    String busType = textString[6].trim();
                    int availableSeats = Integer.parseInt(textString[7].replaceAll(" ", ""));
                    String priceinfo = textString[10].trim();
                    buses.add(new BusInfo(corp,arr,dept,dur,via,busType,availableSeats,price,busId,radOServiceID));
                   
                   // System.out.println("corp:"+corp+"\t price:"+price+"\t time:"+time+"radioService:"+radOServiceID+"\n"+"dept:"+dept+"\t arr:"+arr+"\t dur:"
                     //      + dur+"\n via:"+via+"\t bustype:"+busType+"\n availableSeats:"+availableSeats+"\t PriceInfo:"+priceinfo+"\t busId:"+busId);
                    
                } catch (Exception ex) {
                //    System.out.println(buses.size());
                    System.out.println("Error in file:"+filename+" Possible because seats are sold out and therefore busId is not provided by website.If you wantt"
                            + " to download this data also please change the code to include it");
                   // ex.printStackTrace();
                }

               
            }
        } catch (IOException ex) {
  //          System.out.println("Error");
            ex.printStackTrace();
            return null;
        }
 //       System.out.println("Returning :"+buses.size()+" buses");
        return buses;
    }

    public static void main(String[] args) {
        List<BusInfo> p=new Parser().parse("busNotAvailable.html");
    }

    public static int getTime(String time) {
        boolean isAM;
        boolean isPM;
        int hour = Integer.parseInt(time.split(":")[0].replaceAll(" ", ""));
        int min = Integer.parseInt(time.split(" ")[0].split(":")[1].replaceAll(" ", ""));
        isAM = time.toUpperCase().contains("AM");
        isPM = time.toUpperCase().contains("PM");
        if (isAM || (!(isAM) && !(isPM))) {
            return hour * 60 + min;
        } else {
            return 12 * 60 + hour * 60 + min;
        }

    }

}
