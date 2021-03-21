package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.SettingsDatabase;
import com.example.rubby.R;

public class StorageFragment extends SmoothFragment {

    private SettingsDatabase settingsDatabase;
    private ConstraintSet centralizeSet = new ConstraintSet();
    private String titleS[] = {"Спрашивать место сохранения", "Место сохранения файлов"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new StorageFragmentAdapter();
        settingsDatabase = new SettingsDatabase(getContext());
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    private class StorageFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private SwitchCompat switchCompat;
        private ConstraintLayout layout;

        public StorageFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle_switch_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemSubtitle);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.titleSubtitleSwitchItemSwitch);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleSwitchItemLayout);
            centralizeSet.clone(layout);
            centralizeSet.centerVertically(title.getId(), layout.getId());

        }

    }

    private class StorageFragmentAdapter extends RecyclerView.Adapter<StorageFragmentHolder> {

        @NonNull
        @Override
        public StorageFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new StorageFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final StorageFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);

            if(position == 0) {
                centralizeSet.applyTo(holder.layout);
                holder.switchCompat.setChecked(settingsDatabase.getValue(SettingsDatabase.SAVE_PLACE_SWITCH) == 1);
                holder.switchCompat.setVisibility(View.VISIBLE);
                holder.subtitle.setVisibility(View.GONE);
            }else if(position == 1) {
                holder.subtitle.setText("/storage/emulated/Download");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == 0){
                        holder.switchCompat.setChecked(!holder.switchCompat.isChecked());
                        settingsDatabase.setValue(SettingsDatabase.SAVE_PLACE_SWITCH, holder.switchCompat.isChecked() ? 1 : 0);
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
