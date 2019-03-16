package ru.fintech.homework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationDataPersonInFile {
    private static Random random = new Random();
    private String separator = File.separator;
    private List<List<String>> allDataFile = new ArrayList<>();
    private String[] namesFileTxt = {"country.txt", "region.txt", "city.txt", "street.txt", "surname.txt",
            "female_name.txt", "female_patronymic.txt", "male_name.txt", "male_patronymic.txt"};


    public void readFile() {
        String commonPath = "src" + separator + "main" + separator + "resources" + separator;
        ReadFileTxt file = new ReadFileTxt();
        for (String nameFile : namesFileTxt) {
            allDataFile.add(file.readFileTxt(commonPath + nameFile));
        }
    }

    public void generation(List<Person> dataPeoples) {
        String surnamePeople;
        int sexFemale;
        String consonantsRussian = "йцкнгшщзхфвпрлджбтмсч";

        for (Person dataPeople : dataPeoples) {
            surnamePeople = allDataFile.get(4).get(random.nextInt(allDataFile.get(4).size()));
            sexFemale = consonantsRussian.indexOf(surnamePeople.charAt(surnamePeople.length() - 1));
            dataPeople.setSurname(surnamePeople);
            if (sexFemale != -1) {
                dataPeople.setName(allDataFile.get(7).get(random.nextInt(allDataFile.get(7).size())));
                dataPeople.setPatronymic(allDataFile.get(8).get(random.nextInt(allDataFile.get(8).size())));
                dataPeople.setSex("м");
            } else {
                dataPeople.setName(allDataFile.get(5).get(random.nextInt(allDataFile.get(5).size())));
                dataPeople.setPatronymic(allDataFile.get(6).get(random.nextInt(allDataFile.get(6).size())));
                dataPeople.setSex("ж");
            }
            dataPeople.setDateBirth();
            dataPeople.setAge();
            dataPeople.setInn();
            dataPeople.setIndex();
            dataPeople.setCountry(allDataFile.get(0).get(random.nextInt(allDataFile.get(0).size())));
            dataPeople.setRegion(allDataFile.get(1).get(random.nextInt(allDataFile.get(1).size())));
            dataPeople.setCity(allDataFile.get(2).get(random.nextInt(allDataFile.get(2).size())));
            dataPeople.setStreet(allDataFile.get(3).get(random.nextInt(allDataFile.get(3).size())));
            dataPeople.setHouse(random.nextInt(300));
            dataPeople.setApartment(random.nextInt(300));
        }
    }
}
