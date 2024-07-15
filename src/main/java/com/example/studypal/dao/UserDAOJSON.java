package com.example.studypal.dao;

import com.example.studypal.exceptions.CredenzialiSbagliateException;
import com.example.studypal.exceptions.EmailAlreadyInUseException;
import com.example.studypal.exceptions.UtenteInesistenteException;
import com.example.studypal.model.CredenzialiModel;
import com.example.studypal.model.UserModel;
import com.example.studypal.other.Printer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDAOJSON implements UserDAO {

    private static final String FILE_PATH = "users.json";
    private final Map<String, UserModel> users = new HashMap<>();

    public UserDAOJSON() {
        loadFromFile();
    }

    @Override
    public UserModel loginMethod(CredenzialiModel credenzialiModel) throws CredenzialiSbagliateException, UtenteInesistenteException {
        UserModel user = users.get(credenzialiModel.getEmail());
        if (user == null) {
            throw new UtenteInesistenteException();
        }
        if (!user.getPassword().equals(credenzialiModel.getPassword())) {
            throw new CredenzialiSbagliateException("Credenziali sbagliate");
        }
        return user;
    }

    @Override
    public void registrazioneMethod(UserModel registrazioneModel) {
        users.put(registrazioneModel.getEmail(), registrazioneModel);
        saveToFile();
    }

    @Override
    public void controllaEmailMethod(UserModel registrazioneModel) throws EmailAlreadyInUseException {
        if (users.containsKey(registrazioneModel.getEmail())) {
            throw new EmailAlreadyInUseException();
        }
    }

    @Override
    public void registraTutorMethod(String email, String nome, String cognome) {
        UserModel tutor = users.get(email);
        if (tutor != null) {
            tutor.setNome(nome);
            tutor.setCognome(cognome);
            tutor.setRuolo(true);
            saveToFile();
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            for (Map.Entry<String, UserModel> entry : users.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue().toString() + "\n");
            }
        } catch (IOException e) {
            Printer.errorPrint("Failed to save users to JSON file");
        }
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    UserModel user = UserModel.fromString(parts[1]);
                    users.put(parts[0], user);
                }
            }
        } catch (IOException e) {
            Printer.errorPrint("Failed to load users from JSON file");
        }
    }
}
