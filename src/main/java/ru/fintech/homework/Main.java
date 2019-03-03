package ru.fintech.homework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static String separator = File.separator;
    private static String consonantsRussian = "йцкнгшщзхфвпрлджбтмсч";
    private static String url = "http://randomuser.ru/api.json";
    private static Random random = new Random();
    private static List<List<String>> allDataFile = new ArrayList<>();
    private static int generationCountPeople = 30;
    private static String surnamePeople;
    private static int sexFemale;


    public static void main(String[] args) throws IOException, DocumentException {
        String commonPath = "src" + separator + "main" + separator + "resources" + separator;
        String[] namesFileTxt = {"country.txt", "region.txt", "city.txt", "street.txt", "surname.txt",
                "female_name.txt", "female_patronymic.txt", "male_name.txt", "male_patronymic.txt"};

        String pathFileExcel = commonPath + "data_people.xls";
        String pathFilePdf = commonPath + "data_people.pdf";


        for (String nameFile : namesFileTxt) {
            allDataFile.add(readFileTxt(commonPath + nameFile));
        }

        int maxCountPeople = random.nextInt(generationCountPeople);
        String urlMaxCountPeople = url + "?results=" + maxCountPeople;


        List<Person> objectList = new ArrayList<Person>();

        for (int i = 0; i < maxCountPeople; i++) {
            objectList.add(new Person());
        }

        int responseCode = 0;
        String strJSON = null;

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(new HttpGet(urlMaxCountPeople));

            responseCode = response.getStatusLine().getStatusCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            strJSON = result.toString();

        } catch (UnknownHostException ex){
            System.out.println("Отсутствует сеть");
        }

        if (responseCode == 200) {
            generationDataPeopleOfInternet(strJSON, objectList);
        } else {
            generationDataPeopleOfFile(objectList);
        }

        FileExcel fileExcel = new FileExcel(pathFileExcel);
        fileExcel.writeExcel(objectList);

        FilePdf filePdf = new FilePdf(pathFilePdf);
        filePdf.writePdf(objectList);

    }

    private static void generationDataPeopleOfFile(List<Person> dataPeoples) {
        for (int i = 0; i < dataPeoples.size(); i++) {
            surnamePeople = allDataFile.get(4).get(random.nextInt(allDataFile.get(4).size()));
            sexFemale = consonantsRussian.indexOf(surnamePeople.charAt(surnamePeople.length() - 1));
            dataPeoples.get(i).setSurname(surnamePeople);
            if (sexFemale != -1) {
                dataPeoples.get(i).setName(allDataFile.get(7).get(random.nextInt(allDataFile.get(7).size())));
                dataPeoples.get(i).setPatronymic(allDataFile.get(8).get(random.nextInt(allDataFile.get(8).size())));
                dataPeoples.get(i).setSex("м");
            } else {
                dataPeoples.get(i).setName(allDataFile.get(5).get(random.nextInt(allDataFile.get(5).size())));
                dataPeoples.get(i).setPatronymic(allDataFile.get(6).get(random.nextInt(allDataFile.get(6).size())));
                dataPeoples.get(i).setSex("ж");
            }
            dataPeoples.get(i).setDateBirthRandom();
            dataPeoples.get(i).setAge();
            dataPeoples.get(i).setInn();
            dataPeoples.get(i).setIndex();
            dataPeoples.get(i).setCountry(allDataFile.get(0).get(random.nextInt(allDataFile.get(0).size())));
            dataPeoples.get(i).setRegion(allDataFile.get(1).get(random.nextInt(allDataFile.get(1).size())));
            dataPeoples.get(i).setCity(allDataFile.get(2).get(random.nextInt(allDataFile.get(2).size())));
            dataPeoples.get(i).setStreet(allDataFile.get(3).get(random.nextInt(allDataFile.get(3).size())));
            dataPeoples.get(i).setHouse(random.nextInt(300));
            dataPeoples.get(i).setApartment(random.nextInt(300));
        }
    }

    private static void generationDataPeopleOfInternet(String strJSON, List<Person> dataPeoples) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(strJSON);
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
            dataPeoples.get(i).setDateBirthInternet(dateBirth);
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


    private static List<String> readFileTxt(String pathFile) throws FileNotFoundException {
        List<String> tempList = new ArrayList<String>();
        File file = new File(pathFile);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            tempList.add(scanner.nextLine());
        }
        scanner.close();
        return tempList;
    }
}


