package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.Activities.AppCompatActivities.NotificationsActivity;
import com.example.rubby.OverridedWidgets.ExpandableRecyclerView.ExpandableRecyclerViewAdapter;
import com.example.rubby.OverridedWidgets.ExpandableRecyclerView.models.ExpandableGroup;
import com.example.rubby.OverridedWidgets.ExpandableRecyclerView.viewholders.ChildViewHolder;
import com.example.rubby.OverridedWidgets.ExpandableRecyclerView.viewholders.GroupViewHolder;
import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Database.NotificationsDatabase;
import com.example.rubby.Database.SettingsDatabase;
import com.example.rubby.Model.Item;
import com.example.rubby.Model.SubItem;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationsFragment extends SmoothFragment {

    private SettingsDatabase settingsDatabase;
    private String titleS[] = {"Включить уведомления", "Получаемые уведомления"};
    private String expandableTitle[] = {"Новые подписчики", "Новые сообщения", "Ответы на ваши комментарии", "Отметки на публикациях", "Отметки \"Нравится\""};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ArrayList<String> columns = new ArrayList<>(Arrays.asList(NotificationsDatabase.NEW_FOLLOWERS, NotificationsDatabase.NEW_MESSAGES, NotificationsDatabase.COMMENT_ANSWERS, NotificationsDatabase.POSTS_MARKS, NotificationsDatabase.LIKES));
    private NotificationsDatabase notificationsDatabase;
    private List<Item> items = new ArrayList<>();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NotificationsFragmentAdapter(getItemData());
        settingsDatabase = new SettingsDatabase(NotificationsActivity.context);
        recyclerView.setAdapter(rAdapter);
        notificationsDatabase = new NotificationsDatabase(NotificationsActivity.context);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    private List<Item> getItemData() {
        items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item(new ArrayList<SubItem>(Arrays.asList(new SubItem(), new SubItem(), new SubItem(), new SubItem(), new SubItem()))));
        return items;
    }

    public class ItemViewHolder extends GroupViewHolder {

        private TextView title;
        private ImageView icon;
        private SwitchCompat switchCompat;
        private ConstraintLayout layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleIconSwitchTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconSwitchIcon);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.titleIconSwitchSwitch);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconSwitchLayout);

        }

        @Override
        public void expand() {
            icon.animate().rotation(180).start();
        }

        @Override
        public void collapse() {
            icon.animate().rotation(0).start();
        }
    }

    public class SubItemViewHolder extends ChildViewHolder {

        private TextView title;
        private CheckBox checkBox;
        private ConstraintLayout layout;

        public SubItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.checkBoxTitleItemTitle);
            checkBox = itemView.findViewById(R.id.checkBoxTitleItemCheckBox);
            layout = itemView.findViewById(R.id.checkBoxTitleItemLayout);
            layout.setPadding(Methods.dpToPx(32, getContext()), layout.getPaddingTop(), Methods.dpToPx(32, getContext()), layout.getPaddingBottom());
        }
    }

        public class NotificationsFragmentAdapter extends ExpandableRecyclerViewAdapter<ItemViewHolder, SubItemViewHolder> {

            public NotificationsFragmentAdapter(List<? extends ExpandableGroup> groups) {
                super(groups);
            }

            @Override
            public NotificationsFragment.ItemViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_icon_switch_item, parent, false);
                return new NotificationsFragment.ItemViewHolder(view);
            }

            @Override
            public NotificationsFragment.SubItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_box_title_item, parent, false);

                return new NotificationsFragment.SubItemViewHolder(view);
            }

            @Override
            public void onBindChildViewHolder(final NotificationsFragment.SubItemViewHolder holder, final int flatPosition, ExpandableGroup group,
                                              final int childIndex) {
                holder.title.setText(expandableTitle[childIndex]);

                holder.checkBox.setChecked(notificationsDatabase.getValue(columns.get(childIndex)) == 1);

                if (flatPosition >= 1) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.checkBox.setChecked(!holder.checkBox.isChecked());
                            notificationsDatabase.setValue(columns.get(childIndex), holder.checkBox.isChecked() ? 1:0);
                        }
                    });
                }
            }

            @Override
            public void onBindGroupViewHolder(final NotificationsFragment.ItemViewHolder holder, int flatPosition,
                                              final ExpandableGroup group) {
                group.setPosition(flatPosition);
                final int position = group.getPosition();
                holder.title.setText(titleS[position]);

                if(position == 0)
                    holder.switchCompat.setVisibility(View.VISIBLE);

                if(position == 1)
                    holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_down_60_24dp);

                if (position != 1) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (position == 0) {
                                holder.switchCompat.setChecked(!holder.switchCompat.isChecked());
                                settingsDatabase.setValue(SettingsDatabase.NOTIFICATIONS_SWITCH, holder.switchCompat.isChecked() ? 1 : 0);
                            }
                        }
                    });
                }
            }
        }

}
