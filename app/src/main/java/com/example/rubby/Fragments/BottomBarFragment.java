package com.example.rubby.Fragments;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.Barrier;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.v4.app.Fragment;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Arrays;

public class BottomBarFragment extends Fragment implements View.OnClickListener {

    private ArrayList<ImageView> icons;
    private Context context;
    private int selectedPos = 0;
    private ImageView firstIcon, secondIcon, thirdIcon, fourthIcon;
    private Guideline secondGuideline, thirdGuideline, firstGuideline;
    private Barrier firstBarrier, secondBarrier;
    private ConstraintLayout constraintLayout;
    private TextView firstTextView, secondTextView, thirdTextView, fourthTextView;
    private ImageView firstBackground, secondBackground, thirdBackground, fourthBackground;
    private ImageView selectedIcon;

    private int margin;

    public onItemClickCallBack onItemClickCallBack;

    public interface onItemClickCallBack {
        void onItemClick(int selectedPos);
    }

    public void registerOnItemClickCallBack(onItemClickCallBack callback) {
        this.onItemClickCallBack = callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_bar, container, false);

        constraintLayout = v.findViewById(R.id.constraintLayout);

        context = getContext();

        firstIcon = v.findViewById(R.id.bottomBarFirstIcon);
        secondIcon = v.findViewById(R.id.bottomBarSecondIcon);
        thirdIcon = v.findViewById(R.id.bottomBarThirdIcon);
        fourthIcon = v.findViewById(R.id.bottomBarFourthIcon);

        icons = new ArrayList<>(Arrays.asList(firstIcon, secondIcon, thirdIcon, fourthIcon));

        firstTextView = v.findViewById(R.id.bottomBarFirstTextView);
        secondTextView = v.findViewById(R.id.bottomBarSecondTextView);
        thirdTextView = v.findViewById(R.id.bottomBarThirdTextView);
        fourthTextView = v.findViewById(R.id.bottomBarFourthTextView);

        firstIcon.setOnClickListener(this);
        secondIcon.setOnClickListener(this);
        thirdIcon.setOnClickListener(this);
        fourthIcon.setOnClickListener(this);

        firstBackground = v.findViewById(R.id.bottomBarFirstBackground);
        secondBackground = v.findViewById(R.id.bottomBarSecondBackground);
        thirdBackground = v.findViewById(R.id.bottomBarThirdBackground);
        fourthBackground = v.findViewById(R.id.bottomBarFourthBackground);

        firstGuideline = v.findViewById(R.id.bottomBarFirstGuideline);
        secondGuideline = v.findViewById(R.id.bottomBarSecondGuideline);
        thirdGuideline = v.findViewById(R.id.bottomBarThirdGuideline);

