/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package busindia;

/**
 *
 * @author aul
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Map;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class NeoDbBaseClass {

    public static GraphDatabaseService db;
    public ExecutionEngine engine;

    public NeoDbBaseClass(String db_path,String pathToConfig) {
        db = get_graphdb(db_path,pathToConfig);
        execution_engine();
    }

    public static GraphDatabaseService get_graphdb(String db_path,String pathToConfig) {
        if (db == null) {
            if(pathToConfig==null)
                db = new GraphDatabaseFactory().newEmbeddedDatabase(db_path);
            else db = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder( db_path ).loadPropertiesFromFile( pathToConfig + "neo4j.properties" ).newGraphDatabase();
            registerShutdownHook(db);
        }
        return db;
    }

    public static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }
    /**
     * A typical example for parseRow is:
     *  public String parseRow(Entry<String,Object> row){
     *      for ( Entry<String, Object> column : row.entrySet() ) { 
     *          return column.getKey() + ": " + column.getValue() + "; "; 
     *      }
     */
    public interface RowIterator{
        public void parseRow(Map<String, Object> row);
    }

    public ExecutionEngine execution_engine() {
        if (engine == null) {
            engine = new ExecutionEngine(db);
        }
        return engine;
    }
    
   
    /**
     * Executes a query.Suppose the query is "MATCH (n:Person) return n.name,n.age,n.sex,n.email".And suppose the result after querying is:
     * 
     * foo 21 male foo@gmail.com
     * bar 21 female bar@gmail.com
     * 
     *For row1 Map<String, Object> row will have value:
     *n.name->foo
     *n.age->21
     *n.sex->male
     *n.email->foo@gmail.com
     * 
     * n.name can be accessed as column.getKey()
     * foo can be accessed through  column.getValue
     * 
     * If the data is large
     * @param query         
     * @param callback : Function to performed on each row.
     * @return 
     */
    public void execute_as_row_iterator(String query,RowIterator callback) {
        ExecutionResult result = null;
        try (Transaction tx = db.beginTx()) {
            result = execution_engine().execute(query);

            if (result != null) {
                for (Map<String, Object> row : result) {

                    //operation to be performed on each column of a row.
                     callback.parseRow(row);

                }
            }
            tx.success();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
       public static String add_colon(String s) {
        return "'" + s + "'";
    }

    public static String add_bracket(String s) {
        return "(" + s + ")";
    }
    

     
}
