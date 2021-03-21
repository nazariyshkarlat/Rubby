package com.example.rubby.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationCityModel {

    private static ArrayList<LocationCityModel> ukraineList = new ArrayList<>(Arrays.asList(new LocationCityModel("Харьков"),new LocationCityModel("Богодухов"),new LocationCityModel("Донецк")));
    private static ArrayList<LocationCityModel> russianList = new ArrayList<>(Arrays.asList(new LocationCityModel("Москва"),new LocationCityModel("Тюмень"), new LocationCityModel("Санкт-Петербург")));
    private String title;
    private boolean isSelected = false;
    public int countryKey;

    public LocationCityModel(String title){

        this.title = title;

    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public static ArrayList<LocationCityModel> getAllList(int countryKey){
        if(countryKey == 0)
            return ukraineList;
        else
            return  russianList;

    }

}
