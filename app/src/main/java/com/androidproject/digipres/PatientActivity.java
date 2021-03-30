package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;

public class PatientActivity extends AppCompatActivity {

    TextInputLayout rugiName, rugiPhone, rugiAge,  rugiBp, rugiTemp, rugiPulse, rugiWeight, rugiHeight ;
    Button createPres;
    ProgressBar ProgressBar;
    RadioButton rugiGender;
    RadioGroup Radio_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);


        rugiName = findViewById(R.id.rugi_name);
        rugiPhone= findViewById(R.id.rugi_phone);
        rugiAge = findViewById(R.id.rugi_age);
        Radio_group = findViewById(R.id.radio_group);
        rugiBp = findViewById(R.id.rugi_bp);
        rugiTemp= findViewById(R.id.rugi_temp);
        rugiPulse = findViewById(R.id.rugi_pulse);
        rugiWeight = findViewById(R.id.rugi_weight);
        rugiHeight = findViewById(R.id.rugi_height);
        createPres=findViewById(R.id.create_pres);
        ProgressBar=findViewById(R.id.Progress_bar);

        createPres.setOnClickListener(this::onClick);

    }

    private void onClick(View view) {

        int selected_gender= Radio_group.getCheckedRadioButtonId();
        rugiGender = findViewById(selected_gender);

        if (!valid_name() || !valid_phone() || !valid_Age() || !valid_Gender() || !valid_Bp() || !valid_Temp() || !valid_Pulse() || !valid_weight() || !valid_height()){
            return;
        }

        ProgressBar.setVisibility(View.VISIBLE);


        String name = rugiName.getEditText().getText().toString();
        String phone = rugiPhone.getEditText().getText().toString();
        String age = rugiAge.getEditText().getText().toString();
        String gender =rugiGender.getText().toString();
        String bp = rugiBp.getEditText().getText().toString();
        String temp= rugiTemp.getEditText().getText().toString();
        String pulse = rugiPulse.getEditText().getText().toString();
        String weight = rugiWeight.getEditText().getText().toString();
        String height =rugiHeight.getEditText().getText().toString();


        double BMI = BMI_Cal(height,weight);
        String bmi=String.valueOf(BMI);

        double FC = Celcious_Cal(temp);
        String F_temp = String.valueOf(FC);
        temp = temp + "/" + F_temp;


        Intent intent = new Intent(getApplicationContext(), PrescriptionActivity.class);

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

       startActivity(intent);

    }

    private double Celcious_Cal(String temp) {
          double faren = Double.parseDouble(temp);
          double value =  (faren-32)*(0.5556);
          return Double.parseDouble(new DecimalFormat("##.##").format(value));
    }

    private double BMI_Cal(String height, String weight) {
        double ucha = Double.parseDouble(height);
        ucha/=100;
        double ojon = Double.parseDouble(weight);
        double val = ojon/(ucha*ucha);
        return Double.parseDouble(new DecimalFormat("##.##").format(val));
    }

    private boolean valid_height() {
        String val=rugiHeight.getEditText().getText().toString();

        if(val.isEmpty()){
            rugiHeight.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiHeight.setError(null);
            rugiHeight.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_weight() {
        String val=rugiWeight.getEditText().getText().toString();

        if(val.isEmpty()){
            rugiWeight.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiWeight.setError(null);
            rugiWeight.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_Pulse() {
        String val=rugiPulse.getEditText().getText().toString();
        if(val.isEmpty()){
            rugiPulse.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiPulse.setError(null);
            rugiPulse.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_Temp() {
        String val=rugiTemp.getEditText().getText().toString();

        if(val.isEmpty()){
            rugiTemp.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiTemp.setError(null);
            rugiTemp.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_Bp() {
        String val=rugiBp.getEditText().getText().toString();

        if(val.isEmpty()){
            rugiBp.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiBp.setError(null);
            rugiBp.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_Gender() {
        String val=rugiGender.getText().toString();

        return !val.isEmpty();
    }

    private boolean valid_Age() {
        String val=rugiAge.getEditText().getText().toString();

        if(val.isEmpty()){
            rugiAge.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiAge.setError(null);
            rugiAge.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_phone() {
        String val=rugiPhone.getEditText().getText().toString();

        if(val.isEmpty()){
            rugiPhone.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiPhone.setError(null);
            rugiPhone.setErrorEnabled(false);
            return true;
        }
    }

    private boolean valid_name() {
        String val= rugiName.getEditText().getText().toString();

        if(val.isEmpty()){
            rugiName.setError("Field cann't be empty");
            return false;
        }
        else{
            rugiName.setError(null);
            rugiName.setErrorEnabled(false);
            return true;
        }
    }

}