package com.example.churchmanagementsystem;

import static com.example.churchmanagementsystem.Constants.TAG;
import static com.example.churchmanagementsystem.Constants.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.churchmanagementsystem.listadapter.MemberPrayerAdapter;
import com.example.churchmanagementsystem.listadapter.PastorPrayerAdapter;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Showprayer extends AppCompatActivity {
    @SuppressWarnings("CanBeFinal")
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView lv;
    @SuppressWarnings("CanBeFinal")
    ArrayList<String> holder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showprayer);
        Objects.requireNonNull(getSupportActionBar()).setTitle("PRAYER");
        lv = findViewById(R.id.lv);
        fetchdata();
    }

    public void fetchdata() {
        if (user.getType().equals("Member")) {
            db.collection("Users")
                    .whereEqualTo("username", user.getUname())
                    .get()
                    .addOnCompleteListener(task -> {
                        QuerySnapshot qs = task.getResult();
                        runOnUiThread(() -> {
                            @SuppressWarnings("unchecked") ArrayList<String> u = (ArrayList<String>) qs.getDocuments().get(0).get("prayer");
                            holder.addAll(Objects.requireNonNull(u));
                            MemberPrayerAdapter at = new MemberPrayerAdapter(holder, getApplicationContext());
                            lv.setAdapter(at);
                        });
                    });
        }
        if (user.getType().equals("Pastor")) {
            db.collection("Users")
                    .get().addOnCompleteListener(task -> {
                holder.clear();
                for (QueryDocumentSnapshot qs : task.getResult()) {

                    ArrayList<String> u = (ArrayList<String>) qs.get("prayer");
                    for (String e : u) {
                        holder.add("Name: " + qs.get("username") +
                                "\nBranch: " + qs.get("churchbranch") +
                                "\nType: " + qs.get("Type") +
                                "\nprayer: " + e);
                        Log.d(TAG, "fetchdata: " + e);

                    }
                    runOnUiThread(() -> {
                        PastorPrayerAdapter at = new PastorPrayerAdapter(holder, getApplicationContext(), qs.get("Name").toString());
                        lv.setAdapter(at);
                    });

                }

            });
        }
        if (user.getType().equals("Leader")) {
            db.collection("Users")
                    .get().addOnCompleteListener(task -> {
                holder.clear();
                for (QueryDocumentSnapshot qs : task.getResult()) {

                    ArrayList<String> u = (ArrayList<String>) qs.get("prayer");
                    for (String e : u) {
                        holder.add("Name: " + qs.get("Name") +
                                "\nBranch: " + qs.get("churchbranch") +
                                "\nType: " + qs.get("Type") +
                                "\nprayer: " + e.split("``")[0]);
                        Log.d(TAG, "fetchdata: " + e);

                    }
                    runOnUiThread(() -> {

                        ArrayAdapter<String> at = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, holder);
                        lv.setAdapter(at);

                    });

                }

            });
        }
    }
}