package com.university.service;

import com.university.models.University;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Реалізувати:
 * 1)	функції пошуку університету по місту і країні;                done
 * 2)	вивести всі університети по кількості студентів від x до y;   done
 * 3)	функції додавання\вилучення напрямів навчання;                done
 * 4)	відсортувати всі університети по кількості студентів методом Шелла;  done
 * 5)	вивести всі університети, що мають заданий напрям навчання           done
 * 6)	вивести університети з найменшою і найбільшою ціною навчання.        done
 */
public class UniversityService {
    public List<University> searchByCityAndCountry(List<University> universities,
                                                   String city,
                                                   String country) {
        List<University> result = new ArrayList<>();
        for (University university : universities) {
            if (university.getCity().equals(city) && university.getCountry().equals(country)) {
                result.add(university);
            }
        }
        return result;
    }

    // Task 2: Filter universities by number of students
    public List<University> filterByNumberOfStudents(List<University> universities,
                                                     int minStudents,
                                                     int maxStudents) {
        List<University> result = new ArrayList<>();
        for (University university : universities) {
            if (university.getNumberOfStudents() >= minStudents
                    && university.getNumberOfStudents() <= maxStudents) {
                result.add(university);
            }
        }
        return result;
    }

    // Task 3: Add study program to a university
    public boolean addStudyProgram(List<University> universities, String universityName, String program) {
        University university = universities
                .stream()
                .filter(u -> u.getName().equals(universityName))
                .findFirst()
                .orElse(null);

        if (university == null) {
            return false;
        }


        List<String> studyPrograms = new ArrayList<>(university.getStudyPrograms());
        studyPrograms.add(program);

        university.setStudyPrograms(studyPrograms);

        return true;
    }

    // Task 3: Remove study program from a university
    public boolean removeStudyProgram(List<University> universities, String universityName, String program) {
        University university = universities
                .stream()
                .filter(u -> u.getName().equals(universityName))
                .findFirst()
                .orElse(null);

        if (university == null) {
            return false;
        }

        return university.getStudyPrograms().remove(program);
    }

    // Task 4: Shell sort universities by number of students
    public List<University> shellSortByNumberOfStudents(List<University> universities) {
        Comparator<University> comparator = Comparator.comparingInt(University::getNumberOfStudents);

        List<University> sorted = new ArrayList<>(universities);
        int n = sorted.size();

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                University temp = sorted.get(i);
                int j;
                for (j = i; j >= gap && comparator.compare(sorted.get(j - gap), temp) > 0; j -= gap) {
                    Collections.swap(sorted, j, j - gap);
                }
                sorted.set(j, temp);
            }
        }
        return sorted;
    }

    // Task 5: Search universities by study program
    public List<University> searchByStudyProgram(List<University> universities, String program) {
        List<University> result = new ArrayList<>();
        for (University university : universities) {
            if (university.getStudyPrograms().contains(program)) {
                result.add(university);
            }
        }
        return result;
    }

    // Task 6: Find university with the minimum tuition fee
    public University findMinTuitionFee(List<University> universities) {
        if (universities.isEmpty()) {
            return null; // or throw an exception, handle accordingly
        }

        University minUniversity = universities.get(0);
        for (int i = 1; i < universities.size(); i++) {
            University currentUniversity = universities.get(i);
            if (currentUniversity.getTuitionFee() < minUniversity.getTuitionFee()) {
                minUniversity = currentUniversity;
            }
        }
        return minUniversity;
    }

    // Task 6: Find university with the maximum tuition fee
    public University findMaxTuitionFee(List<University> universities) {
        if (universities.isEmpty()) {
            return null; // or throw an exception, handle accordingly
        }

        University maxUniversity = universities.get(0);
        for (int i = 1; i < universities.size(); i++) {
            University currentUniversity = universities.get(i);
            if (currentUniversity.getTuitionFee() > maxUniversity.getTuitionFee()) {
                maxUniversity = currentUniversity;
            }
        }
        return maxUniversity;
    }

    public boolean writeToFile(List<University> universities, String url, boolean append)
            throws IOException {
        String start = "\n\n----------------------------- New record -----------------------------\n\n";
        File file = new File(url);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fWriter = new FileWriter(file, append)) {
            for (University university : universities) {
                fWriter.write(start);
                fWriter.write(university.toString());
            }
        }
        return true;
    }

    public List<University> readFromFile(String uri) throws IOException {
        File file = new File(uri);
        List<University> list = new ArrayList<>();

        if (file.exists()) {
            try (Scanner reader = new Scanner(new FileReader(file))) {
                addToList(reader, list);
            }
        } else
            throw new IllegalArgumentException();
        return list;
    }

    private void addToList(Scanner reader, List<University> list) {
        while (reader.hasNext()) {
            String name = reader.next();
            String city = reader.next();
            String country = reader.next();
            int students = Integer.parseInt(reader.next());

            // Use StringTokenizer to split the study programs
            StringTokenizer tokenizer = new StringTokenizer(reader.next(), ",");
            List<String> studyPrograms = new ArrayList<>();
            while (tokenizer.hasMoreTokens()) {
                studyPrograms.add(tokenizer.nextToken());
            }

            double tuitionFee = Double.parseDouble(reader.next());

            University university = new University(name, city, country, students, studyPrograms, tuitionFee);
            list.add(university);
        }
    }
}
