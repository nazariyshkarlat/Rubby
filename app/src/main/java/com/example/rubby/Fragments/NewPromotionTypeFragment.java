package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class NewPromotionTypeFragment extends SmoothFragment {

    private String titleS[] = {"Публикация", "Публикация сообщества", "Публикация питомца", "Сторонний продукт", "Мобильное приложение"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public int selectedPos;
    private final String POS_KEY = "PROMOTION_TYPE_POS_KEY";
    private TextView infoTextView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        if(outState != null)
            selectedPos = outState.getInt(POS_KEY);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        infoTextView.setVisibility(View.GONE);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(PromotionsActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NewPromotionTypeFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POS_KEY,selectedPos);
    }

    private class NewPromotionTypeFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RadioButton radioButton;
        private ConstraintLayout layout;

        public NewPromotionTypeFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.radio_button_title_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.radioButtonTitleItemTitle);
            radioButton = (RadioButton) itemView.findViewById(R.id.radioButtonTitleItemRadioButton);
            layout = (ConstraintLayout) itemView.findViewById(R.id.radioButtonTitleItemLayout);
        }

    }

    private class NewPromotionTypeFragmentAdapter extends RecyclerView.Adapter<NewPromotionTypeFragmentHolder> {

        @NonNull
        @Override
        public NewPromotionTypeFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NewPromotionTypeFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NewPromotionTypeFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);

            holder.radioButton.setChecked(selectedPos == position);
            holder.radioButton.setClickable(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPos);
                    selectedPos = holder.getLayoutPosition();
                    holder.radioButton.setChecked(!holder.radioButton.isChecked());
                    notifyItemChanged(selectedPos);
                }
            });


        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }
}
