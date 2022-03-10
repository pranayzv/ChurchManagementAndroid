package com.example.churchmanagementsystem;

import static com.example.churchmanagementsystem.Constants.user;
import static com.example.churchmanagementsystem.dao.DaoUser.getLoggedUser;
import static com.example.churchmanagementsystem.dao.DaoUser.removeLoggedInUser;
import static com.example.churchmanagementsystem.dao.DaoUser.setLoggedInUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.churchmanagementsystem.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class Login_form extends AppCompatActivity {
    EditText uname, password;
    Button login, register;
    TextView tv;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        uname = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        db = FirebaseFirestore.getInstance();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Login Form");
        checklogoutmsg();
        login.setOnClickListener(v -> loginFormValidation());
        user = getLoggedUser(Login_form.this);
        register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            startActivity(new Intent(Login_form.this, MainActivity.class));
            finish();
        });
        if (user != null) {
            uname.setText(user.getUname());
            password.setText(user.getPass());
        }
    }

    public void loginFormValidation() {

        if (uname.getText().toString().equals("")) {
            uname.setError("Enter valid username");
            return;
        } else if (password.getText().toString().equals("")) {
            password.setError("Enter valid password");
            return;
        }

        Log.v("login", "in execute :");

        try {
            db.collection("Users").whereEqualTo("username", uname.getText().toString()).whereEqualTo("pass", password.getText().toString())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.getResult().size() == 1) {
                            QuerySnapshot doc = task.getResult();
                            // noinspection unchecked
                            user = setLoggedInUser(new User(uname.getText().toString(),
                                    password.getText().toString(),
                                    Objects.requireNonNull(task.getResult().getDocuments().get(0).get("Type")).toString(),
                                    (ArrayList<String>) doc.getDocuments().get(0).get("scripture"),
                                    (ArrayList<String>) doc.getDocuments().get(0).get("prayer"),
                                    Objects.requireNonNull(doc.getDocuments().get(0).get("churchbranch")).toString()
                            ), Login_form.this);

                            startActivity(new Intent(getApplicationContext(), Welcome.class));

                        } else {
                            uname.setText("");
                            password.setText("");
                            removeLoggedInUser(Login_form.this);
                            Toast.makeText(Login_form.this, "User doesn't exists.", Toast.LENGTH_SHORT).show();
                        }
                    });


            Log.v("login", "in background");

        } catch (Exception ex) {
            Log.d("dbug", "btn_loginform: ", ex);
        }

    }

    public void checklogoutmsg() {
        tv = findViewById(R.id.tv);
        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        if (sp.contains("msg")) {
            tv.setText(sp.getString("msg", ""));
            SharedPreferences.Editor ed = sp.edit();
            ed.remove("msg");
            ed.apply();
        }
    }
}