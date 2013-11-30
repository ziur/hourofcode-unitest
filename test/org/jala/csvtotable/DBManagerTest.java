/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jala.csvtotable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author adrian
 */
public class DBManagerTest {
    @Mock(name="connection")
    private Connection mockedConnection;
    private Statement mockedStatement;
    
    @InjectMocks
    private DBManager instance;
    
    @Before
    public void setUp() {
        try {
            MockitoAnnotations.initMocks(this);
            mockedStatement = mock(Statement.class);
            when(mockedConnection.createStatement()).thenReturn(mockedStatement);
        } catch (SQLException ex) {
            Logger.getLogger(DBManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of updateSingle method, of class DBManager.
     */
    @Test
    public void testUpdateSingle() throws Exception {
        String dmlQuery = "INSERT INTO test VALUES (10, 'Juan','Perez','12345')";
        when(mockedStatement.executeUpdate(any(String.class))).thenReturn(1);
        
        int expResult = 1;
        int result = instance.updateSingle(dmlQuery);
        assertEquals(expResult, result);
        verify(mockedStatement).executeUpdate(eq(dmlQuery));
        
    }

    /**
     * Test of initBatchUpdate method, of class DBManager.
     */
    @Test
    public void testInitBatchUpdate() throws Exception {
        System.out.println("initBatchUpdate");
        DBManager instance = null;
        instance.initBatchUpdate();
        fail("The test case is a prototype.");
    }

    /**
     * Test of executeBatchUpdate method, of class DBManager.
     */
    @Test
    public void testExecuteBatchUpdate() throws Exception {
        System.out.println("executeBatchUpdate");
        DBManager instance = null;
        instance.executeBatchUpdate();
        fail("The test case is a prototype.");
    }

    /**
     * Test of cancelBatchUpdate method, of class DBManager.
     */
    @Test
    public void testCancelBatchUpdate() throws Exception {
        System.out.println("cancelBatchUpdate");
        DBManager instance = null;
        instance.cancelBatchUpdate();
        fail("The test case is a prototype.");
    }

    /**
     * Test of addUpdateStatementToBatch method, of class DBManager.
     */
    @Test
    public void testAddUpdateStatementToBatch() throws Exception {
        System.out.println("addUpdateStatementToBatch");
        String updateDml = "";
        DBManager instance = null;
        instance.addUpdateStatementToBatch(updateDml);
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildQuery method, of class DBManager.
     */
    @Test
    public void testBuildQuery() {
        System.out.println("buildQuery");
        String[] row = null;
        String tableName = "";
        DBManager instance = null;
        String expResult = "";
        String result = instance.buildQuery(row, tableName);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
