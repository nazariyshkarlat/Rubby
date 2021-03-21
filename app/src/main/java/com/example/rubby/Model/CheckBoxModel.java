package com.example.rubby.Model;

public class CheckBoxModel {

    private boolean isChecked;
    private int position;

    public CheckBoxModel(int position){
        this.position = position;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

    public boolean isChecked(){
        return this.isChecked;
    }

    public int getPosition(){
        return this.position;
    }

}
