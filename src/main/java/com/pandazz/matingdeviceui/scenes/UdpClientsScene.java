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
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

public class UdpClientsScene{
    private FlowPane root = new FlowPane();
    private Scene scene;
    private Stage stage;
    public UdpClientsScene(Map<String, Object> clients_udp_map, Map<String, Object> clients_device_map) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage = new Stage();
            }
        });
        this.scene = new Scene(this.root,300,150);
        HBox clients = new HBox();
        VBox clients_udp = this.get_clients_list(clients_udp_map,"UDP clients:");
        VBox clients_device = this.get_clients_list(clients_device_map,"DEVICE clients:");
        clients.getChildren().addAll(clients_udp,clients_device);
        clients.setSpacing(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(clients);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.setScene(scene);
                stage.setTitle("UDP clients");
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
            ArrayList<Object> client_data = (ArrayList<Object>) item.getValue();
            String client_ip = (String) client_data.get(0);
            String client_port = client_data.get(1).toString();
            result.getChildren().add(new Text(String.format("%s - %s:%s",client_name,client_ip,client_port)));
        }
        return result;
    }
}
