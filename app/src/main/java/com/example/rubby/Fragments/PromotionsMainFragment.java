package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.Model.PromotionsModel;
import com.example.rubby.R;

public class PromotionsMainFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    public static FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tabs_view_pager_center_frame_layout_fab_button, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.tabsViewPagerCenterFrameLayoutFloatingActionButtonViewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabsViewPagerCenterFrameLayoutFloatingActionButtonTabLayout);
        frameLayout = (FrameLayout) v.findViewById(R.id.tabsViewPagerCenterFrameLayoutFloatingActionButtonFrameLayout);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.tabsViewPagerCenterFrameLayoutFloatingActionButtonFloatingActionButton);
        tabLayout.addTab(tabLayout.newTab().setText("Активные"));
        tabLayout.addTab(tabLayout.newTab().setText("Ожидающие"));
        tabLayout.addTab(tabLayout.newTab().setText("Неактивные"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PromotionsActivity)getActivity()).promotionsModel = new PromotionsModel();
                NewPromotionTypeFragment newPromotionTypeFragment = new NewPromotionTypeFragment();
                PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, newPromotionTypeFragment, null).commit();
            }
        });
        return v;
    }

    class PagerAdapter extends FragmentPagerAdapter {

        int numberOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.numberOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PromotionsListFragment();
                case 1:
                    return new PromotionsListFragment();
                case 2:
                    return new PromotionsListFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numberOfTabs;
        }

    }


}
