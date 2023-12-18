import com.university.models.University;
import com.university.service.UniversityService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author rom4ik
 */
public class UniversityServiceTests {
    private static UniversityService universityService;
    private static List<University> universities;

    @BeforeAll
    public static void init() {
        universityService = new UniversityService();

        universities = new ArrayList<>();
        universities.addAll(List.of(
                new University("NULP",
                        "Lviv",
                        "Ukraine",
                        50_000,
                        List.of("PZ", "KN"),
                        100_000),
                new University("LNU",
                        "Lviv",
                        "Ukraine",
                        40_000,
                        List.of("UA", "EN"),
                        110_000),
                new University("KNU",
                        "Kyiv",
                        "Ukraine",
                        45_000,
                        List.of("UA", "EN"),
                        105_000)
                )
        );
    }

    @Test
    void searchByCityAndCountryTest() {
        List<University> result =
                universityService.searchByCityAndCountry(universities, "Lviv", "Ukraine");

        List<University> universitiesWithoutKyivOnly =
                universities.stream().filter(u ->  !u.getCity().equals("Kyiv"))
                        .collect(Collectors.toList());
        assertIterableEquals(universitiesWithoutKyivOnly, result);

        result = universityService.searchByCityAndCountry(universities, "Kyiv", "Ukraine");

        List<University> universitiesWithKyivOnly =
                universities.stream().filter(u ->  u.getCity().equals("Kyiv"))
                .collect(Collectors.toList());
        assertIterableEquals(result, universitiesWithKyivOnly);
    }

    @Test
    void filterByNumberOfStudentsMethodTest() {
        int minStudentsNumber = 40_000;
        int maxStudentsNumber = 46_000;
        List<University> filtered =
                universityService.filterByNumberOfStudents(universities, minStudentsNumber, maxStudentsNumber);

        assertEquals(2, filtered.size());

        maxStudentsNumber = 50_000;
        filtered = universityService.filterByNumberOfStudents(universities, minStudentsNumber, maxStudentsNumber);
        assertEquals(3, filtered.size());

        minStudentsNumber = 20000;
        maxStudentsNumber = 30000;
        filtered = universityService.filterByNumberOfStudents(universities, minStudentsNumber, maxStudentsNumber);
        assertEquals(0, filtered.size());
    }

    @Test
    void shellSortByNumberOfStudentsMethodTest() {
        List<University> sorted =
                universityService.shellSortByNumberOfStudents(universities);

        List<University> sortedExpected = universities.stream()
                        .sorted(Comparator.comparingInt(University::getNumberOfStudents))
                        .collect(Collectors.toList());

        assertIterableEquals(sortedExpected, sorted);
    }

    @Test
    void searchByStudyProgramMethodTest() {
        List<University> result = universityService.searchByStudyProgram(universities, "KN");
        assertEquals(1, result.size());

        result = universityService.searchByStudyProgram(universities, "UA");
        assertEquals(2, result.size());
    }

    @Test
    void findMinAndMaxTuitionFeeMethodTest() {
        University result = universityService.findMinTuitionFee(universities);
        assertEquals(result, universities.get(0));

        result = universityService.findMaxTuitionFee(universities);
        assertEquals(result, universities.get(1));
    }
}
