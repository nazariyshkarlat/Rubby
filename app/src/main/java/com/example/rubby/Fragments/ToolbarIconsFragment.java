package com.example.rubby.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class ToolbarIconsFragment extends SmoothFragment {

    public ImageView firstIcon;
    public ImageView secondIcon;
    public ImageView thirdIcon;
    private int firstIconResource;
    private int secondIconResource;
    private int thirdIconResource;

    public onCreateFragmentCallBack onCreateFragmentCallBack;

    public interface onCreateFragmentCallBack {
        void onCreateFragment();
    }

    public void registerOnCreateFragmentClickCallBack(onCreateFragmentCallBack callback) {
        this.onCreateFragmentCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.icons_layout, container, false);
        firstIcon = v.findViewById(R.id.iconsLayoutFirstIcon);
        secondIcon = v.findViewById(R.id.iconsLayoutSecondIcon);
        thirdIcon = v.findViewById(R.id.iconsLayoutThirdIcon);

        onCreateFragmentCallBack.onCreateFragment();

        return v;
    }

    public void setIcons(int firstIconResource, int secondIconResource, int thirdIconResource, View.OnClickListener firstOnClickListener,View.OnClickListener secondOnClickListener,View.OnClickListener thirdOnClickListener){

        this.firstIconResource = firstIconResource;
        this.secondIconResource = secondIconResource;
        this.thirdIconResource = thirdIconResource;

        this.firstIcon.setOnClickListener(firstOnClickListener);
        if(secondOnClickListener != null)
            this.secondIcon.setOnClickListener(secondOnClickListener);
        if(thirdOnClickListener != null)
            this.thirdIcon.setOnClickListener(thirdOnClickListener);

        firstIcon.setImageResource(firstIconResource);
        if(secondIconResource != 0)
            secondIcon.setImageResource(secondIconResource);
        else
            secondIcon.setVisibility(View.GONE);

        if(thirdIconResource != 0)
            thirdIcon.setImageResource(thirdIconResource);
        else
            thirdIcon.setVisibility(View.GONE);

    }

}
