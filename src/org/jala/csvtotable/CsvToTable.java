package org.jala.csvtotable;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA. User: ale Date: 11/19/13 Time: 11:00 PM To change
 * this template use File | Settings | File Templates.
 */
public class CsvToTable {
    private static final Logger logger = Logger.getLogger(CsvToTable.class.getName());

    
    private final String tableName;
    private final DBManager dbHelper;
    private final CSVParser parser;

    public CsvToTable(String csvFile, String tableName) throws SQLException, FileNotFoundException {
        this.tableName = tableName;
        dbHelper = new DBManager("unittest");
        parser = new CSVParser(csvFile);
    }

    public List<Map<String, Object>> loadCsvFile()  {
        
        try {
            String[] headers = parser.getHeaders();
            String dml = "INSERT INTO " + tableName + " VALUES(";
            
            dbHelper.initBatchUpdate();

            for (String[] row : parser) {
                String query = buildQuery(row);
                dbHelper.addUpdateStatementToBatch(query);
            }
            dbHelper.executeBatchUpdate();
            parser.close();
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, null, e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            try {
                dbHelper.cancelBatchUpdate();  //To change body of catch statement use File | Settings | File Templates.
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    private String buildQuery(String[] row) {
        String dml =String.format("INSERT INTO %s VALUES(", tableName);
        StringBuilder fieldQuery = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (i == 0) {
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
        return dml.concat(fieldQuery.toString());
    }
}
