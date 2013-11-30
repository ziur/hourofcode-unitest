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
    private String user;
    private String pass;
    private Connection connection;
    private Statement batchStatement;

    public DBManager(String url) throws SQLException {
        this(url, null, null);
    }
    
    public DBManager(String url, String user, String pass) throws SQLException {
        this.dbName = url;
        this.user = user;
        this.pass = pass;
        connection = init();
    }
    
    public int updateSingle(String dmlQuery) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(dmlQuery);
    }
    
    public void initBatchUpdate() throws SQLException {
        connection.setAutoCommit(false);
        batchStatement = connection.createStatement();
        batchStatement.clearWarnings();
    }
    
    public void executeBatchUpdate() throws SQLException {
        batchStatement.executeBatch();
        connection.commit();
        batchStatement.close();
        batchStatement = null;
        connection.setAutoCommit(true);
    }
    
    public void cancelBatchUpdate() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
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
    
    private Connection init() throws SQLException {
        String jdbc = String.format(JDBC_TEMPLATE, dbName);
        if (user != null && pass != null) {
            return DriverManager.getConnection(jdbc, user, pass);
        } else {
            return DriverManager.getConnection(jdbc);
        }
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
