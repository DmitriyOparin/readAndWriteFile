package ru.fintech.homework;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static String separator = File.separator;
    private static String consonantsRussian = "йцкнгшщзхфвпрлджбтмсч";
    private static Random random = new Random();
    private static int maxCountPeeple = random.nextInt(30);

    public static void main(String[] args) throws IOException, DocumentException {
//        String separator = File.separator;

        String pathFileCountry = "src" + separator + "main" + separator + "resources" + separator + "country.txt";
        String pathFileCity = "src" + separator + "main" + separator + "resources" + separator + "city.txt";
        String pathFileRegion = "src" + separator + "main" + separator + "resources" + separator + "region.txt";
        String pathFileStreet = "src" + separator + "main" + separator + "resources" + separator + "street.txt";
        String pathFileSurname = "src" + separator + "main" + separator + "resources" + separator + "surname.txt";
        String pathFileFemaleName = "src" + separator + "main" + separator + "resources" + separator + "female_name.txt";
        String pathFileFemalePatronymic = "src" + separator + "main" + separator + "resources" + separator + "female_patronymic.txt";
        String pathFileMaleName = "src" + separator + "main" + separator + "resources" + separator + "male_name.txt";
        String pathFileMalePatronymic = "src" + separator + "main" + separator + "resources" + separator + "male_patronymic.txt";
        String pathFileExcel = "src" + separator + "main" + separator + "resources" + separator + "out.xls";
        String pathFilePdf = "src" + separator + "main" + separator + "resources" + separator + "out.pdf";

        List<String> country = new ArrayList<String>();
        List<String> city = new ArrayList<String>();
        List<String> region = new ArrayList<String>();
        List<String> street = new ArrayList<String>();
        List<String> surname = new ArrayList<String>();
        List<String> femaleName = new ArrayList<String>();
        List<String> femalePatronymic = new ArrayList<String>();
        List<String> maleName = new ArrayList<String>();
        List<String> malePatronymic = new ArrayList<String>();

        country = readFileTxt(pathFileCountry);
        city = readFileTxt(pathFileCity);
        region = readFileTxt(pathFileRegion);
        street = readFileTxt(pathFileStreet);
        surname = readFileTxt(pathFileSurname);
        femaleName = readFileTxt(pathFileFemaleName);
        femalePatronymic = readFileTxt(pathFileFemalePatronymic);
        maleName = readFileTxt(pathFileMaleName);
        malePatronymic = readFileTxt(pathFileMalePatronymic);

        writeExcel(pathFileExcel, country, city, region, street, surname, femaleName, femalePatronymic, maleName, malePatronymic);
        writePdf(pathFilePdf, country, city, region, street, surname, femaleName, femalePatronymic, maleName, malePatronymic);

    }

    private static void writePdf(String pathFilePdf, List<String> country, List<String> city, List<String> region, List<String> street, List<String> surname, List<String> femaleName, List<String> femalePatronymic, List<String> maleName, List<String> malePatronymic) throws IOException, DocumentException {
        String pathFileTtf = "src" + separator + "main" + separator + "resources" + separator + "arial.ttf";
        int sexFemale;

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pathFilePdf));
        document.open();


        BaseFont bf = BaseFont.createFont(pathFileTtf, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);

        PdfPTable table = new PdfPTable(14);

        table.setWidthPercentage(100);


        PdfPCell c1 = new PdfPCell(new Paragraph("имя", font));
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Paragraph("фамилия", font));
        table.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Paragraph("отчество", font));
        table.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Paragraph("возраст", font));
        table.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Paragraph("пол", font));
        table.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Paragraph("дата рождения", font));
        table.addCell(c6);
        PdfPCell c7 = new PdfPCell(new Paragraph("инн", font));
        table.addCell(c7);
        PdfPCell c8 = new PdfPCell(new Paragraph("почтовый индекс", font));
        table.addCell(c8);
        PdfPCell c9 = new PdfPCell(new Paragraph("страна", font));
        table.addCell(c9);
        PdfPCell c10 = new PdfPCell(new Paragraph("область", font));
        table.addCell(c10);
        PdfPCell c11 = new PdfPCell(new Paragraph("город", font));
        table.addCell(c11);
        PdfPCell c12 = new PdfPCell(new Paragraph("улица", font));
        table.addCell(c12);
        PdfPCell c13 = new PdfPCell(new Paragraph("дом", font));
        table.addCell(c13);
        PdfPCell c14 = new PdfPCell(new Paragraph("квартира", font));
        table.addCell(c14);

        for (int i = 0; i < maxCountPeeple; i++) {
            String surnamePeople;
            surnamePeople = surname.get(random.nextInt(surname.size()));
            sexFemale = consonantsRussian.indexOf(surnamePeople.charAt(surnamePeople.length() - 1));
            // фамилия, имя, отчество, пол
            if (sexFemale != -1) {
                // male
                table.addCell(maleName.get(random.nextInt(maleName.size())));
//                table.addCell(malePatronymic.get(random.nextInt(malePatronymic.size())));
//                table.addCell("м");
            } else {
                // female
                table.addCell(femaleName.get(random.nextInt(femaleName.size())));
//                table.addCell(femalePatronymic.get(random.nextInt(femalePatronymic.size())));
//                table.addCell("ж");
            }

        }
        document.add(table);
        document.close();
    }


    private static void writeExcel(String pathFileExcel, List<String> country, List<String> city, List<String> region, List<String> street, List<String> surname, List<String> femaleName, List<String> femalePatronymic, List<String> maleName, List<String> malePatronymic) throws IOException {
        int sexFemale;

//        int maxCountPeeple = random.nextInt(30);
        HSSFWorkbook workbook = new HSSFWorkbook();

        Sheet newSheet = workbook.createSheet("таблица");
        int rownum = 0;
        Row row;
        row = newSheet.createRow(rownum);
        row.createCell(0).setCellValue("имя");
        row.createCell(1).setCellValue("фамилия");
        row.createCell(2).setCellValue("отчество");
        row.createCell(3).setCellValue("возраст");
        row.createCell(4).setCellValue("пол");
        row.createCell(5).setCellValue("дата рождения");
        row.createCell(6).setCellValue("инн");
        row.createCell(7).setCellValue("почтовый индекс");
        row.createCell(8).setCellValue("страна");
        row.createCell(9).setCellValue("область");
        row.createCell(10).setCellValue("город");
        row.createCell(11).setCellValue("улица");
        row.createCell(12).setCellValue("дом");
        row.createCell(13).setCellValue("квартира");


        for (int i = 1; i < maxCountPeeple; i++) {
            row = newSheet.createRow(i);
            String surnamePeople;
            surnamePeople = surname.get(random.nextInt(surname.size()));
            row.createCell(1).setCellValue(surnamePeople);
            sexFemale = consonantsRussian.indexOf(surnamePeople.charAt(surnamePeople.length() - 1));
            // фамилия, имя, отчество, пол
            if (sexFemale != -1) {
                // male
                row.createCell(0).setCellValue(maleName.get(random.nextInt(maleName.size())));
                row.createCell(2).setCellValue(malePatronymic.get(random.nextInt(malePatronymic.size())));
                row.createCell(4).setCellValue("м");
            } else {
                // female
                row.createCell(0).setCellValue(femaleName.get(random.nextInt(femaleName.size())));
                row.createCell(2).setCellValue(femalePatronymic.get(random.nextInt(femalePatronymic.size())));
                row.createCell(4).setCellValue("ж");
            }

            // дата рождения
            String dateBirth;
            dateBirth = randomGenerationDateBirth();
            row.createCell(5).setCellValue(dateBirth);

            // возраст
            int age;
            age = getAge(dateBirth);
            row.createCell(3).setCellValue(age);


            // инн
            String valueInn;
            valueInn = generationValidInn();
            row.createCell(6).setCellValue(valueInn);

            // индекс
            String valueIndex;
            valueIndex = generationIndex();
            row.createCell(7).setCellValue(valueIndex);

            // страна
            String countryPeople;
            countryPeople = country.get(random.nextInt(country.size()));
            row.createCell(8).setCellValue(countryPeople);

            // область
            String regionPeople;
            regionPeople = region.get(random.nextInt(region.size()));
            row.createCell(9).setCellValue(regionPeople);

            // город
            String cityPeople;
            cityPeople = city.get(random.nextInt(city.size()));
            row.createCell(10).setCellValue(cityPeople);

            // улица
            String streetPeople;
            streetPeople = street.get(random.nextInt(street.size()));
            row.createCell(11).setCellValue(streetPeople);

            // дом
            row.createCell(12).setCellValue(random.nextInt(300));

            // квартира
            row.createCell(13).setCellValue(random.nextInt(300));
        }

        FileOutputStream fileout = new FileOutputStream(pathFileExcel);
        workbook.write(fileout);
        fileout.close();
        String fullpath = new File(pathFileExcel).getCanonicalPath();
        System.out.println("Файл создан. Путь: " + fullpath);
    }

    private static String generationIndex() {
        int[] index = {1, 0, 0, 0, 0, 0};
        for (int i = 1; i < index.length; i++) {
            index[i] = random.nextInt(10);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < index.length; i++) {
            result.append(index[i]);
        }
        return result.toString();
    }

    private static int getAge(String dateBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(dateBirth, formatter);
        LocalDate endDate = LocalDate.now();
        Period period = Period.between(startDate, endDate);
        return period.getYears();
    }

    private static String generationValidInn() {
        int[] inn = {7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] validFirstNumbers = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
        int[] validSecondNumbers = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};

        for (int i = 2; i < 10; i++) {
            inn[i] = random.nextInt(10);
        }

        int validSumFirst = 0;
        for (int i = 0; i < validFirstNumbers.length; i++) {
            validSumFirst += validFirstNumbers[i] * inn[i];
        }
        int controlNumberFirst = validSumFirst % 11;
        if (controlNumberFirst > 9) {
            controlNumberFirst = controlNumberFirst % 10;
        }
        inn[10] = controlNumberFirst;
        int validSumSecond = 0;
        for (int i = 0; i < validSecondNumbers.length; i++) {
            validSumSecond += validSecondNumbers[i] * inn[i];
        }
        int controlNumberSecond = validSumSecond % 11;
        if (controlNumberSecond > 9) {
            controlNumberSecond = controlNumberSecond % 10;
        }
        inn[11] = controlNumberSecond;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < inn.length; i++) {
            result.append(inn[i]);
        }
        return result.toString();
    }

    private static String randomGenerationDateBirth() {
        LocalDate localDate;
        localDate = LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = localDate.format(formatter);
        return formattedString;
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



