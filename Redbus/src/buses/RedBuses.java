/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;

import java.util.List;

/**
 *
 * @author aul
 */
public class RedBuses {

    public RedBuses() {
    }

    public class Points {

        public Points(String s1, String s2, Integer s3) {
            id = s1;
            location = s2;
            time = s3;
        }
        String id;
        String location;
        Integer time;
    }

    String rtid;
    Integer journey_dur;
    Integer dept_time;
    Integer arr_time;

    List<Points> bps;
    List<Points> dps;
    
    float rating;
    List<Integer> fare;
    
    boolean isAc,isSlpr,isNAc;
    

    public RedBuses(String rtid, Integer journey_dur, Integer dept_time, Integer arr_time, List<Points> bps, List<Points> dps,List<Integer> fare,boolean isAc,boolean isNAc,boolean isSlpr,float rating) {
        this.rtid = rtid;
        this.journey_dur = journey_dur;
        this.dept_time = dept_time;
        this.arr_time = arr_time;
        this.bps = bps;
        this.dps = dps;
        this.fare=fare;
        this.isAc=isAc;
        this.isNAc=isNAc;
        this.isSlpr=isSlpr;
        this.rating=rating;
    }

    public String displayPoints(List<Points> p) {
        String s = null;
        for (Points p1 : p) {
            s += ("Point id:" + p1.id + " Point_Location:" + p1.location + " Point_time:" + p1.time);
            s = s + "\n";
        }
        return s;
    }
    
    @Override
    public String toString() {
        String s="";
        for(int f:fare){
            s+=f+",";
        }
        return ("Bus ID:" + rtid + " journey_dur:" + journey_dur + " dept_time:" + dept_time + " arr_time:" + arr_time + "\n" + "Arrivals:" + displayPoints(bps) + "Departures:" + displayPoints(dps) + "fare:" + s + " Rating:"+rating + "isAc:"+isAc+" isNAc:"+isNAc + " isSlpr:"+isSlpr);
    }

}
