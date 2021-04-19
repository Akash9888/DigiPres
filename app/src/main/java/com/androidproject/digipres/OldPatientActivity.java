package com.androidproject.digipres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OldPatientActivity extends AppCompatActivity {

    TextInputLayout op_Name, op_Age, op_Temp, op_Gender, op_Pulse,  op_Bp, op_Height, op_Weight;
    TextView op_Phone;
    Button Go_Pres;
    ProgressBar Progress_Bar;

    AutoCompleteTextView ATV;

    ArrayList<Object> arrayList_gender;
    ArrayAdapter<Object> arrayAdapter_gender;


    FirebaseUser user;
    DatabaseReference reference;
    String userID, nameFromDB, ageFromDB, bpFromDB, tempFromDB, genderFromDB,  pulseFromDB, heightFromDB, weightFromDB, phoneFromDB, bmiFromDB, phone_number,atv_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_patient);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference(userID);

        op_Name = findViewById(R.id.op_name);
        op_Age = findViewById(R.id.op_age);
        op_Bp = findViewById(R.id.op_bp);
        op_Temp= findViewById(R.id.op_temp);
        op_Weight = findViewById(R.id.op_weight);
        op_Height = findViewById(R.id.op_height);
        op_Gender = findViewById(R.id.op_gender);
        op_Pulse = findViewById(R.id.op_pulse);
        op_Phone = findViewById(R.id.op_phone);
        Go_Pres = findViewById(R.id.go_pres);
        Progress_Bar = findViewById(R.id.Progress_bar);

        ATV = findViewById(R.id.op_atv);
        gender_activity();

        rugi_info();

        Go_Pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_value();
            }
        });
    }

    private void update_value() {
        if (!new_name() || !new_age() || !new_gender() || !new_pulse() || !new_bp() || !new_temp() || !new_weight() || !new_height()){
            return;
        }

        Progress_Bar.setVisibility(View.VISIBLE);

        name_changed();
        age_changed();
        gender_changed();
        pulse_changed();
        bp_changed();
        temp_changed();
        weight_changed();
        height_changed();
        bmi_changed();

        pass_data();
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


    //pass data in prescription page
    private void pass_data() {
        Intent intent = new Intent(getApplicationContext(), PrescriptionActivity.class);

        intent.putExtra("keyname",nameFromDB);
        intent.putExtra("keyphone",phoneFromDB);
        intent.putExtra("keyage",ageFromDB);
        intent.putExtra("keyweight",weightFromDB);
        intent.putExtra("keyheight",heightFromDB);
        intent.putExtra("keybmi",bmiFromDB);
        intent.putExtra("keybp",bpFromDB);
        intent.putExtra("keytemp",tempFromDB );
        intent.putExtra("keypulse",pulseFromDB);
        intent.putExtra("keygender",genderFromDB);

        startActivity(intent);
    }

    //set updated value in DB
    private void name_changed() {
        if(!nameFromDB.equals(op_Name.getEditText().getText().toString())){
            reference.child(phone_number).child("name").setValue(op_Name.getEditText().getText().toString());
            nameFromDB=op_Name.getEditText().getText().toString();
        }
        else return;
    }

    private void age_changed() {
        if(!ageFromDB.equals(op_Age.getEditText().getText().toString())){
            reference.child(phone_number).child("age").setValue(op_Age.getEditText().getText().toString());
            ageFromDB=op_Age.getEditText().getText().toString();
        }
        else return;
    }

    private void gender_changed() {
        if(!genderFromDB.equals(op_Gender.getEditText().getText().toString())){
            reference.child(phone_number).child("gender").setValue(op_Gender.getEditText().getText().toString());
            nameFromDB=op_Gender.getEditText().getText().toString();
        }
        else return;
    }

    private void pulse_changed() {
        if(!pulseFromDB.equals(op_Pulse.getEditText().getText().toString())){
            reference.child(phone_number).child("pulse").setValue(op_Pulse.getEditText().getText().toString());
            nameFromDB=op_Name.getEditText().getText().toString();
        }
        else return;
    }

    private void bp_changed() {
        if(!bpFromDB.equals(op_Bp.getEditText().getText().toString())){
            reference.child(phone_number).child("bp").setValue(op_Bp.getEditText().getText().toString());
            bpFromDB=op_Name.getEditText().getText().toString();
        }
        else return;
    }

    private void temp_changed() {
        if(!tempFromDB.equals(op_Temp.getEditText().getText().toString())){
            reference.child(phone_number).child("temp").setValue(op_Bp.getEditText().getText().toString());
            tempFromDB=op_Temp.getEditText().getText().toString();
        }
        else return;
    }

    private void weight_changed() {
        if(!weightFromDB.equals(op_Weight.getEditText().getText().toString())){
            reference.child(phone_number).child("name").setValue(op_Weight.getEditText().getText().toString());
            weightFromDB=op_Weight.getEditText().getText().toString();
        }
        else return;
    }

    private void height_changed() {
        if(!heightFromDB.equals(op_Height.getEditText().getText().toString())){
            reference.child(phone_number).child("height").setValue(op_Height.getEditText().getText().toString());
            heightFromDB=op_Height.getEditText().getText().toString();
        }
        else return;
    }

    private void bmi_changed() {
        double ucha = Double.parseDouble(heightFromDB);
        ucha/=100;
        double ojon = Double.parseDouble(weightFromDB);
        double val = ojon/(ucha*ucha);
        double BMI= Double.parseDouble(new DecimalFormat("##.##").format(val));
        bmiFromDB=String.valueOf(BMI);
        reference.child(phone_number).child("bmi").setValue(bmiFromDB);
        return;
    }


    //update patient info
    private boolean new_height() {
        String height= op_Height.getEditText().getText().toString();
        if(height.isEmpty()){
            op_Height.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Height.setError(null);
           op_Height.setErrorEnabled(false);
            return true;
        }
    }

    private boolean new_weight() {
        String weight= op_Weight.getEditText().getText().toString();
        if(weight.isEmpty()){
            op_Weight.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Weight.setError(null);
            op_Weight.setErrorEnabled(false);
            return true;
        }
    }

    private boolean new_temp() {
        String temp= op_Temp.getEditText().getText().toString();
        if(temp.isEmpty()){
            op_Temp.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Temp.setError(null);
            op_Temp.setErrorEnabled(false);
            return true;
        }
    }

    private boolean new_bp() {
        String bp = op_Bp.getEditText().getText().toString();
        if(bp.isEmpty()){
            op_Bp.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Bp.setError(null);
            op_Bp.setErrorEnabled(false);
            return true;
        }
    }

    private boolean new_pulse() {
        String pulse= op_Pulse.getEditText().getText().toString();
        if(pulse.isEmpty()){
            op_Pulse.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Pulse.setError(null);
            op_Pulse.setErrorEnabled(false);
            return true;
        }
    }

    private boolean new_gender() {
        String gender= op_Gender.getEditText().getText().toString();
        if(gender.isEmpty()){
            op_Gender.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Gender.setError(null);
            op_Gender.setErrorEnabled(false);
            return true;
        }
    }

    private boolean new_age() {
        String age= op_Age.getEditText().getText().toString();
        if(age.isEmpty()){
            op_Age.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Age.setError(null);
            op_Age.setErrorEnabled(false);
            return true;
        }
    }

    private boolean new_name() {
        String name= op_Name.getEditText().getText().toString();
        if(name.isEmpty()){
            op_Name.setError("Field cann't be empty");
            return false;
        }
        else{
            op_Name.setError(null);
            op_Name.setErrorEnabled(false);
            return true;
        }
    }


   //display patient previous info in xml file
    private void rugi_info() {

        Intent intent = getIntent();
        phone_number = intent.getStringExtra("keynumber");

        Query chekUser = reference.orderByChild(phone_number);
        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                nameFromDB = snapshot.child(phone_number).child("name").getValue(String.class);
                ageFromDB = snapshot.child(phone_number).child("age").getValue(String.class);
                tempFromDB = snapshot.child(phone_number).child("temp").getValue(String.class);
                bpFromDB = snapshot.child(phone_number).child("bp").getValue(String.class);
                pulseFromDB = snapshot.child(phone_number).child("pulse").getValue(String.class);
                genderFromDB = snapshot.child(phone_number).child("gender").getValue(String.class);
                weightFromDB = snapshot.child(phone_number).child("weight").getValue(String.class);
                heightFromDB = snapshot.child(phone_number).child("height").getValue(String.class);
                phoneFromDB = phone_number;

                op_Name.getEditText().setText(nameFromDB);
                op_Age.getEditText().setText(ageFromDB);
                op_Temp.getEditText().setText(tempFromDB);
                op_Bp.getEditText().setText(bpFromDB);
                op_Pulse.getEditText().setText(pulseFromDB);
               // op_Gender.getEditText().setText(genderFromDB);
                op_Weight.getEditText().setText(weightFromDB);
                op_Height.getEditText().setText(heightFromDB);
                op_Phone.setText(phoneFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( OldPatientActivity.this, "Something Wrong",Toast.LENGTH_LONG).show();
            }
        });

    }


}