package com.example.churchmanagementsystem;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("ALL")
public class ShowScripture extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    @SuppressWarnings("CanBeFinal")
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ListView lv;
    @SuppressWarnings("CanBeFinal")
    ArrayList<String> holder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scripture);
        Objects.requireNonNull(getSupportActionBar()).setTitle("SCRIPTURE");
        lv = findViewById(R.id.lv);
        fetchdata();
    }

    public void fetchdata() {
        db.collection("Users")
                .get().addOnCompleteListener(task -> {
                    holder.clear();
                    for (QueryDocumentSnapshot qs : task.getResult()) {
                        if (qs.get("Type").toString().equals("Member")) {
                            continue;
                        }
                        ArrayList<String> u = (ArrayList<String>) qs.get("scripture");
                        for (String e : u) {
                            holder.add("Name: " + qs.get("Name") +
                                    "\nBranch: " + qs.get("churchbranch") +
                                    "\nType: " + qs.get("Type") +
                                    "\nScripture: " + e);

                        }
                        runOnUiThread(() -> {
                            ArrayAdapter<String> at = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, holder);
                            lv.setAdapter(at);
                        });

                    }

                });

    }
}