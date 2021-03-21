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

import com.example.rubby.Fragments.PromotionInfoFragment;
import com.example.rubby.Other.ColorsAndDrawables;
import com.example.rubby.Database.PromotionsDatabase;
import com.example.rubby.Database.ThemeDatabase;
import com.example.rubby.Fragments.EmptyLayoutFragment;
import com.example.rubby.Fragments.LocationCityCheckFragment;
import com.example.rubby.Fragments.LocationCountryCheckFragment;
import com.example.rubby.Fragments.NewPromotionTypeFragment;
import com.example.rubby.Fragments.NewPromotionDurationFragment;
import com.example.rubby.Fragments.PromotionsFragment;
import com.example.rubby.Fragments.NewPromotionMainFragment;
import com.example.rubby.Fragments.NewPromotionNameFragment;
import com.example.rubby.Fragments.NewPromotionPetsSelectListFragment;
import com.example.rubby.Fragments.NewPromotionPostSelectFragment;
import com.example.rubby.Fragments.NewPromotionTargetingFragment;
import com.example.rubby.Fragments.PaymentMethodsFragment;
import com.example.rubby.Fragments.SelectAgeRangeDialogFragment;
import com.example.rubby.Model.PromotionsModel;
import com.example.rubby.R;

public class PromotionsActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public static ConstraintLayout constraintLayout;
    private TextView toolText;
    public PromotionsModel promotionsModel;
    private ThemeDatabase themeDatabase;
    public PromotionsDatabase promotionsDatabase;
    private ImageView backButton;
    private Fragment fragment;
    public ImageView toolbarSecondButton;
    public ColorsAndDrawables colorsAndDrawables;
    public static Context context;
    public static ConstraintLayout toolbarLayout;
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

            fragment = new PromotionsFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.defaultFrameLayout, fragment)
                    .commit();

        }
        colorsAndDrawables = new ColorsAndDrawables(this);
        constraintLayout = (ConstraintLayout) findViewById(R.id.defaultFrameLayoutConstraintLayout);
        toolText = (TextView) findViewById(R.id.defaultToolbarTitle);
        toolbarSecondButton = (ImageView) findViewById(R.id.defaultToolbarForwardButton);
        toolbarLayout = (ConstraintLayout) findViewById(R.id.toolbarDefaultLayout);
        backButton = (ImageView) findViewById(R.id.defaultToolbarBackButton);
        context = getApplicationContext();
        promotionsDatabase = new PromotionsDatabase(context);
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
        currentFragment = fragment;
        if(!(fragment instanceof BottomSheetDialogFragment) && !(fragment instanceof DialogFragment)) {
            toolbarLayout.setElevation(0);
            hideKeyboard();
            if(toolbarSecondButton.getVisibility() == View.VISIBLE)
                toolbarSecondButton.setVisibility(View.GONE);
        }
        if((fragment instanceof PromotionsFragment) || (fragment instanceof EmptyLayoutFragment))
            toolText.setText("Промоакции");
        else if(fragment instanceof NewPromotionTypeFragment) {
            toolText.setText("Тип продвижения");
            toolbarSecondButton.setVisibility(View.VISIBLE);
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((NewPromotionTypeFragment) fragment).selectedPos == 0){
                        NewPromotionPostSelectFragment promotionsPostSelectFragment = new NewPromotionPostSelectFragment();
                        PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsPostSelectFragment, null).commit();
                    }
                }
            });
            promotionsModel.campaignType = ((NewPromotionTypeFragment) fragment).selectedPos;
        }else if(fragment instanceof NewPromotionPostSelectFragment) {
            toolText.setText("Выберите публикацию");
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            if(((NewPromotionPostSelectFragment) fragment).selectedPos != -1)
                toolbarSecondButton.setVisibility(View.VISIBLE);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPromotionNameFragment promotionsNameFragment = new NewPromotionNameFragment();
                    PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsNameFragment, null).commit();
                }
            });
        }else if(fragment instanceof NewPromotionNameFragment) {
            toolText.setText("Название кампании");
            if(!((NewPromotionNameFragment) fragment).edit)
                toolbarSecondButton.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            else
                toolbarSecondButton.setImageResource(R.drawable.ic_outline_check_active_24dp);
            toolbarSecondButton.setVisibility(View.VISIBLE);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((NewPromotionNameFragment) fragment).buttonClick(((NewPromotionNameFragment) fragment).holder.textInputLayoutCustom,((NewPromotionNameFragment) fragment).holder.subtitle,((NewPromotionNameFragment) fragment).holder.errorIcon,((NewPromotionNameFragment) fragment).holder.layout);

                }
            });
        }else if(fragment instanceof NewPromotionTargetingFragment) {
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondButton.setVisibility(View.VISIBLE);
            toolText.setText("Таргетинг");
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPromotionMainFragment promotionsMainFragment = new NewPromotionMainFragment();
                    PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsMainFragment, null).commit();
                }
            });
        }else if(fragment instanceof NewPromotionPetsSelectListFragment) {
            toolText.setText("Предпочитаемые питомцы");
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_check_active_24dp);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentFragment = null;
                    onBackPressed();
                }
            });
        }else if(fragment instanceof SelectAgeRangeDialogFragment) {
            toolText.setText("Предпочитаемый возраст");
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_check_active_24dp);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((SelectAgeRangeDialogFragment) fragment).validValue) {
                        ((SelectAgeRangeDialogFragment) fragment).onPositiveButtonClickCallBack.onPositiveButtonClick(((SelectAgeRangeDialogFragment) fragment).firstValue, ((SelectAgeRangeDialogFragment) fragment).secondValue);
                        fragment.getActivity().onBackPressed();
                    }else {
                        ((SelectAgeRangeDialogFragment) fragment).acceptClick(((SelectAgeRangeDialogFragment) fragment).firstValue,((SelectAgeRangeDialogFragment) fragment).secondValue,((SelectAgeRangeDialogFragment) fragment).holder.firstTextInputLayout,((SelectAgeRangeDialogFragment) fragment).holder.subtitle,((SelectAgeRangeDialogFragment) fragment).holder.errorIcon,((SelectAgeRangeDialogFragment) fragment).holder.layout);
                    }
                }
            });
        }else if(fragment instanceof NewPromotionMainFragment){
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_arrow_forward_active_24dp);
            toolbarSecondButton.setVisibility(View.VISIBLE);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPromotionDurationFragment promotionsDurationFragment = new NewPromotionDurationFragment();
                    PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsDurationFragment, null).commit();
                    if(((NewPromotionMainFragment) fragment).secondHolder.getLayoutPosition() == 1)
                        promotionsModel.organizationName = ((NewPromotionMainFragment.NewPromotionMainFragmentSecondHolder)((NewPromotionMainFragment) fragment).secondHolder).editText.getText().toString();
                    else if(((NewPromotionMainFragment) fragment).secondHolder.getLayoutPosition() == 2)
                        promotionsModel.campaignAbout = ((NewPromotionMainFragment.NewPromotionMainFragmentSecondHolder)((NewPromotionMainFragment) fragment).secondHolder).editText.getText().toString();
                    else if(((NewPromotionMainFragment) fragment).secondHolder.getLayoutPosition() == 3)
                        promotionsModel.productUrl = ((NewPromotionMainFragment.NewPromotionMainFragmentSecondHolder)((NewPromotionMainFragment) fragment).secondHolder).editText.getText().toString();
                }
            });
            toolText.setText("Основное");
        }else if(fragment instanceof NewPromotionDurationFragment)
            toolText.setText("Продолжительность");
        else if(fragment instanceof PaymentMethodsFragment)
            toolText.setText("Способы оплаты");
        else if(fragment instanceof LocationCountryCheckFragment){
            ((LocationCountryCheckFragment)fragment).showForward();
            toolText.setText("Местоположение");
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocationCityCheckFragment locationCityCheckFragment = new LocationCityCheckFragment();
                    PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, locationCityCheckFragment, null).commit();
                }
            });
        }else if(fragment instanceof LocationCityCheckFragment) {
            toolbarSecondButton.setVisibility(View.VISIBLE);
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_check_active_24dp);
            toolbarSecondButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewPromotionTargetingFragment.selectedLocations.clear();
                    for (int i = 0; i < ((LocationCityCheckFragment) fragment).selectedCities.size(); i++) {
                        NewPromotionTargetingFragment.selectedLocations.put(((LocationCityCheckFragment) fragment).selectedCities.keyAt(i), ((LocationCityCheckFragment) fragment).selectedCities.valueAt(i));
                    }
                    onBackPressed();
                    onBackPressed();
                }
            });
        }else if(fragment instanceof PromotionInfoFragment){
            toolText.setText("Подробнее");
            toolbarSecondButton.setVisibility(View.VISIBLE);
            toolbarSecondButton.setImageResource(R.drawable.ic_outline_more_vert_87_24dp);
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
        if(currentFragment instanceof NewPromotionPetsSelectListFragment) {
            ((NewPromotionPetsSelectListFragment) currentFragment).promotionsBack();
            super.onBackPressed();
        }else
            super.onBackPressed();
        currentFragment = fragmentManager.findFragmentById(R.id.defaultFrameLayout);
        onAttachFragment(currentFragment);
    }

}
