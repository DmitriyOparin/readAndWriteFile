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

import static ru.fintech.homework.Main.separator;

public class FilePdf {
    private String path;


    public FilePdf(String path) {
        this.path = path;
    }

    public void writePdf(List<Person> dataPeoples) throws IOException, DocumentException {
        String commonPath = "src" + separator + "main" + separator + "resources" + separator;
        String pathFileTtf = commonPath + "arial.ttf";

        String[] namesColumnExcel = {"Имя", "Фамилия", "Отчество", "Возраст", "Пол",
                "Дата рождения", "ИНН", "Почтовый индекс", "Страна", "Область",
                "Город", "Улица", "Дом", "Квартира"};

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();


        BaseFont bf = BaseFont.createFont(pathFileTtf, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);

        PdfPTable table = new PdfPTable(namesColumnExcel.length);

        for (int i = 0; i < namesColumnExcel.length; i++) {
            PdfPCell cell = new PdfPCell(new Paragraph(namesColumnExcel[i], font));
            table.addCell(cell);
        }

        for (int i = 0; i < dataPeoples.size(); i++) {
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getName(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getSurname(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getPatronymic(), font)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(dataPeoples.get(i).getAge()), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getSex(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getDateBirth(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getInn(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getIndex(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getCountry(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getRegion(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getCity(), font)));
            table.addCell(new PdfPCell(new Paragraph(dataPeoples.get(i).getStreet(), font)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(dataPeoples.get(i).getHouse()), font)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(dataPeoples.get(i).getApartment()), font)));
        }

        document.add(table);
        document.close();
        String fullpath = new File(this.path).getCanonicalPath();
        System.out.println("Файл создан. Путь: " + fullpath);
    }
}