package com.example.rubby.OverridedWidgets;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.example.rubby.Other.ColorsAndDrawables;

public class TitleTextView extends android.support.v7.widget.AppCompatTextView {

    private String beforeString;

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTitle((Activity)context);
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTitle((Activity)context);
    }

    public TitleTextView(Context context) {
        super(context);
        setTitle((Activity)context);
    }

    private void setTitle(final Activity activity){
        ColorsAndDrawables colorsAndDrawables = new ColorsAndDrawables(activity);
        setTextColor(colorsAndDrawables.textColorPrimary);
        setTextSize(20);
        setTypeface(colorsAndDrawables.regular);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                beforeString = s.toString();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(beforeString.length() > 0 && !s.toString().equals(beforeString)) {
                    setAlpha(0.4F);
                    animate().alpha(1.0F).setDuration(400).start();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
