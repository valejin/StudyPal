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

    private FactoryDAO() {}

    public static UserDAO getUserDAO() throws PersistenzaNonValida {
        try (InputStream input = FactoryDAO.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                Printer.errorPrint("Impossibile trovare " + CONFIG_FILE);
                throw new PersistenzaNonValida();
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            Printer.errorPrint("Impossibile aprire il file di configurazione.");
            throw new PersistenzaNonValida();
        }

        String daoType = properties.getProperty("persistence.type");
        if ("MYSQL".equalsIgnoreCase(daoType)) {
            return new UserDAOMySQL();
        } else if ("JSON".equalsIgnoreCase(daoType)) {
            return new UserDAOJSON();
        } else {
            throw new PersistenzaNonValida();
        }
    }
}
