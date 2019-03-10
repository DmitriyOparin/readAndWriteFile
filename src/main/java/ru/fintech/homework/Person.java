package ru.fintech.homework;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Person {
    private static Random random = new Random();

    private String name;
    private String surname;
    private String patronymic;
    private String sex;
    private String dateBirth;
    private String inn;
    private String index;
    private String country;
    private String region;
    private String city;
    private String street;
    private int age;
    private int house;
    private int apartment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(this.dateBirth, formatter);
        LocalDate endDate = LocalDate.now();
        Period period = Period.between(startDate, endDate);
        this.age = period.getYears();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirthRandom() {
        LocalDate localDate;
        localDate = LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedString = localDate.format(formatter);
        this.dateBirth = formattedString;
    }

    public void setDateBirthInternet(long unixTime) {
        Date date = new Date(unixTime * 1000);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String result = format.format(date);
        this.dateBirth = result;
    }

    public String getInn() {
        return inn;
    }

    public void setInn() {
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
        this.inn = result.toString();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex() {
        int[] indexGenerator = {1, 0, 0, 0, 0, 0};
        for (int i = 1; i < indexGenerator.length; i++) {
            indexGenerator[i] = random.nextInt(10);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < indexGenerator.length; i++) {
            result.append(indexGenerator[i]);
        }
        this.index = result.toString();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }
}
