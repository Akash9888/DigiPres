package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class PrescriptionActivity extends AppCompatActivity {

    TextView val_Age;
    TextView val_Weight;
    TextView val_Height;
    TextView val_Bmi;
    TextView val_Temp;
    TextView val_Bp;
    TextView val_Pulse;
    TextView val_Gender;
    String val_name, val_phone;
    TextInputLayout CC, RR, ADV, DRUG;
    Button Done;
    ProgressBar ProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        val_Age = findViewById(R.id.val_age);
        val_Weight = findViewById(R.id.val_weight);
        val_Height = findViewById(R.id.val_height);
        val_Bmi = findViewById(R.id.val_bmi);
        val_Bp = findViewById(R.id.val_bp);
        val_Temp = findViewById(R.id.val_temp);
        val_Pulse = findViewById(R.id.val_pulse);
        val_Gender = findViewById(R.id.val_gender);

        show_all_rugi_data();

        CC = findViewById(R.id.cc);
        RR = findViewById(R.id.rr);
        ADV = findViewById(R.id.adv);
        DRUG = findViewById(R.id.drugs);
        ProgressBar=findViewById(R.id.Progress_bar);

        Done = findViewById(R.id.done);

        Done.setOnClickListener(v -> final_pres());
    }

    private void final_pres() {


        ProgressBar.setVisibility(View.VISIBLE);

        String name= val_name;
        String phone= val_phone;
        String age = val_Age.getText().toString();
        String weight = val_Weight.getText().toString();
        String height = val_Height.getText().toString();
        String bmi = val_Bmi.getText().toString();
        String bp = val_Bp.getText().toString();
        String temp = val_Temp.getText().toString();
        String pulse = val_Pulse.getText().toString();
        String gender = val_Gender.getText().toString();


        String cc = CC.getEditText().getText().toString();
        String rr = RR.getEditText().getText().toString();
        String adv = ADV.getEditText().getText().toString();
        String drugs =DRUG.getEditText().getText().toString();

        Toast.makeText(PrescriptionActivity.this, "Prescription created successfully",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), FinalActivity.class);

        intent.putExtra("keyname",name);
        intent.putExtra("keyphone",phone);
        intent.putExtra("keyage",age);
        intent.putExtra("keyweight",weight);
        intent.putExtra("keyheight",height);
        intent.putExtra("keybmi",bmi);
        intent.putExtra("keybp",bp);
        intent.putExtra("keytemp",temp);
        intent.putExtra("keypulse",pulse);
        intent.putExtra("keygender",gender);
        intent.putExtra("keycc",cc);
        intent.putExtra("keyrr",rr);
        intent.putExtra("keyadv",adv);
        intent.putExtra("keyrx",drugs);

        startActivity(intent);

    }


    private void show_all_rugi_data() {

        Intent intent = getIntent();

        String Name = intent.getStringExtra("keyname");
        String Phone = intent.getStringExtra("keyphone");

        String Age = intent.getStringExtra("keyage");
        String Weight = intent.getStringExtra("keyweight");
        String Height = intent.getStringExtra("keyheight");
        String BMI = intent.getStringExtra("keybmi");
        String BP = intent.getStringExtra("keybp");
        String Temp = intent.getStringExtra("keytemp");
        String Pulse = intent.getStringExtra("keypulse");
        String Gender = intent.getStringExtra("keygender");

        val_name=Name;
        val_phone=Phone;

        val_Age.setText(Age);
        val_Weight.setText(Weight);
        val_Height.setText(Height);
        val_Bmi.setText(BMI);
        val_Bp.setText(BP);
        val_Temp.setText(Temp);
        val_Pulse.setText(Pulse);
        val_Gender.setText(Gender);

    }

}