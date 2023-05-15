package com.pandazz.matingdeviceui.scenes;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class FrequencyScene {
    private FlowPane root = new FlowPane();
    private Scene scene;
    private Stage stage;
    public FrequencyScene(int interface_fr, int ethernet_fr, int rs_fr){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = new Stage();
            }
        });
        this.scene = new Scene(this.root,300,150);

        Text interface_frequency = new Text(String.format("Interface frequency: %d",interface_fr));
        interface_frequency.setFont(new Font(15));
        Text ethernet_frequency = new Text(String.format("Ethernet device frequency: %d",ethernet_fr));
        ethernet_frequency.setFont(new Font(15));
        Text rs_frequency = new Text(String.format("RS device frequency: %d",rs_fr));
        rs_frequency.setFont(new Font(15));

        VBox vbox = new VBox(
                interface_frequency,
                ethernet_frequency,
                rs_frequency
        );
        vbox.setSpacing(10);

        this.root.getChildren().add(vbox);
        this.root.setAlignment(Pos.CENTER);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.setTitle("Clients frequency");
                stage.setResizable(false);
                stage.setHeight(root.getHeight());
                stage.setWidth(root.getWidth());
                stage.show();
            }
        });
    }

}
