package com.example.rubby.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import com.example.rubby.Activities.AppCompatActivities.PasswordRequestActivity;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.CodeSecurityDatabase;
import com.example.rubby.R;

public class SecurityFragment extends SmoothFragment {

    private String titleS[] = {"Изменить пароль", "Телефон и эл. адрес", "Защита код-пароелм", "Двухэтапная аутентификация", "Чёрный список" , "История активности"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public static int selectedPos;
    private CodeSecurityDatabase codeSecurityDatabase;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new SecurityFragment.SecurityFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        codeSecurityDatabase = new CodeSecurityDatabase(SecurityActivity.context);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    private class SecurityFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private ConstraintLayout layout;

        public SecurityFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconLayout);

        }

    }

    private class SecurityFragmentAdapter extends RecyclerView.Adapter<SecurityFragment.SecurityFragmentHolder> {

        @NonNull
        @Override
        public SecurityFragment.SecurityFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SecurityFragment.SecurityFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final SecurityFragment.SecurityFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_right_60_24dp);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPos = holder.getLayoutPosition();
                    if(position == 0){
                        PasswordRequestFragment passwordRequestFragment = new PasswordRequestFragment();
                        passwordRequestFragment.passwordType = PasswordRequestFragment.TYPE_PASSWORD;
                        passwordRequestFragment.fragmentType = PasswordRequestFragment.PASSWORD_CHANGE;
                        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, passwordRequestFragment,null).commit();
                    }else if(position == 1){
                        NumberMailFragment numberMailFragment = new NumberMailFragment();
                        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,numberMailFragment,null).commit();
                    }else if(position == 2){
                        if (codeSecurityDatabase.getValue(CodeSecurityDatabase.ON_SWITCH) == -1) {
                            PasswordRegistrationFragment passwordRegistrationFragment = new PasswordRegistrationFragment();
                            passwordRegistrationFragment.passwordType = PasswordRegistrationFragment.TYPE_PASSWORD_CODE;
                            passwordRegistrationFragment.fragmentType = PasswordRegistrationFragment.PASSWORD_CODE;
                            passwordRegistrationFragment.editType = PasswordRegistrationFragment.ADD;
                            PasswordRequestActivity.fragment = passwordRegistrationFragment;
                            Intent intent = new Intent(getActivity(), PasswordRequestActivity.class);
                            startActivity(intent);
                        } else {
                            PasswordRequestFragment passwordRequestFragment = new PasswordRequestFragment();
                            passwordRequestFragment.passwordType = PasswordRequestFragment.TYPE_PASSWORD_CODE;
                            passwordRequestFragment.fragmentType = PasswordRequestFragment.PASSWORD_CODE;
                            PasswordRequestActivity.fragment = passwordRequestFragment;
                            Intent intent = new Intent(getActivity(), PasswordRequestActivity.class);
                            startActivity(intent);
                        }
                    }else if(position == 3){
                        PasswordRequestFragment passwordRequestFragment = new PasswordRequestFragment();
                        passwordRequestFragment.passwordType = PasswordRequestFragment.TYPE_PASSWORD;
                        passwordRequestFragment.fragmentType = PasswordRequestFragment.TWO_STEP_AUTHENTICATION;
                        PasswordRequestActivity.fragment = passwordRequestFragment;
                        Intent intent = new Intent(getActivity(), PasswordRequestActivity.class);
                        startActivity(intent);
                    }else if(position == 4){
                        BlackListFragment blackListFragment = new BlackListFragment();
                        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,blackListFragment,null).commit();
                    }else if(position == 5){
                        HistoryFragment historyFragment = new HistoryFragment();
                        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,historyFragment,null).commit();
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
