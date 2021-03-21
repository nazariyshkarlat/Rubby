package com.example.rubby.Activities.AppCompatActivities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.rubby.Database.NumberMailDatabase;
import com.example.rubby.Database.ThemeDatabase;
import com.example.rubby.Fragments.BottomBarFragment;
import com.example.rubby.Fragments.HomeBottomSheetFragment;
import com.example.rubby.Fragments.HomeFragment;
import com.example.rubby.Fragments.SearchFragment;
import com.example.rubby.Fragments.SettingsFragment;
import com.example.rubby.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements BottomBarFragment.onItemClickCallBack {

    public static TextView toolText;
    private TypedValue typedValue = new TypedValue();
    public static int selectableItemBackground;
    public static LayoutInflater layoutInflater;
    public static boolean sheetIsShowing = false;
    private HomeBottomSheetFragment homeBottomSheetFragment = new HomeBottomSheetFragment();
    public static Fragment currentFragment;
    public static Context context;
    public static FrameLayout bottomBar;
    private BottomBarFragment bottomBarFragment = new BottomBarFragment();
    private NumberMailDatabase numberMailDatabase;
    public static Typeface regular;
    public static Typeface medium;
    public static Resources resources;
    public static FrameLayout toolbarTools;
    public static final HomeFragment homeFragment = new HomeFragment();
    public final static SearchFragment searchFragment = new SearchFragment();
    public static Fragment active;
    public static ConstraintLayout toolbarLayout;
    private ArrayList<String> fragmentsNames = new ArrayList<>();
    public static ArrayList<Fragment> fragments = new ArrayList<>();
    public static FragmentManager fragmentManager;
    private ThemeDatabase themeDatabase;
    public static FrameLayout frameLayout;
    public static ConstraintLayout constraintLayout;

    @Override
    public void onCreate(Bundle savedinstanceState) {

        super.onCreate(savedinstanceState);
        themeDatabase = new ThemeDatabase(getApplicationContext());
        switch (themeDatabase.getValue(ThemeDatabase.THEME_POS)){
            case 0:
                setTheme(R.style.LightBlueTheme);
                break;
            case 1:
                setTheme(R.style.LightGreenTheme);
                break;
            case 2:
                setTheme(R.style.NightBlueTheme);
                break;
            case 3:
                setTheme(R.style.NightGreenTheme);
                break;
            case 4:
                setTheme(R.style.DarkBlueTheme);
                break;
            case 5:
                setTheme(R.style.DarkGreenTheme);
                break;

        }
        setContentView(R.layout.frame_layout_main);

        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainerMain);

        if (fragment == null) {

            fragment = new HomeFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainerMain, fragment)
                    .commit();

        }

        if(!bottomBarFragment.isAdded())
            fragmentManager.beginTransaction().add(R.id.bottomBarFrameLayout, bottomBarFragment, null).commit();
        bottomBarFragment.registerOnItemClickCallBack(this);
        frameLayout = (FrameLayout) findViewById(R.id.fragmentContainerMain);
        toolText = (TextView) findViewById(R.id.homeToolbarTitle);
        context = getApplicationContext();
        resources = getResources();
        layoutInflater = getLayoutInflater();
        regular = ResourcesCompat.getFont(this, R.font.roboto_regular);
        medium = ResourcesCompat.getFont(this, R.font.roboto_medium);
        constraintLayout = (ConstraintLayout) findViewById(R.id.frameConstraintLayout);
        bottomBar = (FrameLayout) findViewById(R.id.bottomBarFrameLayout);
        toolbarTools = (FrameLayout) findViewById(R.id.frameLayoutToolbar);
        toolbarLayout = (ConstraintLayout) findViewById(R.id.homeToolbarLayout);
        fragmentManager.beginTransaction().add(R.id.fragmentContainerMain, searchFragment, null).hide(searchFragment).commit();
        if(active == null) {
            active = homeFragment;
            fragments.add(homeFragment);
            toolText.setText("Главная");
        }
        if(toolText.getText() == null){
            toolText.setText("Главная");
        }
        numberMailDatabase = new NumberMailDatabase(getApplicationContext());
        getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        selectableItemBackground = typedValue.resourceId;
         numberMailDatabase.setValue(NumberMailDatabase.NUMBER,"+380973250545");
        numberMailDatabase.setValue(NumberMailDatabase.MAIL,"inasar0327@gmail.com");

    }

    @Override
    protected void onDestroy() {
        HomeBottomSheetFragment.selectedPos = -1;
        hideKeyboard();
        super.onDestroy();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragments.size() != 1) {
            hideKeyboard();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
            if (fragments.size() == 1) {

                finish();

            } else {
                fragmentsNames.clear();
                for (Fragment fragment : fragments) {
                    fragmentsNames.add(fragment.getClass().getName());
                }
                for(Fragment fragment : fragmentManager.getFragments()){
                    if(fragmentsNames.contains(fragment.getClass().getName()))
                            fragments.set(fragmentsNames.indexOf(fragment.getClass().getName()), fragment);
                }
                Fragment fragment = fragments.get(fragments.size() - 1);
                fragmentManager.beginTransaction().hide(fragment).commit();
                fragments.remove(fragments.size() - 1);
                fragment = fragments.get(fragments.size() - 1);
                fragmentManager.beginTransaction().show(fragment).commit();
                active = fragment;
            }
            toolbarLayout.setElevation(0);
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onItemClick(int selectedPos) {
        switch (selectedPos) {
            case 0:
                if(!sheetIsShowing) {
                    if(!homeBottomSheetFragment.isAdded())
                        homeBottomSheetFragment.show(fragmentManager, null);
                }
                break;
            case 1:
                fragmentManager.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
                break;
            case 2:
                fragmentManager.beginTransaction().hide(active).show(searchFragment).commit();
                active = searchFragment;
                break;
            default:
                break;
        }
        if(selectedPos != 0) {
            if (!sheetIsShowing) {
                if (!fragments.contains(active)) {
                    fragments.add(active);
                }else {
                    fragments.remove(active);
                }
            }
        }
    }

}

