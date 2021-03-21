package com.example.rubby.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Other.Methods;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.OverridedWidgets.TintRecyclerView;
import com.example.rubby.Database.BlackListDatabase;
import com.example.rubby.Model.BlackListModel;
import com.example.rubby.R;

import java.util.List;

public class BlackListFragment extends ChildFragment implements EmptyLayoutFragment.onCreateEmptyLayoutCallBack{

    private List<BlackListModel> blackListModels;
    private RecyclerView.LayoutManager layoutManager;
    private TintRecyclerView recyclerView;
    private BlackListDatabase blackListDatabase;
    private int selectedPos = 0;
    private EmptyLayoutFragment emptyLayoutFragment = new EmptyLayoutFragment();
    private ConstraintSet constraintSet = new ConstraintSet();
    private FrameLayout frameLayout;
    private SQLiteDatabase database;
    private Cursor cursor;
    private RecyclerView.Adapter rAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_frame_layout_center, container, false);
        recyclerView = (TintRecyclerView) v.findViewById(R.id.recyclerViewFrameLayoutCenterRecyclerView);
        frameLayout = (FrameLayout) v.findViewById(R.id.recyclerViewFrameLayoutCenterFrameLayout);
        getChildFragmentManager().beginTransaction().add(R.id.recyclerViewFrameLayoutCenterFrameLayout, emptyLayoutFragment, null).hide(emptyLayoutFragment).commit();
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new BlackListFragment.BlackListFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        blackListDatabase = new BlackListDatabase(SecurityActivity.context);
        database =  blackListDatabase.getWritableDatabase();
        cursor = database.query(BlackListDatabase.DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst()){
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
            blackListDatabase.addItem("brianMay", "10.02.2019");
        }
        recyclerView.setElevation(SecurityActivity.toolbarLayout);
        blackListModels = blackListDatabase.addAll();
        recyclerView.setPadding(0,Methods.dpToPx(8, SecurityActivity.context),0,Methods.dpToPx(8, SecurityActivity.context));
        this.v = frameLayout;
        emptyLayoutFragment.registerOnCreateCallBack(this);
        checkIfEmpty();

        return v;
    }

    public void checkIfEmpty(){
        if(blackListModels.size() == 0)
            getChildFragmentManager().beginTransaction().show(emptyLayoutFragment).commit();
        else
            getChildFragmentManager().beginTransaction().hide(emptyLayoutFragment).commit();
    }

    @Override
    public void onCreateEmptyLayout() {
        emptyLayoutFragment.title.setText("Чёрный список пуст");
        emptyLayoutFragment.subtitle.setVisibility(View.GONE);
        emptyLayoutFragment.button.setVisibility(View.GONE);
    }

    private class BlackListFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ImageView icon;
        private ConstraintLayout layout;
        private ImageView secondIcon;

        public BlackListFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.icon_title_subtitle_icon, parent, false));
            title = (TextView) itemView.findViewById(R.id.iconTitleSubtitleIconItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.iconTitleSubtitleIconItemSubtitle);
            icon = (ImageView) itemView.findViewById(R.id.iconTitleSubtitleIconItemIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.iconTitleSubtitleIconItemLayout);
            secondIcon = (ImageView) itemView.findViewById(R.id.iconTitleSubtitleIconItemSecondIcon);
        }

    }

    private class BlackListFragmentAdapter extends RecyclerView.Adapter<BlackListFragment.BlackListFragmentHolder> {

        @NonNull
        @Override
        public BlackListFragment.BlackListFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BlackListFragment.BlackListFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final BlackListFragment.BlackListFragmentHolder holder, final int position) {

            BlackListModel blackListModel = blackListModels.get(position);

            holder.title.setText(blackListModel.userName);
            holder.subtitle.setText("Заблокирован: " + blackListModel.time);
            holder.secondIcon.setImageResource(R.drawable.ic_outline_delete_outline_87_24dp);
            holder.icon.setImageResource(R.drawable.awa_oval);
            holder.icon.setLayoutParams(new ConstraintLayout.LayoutParams(Methods.dpToPx(40,SecurityActivity.context),Methods.dpToPx(40,SecurityActivity.context)));
            constraintSet.clone(holder.layout);
            constraintSet.centerVertically(holder.icon.getId(),holder.layout.getId());
            constraintSet.applyTo(holder.layout);

            holder.secondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPos = holder.getLayoutPosition();
                    blackListDatabase.removeItem(selectedPos);
                    blackListModels.remove(selectedPos);
                    notifyItemRemoved(selectedPos);
                    recyclerView.scrollY = recyclerView.scrollY - holder.layout.getMeasuredHeight();
                    checkIfEmpty();
                }
            });

        }

        @Override
        public int getItemCount() {

            return blackListModels.size();

        }
    }

}
