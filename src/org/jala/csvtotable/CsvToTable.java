package org.jala.csvtotable;

import com.mysql.jdbc.Field;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ale
 * Date: 11/19/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class CsvToTable {

    private String csvFile;
    private String tableName;
    public List<Map<String, Object>> loadCsvFile(){
        File file = new File(csvFile);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            String [] headers = line.split(",");
            String dml = "INSERT INTO " + tableName + " VALUES(";
            Field[] dbFields

            while ((line = bufferedReader.readLine()) != null) {
                String [] row = line.split(",");
                StringBuilder fieldQuery = new StringBuilder();
                for (int i = 0; i < headers.length; i++) {
                    String fieldName = headers[i];
                    String field = row[i];
                    com.mysql.jdbc.Field f;
                    f.
                }


            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    public static void main(String[] args) {



    }
}
