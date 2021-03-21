package com.example.rubby.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.rubby.OverridedWidgets.SmoothFragment;
import com.example.rubby.R;

public class SearchTextInputLayoutFragment extends SmoothFragment {

    private EditText editText;

    public onTextChangeCallBack onTextChangeCallBack;

    public interface onTextChangeCallBack {
        void onTextChange(String text);
    }

    public void registerOnTextChangeCallBack(onTextChangeCallBack callback) {
        this.onTextChangeCallBack = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.text_input_layout_item, container, false);
        editText = (EditText) v.findViewById(R.id.textInputLayoutItemEditText);
        editText.setHint("Поиск");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextChangeCallBack.onTextChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

}
