package com.university.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 24.	Створити клас “Університет” (назва, місто, країна, кількість студентів,
 * напрями навчання, ціна навчання).
 * Для класу створити:
 * 1) конструктор за замовчуванням;               done
 * 2) конструктор з параметрами;                  done
 * 3) конструктор копій;                          done
 * 4) перевизначити операції << та >> для зчитування та запису у файл.
 */
public class University {
    private String name;
    private String city;
    private String country;
    private int numberOfStudents;
    private List<String> studyPrograms;
    private double tuitionFee;

    /**
     * конструктор за замовчуванням
     */
    public University() {
        this.studyPrograms = new ArrayList<>();
    }

    /**
     * конструктор з параметрами;
     */
    public University(String name, String city, String country, int numberOfStudents,
                      List<String> studyPrograms, double tuitionFee) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.numberOfStudents = numberOfStudents;
        this.studyPrograms = studyPrograms;
        this.tuitionFee = tuitionFee;
    }

    /**
     * конструктор копій
     */
    public University(University other) {
        this.name = other.name;
        this.city = other.city;
        this.country = other.country;
        this.numberOfStudents = other.numberOfStudents;
        this.studyPrograms = new ArrayList<>(other.studyPrograms);
        this.tuitionFee = other.tuitionFee;
    }

    /**
     * Перевизначений метод equals для порівння двох обєктів класу University
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        University that = (University) o;
        return numberOfStudents == that.numberOfStudents && Objects.equals(name, that.name) && Objects.equals(city, that.city) && Objects.equals(country, that.country);
    }

    /**
     *
     * Перевизначаєм метод з батьківського класу Object
     */
    @Override
    public String toString() {
        return "University{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", numberOfStudents=" + numberOfStudents +
                ", studyPrograms=" + studyPrograms +
                ", tuitionFee=" + tuitionFee +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<String> getStudyPrograms() {
        return studyPrograms;
    }

    public void setStudyPrograms(List<String> studyPrograms) {
        this.studyPrograms = studyPrograms;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }
}
