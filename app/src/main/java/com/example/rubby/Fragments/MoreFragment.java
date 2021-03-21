package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class MoreFragment extends SmoothFragment {

    private String titleS[] = {"Конфиденциальность", "Политика использования", "О нас", "Связаться с нами"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new MoreFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    private class MoreFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private ConstraintLayout layout;

        public MoreFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconLayout);

        }

    }

    private class MoreFragmentAdapter extends RecyclerView.Adapter<MoreFragmentHolder> {

        @NonNull
        @Override
        public MoreFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new MoreFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final MoreFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_right_60_24dp);

            if(position == 3)
                holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_down_60_24dp);
            else
                holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_right_60_24dp);

        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

}
