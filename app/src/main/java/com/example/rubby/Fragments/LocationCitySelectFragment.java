package com.example.rubby.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.Model.LocationCityModel;
import com.example.rubby.Model.LocationCountryModel;
import com.example.rubby.R;

import java.util.ArrayList;

public class LocationCitySelectFragment extends ChildFragment implements SearchTextInputLayoutFragment.onTextChangeCallBack {

    private ArrayList<LocationCityModel> locationModels = new ArrayList<>();
    private SearchTextInputLayoutFragment searchTextInputLayoutFragment = new SearchTextInputLayoutFragment();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private LocationCityModel selectedModel;
    public int key;
    private FrameLayout frameLayout;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.nested_recycler_view, container, false);
        if(!searchTextInputLayoutFragment.isAdded())
            getChildFragmentManager().beginTransaction().add(R.id.nestedRecyclerFrameLayout, searchTextInputLayoutFragment, null).commit();
        frameLayout = (FrameLayout) v.findViewById(R.id.nestedRecyclerFrameLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.nestedRecyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new LocationCitySelectFragmentAdapter();
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(rAdapter);
        searchTextInputLayoutFragment.registerOnTextChangeCallBack(this);
        locationModels.addAll(LocationCityModel.getAllList(key));
        this.v = frameLayout;

        return v;
    }

    @Override
    public void onTextChange(String text) {
        String validText = text.toLowerCase();
        ArrayList<LocationCityModel> arrayList = new ArrayList<>();
        locationModels.clear();
        arrayList.addAll(LocationCityModel.getAllList(key));
        for (int i = 0;i<arrayList.size();i++) {
            if (arrayList.get(i).getTitle().toLowerCase().startsWith(validText)) {
                locationModels.add(arrayList.get(i));
            }
        }
        rAdapter.notifyDataSetChanged();
    }

    private class LocationCitySelectFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private ConstraintLayout layout;

        public LocationCitySelectFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconLayout);

        }

    }

    private class LocationCitySelectFragmentAdapter extends RecyclerView.Adapter<LocationCitySelectFragmentHolder> {

        @NonNull
        @Override
        public LocationCitySelectFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LocationCitySelectFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final LocationCitySelectFragmentHolder holder, final int position) {

            final LocationCityModel locationModel = locationModels.get(position);
            holder.title.setText(locationModel.getTitle());
            holder.icon.setImageResource(R.drawable.ic_outline_check_selector_24dp);

            holder.icon.setBackgroundResource(0);

            if(selectedModel != null)
                holder.icon.setSelected(locationModel.equals(selectedModel));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedModel != null)
                        selectedModel.setSelected(false);
                    locationModel.setSelected(true);
                    rAdapter.notifyItemChanged(locationModels.indexOf(selectedModel));
                    rAdapter.notifyItemChanged(locationModels.indexOf(locationModel));
                    selectedModel = locationModel;

                }
            });


        }

        @Override
        public int getItemCount() {
            return locationModels.size();
        }

    }

    public void setValue(SQLiteOpenHelper sqLiteOpenHelper, String DATABASE_TABLE, String DATABASE_COLUMN, String ID){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        contentValues.put(DATABASE_COLUMN, value());
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst())
            sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
        else
            sqLiteDatabase.update(DATABASE_TABLE, contentValues, ID + "= 1", null);
    }

    private String value(){
        if(selectedModel != null)
            return LocationCountryModel.getAllList().get(key).getTitle() + ", " + selectedModel.getTitle();
        else
            return LocationCountryModel.getAllList().get(key).getTitle();
    }

}
