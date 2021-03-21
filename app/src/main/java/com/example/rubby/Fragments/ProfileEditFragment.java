package com.example.rubby.Fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Other.Methods;
import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.OverridedWidgets.Snackbars;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.Database.ProfileEditDatabase;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileEditFragment extends ChildFragment implements ProfileHeaderFragment.onCreateProfileHeaderCallBack {

    private Snackbars snackbars = new Snackbars();
    InputMethodManager imm;
    private  ArrayList<Boolean> booleanArrayList = new ArrayList<>(Arrays.asList(false,false,false,false,false));
    private  int selectedPos = -1;
    private static ContentValues contentValues = new ContentValues();
    private RecyclerView recyclerView;
    public boolean backClick = false;
    private FrameLayout frameLayout;
    ProfileHeaderFragment profileHeaderFragment = new ProfileHeaderFragment();
    private ArrayList<String> startArrayList = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    private ProfileEditDatabase profileEditDatabase;
    private String titlesS[] = { "Полное имя", "Логин", "О себе", "Сайт"};
    private  RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.nested_recycler_view,container,false);
        getChildFragmentManager().beginTransaction().add(R.id.nestedRecyclerFrameLayout,profileHeaderFragment,null).commit();
        frameLayout = (FrameLayout) v.findViewById(R.id.nestedRecyclerFrameLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.nestedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        rAdapter = new ProfileEditFragment.ProfileEditAdapter();
        recyclerView.setAdapter(rAdapter);
        profileEditDatabase = new ProfileEditDatabase(AccountEditActivity.context);
        this.v = frameLayout;
        recyclerView.setPadding(0,0,0,Methods.dpToPx(12,getContext()));
        startArrayList.addAll(profileEditDatabase.addAll());
        arrayList.addAll(startArrayList);
        for(int i = 0;i<rAdapter.getItemCount();i++) {
            setArray(i);
        }
        profileHeaderFragment.registerOnCreateCallBack(this);
        return v;

    }

    @Override
    public void onCreateProfileHeader() {

        profileHeaderFragment.awa.setLayoutParams(new ConstraintLayout.LayoutParams(Methods.dpToPx(48, AccountEditActivity.context),Methods.dpToPx(48, AccountEditActivity.context)));
    }

    private void setArray(int position){
        if(position == 0) {
            if ((arrayList.get(0) == null) || (arrayList.get(0).length() == 0)) {
                booleanArrayList.set(0, true);
            } else if (arrayList.get(0).length() > 20) {
                booleanArrayList.set(1, true);
            } else {
                booleanArrayList.set(0, false);
                booleanArrayList.set(1, false);
            }
        }
        if(position == 1) {
            if ((arrayList.get(1) == null) || (arrayList.get(1).length() == 0)) {
                booleanArrayList.set(2, true);
            } else if ((arrayList.get(1).length() > 20)) {
                booleanArrayList.set(3, true);
            } else {
                booleanArrayList.set(2, false);
                booleanArrayList.set(3, false);
            }
        }
        if(position == 3) {
            if ((arrayList.get(3) != null) && (arrayList.get(3).length() != 0) && (arrayList.get(3).length() < 4) & (!arrayList.get(3).endsWith(".com"))) {
                booleanArrayList.set(4,true);
            }else {
                booleanArrayList.set(4,false);
            }
        }
    }

    public void profileEditBack(Resources resources, View view, Activity activity) {

        backClick = true;
        for(int i = 0;i<rAdapter.getItemCount();i++) {
            setArray(i);
        }
        if(booleanArrayList.contains(true)){
        if (booleanArrayList.get(0)) {
            rAdapter.notifyItemChanged(0);
        }
        if (booleanArrayList.get(1)) {
            rAdapter.notifyItemChanged(0);
        }
        if (booleanArrayList.get(2)) {
            rAdapter.notifyItemChanged(1);
        }
        if (booleanArrayList.get(3)) {
            rAdapter.notifyItemChanged(1);
        }
        if (booleanArrayList.get(4)) {
            rAdapter.notifyItemChanged(3);
        }} else {
            ((AccountEditActivity)getActivity()).currentFragment = null;
            getActivity().onBackPressed();
            if (!startArrayList.toString().equals(arrayList.toString())) {
                snackbars.snackbar(resources, view, "Внесённые изменения сохранены!", activity,true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        saveStartDatabase();

                    }
                },0);
            }
        }
    }

    public class ProfileEditHolder extends RecyclerView.ViewHolder{

        private TextInputEditText editText;
        private ConstraintLayout layout;
        private ImageView errorIcon;
        private TextView subtitle;
        private TextInputLayoutCustom textInputLayout;

        public ProfileEditHolder(LayoutInflater inflater, ViewGroup parent){


            super(inflater.inflate(R.layout.text_input_layout_outlined_item,parent,false));

            editText = (TextInputEditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            textInputLayout = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);

        }

    }

    public void saveDatabase(int selectedPos, EditText editText) {
        if(selectedPos == 0)
            profileEditDatabase.setValue(ProfileEditDatabase.FULL_NAME,editText.getText().toString());
        else if(selectedPos == 1)
            profileEditDatabase.setValue(ProfileEditDatabase.LOGIN,editText.getText().toString());
        else if(selectedPos == 2)
            profileEditDatabase.setValue(ProfileEditDatabase.MORE,editText.getText().toString());
        else if(selectedPos == 3)
            profileEditDatabase.setValue(ProfileEditDatabase.SITE,editText.getText().toString());
    }

    public void saveStartDatabase() {
        profileEditDatabase.setValue(ProfileEditDatabase.FULL_NAME,startArrayList.get(0));
        profileEditDatabase.setValue(ProfileEditDatabase.LOGIN, startArrayList.get(1));
        profileEditDatabase.setValue(ProfileEditDatabase.MORE, startArrayList.get(2));
        profileEditDatabase.setValue(ProfileEditDatabase.SITE,startArrayList.get(3));
    }

    private class ProfileEditAdapter extends RecyclerView.Adapter<ProfileEditFragment.ProfileEditHolder>{

        @NonNull
        @Override
        public ProfileEditFragment.ProfileEditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProfileEditFragment.ProfileEditHolder(layoutInflater,parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final ProfileEditFragment.ProfileEditHolder holder, final int position) {

            holder.editText.setText(arrayList.get(position));

            if(!backClick) {
                holder.editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
                holder.editText.setSingleLine(false);
                holder.editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                holder.textInputLayout.setHint(titlesS[position]);


                if (position == titlesS.length - 1 ||
                        position == 2) {
                    holder.subtitle.setVisibility(View.VISIBLE);
                    holder.subtitle.setText("Необязательно");
                }

                if (position == 0)
                    holder.itemView.setPadding(Methods.dpToPx(16, AccountEditActivity.context), Methods.dpToPx(12, AccountEditActivity.context), Methods.dpToPx(16, AccountEditActivity.context), 0);

            }

            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    selectedPos = holder.getLayoutPosition();

                    if(selectedPos == 0)
                        arrayList.set(0,s.toString());
                    else if(selectedPos == 1)
                        arrayList.set(1,s.toString());
                    else if(selectedPos == 2)
                        arrayList.set(2,s.toString());
                    else if(selectedPos == 3)
                        arrayList.set(3,s.toString());

                    if(holder.errorIcon.getVisibility() != View.VISIBLE)
                        saveDatabase(selectedPos,holder.editText);
                    else
                        holder.textInputLayout.setError(false, holder.subtitle, holder.errorIcon, holder.layout, null, recyclerView, selectedPos, true);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            recyclerView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return false;
                }
            });

            if(backClick)
                setError(arrayList.get(0),arrayList.get(1),arrayList.get(3),holder.textInputLayout,holder.errorIcon,holder.layout,holder.subtitle,position);

        }
        private void setError(String name, String login,String site,TextInputLayoutCustom textInputLayoutCustom,ImageView errorIcon,ConstraintLayout layout,TextView subtitle,int position) {
            if (position == 0) {
                if ((name == null) || (name.length() == 0)) {
                    textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Введите ваше имя!", recyclerView, selectedPos, true);
                } else if (name.length() > 20) {
                    textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Не более 20 символов!", recyclerView, selectedPos, true);
                }
            }
            if (position == 1) {
                if ((login == null) || (login.length() == 0)) {
                    textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Введите ваш логин!", recyclerView, selectedPos, true);
                } else if ((login.length() > 20)) {
                    textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Не более 20 символов!", recyclerView, selectedPos, true);
                }
            }
            if (position == 3) {
                if ((site != null) && (site.length() != 0) && (site.length() < 4) & (!site.endsWith(".com"))) {
                textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Введите существующий сайт!", recyclerView, selectedPos, false);
            }
            }

        }

        @Override
        public int getItemCount() {

            return titlesS.length;

        }
    }

}
