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
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.PhoneSpinnerAdapter;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.R;

public class ChangePhoneFragment extends SmoothFragment {

    private int selectedPos;
    private TextView infoTextView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public ChangePhoneFragmentHolder holder;
    private String landCode = "";
    public String introducedNumber;
    private int countries[] = {R.drawable.ic_ua,R.drawable.ic_ru,R.drawable.ic_us,R.drawable.ic_de};
    private String numbers[] = {"+380", "+7","+1","+49"};
    private String NUMBER_KEY = "CHANGE_NUMBER_INTRODUCED_NUMBER_KEY";
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        if(outState != null)
            introducedNumber = outState.getString(NUMBER_KEY);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        infoTextView.setText("Введите новый номер телефона");
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new ChangePhoneFragment.ChangePhoneFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NUMBER_KEY,introducedNumber);
    }

    public class ChangePhoneFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public ConstraintLayout layout;
        public TextInputEditText editText;
        public Spinner spinner;
        public TextInputLayoutCustom textInputLayoutCustom;

        public ChangePhoneFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.phone_number_text_input_layout_item, parent, false));

            subtitle = (TextView) itemView.findViewById(R.id.phoneNumberTextInputLayoutItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.phoneNumberTextInputLayoutItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.phoneNumberTextInputLayoutItemLayout);
            editText = (TextInputEditText) itemView.findViewById(R.id.phoneNumberTextInputLayoutItemEditText);
            spinner = (Spinner) itemView.findViewById(R.id.phoneNumberTextInputLayoutItemSpinner);
            textInputLayoutCustom = (TextInputLayoutCustom) itemView.findViewById(R.id.phoneNumberTextInputLayoutItemInputLayout);
            spinner.setAdapter(new PhoneSpinnerAdapter(getContext(),countries,numbers));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView textView = view.findViewById(R.id.spinnerTextView);
                    landCode = textView.getText().toString();
                    editText.setText(landCode);
                    editText.setSelection(landCode.length());
                    editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(landCode.length() +10)});
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            holder = this;

        }

    }

    private class ChangePhoneFragmentAdapter extends RecyclerView.Adapter<ChangePhoneFragment.ChangePhoneFragmentHolder> {

        @NonNull
        @Override
        public ChangePhoneFragment.ChangePhoneFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ChangePhoneFragment.ChangePhoneFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final ChangePhoneFragment.ChangePhoneFragmentHolder holder, final int position) {

            holder.editText.setImeOptions(EditorInfo.IME_ACTION_GO);
            holder.editText.setMaxLines(1);
            holder.textInputLayoutCustom.setHint("Номер телефона");
            holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.editText.setText(introducedNumber);

            holder.editText.requestFocus();
            ((SecurityActivity)getActivity()).showKeyboard();

            holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        buttonClick(introducedNumber,holder.textInputLayoutCustom,holder.subtitle,holder.errorIcon,holder.layout);
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

                    introducedNumber = charSequence.toString();

                    holder.textInputLayoutCustom.setError(false,holder.subtitle,holder.errorIcon,holder.layout,null, recyclerView,0,true);

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

    public void buttonClick(String number,TextInputLayoutCustom textInputLayoutCustom,TextView subtitle, ImageView errorIcon, ConstraintLayout layout) {
        if (number != null && number.length() == landCode.length() +10) {
            CodeVerificationFragment codeVerificationFragment = new CodeVerificationFragment();
            codeVerificationFragment.TYPE = CodeVerificationFragment.TYPE_NEW_NUMBER;
            codeVerificationFragment.snackbarText = "Номер телефона успешно изменён!";
            codeVerificationFragment.number = number;
            codeVerificationFragment.change = true;
            SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, codeVerificationFragment, null).commit();
        } else
            textInputLayoutCustom.setError(true, subtitle, errorIcon, layout, "Вы ввели некорректный номер телефона!", recyclerView, 0, true);

    }

}
