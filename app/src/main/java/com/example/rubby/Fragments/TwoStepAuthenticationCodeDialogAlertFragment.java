package com.example.rubby.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.CustomDialog;
import com.example.rubby.Other.Methods;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.TextInputLayoutCustom;
import com.example.rubby.R;

public class TwoStepAuthenticationCodeDialogAlertFragment extends DialogFragment {

    public CustomDialog dialogAlert;
    private int selectedPos;
    private TextView positiveButton;
    private TextView negativeButton;
    private TextView title;
    private TextView subtitle;
    public Activity activity;
    private int codeI = 123456;
    private int introducedCode = 1234567;
    private boolean validValue = false;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter dialogAlertRecyclerViewAdapter;
    private RecyclerView.Adapter parentRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    public onPositiveButtonClickCallBack onPositiveButtonClickCallBack;

    public interface onPositiveButtonClickCallBack {
        void onPositiveButtonClick(int introducedCode, int code, int oldPos,TwoStepAuthenticationCodeDialogAlertFragmentHolder holder);
    }

    public void registerOnPositiveButtonClickCallBack(onPositiveButtonClickCallBack callback) {
        this.onPositiveButtonClickCallBack = callback;
    }


    public void showDialogAlert(String title, String subtitle, String positiveText, String negativeText){
        dialogAlert = new CustomDialog(activity);
        dialogAlert.setContentView(R.layout.dialog_alert);
        dialogAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = dialogAlert.findViewById(R.id.dialogAlertRecyclerView);
        this.title = dialogAlert.findViewById(R.id.dialogAlertTitle);
        this.subtitle = dialogAlert.findViewById(R.id.dialogAlertSubtitle);
        if(subtitle != null)
            this.subtitle.setVisibility(View.VISIBLE);
        positiveButton = dialogAlert.findViewById(R.id.dialogAlertPositiveButton);
        negativeButton = dialogAlert.findViewById(R.id.dialogAlertNegativeButton);
        this.title.setText(title);
        this.subtitle.setText(subtitle);
        positiveButton.setText(positiveText);
        negativeButton.setText(negativeText);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dialogAlertRecyclerViewAdapter = new TwoStepAuthenticationCodeDialogAlertFragment.TwoStepAuthenticationCodeDialogAlertFragmentAdapter();
        recyclerView.setAdapter(dialogAlertRecyclerViewAdapter);

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAlert.dismiss();
            }
        });
        dialogAlert.show();
    }

    public class TwoStepAuthenticationCodeDialogAlertFragmentHolder extends RecyclerView.ViewHolder {

        public TextView subtitle;
        public ImageView errorIcon;
        public ConstraintLayout layout;
        public TextInputEditText editText;
        public TextInputLayoutCustom textInputLayoutCustom;

        public TwoStepAuthenticationCodeDialogAlertFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.text_input_layout_outlined_item, parent, false));

            subtitle = (TextView) itemView.findViewById(R.id.textInputLayoutOutlinedItemSubtitle);
            errorIcon = (ImageView) itemView.findViewById(R.id.textInputLayoutOutlinedItemErrorIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.textInputLayoutOutlinedItemLayout);
            editText = (TextInputEditText) itemView.findViewById(R.id.textInputLayoutOutlinedItemEditText);
            textInputLayoutCustom = (TextInputLayoutCustom) itemView.findViewById(R.id.textInputLayoutOutlinedItemInputLayout);
            layout.setPadding(Methods.dpToPx(16,activity), Methods.dpToPx(24,activity),Methods.dpToPx(16,activity),0);

        }

    }

    public class TwoStepAuthenticationCodeDialogAlertFragmentAdapter extends RecyclerView.Adapter<TwoStepAuthenticationCodeDialogAlertFragment.TwoStepAuthenticationCodeDialogAlertFragmentHolder> {

        @NonNull
        @Override
        public TwoStepAuthenticationCodeDialogAlertFragment.TwoStepAuthenticationCodeDialogAlertFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            return new TwoStepAuthenticationCodeDialogAlertFragment.TwoStepAuthenticationCodeDialogAlertFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final TwoStepAuthenticationCodeDialogAlertFragment.TwoStepAuthenticationCodeDialogAlertFragmentHolder holder, final int position) {

            holder.editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            holder.editText.setMaxLines(1);
            holder.textInputLayoutCustom.setHint("Введите код подтверждения");
            holder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            if (toString().valueOf(introducedCode).length() <= 6)
                holder.editText.setText(toString().valueOf(introducedCode));

            holder.editText.requestFocus();
            ((SecurityActivity)activity).showKeyboard();

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPositiveButtonClickCallBack.onPositiveButtonClick(codeI, introducedCode, selectedPos, holder);
                }
            });

            holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        onPositiveButtonClickCallBack.onPositiveButtonClick(codeI, introducedCode, selectedPos, holder);
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
                    } catch (Exception e) {
                    }
                    if (charSequence.length() == 6) {
                        if (introducedCode == codeI) {
                            validValue = true;
                            if (holder.errorIcon.getVisibility() == View.VISIBLE)
                                holder.textInputLayoutCustom.setError(false, holder.subtitle, holder.errorIcon, holder.layout, null, recyclerView, 0, true);
                            onPositiveButtonClickCallBack.onPositiveButtonClick(codeI, introducedCode, selectedPos, holder);
                        } else {
                            validValue = false;
                            holder.textInputLayoutCustom.setError(true, holder.subtitle, holder.errorIcon, holder.layout, "Вы ввели неверный код!", recyclerView, 0, true);
                        }
                    } else {
                        validValue = false;
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

}
