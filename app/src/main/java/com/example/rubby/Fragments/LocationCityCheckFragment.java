package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.rubby.Other.Methods;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.Model.LocationCityModel;
import com.example.rubby.Model.LocationCountryModel;
import com.example.rubby.R;

import java.util.ArrayList;

public class LocationCityCheckFragment extends ChildFragment implements SearchTextInputLayoutFragment.onTextChangeCallBack{

    private ArrayList<ArrayList<LocationCityModel>> locationCountryModels = new ArrayList<>();
    private ArrayList<Integer> posList = new ArrayList<>();
    public static SparseArray<ArrayList<LocationCityModel>> selectedCities = new SparseArray<>();
    private ArrayList<LocationCountryModel> allModels = new ArrayList<>();
    private SearchTextInputLayoutFragment searchTextInputLayoutFragment = new SearchTextInputLayoutFragment();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public int key;
    private int index;
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
        rAdapter = new LocationCityCheckFragmentAdapter();
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(rAdapter);
        searchTextInputLayoutFragment.registerOnTextChangeCallBack(this);
        int size = 0;
        allModels.addAll(LocationCountryCheckFragment.selectedModels);
        selectedCities.clear();
        addModels(size);
        this.v = frameLayout;

        return v;
    }

    @Override
    public void onTextChange(String text) {
        String validText = text.toLowerCase();
        int size = 0;
        posList.clear();
        locationCountryModels.clear();
        updateModels(size,validText);
        key = 0;
        rAdapter.notifyDataSetChanged();
    }

    private class LocationCityCheckFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CheckBox checkBox;
        private ConstraintLayout layout;

        public LocationCityCheckFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.check_box_title_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.checkBoxTitleItemTitle);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxTitleItemCheckBox);
            layout = (ConstraintLayout) itemView.findViewById(R.id.checkBoxTitleItemLayout);

        }

    }

    private class LocationCityCheckFragmentAdapter extends RecyclerView.Adapter<LocationCityCheckFragmentHolder> {

        @NonNull
        @Override
        public LocationCityCheckFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LocationCityCheckFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final LocationCityCheckFragmentHolder holder, final int position) {

            if(position == 0 || posList.contains(position)) {
                index = 0;
                if(locationCountryModels.get(key).size() != 0) {
                    key = posList.indexOf(position);
                    holder.layout.setPadding(0, holder.layout.getPaddingTop(), 0, holder.layout.getPaddingBottom());
                    holder.checkBox.setVisibility(View.GONE);
                    holder.title.setText(LocationCountryModel.getAllList().get(locationCountryModels.get(key).get(0).countryKey).getTitle());
                } }else{
                if(locationCountryModels.get(key).size() != 0) {
                    LocationCityModel locationCityModel = locationCountryModels.get(key).get(index);
                    holder.layout.setPadding(Methods.dpToPx(32, getContext()), holder.layout.getPaddingTop(), holder.layout.getPaddingRight(), holder.layout.getPaddingBottom());
                    holder.title.setText(locationCityModel.getTitle());
                    holder.checkBox.setChecked(locationCityModel.isSelected());
                    holder.checkBox.setVisibility(View.VISIBLE);
                    index++;
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.checkBox.setChecked(!holder.checkBox.isChecked());
                        int groupPos = position;
                        while (!(posList.contains(groupPos))) {
                            groupPos--;
                        }
                        key = posList.indexOf(groupPos);
                        ArrayList<LocationCityModel> locationCityModels = locationCountryModels.get(key);
                        index = (position-1)- posList.get(key);
                        LocationCityModel locationCityModel = locationCityModels.get(index);
                        locationCityModel.setSelected(!locationCityModel.isSelected());
                        int cityKey = locationCityModel.countryKey;
                        if(!(selectedCities.get(cityKey).contains(locationCityModel)))
                            selectedCities.get(cityKey).add(locationCityModel);
                        else
                            selectedCities.get(cityKey).remove(locationCityModel);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return listSize();
        }

    }

    private int listSize(){
        int size = 0;
        for(int i = 0;i < locationCountryModels.size(); i++){
            ArrayList<LocationCityModel> locationCityModels = locationCountryModels.get(i);
            if(locationCityModels.size() != 0)
                size += locationCityModels.size() + 1;
            else
                size += locationCityModels.size();
        }
        return size;

    }

    private void updateModels(int size,String validText){
        for(int i = 0;i < allModels.size();i++) {
            LocationCountryModel locationCountryModel = allModels.get(i);
            int key = locationCountryModel.getKey();
            ArrayList<LocationCityModel> arrayList = new ArrayList<>();
            for (LocationCityModel locationCityModel : LocationCityModel.getAllList(key)) {
                if (locationCityModel.getTitle().toLowerCase().startsWith(validText)) {
                    arrayList.add(locationCityModel);
                }
            }
            if (arrayList.size() != 0) {
                posList.add(size);
                locationCountryModels.add(arrayList);
                size += arrayList.size() + 1;
                for (LocationCityModel locationCityModel : locationCountryModels.get(locationCountryModels.size()-1)) {
                    locationCityModel.countryKey = locationCountryModel.key;
                }
            }
        }
    }

    private void addModels(int size) {
        for (int i = 0; i < allModels.size(); i++) {
            selectedCities.put(allModels.get(i).key, new ArrayList<LocationCityModel>());
        }
        for (int i = 0; i < allModels.size(); i++) {
            LocationCountryModel locationCountryModel = allModels.get(i);
            int key = locationCountryModel.getKey();
            locationCountryModels.add(LocationCityModel.getAllList(key));
            posList.add(size);
            size += LocationCityModel.getAllList(key).size() + 1;
            for (LocationCityModel locationCityModel : locationCountryModels.get(i)) {
                locationCityModel.countryKey = locationCountryModel.key;
                locationCityModel.setSelected(false);
            }
        }
    }
}
