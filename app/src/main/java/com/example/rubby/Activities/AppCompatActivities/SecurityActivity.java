package com.example.rubby.Activities.AppCompatActivities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Other.ColorsAndDrawables;
import com.example.rubby.Database.PasswordsDatabase;
import com.example.rubby.Database.ThemeDatabase;
import com.example.rubby.Fragments.BlackListFragment;
import com.example.rubby.Fragments.ChangeMailFragment;
import com.example.rubby.Fragments.ChangePhoneFragment;
import com.example.rubby.Fragments.CodeSecurityFragment;
import com.example.rubby.Fragments.CodeVerificationFragment;
import com.example.rubby.Fragments.DialogAlertCenterFragment;
import com.example.rubby.Fragments.HistoryFragment;
import com.example.rubby.Fragments.NumberMailFragment;
import com.example.rubby.Fragments.PasswordRegistrationFragment;
import com.example.rubby.Fragments.PasswordRequestFragment;
import com.example.rubby.Fragments.SecurityFragment;
import com.example.rubby.Fragments.TwoStepAuthenticationFragment;
import com.example.rubby.R;

import static com.example.rubby.Fragments.PasswordRequestFragment.PASSWORD_CODE;
import static com.example.rubby.Fragments.PasswordRequestFragment.TWO_STEP_AUTHENTICATION;

