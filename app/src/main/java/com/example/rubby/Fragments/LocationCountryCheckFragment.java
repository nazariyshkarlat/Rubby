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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.Model.LocationCountryModel;
import com.example.rubby.R;

import java.util.ArrayList;

public class LocationCountryCheckFragment extends ChildFragment implements SearchTextInputLayoutFragment.onTextChangeCallBack {

    private ArrayList<LocationCountryModel> locationModels = new ArrayList<>();
    public static ArrayList<LocationCountryModel> selectedModels = new ArrayList<>();
    private SearchTextInputLayoutFragment searchTextInputLayoutFragment = new SearchTextInputLayoutFragment();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private FrameLayout frameLayout;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.nested_recycler_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.nestedRecyclerView);
        if(!searchTextInputLayoutFragment.isAdded())
            getChildFragmentManager().beginTransaction().add(R.id.nestedRecyclerFrameLayout, searchTextInputLayoutFragment, null).commit();
        frameLayout = (FrameLayout) v.findViewById(R.id.nestedRecyclerFrameLayout);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new LocationCountryCheckFragmentAdapter();
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(rAdapter);
        searchTextInputLayoutFragment.registerOnTextChangeCallBack(this);
        locationModels.addAll(LocationCountryModel.getAllList());
        this.v = frameLayout;
        return v;
    }

    @Override
    public void onTextChange(String text) {
        String validText = text.toLowerCase();
        ArrayList<LocationCountryModel> arrayList = new ArrayList<>();
        locationModels.clear();
        arrayList.addAll(LocationCountryModel.getAllList());
        for (int i = 0;i<arrayList.size();i++) {
            if (arrayList.get(i).getTitle().toLowerCase().startsWith(validText)) {
                locationModels.add(arrayList.get(i));
            }
        }
        rAdapter.notifyDataSetChanged();

    }

    private class LocationCountryCheckFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CheckBox checkBox;
        private ConstraintLayout layout;

        public LocationCountryCheckFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.check_box_title_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.checkBoxTitleItemTitle);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxTitleItemCheckBox);
            layout = (ConstraintLayout) itemView.findViewById(R.id.checkBoxTitleItemLayout);

        }

    }

    private class LocationCountryCheckFragmentAdapter extends RecyclerView.Adapter<LocationCountryCheckFragmentHolder> {

        @NonNull
        @Override
        public LocationCountryCheckFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LocationCountryCheckFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final LocationCountryCheckFragmentHolder holder, final int position) {

            final LocationCountryModel locationModel = locationModels.get(position);
            holder.title.setText(locationModel.getTitle());
            holder.checkBox.setClickable(false);

            holder.checkBox.setChecked(selectedModels.contains(locationModel));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.checkBox.setChecked(!holder.checkBox.isChecked());
                    if(!(selectedModels.contains(locationModel))) {
                        selectedModels.add(locationModel);
                    }else
                        selectedModels.remove(locationModel);

                    showForward();

                }
            });


        }

        @Override
        public int getItemCount() {
            return locationModels.size();
        }

    }

    public void showForward(){
        if(selectedModels.size() > 0) {
            ((PromotionsActivity) getActivity()).toolbarSecondButton.setVisibility(View.VISIBLE);
            ((PromotionsActivity) getActivity()).toolbarSecondButton.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
        }else {
            ((PromotionsActivity) getActivity()).toolbarSecondButton.setVisibility(View.GONE);
        }
    }

}
