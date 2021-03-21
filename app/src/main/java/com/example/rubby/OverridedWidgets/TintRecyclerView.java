package com.example.rubby.OverridedWidgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.rubby.Other.Methods;

public class TintRecyclerView extends RecyclerView {


    int toolbarElevationDp = Methods.dpToPx(4,getContext());
    public int scrollY;
    public boolean hide;
    public OnScrollListener scrollListener;

    public TintRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TintRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setElevation(final ConstraintLayout toolbarLayout){
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                scrollY += dy;
                float scrollInt =  (float) scrollY/50;
                if(scrollInt < toolbarElevationDp)
                    toolbarLayout.setElevation(scrollInt);
                else if(toolbarLayout.getElevation() != toolbarElevationDp)
                    toolbarLayout.setElevation(toolbarElevationDp);

            }
        };
        this.setOnScrollListener(scrollListener);

    }

    public void setElevationHideFab(final ConstraintLayout toolbarLayout, final FloatingActionButton floatingActionButton){
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0 || hide) {
                    floatingActionButton.hide();
                    hide = false;
                }else
                    floatingActionButton.show();

                scrollY += dy;
                float scrollInt =  (float) scrollY/50;
                if(scrollInt < toolbarElevationDp)
                    toolbarLayout.setElevation(scrollInt);
                else if(toolbarLayout.getElevation() != toolbarElevationDp)
                    toolbarLayout.setElevation(toolbarElevationDp);

            }
        };
        this.setOnScrollListener(scrollListener);

    }

    public void hideFab(final FloatingActionButton floatingActionButton){
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0 || hide) {
                    floatingActionButton.hide();
                    hide = false;
                }else
                    floatingActionButton.show();

            }
        };
        this.setOnScrollListener(scrollListener);

    }

}
