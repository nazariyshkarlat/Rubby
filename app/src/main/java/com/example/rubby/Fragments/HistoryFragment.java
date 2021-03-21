package com.example.rubby.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Other.Methods;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.OverridedWidgets.TintNestedScrollView;
import com.example.rubby.Database.HistoryDatabase;
import com.example.rubby.Model.HistoryModel;
import com.example.rubby.R;

import java.util.List;

public class HistoryFragment extends ChildFragment implements TitleButtonHeaderFragment.onCreateTitleButtonHeaderCallBack,EmptyLayoutFragment.onCreateEmptyLayoutCallBack {

    private List<HistoryModel> historyModels;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private HistoryDatabase historyDatabase;
    private EmptyLayoutFragment emptyLayoutFragment = new EmptyLayoutFragment();
    private SQLiteDatabase database;
    private int selectedPos;
    private TintNestedScrollView tintNestedScrollView;
    private TitleButtonHeaderFragment titleButtonHeaderFragment = new TitleButtonHeaderFragment();
    private Cursor cursor;
    private FrameLayout topFrameLayout;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.nested_recycler_view_frame_layout_center, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.nestedRecyclerViewFrameLayoutCenterRecyclerView);
        topFrameLayout = (FrameLayout) v.findViewById(R.id.nestedRecyclerViewFrameLayoutCenterTopFrameLayout);
        getChildFragmentManager().beginTransaction().add(R.id.nestedRecyclerViewFrameLayoutCenterCenterFrameLayout,emptyLayoutFragment,null).hide(emptyLayoutFragment).commit();
        topFrameLayout.setBackgroundColor(((SecurityActivity)getActivity()).colorsAndDrawables.page_background);
        topFrameLayout.setElevation(Methods.dpToPx(4, SecurityActivity.context));
        getChildFragmentManager().beginTransaction().add(R.id.nestedRecyclerFrameLayout,titleButtonHeaderFragment,null).commit();
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        tintNestedScrollView = (TintNestedScrollView) v.findViewById(R.id.nestedRecyclerViewFrameLayoutCenterNestedScrollView);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new HistoryFragment.HistoryFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        historyDatabase = new HistoryDatabase(SecurityActivity.context);
        database =  historyDatabase.getWritableDatabase();
        cursor = database.query(HistoryDatabase.DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst()){
            historyDatabase.addItem("Xiaomi X3MX", HistoryModel.TYPE_PHONE_ANDROID, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("Xiaomi X3MX", HistoryModel.TYPE_PHONE_ANDROID, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("Xiaomi X3MX", HistoryModel.TYPE_PHONE_ANDROID, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("Xiaomi Mi Laptop", HistoryModel.TYPE_LAPTOP_WINDOWS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("Xiaomi Mi Laptop", HistoryModel.TYPE_LAPTOP_WINDOWS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("Xiaomi Mi Laptop", HistoryModel.TYPE_LAPTOP_WINDOWS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("Xiaomi Mi Laptop", HistoryModel.TYPE_LAPTOP_WINDOWS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("iPhone XS", HistoryModel.TYPE_PHONE_IOS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("iPhone XS", HistoryModel.TYPE_PHONE_IOS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("iPhone XS", HistoryModel.TYPE_PHONE_IOS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
            historyDatabase.addItem("iPhone XS", HistoryModel.TYPE_PHONE_IOS, "Харьков, Украина", "18:30", "00:3e:e1:c1:c4:5d:df", "192.183.293.291", "С 19:41 по 20:41 (1 час)");
        }
        recyclerView.setPadding(0,Methods.dpToPx(8, SecurityActivity.context),0,Methods.dpToPx(8, SecurityActivity.context));
        topFrameLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tintNestedScrollView.magnetizeTint(SecurityActivity.toolbarLayout,topFrameLayout,topFrameLayout.getMeasuredHeight());
                topFrameLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
        this.v = topFrameLayout;
        historyModels = historyDatabase.addAll();
        emptyLayoutFragment.registerOnCreateCallBack(this);
        titleButtonHeaderFragment.registerOnCreateCallBack(this);
        checkIfEmpty();

        return v;
    }

    @Override
    public void onCreateTitleButtonHeader() {
        titleButtonHeaderFragment.title.setText("Если вы заметили подозрительную активность, завершите все сеансы и измените свой пароль.");
        titleButtonHeaderFragment.button.setText("Завершить все сеансы");
    }

    @Override
    public void onCreateEmptyLayout() {

    }

    private class HistoryFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ImageView icon;
        private ConstraintLayout layout;
        private ImageView secondIcon;

        public HistoryFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.icon_title_subtitle_icon, parent, false));
            title = (TextView) itemView.findViewById(R.id.iconTitleSubtitleIconItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.iconTitleSubtitleIconItemSubtitle);
            icon = (ImageView) itemView.findViewById(R.id.iconTitleSubtitleIconItemIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.iconTitleSubtitleIconItemLayout);
            secondIcon = (ImageView) itemView.findViewById(R.id.iconTitleSubtitleIconItemSecondIcon);

        }

    }

    private class HistoryFragmentAdapter extends RecyclerView.Adapter<HistoryFragment.HistoryFragmentHolder> {

        @NonNull
        @Override
        public HistoryFragment.HistoryFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new HistoryFragment.HistoryFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final HistoryFragment.HistoryFragmentHolder holder, final int position) {

            final HistoryModel historyModel = historyModels.get(position);

            holder.title.setText(historyModel.deviceName);
            holder.subtitle.setText(historyModel.location + " • " + historyModel.time);
            holder.secondIcon.setImageResource(R.drawable.ic_outline_info_active_24dp);

            checkDevice(historyModel,holder.icon);

            holder.secondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HistoryInfoDialogFragment historyInfoDialogFragment = new HistoryInfoDialogFragment();
                    historyInfoDialogFragment.showDialogAlert(historyModel,getActivity());
                }
            });

        }

        private void checkDevice(HistoryModel historyModel, ImageView icon){

            if(historyModel.deviceType == HistoryModel.TYPE_PHONE_IOS)
                icon.setImageResource(R.drawable.ic_outline_phone_iphone_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_PHONE_ANDROID)
                icon.setImageResource(R.drawable.ic_outline_phone_android_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_PHONE_NO_NAME)
                icon.setImageResource(R.drawable.ic_outline_smartphone_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_TABLET_IOS)
                icon.setImageResource(R.drawable.ic_outline_tablet_mac_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_TABLET_ANDROID)
                icon.setImageResource(R.drawable.ic_outline_tablet_android_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_TABLET_NO_NAME)
                icon.setImageResource(R.drawable.ic_outline_tablet_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_DESKTOP_MAC)
                icon.setImageResource(R.drawable.ic_outline_desktop_mac_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_DESKTOP_WINDOWS)
                icon.setImageResource(R.drawable.ic_outline_desktop_windows_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_LAPTOP_MAC)
                icon.setImageResource(R.drawable.ic_outline_laptop_mac_87_24dp);
            else if(historyModel.deviceType == HistoryModel.TYPE_LAPTOP_WINDOWS)
                icon.setImageResource(R.drawable.ic_outline_laptop_windows_87_24dp);

        }

        @Override
        public int getItemCount() {

            return historyModels.size();

        }
    }

    public void checkIfEmpty(){
        if(historyModels.size() == 0)
            getChildFragmentManager().beginTransaction().hide(emptyLayoutFragment).commit();
        else
            getChildFragmentManager().beginTransaction().show(emptyLayoutFragment).commit();
    }

}
