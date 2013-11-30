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

    public List<Map<String, Object>> loadCsvFile() throws CSVParserException  {
        
        try {
            parser.parse();
            dbHelper.initBatchUpdate();

            for (String[] row : parser) {
                String query = dbHelper.buildQuery(row, tableName);
                dbHelper.addUpdateStatementToBatch(query);
            }
            dbHelper.executeBatchUpdate();
            parser.close();
        
        } catch (SQLException e) {
            try {
                dbHelper.cancelBatchUpdate();
                throw new CSVParserException(parser.getFile(), e);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
