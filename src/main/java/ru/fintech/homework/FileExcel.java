package ru.fintech.homework;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class FileExcel {
    private String path;

    FileExcel() {
        String separator = File.separator;
        String commonPath = "src" + separator + "main" + separator + "resources" + separator;
        this.path = commonPath + "data_people.xls";
    }

    public void writeExcel(List<Person> dataPeoples) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        Sheet newSheet = workbook.createSheet("Данные людей");
        String[] namesColumnExcel = {"Имя", "Фамилия", "Отчество", "Возраст", "Пол",
                "Дата рождения", "ИНН", "Почтовый индекс", "Страна", "Область",
                "Город", "Улица", "Дом", "Квартира"};
        int rownum = 0;
        Row row;
        row = newSheet.createRow(rownum);
        for (int i = 0; i < namesColumnExcel.length; i++) {
            row.createCell(i).setCellValue(namesColumnExcel[i]);
        }

        rownum = 1;
        for (Person dataPeople : dataPeoples) {
            row = newSheet.createRow(rownum);
            row.createCell(0).setCellValue(dataPeople.getName());
            row.createCell(1).setCellValue(dataPeople.getSurname());
            row.createCell(2).setCellValue(dataPeople.getPatronymic());
            row.createCell(3).setCellValue(dataPeople.getAge());
            row.createCell(4).setCellValue(dataPeople.getSex());
            row.createCell(5).setCellValue(dataPeople.getDateBirth());
            row.createCell(6).setCellValue(dataPeople.getInn());
            row.createCell(7).setCellValue(dataPeople.getIndex());
            row.createCell(8).setCellValue(dataPeople.getCountry());
            row.createCell(9).setCellValue(dataPeople.getRegion());
            row.createCell(10).setCellValue(dataPeople.getCity());
            row.createCell(11).setCellValue(dataPeople.getStreet());
            row.createCell(12).setCellValue(dataPeople.getHouse());
            row.createCell(13).setCellValue(dataPeople.getApartment());
            rownum++;
        }
        FileOutputStream fileout = null;
        try {
            fileout = new FileOutputStream(this.path);
            workbook.write(fileout);
            String fullpath = new File(this.path).getCanonicalPath();
            System.out.println("Файл создан. Путь: " + fullpath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}