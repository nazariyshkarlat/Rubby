package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
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
import com.example.rubby.Activities.AppCompatActivities.PersonalizationActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.SettingsDatabase;
import com.example.rubby.R;

public class PersonalizationFragment extends SmoothFragment {

    private SettingsDatabase settingsDatabase;
    private String expandableTitle[] = {"Стандартная", "Светло-зелёная", "Тёмная", "Тёмно-зелёная", "Чёрная", "Чёрно-зелёная"};
    private String titleS[] = {"Использовать анимации", "Язык", "Тема приложения"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ThemeChangeFragment themeChangeFragment = new ThemeChangeFragment();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new PersonalizationFragmentAdapter();
        settingsDatabase = new SettingsDatabase(getContext());
        recyclerView.setAdapter(rAdapter);
        if(PersonalizationActivity.recreated) {
            PersonalizationActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.defaultFrameLayout, themeChangeFragment, null).commit();
            PersonalizationActivity.recreated = false;
        }
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    private class PersonalizationFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private SwitchCompat switchCompat;
        private ConstraintLayout layout;

        public PersonalizationFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_switch_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconSwitchTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconSwitchIcon);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.titleIconSwitchSwitch);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconSwitchLayout);
        }

    }

    private class PersonalizationFragmentAdapter extends RecyclerView.Adapter<PersonalizationFragmentHolder> {

        @NonNull
        @Override
        public PersonalizationFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PersonalizationFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PersonalizationFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);

            if (position == 0) {
                holder.switchCompat.setChecked(settingsDatabase.getValue(SettingsDatabase.ANIMATION_SWITCH) == 1);
                holder.switchCompat.setVisibility(View.VISIBLE);
            }else
                holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_right_60_24dp);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position == 0) {
                            holder.switchCompat.setChecked(!holder.switchCompat.isChecked());
                            settingsDatabase.setValue(SettingsDatabase.ANIMATION_SWITCH, holder.switchCompat.isChecked() ? 1 : 0);
                        } else if (position == 1) {
                            LanguageFragment languageFragment = new LanguageFragment();
                            PersonalizationActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, languageFragment, null).commit();
                        }else if(position == 2){
                            ThemeChangeFragment themeChangeFragment = new ThemeChangeFragment();
                            PersonalizationActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, themeChangeFragment, null).commit();
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
