package com.example.rubby.OverridedWidgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private ArrayList<Integer> positions = new ArrayList();
    private Context context;
    private final Rect mBounds = new Rect();

    public DividerItemDecorator(Drawable divider, ArrayList<Integer> positions, Context context) {
            mDivider = divider;
            this.context = context;
            this.positions = positions;
    }

        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            canvas.save();
            final int left;
            final int right;
            if (parent.getClipToPadding()) {
                left = parent.getPaddingLeft();
                right = parent.getWidth() - parent.getPaddingRight();
                canvas.clipRect(left, parent.getPaddingTop(), right,
                        parent.getHeight() - parent.getPaddingBottom());
            } else {
                left = 0;
                right = parent.getWidth();
            }

            try {
                for (int i : positions) {
                    final View child = parent.getChildAt(i);
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                    final int top = bottom - mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
            }catch (Exception e){}
            canvas.restore();
        }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (positions.contains(parent.getChildAdapterPosition(view))) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}
