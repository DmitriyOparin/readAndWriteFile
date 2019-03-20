package ru.fintech.homework;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    private String separator = File.separator;
    private String[] namesFileTxt = {"loginDB.txt", "passDB.txt", "nameDB.txt"};
    private String login;
    private String password;
    private String nameDB;
    private List<List<String>> allDataFile = new ArrayList<>();
    private String url = "jdbc:mysql://localhost:3306";
    private String serverTimezone = "?useUnicode=true&serverTimezone=UTC";
    private String urlDB;

    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String commonPath = "src" + separator + "main" + separator + "resources" + separator;
        ReadFileTxt file = new ReadFileTxt();
        for (String nameFile : namesFileTxt) {
            allDataFile.add(file.readFileTxt(commonPath + nameFile));
        }
        this.login = allDataFile.get(0).get(0);
        this.password = allDataFile.get(1).get(0);
        this.nameDB = allDataFile.get(2).get(0);
        this.urlDB = url + "/" + nameDB + serverTimezone;
    }

    public void createDB() {
        Statement stat = null;
        Connection connServer = null;
        try {
            connServer = DriverManager.getConnection(url + serverTimezone, login, password);
            stat = connServer.createStatement();
            String query = "CREATE DATABASE " + nameDB;
            stat.executeUpdate(query);
            System.out.println("База данных успешно создана");
        } catch (SQLException e) {
            System.out.println("База данных была создана до нас");
        } finally {
            try {
                stat.close();
                connServer.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTables() {
        String createTableAdress = "CREATE TABLE IF NOT EXISTS address" +
                "(id INT NOT NULL AUTO_INCREMENT, " +
                "postcode VARCHAR(256), " +
                "country VARCHAR(256), " +
                "region VARCHAR(256), " +
                "city VARCHAR(256), " +
                "street VARCHAR(256), " +
                "house INT, " +
                "flat INT, " +
                "PRIMARY KEY (id))";


        String createTablePersons = "CREATE TABLE IF NOT EXISTS persons" +
                "(id INT NOT NULL AUTO_INCREMENT, " +
                "surname VARCHAR(256), " +
                "name VARCHAR(256), " +
                "middlename VARCHAR(256), " +
                "birthday DATE, " +
                "gender VARCHAR(1), " +
                "address_id INT NOT NULL, " +
                "FOREIGN KEY (address_id) REFERENCES address(id), " +
                "PRIMARY KEY (id))";

        Statement stat = null;
        Connection connDB = null;
        try {
            connDB = DriverManager.getConnection(urlDB, login, password);
            stat = connDB.createStatement();
            stat.executeUpdate(createTableAdress);
            stat.executeUpdate(createTablePersons);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stat.close();
                connDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean controlEmptyTables() {
        boolean flag = false;
        Connection connDB = null;

        String dataInAdress = "SELECT * FROM address";
        String dataInPersons = "SELECT * FROM persons";

        Statement statDataInAddress = null;
        Statement statDataInPersons = null;
        try {
            connDB = DriverManager.getConnection(urlDB, login, password);
            statDataInAddress = connDB.createStatement();
            statDataInPersons = connDB.createStatement();
            ResultSet resDataInAddress = statDataInAddress.executeQuery(dataInAdress);
            ResultSet resDataInPersons = statDataInPersons.executeQuery(dataInPersons);
            if (!resDataInAddress.next() || !resDataInPersons.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statDataInAddress.close();
                statDataInPersons.close();
                connDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public void addOrUpdateDataPersonInDB(List<Person> dataPeoples) {
        for (Person dataPeople : dataPeoples) {
            List<Integer> idPersonInTables = controlPresencePersonInTables(dataPeople);
            if (idPersonInTables.size() == 0) {
                addDataPersonInDB(dataPeople);
            } else {
                updateDataPersonInDB(dataPeople, idPersonInTables);
            }
        }
    }

    private List<Integer> controlPresencePersonInTables(Person person) {
        List<Integer> idPersonInTables = new ArrayList<Integer>();
        try (Connection connDB = DriverManager.getConnection(urlDB, login, password);
             PreparedStatement prestat = connDB.prepareStatement(
                     "SELECT id, address_id FROM persons WHERE surname = ? AND name = ? AND middlename = ?")) {
            prestat.setString(1, person.getSurname());
            prestat.setString(2, person.getName());
            prestat.setString(3, person.getPatronymic());
            try (ResultSet res = prestat.executeQuery()) {
                while (res.next()) {
                    idPersonInTables.set(0, res.getInt("id"));
                    idPersonInTables.set(1, res.getInt("address_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPersonInTables;
    }

    private void addDataPersonInDB(Person person) {
        try (Connection connDB = DriverManager.getConnection(urlDB, login, password);
             PreparedStatement prestat = connDB.prepareStatement(
                     "INSERT INTO address VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)")) {
            prestat.setString(1, person.getIndex());
            prestat.setString(2, person.getCountry());
            prestat.setString(3, person.getRegion());
            prestat.setString(4, person.getCity());
            prestat.setString(5, person.getStreet());
            prestat.setInt(6, person.getHouse());
            prestat.setInt(7, person.getApartment());
            prestat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int addressIdInDB = getAddressIdInDB(person);
        try (Connection connDB = DriverManager.getConnection(urlDB, login, password);
             PreparedStatement prestat = connDB.prepareStatement(
                     "INSERT INTO persons VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)")) {
            prestat.setString(1, person.getSurname());
            prestat.setString(2, person.getName());
            prestat.setString(3, person.getPatronymic());
            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
            Date dateNew = dt.parse(person.getDateBirth());
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            prestat.setString(4, dt1.format(dateNew));
            prestat.setString(5, person.getSex());
            prestat.setInt(6, addressIdInDB);
            prestat.execute();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    private int getAddressIdInDB(Person person) {
        int idPersonInTables = 0;
        try (Connection connDB = DriverManager.getConnection(urlDB, login, password);
             PreparedStatement prestat = connDB.prepareStatement(
                     "SELECT id FROM address WHERE postcode = ? AND country = ? AND REGION = ? AND city = ? AND street = ? AND house = ? AND flat = ?")) {
            prestat.setString(1, person.getIndex());
            prestat.setString(2, person.getCountry());
            prestat.setString(3, person.getRegion());
            prestat.setString(4, person.getCity());
            prestat.setString(5, person.getStreet());
            prestat.setInt(6, person.getHouse());
            prestat.setInt(7, person.getApartment());
            try (ResultSet res = prestat.executeQuery()) {
                while (res.next()) {
                    idPersonInTables = res.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idPersonInTables;
    }

    private void updateDataPersonInDB(Person person, List<Integer> idPersonInTables) {
        try (Connection connDB = DriverManager.getConnection(urlDB, login, password);
             PreparedStatement prestat = connDB.prepareStatement(
                     "UPDATE persons SET surname = ?, name = ?, middlename = ?, birthday = ?, gender = ? WHERE id = ?")) {
            prestat.setString(1, person.getSurname());
            prestat.setString(2, person.getName());
            prestat.setString(3, person.getPatronymic());
            SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
            Date dateNew = dt.parse(person.getDateBirth());
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            prestat.setString(4, dt1.format(dateNew));
            prestat.setString(5, person.getSex());
            prestat.setInt(6, idPersonInTables.get(0));
            prestat.execute();
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        try (Connection connDB = DriverManager.getConnection(urlDB, login, password);
             PreparedStatement prestat = connDB.prepareStatement(
                     "UPDATE address SET postcode = ?, country = ?, region = ?, city = ?, street = ?, house = ?, flat = ? WHERE id = ?")) {
            prestat.setString(1, person.getIndex());
            prestat.setString(2, person.getCountry());
            prestat.setString(3, person.getRegion());
            prestat.setString(4, person.getCity());
            prestat.setString(5, person.getStreet());
            prestat.setInt(6, person.getHouse());
            prestat.setInt(7, person.getApartment());
            prestat.setInt(8, idPersonInTables.get(1));
            prestat.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getDataPersonInDB() {
        List<Person> dataPeoples = new ArrayList<Person>();
        try (Connection connDB = DriverManager.getConnection(urlDB, login, password);
             ResultSet resPerson = connDB.createStatement().executeQuery(
                     "SELECT * FROM persons")) {
            while (resPerson.next()) {
                Person person = new Person();
                person.setSurname(resPerson.getString("surname"));
                person.setName(resPerson.getString("name"));
                person.setPatronymic(resPerson.getString("middlename"));
                Date date = resPerson.getDate("birthday");
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String dateNew = df.format(date);
                person.setDateBirth(dateNew);
                person.setAge();
                person.setInn();
                person.setSex(resPerson.getString("gender"));
                int addressId = resPerson.getInt("address_id");
                try (PreparedStatement prestat = connDB.prepareStatement(
                        "SELECT * FROM address WHERE id = ?")) {
                    prestat.setInt(1, addressId);
                    try (ResultSet resAddress = prestat.executeQuery()) {
                        while (resAddress.next()) {
                            person.setIndex(resAddress.getString("postcode"));
                            person.setCountry(resAddress.getString("country"));
                            person.setRegion(resAddress.getString("region"));
                            person.setCity(resAddress.getString("city"));
                            person.setStreet(resAddress.getString("street"));
                            person.setHouse(resAddress.getInt("house"));
                            person.setApartment(resAddress.getInt("flat"));
                        }
                    }
                }
                dataPeoples.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataPeoples;
    }
}