/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aul
 */
public class NodesAndRelations {

    public class City {

        String id;
        String name;

        public City(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Map<String, Object> get_map() {
            if (id == null || name == null) {
                return null;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            params.put("name", name);
            return params;
        }
    }

    public class Points {

        String id;
        String name;

        public Points(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Map<String, Object> get_map() {
            if (id == null || name == null) {
                return null;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("name", name);
            return params;
        }
    }

    public class Has_Bus {

        String rtid;
        Integer journey_dur;
        Integer dept_time;
        Integer arr_time;
        Integer first_bp_time;
        Integer first_dp_time;
        String first_bp_id;
        String first_dp_id;
        float rating;
        boolean isAc,isNAc,isSlpr;
        List<Integer> fare;

        public Has_Bus(String rtid, Integer journey_dur, Integer dept_time, Integer arr_time, Integer first_bp_time, Integer first_dp_time, 
                String first_bp_id, String first_dp_id, float rating, boolean isAc, boolean isNAc, boolean isSlpr, List<Integer> fare) {
            this.rtid = rtid;
            this.journey_dur = journey_dur;
            this.dept_time = dept_time;
            this.arr_time = arr_time;
            this.first_bp_time = first_bp_time;
            this.first_dp_time = first_dp_time;
            this.first_bp_id = first_bp_id;
            this.first_dp_id = first_dp_id;
            this.rating = rating;
            this.isAc = isAc;
            this.isNAc = isNAc;
            this.isSlpr = isSlpr;
            this.fare = fare;
        }
        Float k;
        public Map<String, Object> get_map() {
            if (rtid == null || first_bp_time == null || first_dp_time == null || first_bp_id == null || first_dp_id==null ) {
                return null;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("rtid", rtid);
            params.put("duration", journey_dur);
            params.put("dept_time", dept_time);
            params.put("arr_time", arr_time);
            params.put("first_bp_time", first_bp_time);
            params.put("first_bp_id", first_bp_id);
            params.put("first_dp_time", first_dp_time);
            params.put("first_dp_id", first_dp_id);
            for(int i=0;i<fare.size();i++){
                params.put("fare"+i, fare.get(i));
            }
            params.put("rating",(Float)rating);
            params.put("isAc",isAc);
            params.put("isNAc",isNAc);
            params.put("isSlpr", isSlpr);
            return params;

        }

    }

    public class To {

        Integer start_time;
        Integer end_time;
        String rtid;

        public To(Integer start_time, Integer end_time, String rtid) {
            this.start_time = start_time;
            this.end_time = end_time;
            this.rtid = rtid;
        }

        

        public Map<String, Object> get_map() {
            if (start_time == null || end_time == null || rtid == null) {
                return null;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("start_time", start_time);
            params.put("end_time", end_time);
            params.put("rtid", rtid);
            return params;

        }

    }
}
