module com.university {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.university.main to javafx.fxml;
    opens com.university.controllers to javafx.fxml;

    exports com.university.controllers;
    exports com.university.models;
    exports com.university.service;
    exports com.university.main;


}

