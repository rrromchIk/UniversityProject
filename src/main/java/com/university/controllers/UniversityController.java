package com.university.controllers;

import com.university.alerts.MyAlert;
import com.university.main.MyApplication;
import com.university.models.University;
import com.university.service.UniversityService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.university.main.MyApplication.PATH_TO_INPUT_FILE;
import static com.university.main.MyApplication.PATH_TO_OUTPUT_FILE;

public class UniversityController {
    private final List<University> universities;
    private final UniversityService universityService;
    private final MyAlert alert;

    public UniversityController() {
        this.universities = new ArrayList<>();
        this.universityService = new UniversityService();
        this.alert = new MyAlert(MyApplication.WINDOW_TITLE);

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

    /**
     * Викликається коли створюється контролер і завантажується вікно
     */


    @FXML
    private TextArea resultTextArea;

    @FXML
    private TextField countryInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField studyProgramInput;

    @FXML
    private TextField fromNumberOfStudInput;

    @FXML
    private TextField toNumberOfStudInput;

    @FXML
    private TextField studyProgramToAddInput;

    @FXML
    private TextField universityNameInput;

    @FXML
    private TableView<University> universityTable;



    private void addUniversitiesToTable() {
        // Create columns
        TableColumn<University, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<University, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<University, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<University, Integer> studentsColumn = new TableColumn<>("Students");
        studentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfStudents"));

        TableColumn<University, String> programsColumn = new TableColumn<>("Study Programs");
        programsColumn.setCellValueFactory(new PropertyValueFactory<>("studyPrograms"));

        TableColumn<University, Double> tuitionColumn = new TableColumn<>("Tuition Fee");
        tuitionColumn.setCellValueFactory(new PropertyValueFactory<>("tuitionFee"));

        universityTable.getColumns().clear();
        // Add columns to the table
        universityTable.getColumns().addAll(nameColumn, cityColumn, countryColumn, studentsColumn, programsColumn, tuitionColumn);
        // Fill the table with data
        ObservableList<University> universitiesObservable = FXCollections.observableArrayList(universities);

        universityTable.setItems(universitiesObservable);
    }

    @FXML
    public void initialize() {
        addUniversitiesToTable();
    }
    @FXML
    protected void onFindMinTuitionFeeButtonClick() {
        University universityWithMinTuitionFee = universityService.findMinTuitionFee(universities);

        String result = "University with min tuition fee:\n";
        result += universityWithMinTuitionFee.toString();

        resultTextArea.setText(result);
    }

    @FXML
    protected void onFindMaxTuitionFeeButtonClick() {
        University universityWithMaxTuitionFee = universityService.findMaxTuitionFee(universities);

        String result = "University with min tuition fee:\n";
        result += universityWithMaxTuitionFee.toString();
        resultTextArea.setText(result);
    }
    @FXML
    protected void onSearchUniversityByCityAndCountryButtonClick() {
        String country = countryInput.getText();
        String city = cityInput.getText();

        List<University> universitiesFound =
                universityService.searchByCityAndCountry(universities, city, country);

        if(universitiesFound.size() == 0) {
            alert.show(Alert.AlertType.INFORMATION, "No result", "Nothing found");
        } else {
            StringBuilder result = new StringBuilder("University found by city and country:\n");
            universitiesFound.forEach(u -> result.append("" + u.toString() + "\n"));
            resultTextArea.setText(result.toString());
        }
    }

    @FXML
    protected void onSortByNumberOfStudentsButtonClick() {
        List<University> result =
                universityService.shellSortByNumberOfStudents(universities);
        universities.clear();
        universities.addAll(result);

        addUniversitiesToTable();
        resultTextArea.setText(universities.toString());
    }

    @FXML
    protected void onFindByStudyProgramButtonClick() {
        String studyProgram = studyProgramInput.getText();

        List<University> universitiesFound =
                universityService.searchByStudyProgram(universities, studyProgram);

        if(universitiesFound.size() == 0) {
            alert.show(Alert.AlertType.INFORMATION, "No result", "Nothing found");
        } else {
            StringBuilder result = new StringBuilder("University found by study program:\n");
            universitiesFound.forEach(u -> result.append("" + u.toString() + "\n"));
            resultTextArea.setText(result.toString());
        }
    }

    @FXML
    protected void onFindByNumberOfStudentsButtonClick() {
        int minNumOfStud = 0;
        int maxNumOfStud = 0;

        try {
            minNumOfStud = Integer.parseInt(fromNumberOfStudInput.getText());
            maxNumOfStud = Integer.parseInt(toNumberOfStudInput.getText());
        } catch (NumberFormatException e) {
            alert.show(Alert.AlertType.ERROR, "Bad input",
                    "Please provide valid number value");
            return;
        }


        List<University> universitiesFound =
                universityService.filterByNumberOfStudents(universities, minNumOfStud, maxNumOfStud);

        if(universitiesFound.size() == 0) {
            alert.show(Alert.AlertType.INFORMATION, "No result", "Nothing found");
        } else {
            StringBuilder result = new StringBuilder("University found by number of students:\n");
            universitiesFound.forEach(u -> result.append("" + u.toString() + "\n"));
            resultTextArea.setText(result.toString());
        }
    }

    @FXML
    protected void onAddStudyProgramToUniversityButtonClick() {
        String universityName = universityNameInput.getText();
        String studyProgramToAdd = studyProgramToAddInput.getText();


        boolean result =
                universityService.addStudyProgram(universities, universityName, studyProgramToAdd);

        if(result == false) {
            alert.show(Alert.AlertType.WARNING, "Something went wrong", "Please check university name");
            return;
        }

        if(studyProgramToAdd == null || containsDigits(studyProgramToAdd)) {
            alert.show(Alert.AlertType.WARNING, "Validation error", "Please check program name");
            return;
        }

        addUniversitiesToTable();
        resultTextArea.setText(universities.toString());
    }

    @FXML
    protected void onDeleteStudyProgramFromUniversityButtonClick() {
        String universityName = universityNameInput.getText();
        String studyProgramToDelete = studyProgramToAddInput.getText();

        boolean result =
                universityService.removeStudyProgram(universities, universityName, studyProgramToDelete);

        if(result == false) {
            alert.show(Alert.AlertType.WARNING, "Something went wrong", "Please check university name");
            return;
        }

        addUniversitiesToTable();
        resultTextArea.setText(universities.toString());
    }

    @FXML
    protected void onWriteToFileButtonClick() {
        try {
            universityService.writeToFile(universities, PATH_TO_OUTPUT_FILE, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        alert.show(Alert.AlertType.INFORMATION, "Success", "Saved to file successfully");
    }

    @FXML
    protected void onReadFromFileButtonClick() {
        List<University> universitiesFromFile;
        try {
            universitiesFromFile = universityService.readFromFile(PATH_TO_INPUT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } catch (RuntimeException e) {
            alert.show(Alert.AlertType.WARNING, "Something went wrong", "Bad file format");
            return;
        }

        universities.clear();
        universities.addAll(universitiesFromFile);
        alert.show(Alert.AlertType.INFORMATION, "Success", "Read from file successfully");
        addUniversitiesToTable();
    }

    @FXML
    private void onInfoButtonClick() throws IOException {
        openWindow("info.fxml", "Inforamtion");
    }


    private void openWindow(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource(fxmlFile));
        Parent root = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setTitle(title);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private boolean containsDigits(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
}