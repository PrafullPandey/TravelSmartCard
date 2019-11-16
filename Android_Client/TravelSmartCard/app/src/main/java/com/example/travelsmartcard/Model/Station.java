package com.example.travelsmartcard.Model;

public class Station {

    public int Station_ID ;
    public String Staion_Name ;

    public Station(int station_ID, String staion_Name) {
        Station_ID = station_ID;
        Staion_Name = staion_Name;
    }

    public int getStation_ID() {
        return Station_ID;
    }

    public void setStation_ID(int station_ID) {
        Station_ID = station_ID;
    }

    public String getStaion_Name() {
        return Staion_Name;
    }

    public void setStaion_Name(String staion_Name) {
        Staion_Name = staion_Name;
    }

    @Override
    public String toString() {
        return "Station{" +
                "Station_ID=" + Station_ID +
                ", Staion_Name='" + Staion_Name + '\'' +
                '}';
    }

}
