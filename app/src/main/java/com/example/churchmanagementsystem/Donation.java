package com.example.churchmanagementsystem;

import static com.example.churchmanagementsystem.Constants.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Donation extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView dn;
    Button dnBtn;
    EditText dnEdi;
    int amount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        dn = findViewById(R.id.church_donation);
        dnBtn = findViewById(R.id.church_donation_bth);
        dnEdi = findViewById(R.id.church_donation_amount);
        updateAmount();


        dnBtn.setOnClickListener(v->{
            dnBtn.setVisibility(View.INVISIBLE);
            amount += Integer.parseInt(dnEdi.getText().toString());
            db.collection("church_donations").document("CRJuSVhtdodd1DEw8Da8")
                    .update("donation", amount)
                    .addOnCompleteListener(ta->{
                        updateAmount();
                        Toast.makeText(getApplicationContext(),"Thank you for the donation.", Toast.LENGTH_SHORT).show();
                        dnBtn.setVisibility(View.VISIBLE);
                    });
        });
    }

    public void updateAmount(){
        db.collection("church_donations").get().addOnCompleteListener(task -> {
            if(!task.getResult().isEmpty()) {
                for (QueryDocumentSnapshot qs: task.getResult()) {
                    amount = Integer.parseInt(Objects.requireNonNull(qs.get("donation")).toString());
                    dn.setText("Donations from all Believer's\n ₹" + amount);
                }
            }
        });
    }
}


