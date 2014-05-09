/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package busindia;

import java.util.List;
import busindia.RedBuses;
import busindia.Busindia;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author aul
 */
public class StationExtractor {
    
   class StationJson{
       private List<StationInfo> fromPlaceList;

        public StationJson(List<StationInfo> fromPlaceList) {
            this.fromPlaceList = fromPlaceList;
        }

        public List<StationInfo> getFromPlaceList() {
            return fromPlaceList;
        }

        public void setFromPlaceList(List<StationInfo> fromPlaceList) {
            this.fromPlaceList = fromPlaceList;
        }
        
        
        public String toString(){
            return "StationJson [ fromPlaceList = "+fromPlaceList+"]";
        }
       
   }
    
   class StationInfo{
       private String placeID,placeCode,placeName,placeIDAndCodeAndPlaceName;

        public StationInfo(String placeID, String placeCode, String placeName, String placeIDAndCodeAndPlaceName) {
            this.placeID = placeID;
            this.placeCode = placeCode;
            this.placeName = placeName;
            this.placeIDAndCodeAndPlaceName = placeIDAndCodeAndPlaceName;
        }

        public String getPlaceID() {
            return placeID;
        }

        public void setPlaceID(String placeID) {
            this.placeID = placeID;
        }

        public String getPlaceCode() {
            return placeCode;
        }

        public void setPlaceCode(String placeCode) {
            this.placeCode = placeCode;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getPlaceIDAndCodeAndPlaceName() {
            return placeIDAndCodeAndPlaceName;
        }

        public void setPlaceIDAndCodeAndPlaceName(String placeIDAndCodeAndPlaceName) {
            this.placeIDAndCodeAndPlaceName = placeIDAndCodeAndPlaceName;
        }
        
        
       
       
   }
    
   public void extractStation(String source,String dest,String redbusStation){
        List<Busindia.Station> busindiastations=new Busindia().get_station_list(source);
        RedBuses.Station[] redbusstation=new RedBuses().get_station(redbusStation);
       
        List<StationInfo> matched=new ArrayList<>();
        int count=0;
        for(RedBuses.Station s : redbusstation){
            if(s==null) break;System.out.println(s.station_name);
            String redname=s.station_name.toUpperCase();
            
            for(Busindia.Station r : busindiastations){
                String busname=r.cityName.toUpperCase();
                if(busname.equals(redname)){
                    matched.add(new StationInfo(r.cityId,r.cityCode,r.cityName,r.cityDescription));
                }
            }}
            StationJson st=new StationJson(matched);
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(new File("stationlist.json"),st);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        
        
        
        
        
    }
   
    public static void main(String[] args) {
       new StationExtractor().extractStation("get_from_place_list.json", null, "completed_stations.txt");
    }

  
    
}
