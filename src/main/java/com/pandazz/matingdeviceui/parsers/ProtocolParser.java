package com.pandazz.matingdeviceui.parsers;

import static com.pandazz.matingdeviceui.parsers.NmeaParser.months;

public class ProtocolParser {
    public static String[] data_names = {"UTC время", "Дата", "Широта", "Долгота", "Скорость(узлы)", "Скорость(км/ч)", "Истинный курс"};
    public static String[] get_fields(String message){
        message = message.split("\\*")[0];
        return message.split(",");
    }

    public static String[] get_data_values(String message){
        String[] fields = get_fields(message);
        String UTC_TIME = String.format("%s:%s:%s",fields[1].substring(0,2),fields[1].substring(2,4),fields[1].substring(4,6));
        String DATE = fields[2].substring(0,2) + ' ' + months[Integer.parseInt(fields[2].substring(2,4))] + " 20" + fields[2].substring(4,6);
        String LATITUDE = fields[3]+','+fields[4];
        String LONGITUDE = fields[5]+','+fields[6];
        String SPEED_KNOT = fields[7];
        String SPEED_KMH = fields[9];
        String TRUE_HEADING = fields[11];
        return new String[]{UTC_TIME,DATE,LATITUDE,LONGITUDE,SPEED_KNOT,SPEED_KMH,TRUE_HEADING};
    }
}
