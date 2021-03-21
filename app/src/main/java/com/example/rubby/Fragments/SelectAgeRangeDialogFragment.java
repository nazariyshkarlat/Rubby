package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.R;

public class SelectAgeRangeDialogFragment extends SmoothFragment {

    public int firstValue = -1;
    public int secondValue = -1;
    public SelectAgeRangeDialogFragmentHolder holder;
    private RecyclerView recyclerView;
    public boolean validValue;
    private RecyclerView.Adapter dialogAlertRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    public onPositiveButtonClickCallBack onPositiveButtonClickCallBack;

    public interface onPositiveButtonClickCallBack {
        void onPositiveButtonClick(int firstValue, int secondValue);
    }

    public void registerOnPositiveButtonCallBack(onPositiveButtonClickCallBack callback) {
        this.onPositiveButtonClickCallBack = callback;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewLayout);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dialogAlertRecyclerViewAdapter = new SelectAgeRangeDialogFragmentAdapter();
        recyclerView.setAdapter(dialogAlertRecyclerViewAdapter);

        return v;
    }

    public class SelectAgeRangeDialogFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public ConstraintLayout layout;
        public TextInputEditText firstEditText;
        public TextInputEditText secondEditText;
        public TextInputLayoutCustom firstTextInputLayout;
        public TextInputLayoutCustom secondTextInputLayout;
        public ImageView eye;

        public SelectAgeRangeDialogFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.two_text_input_layout_item, parent, false));

            subtitle = (TextView) itemView.findViewById(R.id.twoTextInputLayoutItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.twoTextInputLayoutItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.twoTextInputLayoutItemLayout);
            firstEditText = (TextInputEditText) itemView.findViewById(R.id.twoTextInputLayoutItemFirstEditText);
            secondEditText = (TextInputEditText) itemView.findViewById(R.id.twoTextInputLayoutItemSecondEditText);
            firstTextInputLayout = (TextInputLayoutCustom) itemView.findViewById(R.id.twoTextInputLayoutItemFirstInputLayout);
            secondTextInputLayout = (TextInputLayoutCustom) itemView.findViewById(R.id.twoTextInputLayoutItemSecondInputLayout);
            eye = (ImageView) itemView.findViewById(R.id.twoTextInputLayoutItemIcon);
            layout.setPadding(Methods.dpToPx(16,getActivity()), Methods.dpToPx(24,getActivity()),Methods.dpToPx(16,getActivity()),0);
            holder = this;

        }

    }

    public class SelectAgeRangeDialogFragmentAdapter extends RecyclerView.Adapter<SelectAgeRangeDialogFragmentHolder> {

        @NonNull
        @Override
        public SelectAgeRangeDialogFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SelectAgeRangeDialogFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final SelectAgeRangeDialogFragmentHolder holder, final int position) {

            holder.firstTextInputLayout.setHint("От");
            holder.secondTextInputLayout.setHint("До");
            holder.firstEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.secondEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.firstEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            holder.firstEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
            holder.secondEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});

            ((PromotionsActivity)getActivity()).showKeyboard();
            holder.firstEditText.requestFocus();

            holder.firstEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.length() != 0) {
                        firstValue = Integer.parseInt(s.toString());
                    }else {
                        firstValue = -1;
                    }

                    setValid();

                    if(validValue)
                        ((PromotionsActivity)getActivity()).toolbarSecondButton.setVisibility(View.VISIBLE);
                    else
                        ((PromotionsActivity)getActivity()).toolbarSecondButton.setVisibility(View.GONE);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.secondEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.length() != 0) {
                        secondValue = Integer.parseInt(s.toString());
                    }else {
                        secondValue = -1;
                    }

                    setValid();

                    if(validValue)
                        ((PromotionsActivity)getActivity()).toolbarSecondButton.setVisibility(View.VISIBLE);
                    else
                        ((PromotionsActivity)getActivity()).toolbarSecondButton.setVisibility(View.GONE);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }

        @Override
        public int getItemCount() {

            return 1;

        }

    }

    public void acceptClick(int firstValue, int secondValue, TextInputLayoutCustom textInputLayoutCustom, TextView subtitle, ImageView errorIcon, ConstraintLayout constraintLayout) {
        if((firstValue != -1 && secondValue != -1) && (firstValue > secondValue)){
            textInputLayoutCustom.setError(true, subtitle, errorIcon, constraintLayout, "Минимальный возраст больше максимального!", recyclerView, 0, true);
        }else if((firstValue == -1 && secondValue == 0) || ((firstValue != -1 && secondValue != -1) && (firstValue == secondValue))){
            textInputLayoutCustom.setError(true, subtitle, errorIcon, constraintLayout, "Некорректные значения!", recyclerView, 0, true);
        }else if(secondValue == -1 && firstValue == -1) {
            textInputLayoutCustom.setError(true, subtitle, errorIcon, constraintLayout, "Введите значения!", recyclerView, 0, true);
        }else{
            textInputLayoutCustom.setError(false, subtitle, errorIcon, constraintLayout, null, recyclerView, 0, true);
        }
    }

    public void setValid(){
        if((firstValue != -1 && secondValue != -1) && (firstValue > secondValue)){
            validValue = false;
        }else if((firstValue == -1 && secondValue == 0) || ((firstValue != -1 && secondValue != -1) && (firstValue == secondValue))){
            validValue = false;
        }else if(secondValue == -1 && firstValue == -1) {
            validValue = false;
        }else{
            validValue = true;
        }
    }

}
