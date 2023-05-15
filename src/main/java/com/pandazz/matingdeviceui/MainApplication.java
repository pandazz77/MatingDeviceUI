package com.pandazz.matingdeviceui;

import com.pandazz.matingdeviceui.socket.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("MatingDeviceUI");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        MainController controller = fxmlLoader.getController();

        Client udp_client = new Client("127.0.0.1",5001,5003,controller);
        udp_client.start();
    }

    public static void main(String[] args) throws IOException {
        launch();
        //Client udp_client = new Client("127.0.0.1",5001,5010);
        //udp_client.start();
    }
}