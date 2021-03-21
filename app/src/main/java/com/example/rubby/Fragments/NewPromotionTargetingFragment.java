package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Model.LocationCityModel;
import com.example.rubby.Model.LocationCountryModel;
import com.example.rubby.R;

import java.util.ArrayList;

public class NewPromotionTargetingFragment extends SmoothFragment implements ListBottomSheetWithCallbackFragment.onItemClickCallBack,SelectAgeRangeDialogFragment.onPositiveButtonClickCallBack{

    private int selectedPos;
    private int sexSelectedPos = 0;
    private int platformSelectedPos = -1;
    private String titleS[] = {"Пол", "Возраст", "Предпочитаемые питомцы", "Местоположение", "Предпочитаемая платформа"};
    private String maleS[] = {"Не имеет значения","Мужской", "Женский"};
    private String platformS[] = {"Android", "IOS", "Windows"};
    public String petsNamesS[] = {"Собаки", "Коты", "Птицы", "Попугаи", "Щенки", "Котики", "Бараны", "Хомяки", "Морские свинки", "Шиншилы", "Ленивцы", "Змеи", "Хамелеоны", "Бобры", "Барсуки", "Скунсы", "Дикие животные", "Домашние питомцы", "Суслики", "Травоядные", "Всеядные", "Лесные животные", "Домашний скот", "Рыбы", "Кошачие"};
    private String petsSubtitle;
    public static SparseArray<ArrayList<LocationCityModel>> selectedLocations = new SparseArray<>();
    private int firstAgeValue = -1;
    private int secondAgeValue = -1;
    public ArrayList<Integer> petsSelectedPositions = new ArrayList<>();
    public NewPromotionPetsSelectListFragment promotionsPetsSelectListFragment;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ListBottomSheetWithCallbackFragment listBottomSheetWithCallbackFragment = new ListBottomSheetWithCallbackFragment();
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NewPromotionTargetingFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        listBottomSheetWithCallbackFragment.registerOnItemClickCallBack(this);
        if(promotionsPetsSelectListFragment == null || promotionsPetsSelectListFragment.selectedPositions.size() == 0)
            petsSubtitle = "Не выбраны";
        else {
            petsSubtitle = "";
            String string = "";
            for(int i : petsSelectedPositions){
                if(i != petsSelectedPositions.get(petsSelectedPositions.size() - 1))
                    string = petsNamesS[i] + ", ";
                else
                    string = petsNamesS[i];
                if(petsSubtitle.length() >= 1) {
                    string = string.replaceAll("([A-Z])", "_$1").toLowerCase();
                }
                petsSubtitle = petsSubtitle + string;
            }
            ((PromotionsActivity)getActivity()).promotionsModel.auditoryPets = petsSubtitle;
        }

