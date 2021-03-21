package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class TitleButtonHeaderFragment extends SmoothFragment {

    public TextView button;
    public TextView title;
    public ConstraintLayout constraintLayout;

    public onCreateTitleButtonHeaderCallBack onCreateCallBack;

    public interface onCreateTitleButtonHeaderCallBack {
        void onCreateTitleButtonHeader();
    }

    public void registerOnCreateCallBack(onCreateTitleButtonHeaderCallBack callback) {
        this.onCreateCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.title_button_header, container, false);
        constraintLayout = (ConstraintLayout) v.findViewById(R.id.titleButtonHeaderLayout);
        button = (TextView) v.findViewById(R.id.titleButtonHeaderButton);
        title = (TextView) v.findViewById(R.id.titleButtonHeaderTitle);
        onCreateCallBack.onCreateTitleButtonHeader();

        return v;


    }
}
