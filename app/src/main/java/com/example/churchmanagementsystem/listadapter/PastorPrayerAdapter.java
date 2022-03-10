package com.example.churchmanagementsystem.listadapter;

import static com.example.churchmanagementsystem.Constants.TAG;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.churchmanagementsystem.Addprayer;
import com.example.churchmanagementsystem.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class PastorPrayerAdapter implements ListAdapter {

        ArrayList<String> prayerList;
        Context context;
        String username;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public PastorPrayerAdapter(ArrayList<String> prayerList, Context context, String username) {
            this.prayerList = prayerList;
            this.context = context;
            this.username=username;
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
            view = layoutInflater.inflate(R.layout.pastor_prayer, null);

            TextView tv = view.findViewById(R.id.pastor_prayer_id);
            Button prayerbtn = view.findViewById(R.id.prayer_btn);
            tv.setText(pr.split("``")[0]);
            Log.d(TAG, pr);
            prayerbtn.setOnClickListener(v->{
                String user = pr.split("\n")[0].split(": ")[1];
                String prayer = pr.split("\n")[3].split(": ")[1];
                String upadtePr = prayer.split("``")[0]+"``true";
                Log.d(TAG, "prayer:"+prayer);
                db.collection("Users").document(user)
                        .update("prayer", FieldValue.arrayRemove(prayer))
                        .addOnCompleteListener(ta->{
                            db.collection("Users").document(user)
                                    .update("prayer", FieldValue.arrayUnion(upadtePr))
                                    .addOnCompleteListener(a->{
                                        tv.setBackgroundResource(R.color.accepted);
                                    });
                        });

                v.setVisibility(View.GONE);
            });
            if(pr.split("``")[1].equals("true")) {
                tv.setBackgroundResource(R.color.accepted);
                prayerbtn.setVisibility(View.GONE);
            }else {
                tv.setBackgroundResource(R.color.not_accepted);
            }
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
}
