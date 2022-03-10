package com.example.churchmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//import android.widget.RadioGroup.OnCheckedChangeListener;
@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    EditText fullname, email, username, mobile, password, address;
    RadioGroup gender, usertype, churchbranch;
    RadioButton Male, Female, Pastor, Leader, Member, Thane, Mulund, Kulaba;
    Button register;
    TextView textView;
    String Sfullname, Semail, Susername, Smobile, Spassword, Saddress, Sgender, Susertype,
            Schurchbranch;
    @SuppressWarnings("CanBeFinal")
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Registration Form");
        register = findViewById(R.id.register);
        register.setOnClickListener(v -> insertdata());
        textView = findViewById(R.id.backtologin);
        textView.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Login_form.class));
            finish();
        });
    }

    private void insertdata() {
        fullname = findViewById(R.id.fullname);
        Sfullname = fullname.getText().toString();
        email = findViewById(R.id.email);
        Semail = email.getText().toString();
        username = findViewById(R.id.username);
        Susername = username.getText().toString();
        mobile = findViewById(R.id.mobile);
        Smobile = mobile.getText().toString();
        password = findViewById(R.id.password);
        Spassword = password.getText().toString();
        address = findViewById(R.id.address);
        Saddress = address.getText().toString();
        gender = findViewById(R.id.radiogp1);
        Male = findViewById(R.id.male);
        String Smale = Male.getText().toString();
        Female = findViewById(R.id.female);
        String Sfemale = Female.getText().toString();
        usertype = findViewById(R.id.radiogp2);
        Susertype = usertype.toString();
        Pastor = findViewById(R.id.pastor);
        String Spastor = Pastor.getText().toString();
        Leader = findViewById(R.id.leader);
        String Sleader = Leader.getText().toString();
        Member = findViewById(R.id.member);
        String Smember = Member.getText().toString();
        churchbranch = findViewById(R.id.radiogp3);
        // Schurchbranch = churchbranch.toString();

        Thane = findViewById(R.id.thane);
        String Sthane = Thane.getText().toString();
        Mulund = findViewById(R.id.mulund);
        String SMulund = Mulund.getText().toString();
        Kulaba = findViewById(R.id.colaba);
        String Skulaba = Kulaba.getText().toString();
        //Sgender = gender.toString();
        if (fullname.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                username.getText().toString().isEmpty()
                || mobile.getText().toString().isEmpty() || password.getText().toString().isEmpty() ||
                address.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Fill all the deatails ", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Please enter valid email address");
            return;
        } else if (!fullname.getText().toString().matches("[a-z,A-Z]*")) {
            fullname.setError("Enter only character");
            return;
        } else if (!mobile.getText().toString().matches("[0-9]{10}")) {
            mobile.setError("Enter only 10 Digit Mobile Number");
            return;
        } else if (username.getText().toString().length() > 20) {
            username.setError("username too long");
            return;
        }
        if (!Male.isChecked() && !Female.isChecked()) {
            Toast.makeText(getApplicationContext(), "Please select gender. ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Pastor.isChecked() && !Leader.isChecked() && !Member.isChecked()) {
            Toast.makeText(getApplicationContext(), "Please select User Type.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Thane.isChecked() && !Mulund.isChecked() && !Kulaba.isChecked()) {
            Toast.makeText(getApplicationContext(), "Please select Chruch Branch. ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Male.isChecked()) {
            Sgender = Smale;
        } else if (Female.isChecked()) {
            Sgender = Sfemale;
        }
        if (Pastor.isChecked()) {
            Susertype = Spastor;
        } else if (Leader.isChecked()) {
            Susertype = Sleader;
        } else {
            Susertype = Smember;
        }
        if (Thane.isChecked()) {
            Schurchbranch = Sthane;
        } else if (Mulund.isChecked()) {
            Schurchbranch = SMulund;
        } else {
            Schurchbranch = Skulaba;
        }

        db.collection("Users").whereEqualTo("username", Susername).get()
                .addOnCompleteListener(task -> {
                    if (task.getResult().isEmpty()) {
                        Map<String, Object> param;
                        param = new HashMap<>();
                        param.put("Name", Sfullname);
                        param.put("Email", Semail);
                        param.put("username", Susername);
                        param.put("mob", Smobile);
                        param.put("pass", Spassword);
                        param.put("address", Saddress);
                        param.put("gender", Sgender);
                        param.put("Type", Susertype);
                        param.put("churchbranch", Schurchbranch);
                        param.put("prayer", new ArrayList<String>());
                        param.put("scripture", new ArrayList<String>());


                        db.collection("Users").document(Susername).set(param).addOnCompleteListener(t -> {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, Login_form.class));
                            finish();
                        });

                    } else {
                        Toast.makeText(this, "User with same username already exists!", Toast.LENGTH_LONG).show();
                        username.setError("type a unique username");
                    }
                });


    }
}