package com.pandazz.matingdeviceui.parsers;

public class NmeaParser {
    public static final String[] months = {"января","февраля","марта","апреля","мая","июня","июля","августа","сентября","октября","ноября","декабря"};
    public static final String[] rmc_data_names = {"UTC время","Статус","Широта","Долгота","Скорость(узлы)","Направление","Дата","Магнитные вариации"};
    public static final String[] zda_data_names = {"UTC время","День","Месяц","Год","GMT смещение(час)","GMT смещение(мин)"};
    public static final String[] vtg_data_names = {"Направление(N)","Направление(M)","Скорость(узлы)","Скорость(км/ч)"};
    public static final String[] gll_data_names = {"Широта","Долгота","Время определения","Статус"};
    public static String[] get_fields(String nmea_message){
        nmea_message = nmea_message.split("\\*")[0];
        return nmea_message.split(",");
    }
    public static String[] get_rmc(String nmea_message){
        String[] fields = get_fields(nmea_message);
        //for(int i=0;i< fields.length;i++) System.out.println(fields[i]);
        String UTC_TIME = String.format("%s:%s:%s",fields[1].substring(0,2),fields[1].substring(2,4),fields[1].substring(4,6));
        String STATUS = fields[2];
        String LATITUDE = fields[3]+','+fields[4];
        String LONGITUDE = fields[5]+','+fields[6];
        String KNOT_SPEED = fields[7];
        String HEADING = fields[8];
        String DATE = fields[9].substring(0,2) + ' ' + months[Integer.parseInt(fields[9].substring(2,4))] + " 20" + fields[9].substring(4,6);
        String MAGNETIC = fields[10]+ ',' + fields[11];
        return new String[]{UTC_TIME, STATUS, LATITUDE, LONGITUDE, KNOT_SPEED, HEADING, DATE, MAGNETIC};
    }

    public static String[] get_zda(String nmea_message){
        String[] fields = get_fields(nmea_message);
        String UTC_TIME = String.format("%s:%s:%s",fields[1].substring(0,2),fields[1].substring(2,4),fields[1].substring(4,6));
        String DAY = fields[2];
        String MONTH = fields[3];
        String YEAR = fields[4];
        String HOURS_OFFSET = fields[5];
        String MINUTES_OFFSET = fields[6];
        return new String[]{UTC_TIME,DAY,MONTH,YEAR,HOURS_OFFSET,MINUTES_OFFSET};
    }

    public static String[] get_vtg(String nmea_message){
        String[] fields = get_fields(nmea_message);
        String HEADING_N = fields[1];
        String HEADING_M = fields[3];
        String SPEED_KNOT = fields[5];
        String SPEED_KMH = fields[7];
        return new String[]{HEADING_N,HEADING_M,SPEED_KNOT,SPEED_KMH};
    }

    public static String[] get_gll(String nmea_message){
        String[] fields = get_fields(nmea_message);
        String LATITUDE = fields[1]+','+fields[2];
        String LONGITUDE = fields[3]+','+fields[4];
        String UTC_TIME = String.format("%s:%s:%s",fields[5].substring(0,2),fields[5].substring(2,4),fields[5].substring(4,6));
        String STATUS = fields[6];
        return new String[]{LATITUDE,LONGITUDE,UTC_TIME,STATUS};
    }

    public static String[] get_data_names(String nmea_message){
        String header = get_fields(nmea_message)[0];
        switch (header.substring(3)){
            case("RMC"):
                return rmc_data_names;
            case("ZDA"):
                return zda_data_names;
            case("VTG"):
                return vtg_data_names;
            case("GLL"):
                return gll_data_names;
            default:
                return new String[]{"NULL"};
        }
    }

    public static String[] get_data_values(String nmea_message){
        String header = get_fields(nmea_message)[0];
        switch (header.substring(3)){
            case("RMC"):
                return get_rmc(nmea_message);
            case("ZDA"):
                return get_zda(nmea_message);
            case("VTG"):
                return get_vtg(nmea_message);
            case("GLL"):
                return get_gll(nmea_message);
            default:
                return new String[]{"NULL"};
        }
    }
}
