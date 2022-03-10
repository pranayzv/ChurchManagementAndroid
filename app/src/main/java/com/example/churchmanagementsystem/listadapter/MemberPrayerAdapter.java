package com.example.churchmanagementsystem.listadapter;

import static com.example.churchmanagementsystem.Constants.TAG;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.churchmanagementsystem.R;

import java.util.ArrayList;

public class MemberPrayerAdapter implements ListAdapter {

    ArrayList<String> prayerList;
    Context context;


    public MemberPrayerAdapter(ArrayList<String> prayerList, Context context) {
        this.prayerList = prayerList;
        this.context = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return prayerList.size();
    }

    @Override
    public Object getItem(int i) {
        return prayerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String pr = prayerList.get(i);

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.member_prayer, null);

        TextView tv = view.findViewById(R.id.member_prayer_id);

        Log.d(TAG, pr);
        if(pr.split("``")[1].equals("true")) {
            tv.setText("Your prayer '"+pr.split("``")[0]+"' has been worshiped. ");
            view.setBackgroundResource(R.color.accepted);
        }else {
            tv.setText("Your prayer '"+pr.split("``")[0]+"' has not yet been worshiped. ");
            view.setBackgroundResource(R.color.not_accepted);
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
