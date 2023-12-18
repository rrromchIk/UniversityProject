import com.university.models.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rom4ik
 */
public class UniversityTests {
    private static String name;
    private static String city;
    private static String country;
    private static int numberOfStudents;
    private static List<String> studyPrograms;
    private static double tuitionFee;

    @BeforeAll
    public static void init() {
        name = "NULP";
        city = "Lviv";
        country = "Ukraine";
        numberOfStudents = 10_000;
        studyPrograms = List.of("PZ", "KN");
        tuitionFee = 50_000.5;
    }

    @Test
    public void testCreation() {
        University university1 = new University(name,
                city,
                country,
                numberOfStudents,
                studyPrograms,
                tuitionFee);

        University university2 = new University(university1);

        University university3 = new University();
        university3.setName(name);
        university3.setCity(city);
        university3.setCountry(country);
        university3.setNumberOfStudents(numberOfStudents);
        university3.setStudyPrograms(studyPrograms);
        university3.setTuitionFee(tuitionFee);

        assertNotSame(university1, university2);
        assertNotSame(university2, university3);
        assertNotSame(university1, university3);
        assertEquals(university1, university2);
        assertEquals(university2, university3);
        assertEquals(university1, university3);

        System.out.println(university1.toString());
    }

}
