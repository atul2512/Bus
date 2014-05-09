/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package busindia;

import static busindia.Busindia.get_buses;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author aul
 */
class Downloader extends Thread{
        
        public  static Set<String> started=new HashSet<>();
        private String checkInDate,checkOutDate,maxValidreserve,journeyDate,folderLocataion;
        private List<Busindia.Station> A,B;
        private String name;

        public Downloader(String checkInDate, String checkOutDate, String maxValidreserve, String journeyDate, String folderLocataion, List<Busindia.Station> A, List<Busindia.Station> B,String name) {
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.maxValidreserve = maxValidreserve;
            this.journeyDate = journeyDate;
            this.folderLocataion = folderLocataion;
            this.A = A;
            this.B = B;
            this.name=name;
        }
        
        public String getDownloaderName(){
            return this.name;
        }

        public void run()                       
        {   
            downloadData(A,B,checkInDate,checkOutDate,maxValidreserve,journeyDate,folderLocataion,started);
        }
        
          public void downloadData(List<Busindia.Station> A, List<Busindia.Station> B, String checkInDate, String checkOutDate, String maxValidreserve, String journeyDate, String folderLocation, Set<String> started) {
        for (Busindia.Station from : A) {
            boolean isStarted = false;
            synchronized (this) {                       //for threading.A thread will have to download all destination for a single source.
                if (started.contains(from.cityId)) {
                    isStarted = true;
                } else {
                    started.add(from.cityId);
                    System.out.println("########"+this.getDownloaderName()+"\t is downloading"+from.cityId+" ######");
                    
                }
            }
            if (!isStarted) {
                for (Busindia.Station to : B) {
                    String filename = Downloader.getFileName(from,to);
                    if (!from.cityId.equals(to.cityId) && !Busindia.checkIfFileExist(folderLocation + "/" + filename)) {
                        get_buses(checkInDate, checkOutDate, from, to, maxValidreserve, journeyDate, folderLocation, filename);
                    }
                }
            }
        }
    }
          
           public static String getFileName(Busindia.Station from,Busindia.Station to){
        return from.cityId + "-" + to.cityId + ".html";

}
    }

   