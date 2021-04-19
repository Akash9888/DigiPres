package com.androidproject.digipres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ExsistingPatient_Activity extends AppCompatActivity {

    TextInputLayout exsisting_Number;
    Button Check_btn;
    ProgressBar progress_Bar;

    FirebaseUser user;
    DatabaseReference reference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exsisting_patient_);

        exsisting_Number = findViewById(R.id.exsisting_phone);
        Check_btn = findViewById(R.id.check_btn);
        progress_Bar = findViewById(R.id.progress_bar);


        Check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cheking();
            }
        });
    }

    private void cheking() {

        progress_Bar.setVisibility(View.VISIBLE);

        String entered_number = exsisting_Number.getEditText().getText().toString();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference(userID);

        Query chekUser = reference.orderByChild("phone").equalTo(entered_number);

        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Intent intent = new Intent(getApplicationContext(),OldPatientActivity.class);
                    intent.putExtra("keynumber",entered_number);
                    startActivity(intent);
                }else{
                    Toast.makeText( ExsistingPatient_Activity.this, "Sorrry! this number is not exsist in db",Toast.LENGTH_LONG).show();
                    progress_Bar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( ExsistingPatient_Activity.this, "Something Wrong",Toast.LENGTH_LONG).show();
                progress_Bar.setVisibility(View.GONE);
            }
        });

    }


}