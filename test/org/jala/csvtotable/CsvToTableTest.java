/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jala.csvtotable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adrian
 */
public class CsvToTableTest {
    

    /**
     * Test of loadCsvFile method, of class CsvToTable.
     */
    @Test
    public void testLoadCsvFile() throws Exception {
        String file = CsvToTable.class.getResource("resources/basic_person_data.csv").getFile();
        CsvToTable instance = new CsvToTable(file, "test");
        instance.loadCsvFile();
        
        String query = "SELECT * FROM test";
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/unittest");
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(query);
        int expectedId = 11;
        String expectedName = "Juan";
        String expectedLastName = "Perez";
        String expectedPhone = "123456";
        result.next();
        assertEquals(expectedId, result.getInt(1));
        assertEquals(expectedName, result.getString(2));
        assertEquals(expectedLastName, result.getString(3));
        assertEquals(expectedPhone, result.getString(4));
    }
    
     /**
     * Test of loadCsvFile method, of class CsvToTable.
     */
    @Test
    public void testLoadCsvFileSeveralPeople() throws Exception {
        System.out.println("loadCsvFile");
        String file = CsvToTable.class.getResource("resources/several_people_data.csv").getFile();
        CsvToTable instance = new CsvToTable(file, "test");
        instance.loadCsvFile();
        
        String query = "SELECT * FROM test";
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/unittest");
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(query);
        int expectedId = 11;
        String expectedName = "Juan";
        String expectedLastName = "Perez";
        String expectedPhone = "123456";
        result.next();
        assertEquals(expectedId, result.getInt(1));
        assertEquals(expectedName, result.getString(2));
        assertEquals(expectedLastName, result.getString(3));
        assertEquals(expectedPhone, result.getString(4));
        
        expectedId = 12;
        expectedName = "Hugo";
        expectedLastName = "Perez";
        expectedPhone = "123456";
        result.next();
        assertEquals(expectedId, result.getInt(1));
        assertEquals(expectedName, result.getString(2));
        assertEquals(expectedLastName, result.getString(3));
        assertEquals(expectedPhone, result.getString(4));
        
        expectedId = 13;
        expectedName = "Paco";
        expectedLastName = "Perez";
        expectedPhone = "123456";
        result.next();
        assertEquals(expectedId, result.getInt(1));
        assertEquals(expectedName, result.getString(2));
        assertEquals(expectedLastName, result.getString(3));
        assertEquals(expectedPhone, result.getString(4));
        
        expectedId = 14;
        expectedName = "Luis";
        expectedLastName = "Perez";
        expectedPhone = "123456";
        result.next();
        assertEquals(expectedId, result.getInt(1));
        assertEquals(expectedName, result.getString(2));
        assertEquals(expectedLastName, result.getString(3));
        assertEquals(expectedPhone, result.getString(4));
    }
    
}
