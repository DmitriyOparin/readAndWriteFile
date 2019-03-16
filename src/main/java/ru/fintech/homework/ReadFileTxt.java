package ru.fintech.homework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFileTxt {
    public List<String> readFileTxt(String pathFile) {
        List<String> tempList = new ArrayList<String>();
        File file = new File(pathFile);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                tempList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return tempList;
    }
}
