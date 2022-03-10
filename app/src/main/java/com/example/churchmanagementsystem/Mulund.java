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
public class Mulund extends AppCompatActivity {
    @SuppressWarnings("CanBeFinal")
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //String apiurl="http://192.168.0.111/shalom_project/Mulund_fetch.php";
    ListView lv;
    @SuppressWarnings("CanBeFinal")
    ArrayList<String> holder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulund);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Mulund");
        fetchdata();
    }

    public void fetchdata() {
        lv = findViewById(R.id.lv);
        db.collection("Users")
                .get().addOnCompleteListener(task -> {
                    holder.clear();
                    for (QueryDocumentSnapshot qs : task.getResult()) {

                        runOnUiThread(() -> {
                            if (qs.get("churchbranch").toString().equals("Mulund")) {
                                holder.add("Name: " + qs.get("Name") +
                                        "\nChurchBranch: " + qs.get("churchbranch") +
                                        "\nEmail: " + qs.get("Email") +
                                        "\nMobile Number: " + qs.get("mob") +
                                        "\nGender: " + qs.get("gender")
                                );

                            }
                            ArrayAdapter<String> at = new ArrayAdapter<>(getApplicationContext(),
                                    android.R.layout.simple_list_item_1, holder);
                            lv.setAdapter(at);
                        });

                    }

                });
    }
}