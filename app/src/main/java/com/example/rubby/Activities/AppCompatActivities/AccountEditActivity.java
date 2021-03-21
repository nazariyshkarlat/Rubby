package com.example.rubby.Activities.AppCompatActivities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Database.PersonalDataDatabase;
import com.example.rubby.Database.ThemeDatabase;
import com.example.rubby.Fragments.AccountEditFragment;
import com.example.rubby.Fragments.LocationCitySelectFragment;
import com.example.rubby.Fragments.LocationCountrySelectFragment;
import com.example.rubby.Fragments.PersonalDataFragment;
import com.example.rubby.Fragments.ProfileEditFragment;
import com.example.rubby.Fragments.SafetyFragment;
import com.example.rubby.R;

public class AccountEditActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static ConstraintLayout constraintLayout;
    private TextView toolText;
    private ImageView backButton;
    private ThemeDatabase themeDatabase;
    private Fragment fragment;
    public ImageView toolbarSecondButton;
    public static Context context;
    private ConstraintLayout toolbarLayout;
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

            fragment = new AccountEditFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.defaultFrameLayout, fragment)
                    .commit();

        }
        constraintLayout = (ConstraintLayout) findViewById(R.id.defaultFrameLayoutConstraintLayout);
        toolbarLayout = (ConstraintLayout) findViewById(R.id.toolbarDefaultLayout);
        toolText = (TextView) findViewById(R.id.defaultToolbarTitle);
        backButton = (ImageView) findViewById(R.id.defaultToolbarBackButton);
        toolbarSecondButton = (ImageView) findViewById(R.id.defaultToolbarForwardButton);
        context = getApplicationContext();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onAttachFragment(final Fragment fragment) {
        super.onAttachFragment(fragment);
        if(!(fragment instanceof BottomSheetDialogFragment) && !(fragment instanceof DialogFragment)) {
            toolbarLayout.setElevation(0);
            hideKeyboard();
            if(toolbarSecondButton.getVisibility() == View.VISIBLE)
                toolbarSecondButton.setVisibility(View.GONE);
        }
        currentFragment = fragment;
        if(fragment instanceof AccountEditFragment)
            toolText.setText("Учётная запись");
        else if(fragment instanceof ProfileEditFragment)
            toolText.setText("Редактировать профиль");
        else if(fragment instanceof PersonalDataFragment)
            toolText.setText("Личные данные");
        else if (fragment instanceof SafetyFragment)
            toolText.setText("Настройки приватности");
        else if(fragment instanceof LocationCountrySelectFragment) {
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocationCitySelectFragment locationCitySelectFragment = new LocationCitySelectFragment();
                    locationCitySelectFragment.key = ((LocationCountrySelectFragment) fragment).selectedModel.getKey();
                    AccountEditActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout,locationCitySelectFragment,null).commit();
                }
            });
            toolText.setText("Выбор местоположения");
        }else if(fragment instanceof LocationCitySelectFragment){
            toolbarSecondButton.setVisibility(View.VISIBLE);
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_check_active_24dp);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LocationCitySelectFragment) fragment).setValue(new PersonalDataDatabase(context), PersonalDataDatabase.DATABASE_TABLE, PersonalDataDatabase.LOCATION, PersonalDataDatabase.ID);
                    onBackPressed();
                    onBackPressed();
                }
            });
        }
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
        if(currentFragment instanceof ProfileEditFragment) {
            ((ProfileEditFragment) currentFragment).profileEditBack(getResources(), AccountEditActivity.constraintLayout, this);
        }else
            super.onBackPressed();
        currentFragment = fragmentManager.findFragmentById(R.id.defaultFrameLayout);
        onAttachFragment(currentFragment);
        hideKeyboard();
    }
}
