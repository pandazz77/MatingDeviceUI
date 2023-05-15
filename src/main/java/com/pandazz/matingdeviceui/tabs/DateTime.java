package com.pandazz.matingdeviceui.tabs;

import javafx.application.Platform;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime extends Thread{
    private Text date_and_time;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss / dd.MM.yyyy");
    public DateTime(Text date_and_time){
        this.date_and_time = date_and_time;
    }

    public void run() {
        while(true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    LocalDateTime now = LocalDateTime.now();
                    date_and_time.setText(dtf.format(now));
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
