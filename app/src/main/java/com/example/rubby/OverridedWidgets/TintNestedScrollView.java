package com.example.rubby.OverridedWidgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.rubby.Other.Methods;

public class TintNestedScrollView extends NestedScrollView {

    int toolbarElevationDp = Methods.dpToPx(4,getContext());

    public TintNestedScrollView(@NonNull Context context) {
        this(context, (AttributeSet)null);
    }

    public TintNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setElevation(final ConstraintLayout toolbarLayout){
        this.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float scrollInt =  (float) scrollY/50;
                if(scrollInt < toolbarElevationDp)
                    toolbarLayout.setElevation(scrollInt);
                else if(toolbarLayout.getElevation() != toolbarElevationDp)
                    toolbarLayout.setElevation(toolbarElevationDp);

            }
        });
    }

    public void magnetizeTint(final ConstraintLayout toolbarLayout, final FrameLayout frameLayout, final int tintMargin){
        this.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY >= tintMargin){
                        if (toolbarLayout.getElevation() == 0) {
                            toolbarLayout.setElevation(toolbarElevationDp);
                            frameLayout.setElevation(0);
                        }
                    }else {
                    if (frameLayout.getElevation() == 0) {
                        toolbarLayout.setElevation(0);
                        frameLayout.setElevation(toolbarElevationDp);
                    }
                }
            }
        });
    }

}
