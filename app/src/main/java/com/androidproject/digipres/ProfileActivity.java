package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {



    TextInputLayout  user_fullname, user_degree, user_field, user_regi, user_email, user_phone;
    TextView Name, Un;


    FirebaseUser user;
    DatabaseReference reference;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Doctors");
        userID = user.getUid();


        Name = findViewById(R.id.name);
        Un = findViewById(R.id.uname);
        user_fullname = findViewById(R.id.full_name);
        user_degree = findViewById(R.id.degree);
        user_field = findViewById(R.id.field);
        user_regi = findViewById(R.id.regi);
        user_email =  findViewById(R.id.email);
        user_phone =  findViewById(R.id.phone);

        show_doc_info();
    }

    private void show_doc_info() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        Query chekUser = reference.orderByChild(userID);

        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nameFromDB = snapshot.child(userID).child("doctor_name").getValue(String.class);
                String degreeFromDB = snapshot.child(userID).child("doctor_degree").getValue(String.class);
                String unFromDB = snapshot.child(userID).child("doctor_un").getValue(String.class);
                String fieldFromDB = snapshot.child(userID).child("doctor_field").getValue(String.class);
                String regiFromDB = snapshot.child(userID).child("doctor_regi").getValue(String.class);
                String emailFromDB = snapshot.child(userID).child("doctor_email").getValue(String.class);
                String phoneFromDB = snapshot.child(userID).child("doctor_phone").getValue(String.class);

                Name.setText("Dr."+" "+nameFromDB);
                Un.setText(unFromDB);
                user_fullname.getEditText().setText(nameFromDB);
                user_degree.getEditText().setText(degreeFromDB);
                user_field.getEditText().setText(fieldFromDB);
                user_regi.getEditText().setText(regiFromDB);
                user_email.getEditText().setText(emailFromDB);
                user_phone.getEditText().setText(phoneFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( ProfileActivity.this, "Something Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }


}