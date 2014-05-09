/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;

import buses.Buses.Station;
import buses.Buses.StationList;
import buses.NodesAndRelations.City;
import buses.NodesAndRelations.Has_Bus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author aul
 */
public class NeoDb {

    private static GraphDatabaseService db;
    private static String DB_PATH;
    private ExecutionEngine engine;

    public NeoDb(String DbPath) {
        this.DB_PATH = DbPath;
        initialise();
    }

    public void initialise() {
        get_graphdb();
        get_execution_engine();
        // initialis_constraints();  //Neo 4j don'e support that is both  constraint and is unique!!!!!!!!it sucks
         //  initialise_index();

    }

    private void initialis_constraints() {
        String constraint1 = " CREATE CONSTRAINT ON (n:Point) ASSERT n.id IS UNIQUE ";
        String constraint2 = " CREATE CONSTRAINT ON (n:City) ASSERT n.id IS UNIQUE ";
        engine.execute(constraint1);
        engine.execute(constraint2);
    }

    public static GraphDatabaseService get_graphdb() {
        if (db == null) {
            db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
            registerShutdownHook(db);
        }
        return db;
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
// Registers a shutdown hook for the Neo4j instance so that it
// shuts down nicely when the VM exits (even if you "Ctrl-C" the
// running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    public ExecutionEngine get_execution_engine() {
        if (engine == null) {
            engine = new ExecutionEngine(db);
        }
        return engine;
    }

    public void test() {
        String query1 = "  Match (origin:City)-[r1:HAS_BUS]->(dest:City) where origin.id='462' and dest.id='210' return r1.rtid as r1 ";
        try (Transaction tx = get_graphdb().beginTx()) {
            ExecutionResult result = engine.execute(query1);
            System.out.println("lol");
            ResourceIterator<String> n_column = result.columnAs("r1");
            System.out.println(n_column);
            while (n_column.hasNext()) {
                // Debugged in eclipse and found minval = 0 (obviously)

                String s= n_column.next();
                System.out.format("Bus Id:"+s);
            }

        }
    }

    public void create_node(String name, String id) {
        get_execution_engine();
        ExecutionResult result1, result2;
        Map<String, Object> props = new HashMap<>();
        props.put("name", name);
        props.put("id", id);
        Map<String, Object> params = new HashMap<>();
        params.put("props", props);
        try (Transaction ignored = get_graphdb().beginTx()) {
            String query = "CREATE (n:User {props}) RETURN n";
            engine.execute(query, params);
        }

    }

    public void initialise_index() {

        try (Transaction tx = get_graphdb().beginTx()) {
            engine.execute("CREATE INDEX ON :City(id)");
            engine.execute("CREATE INDEX ON :Point(id)");
            engine.execute("CREATE INDEX ON :City(name)");
            engine.execute("CREATE INDEX ON :Has_Bus(rtid)");
            engine.execute("CREATE INDEX ON :TO(rtid)");
            tx.success();
        }
    }

    public void add_stations(Buses.Station station) {
        NodesAndRelations creator = new NodesAndRelations();
        Map<String, Object> params = creator.new City(station.id, station.station_name).get_map();
        create_station_node(params);
    }

    public void create_stations(StationList stations) {
        ExecutionResult result;
        NodesAndRelations k = new NodesAndRelations();
        Map<String, Object> params, props;
        List<Map<String, Object>> maps = new ArrayList<>();

        for (int i = 0; i < stations.length; i++) {
            Station station = stations.stations[i];
            props = k.new City(station.id, station.station_name).get_map();
            maps.add(props);
        }
        try (Transaction tx = get_graphdb().beginTx()) {

            params = new HashMap<>();
            String query = "CREATE (n:City{props})";

            params.put("props", maps);
            result = engine.execute(query, params);
            tx.success();
        }catch(Exception ex){
        }

    }
    
