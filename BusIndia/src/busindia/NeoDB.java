/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package busindia;

import busindia.Busindia.Station;
import java.util.List;
import java.util.Map;
import org.neo4j.graphdb.Transaction;

/**
 *
 * @author aul
 */
public class NeoDB extends NeoDbBaseClass {

    public NeoDB(String db_path, String pathToConfig) {
        super(db_path, pathToConfig);
        initialise_constraint();
    }
    
    private void initialise_constraint(){
        
    }
    
     public void initialise_index() {

        try (Transaction tx = db.beginTx()) {
            //engine.execute("CREATE INDEX ON :City(id)");
           
        }
    }
    
      public void create_station(List<Station> stations){
        String query="";
        String name="cityNode";
        for (int i = 0; i < stations.size(); i++){
            query+=create_station(stations.get(i),name+i);
        }
        try (Transaction tx = db.beginTx()) {
          //  System.out.println("Executing query");
    //        System.out.println(query);
            engine.execute(query);
       //     System.out.println(query);
            tx.success();
         //   System.out.println(query);
           // System.out.printlnsIn"Query Executed");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
     public String create_station(Station st,String name){
         String query=" MERGE (" + name +":City{id:"+ add_colon(st.cityId) + " })" + " on CREATE set "+name+".name="+add_colon(st.cityName.toLowerCase())+"  ";
         return query;
     }
     
        private String match_station_query(String origin, String origin_id, String dest, String dest_id, final String Oname, final String Dname) {
        String query = null;
        query = "  MATCH (" + Oname + ":City),(" + Dname + ":City) WHERE origin.id=" + add_colon(origin_id) + " AND dest.id=" + add_colon(dest_id) + " WITH (origin),(dest)";
        return query;
    }
        
        private String create_has_bus_query(Map<String, Object> params, final String origin, final String dest) {
        String props = get_props(params);
        String query = "  CREATE " + add_bracket(origin) + "-[:HAS_BUS{" + props + "}]->" + add_bracket(dest);
        return query;
    }
        
            private static String get_props(Map<String, Object> params) {
        String prop = "";int flag=0;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            flag++;
            
            if(flag==1)
                prop=getKeyValue(entry.getKey(),entry.getValue());
            else
                prop = prop + "," + getKeyValue(entry.getKey(),entry.getValue());              //doubt;
        }
        return prop;
    }
    
    private static String getKeyValue(String key,Object value){
        if (value instanceof Float){
            return key + ":" + (Float) value;
        }
        else if(value instanceof Integer ){
            return key + ":" + (Integer) value;
        }
        else if(value instanceof Boolean){
            return key + ":" + (Boolean) value;
        }
        else return key + ":" +  add_colon((String)value);
            
    }
     
      public void update_graph_database(List<BusInfo> buses, String origin, String origin_id, String dest, String dest_id) {
                  final String originName = "origin";
        final String destName = "dest";
        final String space = " ";
        final String bpName = "bp";
        final String dpName = "dp";
        
        if(buses==null || buses.size()==0)
            return;
        
        for(BusInfo bus : buses){
            
        
        int count = 100;
            count++;
            String query = "";
            
            query += match_station_query(origin, origin_id, dest, dest_id, originName, destName) + space;
            query += create_has_bus_query(bus.get_map(), originName, destName) + space;
            
                           try (Transaction tx = db.beginTx()) {
               //                System.out.println(query);
                    engine.execute(query);
                 //   System.out.println("QUERY FOR BUS WITH RTID:"+bus.rtid);
                  //  System.out.println(query);
                    tx.success();
                }catch(Exception ex){
                    ex.printStackTrace();
        }
            }
        }
            
      }
    
      

