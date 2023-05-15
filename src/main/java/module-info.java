module com.pandazz.matingdeviceui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.pandazz.matingdeviceui to javafx.fxml;
    exports com.pandazz.matingdeviceui;
}