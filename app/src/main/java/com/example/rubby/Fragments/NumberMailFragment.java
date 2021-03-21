package com.example.rubby.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.Other.Methods;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.NumberMailDatabase;
import com.example.rubby.R;

public class NumberMailFragment extends SmoothFragment {

    private int selectedPos;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private NumberMailDatabase numberMailDatabase;
    private SQLiteDatabase database;
    private Cursor cursor;
    private ContentValues contentValues = new ContentValues();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NumberMailFragment.NumberPostFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        numberMailDatabase = new NumberMailDatabase(SecurityActivity.context);
        database =  numberMailDatabase.getReadableDatabase();
        recyclerView.setPadding(0, Methods.dpToPx(8,getContext()),0,0);
        cursor = database.query(NumberMailDatabase.DATABASE_TABLE, null, null, null, null, null, null);
        cursor.moveToFirst();

        return v;
    }

    private class NumberPostFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ConstraintLayout layout;

        public NumberPostFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleItemSubtitle);

        }

    }

    private class NumberPostFragmentAdapter extends RecyclerView.Adapter<NumberMailFragment.NumberPostFragmentHolder> {

        @NonNull
        @Override
        public NumberMailFragment.NumberPostFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NumberMailFragment.NumberPostFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NumberMailFragment.NumberPostFragmentHolder holder, final int position) {
            if(position == 0){
                holder.title.setText("Номер телефона");
                holder.subtitle.setText(numberMailDatabase.getCodeNumber(cursor.getString(1)));
            }else if(position == 1){
                holder.title.setText("Электронный адрес");
                holder.subtitle.setText(numberMailDatabase.getCodeMail(cursor.getString(2)));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPos = holder.getLayoutPosition();
                    if(position == 0){
                        ChangePhoneFragment changePhoneFragment = new ChangePhoneFragment();
                        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,changePhoneFragment,null).commit();
                    }else if(position == 1){
                        ChangeMailFragment changeMailFragment = new ChangeMailFragment();
                        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,changeMailFragment,null).commit();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {

            return 2;

        }
    }

}
