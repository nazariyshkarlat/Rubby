package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.R;

public class AccountEditFragment extends Fragment {

    private String titleS[] = {"Редактировать профиль", "Личные данные", "Настройки приватности"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new AccountEditFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    private class AccountEditFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private ConstraintLayout layout;

        public AccountEditFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconLayout);

        }

    }

    private class AccountEditFragmentAdapter extends RecyclerView.Adapter<AccountEditFragment.AccountEditFragmentHolder> {

        @NonNull
        @Override
        public AccountEditFragment.AccountEditFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AccountEditFragment.AccountEditFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final AccountEditFragment.AccountEditFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_right_60_24dp);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(position == 0){
                        ProfileEditFragment profileEditFragment = new ProfileEditFragment();
                        AccountEditActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,profileEditFragment,null).commit();
                    }else if(position == 1){
                        PersonalDataFragment personalDataFragment = new PersonalDataFragment();
                        AccountEditActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,personalDataFragment,null).commit();
                    }else if(position == 2){
                        SafetyFragment safetyFragment = new SafetyFragment();
                        AccountEditActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,safetyFragment,null).commit();
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
