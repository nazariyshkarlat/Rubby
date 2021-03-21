package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

import java.util.ArrayList;

public class NewPromotionPetsSelectListFragment extends SmoothFragment {

    private RecyclerView.LayoutManager layoutManager;
    public String petsNamesS[] = {"Собаки", "Коты", "Птицы", "Попугаи", "Щенки", "Котики", "Бараны", "Хомяки", "Морские свинки", "Шиншилы", "Ленивцы", "Змеи", "Хамелеоны", "Бобры", "Барсуки", "Скунсы", "Дикие животные", "Домашние питомцы", "Суслики", "Травоядные", "Всеядные", "Лесные животные", "Домашний скот", "Рыбы", "Кошачие"};
    private RecyclerView recyclerView;
    private int selectedPos;
    public ArrayList<Integer> selectedPositions = new ArrayList<>();
    private ArrayList<Integer> startSelectedPositions = new ArrayList<>();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(PromotionsActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        rAdapter = new NewPromotionPetsSelectListFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        startSelectedPositions.addAll(selectedPositions);


        return v;
    }

    private class NewPromotionPetsSelectListFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CheckBox checkBox;
        private ConstraintLayout layout;

        public NewPromotionPetsSelectListFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.check_box_title_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.checkBoxTitleItemTitle);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxTitleItemCheckBox);
            layout = (ConstraintLayout) itemView.findViewById(R.id.checkBoxTitleItemLayout);
        }

    }

    public void promotionsBack(){
        selectedPositions.clear();
        selectedPositions.addAll(startSelectedPositions);
    }

    private class NewPromotionPetsSelectListFragmentAdapter extends RecyclerView.Adapter<NewPromotionPetsSelectListFragmentHolder> {

        @NonNull
        @Override
        public NewPromotionPetsSelectListFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NewPromotionPetsSelectListFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NewPromotionPetsSelectListFragmentHolder holder, final int position) {

            holder.title.setText(petsNamesS[position]);

            holder.checkBox.setChecked(selectedPositions.contains(position));
            holder.checkBox.setClickable(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos = holder.getAdapterPosition();
                    if(!selectedPositions.contains(selectedPos))
                        selectedPositions.add(selectedPos);
                    else
                        selectedPositions.remove(Integer.valueOf(selectedPos));
                    holder.checkBox.setChecked(selectedPositions.contains(selectedPos));
                    if(!(startSelectedPositions.equals(selectedPositions)))
                        ((PromotionsActivity)getActivity()).toolbarSecondButton.setVisibility(View.VISIBLE);
                    else
                        ((PromotionsActivity)getActivity()).toolbarSecondButton.setVisibility(View.GONE);
                }
            });


        }

        @Override
        public int getItemCount() {

            return petsNamesS.length;

        }
    }

}
