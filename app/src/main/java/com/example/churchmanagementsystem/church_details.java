package com.example.churchmanagementsystem;

import static com.example.churchmanagementsystem.Constants.TAG;
import static com.example.churchmanagementsystem.Constants.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class church_details extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView chName, chFounder, chMembers, chPastors, chBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_details);

        chFounder = findViewById(R.id.cd_church_founder);
        chMembers = findViewById(R.id.cd_church_members);
        chName = findViewById(R.id.cd_church_name);
        chPastors = findViewById(R.id.cd_church_pastors);
        chBranch = findViewById(R.id.cd_church_branch);
        if(user.getBranch().equals("Kulaba")){
            chName.setText("St. Joseph R.C. Church");
            chFounder.setText("Founder & History:\nparish of St. Joseph's, Kulaba was a long, narrow island off the west coast of Salsette, just north of the city.");
        }
        else if (user.getBranch().equals("Thane")){
            chName.setText("St. John the Baptist Church");
            chFounder.setText("Founder & History:\nIt was built by the Portuguese Jesuits in 1579 and opened to public worship on the feast of John the Baptist that year.");
        }
        else if (user.getBranch().equals("Mulund")){
            chName.setText("St. Pius X Church");
            chFounder.setText("Founder & History:\nThe History of this Church located at Mulund dates back to 1968. On the 8th of January, 1989, His Eminence, Cardinal Simon Pimenta inaugurated and consecrated the new Church Building.");
        }
        chBranch.setText("Church Branch: "+user.getBranch());
        StringBuilder pastors = new StringBuilder("Pastors: ");

        db.collection("Users").whereEqualTo("churchbranch",user.getBranch()).get().addOnCompleteListener(task ->{
            int branchMembersSize = task.getResult().size();
            chMembers.setText("Total Members: "+branchMembersSize);
            if(branchMembersSize>0){
                for (QueryDocumentSnapshot qs : task.getResult()){
                    if(qs.get("Type").toString().equals("Pastor")){
                        pastors.append("\n Name: "+qs.get("Name")+", Phone: "+qs.get("mob"));
                    }
                }
            }
            chPastors.setText(pastors.toString());
            Log.d(TAG, "Size:"+branchMembersSize);
        });

    }
}