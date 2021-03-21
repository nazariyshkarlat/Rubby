package com.example.rubby.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SubItem implements Parcelable {

    protected SubItem(Parcel in) {
    }

    public SubItem() {
    }

        public static final Creator<SubItem> CREATOR = new Creator<SubItem>() {
            @Override
            public SubItem createFromParcel(Parcel in) {
                return new SubItem(in);
            }

            @Override
            public SubItem[] newArray(int size) {
                return new SubItem[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
}
