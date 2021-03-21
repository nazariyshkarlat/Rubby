package com.example.rubby.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.CustomDialog;
import com.example.rubby.Other.Methods;
import com.example.rubby.Model.HistoryModel;
import com.example.rubby.R;

public class HistoryInfoDialogFragment extends DialogFragment {

    public CustomDialog dialogAlert;
    private TextView positiveButton;
    private TextView negativeButton;
    private Activity activity;
    private TextView title;
    private TextView subtitle;
    private String[] titleS = {"Устройство", "MAC-адрес", "Местоположение", "IP-адрес", "Время активности"};
    private HistoryModel historyModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter dialogAlertRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;


    public void showDialogAlert(HistoryModel historyModel,Activity activity){
        dialogAlert = new CustomDialog(activity);
        dialogAlert.setContentView(R.layout.dialog_alert);
        this.historyModel = historyModel;
        this.activity = activity;
        dialogAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recyclerView = dialogAlert.findViewById(R.id.dialogAlertRecyclerView);
        this.title = dialogAlert.findViewById(R.id.dialogAlertTitle);
        this.subtitle = dialogAlert.findViewById(R.id.dialogAlertSubtitle);
        this.subtitle.setVisibility(View.GONE);
        positiveButton = dialogAlert.findViewById(R.id.dialogAlertPositiveButton);
        negativeButton = dialogAlert.findViewById(R.id.dialogAlertNegativeButton);
        negativeButton.setVisibility(View.GONE);
        positiveButton.setText("ЗАКРЫТЬ");
        this.title.setText("Информация об устройстве");
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dialogAlertRecyclerViewAdapter = new HistoryInfoDialogFragmentAdapter();
        recyclerView.setAdapter(dialogAlertRecyclerViewAdapter);
        recyclerView.setPadding(0,Methods.dpToPx(16,activity),0,0);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAlert.dismiss();
            }
        });
        dialogAlert.show();
    }

    public class HistoryInfoDialogFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ConstraintLayout layout;

        public HistoryInfoDialogFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle, parent, false));

            title = (TextView) itemView.findViewById(R.id.titleSubtitleItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleItemSubtitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleItemLayout);
            layout.setPadding(0,layout.getPaddingTop(),0,layout.getPaddingBottom());

        }

    }

    public class HistoryInfoDialogFragmentAdapter extends RecyclerView.Adapter<HistoryInfoDialogFragmentHolder> {

        @NonNull
        @Override
        public HistoryInfoDialogFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            return new HistoryInfoDialogFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final HistoryInfoDialogFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            if(position == 0)
                holder.subtitle.setText(historyModel.deviceName);
            else if(position == 1)
                holder.subtitle.setText(historyModel.location);
            else if(position == 2)
                holder.subtitle.setText(historyModel.MAC);
            else if(position == 3)
                holder.subtitle.setText(historyModel.IP);
            else
                holder.subtitle.setText(historyModel.TIME_RANGE);

        }

        @Override
        public int getItemCount() {

            return 5;

        }

    }

}
