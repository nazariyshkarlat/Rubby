package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class EmptyLayoutFragment extends SmoothFragment {

    public TextView title;
    public TextView subtitle;
    public TextView button;
    public ConstraintLayout layout;

    public onCreateEmptyLayoutCallBack onCreateCallBack;

    public interface onCreateEmptyLayoutCallBack {
        void onCreateEmptyLayout();
    }

    public void registerOnCreateCallBack(onCreateEmptyLayoutCallBack callback){
        this.onCreateCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.empty_layout_title_subtitle_layout, container, false);
        title = (TextView) v.findViewById(R.id.emptyLayoutTitle);
        subtitle = (TextView) v.findViewById(R.id.emptyLayoutSubtitle);
        button = (TextView) v.findViewById(R.id.emptyLayoutButton);
        layout = (ConstraintLayout) v.findViewById(R.id.emptyLayoutLayout);
        onCreateCallBack.onCreateEmptyLayout();

        return v;
    }

}
