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


    public FileExcel(String pathFileExcel) {
        this.path = pathFileExcel;
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
        for (int i = 0; i < dataPeoples.size(); i++) {
            row = newSheet.createRow(rownum);
            row.createCell(0).setCellValue(dataPeoples.get(i).getName());
            row.createCell(1).setCellValue(dataPeoples.get(i).getSurname());
            row.createCell(2).setCellValue(dataPeoples.get(i).getPatronymic());
            row.createCell(3).setCellValue(dataPeoples.get(i).getAge());
            row.createCell(4).setCellValue(dataPeoples.get(i).getSex());
            row.createCell(5).setCellValue(dataPeoples.get(i).getDateBirth());
            row.createCell(6).setCellValue(dataPeoples.get(i).getInn());
            row.createCell(7).setCellValue(dataPeoples.get(i).getIndex());
            row.createCell(8).setCellValue(dataPeoples.get(i).getCountry());
            row.createCell(9).setCellValue(dataPeoples.get(i).getRegion());
            row.createCell(10).setCellValue(dataPeoples.get(i).getCity());
            row.createCell(11).setCellValue(dataPeoples.get(i).getStreet());
            row.createCell(12).setCellValue(dataPeoples.get(i).getHouse());
            row.createCell(13).setCellValue(dataPeoples.get(i).getApartment());
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