package com.example.rubby.Model;

public class HistoryModel {

    public int ID;
    public String deviceName;
    public int deviceType;
    public String location;
    public String time;
    public String MAC;
    public String IP;
    public String TIME_RANGE;

    public final static int TYPE_PHONE_IOS = 0;
    public final static int TYPE_PHONE_ANDROID = 1;
    public final static int TYPE_PHONE_NO_NAME = 2;
    public final static int TYPE_TABLET_IOS = 3;
    public final static int TYPE_TABLET_ANDROID = 4;
    public final static int TYPE_TABLET_NO_NAME = 5;
    public final static int TYPE_DESKTOP_MAC = 6;
    public final static int TYPE_DESKTOP_WINDOWS = 7;
    public final static int TYPE_LAPTOP_MAC = 8;
    public final static int TYPE_LAPTOP_WINDOWS = 9;

    public HistoryModel(int ID, String deviceName, int deviceType, String location, String time, String MAC, String IP, String timeRange){
        this.ID = ID;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.location = location;
        this.time = time;
        this.MAC = MAC;
        this.IP = IP;
        this.TIME_RANGE = timeRange;
    }

}
