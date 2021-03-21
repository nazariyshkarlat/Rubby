package com.example.rubby.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PasswordRequestActivity;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.Snackbars;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.Database.CodeSecurityDatabase;
import com.example.rubby.Database.PasswordsDatabase;
import com.example.rubby.R;

public class PasswordRegistrationFragment extends Fragment {

    private int selectedPos;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public PasswordRegistrationFragmentHolder holder;
    private boolean buttonClick = false;
    private boolean eyeSelected;
    private TextView infoTextView;
    private TextView buttonTextView;
    private String firstPasswordS;
    private String secondPasswordS;
    private Snackbars snackbars = new Snackbars();
    private boolean onCreate = true;
    private boolean validValue = false;
    private Activity activity;
    public int passwordType;
    private PasswordsDatabase passwordsDatabase;
    private String passwordName;
    public int editType;
    public int fragmentType;
    public static final int TYPE_PASSWORD = 0;
    public static final int TYPE_PASSWORD_CODE = 1;
    public static final int CHANGE = 0;
    public static final int ADD = 1;
    public final static int TWO_STEP_AUTHENTICATION = 1;
    public final static int PASSWORD_CODE = 2;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        buttonTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoButtonTextView);
        buttonTextView.setVisibility(View.VISIBLE);
        buttonTextView.setText("Руководство по созданию надёжного пароля");
        infoTextView.setVisibility(View.GONE);
        passwordsDatabase = new PasswordsDatabase(getContext());
        activity = getActivity();
        if(passwordType == 0)
            passwordName = "пароль";
        else if(passwordType == 1)
            passwordName = "код-пароль";
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new PasswordRegistrationFragment.PasswordRegistrationFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    private void checkPassword(){
        if((firstPasswordS != null) && (secondPasswordS != null) && (firstPasswordS.length() >= 8) && (firstPasswordS.equals(secondPasswordS))) {
            validValue = true;
        }else
            validValue = false;
        if(validValue) {
            if(getActivity() instanceof SecurityActivity)
                ((SecurityActivity) getActivity()).hideKeyboard();
            else
                ((PasswordRequestActivity) getActivity()).hideKeyboard();
            if(passwordType == TYPE_PASSWORD) {
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                passwordsDatabase.setValue(PasswordsDatabase.PASSWORD, firstPasswordS);
                snackbars.snackbar(getResources(), SecurityActivity.constraintLayout, passwordName.substring(0,1).toUpperCase()+passwordName.substring(1)+" успешно изменён!", activity,false, null,0);
            }else if(passwordType == TYPE_PASSWORD_CODE) {
                if (editType == CHANGE) {
                    getActivity().onBackPressed();
                    getActivity().onBackPressed();
                    snackbars.snackbar(getResources(), SecurityActivity.constraintLayout, passwordName.substring(0,1).toUpperCase()+passwordName.substring(1)+" успешно изменён!", activity,false, null,0);
                } else if (editType == ADD) {
                    CodeSecurityDatabase codeSecurityDatabase = new CodeSecurityDatabase(getContext());
                    SecurityActivity.fragmentType = fragmentType;
                    getActivity().finish();
                    codeSecurityDatabase.setValue(CodeSecurityDatabase.ON_SWITCH,0);
                }
                passwordsDatabase.setValue(PasswordsDatabase.PASSWORD_CODE, firstPasswordS);
            }
        }
    }

    public class PasswordRegistrationFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public ConstraintLayout layout;
        public TextInputEditText editText;
        public ImageView eye;
        public TextInputLayoutCustom textInputLayoutCustom;

        public PasswordRegistrationFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.text_input_layout_outlined_item, parent, false));

            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            editText = (TextInputEditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            eye = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemIcon);
            textInputLayoutCustom = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);
            holder = this;

        }

    }

    private class PasswordRegistrationFragmentAdapter extends RecyclerView.Adapter<PasswordRegistrationFragment.PasswordRegistrationFragmentHolder> {

        @NonNull
        @Override
        public PasswordRegistrationFragment.PasswordRegistrationFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PasswordRegistrationFragment.PasswordRegistrationFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PasswordRegistrationFragment.PasswordRegistrationFragmentHolder holder, final int position) {

            if(onCreate) {
                holder.editText.setTransformationMethod(new PasswordTransformationMethod());
                holder.editText.setMaxLines(1);

                if (position == 0) {
                    holder.textInputLayoutCustom.setHint("Придумайте новый " + passwordName);
                    holder.eye.setVisibility(View.VISIBLE);
                    holder.eye.setImageResource(R.drawable.visibility_selector);
                    holder.editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    holder.editText.requestFocus();
                    if(getActivity() instanceof SecurityActivity)
                        ((SecurityActivity) getActivity()).showKeyboard();
                    else
                        ((PasswordRequestActivity) getActivity()).showKeyboard();
                } else if (position == 1) {
                    holder.textInputLayoutCustom.setHint("Повторите новый " + passwordName);
                    holder.editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    onCreate = false;
                }
                holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            buttonClick = true;
                            rAdapter.notifyItemRangeChanged(0,2);
                            checkPassword();
                            return true;
                        }
                        return false;
                    }
                });
            }

            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    selectedPos = holder.getLayoutPosition();

                    if(selectedPos == 0)
                        firstPasswordS = charSequence.toString();
                    else if(selectedPos == 1)
                        secondPasswordS = charSequence.toString();

                    if(holder.errorIcon.getVisibility() == View.VISIBLE)
                        holder.textInputLayoutCustom.setError(false,holder.subtitle,holder.errorIcon,holder.layout,null,recyclerView,selectedPos,true);

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            holder.eye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.eye.setSelected(!holder.eye.isSelected());
                    eyeSelected = holder.eye.isSelected();
                    eyeClick(holder.editText,eyeSelected);
                    holder.editText.setSelection(holder.editText.getText().length());
                    notifyItemChanged(1);
                }
            });

            if(holder.editText.isFocused())
                selectedPos = holder.getLayoutPosition();

            if(position == 1)
                eyeClick(holder.editText,eyeSelected);
            if(selectedPos == 1)
                holder.editText.setSelection(holder.editText.getText().length());

            if(buttonClick)
                setError(firstPasswordS,secondPasswordS,holder.textInputLayoutCustom,holder.errorIcon,holder.layout,holder.subtitle,position);

        }

        private void eyeClick(EditText editText ,boolean eyeSelected){
            if(!eyeSelected) {
                editText.setTransformationMethod(new PasswordTransformationMethod());
            }else {
                editText.setTransformationMethod(null);
            }
        }

        private void setError(String firstPassword, String secondPassword,TextInputLayoutCustom textInputLayoutCustom,ImageView errorIcon,ConstraintLayout layout,TextView subtitle,int position){
            if(position == 0) {
                if ((firstPassword == null) || firstPassword.length() == 0) {
                    textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Введите новый пароль!", recyclerView, selectedPos, true);
                }else if(firstPassword.length() < 8)
                    textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Не менее 8 символов!", recyclerView, selectedPos, true);
            }else if(position == 1){
                if((firstPassword != null) && (secondPasswordS != null) && (firstPassword.length() >= 8) && (!firstPassword.equals(secondPassword))) {
                    textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Пароли не совпадают!", recyclerView, selectedPos, true);
                }
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    public void buttonClick(){
        checkPassword();
        buttonClick = true;
        rAdapter.notifyItemRangeChanged(0,2);
    }

}
