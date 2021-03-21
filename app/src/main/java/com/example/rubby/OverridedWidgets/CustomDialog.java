package com.example.rubby.OverridedWidgets;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

public class CustomDialog extends Dialog {

    private Context activity;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.activity = context;
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
    }

}
