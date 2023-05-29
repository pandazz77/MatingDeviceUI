package com.pandazz.matingdeviceui.tabs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class MessageHistory {
    private ScrollPane scroll_pane;
    private Pane content_pane;
    private TextArea text_area = new TextArea();
    private int max_content_size = 100000; // if history reach this = > history will be sliced
    private IndexRange clear_range = new IndexRange(0,max_content_size/10);
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
                if(text_area.getLength() > max_content_size) {
                    text_area.replaceText(clear_range,"");
                    System.out.println("MESSAGE HISTORY: max content size reached, part of content was dropped.");
                }
                text_area.appendText(finalData);
            }});
    }

    public void clear(){
        this.text_area.clear();
    }
}
