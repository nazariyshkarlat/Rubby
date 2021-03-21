package com.example.rubby.OverridedWidgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rubby.R;

public class PhoneSpinnerAdapter extends ArrayAdapter<String> {

    private int[] images;
    private String[] strings;
    private Context context;

    public PhoneSpinnerAdapter(@NonNull Context context, int[] images,String[] strings) {
        super(context, R.layout.phone_spiner_item);
        this.images = images;
        this.strings = strings;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.phone_spiner_item, parent, false);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.spinnerImageView);
            mViewHolder.textView = (TextView) convertView.findViewById(R.id.spinnerTextView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.imageView.setImageResource(images[position]);
        mViewHolder.textView.setText(strings[position]);

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    @Override
    public int getCount() {
        return images.length;
    }

}
