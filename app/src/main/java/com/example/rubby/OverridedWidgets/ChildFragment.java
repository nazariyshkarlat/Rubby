package com.example.rubby.OverridedWidgets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;

public class ChildFragment extends Fragment {

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (nextAnim == 0) {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }

        Animation anim = android.view.animation.AnimationUtils.loadAnimation(getContext(), nextAnim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                // Do any process intensive work that can wait until after fragment has loaded
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        return anim;
    }

    private Bitmap b = null;
    public View v;

    @Override
    public void onPause() {
        try {
            b = loadBitmapFromView(v);
        }catch (Exception e){}
        super.onPause();
    }

    public static Bitmap loadBitmapFromView(View v){

        Bitmap b = Bitmap.createBitmap(v.getWidth(),

                v.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);

        v.layout(0, 0, v.getWidth(),

                v.getHeight());

        v.draw(c);

        return b;

    }


    @Override

    public void onDestroyView() {

        Drawable drawable = new BitmapDrawable(getResources(),b);

        v.setBackground(drawable);

        b = null;

        super.onDestroyView();

    }

}
