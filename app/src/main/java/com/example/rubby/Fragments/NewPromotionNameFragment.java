package com.example.rubby.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Database.PromotionsDatabase;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.OverridedWidgets.Snackbars;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.R;

public class NewPromotionNameFragment extends SmoothFragment {

    private Snackbars snackbars = new Snackbars();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public String name;
    private int selectedPos;
    public boolean edit = false;
    public NewPromotionNameFragmentHolder holder;
    private String NAME_KEY = "PROMOTIONS_NAME_KEY";
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        if(outState != null)
            name = outState.getString(NAME_KEY);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(PromotionsActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NewPromotionNameFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(name,NAME_KEY);
    }

    public class  NewPromotionNameFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public EditText editText;
        public TextInputLayoutCustom textInputLayoutCustom;
        public ConstraintLayout layout;

        public  NewPromotionNameFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.text_input_layout_outlined_item, parent, false));
            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            editText = (EditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            textInputLayoutCustom = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            holder = this;
        }

    }

    public void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private class NewPromotionNameFragmentAdapter extends RecyclerView.Adapter<NewPromotionNameFragmentHolder> {

        @NonNull
        @Override
        public  NewPromotionNameFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new  NewPromotionNameFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NewPromotionNameFragmentHolder holder, final int position) {

            holder.textInputLayoutCustom.setHint("Название кампании");
            holder.editText.requestFocus();
            holder.editText.setImeOptions(EditorInfo.IME_ACTION_GO);
            holder.editText.setText(name);
            if(name != null)
                holder.editText.setSelection(name.length());
            showKeyboard();

            holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        buttonClick(holder.textInputLayoutCustom,holder.subtitle,holder.errorIcon,holder.layout);
                        return true;
                    }
                    return false;
                }
            });

            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    name = s.toString();
                    holder.textInputLayoutCustom.setError(false,holder.subtitle,holder.errorIcon,holder.layout,null, recyclerView,0,true);
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

    public void buttonClick(TextInputLayoutCustom textInputLayoutCustom,TextView subtitle, ImageView errorIcon, ConstraintLayout layout){
        if(name != null && name.length() > 0){
            changeFragment();
        }else {
            textInputLayoutCustom.setError(true,subtitle,errorIcon,layout,"Введите название кампании!", recyclerView,0,true);
        }
    }

    private void changeFragment() {
        if (!edit) {
            NewPromotionTargetingFragment promotionsTargetingFragment = new NewPromotionTargetingFragment();
            PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsTargetingFragment, null).commit();
            ((PromotionsActivity) getActivity()).promotionsModel.campaignName = name;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PromotionsDatabase.CAMPAIGN_NAME, name);
            ((PromotionsActivity) getActivity()).promotionsDatabase.updateItem(contentValues, PromotionInfoFragment.parentPos);
            getActivity().onBackPressed();
            snackbars.snackbar(getResources(), PromotionsActivity.constraintLayout, "Внесённые изменения сохранены!", getActivity(), true, null, 0);
        }
    }

}
