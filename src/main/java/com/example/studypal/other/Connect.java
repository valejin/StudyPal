package com.example.studypal.other;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


//classe implementa pattern singleton per istanziare connessione con database

public class Connect {

    private String jdbc;
    private String user;
    private String password;
    private static Connect instance = null;
    private Connection conn;
    private static final String PATH = "src/main/resources/com/example/studypal/connection.properties";

    private Connect() {}

    /** Singleton */
    public static synchronized Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    public synchronized Connection getDBConnection() {
        if (this.conn == null) {
            getInfo();
            System.out.println("connesione creata 1");

            try{
                this.conn = DriverManager.getConnection(jdbc, user, password);
            } catch (SQLException e){
                Printer.errorPrint(String.format("Error in Connect.java %s", e.getMessage()));
            }

        }
        System.out.println("connesione creata 2");
        return this.conn;
    }


    private void getInfo() {
        try(FileInputStream fileInputStream = new FileInputStream(PATH)) {

            // carico dal file properties le informazioni necessarie alla connessione
            Properties prop = new Properties() ;
            prop.load(fileInputStream);

            jdbc = prop.getProperty("JDBC_URL") ;
            user = prop.getProperty("USER") ;
            password = prop.getProperty("PASSWORD") ;

        } catch (IOException e){
            Printer.errorPrint(e.getMessage());
        }
    }

}

