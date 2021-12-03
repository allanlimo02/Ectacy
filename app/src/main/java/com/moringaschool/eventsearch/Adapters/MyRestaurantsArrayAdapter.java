package com.moringaschool.eventsearch.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class MyRestaurantsArrayAdapter extends ArrayAdapter {
private Context context;
private String[] restaurants;

    public MyRestaurantsArrayAdapter(Context context, int resource, String[] restaurants) {
        super(context, resource);
        this.context = context;
        this.restaurants = restaurants;
    }
    @Override
    public Object getItem(int position) {
        String restaurant = restaurants[position];
        return String.format("%s \nServes great food", restaurant);
    }
    @Override
    public int getCount() {
        return restaurants.length;
    }
}
