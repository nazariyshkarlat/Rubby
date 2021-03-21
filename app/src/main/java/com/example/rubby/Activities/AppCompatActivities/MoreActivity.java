package com.example.rubby.Activities.AppCompatActivities;

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

import com.example.rubby.Database.ThemeDatabase;
import com.example.rubby.Fragments.MoreFragment;
import com.example.rubby.Fragments.StorageFragment;
import com.example.rubby.R;

public class MoreActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static ConstraintLayout constraintLayout;
    private TextView toolText;
    private ImageView backButton;
    private ThemeDatabase themeDatabase;
    private Fragment fragment;
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

            fragment = new MoreFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.defaultFrameLayout, fragment)
                    .commit();

        }
        constraintLayout = (ConstraintLayout) findViewById(R.id.defaultFrameLayoutConstraintLayout);
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
        if(fragment instanceof MoreFragment)
            toolText.setText("Другое");
        if(!(fragment instanceof BottomSheetDialogFragment) && !(fragment instanceof DialogFragment))
            toolbarLayout.setElevation(0);    }

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
        super.onBackPressed();
        currentFragment = fragmentManager.findFragmentById(R.id.defaultFrameLayout);
        onAttachFragment(currentFragment);
    }

}
