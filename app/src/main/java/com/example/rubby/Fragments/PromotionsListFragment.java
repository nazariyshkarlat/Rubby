package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.TintRecyclerView;
import com.example.rubby.Model.PromotionsModel;
import com.example.rubby.R;

import java.util.ArrayList;

public class PromotionsListFragment extends Fragment {

    private int selectedPos;
    private RecyclerView.LayoutManager layoutManager;
    private TintRecyclerView recyclerView;
    private ArrayList<PromotionsModel> promotionsModels = new ArrayList<>();
    public RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (TintRecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(PromotionsActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new PromotionsListFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        recyclerView.hideFab(PromotionsMainFragment.floatingActionButton);
        promotionsModels.clear();
        promotionsModels.addAll(((PromotionsActivity)getActivity()).promotionsDatabase.addModelForList());
        recyclerView.setPadding(0, Methods.dpToPx(8,getContext()),0,Methods.dpToPx(8,getContext()));

        return v;
    }

    private class PromotionsListFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ConstraintLayout layout;

        public PromotionsListFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleItemSubtitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleItemLayout);

        }

    }

    private class PromotionsListFragmentAdapter extends RecyclerView.Adapter<PromotionsListFragmentHolder> {

        @NonNull
        @Override
        public PromotionsListFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PromotionsListFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PromotionsListFragmentHolder holder, final int position) {

            PromotionsModel promotionsModel = promotionsModels.get(position);
            holder.title.setText(promotionsModel.campaignName);
            holder.subtitle.setText(promotionsModel.toDates());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos = holder.getLayoutPosition();
                    PromotionInfoFragment promotionInfoFragment = new PromotionInfoFragment();
                    PromotionInfoFragment.parentPos = selectedPos;
                    PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,promotionInfoFragment,null).commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return promotionsModels.size();
        }

    }
}
