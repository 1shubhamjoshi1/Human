package com.jiit.minor2.shubhamjoshi.human;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Shubham Joshi on 21-03-2017.
 */
public class MyAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MyAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.train_list_item, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        Picasso.with(context).load(values[position]).into(imageView);

        return rowView;
    }
}