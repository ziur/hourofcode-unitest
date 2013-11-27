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
    
    private Connection init() throws SQLException {
        String jdbc = String.format(JDBC_TEMPLATE, dbName);
        if (user != null && pass != null) {
            return DriverManager.getConnection(jdbc, user, pass);
        } else {
            return DriverManager.getConnection(jdbc);
        }
    }
    
}
