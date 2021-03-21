package com.example.rubby.Fragments;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.Database.PersonalDataDatabase;
import com.example.rubby.R;

public class PersonalDataFragment extends Fragment {

    private int selectedPos;
    private String titleS[] = {"Пол", "Дата рождения", "Регион"};
    private String maleS[] = {"Мужской", "Женский","Не скажу"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private PersonalDataDatabase personalDataDatabase;
    private ListBottomSheetFragment listBottomSheetFragment = new ListBottomSheetFragment();
    public RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new PersonalDataFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        personalDataDatabase = new PersonalDataDatabase(AccountEditActivity.context);

        return v;
    }

    private class PersonalDataFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ConstraintLayout layout;

        public PersonalDataFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleItemSubtitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleItemLayout);

        }

    }

    private class PersonalDataFragmentAdapter extends RecyclerView.Adapter<PersonalDataFragmentHolder> implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public PersonalDataFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PersonalDataFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PersonalDataFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            if (position == 0)
                holder.subtitle.setText(maleS[personalDataDatabase.getIntValue(PersonalDataDatabase.SEX_POS)]);
            else if(position == 1){
                if(personalDataDatabase.getIntValue(PersonalDataDatabase.DAY_INT ) != 0){
                    String day =String.format("%02d",personalDataDatabase.getIntValue(PersonalDataDatabase.DAY_INT));
                    String month =String.format("%02d",personalDataDatabase.getIntValue(PersonalDataDatabase.MONTH_INT));
                    String year = toString().valueOf(personalDataDatabase.getIntValue(PersonalDataDatabase.YEAR_INT));
                    holder.subtitle.setText(day + "." + month + "."  + year);
                }else
                    holder.subtitle.setText("Не указана");
            }else if(position == 2){
                if(personalDataDatabase.getStringValue(PersonalDataDatabase.LOCATION) == null) {
                    holder.subtitle.setText("Не выбрано");
                }else
                    holder.subtitle.setText(personalDataDatabase.getStringValue(PersonalDataDatabase.LOCATION));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedPos = holder.getLayoutPosition();

                    if(position == 0){
                        if(!listBottomSheetFragment.isAdded()) {
                            listBottomSheetFragment.title = "Пол";
                            listBottomSheetFragment.parentAdapter = rAdapter;
                            listBottomSheetFragment.parentPos = selectedPos;
                            listBottomSheetFragment.titleS = maleS;
                            listBottomSheetFragment.sqLiteOpenHelper = personalDataDatabase;
                            listBottomSheetFragment.TABLE_STRING = PersonalDataDatabase.DATABASE_TABLE;
                            listBottomSheetFragment.COLUMN_STRING = PersonalDataDatabase.SEX_POS;
                            listBottomSheetFragment.ID_STRING = PersonalDataDatabase.ID;
                            listBottomSheetFragment.show(AccountEditActivity.fragmentManager, null);
                        }
                    }else if(position == 1){
                        int year,month,day;
                        if(personalDataDatabase.getIntValue(PersonalDataDatabase.DAY_INT) == 0) {
                            java.util.Calendar c = java.util.Calendar.getInstance();
                            year = c.get(java.util.Calendar.YEAR);
                            month = c.get(java.util.Calendar.MONTH);
                            day = c.get(java.util.Calendar.DAY_OF_MONTH);
                        }else {
                            year = personalDataDatabase.getIntValue(PersonalDataDatabase.YEAR_INT);
                            month = personalDataDatabase.getIntValue(PersonalDataDatabase.MONTH_INT);
                            day = personalDataDatabase.getIntValue(PersonalDataDatabase.DAY_INT);
                        }
                        DatePickerDialog dialog = new DatePickerDialog(getActivity(), PersonalDataFragmentAdapter.this, year, month, day);
                        dialog.show();
                    }else if(position == 2){
                        LocationCountrySelectFragment locationSelectFragment = new LocationCountrySelectFragment();
                        AccountEditActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,locationSelectFragment,null).commit();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return titleS.length;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            personalDataDatabase.setIntValue(PersonalDataDatabase.YEAR_INT, i);
            personalDataDatabase.setIntValue(PersonalDataDatabase.MONTH_INT,i1+1);
            personalDataDatabase.setIntValue(PersonalDataDatabase.DAY_INT,i2);
            rAdapter.notifyItemChanged(1);
        }
    }

}
