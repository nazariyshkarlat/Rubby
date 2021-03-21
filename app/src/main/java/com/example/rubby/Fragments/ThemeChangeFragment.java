package com.example.rubby.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.Activities.AppCompatActivities.PersonalizationActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.ThemeDatabase;
import com.example.rubby.R;

public class ThemeChangeFragment extends SmoothFragment {

    private ThemeDatabase themeDatabase;
    private int selectedPos = -1;
    private String titleS[] = {"Стандартная", "Светло-зелёная", "Тёмная", "Тёмно-зелёная", "Чёрная", "Чёрно-зелёная"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new ThemeChangeFragmentAdapter();
        themeDatabase = new ThemeDatabase(getContext());
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        selectedPos = themeDatabase.getValue(ThemeDatabase.THEME_POS);

        return v;
    }

    private class ThemeChangeFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private ConstraintLayout layout;

        public ThemeChangeFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconLayout);
        }

    }

    private class ThemeChangeFragmentAdapter extends RecyclerView.Adapter<ThemeChangeFragmentHolder> {

        @NonNull
        @Override
        public ThemeChangeFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ThemeChangeFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final ThemeChangeFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            holder.icon.setFocusable(false);

            holder.icon.setImageResource(R.drawable.ic_outline_check_selector_24dp);
            holder.icon.setSelected(selectedPos == position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rAdapter.notifyItemChanged(selectedPos);
                    selectedPos = holder.getLayoutPosition();
                    holder.icon.setSelected(!holder.icon.isSelected());
                    rAdapter.notifyItemChanged(selectedPos);
                    if (themeDatabase.getValue(ThemeDatabase.THEME_POS) != selectedPos) {
                        themeDatabase.setValue(ThemeDatabase.THEME_POS, selectedPos);
                        PersonalizationActivity.recreated = true;
                        Intent intent = new Intent(getActivity(), PersonalizationActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(0, 0);
                        PersonalizationActivity.homeActivity.recreate();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return titleS.length;
        }
    }

}
