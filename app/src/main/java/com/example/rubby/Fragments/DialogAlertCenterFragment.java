package com.example.rubby.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.CustomDialog;
import com.example.rubby.R;

public class DialogAlertCenterFragment extends DialogFragment {

    public CustomDialog dialogAlert;
    private TextView button;
    private Activity activity;
    private TextView title;
    private TextView subtitle;

    public void showDialogAlert(String titleText, String subtitleText, String buttonText, Activity activity, View.OnClickListener onClickListener){
        dialogAlert = new CustomDialog(activity);
        dialogAlert.setContentView(R.layout.dialog_alert_center);
        this.activity = activity;
        dialogAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        title = dialogAlert.findViewById(R.id.dialogAlertCenterTitle);
        subtitle = dialogAlert.findViewById(R.id.dialogAlertCenterSubtitle);
        button = dialogAlert.findViewById(R.id.dialogAlertCenterButton);
        title.setText(titleText);
        subtitle.setText(subtitleText);
        button.setText(buttonText);
        button.setOnClickListener(onClickListener);

        dialogAlert.show();
    }

}
