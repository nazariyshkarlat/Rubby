package com.example.rubby.OverridedWidgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;

import com.example.rubby.Other.Methods;

public class RoundedCornersImageView extends android.support.v7.widget.AppCompatImageView {

    private Context context;

    public RoundedCornersImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public RoundedCornersImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RoundedCornersImageView(Context context) {
        super(context);
        this.context = context;
    }

    public void setImageDrawable(int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                resId);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
        roundedBitmapDrawable.setCornerRadius(Methods.dpToPx(14,context));
        this.setImageDrawable(roundedBitmapDrawable);
    }

}
