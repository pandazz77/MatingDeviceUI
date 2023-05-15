package com.pandazz.matingdeviceui.scenes;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class RsClientsScene {
    private FlowPane root = new FlowPane();
    private Scene scene;
    private Stage stage;
    public RsClientsScene(Map<String, Object> com_in_map, Map<String, Object> com_out_map ){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = new Stage();
            }
        });

        this.scene = new Scene(this.root,300,150);
        HBox clients = new HBox();
        VBox clients_udp = this.get_clients_list(com_in_map,"COM port IN:");
        VBox clients_device = this.get_clients_list(com_out_map,"COM port OUT:");
        clients.getChildren().addAll(clients_udp,clients_device);
        clients.setSpacing(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(clients);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.setTitle("RS clients");
                stage.setResizable(false);
                stage.setHeight(root.getHeight());
                stage.show();
            }
        });

    }
    private VBox get_clients_list(Map<String,Object> map, String header){
        VBox result = new VBox();
        result.setSpacing(5);
        Text text_header = new Text(header);
        text_header.setFont(new Font(15));
        result.getChildren().add(text_header);
        for(Map.Entry<String, Object> item : map.entrySet()){
            String client_name = item.getKey();
            String client_address = (String) item.getValue();
            result.getChildren().add(new Text(String.format("%s : %s",client_name,client_address)));
        }
        return result;
    }
}
