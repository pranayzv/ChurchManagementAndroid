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
public class AddScripture extends AppCompatActivity {
    EditText addscripture;
    String saddscripture;
    Button send;
    @SuppressWarnings("CanBeFinal")
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scripture);
        Objects.requireNonNull(getSupportActionBar()).setTitle("SCRIPTURE");
        send = findViewById(R.id.send);
        send.setOnClickListener(v -> insertdata());
    }

    private void insertdata() {
        addscripture = findViewById(R.id.addscripture);
        saddscripture = addscripture.getText().toString().trim();
        if (saddscripture.isEmpty()) {
            addscripture.setError("Enter a scripture");
            return;
        }
        addscripture.setEnabled(false);
        send.setEnabled(false);
        db.collection("Users").document(user.getUname())
                .update("scripture", FieldValue.arrayUnion(saddscripture))
                .addOnCompleteListener(task -> {
                    addscripture.setText("");
                    Toast.makeText(AddScripture.this, "Your is scripture sent successfully", Toast.LENGTH_SHORT).show();
                    send.setEnabled(true);
                    addscripture.setEnabled(true);
                });

    }
}