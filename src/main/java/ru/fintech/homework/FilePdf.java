package ru.fintech.homework;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class FilePdf {
    private String separator = File.separator;
    private String commonPath = "src" + separator + "main" + separator + "resources" + separator;
    private String path;

    FilePdf() {
        this.path = commonPath + "data_people.pdf";
    }

    public void writePdf(List<Person> dataPeoples) {
        String pathFileTtf = commonPath + "arial.ttf";

        String[] namesColumnExcel = {"Имя", "Фамилия", "Отчество", "Возраст", "Пол",
                "Дата рождения", "ИНН", "Почтовый индекс", "Страна", "Область",
                "Город", "Улица", "Дом", "Квартира"};

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        Font font = null;
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            BaseFont bf = BaseFont.createFont(pathFileTtf, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            font = new Font(bf);
            document.open();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }


        PdfPTable table = new PdfPTable(namesColumnExcel.length);

        for (String aNamesColumnExcel : namesColumnExcel) {
            PdfPCell cell = new PdfPCell(new Paragraph(aNamesColumnExcel, font));
            table.addCell(cell);
        }

        for (Person dataPeople : dataPeoples) {
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getName(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getSurname(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getPatronymic(), font)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(dataPeople.getAge()), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getSex(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getDateBirth(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getInn(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getIndex(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getCountry(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getRegion(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getCity(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeople.getStreet(), font)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(dataPeople.getHouse()), font)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(dataPeople.getApartment()), font)));
        }

        try {
            document.add(table);
            String fullpath = new File(this.path).getCanonicalPath();
            System.out.println("Файл создан. Путь: " + fullpath);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}