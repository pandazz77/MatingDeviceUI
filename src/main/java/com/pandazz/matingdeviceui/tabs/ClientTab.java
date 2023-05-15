package com.pandazz.matingdeviceui.tabs;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ClientTab {
    public VBox vbox_content;
    private Text client_name;
    private Text address;
    private Tab tab;
    private GridPane data_grid = new GridPane();
    private TextField data_values[];
    private Circle indicator = new Circle(7, Paint.valueOf("red"));
    public ClientTab(TabPane tab_pane, String tab_name ,String client_name, String address, String data_promts[]) {
        this.data_values = new TextField[data_promts.length];
        this.client_name = new Text(client_name);
        this.client_name.setFont(new Font(20));

        Text address_promt = new Text("Client address:");
        address_promt.setFont(new Font(15));
        this.address = new Text(address);
        this.address.setFont(new Font(15));

        HBox client_address = new HBox(address_promt, this.address);
        client_address.setSpacing(20);

        this.tab = new Tab(tab_name);
        this.indicator.setStroke(Paint.valueOf("black"));
        this.tab.setGraphic(indicator);
        this.vbox_content = new VBox(
                this.client_name,
                client_address,
                this.data_grid

        );
        vbox_content.setSpacing(5);
        vbox_content.setPadding(new Insets(10, 10, 10, 10));

        // forming data grid
        this.data_grid.setPadding(new Insets(10,10,10,10));
        this.data_grid.setStyle("-fx-border-color: black");
        int i = 0, c = 0;
        while(c<data_values.length){
            for (int j = 0; j < 3; j++) { // column
                if(c>=data_values.length) break;
                data_values[c] = new TextField();
                VBox cell = new VBox(
                        new Text(data_promts[c]),
                        data_values[c]
                );
                data_values[c].setText("NULL");
                data_values[c].setEditable(false);
                cell.setPadding(new Insets(5,5,5,5));
                cell.setSpacing(5);
                this.data_grid.add(cell,j,i);
                c++;
            }
            i++;
        }


        this.tab.setContent(vbox_content);
        tab_pane.getTabs().add(tab);
    }
    public void set_value(int index,String value){
        this.data_values[index].setText(value);
    }

    public void set_indicator(boolean status){
        if(status) this.indicator.setFill(Paint.valueOf("#56f260")); // green
        else this.indicator.setFill(Paint.valueOf("red"));
    }

}
