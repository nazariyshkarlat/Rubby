package com.example.rubby.Other;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.example.rubby.R;

public class ColorsAndDrawables extends AppCompatActivity {

    public int colorControlNormal;
    public int colorPrimary;
    public int colorPrimaryDark;
    public int dialog_color;
    public int colorAccent;
    public int textColorPrimary;
    public int textColorSecondary;
    public int active_pressed;
    public int page_background;
    public int active_40;
    public int active_20;
    public int snackbar_color;
    public int default_12;
    public int default_60;
    public int default_87;
    public int divider_color;
    public int dark;
    public int white;
    public int red;
    public Typeface regular;
    public Typeface medium;
    private TypedValue typedValue = new TypedValue();
    private Resources.Theme theme;

    public ColorsAndDrawables(Activity activity){

        theme = activity.getTheme();
        theme.resolveAttribute(R.attr.colorControlNormal, typedValue, true);
        colorControlNormal = typedValue.data;
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        colorPrimary = typedValue.data;
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        colorPrimaryDark = typedValue.data;
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        colorAccent = typedValue.data;
        theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        textColorPrimary= typedValue.data;
        theme.resolveAttribute(android.R.attr.textColorSecondary, typedValue, true);
        textColorSecondary = typedValue.data;
        theme.resolveAttribute(R.attr.active_pressed, typedValue, true);
        active_pressed = typedValue.data;
        theme.resolveAttribute(R.attr.page_background_color, typedValue, true);
        page_background = typedValue.data;
        theme.resolveAttribute(R.attr.active_color_40, typedValue, true);
        active_40 = typedValue.data;
        theme.resolveAttribute(R.attr.active_color_40, typedValue, true);
        active_20 = typedValue.data;
        theme.resolveAttribute(R.attr.snackbar_color, typedValue, true);
        snackbar_color = typedValue.data;
        theme.resolveAttribute(R.attr.divider_color, typedValue, true);
        divider_color = typedValue.data;
        theme.resolveAttribute(R.attr.dialog_color, typedValue, true);
        dialog_color = typedValue.data;
        theme.resolveAttribute(R.attr.default_12,typedValue,true);
        default_12 = typedValue.data;
        theme.resolveAttribute(R.attr.default_60,typedValue,true);
        default_60 = typedValue.data;
        theme.resolveAttribute(R.attr.default_87,typedValue,true);
        default_87 = typedValue.data;
        white = activity.getResources().getColor(R.color.white);
        dark = activity.getResources().getColor(R.color.black);
        red = activity.getResources().getColor(R.color.red);
        regular = ResourcesCompat.getFont(activity, R.font.roboto_regular);
        medium = ResourcesCompat.getFont(activity, R.font.roboto_medium);

    }

}
