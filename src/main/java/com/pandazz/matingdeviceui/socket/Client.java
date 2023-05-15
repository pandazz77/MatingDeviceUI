package com.pandazz.matingdeviceui.socket;

import com.pandazz.matingdeviceui.MainController;
import com.pandazz.matingdeviceui.parsers.NmeaParser;
import com.pandazz.matingdeviceui.parsers.ProtocolParser;
import com.pandazz.matingdeviceui.scenes.FrequencyScene;
import com.pandazz.matingdeviceui.scenes.RsClientsScene;
import com.pandazz.matingdeviceui.scenes.UdpClientsScene;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class Client extends Thread{
    private final DatagramSocket socket;
    private final InetAddress ENDPOINT_IP;
    private final int ENDPOINT_PORT;
    private final int CLIENT_PORT;
    private final MainController controller;
    public Client(String ENDPOINT_IP, int ENDPOINT_PORT, int CLIENT_PORT, MainController controller) throws IOException{
        this.socket = new DatagramSocket(CLIENT_PORT);
        this.ENDPOINT_IP = InetAddress.getByName(ENDPOINT_IP);
        this.ENDPOINT_PORT = ENDPOINT_PORT;
        this.CLIENT_PORT = CLIENT_PORT;
        this.controller = controller;

    }

    // start thread
    public void run(){
        byte[] data_buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data_buffer, data_buffer.length);
        while(true){
            try {
                //System.out.println("Waiting for data");
                this.socket.receive(packet);
                String received_data = new String(packet.getData(),packet.getOffset(),packet.getLength());
                System.out.println("<"+received_data);
                this.handle_data(received_data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handle_data(String received_data){
        //received_data.replace("null","");
        try {
            JSONObject jo = new JSONObject(received_data);
            String type = (String) jo.get("type");
            String client_name, message, client_address, date_time;
            switch(type){
                case("last_message"):
                    message = (String) jo.get("message");
                    client_name = (String) jo.get("client_name");
                    client_address = (String) jo.get("client_address");
                    date_time = (String) jo.get("date_time");
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            controller.update_message_history(String.format("%s %s\n",date_time,message));
                            controller.create_tab(client_name,client_name,client_address, NmeaParser.get_data_names(message),"client");
                            String[] data_values = NmeaParser.get_data_values(message);
                            for(int i=0;i<data_values.length;i++){
                                controller.set_tab_value(client_name,i,data_values[i]);
                            }
                            controller.set_tab_indicator(client_name,true);
                        }
                    });

                    break;
                case("protokol_message"):
                    client_name = (String) jo.get("client_name");
                    message = (String) jo.get("message");
                    client_address = (String) jo.get("client_address");
                    date_time = (String) jo.get("date_time");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.update_message_history(String.format("%s %s\n",date_time,message));
                            controller.create_tab(client_name, client_name, client_address, ProtocolParser.data_names,"controller", Client.this);
                            String[] data_values = ProtocolParser.get_data_values(message);
                            for(int i=0;i<data_values.length;i++){
                                controller.set_tab_value(client_name,i,data_values[i]);
                            }
                            controller.set_tab_indicator(client_name,true);
                        }
                    });
                    break;
                case("clients_udp"):
                    JSONObject clients_sensor = (JSONObject) jo.get("clients_sensor");
                    JSONObject clients_device = (JSONObject) jo.get("clients_device");
                    new UdpClientsScene(clients_sensor.toMap(),clients_device.toMap());
                    break;
                case("clients_rs"):
                    JSONObject comport_in = (JSONObject) jo.get("comport_in");
                    JSONObject comport_out = (JSONObject) jo.get("comport_out");
                    new RsClientsScene(comport_in.toMap(),comport_out.toMap());
                    break;
                case("clients_frequency"):
                    int interface_fr = jo.getInt("interface");
                    int ethernet_fr = jo.getInt("ethernet_device");
                    int rs_ft = jo.getInt("rs_device");
                    new FrequencyScene(interface_fr,ethernet_fr,rs_ft);

            }
        } catch (JSONException e) { // received data not JSON

        }
    }

    public void send_data(String data) throws IOException {
        byte[] data_buffer = new byte[1024];
        data_buffer = data.getBytes();
        DatagramPacket packet = new DatagramPacket(data_buffer, data_buffer.length,this.ENDPOINT_IP,this.ENDPOINT_PORT);
        System.out.println(">"+data);
        this.socket.send(packet);
    }

    public void close(){
        this.socket.close();
    }
}
