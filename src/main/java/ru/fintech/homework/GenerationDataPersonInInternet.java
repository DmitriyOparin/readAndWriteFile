package ru.fintech.homework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationDataPersonInInternet {
    private static Random random = new Random();
    private String separator = File.separator;
    private List<List<String>> allDataFile = new ArrayList<>();
    private String[] namesFileTxt = {"country.txt"};


    public void readFile() {
        String commonPath = "src" + separator + "main" + separator + "resources" + separator;
        ReadFileTxt file = new ReadFileTxt();
        for (String nameFile : this.namesFileTxt) {
            this.allDataFile.add(file.readFileTxt(commonPath + nameFile));
        }
    }

    public void generation(String strJSON, List<Person> dataPeoples) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(strJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < dataPeoples.size(); i++) {
            String sex = root.at("/" + i + "/user/gender").asText();
            if (!sex.equals("female")) {
                dataPeoples.get(i).setSex("м");
            } else {
                dataPeoples.get(i).setSex("ж");
            }
            String name = root.at("/" + i + "/user/name/first").asText();
            dataPeoples.get(i).setName(name);
            String surname = root.at("/" + i + "/user/name/last").asText();
            dataPeoples.get(i).setSurname(surname);
            String patronymic = root.at("/" + i + "/user/name/middle").asText();
            dataPeoples.get(i).setPatronymic(patronymic);
            long dateBirth = root.at("/" + i + "/user/dob").asLong();
            dataPeoples.get(i).setDateBirth(dateBirth);
            dataPeoples.get(i).setAge();
            dataPeoples.get(i).setInn();
            dataPeoples.get(i).setIndex();
            dataPeoples.get(i).setCountry(allDataFile.get(0).get(random.nextInt(allDataFile.get(0).size())));
            String region = root.at("/" + i + "/user/location/state").asText();
            dataPeoples.get(i).setRegion(region);
            String city = root.at("/" + i + "/user/location/city").asText();
            dataPeoples.get(i).setCity(city);
            String street = root.at("/" + i + "/user/location/street").asText();
            dataPeoples.get(i).setStreet(street);
            int house = root.at("/" + i + "/user/location/building").asInt();
            dataPeoples.get(i).setHouse(house);
            dataPeoples.get(i).setApartment(random.nextInt(300));
        }
    }
}
