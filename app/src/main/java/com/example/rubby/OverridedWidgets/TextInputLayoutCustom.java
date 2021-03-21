package com.example.rubby.OverridedWidgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Other.ColorsAndDrawables;
import com.example.rubby.Other.Methods;

import java.lang.reflect.Field;

public class TextInputLayoutCustom extends TextInputLayout {

    private ColorsAndDrawables colorsAndDrawables;
    private ConstraintSet showError = new ConstraintSet();
    private ConstraintSet hideError = new ConstraintSet();

    public TextInputLayoutCustom(Context context) {
        super(context);
        colorsAndDrawables = new ColorsAndDrawables(((Activity) ((ContextThemeWrapper) getContext()).getBaseContext()));
        try {
            Field fDefaultStrokeColor = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
            fDefaultStrokeColor.setAccessible(true);
            fDefaultStrokeColor.set(this, colorsAndDrawables.default_12);
        }catch (Exception e){

        }
    }

    public TextInputLayoutCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        colorsAndDrawables = new ColorsAndDrawables(((Activity) ((ContextThemeWrapper) getContext()).getBaseContext()));
        try {
            Field fDefaultStrokeColor = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
            fDefaultStrokeColor.setAccessible(true);
            fDefaultStrokeColor.set(this, colorsAndDrawables.default_12);
        }catch (Exception e){

        }
    }

    public TextInputLayoutCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        colorsAndDrawables = new ColorsAndDrawables(((Activity) ((ContextThemeWrapper) getContext()).getBaseContext()));
        try {
            Field fDefaultStrokeColor = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
            fDefaultStrokeColor.setAccessible(true);
            fDefaultStrokeColor.set(this, colorsAndDrawables.default_12);
        }catch (Exception e){

        }
    }

    private void setInputTextLayoutError(TextView subtitles, ImageView errorIcon, ConstraintLayout constraintLayout, String errorText) {
        try {
            if(this.getBoxStrokeColor() != colorsAndDrawables.red) {
                Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("defaultHintTextColor");
                fDefaultTextColor.setAccessible(true);
                fDefaultTextColor.set(this, new ColorStateList(new int[][]{{0}}, new int[]{colorsAndDrawables.red}));

                Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("focusedTextColor");
                fFocusedTextColor.setAccessible(true);
                fFocusedTextColor.set(this, new ColorStateList(new int[][]{{0}}, new int[]{colorsAndDrawables.red}));

                Field FBoxStrokeWidthPx = TextInputLayout.class.getDeclaredField("boxStrokeWidthDefaultPx");
                FBoxStrokeWidthPx.setAccessible(true);
                FBoxStrokeWidthPx.set(this, Methods.dpToPx(2, getContext()));

                Field fDefaultStrokeColor = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
                fDefaultStrokeColor.setAccessible(true);
                fDefaultStrokeColor.set(this, colorsAndDrawables.red);

                this.setBoxStrokeColor(colorsAndDrawables.red);
                this.drawableStateChanged();
                showError.clone(constraintLayout);
                showError.connect(subtitles.getId(), ConstraintSet.START, errorIcon.getId(), ConstraintSet.END, Methods.dpToPx(8, getContext()));
                showError.applyTo(constraintLayout);
                subtitles.setTextColor(colorsAndDrawables.red);
                subtitles.setText(errorText);
                errorIcon.setVisibility(View.VISIBLE);
                subtitles.setVisibility(View.VISIBLE);
            }else {
                changeErrorText(subtitles, errorText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideInputTextLayoutError(TextView subtitles, ImageView errorIcon, ConstraintLayout constraintLayout, boolean hideSubtitles) {
        try {
            if(this.getBoxStrokeColor() == colorsAndDrawables.red) {
                Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("defaultHintTextColor");
                fDefaultTextColor.setAccessible(true);
                fDefaultTextColor.set(this, new ColorStateList(new int[][]{{0}}, new int[]{colorsAndDrawables.default_60}));

                Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("focusedTextColor");
                fFocusedTextColor.setAccessible(true);
                fFocusedTextColor.set(this, new ColorStateList(new int[][]{{0}}, new int[]{colorsAndDrawables.colorAccent}));

                Field FBoxStrokeWidthPx = TextInputLayout.class.getDeclaredField("boxStrokeWidthDefaultPx");
                FBoxStrokeWidthPx.setAccessible(true);
                FBoxStrokeWidthPx.set(this, Methods.dpToPx(1, getContext()));

                Field fDefaultStrokeColor = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
                fDefaultStrokeColor.setAccessible(true);
                fDefaultStrokeColor.set(this, colorsAndDrawables.default_12);
                this.setBoxStrokeColor(colorsAndDrawables.colorAccent);
                errorIcon.setVisibility(GONE);
                hideError.clone(constraintLayout);
                hideError.connect(subtitles.getId(), ConstraintSet.START, errorIcon.getId(), ConstraintSet.END, Methods.dpToPx(32, getContext()));
                hideError.applyTo(constraintLayout);
                if(!hideSubtitles) {
                    subtitles.setTextColor(colorsAndDrawables.default_12);
                    subtitles.setText("Необязательно");
                    subtitles.setVisibility(View.VISIBLE);
                }else
                    subtitles.setVisibility(View.GONE);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setError(boolean error, TextView subtitles, ImageView errorIcon, ConstraintLayout constraintLayout, String errorText, RecyclerView recyclerView, int selectedPos, boolean hideSubtitles){
        if(error)
            setInputTextLayoutError(subtitles,errorIcon,constraintLayout,errorText);
        else
            hideInputTextLayoutError(subtitles,errorIcon,constraintLayout,hideSubtitles);
        if(selectedPos != -1)
            recyclerView.smoothScrollToPosition(selectedPos);
    }

    public void changeErrorText(TextView subtitles,String newText){
        subtitles.setText(newText);
    }
    }
