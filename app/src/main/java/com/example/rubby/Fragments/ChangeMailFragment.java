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
import android.text.InputType;
import android.text.TextWatcher;
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
import com.example.rubby.R;

public class ChangeMailFragment extends SmoothFragment {

    private int selectedPos;
    private TextView infoTextView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView button;
    public ChangeMailFragmentHolder holder;
    public String introducedMail;
    private String MAIL_KAY = "CHANGE_MAIL_INTRODUCED_MAIL_KEY";
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle outState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        if(outState != null)
            introducedMail = outState.getString(MAIL_KAY);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        infoTextView.setText("Введите новый электронный адрес");
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new ChangeMailFragment.ChangeMailFragmentAdapter();
        recyclerView.setAdapter(rAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MAIL_KAY,introducedMail);
    }

    public class ChangeMailFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public ConstraintLayout layout;
        public TextInputEditText editText;
        public TextInputLayoutCustom textInputLayoutCustom;

        public ChangeMailFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.text_input_layout_outlined_item, parent, false));

            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            editText = (TextInputEditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            textInputLayoutCustom = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);

        }

    }

    private class ChangeMailFragmentAdapter extends RecyclerView.Adapter<ChangeMailFragment.ChangeMailFragmentHolder> {

        @NonNull
        @Override
        public ChangeMailFragment.ChangeMailFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ChangeMailFragment.ChangeMailFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final ChangeMailFragment.ChangeMailFragmentHolder holder, final int position) {

            holder.editText.setImeOptions(EditorInfo.IME_ACTION_GO);
            holder.editText.setMaxLines(1);
            holder.textInputLayoutCustom.setHint("Электронный адрес");
            holder.editText.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
            holder.editText.setText(introducedMail);

            holder.editText.requestFocus();
            ((SecurityActivity)getActivity()).showKeyboard();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClick(introducedMail,holder.textInputLayoutCustom,holder.subtitle,holder.errorIcon,holder.layout);
                }
            });

            holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        buttonClick(introducedMail,holder.textInputLayoutCustom,holder.subtitle,holder.errorIcon,holder.layout);
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

                    introducedMail = charSequence.toString();

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


    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void buttonClick(String mail,TextInputLayoutCustom textInputLayoutCustom,TextView subtitle, ImageView errorIcon, ConstraintLayout layout){
        if(isEmailValid(mail)){
            CodeVerificationFragment codeVerificationFragment = new CodeVerificationFragment();
            codeVerificationFragment.TYPE = CodeVerificationFragment.TYPE_NEW_MAIL;
            codeVerificationFragment.snackbarText = "Эл. адрес успешно изменён!";
            codeVerificationFragment.mail = mail;
            codeVerificationFragment.change = true;
            SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,codeVerificationFragment,null).commit();
        }else
            textInputLayoutCustom.setError(true,subtitle,errorIcon,layout,"Вы ввели некорректный эл. адрес!", recyclerView,0,true);

    }

}
