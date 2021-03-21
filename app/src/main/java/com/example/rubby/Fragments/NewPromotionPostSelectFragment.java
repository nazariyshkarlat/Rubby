package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class NewPromotionPostSelectFragment extends SmoothFragment {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public int selectedPos = -1;
    private String POS_KEY = "PROMOTIONS_POST_SELECT_POS_KEY";
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {
        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        if(outState != null)
            selectedPos = outState.getInt(POS_KEY);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(PromotionsActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NewPromotionPostSelectFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POS_KEY,selectedPos);
    }

    private class NewPromotionPostSelectFragmentHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView timeLocation;
        private TextView subtitle;
        private TextView likeTextView;
        private TextView commentTextView;
        private ImageView postImage;
        private RadioButton radioButton;

        public NewPromotionPostSelectFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.post_select_item, parent, false));
            name = (TextView) itemView.findViewById(R.id.postSelectItemName);
            timeLocation = (TextView) itemView.findViewById(R.id.postSelectItemTimeLocation);
            subtitle = (TextView) itemView.findViewById(R.id.postSelectItemSubtitle);
            postImage = (ImageView) itemView.findViewById(R.id.postSelectItemPostImage);
            likeTextView = (TextView) itemView.findViewById(R.id.postSelectItemLikeTextView);
            commentTextView = (TextView) itemView.findViewById(R.id.postSelectItemCommentTextView);
            radioButton = (RadioButton) itemView.findViewById(R.id.postSelectItemRadioButton);
        }

    }

    private class NewPromotionPostSelectFragmentAdapter extends RecyclerView.Adapter<NewPromotionPostSelectFragmentHolder> {

        @NonNull
        @Override
        public NewPromotionPostSelectFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NewPromotionPostSelectFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NewPromotionPostSelectFragmentHolder holder, final int position) {


            holder.name.setText("valenoc");
            holder.timeLocation.setText("14:41, Харьков");
            setTags(holder.subtitle, "Действительно, LA прекрасен #usa #la #hlyshtitsamazing");
            holder.likeTextView.setText("123");
            holder.commentTextView.setText("45");
            holder.radioButton.setChecked(selectedPos == position);
            holder.radioButton.setClickable(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPos);
                    selectedPos = holder.getLayoutPosition();
                    holder.radioButton.setChecked(!holder.radioButton.isChecked());
                    notifyItemChanged(selectedPos);
                    ((PromotionsActivity)getActivity()).toolbarSecondButton.setVisibility(View.VISIBLE);
                }
            });

        }

        @Override
        public int getItemCount() {

            return 10;

        }
    }

    private void setTags(TextView pTextView, String pTagString) {
        SpannableString string = new SpannableString(pTagString);

        int start = -1;
        for (int i = 0; i < pTagString.length(); i++) {
            if (pTagString.charAt(i) == '#') {
                start = i;
            } else if (pTagString.charAt(i) == ' ' || (i == pTagString.length() - 1 && start != -1)) {
                if (start != -1) {
                    if (i == pTagString.length() - 1) {
                        i++;
                    }
                    string.setSpan(new ForegroundColorSpan(((PromotionsActivity)getActivity()).colorsAndDrawables.colorAccent), start, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = -1;
                }
            }
        }
        pTextView.setText(string);
    }

}
