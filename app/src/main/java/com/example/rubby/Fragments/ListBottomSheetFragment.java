package com.example.rubby.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

public class ListBottomSheetFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    public String titleS[];
    public String title;
    private TextView titleTextView;
    private RecyclerView.Adapter rAdapter;
    public RecyclerView.Adapter parentAdapter;
    private SQLiteDatabase database;
    public int parentPos;
    public int itemCount = 1;
    private Cursor cursor;
    public SQLiteOpenHelper sqLiteOpenHelper;
    private ContentValues contentValues = new ContentValues();
    public String TABLE_STRING;
    public String ID_STRING;
    public String COLUMN_STRING;
    private int selectedPos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.list_bottom_sheet,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listBottomSheetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        titleTextView = (TextView) v.findViewById(R.id.listBottomSheetTitle);
        titleTextView.setText(title);
        rAdapter = new ListBottomSheetFragment.ListBottomSheetAdapter();
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        database = sqLiteOpenHelper.getWritableDatabase();
        cursor = database.query(TABLE_STRING, null, null, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(COLUMN_STRING);
        selectedPos = cursor.getInt(index);
        return v;

    }

    private class ListBottomSheetHolder extends RecyclerView.ViewHolder{

        private TextView titles;
        private ConstraintLayout layout;

        public ListBottomSheetHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.list_bottom_sheet_item,parent,false));
            titles = (TextView) itemView.findViewById(R.id.listBottomSheetItemTitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.listBottomSheetItemLayout);
        }

    }

    private class ListBottomSheetAdapter extends RecyclerView.Adapter<ListBottomSheetFragment.ListBottomSheetHolder>{

        @NonNull
        @Override
        public ListBottomSheetFragment.ListBottomSheetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ListBottomSheetFragment.ListBottomSheetHolder(layoutInflater,parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final ListBottomSheetFragment.ListBottomSheetHolder holder, final int position) {

            holder.titles.setText(titleS[position]);

            holder.itemView.setSelected(selectedPos == position);

            if(holder.itemView.isSelected())
                holder.titles.setTypeface(HomeActivity.medium);
            else
                holder.titles.setTypeface(HomeActivity.regular);

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notifyItemChanged(selectedPos);
                    selectedPos = holder.getLayoutPosition();
                    notifyItemChanged(selectedPos);
                    contentValues.put(COLUMN_STRING,selectedPos);
                    database.update(TABLE_STRING, contentValues, ID_STRING+"= 1", null);
                    parentAdapter.notifyItemRangeChanged(parentPos,itemCount);
                    ListBottomSheetFragment.this.dismiss();

                }});
        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

}
