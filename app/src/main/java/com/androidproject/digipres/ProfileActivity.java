package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {



    TextView   user_Fullname, user_Degree, user_Field, user_Regi, user_Email, user_Phone, user_Position, user_Office,  Name, Un;
    Button updateBtn;


    FirebaseUser user;
    DatabaseReference reference;
    String userID, nameFromDB, degreeFromDB,  unFromDB, fieldFromDB, positionFromDB, officeFromDB, regiFromDB,  emailFromDB, phoneFromDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Name = findViewById(R.id.name);
        Un = findViewById(R.id.uname);
        user_Fullname = findViewById(R.id.user_fullname);
        user_Degree = findViewById(R.id.user_degree);
        user_Field = findViewById(R.id.user_field);
        user_Position = findViewById(R.id.user_position);
        user_Office = findViewById(R.id.user_office);
        user_Regi = findViewById(R.id.user_regi);
        user_Email =  findViewById(R.id.user_email);
        user_Phone =  findViewById(R.id.user_phone);

        updateBtn=findViewById(R.id.update_page);

        show_doc_info();

        updateBtn.setOnClickListener(v -> pass_data());
    }

    private void pass_data() {
        String name = nameFromDB ;
        String un = unFromDB;
        String degree = degreeFromDB;
        String field = fieldFromDB;
        String position = positionFromDB;
        String office = officeFromDB;
        String phone = phoneFromDB;


        Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);

        intent.putExtra("key_name",name);
        intent.putExtra("key_un",un);
        intent.putExtra("key_degree",degree);
        intent.putExtra("key_field",field);
        intent.putExtra("key_position",position);
        intent.putExtra("key_office",office);
        intent.putExtra("key_phone",phone);

        startActivity(intent);
    }

    private void show_doc_info() {
        user = FirebaseAuth.getInstance().getCurrentUser();
       //reference = FirebaseDatabase.getInstance().getReference("Doctors");
        userID = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
        Query chekUser = reference.orderByChild(userID);

        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                nameFromDB = snapshot.child(userID).child("doctor_name").getValue(String.class);
                degreeFromDB = snapshot.child(userID).child("doctor_degree").getValue(String.class);
                unFromDB = snapshot.child(userID).child("doctor_un").getValue(String.class);
                fieldFromDB = snapshot.child(userID).child("doctor_field").getValue(String.class);
                positionFromDB = snapshot.child(userID).child("doctor_position").getValue(String.class);
                officeFromDB = snapshot.child(userID).child("doctor_office").getValue(String.class);
                regiFromDB = snapshot.child(userID).child("doctor_regi").getValue(String.class);
                emailFromDB = snapshot.child(userID).child("doctor_email").getValue(String.class);
                phoneFromDB = snapshot.child(userID).child("doctor_phone").getValue(String.class);

                Name.setText("Dr."+" "+nameFromDB);
                Un.setText(unFromDB);
                user_Fullname.setText(nameFromDB);
                user_Degree.setText(degreeFromDB);
                user_Field.setText(fieldFromDB);
                user_Position.setText(positionFromDB);
                user_Office.setText(officeFromDB);
                user_Regi.setText(regiFromDB);
                user_Email.setText(emailFromDB);
                user_Phone.setText(phoneFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( ProfileActivity.this, "Something Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }


}