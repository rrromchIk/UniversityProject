package com.university.alerts;

import com.university.main.MyApplication;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MyAlert extends Alert {
    public static final AlertType WARNING = AlertType.WARNING;
    public static final AlertType ERROR = AlertType.ERROR;
    public static final AlertType INFORMATION = AlertType.INFORMATION;

    public MyAlert() {
        super(AlertType.INFORMATION);
    }

    public MyAlert(String title) {
        super(AlertType.INFORMATION);
        super.setTitle(title);
        ((Stage)super.getDialogPane()
                .getScene()
                .getWindow())
                .getIcons()
                .add(new Image(MyApplication.ICON_PATH));
    }

    public void show(AlertType type, String headerText, String bodyText) {
        //super - parent class
        super.setAlertType(type);
        super.setHeaderText(headerText);
        super.setContentText(bodyText);

        super.showAndWait();
    }
}
