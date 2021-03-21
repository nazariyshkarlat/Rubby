package com.example.rubby.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.HomeActivity;
import com.example.rubby.R;

public class HomeBottomSheetFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private String titleS[] = {"Добавить публикацию", "Добавить историю", "Сообщения", "Уведомления", "Популярное", "Избранное","Настройки"};
    private int iconsI[] = {R.drawable.ic_outline_add_selector_24dp,R.drawable.ic_outline_photo_camera_selector_24dp,R.drawable.ic_outline_mail_selector_24dp,R.drawable.ic_outline_notifications_selector_24dp,R.drawable.ic_outline_whatshot_seletor_24dp,R.drawable.ic_outline_grade_selector_24dp,R.drawable.ic_outline_settings_selector_24dp};
    private RecyclerView.Adapter rAdapter;
    public static SettingsFragment settingsFragment = new SettingsFragment();
    public static Fragment addedFragment;
    public static Fragment currentFragment;
    private boolean click = false;
    public static int selectedPos = -1;

    @Override
    public void onDismiss(DialogInterface dialog) {
        /*
        HomeActivity.sheetIsShowing = false;
        HomeActivity.bottomRecyclerView.setClickable(true);
        HomeActivity.changeToolbar();
        if(!click) {
            HomeActivity.updateBottomToolbar(HomeActivity.active);
        }
        */
        super.onDismiss(dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.home_bottom_sheet,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.homeBlistBottomSheetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rAdapter = new HomeBottomSheetFragment.HomeBottomSheetAdapter();
        recyclerView.setAdapter(rAdapter);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) bottomSheet.getParent();
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2);
                coordinatorLayout.getParent().requestLayout();
            }
        });
        return v;

    }

        private class HomeBottomSheetHolder extends RecyclerView.ViewHolder{

        private TextView titles;
        private ImageView icon;

        public HomeBottomSheetHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.home_bottom_sheet_item,parent,false));
            titles = (TextView) itemView.findViewById(R.id.homeBottomSheetTextView);
            icon = (ImageView) itemView.findViewById(R.id.homeBottomSheetIcon);
        }

    }

    private class HomeBottomSheetAdapter extends RecyclerView.Adapter<HomeBottomSheetFragment.HomeBottomSheetHolder>{

        private Context context;

        @NonNull
        @Override
        public HomeBottomSheetFragment.HomeBottomSheetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new HomeBottomSheetFragment.HomeBottomSheetHolder(layoutInflater,parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final HomeBottomSheetFragment.HomeBottomSheetHolder holder, final int position) {

            holder.titles.setText(titleS[position]);
            holder.icon.setImageResource(iconsI[position]);

            holder.itemView.setSelected(selectedPos == position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notifyItemChanged(selectedPos);
                    selectedPos = holder.getLayoutPosition();
                    notifyItemChanged(selectedPos);

                    if(position == 6) {
                        click = true;
                        currentFragment = settingsFragment;
                        for(Fragment f : HomeActivity.fragments){
                                if(f instanceof SettingsFragment) {
                                    addedFragment = f;
                                }
                            }
                            if(addedFragment != null) {
                                HomeActivity.fragmentManager.beginTransaction().hide(HomeActivity.active).show(settingsFragment).commit();
                                HomeActivity.fragments.remove(addedFragment);
                            }else {
                                settingsFragment = new SettingsFragment();
                                HomeActivity.fragmentManager.beginTransaction().add(R.id.fragmentContainerMain, settingsFragment, null).commit();
                                HomeActivity.fragmentManager.beginTransaction().hide(HomeActivity.active).commit();
                            }
                            addedFragment = null;
                            HomeActivity.active = settingsFragment;
                            HomeActivity.fragments.add(settingsFragment);
                            HomeBottomSheetFragment.this.dismiss();
                    }
                }
            });
            }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

}
