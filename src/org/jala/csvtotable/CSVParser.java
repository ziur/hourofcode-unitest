/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jala.csvtotable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
class CSVParser implements Iterable<String[]>, Iterator<String[]>{

    public static final String COMMA_SEPARATOR = ",";
    private static final Logger LOGGER = Logger.getLogger(CSVParser.class.getName());

    private final String file;
    private final String columnSeparator = COMMA_SEPARATOR;
    private String[] columns;
    private String currentLine;
    private String nextLine;

    private BufferedReader reader;

    public CSVParser(String file) {
        this.file = file;
    }

    public void parse()throws CSVParserException {
        try {
        initReader(file);
        getHeaders();
        } catch (IOException ex) {
            throw new CSVParserException(file, ex);
        }
    }
    

    public String getFile() {
        return file;
    }

    public String[] getHeaders() throws IOException {
        if (columns == null) {
            String line = reader.readLine();
            columns = line.split(columnSeparator);
        }
        return columns;
    }

    @Override
    public Iterator<String[]> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (columns == null) {
            throw new IllegalStateException("You need to call getHeader first");
        }
        try {
            nextLine = reader.readLine();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            nextLine = null;
        }
        return nextLine != null;
    }

    @Override
    public String[] next() {
        if (columns == null) {
            throw new IllegalStateException("You need to call getHeader first");
        }
        currentLine = nextLine;
        return currentLine.split(columnSeparator);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void close() throws CSVParserException  {
        try {
            reader.close();
        } catch (IOException ex) {
            throw new CSVParserException(file, ex);
        }
    }
    
    private void initReader(String file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
    }

}
