package com.androidproject.digipres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class PatientActivity extends AppCompatActivity {

    TextInputLayout rugiName, rugiPhone, rugiAge, rugiGender, rugiBp, rugiTemp, rugiPulse, rugiWeight, rugiHeight ;
    Button createPres;
    ProgressBar ProgressBar;
    AutoCompleteTextView ATV;

    ArrayList arrayList_gender;
    ArrayAdapter<String>arrayAdapter_gender;

    String atv_gender, userID;
    FirebaseUser user;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        rugiName = findViewById(R.id.rugi_name);
        rugiPhone= findViewById(R.id.rugi_phone);
        rugiAge = findViewById(R.id.rugi_age);
        rugiGender = findViewById(R.id.rugi_gender);
        ATV = findViewById(R.id.atv);
        rugiBp = findViewById(R.id.rugi_bp);
        rugiTemp= findViewById(R.id.rugi_temp);
        rugiPulse = findViewById(R.id.rugi_pulse);
        rugiWeight = findViewById(R.id.rugi_weight);
        rugiHeight = findViewById(R.id.rugi_height);
        createPres=findViewById(R.id.create_pres);
        ProgressBar=findViewById(R.id.Progress_bar);

        gender_activity();

        createPres.setOnClickListener(this::onClick);

    }

    private void gender_activity() {
        arrayList_gender = new ArrayList<>();
        arrayList_gender.add("Male");
        arrayList_gender.add("Female");
        arrayList_gender.add("Other");

        arrayAdapter_gender = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arrayList_gender);
        ATV.setAdapter(arrayAdapter_gender);
        ATV.setThreshold(1);

        ATV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                atv_gender =ATV.getText().toString();
            }
        });
    }

    private void onClick(View view) {

        if (!valid_name() || !valid_phone() || !valid_Age() || !valid_Gender() || !valid_Bp() || !valid_Temp() || !valid_Pulse() || !valid_weight() || !valid_height()){
            return;
        }

        ProgressBar.setVisibility(View.VISIBLE);


        String name = rugiName.getEditText().getText().toString();
        String phone = rugiPhone.getEditText().getText().toString();
        String age = rugiAge.getEditText().getText().toString();
        String gender = atv_gender;
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

        PatientHelperClass patientHelperClass = new PatientHelperClass(name, age, phone, gender, pulse, bp, temp, weight, height, bmi);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        String finalTemp = temp;
        FirebaseDatabase.getInstance().getReference(String.valueOf(userID))
                .child(phone).setValue(patientHelperClass).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()){
                Toast.makeText(PatientActivity.this, "Patient Account created successfully",Toast.LENGTH_LONG).show();
                ProgressBar.setVisibility(View.GONE);

                Intent intent = new Intent(getApplicationContext(), PrescriptionActivity.class);

                intent.putExtra("keyname",name);
                intent.putExtra("keyphone",phone);
                intent.putExtra("keyage",age);
                intent.putExtra("keyweight",weight);
                intent.putExtra("keyheight",height);
                intent.putExtra("keybmi",bmi);
                intent.putExtra("keybp",bp);
                intent.putExtra("keytemp", finalTemp);
                intent.putExtra("keypulse",pulse);
                intent.putExtra("keygender",gender);

                startActivity(intent);
            }
            else{
                Exception error = task1.getException();
                Toast.makeText(PatientActivity.this, "Error " + error,Toast.LENGTH_LONG).show();
                ProgressBar.setVisibility(View.GONE);

            }
        });

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
        String val= Objects.requireNonNull(rugiHeight.getEditText()).getText().toString();

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
        String val= Objects.requireNonNull(rugiWeight.getEditText()).getText().toString();

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
        String val=rugiGender.toString();

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