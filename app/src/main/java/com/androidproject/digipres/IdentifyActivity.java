package com.androidproject.digipres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IdentifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
    }

    public void new_patient_page(View view) {
        Intent intent = new Intent(IdentifyActivity.this,PatientActivity.class);
        startActivity(intent);
    }

    public void exsisting_patient_page(View view) {
        Intent intent = new Intent(IdentifyActivity.this,ExsistingPatient_Activity.class);
        startActivity(intent);
    }
}