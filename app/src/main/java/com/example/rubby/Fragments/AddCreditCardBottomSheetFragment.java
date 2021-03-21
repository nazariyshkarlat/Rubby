package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.R;

import java.util.Calendar;

public class AddCreditCardBottomSheetFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    public String title;
    private int year;
    public String cardNumber = "";
    public String validationText = "";
    public String securityCode = "";
    public String ownerName = "";
    public String buttonText;
    private int validationBefore;
    private boolean clickError;
    private int introducedYear = 0;
    private boolean validCard = false;
    private boolean validSecurityCode = false;
    private boolean validName = false;
    private String validationBeforeText;
    private char validationBeforeDivider = '/';
    private TextView titleTextView;
    private RecyclerView.Adapter rAdapter;
    public TextView button;

    public onButtonClickCallBack onButtonClickCallBack;

    public interface onButtonClickCallBack {
        void onButtonClick(String cardNumber, String cardValidation, String cardSecurityNumber, String ownerName);
    }

    public void registerOnButtonClickCallBack(onButtonClickCallBack callback) {
        this.onButtonClickCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.list_bottom_sheet,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listBottomSheetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        titleTextView = (TextView) v.findViewById(R.id.listBottomSheetTitle);
        button = (TextView) v.findViewById(R.id.listBottomSheetButton);
        button.setVisibility(View.VISIBLE);
        button.setText(buttonText);
        titleTextView.setText(title);
        year = Calendar.getInstance().get(Calendar.YEAR) % 100;
        rAdapter = new AddCreditCardBottomSheetAdapter();
        recyclerView.setItemViewCacheSize(rAdapter.getItemCount());
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });

        return v;

    }

    private class AddCreditCardBottomSheetFirstHolder extends RecyclerView.ViewHolder{

        private TextInputEditText editText;
        private ConstraintLayout layout;
        private ImageView errorIcon;
        private TextView subtitle;
        private TextInputLayoutCustom textInputLayout;

        public AddCreditCardBottomSheetFirstHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.text_input_layout_outlined_item,parent,false));
            editText = (TextInputEditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            textInputLayout = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);
            layout.setPadding(Methods.dpToPx(16,getActivity()), Methods.dpToPx(24,getActivity()),Methods.dpToPx(16,getActivity()),0);
        }

    }

    private class AddCreditCardBottomSheetSecondHolder extends RecyclerView.ViewHolder{

        private TextView subtitle;
        private ImageView errorIcon;
        private ConstraintLayout layout;
        private TextInputEditText firstEditText;
        private TextInputEditText secondEditText;
        private TextInputLayoutCustom firstTextInputLayout;
        private TextInputLayoutCustom secondTextInputLayout;
        private ImageView eye;

        public AddCreditCardBottomSheetSecondHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.two_text_input_layout_item, parent, false));
            subtitle = (TextView) itemView.findViewById(R.id.twoTextInputLayoutItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.twoTextInputLayoutItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.twoTextInputLayoutItemLayout);
            firstEditText = (TextInputEditText) itemView.findViewById(R.id.twoTextInputLayoutItemFirstEditText);
            secondEditText = (TextInputEditText) itemView.findViewById(R.id.twoTextInputLayoutItemSecondEditText);
            firstTextInputLayout = (TextInputLayoutCustom) itemView.findViewById(R.id.twoTextInputLayoutItemFirstInputLayout);
            secondTextInputLayout = (TextInputLayoutCustom) itemView.findViewById(R.id.twoTextInputLayoutItemSecondInputLayout);
            eye = (ImageView) itemView.findViewById(R.id.twoTextInputLayoutItemIcon);
            layout.setPadding(Methods.dpToPx(16,getActivity()), Methods.dpToPx(24,getActivity()),Methods.dpToPx(16,getActivity()),0);
        }

    }

    private class AddCreditCardBottomSheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            switch (viewType) {
                case 0:
                    return new AddCreditCardBottomSheetFirstHolder(layoutInflater, parent);
                case 2:
                    return new AddCreditCardBottomSheetSecondHolder(layoutInflater, parent);
                    default:
                        return null;
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position % 2 * 2;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

            switch (holder.getItemViewType()) {
                case 0:
                    AddCreditCardBottomSheetFirstHolder addCreditCardBottomSheetFirstHolder = (AddCreditCardBottomSheetFirstHolder) holder;
                    if (position == 0) {
                        ((AddCreditCardBottomSheetFirstHolder) holder).textInputLayout.setHint("Номер карты");
                        ((AddCreditCardBottomSheetFirstHolder) holder).editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        ((AddCreditCardBottomSheetFirstHolder) holder).editText.setKeyListener(DigitsKeyListener.getInstance("0123456789 "));
                        ((AddCreditCardBottomSheetFirstHolder) holder).editText.setNextFocusDownId(R.id.twoTextInputLayoutItemFirstEditText);
                        ((AddCreditCardBottomSheetFirstHolder) holder).editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(!hasFocus && cardNumber.length() < 16){
                                    validCard = false;
                                    ((AddCreditCardBottomSheetFirstHolder) holder).textInputLayout.setError(true, ((AddCreditCardBottomSheetFirstHolder) holder).subtitle, ((AddCreditCardBottomSheetFirstHolder) holder).errorIcon, ((AddCreditCardBottomSheetFirstHolder) holder).layout, "Обязательное поле!", recyclerView, -1, true);
                                }else if(cardNumber.length() == 16)
                                    validCard = true;
                            }
                        });
                        ((AddCreditCardBottomSheetFirstHolder) holder).editText.addTextChangedListener(new TextWatcher() {

                            private static final int TOTAL_SYMBOLS = 19;
                            private static final int TOTAL_DIGITS = 16;
                            private static final int DIVIDER_MODULO = 5;
                            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1;
                            private static final char DIVIDER = ' ';
                            private int before;
                            private int after;

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                if(s.toString().endsWith(" "))
                                    before = s.length();
                                else
                                    before = 0;
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                cardNumber = s.toString().replace(" ", "");
                                ((AddCreditCardBottomSheetFirstHolder) holder).textInputLayout.setError(false, ((AddCreditCardBottomSheetFirstHolder) holder).subtitle, ((AddCreditCardBottomSheetFirstHolder) holder).errorIcon, ((AddCreditCardBottomSheetFirstHolder) holder).layout, null, recyclerView, -1, true);
                                if(s.length() == 19)
                                    validCard = true;
                                else
                                    validCard = false;
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                after = s.length();
                                if(after < before) {
                                    ((AddCreditCardBottomSheetFirstHolder) holder).editText.setText(s.toString().substring(0, s.length() - 1));
                                    ((AddCreditCardBottomSheetFirstHolder) holder).editText.setSelection(after);
                                }
                                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                                }
                            }
                        });
                        if(clickError){
                            clickError((AddCreditCardBottomSheetFirstHolder) holder);
                        }else {
                            if(cardNumber.length() != 0) {
                                ((AddCreditCardBottomSheetFirstHolder) holder).editText.setText(cardNumber);
                                ((AddCreditCardBottomSheetFirstHolder) holder).editText.setSelection(cardNumber.length() + 3);
                                validCard = true;
                                introducedYear = Integer.parseInt(validationText.substring(validationText.indexOf("/") + 1));
                                validSecurityCode = true;
                                validName = true;
                            }
                        }
                    }else if (position == 2) {
                        ((AddCreditCardBottomSheetFirstHolder) holder).textInputLayout.setHint("Имя владельца");
                        ((AddCreditCardBottomSheetFirstHolder) holder).editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                ((AddCreditCardBottomSheetFirstHolder) holder).textInputLayout.setError(false, ((AddCreditCardBottomSheetFirstHolder) holder).subtitle, ((AddCreditCardBottomSheetFirstHolder) holder).errorIcon, ((AddCreditCardBottomSheetFirstHolder) holder).layout, null, recyclerView, -1, true);
                                ownerName = s.toString();
                                if(s.toString().length() > 0 && !s.toString().startsWith(" ")) {
                                    validName = true;
                                }else{
                                    validName = false;
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        if(clickError){
                            clickError((AddCreditCardBottomSheetFirstHolder) holder);
                        }else {
                            ((AddCreditCardBottomSheetFirstHolder) holder).editText.setText(ownerName);
                        }
                    }
                    break;
                case 2:
                    AddCreditCardBottomSheetSecondHolder addCreditCardBottomSheetSecondHolder = (AddCreditCardBottomSheetSecondHolder) holder;
                    if (position == 1) {
                        if(clickError){
                            clickError((AddCreditCardBottomSheetSecondHolder) holder);
                        }else {
                            ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.setText(validationText);
                            ((AddCreditCardBottomSheetSecondHolder) holder).secondEditText.setText(securityCode);
                        }
                        ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setHint("Срок действия");
                        ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.setLongClickable(false);
                        ((AddCreditCardBottomSheetSecondHolder) holder).secondTextInputLayout.setHint("CVV/CVC");
                        ((AddCreditCardBottomSheetSecondHolder) holder).secondEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
                        ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789/"));


                        ((AddCreditCardBottomSheetSecondHolder) holder).secondEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                securityCode = s.toString();
                                if(s.length() == 3){
                                    validSecurityCode = true;
                                }else {
                                    validSecurityCode = false;
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


                        ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                if(s != null)
                                    validationBefore = s.toString().length();
                                else
                                    validationBefore = 0;
                                validationBeforeText = s.toString();
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                               setValidation(s.toString(), ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText,((AddCreditCardBottomSheetSecondHolder) holder));
                        }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                    ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    ((AddCreditCardBottomSheetSecondHolder) holder).secondEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
            }
        }

        @Override
        public int getItemCount() {

            return 3;

        }
    }



    private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
        boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
        for (int i = 0; i < s.length(); i++) { // check that every element is right
            if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    private void setValidation(String string, EditText editText, RecyclerView.ViewHolder holder) {
        if (string != null && string.length() > 0) {
            if (string.length() == 1 && string.contains("/")) {
                editText.setText("");
                ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setError(true, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, "Обязательное поле!", recyclerView, -1, true);
            } else if (string.length() == 1 && !string.contains("/")) {
                if (((AddCreditCardBottomSheetSecondHolder) holder).subtitle.getText().equals("Обязательное поле!"))
                    ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setError(false, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, null, recyclerView, -1, true);
            }
            if (validationBefore < string.length()) {
                if (string.startsWith("0") && !string.contains("/")) {
                    editText.setText(string.substring(0, 1) + "/" + string.substring(1));
                    editText.setSelection(1);
                }
                if (!string.startsWith("/") && Integer.parseInt(toString().valueOf(string.charAt(0))) > 1) {
                    if (string.length() == 1)
                        editText.setText(string.substring(0, 1) + "/" + string.substring(1));
                    if (!string.contains("/"))
                        editText.setSelection(string.length() + 1);
                    else
                        editText.setSelection(string.length());
                }

                if (string.startsWith("00")) {
                    editText.setText("0" + string.substring(2));
                    editText.setSelection(1);
                } else if (string.startsWith("0") && !string.contains("/")) {
                    editText.setText("0/" + string.substring(1));
                    editText.setSelection(1);
                } else if (string.startsWith("0") && string.contains("/") && string.indexOf("/") > 1) {
                    editText.setSelection(string.length());
                } else if (string.startsWith("1")) {
                    if (!string.contains("/")) {
                        editText.setText(string + "/");
                        editText.setSelection(1);
                    }
                    if (string.length() == 3) {
                        if (string.startsWith("1") && Integer.parseInt(toString().valueOf(string.charAt(1))) <= 2) {
                            editText.setSelection(string.length());
                        } else if (Integer.parseInt(toString().valueOf(string.charAt(1))) > 2) {
                            editText.setText("1" + "/" + string.substring(1, 2));
                            editText.setSelection(string.length());
                        }
                    }
                }
            } else {
                if (validationBeforeText.contains("/")) {
                    int dividerIndex = validationBeforeText.indexOf('/');
                    if (dividerIndex != 0) {
                        if (dividerIndex == ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.getSelectionStart()) {
                            ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.setText(validationBeforeText.substring(0, dividerIndex - 1) + "/" + validationBeforeText.substring(dividerIndex + 1));
                            ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.setSelection(dividerIndex - 1);
                        }
                    }
                }
            }
            if (string.contains("/")) {
                if ((string.length() - (string.indexOf("/") + 1)) >= 1) {
                    introducedYear = Integer.parseInt(string.substring(string.indexOf("/") + 1));
                    if (!introducedYearIsValid(introducedYear)) {
                        ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setError(true, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, "Год указан неверно!", recyclerView, -1, true);
                        introducedYear = 0;
                    }if (!introducedMonthIsValid(string)) {
                        ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setError(true, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, "Месяц указан неверно!", recyclerView, -1, true);
                    }}else {
                    introducedYear = 0;
                }}else {
                introducedYear = 0;
            }
            if (((AddCreditCardBottomSheetSecondHolder) holder).errorIcon.getVisibility() == View.VISIBLE) {
                if (introducedYearIsValid(introducedYear) && introducedMonthIsValid(string)) {
                    ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setError(false, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, null, recyclerView, -1, true);
                }
            }
        }
            if(string.contains("/")){
                if(introducedMonthIsValid(string))
                    ((AddCreditCardBottomSheetSecondHolder) holder).firstEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(string.indexOf(validationBeforeDivider) + 1 + 2)});
        }else {
            ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setError(false, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, null, recyclerView, -1, true);
        }
        validationText = string;

    }

    private boolean introducedMonthIsValid(String string){
        return (string.indexOf(validationBeforeDivider) > 0 && (!string.startsWith("0") || (string.startsWith("0") && string.indexOf(validationBeforeDivider) == 2) || !string.startsWith("0")));
    }

    private boolean introducedYearIsValid(int year ){
        return year >= this.year && year < (this.year + 21);
    }

    private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }

    private void clickError(RecyclerView.ViewHolder holder){
        if(holder instanceof AddCreditCardBottomSheetFirstHolder)
            ((AddCreditCardBottomSheetFirstHolder) holder).textInputLayout.setError(true, ((AddCreditCardBottomSheetFirstHolder) holder).subtitle, ((AddCreditCardBottomSheetFirstHolder) holder).errorIcon, ((AddCreditCardBottomSheetFirstHolder) holder).layout, "Обязательное поле", recyclerView, -1, true);
        else {
            if (!introducedMonthIsValid(validationText) || !introducedYearIsValid(introducedYear))
                ((AddCreditCardBottomSheetSecondHolder) holder).firstTextInputLayout.setError(true, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, "Обязательное поле", recyclerView, -1, true);
            if (!validSecurityCode) {
                ((AddCreditCardBottomSheetSecondHolder) holder).secondTextInputLayout.setError(true, ((AddCreditCardBottomSheetSecondHolder) holder).subtitle, ((AddCreditCardBottomSheetSecondHolder) holder).errorIcon, ((AddCreditCardBottomSheetSecondHolder) holder).layout, "Обязательное поле", recyclerView, -1, true);
            }
        }
    }

    private boolean isValid(){
        if(validName && validCard && validSecurityCode && dateIsValid())
            return  true;
        else
            return false;
    }

    private boolean dateIsValid(){
        return introducedMonthIsValid(validationText) && introducedYearIsValid(introducedYear);
    }

    private void buttonClick(){
        if(isValid()){
            onButtonClickCallBack.onButtonClick(cardNumber, validationText, securityCode, ownerName);
            this.dismiss();
        }else {
            clickError = true;
            if (!validCard) {
                rAdapter.notifyItemChanged(0);
            }
            if (!introducedMonthIsValid(validationText) || !introducedYearIsValid(introducedYear) || !validSecurityCode) {
                rAdapter.notifyItemChanged(1);
            }
            if (!validName) {
                rAdapter.notifyItemChanged(2);
            }
        }
    }

}


