package ru.fintech.homework;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {
    private static String url = "http://randomuser.ru/api.json";
    private static Random random = new Random();
    private static int generationCountPeople = 30;

    public static void main(String[] args) {
        int maxCountPeople = random.nextInt(generationCountPeople) + 1;
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
        } catch (UnknownHostException ex) {
            System.out.println("Нет сети");
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean flagEmptyDB;
        Database db = new Database();
        db.createDB();
        db.createTables();
        flagEmptyDB = db.controlEmptyTables();

        if (responseCode == 200 && strJSON != null) {
            GenerationDataPersonInInternet genDataPerson = new GenerationDataPersonInInternet();
            genDataPerson.readFile();
            genDataPerson.generation(strJSON, objectList);
            db.addOrUpdateDataPersonInDB(objectList);
        } else if (!flagEmptyDB) {
            objectList = db.getDataPersonInDB();
        } else {
            GenerationDataPersonInFile genDataPerson = new GenerationDataPersonInFile();
            genDataPerson.readFile();
            genDataPerson.generation(objectList);
        }

        FileExcel fileExcel = new FileExcel();
        fileExcel.writeExcel(objectList);

        FilePdf filePdf = new FilePdf();
        filePdf.writePdf(objectList);
    }
}