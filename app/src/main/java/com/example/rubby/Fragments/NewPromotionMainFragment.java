package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.R;

public class NewPromotionMainFragment extends ChildFragment implements  IconTitleCenterHeaderFragment.onCreateIconTitleCenterHeaderCallBack {

    private RecyclerView recyclerView;
    private  IconTitleCenterHeaderFragment iconTitleCenterHeaderFragment = new IconTitleCenterHeaderFragment();
    public RecyclerView.Adapter rAdapter;
    private FrameLayout frameLayout;
    public RecyclerView.ViewHolder secondHolder;
    private String[] titleS = {"Отключить комментарии", "Название вашей организации", "Описание", "Веб-сайт"};
    private String[] subtitleS = {"Никто не сможет комментировать вашу публикацию"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.nested_recycler_view,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.nestedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(!iconTitleCenterHeaderFragment.isAdded())
            getChildFragmentManager().beginTransaction().add(R.id.nestedRecyclerFrameLayout, iconTitleCenterHeaderFragment, null).commit();
        frameLayout = (FrameLayout) v.findViewById(R.id.nestedRecyclerFrameLayout);
        rAdapter = new NewPromotionMainFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        recyclerView.setPadding(0,Methods.dpToPx(8,getContext()),0,0);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        iconTitleCenterHeaderFragment.registerOnCreateCallBack(this);

        this.v = frameLayout;

        return v;

    }

    @Override
    public void onCreateIconTitleCenterHeader() {
        iconTitleCenterHeaderFragment.icon.setImageResource(R.drawable.ic_outline_photo_camera_active_24dp);
        iconTitleCenterHeaderFragment.title.setText("Добавить фото или видео");
    }

    private class NewPromotionMainFragmentFirstHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView subtitle;
        private SwitchCompat switchCompat;
        private ConstraintLayout layout;

        public NewPromotionMainFragmentFirstHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.title_subtitle_switch_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemSubtitle);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.titleSubtitleSwitchItemSwitch);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleSwitchItemLayout);
        }

    }

    public class NewPromotionMainFragmentSecondHolder extends RecyclerView.ViewHolder{

        public TextInputEditText editText;
        public ConstraintLayout layout;
        public ImageView errorIcon;
        public TextView subtitle;
        public TextInputLayoutCustom textInputLayout;

        public NewPromotionMainFragmentSecondHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.text_input_layout_outlined_item,parent,false));
            editText = (TextInputEditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            textInputLayout = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);
            secondHolder = this;
        }

    }

    private class NewPromotionMainFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            switch (viewType) {
                case 0:
                    return new NewPromotionMainFragmentFirstHolder(layoutInflater, parent);
                case 2:
                    return new NewPromotionMainFragmentSecondHolder(layoutInflater, parent);
                default:
                    return null;
            }

        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0)
                return 0;
            else
                return 2;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

            switch (holder.getItemViewType()) {
                case 0:
                    NewPromotionMainFragmentFirstHolder promotionsMainFragmentFirstHolder = (NewPromotionMainFragmentFirstHolder) holder;
                    ((NewPromotionMainFragmentFirstHolder) holder).title.setText(titleS[position]);
                    ((NewPromotionMainFragmentFirstHolder) holder).subtitle.setText(subtitleS[position]);
                    if(position == 0) {
                        ((NewPromotionMainFragmentFirstHolder) holder).switchCompat.setVisibility(View.VISIBLE);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((NewPromotionMainFragmentFirstHolder) holder).switchCompat.setChecked(!((NewPromotionMainFragmentFirstHolder) holder).switchCompat.isChecked());
                                ((PromotionsActivity)getActivity()).promotionsModel.offComments = ((NewPromotionMainFragmentFirstHolder) holder).switchCompat.isChecked() ? 1 : 0;
                            }
                        });
                    }
                    break;

                case 2:
                    NewPromotionMainFragmentSecondHolder promotionsMainFragmentSecondHolder = (NewPromotionMainFragmentSecondHolder) holder;
                    ((NewPromotionMainFragmentSecondHolder) holder).textInputLayout.setHint(titleS[position]);
                    if(position == 1) {
                        holder.itemView.setPadding(holder.itemView.getPaddingLeft(),Methods.dpToPx(14, getContext()),holder.itemView.getPaddingRight(),holder.itemView.getPaddingBottom());
                    }else if(position == 2) {
                        ((NewPromotionMainFragmentSecondHolder) holder).editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                        ((NewPromotionMainFragmentSecondHolder) holder).editText.setSingleLine(false);
                        ((NewPromotionMainFragmentSecondHolder) holder).editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    }else if(position == getItemCount()-1)
                        holder.itemView.setPadding(holder.itemView.getPaddingLeft(),holder.itemView.getPaddingTop(),holder.itemView.getPaddingRight(),Methods.dpToPx(14,getContext()));
                    break;
            }
        }

        @Override
        public int getItemCount() {

            return 4;

        }
    }

}
