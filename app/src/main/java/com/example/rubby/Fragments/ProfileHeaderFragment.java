package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class ProfileHeaderFragment extends SmoothFragment {

    public ImageView awa;
    public ConstraintLayout constraintLayout;

    public onCreateProfileHeaderCallBack onCreateCallBack;

    public interface onCreateProfileHeaderCallBack {
        void onCreateProfileHeader();
    }

    public void registerOnCreateCallBack(onCreateProfileHeaderCallBack callback){
        this.onCreateCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.profile_header,container,false);
        awa = (ImageView) v.findViewById(R.id.profileHeaderAwa);
        constraintLayout = (ConstraintLayout) v.findViewById(R.id.profileHeaderLayout);
        try {
            onCreateCallBack.onCreateProfileHeader();
        }catch (Exception e){}

        return v;

    }
}
