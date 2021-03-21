package com.example.rubby.Fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.OverridedWidgets.DividerItemDecorator;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.SafetyDatabase;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SafetyFragment extends SmoothFragment {

    private int selectedPos;
    private ConstraintSet constraintSet = new ConstraintSet();
    private String titleS[] = {"Кто может оставлять комментарии", "Кто может видеть моих питомцев", "Кто может видеть мои сообщества", "Получать сообщения от", "Показывать мой аккаунт в поиске","Закрытый профиль"};
    private String subtitleS[];
    private String nominativeS[] = {"Все", "Мои подписки", "Никто"};
    private String genitiveS[] = {"Всех", "Моих подписок","Никого"};
    private String columnS[] = {SafetyDatabase.COMMENTS_POS, SafetyDatabase.PETS_POS, SafetyDatabase.COMMUNITIES_POS, SafetyDatabase.MESSAGES_POS};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private SafetyDatabase safetyDatabase;
    private ArrayList<Integer> dividerPos = new ArrayList<>(Arrays.asList(3));
    private ContentValues contentValues = new ContentValues();
    private ListBottomSheetFragment listBottomSheetFragment = new ListBottomSheetFragment();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new SafetyFragment.SafetyFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        recyclerView.addItemDecoration(new DividerItemDecorator(ResourcesCompat.getDrawable(AccountEditActivity.context.getResources(), R.drawable.divider_list,getActivity().getTheme()), dividerPos,getActivity()));
        safetyDatabase = new SafetyDatabase(AccountEditActivity.context);

        return v;
    }

    private class SafetyFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ImageView icon;
        private SwitchCompat switchCompat;
        private ConstraintLayout layout;

        public SafetyFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.icon_title_subtitle_switch_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.iconTitleSubtitleSwitchItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.iconTitleSubtitleSwitchItemSubtitle);
            icon = (ImageView) itemView.findViewById(R.id.iconTitleSubtitleSwitchItemIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.iconTitleSubtitleSwitchItemLayout);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.iconTitleSubtitleSwitchItemSwitch);
            constraintSet.clone(layout);
            constraintSet.connect(title.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START,0);
            constraintSet.connect(subtitle.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START,0);
        }

    }

    private class SafetyFragmentAdapter extends RecyclerView.Adapter<SafetyFragment.SafetyFragmentHolder> {

        @NonNull
        @Override
        public SafetyFragment.SafetyFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SafetyFragment.SafetyFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final SafetyFragment.SafetyFragmentHolder holder, final int position) {
            subtitleS = new String[] {nominativeS[safetyDatabase.getValue(SafetyDatabase.COMMENTS_POS)],nominativeS[safetyDatabase.getValue(SafetyDatabase.PETS_POS)],nominativeS[safetyDatabase.getValue(SafetyDatabase.COMMUNITIES_POS)],genitiveS[safetyDatabase.getValue(SafetyDatabase.MESSAGES_POS)],null ,"Ваши публикации будут скрыты от всех, кроме ваших подписчиков", null};
            holder.title.setText(titleS[position]);
            holder.subtitle.setText(subtitleS[position]);
            holder.icon.setVisibility(View.GONE);
            constraintSet.applyTo(holder.layout);

            if(position > 3){
                holder.switchCompat.setVisibility(View.VISIBLE);
                if(position == 4)
                    holder.switchCompat.setChecked(safetyDatabase.getValue(SafetyDatabase.SEARCH_SHOW_SWITCH) == 1);
                else {
                    holder.switchCompat.setChecked(safetyDatabase.getValue(SafetyDatabase.CLOSED_PROFILE_SWITCH) == 1);
                }
            }

            if(position == 4){
                ConstraintSet constraintSet = new ConstraintSet();
                holder.subtitle.setVisibility(View.GONE);
                constraintSet.clone(holder.layout);
                constraintSet.centerVertically(holder.title.getId(),holder.layout.getId());
                constraintSet.applyTo(holder.layout);
            }

            holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(selectedPos == 4)
                        safetyDatabase.setValue(SafetyDatabase.SEARCH_SHOW_SWITCH, b ? 1 : 0);
                    else if(selectedPos == 5)
                        safetyDatabase.setValue(SafetyDatabase.CLOSED_PROFILE_SWITCH, b ? 1 : 0);

                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedPos = holder.getLayoutPosition();

                    if(position < 4) {
                        if (!listBottomSheetFragment.isAdded()) {
                            listBottomSheetFragment.title = titleS[position];
                            listBottomSheetFragment.parentAdapter = rAdapter;
                            listBottomSheetFragment.parentPos = selectedPos;
                            if (position != 3)
                                listBottomSheetFragment.titleS = nominativeS;
                            else
                                listBottomSheetFragment.titleS = genitiveS;
                            listBottomSheetFragment.sqLiteOpenHelper = safetyDatabase;
                            listBottomSheetFragment.TABLE_STRING = SafetyDatabase.DATABASE_TABLE;
                            listBottomSheetFragment.COLUMN_STRING = columnS[position];
                            listBottomSheetFragment.ID_STRING = SafetyDatabase.ID;
                            listBottomSheetFragment.show(AccountEditActivity.fragmentManager, null);
                        }
                    }else {
                        holder.switchCompat.setChecked(!holder.switchCompat.isChecked());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

}
