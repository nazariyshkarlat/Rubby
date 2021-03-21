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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.Model.LocationCountryModel;
import com.example.rubby.R;

import java.util.ArrayList;

public class LocationCountrySelectFragment extends ChildFragment implements SearchTextInputLayoutFragment.onTextChangeCallBack {

    private ArrayList<LocationCountryModel> locationModels = new ArrayList<>();
    private SearchTextInputLayoutFragment searchTextInputLayoutFragment = new SearchTextInputLayoutFragment();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public LocationCountryModel selectedModel;
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
        rAdapter = new LocationSelectFragmentAdapter();
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

    private class LocationSelectFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private ConstraintLayout layout;

        public LocationSelectFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconLayout);

        }

    }

    private class LocationSelectFragmentAdapter extends RecyclerView.Adapter<LocationSelectFragmentHolder> {

        @NonNull
        @Override
        public LocationSelectFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LocationSelectFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final LocationSelectFragmentHolder holder, final int position) {

            final LocationCountryModel locationModel = locationModels.get(position);
            holder.title.setText(locationModel.getTitle());
            holder.icon.setImageResource(R.drawable.ic_outline_check_selector_24dp);

            holder.icon.setBackgroundResource(0);

            if(selectedModel != null)
                holder.icon.setSelected(locationModel.equals(selectedModel));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((AccountEditActivity)getActivity()).toolbarSecondButton.getVisibility() != View.VISIBLE) {
                        ((AccountEditActivity) getActivity()).toolbarSecondButton.setVisibility(View.VISIBLE);
                        ((AccountEditActivity) getActivity()).toolbarSecondButton.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
                    }
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

}
