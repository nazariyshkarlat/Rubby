package com.example.rubby.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.CustomDialog;
import com.example.rubby.R;

public class DialogDisableCallbackFragment extends DialogFragment {

    public CustomDialog dialogAlert;
    private TextView positiveButton;
    private TextView negativeButton;
    private Activity activity;
    private TextView title;
    private TextView subtitle;
    private RecyclerView recyclerView;

    public onNegativeButtonClickCallBack onNegativeButtonClickCallBack;

    public interface onNegativeButtonClickCallBack {
        void onNegativeButtonClick(CustomDialog dialogAlert);
    }

    public void registerOnNegativeButtonCallBack(onNegativeButtonClickCallBack callback) {
        this.onNegativeButtonClickCallBack = callback;
    }

    public void showDialogAlert(String titleText,String subtitleText,String positiveButtonText, String negativeButtonText,Activity activity){
        dialogAlert = new CustomDialog(activity);
        dialogAlert.setContentView(R.layout.dialog_alert);
        this.activity = activity;
        dialogAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = dialogAlert.findViewById(R.id.dialogAlertRecyclerView);
        recyclerView.setVisibility(View.GONE);
        title = dialogAlert.findViewById(R.id.dialogAlertTitle);
        subtitle = dialogAlert.findViewById(R.id.dialogAlertSubtitle);
        positiveButton = dialogAlert.findViewById(R.id.dialogAlertPositiveButton);
        negativeButton = dialogAlert.findViewById(R.id.dialogAlertNegativeButton);
        subtitle.setVisibility(View.VISIBLE);
        title.setText(titleText);
        subtitle.setText(subtitleText);
        negativeButton.setText(negativeButtonText);
        positiveButton.setText(positiveButtonText);

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNegativeButtonClickCallBack.onNegativeButtonClick(dialogAlert);
            }
        });

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAlert.dismiss();
            }
        });
        dialogAlert.show();
    }
}
