package com.example.studypal.other;

import com.example.studypal.dao.UserDAO;
import com.example.studypal.dao.UserDAOJSON;
import com.example.studypal.dao.UserDAOMySQL;
import com.example.studypal.exceptions.PersistenzaNonValida;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FactoryDAO {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    private FactoryDAO(){}

    static {
        try (InputStream input = FactoryDAO.class.getClassLoader().getResourceAsStream("/resources/com.example.studypal/config.properties")) {
            if (input == null) {
                Printer.errorPrint("Impossibile trovare " + CONFIG_FILE);
            }
            properties.load(input);
        } catch (IOException ex) {
            Printer.errorPrint("Impossibile aprire il file di configurazione.");
        }
    }

    public static UserDAO getUserDAO() throws PersistenzaNonValida {
        String daoType = properties.getProperty("persistence.type");
        if ("MySQL".equalsIgnoreCase(daoType)) {
            return new UserDAOMySQL();
        } else if ("file".equalsIgnoreCase(daoType)) {
            return new UserDAOJSON();
        } else {
            throw new PersistenzaNonValida();
        }
    }
}
