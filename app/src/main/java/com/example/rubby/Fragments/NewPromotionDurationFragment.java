package com.example.rubby.Fragments;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Database.PromotionsDatabase;
import com.example.rubby.Model.PromotionsModel;
import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.DividerItemDecorator;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.OverridedWidgets.Snackbars;
import com.example.rubby.Database.CreditCardsDatabase;
import com.example.rubby.Model.CreditCardModel;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NewPromotionDurationFragment extends SmoothFragment implements ListBottomSheetWithCallbackFragment.onItemClickCallBack,AddCreditCardBottomSheetFragment.onButtonClickCallBack{

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ImageView infoIcon;
    private CreditCardsDatabase creditCardsDatabase;
    public PromotionsModel promotionsModel;
    private ConstraintSet constraintSet = new ConstraintSet();
    private String[] paymentTypes = {"Вся сумма", "Ежедневно"};
    private ArrayList<String> creditCardNames = new ArrayList<>();
    private ArrayList<CreditCardModel> creditCardModels = new ArrayList<>();
    public boolean edit = false;
    private ArrayList<Integer> campaignStartDate = new ArrayList<>();
    private ArrayList<Integer> campaignEndDate = new ArrayList<>();
    private ListBottomSheetWithCallbackFragment listBottomSheetWithCallbackFragment = new ListBottomSheetWithCallbackFragment();
    private String[] titleS = {"Начало кампании", "Конец кампании", "Тип оплаты", "К оплате"};
    private int selectedPos;
    private TextView button;
    private boolean switchChecked;
    public int modelIndex;
    private Snackbars snackbars = new Snackbars();
    private ConstraintLayout layout;
    private ArrayList<Integer> dividerPos = new ArrayList<>(Collections.singletonList(2));
    private int paymentMethodSelectedPos = 0;
    private TextView infoTextView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {

        View v = inflater.inflate(R.layout.password_change_layout, container, false);
        layout = (ConstraintLayout) v.findViewById(R.id.passwordChangeLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.passwordChangeRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.passwordChangeInfoTextView);
        infoIcon = (ImageView) v.findViewById(R.id.passwordChangeInfoIcon);
        button = (TextView) v.findViewById(R.id.passwordChangeButton);
        infoIcon.setVisibility(View.GONE);
        infoTextView.setVisibility(View.GONE);
        constraintSet.clone(layout);
        constraintSet.connect(button.getId(), ConstraintSet.TOP, recyclerView.getId(), ConstraintSet.BOTTOM, Methods.dpToPx(10, getContext()));
        constraintSet.applyTo(layout);
        button.setText("Оплатить");
        creditCardsDatabase = new CreditCardsDatabase(getContext());
        layoutManager = new LinearLayoutManager(PromotionsActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecorator(ResourcesCompat.getDrawable(PromotionsActivity.context.getResources(), R.drawable.divider_list, getActivity().getTheme()), dividerPos, getActivity()));
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NewPromotionDurationFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        listBottomSheetWithCallbackFragment.registerOnItemClickCallBack(this);

        creditCardModels.addAll(creditCardsDatabase.addAll());

        for (CreditCardModel creditCardModel : creditCardModels)
            creditCardNames.add(creditCardModel.cardNumber);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    if (!listBottomSheetWithCallbackFragment.isAdded()) {
                        if (creditCardsDatabase.addAll().size() != 0) {
                            selectedPos = 0;
                            listBottomSheetWithCallbackFragment.title = "Способы оплаты";
                            listBottomSheetWithCallbackFragment.titleS = creditCardNames.toArray(new String[0]);
                            listBottomSheetWithCallbackFragment.selectedPos = 0;
                            listBottomSheetWithCallbackFragment.buttonText = "Оплатить";
                            listBottomSheetWithCallbackFragment.onClickListener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    listBottomSheetWithCallbackFragment.dismiss();
                                    onButtonClick();
                                }
                            };
                            listBottomSheetWithCallbackFragment.buttonIsVisible = true;
                            listBottomSheetWithCallbackFragment.show(PromotionsActivity.fragmentManager, null);
                        } else {
                            AddCreditCardBottomSheetFragment addCreditCardBottomSheetFragment = new AddCreditCardBottomSheetFragment();
                            addCreditCardBottomSheetFragment.registerOnButtonClickCallBack(NewPromotionDurationFragment.this);
                            addCreditCardBottomSheetFragment.title = "Добавте новый способ оплаты";
                            addCreditCardBottomSheetFragment.buttonText = "Оплатить";
                            addCreditCardBottomSheetFragment.show(PromotionsActivity.fragmentManager, null);
                        }
                    }
                } else {
                    if (campaignStartDate.size() == 0)
                        snackbars.snackbar(getResources(), PromotionsActivity.constraintLayout, "Выберите начальную дату!", getActivity(), false, null, 0);
                    else if(campaignEndDate == null && paymentMethodSelectedPos == 0)
                        snackbars.snackbar(getResources(), PromotionsActivity.constraintLayout, "Конечная дата может быть не выбрана только при ежедневном способе оплаты!", getActivity(), false, null, 0);
                }
            }
        });
        return v;
    }

    @Override
    public void onItemClick(int position, ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentAdapter rAdapter, ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder viewHolder, ListBottomSheetWithCallbackFragment listBottomSheetWithCallbackFragment) {
        if(selectedPos == (this.rAdapter.getItemCount() - 2)) {
            paymentMethodSelectedPos = position;
            this.rAdapter.notifyItemChanged(selectedPos);
            listBottomSheetWithCallbackFragment.dismiss();
        }
    }

    @Override
    public void onButtonClick(String cardNumber, String cardValidation, String cardSecurityNumber, String ownerName) {
        if(creditCardsDatabase.addAll().size() == 0)
            creditCardsDatabase.addItem(cardNumber, cardValidation, cardSecurityNumber, ownerName);
        onButtonClick();
    }

    private class  NewPromotionDurationFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ConstraintLayout layout;

        public NewPromotionDurationFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleItemSubtitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleItemLayout);

        }

    }

    private class  NewPromotionDurationFragmentAdapter extends RecyclerView.Adapter<NewPromotionDurationFragmentHolder> implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public NewPromotionDurationFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NewPromotionDurationFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NewPromotionDurationFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);

            if(edit && campaignStartDate.size() == 0){
                campaignStartDate = getDateFromString(promotionsModel.campaignStartDate);
                campaignEndDate = getDateFromString(promotionsModel.campaignEndDate);
                paymentMethodSelectedPos = promotionsModel.campaignPaymentMethod;
            }

            if(position == 0){
                if(campaignStartDate.size() != 0)
                    holder.subtitle.setText(getDate(campaignStartDate));
                else
                    holder.subtitle.setText("Не выбрано");
            }else if(position == 1){
                if(campaignEndDate.size() != 0)
                    holder.subtitle.setText(getDate(campaignEndDate));
                else
                    holder.subtitle.setText("Не выбрано");
            }else if(position == 2){
                holder.subtitle.setText(paymentTypes[paymentMethodSelectedPos]);
            }else if(position == 3){
                holder.subtitle.setText("$15");
                holder.layout.setBackgroundColor(getResources().getColor(R.color.null_color));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos = holder.getLayoutPosition();
                    if(position == 0 || position == 1){
                        int year,month,day;
                        java.util.Calendar c = java.util.Calendar.getInstance();
                        year = c.get(java.util.Calendar.YEAR);
                        month = c.get(java.util.Calendar.MONTH);
                        day = c.get(java.util.Calendar.DAY_OF_MONTH);
                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), NewPromotionDurationFragmentAdapter.this, year, month, day);
                        if(campaignStartDate.size() != 0 && selectedPos == 1){
                            c.set(Calendar.YEAR, campaignStartDate.get(0));
                            c.set(Calendar.MONTH, campaignStartDate.get(1));
                            c.set(Calendar.DAY_OF_MONTH, campaignStartDate.get(2)+1);
                            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
                        }else if(campaignEndDate.size() != 0 && selectedPos == 0){
                            c.set(Calendar.YEAR, campaignEndDate.get(0));
                            c.set(Calendar.MONTH, campaignEndDate.get(1));
                            c.set(Calendar.DAY_OF_MONTH, campaignEndDate.get(2)-1);
                            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                        }
                        year = c.get(java.util.Calendar.YEAR);
                        month = c.get(java.util.Calendar.MONTH);
                        day = c.get(java.util.Calendar.DAY_OF_MONTH);
                        dialog.updateDate(year,month,day);
                        dialog.show();
                    }else if(position == 2){
                        if (!listBottomSheetWithCallbackFragment.isAdded()) {
                            listBottomSheetWithCallbackFragment.title = titleS[position];
                            listBottomSheetWithCallbackFragment.titleS = paymentTypes;
                            listBottomSheetWithCallbackFragment.selectedPos = paymentMethodSelectedPos;
                            listBottomSheetWithCallbackFragment.show(PromotionsActivity.fragmentManager, null);
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if(selectedPos == 0)
                setDate(campaignStartDate,year, month, dayOfMonth);
            else if(selectedPos == 1)
                setDate(campaignEndDate,year, month, dayOfMonth);
            rAdapter.notifyItemChanged(selectedPos);
        }
    }

    private String getDate(ArrayList<Integer> arrayList){
        String year = toString().valueOf(arrayList.get(0));
        String month =String.format("%02d",arrayList.get(1));
        String day =String.format("%02d",arrayList.get(2));
        return day+ "." + month + "." +year;
    }

    private void setDate(ArrayList<Integer> arrayList, int year, int month, int day){
        arrayList.clear();
        arrayList.add(year);
        arrayList.add(month);
        arrayList.add(day);
    }

    public boolean isValid(){
        if((campaignStartDate == null) || (campaignEndDate == null && paymentMethodSelectedPos == 0))
            return false;
        else
            return true;
    }

    private ArrayList<Integer> getDateFromString(String date){
        String string = date;
        ArrayList<Integer> arrayList = new ArrayList<>();
        String day = string.substring(0,string.indexOf('.'));
        string = string.substring(string.indexOf('.')+1);
        String month = string.substring(0,string.indexOf('.'));
        string = string.substring(string.indexOf('.')+1);
        String year = string;
        arrayList.add(Integer.parseInt(year));
        arrayList.add(Integer.parseInt(month));
        arrayList.add(Integer.parseInt(day));

        return arrayList;
    }

    public void onButtonClick(){
        if(!edit) {
            ((PromotionsActivity) getActivity()).promotionsModel.campaignStartDate = getDate(campaignStartDate);
            if (campaignEndDate.size() != 0)
                ((PromotionsActivity) getActivity()).promotionsModel.campaignEndDate = getDate(campaignEndDate);
            ((PromotionsActivity) getActivity()).promotionsModel.campaignPaymentMethod = paymentMethodSelectedPos;
            ((PromotionsActivity) getActivity()).promotionsDatabase.addItemByModel(((PromotionsActivity) getActivity()).promotionsModel);
            PromotionsFragment.newPromotionCreated = true;
            for (int i = 0; i < 7; i++)
                getActivity().onBackPressed();
        }else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PromotionsDatabase.CAMPAIGN_START_DATE, getDate(campaignStartDate));
            contentValues.put(PromotionsDatabase.CAMPAIGN_END_DATE, getDate(campaignEndDate));
            contentValues.put(PromotionsDatabase.CAMPAIGN_PAYMENT_METHOD, paymentMethodSelectedPos);
            ((PromotionsActivity)getActivity()).promotionsDatabase.updateItem(contentValues, modelIndex);
            getActivity().onBackPressed();
        }
    }

}
