package com.example.rubby.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationCountryModel {

    private String title;
    private boolean isSelected = false;
    public int key;
    public final static int KEY_UKRAINE = 0;
    public final static int KEY_RUSSIA = 1;
    private static ArrayList<LocationCountryModel> allList = new ArrayList<>(Arrays.asList(new LocationCountryModel("Украина",KEY_UKRAINE),new LocationCountryModel("Россия", KEY_RUSSIA)));

    public LocationCountryModel(String title,int key){

        this.title = title;
        this.key = key;

    }

    public int getKey() {
        return key;
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

    public static ArrayList<LocationCountryModel> getAllList(){
        return allList;
    }

}

