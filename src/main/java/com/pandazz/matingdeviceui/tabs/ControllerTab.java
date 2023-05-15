package com.pandazz.matingdeviceui.tabs;

import com.pandazz.matingdeviceui.socket.Client;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ControllerTab extends ClientTab{
    private Client udp_client;
    public ControllerTab(TabPane tab_pane, String tab_name, String client_name, String address, String[] data_promts, Client udp_client) {
        super(tab_pane, tab_name, client_name, address, data_promts);
        this.udp_client = udp_client;
        HBox get_buttons = new HBox();
        Button get_udp_clients = new Button("GET UDP CLIENTS");
        Button get_rs_clients = new Button("GET RS CLIENTS");
        Button get_frequency = new Button("GET FREQUENCY");

        get_buttons.getChildren().addAll(get_udp_clients,get_rs_clients,get_frequency);
        get_buttons.setSpacing(10);
        get_buttons.setAlignment(Pos.TOP_CENTER);

        get_udp_clients.setOnAction(actionEvent -> {
            try {
                udp_client.send_data("{\"type\":\"get_udp_clients\"}");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        get_rs_clients.setOnAction(actionEvent -> {
            try {
                udp_client.send_data("{\"type\":\"get_rs_clients\"}");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        get_frequency.setOnAction(actionEvent -> {
            try {
                udp_client.send_data("{\"type\":\"get_frequency\"}");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.vbox_content.getChildren().add(get_buttons);
    }

}