        return v;
    }

    @Override
    public void onPositiveButtonClick(int firstValue, int secondValue) {
        if(firstValue <= 0)
            this.firstAgeValue = -1;
        else
            this.firstAgeValue = firstValue;
        if(firstValue == -1 && secondValue == -1) {
            this.secondAgeValue = -1;
        }else if(secondValue == -1)
            this.secondAgeValue = 100;
        else
            this.secondAgeValue = secondValue;
        rAdapter.notifyItemChanged(selectedPos);
    }

    private class NewPromotionTargetingFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ConstraintLayout layout;

        public NewPromotionTargetingFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleItemSubtitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleItemLayout);

        }

    }

    @Override
    public void onItemClick(int position, ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentAdapter rAdapter, ListBottomSheetWithCallbackFragment.ListBottomSheetWithCallbackFragmentHolder viewHolder, ListBottomSheetWithCallbackFragment listBottomSheetWithCallbackFragment) {
        if(selectedPos == 0) {
            sexSelectedPos = position;
            ((PromotionsActivity)getActivity()).promotionsModel.auditorySex = position;
        }else if(selectedPos == 4) {
            platformSelectedPos = position;
            ((PromotionsActivity)getActivity()).promotionsModel.auditoryPlatform = position;
        }

        this.rAdapter.notifyItemChanged(selectedPos);
        listBottomSheetWithCallbackFragment.dismiss();
    }

    private class NewPromotionTargetingFragmentAdapter extends RecyclerView.Adapter<NewPromotionTargetingFragmentHolder> {

        @NonNull
        @Override
        public NewPromotionTargetingFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NewPromotionTargetingFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final NewPromotionTargetingFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);

            if (position == 0)
                holder.subtitle.setText(maleS[sexSelectedPos]);
            else if (position == 1) {
                if(firstAgeValue <= 0 && secondAgeValue <= 0)
                    holder.subtitle.setText("Не выбран");
                else if(firstAgeValue == -1)
                    holder.subtitle.setText("До " + secondAgeValue + " лет");
                else if(secondAgeValue == 100)
                    holder.subtitle.setText("От " + firstAgeValue + " лет");
                else
                    holder.subtitle.setText("От " + firstAgeValue + " до " + secondAgeValue + " лет");
                if(!(holder.subtitle.getText().equals("Не выбран")))
                    ((PromotionsActivity)getActivity()).promotionsModel.auditoryAge = holder.subtitle.getText().toString();
            }else if(position == 2){
                holder.subtitle.setText(petsSubtitle);
            }else if (position == 3) {
                if(selectedLocations.size() == 0)
                    holder.subtitle.setText("Не выбано");
                else
                    holder.subtitle.setText(getLocationText());
            }else if (position == 4) {
                if (platformSelectedPos != -1)
                    holder.subtitle.setText(platformS[platformSelectedPos]);
                else
                    holder.subtitle.setText("Не выбрана");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos = holder.getLayoutPosition();
                    if (position == 0){
                        listBottomSheetWithCallbackFragment.title = "Пол";
                        listBottomSheetWithCallbackFragment.titleS = maleS;
                        listBottomSheetWithCallbackFragment.selectedPos = sexSelectedPos;
                        listBottomSheetWithCallbackFragment.show(PromotionsActivity.fragmentManager, null);
                    }else if(position == 1){
                        SelectAgeRangeDialogFragment selectAgeRangeDialogFragment = new SelectAgeRangeDialogFragment();
                        selectAgeRangeDialogFragment.registerOnPositiveButtonCallBack(NewPromotionTargetingFragment.this);
                        PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, selectAgeRangeDialogFragment, null).commit();
                    }else if (position == 2) {
                        if (promotionsPetsSelectListFragment != null)
                            petsSelectedPositions = promotionsPetsSelectListFragment.selectedPositions;
                        promotionsPetsSelectListFragment = new NewPromotionPetsSelectListFragment();
                        if (petsSelectedPositions != null)
                            promotionsPetsSelectListFragment.selectedPositions = petsSelectedPositions;
                        PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsPetsSelectListFragment, null).commit();
                    }else if(position == 3) {
                        LocationCountryCheckFragment.selectedModels.clear();
                        LocationCountryCheckFragment locationCountryCheckFragment = new LocationCountryCheckFragment();
                        PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, locationCountryCheckFragment, null).commit();
                    }else if (position == 4) {
                        listBottomSheetWithCallbackFragment.title = "Предпочитаемая платформа";
                        listBottomSheetWithCallbackFragment.titleS = platformS;
                        listBottomSheetWithCallbackFragment.selectedPos = platformSelectedPos;
                        listBottomSheetWithCallbackFragment.show(PromotionsActivity.fragmentManager, null);
                    }
                }
            });
        }

        private String getLocationText(){

            String text = null;
            for(int i = 0;i<selectedLocations.size();i++) {
                ArrayList<LocationCityModel> arrayList = selectedLocations.valueAt(i);
                if(arrayList.size() == 0) {
                    if(text != null)
                        text = new StringBuilder().append(text).append(LocationCountryCheckFragment.selectedModels.get(i).getTitle()).toString();
                    else
                        text = new StringBuilder().append(LocationCountryCheckFragment.selectedModels.get(i).getTitle()).toString();
                }else {
                    String cities = null;
                    for(LocationCityModel locationCityModel : arrayList){
                        if(cities != null)
                            cities = new StringBuilder().append(cities).append(locationCityModel.getTitle()).toString();
                        else
                            cities = new StringBuilder().append(locationCityModel.getTitle()).toString();
                        if(arrayList.indexOf(locationCityModel)+1 != arrayList.size()){
                            cities = new StringBuilder().append(cities).append(", ").toString();
                        }
                    }
                    if(text != null)
                        text = new StringBuilder().append(text).append(LocationCountryModel.getAllList().get(arrayList.get(0).countryKey).getTitle()).append(" (" + cities + ")").toString();
                    else
                        text = new StringBuilder().append(LocationCountryModel.getAllList().get(arrayList.get(0).countryKey).getTitle()).append((" (" + cities + ")")).toString();
                }
                if((i+1) != selectedLocations.size())
                    text = new StringBuilder().append(text).append(", ").toString();
            }

            ((PromotionsActivity)getActivity()).promotionsModel.auditoryLocation = text;
            return text;

        }

        @Override
        public int getItemCount() {
            return titleS.length;
        }

    }
}
