package com.pandazz.matingdeviceui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.pandazz.matingdeviceui.socket.Client;
import com.pandazz.matingdeviceui.tabs.ClientTab;
import com.pandazz.matingdeviceui.tabs.ControllerTab;
import com.pandazz.matingdeviceui.tabs.DateTime;
import com.pandazz.matingdeviceui.tabs.MessageHistory;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MainController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text date_and_time;

    @FXML
    private Pane messsage_history_pane;

    @FXML
    private TabPane tabpane;

    private MessageHistory message_history;

    private HashMap<String, ClientTab> client_tabs = new HashMap<>();

    @FXML
    void initialize() throws IOException {
        assert date_and_time != null : "fx:id=\"date_and_time\" was not injected: check your FXML file 'main-view.fxml'.";
        assert messsage_history_pane != null : "fx:id=\"messsage_history_pane\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tabpane != null : "fx:id=\"tabpane\" was not injected: check your FXML file 'main-view.fxml'.";
        this.message_history = new MessageHistory(this.messsage_history_pane);
        DateTime date_time_thread = new DateTime(date_and_time);
        date_time_thread.start();

    }

    public void create_tab(String tab_name, String client_name, String address, String[] data_names, String tab_identificator, Client udp_client){
        if(this.client_tabs.containsKey(tab_name)) return;
        ClientTab new_tab = null;
        if(tab_identificator=="client") {
            new_tab = new ClientTab(this.tabpane, tab_name,client_name,address,data_names);
        } else if (tab_identificator=="controller") {
            new_tab = new ControllerTab(this.tabpane, tab_name, client_name, address, data_names,udp_client);
        }
        this.client_tabs.put(tab_name,new_tab);
    }

    public void create_tab(String tab_name, String client_name, String address, String[] data_names, String tab_identificator){
        this.create_tab(tab_name, client_name, address, data_names, tab_identificator, null);
    }

    public void set_tab_value(String tab_name,int index, String value){
        this.client_tabs.get(tab_name).set_value(index,value);
    }

    public void set_tab_indicator(String tab_name, boolean status){
        this.client_tabs.get(tab_name).set_indicator(status);
    }

    public void update_message_history(String new_data){
        this.message_history.update(new_data);
    }
}