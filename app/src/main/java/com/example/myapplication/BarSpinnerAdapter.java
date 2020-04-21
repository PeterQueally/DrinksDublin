package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class BarSpinnerAdapter extends ArrayAdapter<String> {

    public BarSpinnerAdapter(Context context, ArrayList<String> barNames){
        super(context,0,barNames);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    public View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bar_spinner_row,parent,false);
        }

        TextView barTextView = convertView.findViewById(R.id.barSpinnerTextview);
        String barName = getItem(position);
        barTextView.setText(barName);
        return convertView;
    }
}