public class SecurityActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static ConstraintLayout constraintLayout;
    public static ConstraintLayout toolbarLayout;
    private ImageView toolbarSecondIcon;
    private TextView toolText;
    private PasswordsDatabase passwordsDatabase;
    private ImageView backButton;
    private ThemeDatabase themeDatabase;
    public ColorsAndDrawables colorsAndDrawables;
    private SQLiteDatabase sqLiteDatabase;
    public static int fragmentType;
    private Cursor cursor;
    private Fragment fragment;
    public static Context context;
    public Fragment currentFragment;

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
        setContentView(R.layout.default_frame_layout);

        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.defaultFrameLayout);

        if (fragment == null) {

            fragment = new SecurityFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.defaultFrameLayout, fragment)
                    .commit();

        }
        toolbarLayout = (ConstraintLayout) findViewById(R.id.toolbarDefaultLayout);
        constraintLayout = (ConstraintLayout) findViewById(R.id.defaultFrameLayoutConstraintLayout);
        toolText = (TextView) findViewById(R.id.defaultToolbarTitle);
        toolbarSecondIcon = (ImageView) findViewById(R.id.defaultToolbarForwardButton);
        backButton = (ImageView) findViewById(R.id.defaultToolbarBackButton);
        context = getApplicationContext();
        passwordsDatabase = new PasswordsDatabase(context);
        sqLiteDatabase = passwordsDatabase.getReadableDatabase();
        cursor = sqLiteDatabase.query(PasswordsDatabase.DATABASE_TABLE, null, null, null, null, null, null);
        if(!cursor.moveToFirst())
            passwordsDatabase.setValue(PasswordsDatabase.PASSWORD , "valenoc228");
        colorsAndDrawables = new ColorsAndDrawables(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(fragmentType == TWO_STEP_AUTHENTICATION){
            TwoStepAuthenticationFragment twoStepAuthenticationFragment = new TwoStepAuthenticationFragment();
            fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, twoStepAuthenticationFragment, null).commit();
        }else if(fragmentType == PASSWORD_CODE){
            CodeSecurityFragment codeSecurityFragment = new CodeSecurityFragment();
            fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, codeSecurityFragment, null).commit();
        }
        fragmentType = 0;
    }

    @Override
    public void onAttachFragment(final Fragment fragment) {
        super.onAttachFragment(fragment);
        if(!(fragment instanceof BottomSheetDialogFragment) && !(fragment instanceof DialogFragment)) {
            toolbarLayout.setElevation(0);
            hideKeyboard();
            if(toolbarSecondIcon.getVisibility() == View.VISIBLE)
                toolbarSecondIcon.setVisibility(View.GONE);
            currentFragment = fragment;
        }
        if(fragment instanceof SecurityFragment)
            toolText.setText("Безопасность");
        else if((fragment instanceof PasswordRequestFragment) && SecurityFragment.selectedPos == 0) {
            toolbarSecondIcon.setVisibility(View.VISIBLE);
            toolbarSecondIcon.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PasswordRequestFragment) fragment).buttonClick(((PasswordRequestFragment) fragment).holder.textInputLayoutCustom,((PasswordRequestFragment) fragment).holder.subtitle,((PasswordRequestFragment) fragment).holder.errorIcon,((PasswordRequestFragment) fragment).holder.layout);

                }
            });
            toolText.setText("Новый пароль");
        }else if((fragment instanceof PasswordRegistrationFragment) && (SecurityFragment.selectedPos == 0)){
            toolbarSecondIcon.setVisibility(View.VISIBLE);
            toolbarSecondIcon.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PasswordRegistrationFragment) fragment).buttonClick();

                }
            });
        }else if((fragment instanceof CodeVerificationFragment) && (SecurityFragment.selectedPos == 0)){
            toolbarSecondIcon.setVisibility(View.VISIBLE);
            toolbarSecondIcon.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CodeVerificationFragment) fragment).buttonClick(((CodeVerificationFragment) fragment).holder.textInputLayoutCustom,((CodeVerificationFragment) fragment).holder.subtitle,((CodeVerificationFragment) fragment).holder.errorIcon,((CodeVerificationFragment) fragment).holder.layout);

                }
        });}else if(fragment instanceof NumberMailFragment) {
            toolText.setText("Телефон и эл. адрес");
            toolbarSecondIcon.setVisibility(View.VISIBLE);
            toolbarSecondIcon.setImageResource(R.drawable.ic_round_help_outline_87_24dp);
            toolbarSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogAlertCenterFragment dialogAlertCenterFragment = new DialogAlertCenterFragment();
                    dialogAlertCenterFragment.showDialogAlert("Помощь", "С помощью номера телефона и эл. адреса можно сбросить пароль в случае утери доступа к аккаунту, а так же защитить его с помощью двухэтапной аутентификации.", "Закрыть", SecurityActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogAlertCenterFragment.dialogAlert.dismiss();
                        }
                    });
                }
            });
        }else if(fragment instanceof ChangePhoneFragment){
            toolbarSecondIcon.setVisibility(View.VISIBLE);
            toolbarSecondIcon.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChangePhoneFragment) fragment).buttonClick(((ChangePhoneFragment)fragment).introducedNumber,((ChangePhoneFragment) fragment).holder.textInputLayoutCustom,((ChangePhoneFragment) fragment).holder.subtitle,((ChangePhoneFragment) fragment).holder.errorIcon,((ChangePhoneFragment) fragment).holder.layout);

                }
            });
        }else if(fragment instanceof ChangeMailFragment){
            toolbarSecondIcon.setVisibility(View.VISIBLE);
            toolbarSecondIcon.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChangeMailFragment) fragment).buttonClick(((ChangeMailFragment)fragment).introducedMail,((ChangeMailFragment) fragment).holder.textInputLayoutCustom,((ChangeMailFragment) fragment).holder.subtitle,((ChangeMailFragment) fragment).holder.errorIcon,((ChangeMailFragment) fragment).holder.layout);

                }
            });
        } else if((fragment instanceof CodeSecurityFragment) || (fragment instanceof PasswordRequestFragment && SecurityFragment.selectedPos == 2))
            toolText.setText("Защита код-паролем");
        else if((fragment instanceof TwoStepAuthenticationFragment) || SecurityFragment.selectedPos == 3) {
            toolText.setText("Двухэтапная аутентификация");
        }else if(fragment instanceof BlackListFragment)
            toolText.setText("Чёрный список");
        else if(fragment instanceof HistoryFragment)
            toolText.setText("История активности");
    }

    @Override
    public void onBackPressed() {
        if(currentFragment instanceof TwoStepAuthenticationFragment) {
            ((TwoStepAuthenticationFragment) currentFragment).onBackClick();
        }else
            super.onBackPressed();
        hideKeyboard();
        currentFragment = fragmentManager.findFragmentById(R.id.defaultFrameLayout);
        onAttachFragment(currentFragment);
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

}
