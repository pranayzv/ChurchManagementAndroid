package com.example.churchmanagementsystem;

import static com.example.churchmanagementsystem.Constants.user;
import static com.example.churchmanagementsystem.dao.DaoUser.removeLoggedInUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Welcome extends AppCompatActivity {
    TextView tv;
    Button thane, Mulund, kulaba, addscripture, showscripture, addprayer, showprayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Objects.requireNonNull(getSupportActionBar()).setTitle("HOME PAGE");
        thane = findViewById(R.id.thane);
        Mulund = findViewById(R.id.mulund);
        kulaba = findViewById(R.id.colaba);
        addscripture = findViewById(R.id.addscripture);
        showscripture = findViewById(R.id.showscripture);
        addprayer = findViewById(R.id.addprayer);
        showprayer = findViewById(R.id.showprayer);
        tv = findViewById(R.id.tv);
        if (user.getType().equals("Member")) {
            addscripture.setVisibility(View.GONE);
            Mulund.setVisibility(View.GONE);
            kulaba.setVisibility(View.GONE);
            thane.setVisibility(View.GONE);
        }
        else if (user.getType().equals("Pastor")) {
            addscripture.setVisibility(View.GONE);
                if(user.getBranch().equals("Mulund")){
                    kulaba.setVisibility(View.GONE);
                    thane.setVisibility(View.GONE);
                }
                else if(user.getBranch().equals("Kulaba")){
                    Mulund.setVisibility(View.GONE);
                    thane.setVisibility(View.GONE);
                }
                else if(user.getBranch().equals("Thane")){
                    Mulund.setVisibility(View.GONE);
                    kulaba.setVisibility(View.GONE);
                }
        }
        String s=user.getType().toUpperCase() + ": " + user.getUname() +
                "\nBRANCH: " + user.getBranch();
        tv.setText(s);
        thane.setOnClickListener(v -> startActivity(new Intent(Welcome.this, Thane.class)));
        Mulund.setOnClickListener(v -> startActivity(new Intent(Welcome.this, Mulund.class)));
        kulaba.setOnClickListener(v -> startActivity(new Intent(Welcome.this, com.example.churchmanagementsystem.kulaba.class)));
        addprayer.setOnClickListener(v -> startActivity(new Intent(Welcome.this, Addprayer.class)));
        showprayer.setOnClickListener(v -> startActivity(new Intent(Welcome.this, Showprayer.class)));
        addscripture.setOnClickListener(v -> startActivity(new Intent(Welcome.this, AddScripture.class)));
        showscripture.setOnClickListener(v -> startActivity(new Intent(Welcome.this, ShowScripture.class)));
    }

    public void logout_process(View v) {
        removeLoggedInUser(Welcome.this);
        startActivity(new Intent(getApplicationContext(), Login_form.class));
        finish();
    }
}