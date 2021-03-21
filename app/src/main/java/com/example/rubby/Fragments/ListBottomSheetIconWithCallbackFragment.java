package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.HomeActivity;
import com.example.rubby.R;

public class ListBottomSheetIconWithCallbackFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    public String titleS[];
    public int iconsS[];
    public String title;
    private TextView titleTextView;
    private ListBottomSheetIconWithCallbackFragmentAdapter rAdapter;
    public int selectedPos = -1;

    public onItemClickCallBack onItemClickCallBack;

    public interface onItemClickCallBack {
        void onItemClick(int position, ListBottomSheetIconWithCallbackFragmentAdapter rAdapter,ListBottomSheetIconWithCallbackFragmentHolder viewHolder, ListBottomSheetIconWithCallbackFragment listBottomSheetIconWithCallbackFragment);
    }

    public void registerOnItemClickCallBack(onItemClickCallBack callback) {
        this.onItemClickCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.list_bottom_sheet,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listBottomSheetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        titleTextView = (TextView) v.findViewById(R.id.listBottomSheetTitle);
        titleTextView.setText(title);
        titleTextView.setVisibility(View.GONE);
        rAdapter = new ListBottomSheetIconWithCallbackFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        return v;

    }

    public class ListBottomSheetIconWithCallbackFragmentHolder extends RecyclerView.ViewHolder{

        private TextView titles;
        private ImageView icon;

        public ListBottomSheetIconWithCallbackFragmentHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.home_bottom_sheet_item,parent,false));
            titles = (TextView) itemView.findViewById(R.id.homeBottomSheetTextView);
            icon = (ImageView) itemView.findViewById(R.id.homeBottomSheetIcon);
        }

    }

    public class ListBottomSheetIconWithCallbackFragmentAdapter extends RecyclerView.Adapter<ListBottomSheetIconWithCallbackFragmentHolder>{

        @NonNull
        @Override
        public ListBottomSheetIconWithCallbackFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ListBottomSheetIconWithCallbackFragmentHolder(layoutInflater,parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final ListBottomSheetIconWithCallbackFragmentHolder holder, final int position) {

            holder.titles.setText(titleS[position]);
            holder.icon.setImageResource(iconsS[position]);

            holder.itemView.setSelected(selectedPos == position);

            if(holder.itemView.isSelected())
                holder.titles.setTypeface(HomeActivity.medium);
            else
                holder.titles.setTypeface(HomeActivity.regular);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedPos = holder.getLayoutPosition();
                    onItemClickCallBack.onItemClick(selectedPos, rAdapter, holder,ListBottomSheetIconWithCallbackFragment.this);

                }});
        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

}
