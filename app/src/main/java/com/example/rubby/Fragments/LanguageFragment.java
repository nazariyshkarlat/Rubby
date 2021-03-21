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

import com.example.rubby.Activities.AppCompatActivities.PersonalizationActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Model.LanguageModel;
import com.example.rubby.R;

import java.util.ArrayList;

public class LanguageFragment extends SmoothFragment implements ToolbarIconsFragment.onCreateFragmentCallBack,SearchBarFragment.onTextChangeCallBack {

    private ArrayList<LanguageModel> languageModels = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ArrayList<LanguageModel> allModels = LanguageModel.addAll();
    private LanguageModel selectedModel;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        if(!((PersonalizationActivity)getActivity()).toolbarIconsFragment.isAdded())
            PersonalizationActivity.fragmentManager.beginTransaction().add(R.id.toolbarIconsFrameLayout, ((PersonalizationActivity)getActivity()).toolbarIconsFragment, null).commit();
        if(!((PersonalizationActivity)getActivity()).searchBarFragment.isAdded())
            PersonalizationActivity.fragmentManager.beginTransaction().add(R.id.toolbarIconsFrameLayout, ((PersonalizationActivity)getActivity()).searchBarFragment, null).commit();
        ((PersonalizationActivity)getActivity()).searchBarFragment.hint = "Найти язык";
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(PersonalizationActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new LanguageFragmentAdapter();
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(rAdapter);
        languageModels.addAll(LanguageModel.addAll());
        ((PersonalizationActivity)getActivity()).toolbarIconsFragment.registerOnCreateFragmentClickCallBack(this);
        ((PersonalizationActivity)getActivity()).searchBarFragment.registerOnTextChangeCallBack(this);
        selectedModel = languageModels.get(0);

        return v;
    }

    @Override
    public void onCreateFragment() {
        ((PersonalizationActivity)getActivity()).toolbarIconsFragment.setIcons(R.drawable.ic_outline_search_87_24dp,0,0,null,null,null);
        ((PersonalizationActivity)getActivity()).searchBarFragment.searchIcon = ((PersonalizationActivity)getActivity()).toolbarIconsFragment.firstIcon;
    }

    @Override
    public void onTextChange(String text) {
        String validText = text.toLowerCase();
        ArrayList<LanguageModel> arrayList = new ArrayList<>();
        languageModels.clear();
        arrayList.addAll(LanguageModel.addAll());
        for (int i = 0;i<arrayList.size();i++) {
            if (arrayList.get(i).getTitle().toLowerCase().startsWith(validText)) {
                languageModels.add(arrayList.get(i));
            }
        }
        rAdapter.notifyDataSetChanged();
    }

    private class LanguageFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ImageView icon;
        private ConstraintLayout layout;

        public LanguageFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle_icon, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleIconItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleIconItemSubtitle);
            icon = (ImageView) itemView.findViewById(R.id.titleSubtitleIconItemIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleIconItemLayout);

        }

    }

    private class LanguageFragmentAdapter extends RecyclerView.Adapter<LanguageFragmentHolder> {

        @NonNull
        @Override
        public LanguageFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LanguageFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final LanguageFragmentHolder holder, final int position) {

            final LanguageModel languageModel = languageModels.get(position);
            holder.title.setText(languageModel.getTitle());
            holder.subtitle.setText(languageModel.getSubtitle());

            holder.icon.setImageResource(R.drawable.ic_outline_check_selector_24dp);
            holder.icon.setBackgroundResource(0);
            holder.icon.setSelected(languageModel.equals(selectedModel));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedModel.setSelected(false);
                    languageModel.setSelected(true);
                    rAdapter.notifyItemChanged(languageModels.indexOf(selectedModel));
                    rAdapter.notifyItemChanged(languageModels.indexOf(languageModel));
                    selectedModel = languageModel;
                }
            });

        }

        @Override
        public int getItemCount() {
            return languageModels.size();
        }
    }

}
