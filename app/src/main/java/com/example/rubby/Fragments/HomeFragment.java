package com.example.rubby.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class HomeFragment extends SmoothFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_layout, container, false);
    }


}
