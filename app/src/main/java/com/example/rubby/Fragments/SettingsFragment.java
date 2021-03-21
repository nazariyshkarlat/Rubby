package com.example.rubby.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.Activities.AppCompatActivities.HomeActivity;
import com.example.rubby.Activities.AppCompatActivities.MoreActivity;
import com.example.rubby.Activities.AppCompatActivities.NetworkActivity;
import com.example.rubby.Activities.AppCompatActivities.NotificationsActivity;
import com.example.rubby.Activities.AppCompatActivities.PersonalizationActivity;
import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.Activities.AppCompatActivities.StorageActivity;
import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.OverridedWidgets.TintNestedScrollView;
import com.example.rubby.Database.SettingsDatabase;
import com.example.rubby.R;

public class SettingsFragment extends SmoothFragment {

    private Context context;
    private String[] titleS = {"Аккаунт", "Безопасность", "Персонализация", "Уведомления", "Сеть", "Хранилище", "Промоакции", "Другое", "Справка", "Поддержка"};
    private String[] subtitleS = {"Профиль, личные данные...", "Пароль, история активности...",  "Тема, язык, анимации","Настройка уведомлений", "Экономия траффика, данные", "Сохранение файлов","Промоакции, способы оплаты","Конфиденциальность, условия польз...", null , null, };
    private int[] iconsI = {R.drawable.ic_outline_person_87_24dp, R.drawable.ic_outline_security_87_24dp, R.drawable.ic_outline_tune_87_24dp, R.drawable.ic_outline_notifications_87_24dp, R.drawable.ic_outline_wifi_87_24dp, R.drawable.ic_outline_storage_87_24dp, R.drawable.ic_outline_open_in_new_87_24dp, R.drawable.ic_outline_more_horiz_87_24dp, R.drawable.ic_round_help_outline_87_24dp, R.drawable.ic_outline_contact_support_87_24dp};
    public static int selectedPos;
    private ConstraintSet titleSet = new ConstraintSet();
    private SettingsDatabase settingsDatabase;
    private SQLiteDatabase database;
    private ContentValues contentValues = new ContentValues();
    private Cursor cursor;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TintNestedScrollView tintNestedScrollView;
    private ProfileHeaderFragment profileHeaderFragment = new ProfileHeaderFragment();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.nested_recycler_view, container, false);
        if(!PersonalizationActivity.recreated)
            getChildFragmentManager().beginTransaction().add(R.id.nestedRecyclerFrameLayout,profileHeaderFragment,null).commit();
        recyclerView = (RecyclerView) v.findViewById(R.id.nestedRecyclerView);
        tintNestedScrollView = (TintNestedScrollView) v.findViewById(R.id.tintNestedScrollView);
        tintNestedScrollView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        context = getContext();
        settingsDatabase = new SettingsDatabase(context);
        database = settingsDatabase.getWritableDatabase();
        cursor = database.query(SettingsDatabase.DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst()){
            contentValues.put(SettingsDatabase.ANIMATION_SWITCH,0);
            contentValues.put(SettingsDatabase.TRAFFIC_SAVING_SWITCH,0);
            contentValues.put(SettingsDatabase.SAVE_PLACE_SWITCH,0);
            database.insert(SettingsDatabase.DATABASE_TABLE, null, contentValues);
        }
        cursor = database.query(SettingsDatabase.DATABASE_TABLE, null, null, null, null, null, null);
        cursor.moveToFirst();
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new SettingsFragment.SettingsFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        recyclerView.setPadding(0, Methods.dpToPx(2,getContext()),0,Methods.dpToPx(18,getContext()));
        tintNestedScrollView.setElevation(HomeActivity.toolbarLayout);
        return v;

    }

    private class SettingsFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private TextView subtitle;
        private ConstraintLayout layout;

        public SettingsFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.icon_title_subtitle, parent, false));
            title = (TextView) itemView.findViewById(R.id.iconTitleSubtitleItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.iconTitleSubtitleItemSubtitle);
            icon = (ImageView) itemView.findViewById(R.id.iconTitleSubtitleItemIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.iconTitleSubtitleItemLayout);
            titleSet.clone(layout);
            titleSet.centerVertically(title.getId(), layout.getId());

        }

    }

    private class SettingsFragmentAdapter extends RecyclerView.Adapter<SettingsFragment.SettingsFragmentHolder> {

        @NonNull
        @Override
        public SettingsFragment.SettingsFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SettingsFragment.SettingsFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final SettingsFragment.SettingsFragmentHolder holder, final int position) {

            holder.icon.setImageResource(iconsI[position]);
            holder.title.setText(titleS[position]);
            holder.subtitle.setText(subtitleS[position]);

            if(position == 8 || position == 9){
                titleSet.applyTo(holder.layout);
                holder.subtitle.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == 0){
                        Intent intent = new Intent(getActivity(), AccountEditActivity.class);
                        startActivity(intent);
                    }else if(position == 1){
                        Intent intent = new Intent(getActivity(), SecurityActivity.class);
                        startActivity(intent);
                    }else if(position == 2){
                        Intent intent = new Intent(getActivity(), PersonalizationActivity.class);
                        PersonalizationActivity.homeActivity = getActivity();
                        PersonalizationActivity.recreated = false;
                        startActivity(intent);
                    }else if(position == 3){
                        Intent intent = new Intent(getActivity(), NotificationsActivity.class);
                        startActivity(intent);
                    }else if(position == 4){
                        Intent intent = new Intent(getActivity(), NetworkActivity.class);
                        startActivity(intent);
                    }else if(position == 5){
                        Intent intent = new Intent(getActivity(), StorageActivity.class);
                        startActivity(intent);
                    }else if(position == 6){
                        Intent intent = new Intent(getActivity(), PromotionsActivity.class);
                        startActivity(intent);
                    } else if(position == 7){
                        Intent intent = new Intent(getActivity(), MoreActivity.class);
                        startActivity(intent);
                    }
                }
            });


        }
        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

}
