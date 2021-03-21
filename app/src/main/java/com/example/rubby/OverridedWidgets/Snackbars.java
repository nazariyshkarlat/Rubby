package com.example.rubby.OverridedWidgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.Other.ColorsAndDrawables;
import com.example.rubby.R;

public class Snackbars {

    private TextView textView;
    private View sbView;
    private TextView actionText;
    public static Snackbar snackbar;

    public void snackbar(Resources resources, View view, final String text, Activity activity, boolean button, View.OnClickListener listener,int bottomMargin){
        int margins = convertDpToPixel(8,activity);
        int paddings = convertDpToPixel(16,activity);
        ColorsAndDrawables colorsAndDrawables = new ColorsAndDrawables(activity);
        if(!button) {
            snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
            sbView = snackbar.getView();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) sbView.getLayoutParams();
            params.setMargins(margins, margins, margins, bottomMargin + margins);
            sbView.setLayoutParams(params);
            sbView.setBackgroundResource(R.drawable.snackbar_rectangle);
            sbView.setElevation(6f);
            textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setPadding(margins,paddings, margins, paddings);
            textView.setTextColor(colorsAndDrawables.white);
            textView.setTextSize(16);
            textView.setTypeface(colorsAndDrawables.regular);
            snackbar.show();
        }else {
            snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
            snackbar.setAction("Отменить", listener);
            sbView = snackbar.getView();
            textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            actionText = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_action);
            actionText.setTypeface(colorsAndDrawables.medium);
            actionText.setAllCaps(false);actionText.setTextSize(16);
            snackbar.setActionTextColor(colorsAndDrawables.colorAccent);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) sbView.getLayoutParams();
            params.setMargins(margins, margins, margins, bottomMargin + margins);
            sbView.setLayoutParams(params);
            sbView.setBackgroundResource(R.drawable.snackbar_rectangle);
            sbView.setElevation(6f);
            textView.setPadding(margins,paddings, margins, paddings);
            textView.setTextColor(colorsAndDrawables.white);
            textView.setTextSize(16);
            textView.setTypeface(colorsAndDrawables.regular);
            textView.setText(text);
            snackbar.show();
        }
    }

    public static int convertDpToPixel(int dp, Context context){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
        return Math.round(px);
    }

}
