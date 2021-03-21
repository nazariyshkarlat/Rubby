package com.example.rubby.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.AccountEditActivity;
import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.Model.PromotionsModel;
import com.example.rubby.R;

public class PromotionsFragment extends SmoothFragment implements EmptyLayoutFragment.onCreateEmptyLayoutCallBack {

    private String titleS[] = {"Промоакции", "Способы оплаты"};
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    public static boolean newPromotionCreated = false;
    private EmptyLayoutFragment emptyLayoutFragment;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_layout, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewLayout);
        layoutManager = new LinearLayoutManager(AccountEditActivity.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new PromotionsFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        if(newPromotionCreated) {
            openPromotions();
            newPromotionCreated = false;
        }
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        return v;
    }

    @Override
    public void onCreateEmptyLayout() {
        emptyLayoutFragment.title.setText("Список ваших промоакций пуст");
        emptyLayoutFragment.subtitle.setVisibility(View.GONE);
        emptyLayoutFragment.button.setText("Создать промоакцию");
        emptyLayoutFragment.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PromotionsActivity)getActivity()).promotionsModel = new PromotionsModel();
                NewPromotionTypeFragment newPromotionTypeFragment = new NewPromotionTypeFragment();
                PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, newPromotionTypeFragment, null).commit();
            }
        });
    }

    private class PromotionsFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private ConstraintLayout layout;

        public PromotionsFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_icon_item, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleIconTitle);
            icon = (ImageView) itemView.findViewById(R.id.titleIconIcon);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleIconLayout);

        }

    }

    private class PromotionsFragmentAdapter extends RecyclerView.Adapter<PromotionsFragmentHolder> {

        @NonNull
        @Override
        public PromotionsFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PromotionsFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PromotionsFragmentHolder holder, final int position) {

            holder.title.setText(titleS[position]);
            holder.icon.setImageResource(R.drawable.ic_outline_keyboard_arrow_right_60_24dp);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        openPromotions();
                    }else if(position == 1){
                        PaymentMethodsFragment promotionsPaymentMethodsFragment = new PaymentMethodsFragment();
                        PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsPaymentMethodsFragment, null).commit();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {

            return titleS.length;

        }
    }

    public void openPromotions(){
        if(((PromotionsActivity)getActivity()).promotionsDatabase.addModelForList().size() == 0) {
            emptyLayoutFragment = new EmptyLayoutFragment();
            emptyLayoutFragment.registerOnCreateCallBack(PromotionsFragment.this);
            PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, emptyLayoutFragment, null).commit();
        }else {
            PromotionsMainFragment promotionsMainFragment = new PromotionsMainFragment();
            PromotionsActivity.fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).replace(R.id.defaultFrameLayout, promotionsMainFragment, null).commit();
        }
    }

}
