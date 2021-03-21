package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.SettingsDatabase;
import com.example.rubby.R;

public class NetworkFragment extends SmoothFragment {

    private SettingsDatabase settingsDatabase;
    private String titleS[] = {"Экономия траффика", "Используемые данные"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NetworkFragmentAdapter();
        settingsDatabase = new SettingsDatabase(getContext());
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    private class NetworkFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private SwitchCompat switchCompat;
        private ConstraintLayout layout;

        public NetworkFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_switch_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconSwitchTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconSwitchIcon);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.titleIconSwitchSwitch);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconSwitchLayout);

        }

    }

    private class NetworkFragmentAdapter extends RecyclerView.Adapter<NetworkFragmentHolder> {

        @NonNull
        @Override
        public NetworkFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NetworkFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NetworkFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);

            if(position == 0) {
                holder.switchCompat.setChecked(settingsDatabase.getValue(SettingsDatabase.TRAFFIC_SAVING_SWITCH) == 1);
                holder.switchCompat.setVisibility(View.VISIBLE);
            }else if(position == 1)
                holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_right_60_24dp);
            else if(position == 2)
                holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_down_60_24dp);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == 0){
                        holder.switchCompat.setChecked(!holder.switchCompat.isChecked());
                        settingsDatabase.setValue(SettingsDatabase.TRAFFIC_SAVING_SWITCH, holder.switchCompat.isChecked() ? 1 : 0);
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