        firstBarrier = v.findViewById(R.id.bottomBarFirstBarrier);
        secondBarrier = v.findViewById(R.id.bottomBarSecondBarrier);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        margin = (((point.x / 4)) / 2) - (convertDpToPixel(12, context));

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(fourthTextView.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, margin);
        constraintSet.applyTo(constraintLayout);
        return v;
    }

    private void alpha0(final ImageView background, final ImageView icon) {
        background.clearAnimation();
        background.animate().alpha(0F).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!icon.isSelected())
                    background.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(100).start();
    }

    private void alpha1(ImageView background) {
        background.clearAnimation();
        background.setAlpha(0F);
        background.setVisibility(View.VISIBLE);
        background.animate().alpha(1F).setDuration(100).start();
    }


    private void unSelect(ImageView icon) {
        for (ImageView imageView : icons) {
            if (imageView == icon) {
                imageView.setSelected(false);
                if (imageView == firstIcon) {
                    firstIcon.setSelected(false);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(firstTextView.getId(), ConstraintSet.START, firstIcon.getId(), ConstraintSet.END, 0);
                    constraintSet.applyTo(constraintLayout);
                    firstTextView.setVisibility(View.INVISIBLE);
                    alpha0(firstBackground, firstIcon);
                } else if (imageView == secondIcon) {
                    secondIcon.setSelected(false);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(secondBackground.getId(), ConstraintSet.START, secondTextView.getId(), ConstraintSet.START);
                    constraintSet.connect(secondIcon.getId(), ConstraintSet.START, firstBarrier.getId(), ConstraintSet.END, 0);
                    constraintSet.connect(secondIcon.getId(), ConstraintSet.END, secondGuideline.getId(), ConstraintSet.START, convertDpToPixel(12, context));
                    constraintSet.setHorizontalBias(secondIcon.getId(), 0.5F);
                    constraintSet.applyTo(constraintLayout);
                    secondTextView.setVisibility(View.INVISIBLE);
                    alpha0(secondBackground, secondIcon);
                } else if (imageView == thirdIcon) {
                    thirdIcon.setSelected(false);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(thirdBackground.getId(), ConstraintSet.START, thirdTextView.getId(), ConstraintSet.START);
                    constraintSet.connect(secondIcon.getId(), ConstraintSet.START, firstBarrier.getId(), ConstraintSet.END, 0);
                    constraintSet.connect(secondIcon.getId(), ConstraintSet.END, secondGuideline.getId(), ConstraintSet.START, convertDpToPixel(12, context));
                    constraintSet.connect(thirdIcon.getId(), ConstraintSet.START, secondGuideline.getId(), ConstraintSet.END);
                    constraintSet.connect(thirdIcon.getId(), ConstraintSet.END, secondBarrier.getId(), ConstraintSet.START, convertDpToPixel(12, context));
                    constraintSet.applyTo(constraintLayout);
                    thirdTextView.setVisibility(View.INVISIBLE);
                    alpha0(thirdBackground, thirdIcon);
                } else if (imageView == fourthIcon) {
                    fourthIcon.setSelected(false);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.connect(fourthIcon.getId(), ConstraintSet.START, thirdGuideline.getId(), ConstraintSet.END);
                    constraintSet.connect(thirdIcon.getId(), ConstraintSet.START, secondGuideline.getId(), ConstraintSet.END);
                    constraintSet.connect(thirdIcon.getId(), ConstraintSet.END, secondBarrier.getId(), ConstraintSet.START, convertDpToPixel(12, context));
                    constraintSet.connect(secondIcon.getId(), ConstraintSet.START, firstBarrier.getId(), ConstraintSet.END, 0);
                    constraintSet.connect(secondIcon.getId(), ConstraintSet.END, secondGuideline.getId(), ConstraintSet.START, convertDpToPixel(12, context));
                    constraintSet.connect(fourthBackground.getId(), ConstraintSet.START, fourthTextView.getId(), ConstraintSet.START);
                    constraintSet.connect(fourthIcon.getId(), ConstraintSet.END, constraintLayout.getId(), ConstraintSet.END, convertDpToPixel(12, context));
                    constraintSet.applyTo(constraintLayout);
                    fourthTextView.setVisibility(View.INVISIBLE);
                    alpha0(fourthBackground, fourthIcon);
                }
            }
        }
    }

    public static int convertDpToPixel(int dp, Context context){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    @Override
    public void onClick(View v) {
        selectedPos = icons.indexOf((ImageView)v);
        onItemClickCallBack.onItemClick(selectedPos);
        if(!v.isSelected()) {
            unSelect(selectedIcon);
            selectedIcon = (ImageView) v;
            if (v == firstIcon) {
                alpha1(firstBackground);
                AutoTransition borderTransition = new AutoTransition();
                borderTransition.setDuration(150);
                AutoTransition autoTransition = new AutoTransition();
                TransitionManager.beginDelayedTransition(constraintLayout, borderTransition);
                firstIcon.setSelected(true);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(firstTextView.getId(), ConstraintSet.START, firstIcon.getId(), ConstraintSet.END, convertDpToPixel(8,context));
                constraintSet.applyTo(constraintLayout);
                firstTextView.setVisibility(View.VISIBLE);
            } else if (v == secondIcon) {
                alpha1(secondBackground);
                secondIcon.setSelected(true);
                secondBackground.setVisibility(View.VISIBLE);
                AutoTransition borderTransition = new AutoTransition();
                borderTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(constraintLayout, borderTransition);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(secondBackground.getId(), ConstraintSet.START, secondIcon.getId(), ConstraintSet.START);
                constraintSet.connect(secondIcon.getId(), ConstraintSet.END, secondTextView.getId(), ConstraintSet.START, convertDpToPixel(8, context));
                constraintSet.applyTo(constraintLayout);
                secondTextView.setVisibility(View.VISIBLE);
            } else if (v == thirdIcon) {
                alpha1(thirdBackground);
                thirdIcon.setSelected(true);
                thirdBackground.setVisibility(View.VISIBLE);
                AutoTransition borderTransition = new AutoTransition();
                borderTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(constraintLayout, borderTransition);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(thirdBackground.getId(), ConstraintSet.START, thirdIcon.getId(), ConstraintSet.START);
                constraintSet.connect(secondIcon.getId(), ConstraintSet.END, thirdIcon.getId(), ConstraintSet.START);
                constraintSet.connect(secondIcon.getId(), ConstraintSet.START, firstIcon.getId(), ConstraintSet.END, convertDpToPixel(12, context));
                constraintSet.clear(thirdIcon.getId(), ConstraintSet.START);
                constraintSet.connect(thirdIcon.getId(), ConstraintSet.END, thirdTextView.getId(), ConstraintSet.START, convertDpToPixel(8, context));
                constraintSet.applyTo(constraintLayout);
                thirdTextView.setVisibility(View.VISIBLE);
            } else if (v == fourthIcon) {
                fourthIcon.setSelected(true);
                alpha1(fourthBackground);
                AutoTransition borderTransition = new AutoTransition();
                borderTransition.setDuration(150);
                TransitionManager.beginDelayedTransition(constraintLayout, borderTransition);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.clear(fourthIcon.getId(), ConstraintSet.START);
                constraintSet.connect(thirdIcon.getId(), ConstraintSet.START, secondIcon.getId(), ConstraintSet.END);
                constraintSet.connect(thirdIcon.getId(), ConstraintSet.END, secondBarrier.getId(), ConstraintSet.START, convertDpToPixel(12, context));
                constraintSet.connect(secondIcon.getId(), ConstraintSet.START, firstIcon.getId(), ConstraintSet.END, convertDpToPixel(12, context));
                constraintSet.connect(secondIcon.getId(), ConstraintSet.END, thirdIcon.getId(), ConstraintSet.START);
                constraintSet.connect(fourthBackground.getId(), ConstraintSet.START, fourthIcon.getId(), ConstraintSet.START);
                constraintSet.connect(fourthIcon.getId(), ConstraintSet.END, fourthTextView.getId(), ConstraintSet.START, convertDpToPixel(8, context));
                constraintSet.applyTo(constraintLayout);
                fourthTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}
