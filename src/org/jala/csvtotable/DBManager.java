/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jala.csvtotable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author adrian
 */
public class DBManager {

    private static final String JDBC_TEMPLATE = "jdbc:derby://localhost/%s";
    private String dbName;
    private Connection connection;
    private Statement batchStatement;

    DBManager(String url, Connection con) {
        dbName = url;
        connection = con;
    }

    public DBManager(String url) throws SQLException {
        this.dbName = url;
    }

    public int updateSingle(String dmlQuery) throws SQLException {
        Statement statement = getConnection().createStatement();
        return statement.executeUpdate(dmlQuery);
    }
    
    

    public void initBatchUpdate() throws SQLException {
        getConnection().setAutoCommit(false);
        batchStatement = getConnection().createStatement();
        batchStatement.clearWarnings();
    }

    public void executeBatchUpdate() throws SQLException {
        batchStatement.executeBatch();
        getConnection().commit();
        batchStatement.close();
        batchStatement = null;
        getConnection().setAutoCommit(true);
    }

    public void cancelBatchUpdate() throws SQLException {
        getConnection().rollback();
        getConnection().setAutoCommit(true);
    }

    public void addUpdateStatementToBatch(String updateDml) throws SQLException {
        if (batchStatement == null) {
            throw new IllegalStateException("You need to call initBatchUpdate method before to add batch statements");
        }
        if (updateDml == null || updateDml.isEmpty()) {
            throw new IllegalArgumentException("UpdateDml cannot be null or empty");
        }
        batchStatement.addBatch(updateDml);
    }

    private Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = init();
        }
        return connection;
    }
    
    private Connection init() throws SQLException {
        String jdbc = String.format(JDBC_TEMPLATE, dbName);
        return DriverManager.getConnection(jdbc);
    }

    public String buildQuery(String[] row, String tableName) {
        String dml = String.format("INSERT INTO %s VALUES(", tableName);
        StringBuilder fieldQuery = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            if (i == 0) {
                fieldQuery.append(row[i]);
                fieldQuery.append(",");
            } else {
                fieldQuery.append("'");
                fieldQuery.append(row[i]);
                fieldQuery.append("',");
            }
        }
        fieldQuery.deleteCharAt(fieldQuery.length() - 1);
        fieldQuery.append(")");
        return dml.concat(fieldQuery.toString());
    }

}
