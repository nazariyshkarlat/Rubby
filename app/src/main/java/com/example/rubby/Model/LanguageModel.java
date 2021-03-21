package com.example.rubby.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageModel {

    private String title;
    private String subtitle;
    private static ArrayList<LanguageModel> allList = new ArrayList<>(Arrays.asList(new LanguageModel("Русский", "Русский"), new LanguageModel("Українська", "Украиснкий"), new LanguageModel("English", "Английский"), new LanguageModel("Deutsch", "Немецкий")));
    private boolean isSelected = false;

    public LanguageModel(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle(){
        return this.title;
    }

    public String getSubtitle(){
        return this.subtitle;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public static ArrayList<LanguageModel> addAll(){
      return allList;
    }

}
