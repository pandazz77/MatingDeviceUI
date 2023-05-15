package com.pandazz.matingdeviceui.tabs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class MessageHistory {
    private ScrollPane scroll_pane;
    private Pane content_pane;
    private TextArea text_area = new TextArea();
    public MessageHistory(Pane content_pane){
        this.content_pane = content_pane;
        this.text_area.setPrefWidth(this.content_pane.getPrefWidth());
        this.text_area.setPrefHeight(this.content_pane.getPrefHeight());
        this.text_area.setEditable(false);
        this.scroll_pane = new ScrollPane(this.text_area);
        this.scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.scroll_pane.fitToHeightProperty().set(true);
        this.scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scroll_pane.setPrefWidth(this.content_pane.getPrefWidth());
        this.scroll_pane.setPrefHeight(this.content_pane.getPrefHeight());
        this.content_pane.getChildren().add(this.scroll_pane);
        this.text_area.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                text_area.setScrollTop(Double.MIN_VALUE);
            }
        });
    }

    public void update(String data){
        if(data.charAt(data.length()-1)!='\n') data+='\n';
        String finalData = data;
        Platform.runLater(new Runnable() {
            @Override public void run() {
                text_area.appendText(finalData);
            }});
    }

    public void clear(){
        this.text_area.clear();
    }
}
