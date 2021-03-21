package com.example.rubby.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.Activities.AppCompatActivities.PromotionsActivity;
import com.example.rubby.OverridedWidgets.ChildFragment;
import com.example.rubby.OverridedWidgets.CustomSpeedScrollRecyclerViewLayoutManager;
import com.example.rubby.OverridedWidgets.TintRecyclerView;
import com.example.rubby.Database.CreditCardsDatabase;
import com.example.rubby.Model.CreditCardModel;
import com.example.rubby.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodsFragment extends ChildFragment implements ListBottomSheetIconWithCallbackFragment.onItemClickCallBack,EmptyLayoutFragment.onCreateEmptyLayoutCallBack,AddCreditCardBottomSheetFragment.onButtonClickCallBack {

    private int selectedPos;
    private int itemViewHeight;
    private boolean editCard = false;
    private List<CreditCardModel> creditCardModels = new ArrayList<>();
    private CreditCardsDatabase creditCardsDatabase;
    private EmptyLayoutFragment emptyLayoutFragment = new EmptyLayoutFragment();
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private FrameLayout frameLayout;
    private FloatingActionButton floatingActionButton;
    private RecyclerView.LayoutManager layoutManager;
    private TintRecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.recycler_view_with_fab_layout, container, false);
        recyclerView = (TintRecyclerView) v.findViewById(R.id.recyclerViewWithFabLayout);
        frameLayout = (FrameLayout) v.findViewById(R.id.recyclerViewWithFabFrameLayout);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.recyclerViewWithFabFab);
        getChildFragmentManager().beginTransaction().add(R.id.recyclerViewWithFabFrameLayout, emptyLayoutFragment, null).hide(emptyLayoutFragment).commit();
        emptyLayoutFragment.registerOnCreateCallBack(this);
        layoutManager = new CustomSpeedScrollRecyclerViewLayoutManager(PromotionsActivity.context, CustomSpeedScrollRecyclerViewLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        creditCardsDatabase = new CreditCardsDatabase(PromotionsActivity.context);
        sqLiteDatabase =  creditCardsDatabase.getWritableDatabase();
        cursor = sqLiteDatabase.query(CreditCardsDatabase.DATABASE_TABLE, null, null, null, null, null, null);
        creditCardModels = creditCardsDatabase.addAll();
        rAdapter = new PaymentMethodsFragmentAdapter();
        recyclerView.setAdapter(rAdapter);
        recyclerView.setElevationHideFab(PromotionsActivity.toolbarLayout, floatingActionButton);
        checkIfEmpty();
        this.v = frameLayout;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });

        return v;
    }

    @Override
    public void onItemClick(int position, ListBottomSheetIconWithCallbackFragment.ListBottomSheetIconWithCallbackFragmentAdapter rAdapter, ListBottomSheetIconWithCallbackFragment.ListBottomSheetIconWithCallbackFragmentHolder viewHolder, ListBottomSheetIconWithCallbackFragment listBottomSheetIconWithCallbackFragment) {
        if(position == 0){
            editCard(selectedPos);
        }else if(position == 1){
            creditCardsDatabase.removeItem(selectedPos);
            creditCardModels.remove(selectedPos);
            this.rAdapter.notifyItemRemoved(selectedPos);
            recyclerView.scrollY = recyclerView.scrollY - itemViewHeight;
            checkIfEmpty();
            floatingActionButton.hide();
        }
        listBottomSheetIconWithCallbackFragment.dismiss();
    }

    @Override
    public void onCreateEmptyLayout() {
        emptyLayoutFragment.title.setText("Вы пока не добавили ни одного способа оплаты");
        emptyLayoutFragment.subtitle.setText("Добавьте свой первый способ оплаты прямо сейчас");
        emptyLayoutFragment.button.setText("Добавить");
        emptyLayoutFragment.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });
    }

    @Override
    public void onButtonClick(String cardNumber, String cardValidation, String cardSecurityNumber, String ownerName) {

        if(!editCard) {
            creditCardsDatabase.addItem(cardNumber, cardValidation, cardSecurityNumber, ownerName);
            creditCardModels.add(new CreditCardModel(creditCardModels.size() + 1, cardNumber, cardValidation, cardSecurityNumber, ownerName));
            rAdapter.notifyItemInserted(0);
            recyclerView.smoothScrollToPosition(0);
            checkIfEmpty();
        }else {
            creditCardsDatabase.updateItem(cardNumber, cardValidation, cardSecurityNumber, ownerName, selectedPos+1);
            creditCardModels.set(selectedPos,creditCardsDatabase.getItem(selectedPos+1));
            rAdapter.notifyItemChanged(selectedPos);
        }
    }

    private class PaymentMethodsFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subtitle;
        private ImageView icon;
        private ConstraintLayout layout;

        public PaymentMethodsFragmentHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.title_subtitle_icon, parent, false));
            title = (TextView) itemView.findViewById(R.id.titleSubtitleIconItemTitle);
            subtitle = (TextView) itemView.findViewById(R.id.titleSubtitleIconItemSubtitle);
            layout = (ConstraintLayout) itemView.findViewById(R.id.titleSubtitleIconItemLayout);
            icon = (ImageView) itemView.findViewById(R.id.titleSubtitleIconItemIcon);

        }

    }

    private class PaymentMethodsFragmentAdapter extends RecyclerView.Adapter<PaymentMethodsFragmentHolder> {

        @NonNull
        @Override
        public PaymentMethodsFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PaymentMethodsFragmentHolder(layoutInflater, parent);

        }

        @Override
        public void onBindViewHolder(@NonNull final PaymentMethodsFragmentHolder holder, final int position) {

            CreditCardModel creditCardModel = creditCardModels.get((getItemCount()-1)-position);
            checkIfEmpty();

            if(itemViewHeight == 0)
                itemViewHeight = holder.layout.getMeasuredHeight();

            holder.icon.setImageResource(R.drawable.ic_outline_more_vert_87_24dp);

            if(recyclerView.getVisibility() != View.VISIBLE)
                recyclerView.setVisibility(View.VISIBLE);

            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPos = holder.getLayoutPosition();
                    ListBottomSheetIconWithCallbackFragment listBottomSheetIconWithCallbackFragment = new ListBottomSheetIconWithCallbackFragment();
                    listBottomSheetIconWithCallbackFragment.iconsS = new int[] {R.drawable.ic_outline_edit_selector_24dp, R.drawable.ic_outline_delete_outline_selector_24dp};
                    listBottomSheetIconWithCallbackFragment.titleS = new String[] {"Изменить", "Удалить"};
                    listBottomSheetIconWithCallbackFragment.show(PromotionsActivity.fragmentManager, null);
                    listBottomSheetIconWithCallbackFragment.registerOnItemClickCallBack(PaymentMethodsFragment.this);
                }
            });

            holder.title.setText(creditCardModel.getEncodedNumber(creditCardModel.cardNumber));
            holder.subtitle.setText(creditCardModel.getEncodedCardValidity(creditCardModel.cardValidity));


        }

        @Override
        public int getItemCount() {
            return creditCardModels.size();
        }
    }

    public void checkIfEmpty(){
        if(creditCardModels.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            getChildFragmentManager().beginTransaction().show(emptyLayoutFragment).commit();
            floatingActionButton.hide();
            recyclerView.hide = true;
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            getChildFragmentManager().beginTransaction().hide(emptyLayoutFragment).commit();
            floatingActionButton.show();
        }
    }

    private void editCard(int position){
        editCard = true;
        CreditCardModel creditCardModel = creditCardModels.get(position);
        AddCreditCardBottomSheetFragment addCreditCardBottomSheetFragment = new AddCreditCardBottomSheetFragment();
        addCreditCardBottomSheetFragment.registerOnButtonClickCallBack(PaymentMethodsFragment.this);
        addCreditCardBottomSheetFragment.title = "Изменить способ оплаты";
        addCreditCardBottomSheetFragment.buttonText = "Сохранить";
        addCreditCardBottomSheetFragment.cardNumber = creditCardModel.cardNumber;
        addCreditCardBottomSheetFragment.validationText = creditCardModel.cardValidity;
        addCreditCardBottomSheetFragment.securityCode = creditCardModel.securityCode;
        addCreditCardBottomSheetFragment.ownerName = creditCardModel.ownerName;
        addCreditCardBottomSheetFragment.show(PromotionsActivity.fragmentManager,null);
    }

    private void addCard(){
        editCard = false;
        AddCreditCardBottomSheetFragment addCreditCardBottomSheetFragment = new AddCreditCardBottomSheetFragment();
        addCreditCardBottomSheetFragment.registerOnButtonClickCallBack(PaymentMethodsFragment.this);
        addCreditCardBottomSheetFragment.title = "Добавить новый способ оплаты";
        addCreditCardBottomSheetFragment.buttonText = "Добавить";
        addCreditCardBottomSheetFragment.show(PromotionsActivity.fragmentManager,null);
    }

}
