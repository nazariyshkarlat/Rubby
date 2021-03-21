package com.example.rubby.Fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.DividerItemDecorator;
import com.example.rubby.OverridedWidgets.TintRecyclerView;
import com.example.rubby.Model.PromotionsModel;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Collections;

public class PromotionInfoFragment extends Fragment {

    public static int parentPos;
    private DividerItemDecorator dividerItemDecorator;
    private String[] titleS = {"Название", "Продолжительность", "Просмотры", "Переходы по ссылке", "Отметки нравится", "Комментарии", "Добавлено в избранное"};
    private String[] subtitleS;
    private ArrayList<Integer> dividerPos = new ArrayList<>(Collections.singletonList(1));
    private RecyclerView.LayoutManager layoutManager;
    private TintRecyclerView recyclerView;
    private PromotionsModel promotionsModel;
    public RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (TintRecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(PromotionsActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new PromotionInfoFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        dividerItemDecorator = new DividerItemDecorator(ResourcesCompat.getDrawable(PromotionsActivity.context.getResources(), R.drawable.divider_list,getActivity().getTheme()), dividerPos,getActivity());
        recyclerView.addItemDecoration(dividerItemDecorator);
        promotionsModel = ((PromotionsActivity)getActivity()).promotionsDatabase.getModelForInfo(parentPos);
        recyclerView.setPadding(0, Methods.dpToPx(8,getContext()),0,Methods.dpToPx(8,getContext()));
        subtitleS = new String[] {promotionsModel.campaignName, promotionsModel.toDates(), toString().valueOf(promotionsModel.campaignViews), toString().valueOf(promotionsModel.campaignClicks), toString().valueOf(promotionsModel.campaignLikes), toString().valueOf(promotionsModel.campaignComments), toString().valueOf(promotionsModel.campaignFavorites)};

        return v;
    }

    private class PromotionInfoFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ImageView icon;
        private ConstraintLayout layout;

        public PromotionInfoFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle_icon, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleIconItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleIconItemSubtitle);
            icon = (ImageView) itemView.findViewById(R.id.titleSubtitleIconItemIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleIconItemLayout);

        }

    }

    private class PromotionInfoFragmentAdapter extends RecyclerView.Adapter<PromotionInfoFragmentHolder> {

        @NonNull
        @Override
        public PromotionInfoFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PromotionInfoFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PromotionInfoFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            holder.subtitle.setText(subtitleS[position]);

            if(position == 0 || position == 1){
                holder.icon.setImageResource(R.drawable.ic_outline_edit_87_24dp);
                holder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(position == 0) {
                            NewPromotionNameFragment promotionsNameFragment = new NewPromotionNameFragment();
                            promotionsNameFragment.edit = true;
                            promotionsNameFragment.name = subtitleS[0];
                            PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsNameFragment, null).commit();
                        }else if(position == 1){
                            NewPromotionDurationFragment promotionDurationFragment = new NewPromotionDurationFragment();
                            promotionDurationFragment.edit = true;
                            promotionDurationFragment.promotionsModel = promotionsModel;
                            promotionDurationFragment.modelIndex = parentPos;
                            PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionDurationFragment, null).commit();
                        }
                    }
                });
            }else
                holder.icon.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return titleS.length;
        }

    }

}
