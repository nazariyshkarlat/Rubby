package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.DividerItemDecorator;
import com.example.rubby.Activities.AppCompatActivities.SecurityActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.CodeSecurityDatabase;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CodeSecurityFragment extends SmoothFragment {

    private int selectedPos;
    private DividerItemDecorator dividerItemDecorator;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String interval[] = {"5 минут", "15 минут", "30 минут"};
    private ConstraintSet centerSet = new ConstraintSet();
    private ListBottomSheetFragment listBottomSheetFragment = new ListBottomSheetFragment();
    private ArrayList<Integer> dividerPos = new ArrayList<>(Arrays.asList(0));
    private TextView infoTextView;
    private CodeSecurityDatabase codeSecurityDatabase;
    private int count = 1;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_with_info, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewWithInfoRecyclerView);
        infoTextView = (TextView) v.findViewById(R.id.recyclerViewWithInfoInfoTextView);
        infoTextView.setText("Приложение будет автоматически блокироваться при бездействии через указанный период, а так же запрашивать пароль при каждом входе.\n\nВключите данную функцию, если хотите защитить свои данные в случае утери устройства или попадания в нежелательные руки.");
        layoutManager = new LinearLayoutManager(SecurityActivity.context, LinearLayoutManager.VERTICAL, false);
        dividerItemDecorator = new DividerItemDecorator(ResourcesCompat.getDrawable(SecurityActivity.context.getResources(), R.drawable.divider_list,getActivity().getTheme()), dividerPos,getActivity());
        recyclerView.addItemDecoration(dividerItemDecorator);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new CodeSecurityFragment.CodeSecurityFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        codeSecurityDatabase = new CodeSecurityDatabase(SecurityActivity.context);
        setCount(codeSecurityDatabase.getValue(CodeSecurityDatabase.ON_SWITCH) == 1);

        return v;
    }

    private class CodeSecurityFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ConstraintLayout layout;
        private SwitchCompat switchCompat;

        public CodeSecurityFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle_switch_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleSwitchItemSubtitle);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.titleSubtitleSwitchItemSwitch);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleSwitchItemLayout);
            centerSet.clone(layout);
            centerSet.centerVertically(title.getId(), layout.getId());

        }

    }

    public class CodeSecurityFragmentAdapter extends RecyclerView.Adapter<CodeSecurityFragment.CodeSecurityFragmentHolder> {

        @NonNull
        @Override
        public CodeSecurityFragment.CodeSecurityFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CodeSecurityFragment.CodeSecurityFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final CodeSecurityFragment.CodeSecurityFragmentHolder holder, final int position) {

            if(position == 0) {
                centerSet.applyTo(holder.layout);
                holder.title.setText("Включить");
                holder.subtitle.setVisibility(View.GONE);
                holder.switchCompat.setVisibility(View.VISIBLE);
                holder.switchCompat.setClickable(false);
                holder.title.setMaxLines(1);
                if(codeSecurityDatabase.getValue(CodeSecurityDatabase.ON_SWITCH) != -1){
                    holder.switchCompat.setChecked(codeSecurityDatabase.getValue(CodeSecurityDatabase.ON_SWITCH) == 1);
                }
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPos = holder.getLayoutPosition();
                    if(position == 0) {
                        if (!holder.switchCompat.isChecked()) {
                            holder.switchCompat.setChecked(true);
                            codeSecurityDatabase.setValue(CodeSecurityDatabase.ON_SWITCH, 1);
                            infoTextView.setVisibility(View.GONE);
                        } else {
                            holder.switchCompat.setChecked(false);
                            codeSecurityDatabase.setValue(CodeSecurityDatabase.ON_SWITCH, 0);
                            infoTextView.setVisibility(View.VISIBLE);
                        }
                        setCount(holder.switchCompat.isChecked());
                        rAdapter.notifyItemRangeChanged(1,3);
                    }else if(position == 1){
                        PasswordRequestFragment passwordRequestFragment = new PasswordRequestFragment();
                        passwordRequestFragment.passwordType = PasswordRequestFragment.TYPE_PASSWORD_CODE;
                        SecurityActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, passwordRequestFragment,null).commit();
                    }else if(position == 3){
                        if (!listBottomSheetFragment.isAdded()) {
                            listBottomSheetFragment.title = "Интервал автоблокировки:";
                            listBottomSheetFragment.parentAdapter = rAdapter;
                            listBottomSheetFragment.parentPos = selectedPos;
                            listBottomSheetFragment.titleS = interval;
                            listBottomSheetFragment.sqLiteOpenHelper = codeSecurityDatabase;
                            listBottomSheetFragment.TABLE_STRING = CodeSecurityDatabase.DATABASE_TABLE;
                            listBottomSheetFragment.COLUMN_STRING = CodeSecurityDatabase.REPEAT_TIME_POS;
                            listBottomSheetFragment.ID_STRING = CodeSecurityDatabase.ID;
                            listBottomSheetFragment.show(SecurityActivity.fragmentManager, null);
                        }
                    }
                }
            });

            if(count == 4){
                if(position ==1 || position == 2 || position == 3){
                    String titles[] = {"Сменить код-пароль", "Разблокировка отпечатком", "Интервал автоблокировки"};
                    holder.title.setText(titles[position-1]);
                    if(position == 1 || position == 2) {
                        holder.subtitle.setVisibility(View.GONE);
                        if (position == 2) {
                            holder.switchCompat.setClickable(false);
                            holder.switchCompat.setVisibility(View.VISIBLE);
                            holder.switchCompat.setChecked(codeSecurityDatabase.getValue(CodeSecurityDatabase.FINGERPRINT_SWITCH) == 1);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    holder.switchCompat.setChecked(!holder.switchCompat.isChecked());
                                    codeSecurityDatabase.setValue(CodeSecurityDatabase.FINGERPRINT_SWITCH, holder.switchCompat.isChecked() ? 1 : 0);
                                }
                            });
                        }
                    }else if(position == 3){
                        holder.switchCompat.setVisibility(View.GONE);
                        if(codeSecurityDatabase.getValue(CodeSecurityDatabase.REPEAT_TIME_POS) == -1){
                            holder.subtitle.setText("Не выбран");
                        }else
                            holder.subtitle.setText(interval[codeSecurityDatabase.getValue(CodeSecurityDatabase.REPEAT_TIME_POS)]);
                    }
                }
            }

        }

        @Override
        public int getItemCount() {

            return count;

        }
    }

    private void setCount(boolean b){
        if(b) {
            count = 4;
        }else {
            count = 1;
        }
    }

}
