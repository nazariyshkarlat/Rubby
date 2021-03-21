package com.example.rubby.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.HomeActivity;
import com.example.rubby.R;

public class ListBottomSheetWithCallbackFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    public String titleS[];
    public String title;
    public TextView button;
    public String buttonText;
    public View.OnClickListener onClickListener;
    public boolean buttonIsVisible = false;
    private TextView titleTextView;
    private ListBottomSheetWithCallbackFragmentAdapter rAdapter;
    public int selectedPos;

    public onItemClickCallBack onItemClickCallBack;

    public interface onItemClickCallBack {
        void onItemClick(int position,ListBottomSheetWithCallbackFragmentAdapter rAdapter, ListBottomSheetWithCallbackFragmentHolder viewHolder,ListBottomSheetWithCallbackFragment listBottomSheetWithCallbackFragment);
    }

    public void registerOnItemClickCallBack(ListBottomSheetWithCallbackFragment.onItemClickCallBack callback) {
        this.onItemClickCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.list_bottom_sheet,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listBottomSheetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        titleTextView = (TextView) v.findViewById(R.id.listBottomSheetTitle);
        titleTextView.setText(title);
        button = (TextView) v.findViewById(R.id.listBottomSheetButton);
        if(buttonIsVisible)
            button.setVisibility(View.VISIBLE);
        button.setText(buttonText);
        button.setOnClickListener(onClickListener);
        rAdapter = new ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        return v;

    }

    public class ListBottomSheetWithCallbackFragmentHolder extends RecyclerView.ViewHolder{

        private TextView titles;
        private ConstraintLayout layout;

        public ListBottomSheetWithCallbackFragmentHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.list_bottom_sheet_item,parent,false));
            titles = (TextView) itemView.findViewById(R.id.listBottomSheetItemTitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.listBottomSheetItemLayout);
        }

    }

    public class ListBottomSheetWithCallbackFragmentAdapter extends RecyclerView.Adapter<ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder>{

        @NonNull
        @Override
        public ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder(layoutInflater,parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder holder, final int position) {

            holder.titles.setText(titleS[position]);

            holder.itemView.setSelected(selectedPos == position);

            if(holder.itemView.isSelected())
                holder.titles.setTypeface(HomeActivity.medium);
            else
                holder.titles.setTypeface(HomeActivity.regular);

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedPos = holder.getLayoutPosition();
                    onItemClickCallBack.onItemClick(selectedPos, rAdapter, holder,ListBottomSheetWithCallbackFragment.this);

                }});
        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

}
