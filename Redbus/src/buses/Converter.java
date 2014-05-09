/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buses;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author aul
 */
public class Converter extends BaseClass {

    String QUERY = "Match (n1)-[r:HAS_BUS]->(n2) return n1.id,n1.name,n2.id,n2.name,r.rtid,r.dept_time,r.arr_time,r.duration,r.fare0,r.rating,r.isAc,r.isNAc,r.isSlpr";

    public Converter(String db_path, String pathToConfig) {
        super(db_path, pathToConfig);
    }

    /**
     * Implement callback as you wish the data to be and keep writing it to a file.
     *
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void to_csv() throws FileNotFoundException, UnsupportedEncodingException {
        final PrintWriter writer = new PrintWriter("buses2.csv", "UTF-8");

        BaseClass.RowIterator callback = new BaseClass.RowIterator() {
            public void parseRow(Map<String, Object> row) {
                String rows = "";
                for (Entry<String, Object> column : row.entrySet()) {
                    if(!(column.getValue()==null))
                        rows += column.getKey() + "=" + column.getValue() + "; ";

                }
                writer.append(rows);
                writer.append("\n");
            }
        };

        execute_as_row_iterator(QUERY, callback);
        writer.close();

    }
    public void to_csv1() throws FileNotFoundException, UnsupportedEncodingException {
        final PrintWriter writer = new PrintWriter("buses4.csv", "UTF-8");

        BaseClass.RowIterator callback = new BaseClass.RowIterator() {
            public void parseRow(Map<String, Object> row) {
                
                String comma=";";
                String rows = row.get("n1.id")+comma+row.get("n1.name")+comma+row.get("n2.id")+comma+row.get("n2.name")+comma+row.get("r.rtid")+comma
                        +row.get("r.dept_time")+comma+row.get("r.arr_time")+comma+row.get("r.duration")+comma+row.get("r.fare0")+comma+row.get("r.rating")+comma+row.get("r.isAc")+comma+
                        row.get("r.isNAc")+comma+row.get("r.isSlpr")+comma+comma+comma;
                
                        
                writer.append(rows);
                writer.append("\n");
            }
        };

        execute_as_row_iterator(QUERY, callback);
        writer.close();

    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        new Converter("database/final/redbus255", null).to_csv1();
    }

}
