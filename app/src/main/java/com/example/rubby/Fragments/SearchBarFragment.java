package com.example.rubby.Fragments;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class SearchBarFragment extends SmoothFragment {

    private EditText toolbar;
    public ImageView searchIcon;
    private ImageView close;
    private ImageView back;
    public String hint;
    private InputMethodManager imgr;
    private final ChangeBounds transition = new ChangeBounds();
    private ConstraintLayout parent;
    private ConstraintSet constraintSet = new ConstraintSet();
    private AutoTransition autoTransition = new AutoTransition();
    public boolean isShown = false;

    public onTextChangeCallBack onTextChangeCallBack;

    public interface onTextChangeCallBack {
        void onTextChange(String text);
    }

    public void registerOnTextChangeCallBack(onTextChangeCallBack callback) {
        this.onTextChangeCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_bar, container, false);
        toolbar = (EditText) v.findViewById(R.id.searchBarEditText);
        parent = (ConstraintLayout) v.findViewById(R.id.searchBarConstraintLayout);
        back = (ImageView) v.findViewById(R.id.searchBarArrowBackButton);
        close = (ImageView) v.findViewById(R.id.searchBarCloseButton);
        imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        transition.setDuration(150);
        autoTransition.addTarget(toolbar);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolbar.getText().toString().length() == 0) {
                    hideSearch();
                }else {
                    clearText();
                }
            }
        });

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearch();
            }
        });

        toolbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextChangeCallBack.onTextChange(s.toString());
                if(count > 0 && close.getVisibility() != View.VISIBLE) {
                    showClose();
                }else if(count == 0){
                    hideClose();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    private void clearText(){
        toolbar.setText("");
    }

    public void hideSearch(){
        constraintSet.clone(parent);
        constraintSet.clear(toolbar.getId(), ConstraintSet.START);
        TransitionManager.beginDelayedTransition(parent, transition);
        constraintSet.applyTo(parent);
        back.animate().alpha(0F).setDuration(250);
        toolbar.setPadding(0, toolbar.getPaddingTop(), 0, toolbar.getPaddingBottom());
        toolbar.setCursorVisible(false);
        toolbar.setHint("");
        imgr.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
        back.setVisibility(View.GONE);
        isShown = false;
    }

    private void showSearch(){
        toolbar.setCursorVisible(true);
        autoTransition.addTarget(toolbar);
        constraintSet.clone(parent);
        constraintSet.connect(toolbar.getId(), ConstraintSet.START, parent.getId(), ConstraintSet.START, Methods.dpToPx(16,getContext()));
        TransitionManager.beginDelayedTransition(parent, transition);
        constraintSet.applyTo(parent);
        back.setVisibility(View.VISIBLE);
        back.setAlpha(0F);
        back.animate().alpha(1F).setDuration(250);
        toolbar.setPadding(Methods.dpToPx(56,getContext()),toolbar.getPaddingTop(),Methods.dpToPx(56,getContext()),toolbar.getPaddingBottom());
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbar.requestFocus();
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                toolbar.setHint(hint);
                mgr.showSoftInput(toolbar, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        isShown = true;
    }

    private void showClose(){
        close.setAlpha(0F);
        close.setVisibility(View.VISIBLE);
        close.animate().alpha(1F).setDuration(150);
    }

    private void hideClose(){
        close.animate().alpha(0F).setDuration(150).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(close.getAlpha() == 0F)
                    close.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
