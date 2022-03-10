package com.example.churchmanagementsystem;

import static com.example.churchmanagementsystem.Constants.user;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

@SuppressWarnings("ALL")
public class Addprayer extends AppCompatActivity {
    EditText addprayer;
    Button send;
    @SuppressWarnings("CanBeFinal")
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprayer);
        Objects.requireNonNull(getSupportActionBar()).setTitle("PRAYER");
        send = findViewById(R.id.send);
        send.setOnClickListener(v -> insertData());
    }

    private void insertData() {

        addprayer = findViewById(R.id.addprayer);
        send.setEnabled(false);
        String saddprayer = addprayer.getText().toString().trim();
        if (saddprayer.isEmpty()) {
            addprayer.setError("Enter a prayer");
            return;
        }
        addprayer.setEnabled(false);
        send.setEnabled(false);
        db.collection("Users").document(user.getUname())
                .update("prayer", FieldValue.arrayUnion(saddprayer+"``false"))
                .addOnCompleteListener(task -> {
                    addprayer.setText("");
                    Toast.makeText(Addprayer.this, "Your is prayer sent successfully", Toast.LENGTH_SHORT).show();
                    addprayer.setEnabled(true);
                    send.setEnabled(true);
                });


    }


}