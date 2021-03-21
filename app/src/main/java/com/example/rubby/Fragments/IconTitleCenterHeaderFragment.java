package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class IconTitleCenterHeaderFragment extends SmoothFragment {

    public ConstraintLayout layout;
    public ImageView icon;
    public TextView title;

    public onCreateIconTitleCenterHeaderCallBack onCreateCallBack;

    public interface onCreateIconTitleCenterHeaderCallBack {
        void onCreateIconTitleCenterHeader();
    }

    public void registerOnCreateCallBack(onCreateIconTitleCenterHeaderCallBack callback){
        this.onCreateCallBack = callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.icon_title_center_header, container, false);
        layout = (ConstraintLayout) v.findViewById(R.id.iconTitleHeaderLayout);
        icon = (ImageView) v.findViewById(R.id.iconTitleHeaderIcon);
        title = (TextView) v.findViewById(R.id.iconTitleHeaderTitle);
        onCreateCallBack.onCreateIconTitleCenterHeader();
        return v;
    }

}
