package com.example.rubby.Activities.AppCompatActivities;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Database.ThemeDatabase;
import com.example.rubby.Fragments.AccountEditFragment;
import com.example.rubby.Fragments.LanguageFragment;
import com.example.rubby.Fragments.PersonalDataFragment;
import com.example.rubby.Fragments.PersonalizationFragment;
import com.example.rubby.Fragments.ProfileEditFragment;
import com.example.rubby.Fragments.SafetyFragment;
import com.example.rubby.Fragments.SearchBarFragment;
import com.example.rubby.Fragments.ThemeChangeFragment;
import com.example.rubby.Fragments.ToolbarIconsFragment;
import com.example.rubby.R;

public class PersonalizationActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static ConstraintLayout constraintLayout;
    private TextView toolText;
    private ThemeDatabase themeDatabase;
    public static boolean recreated = false;
    private ImageView backButton;
    public SearchBarFragment searchBarFragment = new SearchBarFragment();
    private FrameLayout toolbarIconsFrameLayout;
    public ToolbarIconsFragment toolbarIconsFragment = new ToolbarIconsFragment();
    private Fragment fragment;
    public static FragmentActivity homeActivity;
    private ConstraintLayout toolbarLayout;
    public static Context context;
    public Fragment currentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.default_frame_layout);

        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.defaultFrameLayout);

        if (fragment == null) {

            fragment = new PersonalizationFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.defaultFrameLayout, fragment)
                    .commit();

        }
        constraintLayout = (ConstraintLayout) findViewById(R.id.defaultFrameLayoutConstraintLayout);
        toolbarIconsFrameLayout = (FrameLayout) findViewById(R.id.toolbarIconsFrameLayout);
        toolText = (TextView) findViewById(R.id.defaultToolbarTitle);
        backButton = (ImageView) findViewById(R.id.defaultToolbarBackButton);
        toolbarLayout = (ConstraintLayout) findViewById(R.id.toolbarDefaultLayout);
        context = getApplicationContext();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(!(fragment instanceof BottomSheetDialogFragment))
            currentFragment = fragment;
        if(currentFragment instanceof PersonalizationFragment)
            toolText.setText("Персонализация");
        else if(currentFragment instanceof LanguageFragment) {
            toolText.setText("Язык");
        }else if(currentFragment instanceof ThemeChangeFragment){
            toolText.setText("Тема");
        }
        if(!(fragment instanceof BottomSheetDialogFragment) && !(fragment instanceof DialogFragment))
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
    public void onBackPressed() {
        if (currentFragment instanceof LanguageFragment){
            PersonalizationActivity.fragmentManager.beginTransaction().remove(toolbarIconsFragment).commit();
            PersonalizationActivity.fragmentManager.beginTransaction().remove(searchBarFragment).commit();
            super.onBackPressed();
        }else if(currentFragment instanceof SearchBarFragment) {
             if (((SearchBarFragment) currentFragment).isShown)
                 ((SearchBarFragment) currentFragment).hideSearch();
             else
                 super.onBackPressed();
         }else
             super.onBackPressed();
        currentFragment = fragmentManager.findFragmentById(R.id.defaultFrameLayout);
        onAttachFragment(currentFragment);
    }

}