    public void new_create_station(StationList stations){
        String query="";
        String name="cityNode";
        if(stations==null || stations.length==0) return;
        for (int i = 0; i < stations.length; i++){
            query+=create_station(stations.stations[i],name+i);
        }
        try (Transaction tx = get_graphdb().beginTx()) {
          //  System.out.println("Executing query");
            engine.execute(query);
       //     System.out.println(query);
            tx.success();
         //   System.out.println(query);
           // System.out.println("Query Executed");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
     public String create_station(Station st,String name){
         String query=" MERGE (" + name +":City{id:"+ add_colon(st.id) + " })" + " on CREATE set "+name+".name="+add_colon(st.station_name)+"  ";
         return query;
     }

    private Node create_station_node(Map<String, Object> props) {
        Node node = null;
        ResourceIterator<Node> resultIterator = null;

        ExecutionResult result;
        Map<String, Object> params = new HashMap<>();
        params.put("props", props);

        try (Transaction tx = get_graphdb().beginTx()) {
            String query = "CREATE (n:User {props} RETURN n)";
            resultIterator = engine.execute(query, params).columnAs("n");
            node = resultIterator.next();
            tx.success();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return node;

    }
    
    public void update_graph_database(List<RedBuses> buses, String origin, String origin_id, String dest, String dest_id) {
        final String originName = "origin";
        final String destName = "dest";
        final String space = " ";
        final String bpName = "bp";
        final String dpName = "dp";
        
        for (RedBuses bus : buses) {
            int count = 100;
            count++;
            String query = "";
            
            if (bus.bps.size() == 0 || bus.dps.size() == 0) {
                //avoid any transaction if bus is sold out..
            } 
            else {
                query += match_station_query(origin, origin_id, dest, dest_id, originName, destName) + space; //search for station
                query += get_points_and_TO_query(bus.bps, bus.rtid, bpName + count); //create boarding points and boarding relations
                query += get_points_and_TO_query(bus.dps, bus.rtid, dpName + count);

                Has_Bus obj = new NodesAndRelations().new Has_Bus(bus.rtid, bus.journey_dur, bus.dept_time, bus.arr_time,
                        bus.bps.get(0).time, bus.dps.get(0).time, bus.bps.get(0).id, bus.dps.get(0).id,bus.rating,bus.isAc,bus.isNAc,bus.isSlpr,bus.fare);
                query += create_has_bus_query(obj.get_map(), originName, destName) + space;

       //  new Buses().write_file("finalQuery" + "-" + bus.rtid + ".txt", query);
                try (Transaction tx = get_graphdb().beginTx()) {
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
    
    /*
     public void update_graph_database(List<RedBuses> buses, String origin, String origin_id, String dest, String dest_id) {
        final String originName = "origin";
        final String destName = "dest";
        final String space = " ";
        final String bpName = "bp";
        final String dpName = "dp";
        String query = "";
        int count = 100;
        query += match_station_query(origin, origin_id, dest, dest_id, originName, destName) + space; //search for station

        for (RedBuses bus : buses) {

            count++;

            query += get_points_and_TO_query(bus.bps, bus.rtid, bpName + count); //create boarding points and boarding relations
            query += get_points_and_TO_query(bus.dps, bus.rtid, dpName + count);
            Has_Bus obj = new NodesAndRelations().new Has_Bus(bus.rtid, bus.journey_dur, bus.dept_time, bus.arr_time, bus.bps.get(0).time, bus.dps.get(0).time, bus.bps.get(0).id, bus.dps.get(0).id);
            query += create_has_bus_query(obj.get_map(), originName, destName) + space;
        }

        new Buses().write_file("finalAllQuery.txt", query);
        try (Transaction tx = get_graphdb().beginTx()) {
            engine.execute(query);
            tx.success();

        }

    }*/

    private String get_points_and_TO_query(List<RedBuses.Points> bps, String rtid, String bpName) {
        String query = "";
        String space = " ";
        if (bps.size() == 1) {
            query += create_points_query(bps.get(0), name_id(bpName, 0)) + space;
        } else {
            for (int i = 0; i < bps.size(); i++) {
                query += create_points_query(bps.get(i), name_id(bpName, i)) + space;
            }
            for (int i = 0; i < bps.size() - 1; i++) {
                query += create_TO_query(name_id(bpName, i), name_id(bpName, i + 1), bps.get(i).time, bps.get(i + 1).time, rtid) + space;
            }
        }
        return query;
    }

    private String name_id(String name, int i) {
        return name + Integer.toString(i);
    }

    private String match_station_query(String origin, String origin_id, String dest, String dest_id, final String Oname, final String Dname) {
        String query = null;
        query = "  MATCH (" + Oname + ":City),(" + Dname + ":City) WHERE origin.id=" + add_colon(origin_id) + " AND dest.id=" + add_colon(dest_id) + " WITH (origin),(dest)";
        return query;
    }

    private String create_points_query(RedBuses.Points point, final String name) {
    //    String query = "MERGE (" + name + ":Point{id:" + add_colon(point.id) + ",name:" + add_colon(point.location) + "})";
          String query= "  MERGE (" + name + ":Point{id:"+add_colon(point.id)+"})"+" ON CREATE set "+name+".name="+(point.location); 
          return query;
    }

    private String create_has_bus_query(Map<String, Object> params, final String origin, final String dest) {
        String props = get_props(params);
        String query = "  CREATE " + add_bracket(origin) + "-[:HAS_BUS{" + props + "}]->" + add_bracket(dest);
        return query;
    }

    private String get_props(Map<String, Object> params) {
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
    
    private String getKeyValue(String key,Object value){
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

    /**
     * (pointA)-[:TO{start_time:'start-time',end_time:'end_time',rtid:'rtid'}]->(pointB)
     *
     * @param pointA
     * @param pointB
     * @param start_time
     * @param end_time
     * @param rtid
     * @return
     */
    private String create_TO_query(String pointA, String pointB, Integer start_time, Integer end_time, String rtid) {
        String query = "  CREATE " + add_bracket(pointA) + "-[:TO{start_time:" + start_time + ",end_time:" + end_time + ",rtid:" + add_colon(rtid) + "}]->" + add_bracket(pointB);
        return query;
    }

    private String add_colon(String s) {
        return "'" + s + "'";
    }

    private String add_bracket(String s) {
        return "(" + s + ")";
    }
    /*
     MERGE (name:Point{id:'id',name:'name')
     */

   
    public static void main(String[] args) {
        NeoDb db = new NeoDb("database/test");
        db.initialise_index();
        db.create_node("Goa", "23423");
        db.create_node("Mumbai", "34343");
        db.create_node("Mumbai", "pune");
    }

   

}
