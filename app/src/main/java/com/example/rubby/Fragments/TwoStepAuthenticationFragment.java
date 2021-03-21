package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.CustomDialog;
import com.example.rubby.OverridedWidgets.DividerItemDecorator;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.NumberMailDatabase;
import com.example.rubby.Database.TwoStepAuthenticationDatabase;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Collections;

public class TwoStepAuthenticationFragment extends SmoothFragment implements ListBottomSheetWithCallbackFragment.onItemClickCallBack,TwoStepAuthenticationCodeDialogAlertFragment.onPositiveButtonClickCallBack,DialogApplyCallbackFragment.onPositiveButtonClickCallBack,DialogDisableCallbackFragment.onNegativeButtonClickCallBack {

    private int selectedPos;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String[] appsAuthenticators = {"Google Authenticator", "Microsoft Authenticator","LastPass Authenticator"};
    private String[] confirmationMethods = {"Через SMS", "Через приложение-аутентификатор"};
    private TwoStepAuthenticationDatabase twoStepAuthenticationDatabase;
    private NumberMailDatabase numberMailDatabase;
    private boolean validValue = true;
    private int count = 2;
    private ConstraintSet centerSet = new ConstraintSet();
    private int firstSelectedPos;
    private int secondSelectedPos;
    private TextView infoText;
    private TextView buttonText;
    private ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder bottomSheetHolder;
    private ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentAdapter bottomSheetAdapter;
    private TwoStepAuthenticationCodeDialogAlertFragment twoStepAuthenticationCodeDialogAlertFragment;
    private ArrayList<Integer> dividerPos = new ArrayList<>(Collections.singletonList(0));
    private ListBottomSheetWithCallbackFragment listBottomSheetWithCallbackFragment = new ListBottomSheetWithCallbackFragment();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoText = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        buttonText = (TextView) v.findViewById(R.id.recyclerViewWithInfoButtonTextView);
        buttonText.setVisibility(View.VISIBLE);
        infoText.setText("При каждом входе с подозрительного устройства мы будем запрашивать код подтверждения из SMS или аутентификатора.");
        buttonText.setText("Узнайте больше о двухэтапной аутентификации");
        twoStepAuthenticationDatabase = new TwoStepAuthenticationDatabase(SecurityActivity.context);
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecorator(ResourcesCompat.getDrawable(SecurityActivity.context.getResources(), R.drawable.divider_list,getActivity().getTheme()), dividerPos,getActivity()));
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new TwoStepAuthenticationFragment.TwoStepAuthenticationFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        numberMailDatabase = new NumberMailDatabase(getContext());
        listBottomSheetWithCallbackFragment.registerOnItemClickCallBack(this);
        firstSelectedPos = twoStepAuthenticationDatabase.getValue(TwoStepAuthenticationDatabase.CONFIRMATION_METHOD_POS);
        secondSelectedPos = twoStepAuthenticationDatabase.getValue(TwoStepAuthenticationDatabase.APP_AUTHENTICATOR_POS);
        setCount(twoStepAuthenticationDatabase.getValue(TwoStepAuthenticationDatabase.AUTHENTICATION_SWITCH) == 1);

        return v;
    }

    @Override
    public void onItemClick(int position, ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentAdapter rAdapter, ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder holder,ListBottomSheetWithCallbackFragment listBottomSheetWithCallbackFragment) {

        if(selectedPos == 2) {
            firstSelectedPos = position;
            if(position == 0) {
                if (twoStepAuthenticationDatabase.getValue(TwoStepAuthenticationDatabase.CONFIRMATION_METHOD_POS) != position) {
                    twoStepAuthenticationCodeDialogAlertFragment = new TwoStepAuthenticationCodeDialogAlertFragment();
                    twoStepAuthenticationCodeDialogAlertFragment.registerOnPositiveButtonClickCallBack(this);
                    bottomSheetAdapter = rAdapter;
                    bottomSheetHolder = holder;
                    twoStepAuthenticationCodeDialogAlertFragment.activity = getActivity();
                    twoStepAuthenticationCodeDialogAlertFragment.showDialogAlert("Введите код из SMS", "Мы должны убедиться, что данный номер всё ещё у вас", "Подтвердить", "Отменить");
                    this.rAdapter.notifyItemRangeChanged(2,2);
                } else {
                    listBottomSheetWithCallbackFragment.dismiss();
                }
            }else if(position == 1){
                rAdapter.notifyItemChanged(position);
                int selectedPos = holder.getLayoutPosition();
                rAdapter.notifyItemChanged(selectedPos);
                count = 4;
                this.rAdapter.notifyItemRangeChanged(2,2);
                listBottomSheetWithCallbackFragment.dismiss();
                twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.AUTHENTICATION_SWITCH, 0);
                twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.APP_AUTHENTICATOR_POS,-1);
                validValue = false;
            }
        }else if(selectedPos == 3){
            if (twoStepAuthenticationDatabase.getValue(TwoStepAuthenticationDatabase.APP_AUTHENTICATOR_POS) != position) {
                twoStepAuthenticationCodeDialogAlertFragment = new TwoStepAuthenticationCodeDialogAlertFragment();
                twoStepAuthenticationCodeDialogAlertFragment.registerOnPositiveButtonClickCallBack(this);
                bottomSheetAdapter = rAdapter;
                bottomSheetHolder = holder;
                twoStepAuthenticationCodeDialogAlertFragment.activity = getActivity();
                twoStepAuthenticationCodeDialogAlertFragment.showDialogAlert("Введите числовой код из аутентификатора", null, "Подтвердить", "Отменить");
            } else {
                listBottomSheetWithCallbackFragment.dismiss();
            }
        }

    }

    @Override
    public void onPositiveButtonClick(int introducedCode, int code, int oldPos,TwoStepAuthenticationCodeDialogAlertFragment.TwoStepAuthenticationCodeDialogAlertFragmentHolder holder) {

            if (toString().valueOf(introducedCode).length() == 6) {

                if (introducedCode == code) {
                    twoStepAuthenticationCodeDialogAlertFragment.dialogAlert.dismiss();
                    int selectedPos = bottomSheetHolder.getLayoutPosition();
                    if(this.selectedPos == 2) {
                        firstSelectedPos = selectedPos;
                        if(selectedPos == 0) {
                            twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.AUTHENTICATION_SWITCH, 1);
                            twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.CONFIRMATION_METHOD_POS, selectedPos);
                            twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.APP_AUTHENTICATOR_POS,-1);
                            validValue = true;
                        }
                        count = 3;
                        this.rAdapter.notifyItemRangeChanged(2,2);
                    }else if(this.selectedPos == 3){
                        secondSelectedPos = selectedPos;
                        twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.AUTHENTICATION_SWITCH, 1);
                        twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.APP_AUTHENTICATOR_POS,selectedPos);
                        twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.CONFIRMATION_METHOD_POS, 1);
                        this.rAdapter.notifyItemChanged(3);
                        validValue = true;
                    }
                    listBottomSheetWithCallbackFragment.dismiss();
                }else {
                    holder.textInputLayoutCustom.setError(true, holder.subtitle, holder.errorIcon, holder.layout, "Вы ввели неверный код!", recyclerView, 0, true);
                }

            }

    }

    @Override
    public void onPositiveButtonClick(CustomDialog dialog) {
        ChangePhoneFragment changePhoneFragment = new ChangePhoneFragment();
        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,changePhoneFragment,null).commit();
        dialog.dismiss();
    }

    public void onBackClick(){
        if(!validValue) {
            DialogDisableCallbackFragment dialogDisableCallbackFragment = new DialogDisableCallbackFragment();
            dialogDisableCallbackFragment.registerOnNegativeButtonCallBack(TwoStepAuthenticationFragment.this);
            dialogDisableCallbackFragment.showDialogAlert("Покинуть настройки?", "Вы не настроили двухфакторную аутентификацию. Изменения не будут сохранены.", "Остаться", "Выйти", getActivity());
            twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.AUTHENTICATION_SWITCH, 0);
            twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.APP_AUTHENTICATOR_POS,-1);
            twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.CONFIRMATION_METHOD_POS, -1);
        }else {
            ((SecurityActivity)getActivity()).currentFragment = null;
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onNegativeButtonClick(CustomDialog dialogAlert) {
        dialogAlert.dismiss();
        ((SecurityActivity)getActivity()).currentFragment = null;
        getActivity().onBackPressed();
    }

    private class TwoStepAuthenticationFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private SwitchCompat switchCompat;
        private ConstraintLayout layout;

        public TwoStepAuthenticationFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle_switch_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemSubtitle);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.titleSubtitleSwitchItemSwitch);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleSwitchItemLayout);
            centerSet.clone(layout);
            centerSet.centerVertically(title.getId(), layout.getId());

        }

    }

    private class TwoStepAuthenticationFragmentAdapter extends RecyclerView.Adapter<TwoStepAuthenticationFragment.TwoStepAuthenticationFragmentHolder> {

        @NonNull
        @Override
        public TwoStepAuthenticationFragment.TwoStepAuthenticationFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TwoStepAuthenticationFragment.TwoStepAuthenticationFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final TwoStepAuthenticationFragment.TwoStepAuthenticationFragmentHolder holder, final int position) {
            holder.switchCompat.setClickable(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPos = holder.getLayoutPosition();
                        if (position == 0) {
                            if(!holder.switchCompat.isChecked()) {
                                holder.switchCompat.setChecked(true);
                                infoText.setVisibility(View.GONE);
                                buttonText.setVisibility(View.GONE);
                            }else {
                                holder.switchCompat.setChecked(false);
                                infoText.setVisibility(View.VISIBLE);
                                buttonText.setVisibility(View.VISIBLE);
                                twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.AUTHENTICATION_SWITCH, 0);
                                twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.APP_AUTHENTICATOR_POS,-1);
                                twoStepAuthenticationDatabase.setValue(TwoStepAuthenticationDatabase.CONFIRMATION_METHOD_POS, -1);
                                validValue = true;
                            }
                        }else if(position == 1){
                            DialogApplyCallbackFragment dialogApplyCallbackFragment = new DialogApplyCallbackFragment();
                            dialogApplyCallbackFragment.registerOnPositiveButtonCallBack(TwoStepAuthenticationFragment.this);
                            dialogApplyCallbackFragment.showDialogAlert("Номер телефона", "Изменить номер телефон можно в настройках", "Изменить", "Закрыть",getActivity());
                        }else {
                            if(count >= 3) {
                                if(position == 2) {
                                    if (!listBottomSheetWithCallbackFragment.isAdded()) {
                                        listBottomSheetWithCallbackFragment.title = "Способ подтверждения";
                                        listBottomSheetWithCallbackFragment.titleS = confirmationMethods;
                                        listBottomSheetWithCallbackFragment.selectedPos = firstSelectedPos;
                                        listBottomSheetWithCallbackFragment.show(SecurityActivity.fragmentManager, null);
                                    }} else if (position == 3) {
                                    if (!listBottomSheetWithCallbackFragment.isAdded()) {
                                        listBottomSheetWithCallbackFragment.title = "Приложение-аутентификатор";
                                        listBottomSheetWithCallbackFragment.titleS = appsAuthenticators;
                                        listBottomSheetWithCallbackFragment.selectedPos = secondSelectedPos;
                                        listBottomSheetWithCallbackFragment.show(SecurityActivity.fragmentManager, null);
                                    }
                                }
                            }
                    }
                }
            });

            if(position == 0) {
                centerSet.applyTo(holder.layout);
                holder.title.setText("Включить");
                holder.subtitle.setVisibility(View.GONE);
                holder.switchCompat.setVisibility(View.VISIBLE);
                holder.title.setMaxLines(1);
                holder.switchCompat.setChecked(twoStepAuthenticationDatabase.getValue(TwoStepAuthenticationDatabase.AUTHENTICATION_SWITCH) == 1);
                holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b) {
                            validValue = false;
                            firstSelectedPos = -1;
                            secondSelectedPos = -1;
                        }
                        setCount(b);
                        rAdapter.notifyItemRangeChanged(1,3);
                    }
                });
            }
            if(count >= 3){
                String titleS[] = {"Номер телефона", "Способ подтверждения", "Приложение-аутентификатор"};
                if(position > 0) {
                    holder.title.setText(titleS[position-1]);
                }
                if (position == 1) {
                    holder.subtitle.setText(numberMailDatabase.getValue(NumberMailDatabase.NUMBER));
                }else if(position == 2){
                    if(firstSelectedPos != -1)
                        holder.subtitle.setText(confirmationMethods[firstSelectedPos]);
                    else
                        holder.subtitle.setText("Не выбрано");
                }
                if(count == 4){
                    if(position == 3) {
                        if(secondSelectedPos != -1)
                            holder.subtitle.setText(appsAuthenticators[secondSelectedPos]);
                        else
                            holder.subtitle.setText("Не выбрано");
                    }
                }
            }

        }

        @Override
        public int getItemCount() {

            return count;

        }
    }

    private void setCount(boolean b){
        if(b) {
            if(firstSelectedPos <= 0) {
                count = 3;
            }else if(secondSelectedPos == 1){
                count = 4;
            }
        }else {
            count = 1;
        }
    }

}
