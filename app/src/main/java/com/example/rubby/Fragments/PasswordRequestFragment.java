package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.Database.PasswordsDatabase;
import com.example.rubby.R;

public class PasswordRequestFragment extends SmoothFragment {

    private int selectedPos;
    private TextView infoTextView;
    private TextView buttonTextView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public PasswordChangeFirstFragmentHolder holder;
    private boolean eyeChange = false;
    private boolean validValue = false;
    private String OLD_PASSWORD_STRING_KEY = "OLD_PASSWORD_STRING_KEY";
    private String oldPasswordS;
    public int fragmentType;
    private PasswordsDatabase passwordsDatabase;
    public int passwordType;
    private String passwordName;
    private ConstraintLayout layout;
    public static final int TYPE_PASSWORD = 0;
    public static final int TYPE_PASSWORD_CODE = 1;
    private String introducedPass;
    private RecyclerView.Adapter rAdapter;
    public final static int PASSWORD_CHANGE = 0;
    public final static int TWO_STEP_AUTHENTICATION = 1;
    public final static int PASSWORD_CODE = 2;

    public onValidButtonClick onValidButtonClick;
    public onValidTextChange onValidTextChange;

    public interface onValidButtonClick {
        void onValidButtonClick();
    }

    public void registerOnValidButtonClick(onValidButtonClick callback) {
        this.onValidButtonClick = callback;
    }

    public interface onValidTextChange {
        void onValidTextChange();
    }

    public void registerOnValidTextChange(onValidTextChange callback) {
        this.onValidTextChange = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        buttonTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoButtonTextView);
        layout = (ConstraintLayout) v.findViewById(R.id.recyclerViewWithInfoLayout);
        infoTextView.setVisibility(View.GONE);
        buttonTextView.setVisibility(View.VISIBLE);
        buttonTextView.setText("Руководство по созданию надёжного пароля");
        if(outState != null)
            introducedPass = outState.getString(OLD_PASSWORD_STRING_KEY);
        passwordsDatabase = new PasswordsDatabase(SecurityActivity.context);
        if(passwordType == TYPE_PASSWORD)
           oldPasswordS = passwordsDatabase.getValue(PasswordsDatabase.PASSWORD);
        else if(passwordType == TYPE_PASSWORD_CODE)
            oldPasswordS = passwordsDatabase.getValue(PasswordsDatabase.PASSWORD_CODE);
        if(passwordType == 0)
            passwordName = "пароль";
        else if(passwordType == 1)
            passwordName = "код-пароль";
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new PasswordRequestFragment.PasswordChangeFirstFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(OLD_PASSWORD_STRING_KEY,introducedPass);
    }

    public class PasswordChangeFirstFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public ConstraintLayout layout;
        public TextInputEditText editText;
        public ImageView eye;
        public TextInputLayoutCustom textInputLayoutCustom;

        public PasswordChangeFirstFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

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

    private class PasswordChangeFirstFragmentAdapter extends RecyclerView.Adapter<PasswordRequestFragment.PasswordChangeFirstFragmentHolder> {

        @NonNull
        @Override
        public PasswordRequestFragment.PasswordChangeFirstFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PasswordRequestFragment.PasswordChangeFirstFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PasswordRequestFragment.PasswordChangeFirstFragmentHolder holder, final int position) {

            holder.editText.setImeOptions(EditorInfo.IME_ACTION_GO);
            holder.editText.setMaxLines(1);
            holder.eye.setVisibility(View.VISIBLE);
            holder.textInputLayoutCustom.setHint("Введите текущий " + passwordName);
            holder.editText.setTransformationMethod(new PasswordTransformationMethod());
            holder.editText.setText(introducedPass);

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

            holder.editText.requestFocus();
            if(getActivity() instanceof SecurityActivity)
            ((SecurityActivity)getActivity()).showKeyboard();

            holder.eye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eyeChange = true;
                    if(holder.eye.isSelected()) {
                        holder.editText.setTransformationMethod(new PasswordTransformationMethod());
                        holder.editText.setSelection(holder.editText.getText().length());
                    }else {
                        holder.editText.setTransformationMethod(null);
                        holder.editText.setSelection(holder.editText.getText().length());
                    }
                    holder.eye.setSelected(!holder.eye.isSelected());
                    eyeChange = false;
                }
            });

            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if(!eyeChange) {
                        introducedPass = charSequence.toString();
                        if (introducedPass.equals(oldPasswordS)) {
                            validValue = true;
                            if(getActivity() instanceof SecurityActivity)
                                changeFragment();
                            else {
                                SecurityActivity.fragmentType = fragmentType;
                                getActivity().finish();
                            }
                        } else {
                            validValue = false;
                        }

                        if (holder.errorIcon.getVisibility() == View.VISIBLE)
                            holder.textInputLayoutCustom.setError(false, holder.subtitle, holder.errorIcon, holder.layout, null, recyclerView, 0, true);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedPos = holder.getLayoutPosition();

                }
            });

        }

        @Override
        public int getItemCount() {

            return 1;

        }
    }

    public void buttonClick(TextInputLayoutCustom textInputLayoutCustom,TextView subtitle, ImageView errorIcon, ConstraintLayout layout){
        if(validValue){
            if(getActivity() instanceof SecurityActivity)
                changeFragment();
            else {
                getActivity().finish();
                if(fragmentType == TWO_STEP_AUTHENTICATION){
                    TwoStepAuthenticationFragment twoStepAuthenticationFragment = new TwoStepAuthenticationFragment();
                    SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, twoStepAuthenticationFragment, null).commit();
                }else if(fragmentType == PASSWORD_CODE){
                    CodeSecurityFragment codeSecurityFragment = new CodeSecurityFragment();
                    SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, codeSecurityFragment, null).commit();
                }
            }
        }else {
            textInputLayoutCustom.setError(true,subtitle,errorIcon,layout,"Вы ввели неверный пароль!", recyclerView,0,true);
        }
    }

    private void changeFragment(){
        CodeVerificationFragment codeVerificationFragment = new CodeVerificationFragment();
        codeVerificationFragment.TYPE = CodeVerificationFragment.TYPE_NUMBER;
        codeVerificationFragment.passwordType = PasswordRequestFragment.this.passwordType;
        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, codeVerificationFragment, null).commit();
        ((SecurityActivity)getActivity()).hideKeyboard();
    }

}
