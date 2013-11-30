/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jala.csvtotable;

/**
 *
 * @author adrian
 */
public class CSVParserException extends Exception{
    
    private static final String TEMPLATE = "Fail to parse file %s, %s";
    
    private final String file;

    public CSVParserException(String file, Exception error) {
        super(String.format(TEMPLATE, file, error.getMessage()), error);
        this.file = file;
        
    }

    public String getFile() {
        return file;
    }
    
}
