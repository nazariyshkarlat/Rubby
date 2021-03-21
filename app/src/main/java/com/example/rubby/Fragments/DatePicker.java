package com.example.rubby.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.rubby.R;

public class DatePicker extends DialogFragment {

    private int year;
    private int month;
    private int day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){


        java.util.Calendar c = java.util.Calendar.getInstance();

        year = c.get(java.util.Calendar.YEAR);
        month = c.get(java.util.Calendar.MONTH);
        day = c.get(java.util.Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getParentFragment(), year, month, day);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.datepicker_background);
        return dialog;

    }

}
