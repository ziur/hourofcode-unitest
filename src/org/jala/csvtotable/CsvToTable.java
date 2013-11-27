package org.jala.csvtotable;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: ale Date: 11/19/13 Time: 11:00 PM To change
 * this template use File | Settings | File Templates.
 */
public class CsvToTable {

    private String csvFile;
    private String tableName;
    private final DBManager dbHelper;
    

    public CsvToTable(String csvFile, String tableName) throws SQLException {
        this.csvFile = csvFile;
        this.tableName = tableName;
        dbHelper = new DBManager("unittest");
    }

    public List<Map<String, Object>> loadCsvFile() {
        File file = new File(csvFile);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            String[] headers = line.split(",");
            String dml = "INSERT INTO " + tableName + " VALUES(";

            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                StringBuilder fieldQuery = new StringBuilder();
                for (int i = 0; i < headers.length; i++) {
                    if (i == 0 ) {
                        fieldQuery.append(row[i]);
                        fieldQuery.append(",");
                    } else {
                        fieldQuery.append("\'");
                        fieldQuery.append(row[i]);
                        fieldQuery.append("\',");
                    }

                }
                fieldQuery.deleteCharAt(fieldQuery.length() - 1);
                fieldQuery.append(")");
               
                int result = dbHelper.updateSingle(dml);
                System.out.println("updates " + result);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;

    }

    public static void main(String[] args) {

    }
}
