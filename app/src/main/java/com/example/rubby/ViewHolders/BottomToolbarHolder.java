package com.example.rubby.ViewHolders;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rubby.R;

public class BottomToolbarHolder extends RecyclerView.ViewHolder{

    public ImageView icon;

    public BottomToolbarHolder(LayoutInflater inflater, ViewGroup parent, Resources resources, Context context){

        super(inflater.inflate(R.layout.toolbar_bottom_item,parent,false));
        icon = (ImageView) itemView.findViewById(R.id.bottomToolbarIcon);

    }

}