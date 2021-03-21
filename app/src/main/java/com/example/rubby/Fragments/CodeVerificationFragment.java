package com.example.rubby.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.HomeActivity;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.OverridedWidgets.Snackbars;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.Database.NumberMailDatabase;
import com.example.rubby.R;

public class CodeVerificationFragment extends SmoothFragment {

    private TextView infoTextView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView buttonTextView;
    public CodeVerificationFragmentHolder holder;
    private int codeI = 123456;
    private int introducedCode = 1234567;
    public final static String CODE_VERIFICATION_CODE_STRING_KEY = "CODE_VERIFICATION_CODE_STRING_KEY";
    private boolean validValue = false;
    private NumberMailDatabase numberMailDatabase;
    private Snackbars snackbars = new Snackbars();
    public String snackbarText;
    private RecyclerView.Adapter rAdapter;
    public int TYPE;
    public String mail;
    public String number;
    public boolean change = false;
    public int passwordType = 0;
    public final static int TYPE_NUMBER = 0;
    public final static int TYPE_NEW_NUMBER = 1;
    public final static int TYPE_NEW_MAIL = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        if(outState != null)
            introducedCode = outState.getInt(CODE_VERIFICATION_CODE_STRING_KEY);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        buttonTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoButtonTextView);
        buttonTextView.setVisibility(View.VISIBLE);
        numberMailDatabase = new NumberMailDatabase(getContext());
        if(number == null) {
            number = numberMailDatabase.getCodeNumber(numberMailDatabase.getValue(NumberMailDatabase.NUMBER));
        }else if(mail == null){
            mail = numberMailDatabase.getCodeMail(numberMailDatabase.getValue(NumberMailDatabase.MAIL));
        }
        if(TYPE == TYPE_NUMBER)
            infoTextView.setText("Мы отправили код подтверждения на привязанный к учётной записи номер телефона (" + number + ")");
        else if(TYPE == TYPE_NEW_NUMBER)
            infoTextView.setText("Мы отправили код подтверждения на новый номер телефона (" + number + ")");
        else if(TYPE == TYPE_NEW_MAIL)
            infoTextView.setText("Мы отправили код подтверждения на новый эл. адрес  (" + mail +")");
        buttonTextView.setText("Отправить код повторно");
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new CodeVerificationFragment.CodeVerificationFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CODE_VERIFICATION_CODE_STRING_KEY,introducedCode);
    }

    public class CodeVerificationFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public ConstraintLayout layout;
        public TextInputEditText editText;
        public TextInputLayoutCustom textInputLayoutCustom;

        public CodeVerificationFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.text_input_layout_outlined_item, parent, false));

            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            editText = (TextInputEditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            textInputLayoutCustom = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);
            holder = this;

        }

    }

    public void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private class CodeVerificationFragmentAdapter extends RecyclerView.Adapter<CodeVerificationFragment.CodeVerificationFragmentHolder> {

        @NonNull
        @Override
        public CodeVerificationFragment.CodeVerificationFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CodeVerificationFragment.CodeVerificationFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final CodeVerificationFragment.CodeVerificationFragmentHolder holder, final int position) {

            holder.editText.setImeOptions(EditorInfo.IME_ACTION_GO);
            holder.editText.setMaxLines(1);
            holder.textInputLayoutCustom.setHint("Введите код подтверждения");
            holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});
            if(toString().valueOf(introducedCode).length() <= 6)
                holder.editText.setText(toString().valueOf(introducedCode));

            holder.editText.requestFocus();
            showKeyboard();

            holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        buttonClick(holder.textInputLayoutCustom,holder.subtitle,holder.errorIcon,holder.layout);
                        return true;
                    }
                    return false;
                }
            });

            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (holder.errorIcon.getVisibility() == View.VISIBLE)
                        holder.textInputLayoutCustom.setError(false, holder.subtitle, holder.errorIcon, holder.layout, null, recyclerView, 0, true);
                    try {
                        introducedCode = Integer.parseInt(charSequence.toString());
                    }catch (Exception e){}
                    if(charSequence.length() == 6) {
                        if (introducedCode == codeI) {
                            validValue= true;
                            if (holder.errorIcon.getVisibility() == View.VISIBLE)
                                holder.textInputLayoutCustom.setError(false, holder.subtitle, holder.errorIcon, holder.layout, null, recyclerView, 0, true);
                            buttonClick(holder.textInputLayoutCustom,holder.subtitle,holder.errorIcon,holder.layout);
                        } else {
                            validValue = false;
                            holder.textInputLayoutCustom.setError(true, holder.subtitle, holder.errorIcon, holder.layout, "Вы ввели неверный код!", recyclerView, 0, true);
                        }
                    }else {
                        validValue = false;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

        @Override
        public int getItemCount() {

            return 1;

        }

    }

    private void changeFragment(){
        if(snackbarText == null) {
            PasswordRegistrationFragment passwordRegistrationFragment = new PasswordRegistrationFragment();
            passwordRegistrationFragment.passwordType = CodeVerificationFragment.this.passwordType;
            SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, passwordRegistrationFragment, null).commit();
        }else
            getActivity().finish();
    }

    public void buttonClick(TextInputLayoutCustom textInputLayoutCustom,TextView subtitle, ImageView errorIcon, ConstraintLayout layout){
        if(validValue){
            changeFragment();
            if(snackbarText != null) {
                snackbars.snackbar(getResources(), HomeActivity.constraintLayout, snackbarText, getActivity(), false, null, HomeActivity.bottomBar.getMeasuredHeight());
            }
            if(change){
                if (number != null) {
                    numberMailDatabase.setValue(NumberMailDatabase.NUMBER,number);
                }else
                    numberMailDatabase.setValue(NumberMailDatabase.MAIL,mail);
            }
        }else {
            textInputLayoutCustom.setError(true,subtitle,errorIcon,layout,"Вы ввели неверный код!", recyclerView,0,true);
        }
    }

}
