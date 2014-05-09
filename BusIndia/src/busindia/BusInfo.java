/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package busindia;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aul
 */
public class BusInfo {
    
        public String corporation;
        public int arrTime;
        public int deptTime;
        public int duration;
        public String via;
        public String busType;
        public int availableSeat;
        public int price;
        public String id;
        public String radOServiceID;

    public BusInfo(String corpooration, int arrTime, int deptTime, int duration, String via, String busType, int availableSeat, int price, String id, String radOServiceID) {
        this.corporation = corpooration;
        this.arrTime = arrTime;
        this.deptTime = deptTime;
        this.duration = duration;
        this.via = via;
        this.busType = busType;
        this.availableSeat = availableSeat;
        this.price = price;
        this.id = id;
        this.radOServiceID = radOServiceID;
    }
    
    public Map<String,Object> get_map(){
        Map<String,Object> map=new HashMap<String,Object>(){
            {
                put("corporation",corporation);
                put("arr_time",arrTime);
                put("dept_time",deptTime);
                put("duration",duration);
                put("rtid",id);
                put("radOServiceID",radOServiceID);
                put("fare",price);
                put("available_seat",availableSeat);
                put("via",via);
                put("busClass",busType);
                
                
                
                
            }
        };
        return map;
        
    }
        
   
        
        
    
}
